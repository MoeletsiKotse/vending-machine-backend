package com.vending_machine.vending_machine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashInventory {

    @Id
    private Integer cashValue;

    private Integer quantity;

}
