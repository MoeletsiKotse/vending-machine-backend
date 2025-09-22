package com.vending_machine.vending_machine;

import com.vending_machine.vending_machine.dto.ItemDTO;
import com.vending_machine.vending_machine.dto.PurchaseRequest;
import com.vending_machine.vending_machine.model.CashInventory;
import com.vending_machine.vending_machine.model.Product;
import com.vending_machine.vending_machine.model.Transaction;
import com.vending_machine.vending_machine.repository.CashInventoryRepository;
import com.vending_machine.vending_machine.repository.TransactionRepository;
import com.vending_machine.vending_machine.service.ProductService;
import com.vending_machine.vending_machine.service.PurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PurchaseServiceTests {
    @Mock
    private ProductService productService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CashInventoryRepository cashInventoryRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void purchase_successfulTransaction() {
        List<Product> products = new ArrayList<>();
        Product product = new Product("test", 5, 50);
        products.add(product);

        ItemDTO item1 = new ItemDTO(50, product.getId(), 1);
        List<ItemDTO> items = new ArrayList<>();
        items.add(item1);

        PurchaseRequest request = new PurchaseRequest(items,100,200);

        List<CashInventory> cashInventories = new ArrayList<>();
        CashInventory cash1 = new CashInventory(100, 2);
        CashInventory cash2 = new CashInventory(50, 3);
        cashInventories.add(cash1);
        cashInventories.add(cash2);

        when(productService.getProducts()).thenReturn(products);
        when(cashInventoryRepository.findAll()).thenReturn(cashInventories);

        Transaction savedTransaction = new Transaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = purchaseService.purchase(request);

        assertNotNull(result);
        verify(productService).updateProducts(anyList());
        verify(transactionRepository).save(any(Transaction.class));
        verify(cashInventoryRepository).saveAll(anyList());
    }

    @Test
    void purchase_invalidPaidAmount_throwsException() {
        PurchaseRequest request = new PurchaseRequest(Collections.emptyList(),100,0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> purchaseService.purchase(request));

        assertEquals("PaidAmount or TotalAmount cannot be less or equal to zero", ex.getMessage());
    }

    @Test
    void purchase_insufficientFunds_throwsException() {
        PurchaseRequest request = new PurchaseRequest(Collections.emptyList(),100,50);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> purchaseService.purchase(request));

        assertEquals("Insufficient funds", ex.getMessage());
    }

    @Test
    void purchase_itemsPriceMismatch_throwsException() {
        List<ItemDTO> items = new ArrayList<>();
        ItemDTO item = new ItemDTO(1, UUID.randomUUID(), 60);
        items.add(item);
        PurchaseRequest request = new PurchaseRequest(items,40,50);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> purchaseService.purchase(request));

        assertEquals("Items prices not match total price", ex.getMessage());
    }

    @Test
    void purchase_cannotProvideChange_throwsException() {
        List<Product> products = new ArrayList<>();
        Product product = new Product("test", 5, 50);
        products.add(product);
        List<ItemDTO> items = new ArrayList<>();
        ItemDTO item = new ItemDTO(1, product.getId(), 50);
        items.add(item);
        PurchaseRequest request = new PurchaseRequest(items,50,200);

        when(productService.getProducts()).thenReturn(products);

        List<CashInventory> cashInventories = new ArrayList<>();
        CashInventory cash1 = new CashInventory(100, 0);
        CashInventory cash2 = new CashInventory(50, 0);
        cashInventories.add(cash1);
        cashInventories.add(cash2);

        when(cashInventoryRepository.findAll()).thenReturn(cashInventories);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> purchaseService.purchase(request));

        assertEquals("Cannot provide a change given amount", ex.getMessage());
    }
}
