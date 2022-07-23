package com.tce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tce.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
