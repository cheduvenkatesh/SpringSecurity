package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Payroll;
import com.example.service.PayrollService;

@RestController
@RequestMapping("/payroll")
public class PayrollController {
	
	@Autowired
	private PayrollService payrollService;
	
	@GetMapping
	public List<Payroll> getAll(){
		return payrollService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Payroll> getPayrollById(@PathVariable Long id){
		return payrollService.getPayrollById(id)
				.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/user/{id}")
    public List<Payroll> getAllPayrollsOfUser(@PathVariable Long id){
    	return payrollService.findAllPayrollsByUserId(id);
    }
	
	@PostMapping("/{id}")
	public Payroll save(@RequestBody Payroll payroll,@PathVariable Long id) {
		return payrollService.savePayroll(payroll,id);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Payroll> updatePayroll(@PathVariable Long id, @RequestBody Payroll payroll) {
        return payrollService.findById(id)
                .map(existingPayroll -> {
                	existingPayroll.setPaymentDate(payroll.getPaymentDate());
                	existingPayroll.setAmount(payroll.getAmount());
                	existingPayroll.setStatus(payroll.getStatus());
                	existingPayroll.setUser(payroll.getUser());
                    return ResponseEntity.ok(payrollService.save(existingPayroll));
                })
                .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        if (payrollService.findById(id).isPresent()) {
        	payrollService.deletePayroll(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
