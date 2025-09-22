package com.vending_machine.vending_machine.service;

import com.vending_machine.vending_machine.dto.PurchaseRequest;
import com.vending_machine.vending_machine.model.Transaction;

public interface PurchaseService {

    Transaction purchase(PurchaseRequest purchaseRequest);
}
