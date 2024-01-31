package com.eshop.payment.service;

import com.eshop.payment.dto.PaymentDTO;
import com.eshop.payment.entity.Payment;
import com.eshop.payment.repository.PaymentRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProcessPayment() throws NotFoundException {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setPaymentMethod("credit card");
        paymentDTO.setStatus("processing");
        paymentDTO.setAmount(BigDecimal.valueOf(100));
        paymentDTO.setOrderId(1234L);

        Payment paymentEntity = new Payment();
        paymentEntity.setId(1L);
        paymentEntity.setPaymentMethod("credit card");
        paymentEntity.setStatus("processing");
        paymentEntity.setAmount(BigDecimal.valueOf(100));
        paymentEntity.setOrderId(1234L);

        Mockito.when(paymentRepository.save(Mockito.any(Payment.class)))
                .thenReturn(paymentEntity);

        PaymentDTO processPaymentDto = paymentService.processPayment(paymentDTO);

       // verify(paymentRepository).save(paymentEntity);

        assertEquals(processPaymentDto.getId(),paymentDTO.getId());
        assertEquals(processPaymentDto.getAmount(),paymentDTO.getAmount());
    }

   /* @Test
    void TestGetPaymentStatus() throws NotFoundException {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setOrderId(1234L);
        payment.setAmount(BigDecimal.valueOf(1000));
        payment.setStatus("processing");

        Mockito.when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        PaymentDTO paymentDTO = paymentService.getPaymentStatus(1L);

        verify(paymentRepository).findById(1L);

        assertEquals(paymentDTO.getId(),payment.getId());
        assertEquals(paymentDTO.getOrderId(),payment.getOrderId());


    } */

}
