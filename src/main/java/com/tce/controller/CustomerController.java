package com.tce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tce.dto.Customer;
import com.tce.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		return new ResponseEntity<Customer>(customerRepository.findById(id).orElse(null), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
		return new ResponseEntity<>(customerRepository.save(customer),HttpStatus.OK);
	}

}
