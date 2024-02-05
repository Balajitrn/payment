package com.eshop.payment.service;

import com.eshop.payment.enums.*;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    List<Payment> getPaymentList() {
        List<Payment> paymentList = new ArrayList<Payment>();
        Payment paymentEntity = new Payment();
        paymentEntity.setId(1L);
        paymentEntity.setPaymentMethod("credit card");
        paymentEntity.setStatus("processed");
        paymentEntity.setAmount(BigDecimal.valueOf(100));
        paymentEntity.setOrderId(1234L);

        paymentList.add(paymentEntity);

        return paymentList;
    }

    @Test
    void testProcessPayment() throws NotFoundException {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(1L);
        paymentDTO.setPaymentMethod("credit card");
        paymentDTO.setStatus("processed");
        paymentDTO.setAmount(BigDecimal.valueOf(100));
        paymentDTO.setOrderId(1234L);

        Payment paymentEntity = new Payment();
        paymentEntity.setId(1L);
        paymentEntity.setPaymentMethod("credit card");
        paymentEntity.setStatus("processed");
        paymentEntity.setAmount(BigDecimal.valueOf(100));
        paymentEntity.setOrderId(1234L);

        Mockito.when(paymentRepository.save(Mockito.any(Payment.class)))
                .thenReturn(paymentEntity);

        PaymentDTO processPaymentDto = paymentService.processPayment(paymentDTO);

       // verify(paymentRepository).save(paymentEntity);

        assertEquals(processPaymentDto.getId(),paymentDTO.getId());
        assertEquals(processPaymentDto.getAmount(),paymentDTO.getAmount());
    }

    @Test
    void TestGetPaymentStatus() throws NotFoundException {

        List<PaymentDTO> paymentDtoList = new ArrayList<>();

        Long orderId = Long.valueOf(1234);

       /* PaymentDTO paymentDto = new PaymentDTO();
        paymentDto.setId(1L);
        paymentDto.setOrderId(123L);
        paymentDto.setAmount(BigDecimal.valueOf(1000));
        paymentDto.setPaymentMethod("credit card");
        paymentDto.setStatus("Processed");

        PaymentDTO paymentDto1 = new PaymentDTO();
        paymentDto1.setId(2L);
        paymentDto1.setOrderId(1123L);
        paymentDto1.setAmount(BigDecimal.valueOf(1000));
        paymentDto1.setPaymentMethod("credit card");
        paymentDto1.setStatus("Refunded");

        paymentDtoList.add(paymentDto);
        paymentDtoList.add(paymentDto1);*/

     /*   when(paymentRepository.findByOrderIdOrderByCreatedAtDesc(orderId))
                .thenReturn(Stream.of(new Payment(1,123,3456,"Processed","credit card")).collect(Collectors.toList()));*/
    /*   Mockito.when(paymentRepository.findByOrderIdOrderByCreatedAtDesc(orderId))
                .thenReturn(Stream.of(payment).collect(Collectors.toList()));

        assertEquals(1,paymentService.getPaymentStatus(orderId).size()); */
        System.out.println(orderId);

       Mockito.when(paymentRepository.findByOrderIdOrderByCreatedAtDesc(1234L))
               .thenReturn(getPaymentList());
        Mockito.when(paymentRepository.findByOrderIdOrderByCreatedAtDesc(-1L))
                .thenReturn(null);

       System.out.println(paymentService.getPaymentStatus(orderId));

        assertEquals(1,paymentService.getPaymentStatus(orderId).size());
        assertThrows(NotFoundException.class, () -> paymentService.getPaymentStatus(-1L));

       /* Payment payment = new Payment();
        payment.setId(1L);
        payment.setOrderId(123L);
        payment.setAmount(BigDecimal.valueOf(1000));
        payment.setStatus("Processed");

        Mockito.when(paymentRepository.findByOrderIdOrderByCreatedAtDesc(123L))
                .thenReturn((List<Payment>) payment);

        List<PaymentDTO> paymentDTO = paymentService.getPaymentStatus(123L);

        verify(paymentRepository).findByOrderIdOrderByCreatedAtDesc(123L);

        assertEquals(paymentDTO.get(0),payment.getId());
        assertEquals(paymentDTO.get(1),payment.getOrderId()); */


    }

}
