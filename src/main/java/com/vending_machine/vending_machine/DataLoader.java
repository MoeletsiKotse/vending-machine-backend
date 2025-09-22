package com.vending_machine.vending_machine;

import com.vending_machine.vending_machine.model.CashInventory;
import com.vending_machine.vending_machine.model.Product;
import com.vending_machine.vending_machine.repository.CashInventoryRepository;
import com.vending_machine.vending_machine.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final CashInventoryRepository cashInventoryRepository;

    private final ProductRepository productRepository;

    public DataLoader(CashInventoryRepository cashInventoryRepository, ProductRepository productRepository) {
        this.cashInventoryRepository = cashInventoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Coke", 10, 15));
        products.add(new Product("KiKat", 20, 25));
        products.add(new Product("BarOne", 30, 20));
        products.add(new Product("Fanta", 25, 15));
        products.add(new Product("Pepsi", 15, 18));

        this.productRepository.saveAll(products);

        List<CashInventory> cashInventories = new ArrayList<>();
        cashInventories.add(new CashInventory(5, 10));
        cashInventories.add(new CashInventory(10, 20));
        cashInventories.add(new CashInventory(20, 25));
        cashInventories.add(new CashInventory(50, 15));
        cashInventories.add(new CashInventory(100, 5));

        this.cashInventoryRepository.saveAll(cashInventories);

        log.info("data loaded.....");
    }
}
