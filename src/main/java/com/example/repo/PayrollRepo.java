package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Payroll;
import com.example.model.User;

import java.util.List;


@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Long>{
	List<Payroll> findByUser(User user);
}
