package com.tce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tce.dto.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
