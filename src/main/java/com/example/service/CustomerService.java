package com.example.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repo.CustomerRepo;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepository;

	 public List<Customer> findAllCustomers() {
	     return customerRepository.findAll();
	 }

     public Optional<Customer> findCustomerById(Long id) {
	     return customerRepository.findById(id);
     }

	 public Customer saveCustomer(Customer customer) {
	     return customerRepository.save(customer);
	 }

	 public void deleteCustomer(Long id) {
	     customerRepository.deleteById(id);
	 }
}
