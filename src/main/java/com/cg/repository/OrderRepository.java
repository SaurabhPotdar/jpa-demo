package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.dto.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
