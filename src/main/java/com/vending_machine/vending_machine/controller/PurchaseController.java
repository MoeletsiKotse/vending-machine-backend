package com.vending_machine.vending_machine.controller;

import com.vending_machine.vending_machine.dto.PurchaseRequest;
import com.vending_machine.vending_machine.model.Transaction;
import com.vending_machine.vending_machine.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/purchase")
@CrossOrigin
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping()
    public ResponseEntity<Transaction> purchase(@Valid @RequestBody PurchaseRequest purchaseRequest){
        return ResponseEntity.ok(this.purchaseService.purchase(purchaseRequest));
    }

    @GetMapping("/test")
    private ResponseEntity<String> test(){
        return ResponseEntity.ok("ping pong");
    }

}
