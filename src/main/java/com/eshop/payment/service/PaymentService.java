package com.eshop.payment.service;

import com.eshop.payment.dto.PaymentDTO;
import com.eshop.payment.entity.Payment;
import com.eshop.payment.enums.PaymentStatus;
import com.eshop.payment.repository.PaymentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
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
     * @param orderId
     * @return
     * @throws NotFoundException
     */
    public List<PaymentDTO> getPaymentStatus(Long orderId) throws NotFoundException {
        List<Payment> payment = paymentRepository.findByOrderIdOrderByCreatedAtDesc(orderId);
        if (payment != null && !payment.isEmpty()){
            return payment.stream().map(e -> convertToDto(e)).collect(Collectors.toList());
        }else{
            throw new NotFoundException("Order Status not found "+orderId);
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
       System.out.println(paymentDto.getOrderId());
        PaymentDTO returnRefund = null;
        List<Payment> status = paymentRepository.findByOrderIdOrderByCreatedAtDesc(paymentDto.getOrderId());
        if(!status.isEmpty()){
            if(status.get(0).getStatus().equalsIgnoreCase(String.valueOf(PaymentStatus.Processed)) ){

                Payment refundPayment = convertToEntity(paymentDto);
                Payment savePayment = paymentRepository.save(refundPayment);
                returnRefund = convertToDto(savePayment);
                return returnRefund;

            }else{
                return null;
            }
        }else {
                 throw new NotFoundException("order id does not exist ");
        }

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
