package com.vending_machine.vending_machine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PurchaseRequest {
    private final List<ItemDTO> items;
    private final Integer totalAmount;
    private final Integer paidAmount;
}
