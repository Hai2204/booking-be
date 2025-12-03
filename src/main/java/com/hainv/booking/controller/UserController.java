package com.hainv.booking.controller;

import com.hainv.booking.entity.booking.Booking;
import com.hainv.booking.entity.user.User;
import com.hainv.booking.entity.user.UserRequest;
import com.hainv.booking.entity.user.UserSummary;
import com.hainv.booking.repository.booking.BookingRepository;
import com.hainv.booking.repository.booking.CustomerRepository;
import com.hainv.booking.repository.user.RoleRepository;
import com.hainv.booking.repository.user.UserRepository;
import com.hainv.booking.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwt;

    @PostMapping
    public Optional<UserSummary> create(@RequestBody UserRequest userRequest) throws Exception {
        Optional<User> existingUser = userRepository.findByUsernameAll(userRequest.getUsername());
        if (existingUser.isPresent()) {
            throw new Exception("Tên đăng nhập đã tồn tại");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFullName(userRequest.getFullName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userRequest.getPassword()));

        user.setRoles(roleRepository.findAllById(userRequest.getRoleIds()));

        userRepository.save(user);

        return userRepository.findProjectedByUsername(user.getUsername());
    }

    @DeleteMapping("/{userName}")
    public void deleteUser(@PathVariable String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    @PostMapping("/role")
    public List<User> setRole(@RequestBody User user) {
        return userRepository.findAll();
    }

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/bookings")
    public List<Booking> getBookings() {
        return bookingRepository.findAll(); // → truy cập DB booking_hotel
    }
}
