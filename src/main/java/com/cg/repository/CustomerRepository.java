package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
