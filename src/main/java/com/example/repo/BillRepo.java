package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Bill;
import com.example.model.Customer;

import java.util.List;


@Repository
public interface BillRepo extends JpaRepository<Bill, Long>{
	List<Bill> findByCustomer(Customer customer);
}
