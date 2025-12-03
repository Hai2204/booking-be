package com.hainv.booking.entity.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    private Integer amount;

    @Column(name = "payment_type", length = 10)
    private String paymentType; // deposit / rental / refund

    @Column(name = "payment_method", length = 10)
    private String paymentMethod = "cash"; // cash / transfer / credit / vnpay / momo

    @Column(name = "payment_time")
    private LocalDateTime paymentTime = LocalDateTime.now();

    @Column
    private String note;

    @Column(name = "transaction_code", length = 50, nullable = false)
    private String transactionCode;
}
