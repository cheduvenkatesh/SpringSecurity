package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Payroll;
import com.example.repo.PayrollRepo;
import com.example.repo.UserRepo;

@Service
public class PayrollService {
	
	@Autowired
	private PayrollRepo payrollRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	public List<Payroll> getAll() {
		return payrollRepo.findAll(); 
	}
	public Optional<Payroll> getPayrollById(Long id) {
		return payrollRepo.findById(id); 
	}
	
	public List<Payroll> findAllPayrollsByUserId(Long id) {
		return payrollRepo.findByUser(userRepo.findById(id).get());
	}
	
	public Payroll save(Payroll payroll) {
		return payrollRepo.save(payroll);
	}
	public Payroll savePayroll(Payroll payroll,Long id) {
		payroll.setUser(userRepo.findById(id).get());
		return payrollRepo.save(payroll);
	}
	
	public void deletePayroll(Long id) {
		 payrollRepo.deleteById(id);
	}
	
	public Optional<Payroll> findById(Long id) {
		return payrollRepo.findById(id);
	}
}
