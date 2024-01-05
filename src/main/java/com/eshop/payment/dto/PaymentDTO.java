package com.eshop.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {

    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private String status;
    private String paymentMethod;

}
