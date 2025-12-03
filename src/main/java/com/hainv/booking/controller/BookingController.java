package com.hainv.booking.controller;

import com.hainv.booking.entity.booking.Booking;
import com.hainv.booking.entity.booking.Customer;
import com.hainv.booking.entity.booking.Room;
import com.hainv.booking.entity.dto.BookingRequest;
import com.hainv.booking.repository.booking.BookingRepository;
import com.hainv.booking.repository.booking.CustomerRepository;
import com.hainv.booking.repository.booking.RoomRepository;
import com.hainv.booking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;

    @PostMapping()
    public ApiResponse<Booking> booking(@RequestBody BookingRequest request) throws Exception {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setCustomer(customer);
        booking.setTimeIn(request.getTimeIn());
        booking.setTimeOut(request.getTimeOut());
        booking.setNote(request.getNote());

        booking = bookingRepository.save(booking);
        return ApiResponse.success(booking);
    }

    @PostMapping("/payment")
    public ApiResponse<Booking> paymentBooking(@RequestBody BookingRequest request) throws Exception {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setCustomer(customer);
        booking.setTimeIn(request.getTimeIn());
        booking.setTimeOut(request.getTimeOut());
        booking.setNote(request.getNote());

        booking = bookingRepository.save(booking);
        return ApiResponse.success(booking);
    }
}
