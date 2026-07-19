package com.example.DropBGBackend.repository;

import com.example.DropBGBackend.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    Optional<OrderEntity> findByOrderId(String orderId);
}
