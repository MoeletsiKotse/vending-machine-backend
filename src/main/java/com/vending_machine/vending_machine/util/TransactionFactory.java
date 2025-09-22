package com.vending_machine.vending_machine.util;

import com.vending_machine.vending_machine.dto.ItemDTO;
import com.vending_machine.vending_machine.dto.PurchaseRequest;
import com.vending_machine.vending_machine.model.Item;
import com.vending_machine.vending_machine.model.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionFactory {

    public static Transaction from(PurchaseRequest purchaseRequest, Map<Integer,Integer> change){
        return new Transaction(
                purchaseRequest.getTotalAmount(),purchaseRequest.getPaidAmount(), from(purchaseRequest.getItems()), change);
    }

    private static List<Item> from(List<ItemDTO> items){
        return items.stream().map(
                itemDTO -> new Item(itemDTO.getId(),itemDTO.getPrice(),itemDTO.getQuantity())
        ).collect(Collectors.toList());
    }

}
