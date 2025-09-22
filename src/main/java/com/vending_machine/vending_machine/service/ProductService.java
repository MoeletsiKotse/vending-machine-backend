package com.vending_machine.vending_machine.service;

import com.vending_machine.vending_machine.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getProducts();

    List<Product> updateProducts(List<Product> products);

    List<Product> getProductsByIds(List<UUID> ids);

    List<Product> getAllProducts();
}
