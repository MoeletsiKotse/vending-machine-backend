package com.vending_machine.vending_machine.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ItemDTO {
    private final Integer price;
    private final UUID id;
    private final int quantity;
}
