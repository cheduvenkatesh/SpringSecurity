package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.Bill;
import com.example.service.BillService;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillsController {

    @Autowired
    private BillService billService;

    @GetMapping
    public List<Bill> getAllBills() {
        return billService.findAllBills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return billService.findBillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/customer/{id}")
    public List<Bill> getAllBillsOfCustomer(@PathVariable Long id){
    	return billService.findAllBillsByCustomerId(id);
    }

    @PostMapping("/{id}")
    public Bill createBill(@RequestBody Bill bill,@PathVariable Long id) {
        return billService.saveBill(bill,id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long id, @RequestBody Bill billDetails) {
        return billService.findBillById(id)
                .map(existingBill -> {
                    existingBill.setDescription(billDetails.getDescription());
                    existingBill.setAmount(billDetails.getAmount());
                    existingBill.setBillDate(billDetails.getBillDate());
                    existingBill.setCustomer(billDetails.getCustomer());
                    return ResponseEntity.ok(billService.save(existingBill));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
        if (billService.findBillById(id).isPresent()) {
            billService.deleteBill(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

