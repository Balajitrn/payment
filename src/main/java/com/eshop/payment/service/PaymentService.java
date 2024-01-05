package com.eshop.payment.service;

import com.eshop.payment.dto.PaymentDTO;
import com.eshop.payment.entity.Payment;
import com.eshop.payment.repository.PaymentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import javax.transaction.Transactional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository)

    {
        this.paymentRepository = paymentRepository;
    }

    /**
     * method to save the payment
     * @param paymentDto
     * @return
     * @throws NotFoundException
     */
    @Transactional
    public PaymentDTO processPayment(PaymentDTO paymentDto) throws NotFoundException {

        Payment payment = convertToEntity(paymentDto);
        Payment savePayment = paymentRepository.save(payment);
        return convertToDto(savePayment);
    }

    /**
     * method to return status of a payment
     * @param paymentId
     * @return
     * @throws NotFoundException
     */
    public PaymentDTO getPaymentStatus(Long paymentId) throws NotFoundException {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isPresent()){
            return convertToDto(payment.get());
        }else{
            throw new NotFoundException("Payment Status not found "+paymentId);
        }

    }

    /**
     * method to add refund in payment
     * @param paymentDto
     * @return
     * @throws NotFoundException
     */
    @Transactional
    public PaymentDTO paymentRefund(PaymentDTO paymentDto) throws NotFoundException {
        Payment refundPayment = convertToEntity(paymentDto);
        Payment savePayment = paymentRepository.save(refundPayment);
        return convertToDto(savePayment);
    }

    /**
     * method to convert the entity to DTO
     * @param payment
     * @return
     */
    private PaymentDTO convertToDto(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus());
    }

    /**
     * method is to set the value from dto to entity
     * @param paymentDTO
     * @return
     * @throws NotFoundException
     */
    private Payment convertToEntity(PaymentDTO paymentDTO) throws NotFoundException{
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setOrderId(paymentDTO.getOrderId());
        payment.setStatus(paymentDTO.getStatus());

        /*if(paymentDTO.getOrderId()!= null){

        }*/

        return payment;
    }

}
