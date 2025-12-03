package com.hainv.booking.entity.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Integer amount;
    private String paymentType;
    private String paymentMethod;
    private String transactionCode;
}