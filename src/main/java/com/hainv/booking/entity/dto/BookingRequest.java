package com.hainv.booking.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Long roomId;
    private Long customerId;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private String note;

    private PaymentRequest payment; // nested payment info
}
