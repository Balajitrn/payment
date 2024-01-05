package com.eshop.payment.controller;

import com.eshop.payment.dto.PaymentDTO;
import com.eshop.payment.service.PaymentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> paymentStatus(@PathVariable Long paymentId) throws NotFoundException {
        PaymentDTO paymentDTO = paymentService.getPaymentStatus(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }

    @PostMapping("/{paymentId}/refund")
    public PaymentDTO paymentRefund(@RequestBody PaymentDTO paymentDto) throws NotFoundException {
        return paymentService.paymentRefund(paymentDto);

    }

}
