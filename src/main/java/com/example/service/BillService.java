package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Bill;
import com.example.model.Customer;
import com.example.repo.BillRepo;
import com.example.repo.CustomerRepo;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepo billRepository;
    
    @Autowired
    private CustomerRepo customerRepo;

    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> findBillById(Long id) {
        return billRepository.findById(id);
    }

    public Bill saveBill(Bill bill,Long id) {
    	Customer customer = customerRepo.findById(id).get();
    	bill.setCustomer(customer);
        return billRepository.save(bill);
    }
    public Bill save(Bill bill) {
    	return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
    
    public List<Bill> findAllBillsByCustomerId(Long id){
    	return billRepository.findByCustomer(customerRepo.findById(id).get());
    }
}

