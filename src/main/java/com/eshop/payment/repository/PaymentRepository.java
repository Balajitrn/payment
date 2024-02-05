package com.eshop.payment.repository;

import com.eshop.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByOrderIdOrderByCreatedAtDesc(Long orderId);
}
