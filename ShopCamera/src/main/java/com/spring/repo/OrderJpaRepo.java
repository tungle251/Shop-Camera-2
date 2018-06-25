package com.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.domain.Order;

@Repository
public interface OrderJpaRepo extends JpaRepository<Order, Integer> {

}
