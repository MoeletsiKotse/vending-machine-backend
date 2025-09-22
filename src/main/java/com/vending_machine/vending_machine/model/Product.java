package com.vending_machine.vending_machine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @Column(updatable = false, nullable = false, columnDefinition="UUID")
    private UUID id = UUID.randomUUID();
    private String name;
    private Integer quantity;
    @Version
    private Long version;
    private Integer price;

    public Product(String productName, int quantity, Integer price) {
        this.name = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
