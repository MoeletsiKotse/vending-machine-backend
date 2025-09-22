package com.vending_machine.vending_machine.repository;

import com.vending_machine.vending_machine.model.CashInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashInventoryRepository extends JpaRepository<CashInventory,Integer> {
}
