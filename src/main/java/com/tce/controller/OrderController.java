package com.tce.controller;

import com.tce.dto.Customer;
import com.tce.dto.Order;
import com.tce.repository.CustomerRepository;
import com.tce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

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
	
	@GetMapping()
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> addCourse(@RequestBody Order order, @RequestParam("customerId") int customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if(optionalCustomer.isPresent()) {
			final Customer customer = optionalCustomer.get();
			order.setCustomer(customer);
			final Set<Order> orders = customer.getOrders();
			orders.add(order);
			customer.setOrders(orders);
			return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
		}
		return new ResponseEntity<>("Customer not found", HttpStatus.BAD_REQUEST);
	}

}
