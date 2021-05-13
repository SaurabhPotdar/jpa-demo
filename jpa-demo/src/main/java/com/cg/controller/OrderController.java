package com.cg.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.Customer;
import com.cg.dto.Order;
import com.cg.repository.CustomerRepository;
import com.cg.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		return new ResponseEntity<>(orderRepository.findById(id).orElse(null), HttpStatus.OK);
	}
	
	@GetMapping(value="")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping(value="")
	public ResponseEntity<?> addCourse(@RequestBody Order order, @RequestParam("customerId") int customerId) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		order.setCustomer(customer);
		Set<Order> orders = customer.getOrders();
		orders.add(order);
		customer.setOrders(orders);
		return new ResponseEntity<>(customerRepository.save(customer),HttpStatus.OK);
	}

}
