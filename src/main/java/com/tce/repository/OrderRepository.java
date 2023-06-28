package com.tce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tce.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
