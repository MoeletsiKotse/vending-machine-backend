package com.vending_machine.vending_machine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Transaction {
    @Id
    private UUID id = UUID.randomUUID();
    private Integer totalAmount;
    private Integer changeAmount;
    private Integer paidAmount;
    private Integer totalItems;
    private OffsetDateTime timeStamp;
    @Version
    private Long version;
    @ElementCollection
    private List<Item> items;

    @ElementCollection
    private Map<Integer,Integer> change;

    public Transaction(Integer totalAmount, Integer paidAmount, List<Item> items, Map<Integer, Integer> change) {
        this.totalAmount = totalAmount;
        this.timeStamp = OffsetDateTime.now();
        this.change = change;
        this.changeAmount = paidAmount - totalAmount;
        this.paidAmount = paidAmount;
        this.totalItems = Objects.nonNull(items) ? items.stream().mapToInt(Item::getQuantity).sum() : 0;
        this.items = items;
    }
}
