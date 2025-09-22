package com.vending_machine.vending_machine.service;

import com.vending_machine.vending_machine.dto.ItemDTO;
import com.vending_machine.vending_machine.dto.PurchaseRequest;
import com.vending_machine.vending_machine.model.CashInventory;
import com.vending_machine.vending_machine.model.Product;
import com.vending_machine.vending_machine.model.Transaction;
import com.vending_machine.vending_machine.repository.CashInventoryRepository;
import com.vending_machine.vending_machine.repository.TransactionRepository;
import com.vending_machine.vending_machine.util.TransactionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final ProductService productService;

    private final TransactionRepository transactionRepository;

    private final CashInventoryRepository cashInventoryRepository;

    public PurchaseServiceImpl(ProductService productService, TransactionRepository transactionRepository, CashInventoryRepository cashInventoryRepository) {
        this.productService = productService;
        this.transactionRepository = transactionRepository;
        this.cashInventoryRepository = cashInventoryRepository;
    }

    @Override
    @Transactional
    public Transaction purchase(PurchaseRequest purchaseRequest) {

        this.validateRequest(purchaseRequest);

        Map<Integer, Integer> integerIntegerMap = this.changeValidations(purchaseRequest.getPaidAmount());

        this.saveProducts(purchaseRequest.getItems());

        return this.transactionRepository.save(TransactionFactory.from(purchaseRequest,integerIntegerMap));
    }

    private void validateRequest(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.getPaidAmount()<=0 || purchaseRequest.getTotalAmount()<=0) {
            throw new IllegalArgumentException("PaidAmount or TotalAmount cannot be less or equal to zero");
        }

        if (purchaseRequest.getPaidAmount().compareTo(purchaseRequest.getTotalAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }



        int totalPrice = purchaseRequest.getItems().stream().mapToInt(it -> it.getPrice()
                 * it.getQuantity()).sum();

        if (purchaseRequest.getTotalAmount().compareTo(totalPrice) < 0) {
            throw new IllegalArgumentException("Items prices not match total price");
        }

    }

    private void saveProducts(List<ItemDTO> items){
        log.info("items : {}", items.get(0).getId());

        List<Product> products = this.productService.getProducts();

        log.info("productsByIds : {}", products);

        List<Product> updateProducts = items.stream().map(
                itemDTO -> products.stream().filter( product ->
                        itemDTO.getId().equals(product.getId())
                                && product.getQuantity() >= itemDTO.getQuantity()
                ).findFirst().map(
                        p-> {
                            p.setQuantity(p.getQuantity() - itemDTO.getQuantity());
                            return p;
                        }
                ).orElseThrow(() -> new IllegalArgumentException("Product with Quantity not found"))
        ).collect(Collectors.toList());

        log.info("products after : {}", updateProducts);

        this.productService.updateProducts(updateProducts);
    }

    private Map<Integer,Integer> changeValidations(Integer changeAmount){
        Map<Integer,Integer> changeMap = new LinkedHashMap<>();

        int remainingAmount = changeAmount;

        List<CashInventory> inventories = this.cashInventoryRepository.findAll()
                .stream().sorted(Comparator.comparingInt(CashInventory::getCashValue).reversed()).collect(Collectors.toList());

        for(CashInventory cashInventory: inventories){
            if(remainingAmount <=0) break;

            if(remainingAmount >= cashInventory.getCashValue() && cashInventory.getQuantity()>0 ){
                int countForChange = Math.min(remainingAmount/cashInventory.getCashValue(),
                        cashInventory.getQuantity());

                if(countForChange>0){
                    remainingAmount-= countForChange*cashInventory.getCashValue();
                    cashInventory.setQuantity(cashInventory.getQuantity() - countForChange);
                    changeMap.put(cashInventory.getCashValue(),cashInventory.getQuantity());
                }

            }

        }

        if(remainingAmount > 0){
            throw new IllegalArgumentException("Cannot provide a change given amount");
        }

        this.cashInventoryRepository.saveAll(inventories);

        return changeMap;

    }
}
