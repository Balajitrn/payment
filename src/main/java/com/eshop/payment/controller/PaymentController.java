package com.eshop.payment.controller;

import com.eshop.payment.dto.PaymentDTO;
import com.eshop.payment.service.PaymentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Payment module
 */

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){

        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public PaymentDTO processPayment(@RequestBody PaymentDTO paymentDto) throws NotFoundException {
        return paymentService.processPayment(paymentDto);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<PaymentDTO>> paymentStatus(@PathVariable Long orderId) throws NotFoundException {
       List<PaymentDTO> orderStatus = paymentService.getPaymentStatus(orderId);
        return ResponseEntity.ok(orderStatus);
    }

    @PostMapping("/{orderId}/refund")
    public PaymentDTO paymentRefund(@RequestBody PaymentDTO paymentDto) throws NotFoundException {
        return paymentService.paymentRefund(paymentDto);

    }

}
