package com.vending_machine.vending_machine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Embeddable
public class Item {
    private final UUID itemId = UUID.randomUUID();
    private UUID productId;
    private Integer price;
    private Integer quantity;

    public Item(UUID productId, Integer price, Integer quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

}
