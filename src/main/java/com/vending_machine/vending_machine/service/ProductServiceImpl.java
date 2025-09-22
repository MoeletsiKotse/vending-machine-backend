package com.vending_machine.vending_machine.service;

import com.vending_machine.vending_machine.model.Product;
import com.vending_machine.vending_machine.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> updateProducts(List<Product> products) {
        return this.productRepository.saveAll(products);
    }

    @Override
    public List<Product> getProductsByIds(List<UUID> ids) {
        return this.productRepository.findAllById(ids);
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
