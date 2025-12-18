package com.hainv.booking.controller;

import com.hainv.booking.entity.booking.Booking;
import com.hainv.booking.entity.booking.Customer;
import com.hainv.booking.entity.booking.Room;
import com.hainv.booking.entity.dto.UserCustomerModal;
import com.hainv.booking.entity.user.Role;
import com.hainv.booking.entity.user.User;
import com.hainv.booking.entity.user.UserRequest;
import com.hainv.booking.entity.user.UserSummary;
import com.hainv.booking.repository.booking.BookingRepository;
import com.hainv.booking.repository.booking.CustomerRepository;
import com.hainv.booking.repository.user.RoleRepository;
import com.hainv.booking.repository.user.UserRepository;
import com.hainv.booking.security.JwtTokenProvider;
import com.hainv.booking.service.IUserService;
import com.hainv.booking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwt;
    private final IUserService userService;

    @PostMapping("/user")
    public ApiResponse<UserSummary> create(@RequestBody UserRequest userRequest) throws Exception {
        Optional<User> existingUser = userRepository.findByUsernameAll(userRequest.getUsername());
        if (existingUser.isPresent()) {
            throw new Exception("Tên đăng nhập đã tồn tại");
        }
        Optional<Customer> customer = customerRepository.findByNationalId(userRequest.getNationalId());
        if (customer.isPresent()){
            throw new Exception("Khách hàng đã tồn tại");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFullName(userRequest.getFullName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userRequest.getPassword()));
        Role rol = roleRepository.findByRoleCode(userRequest.getRoleName()).orElseThrow(() -> new Exception("Role không tồn tại"));
        user.setRole(rol);
        userRepository.save(user);

        Customer cus = new Customer();
        cus.setAge(userRequest.getAge());
        cus.setName(userRequest.getFullName());
        cus.setEmail(userRequest.getEmail());
        cus.setPhoneNumber(userRequest.getPhone());
        cus.setNationalId(userRequest.getNationalId());
        cus.setUserName(userRequest.getUsername());
        customerRepository.save(cus);

        return ApiResponse.success(userRepository.findProjectedByUsername(user.getUsername()).get());
    }

    @DeleteMapping("/user/{userName}")
    public void deleteUser(@PathVariable String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    @PostMapping("/api/users/role")
    public List<User> setRole(@RequestBody User user) {
        return userRepository.findAll();
    }

    @GetMapping("/api/users")
    public ApiResponse<List<UserCustomerModal>> findAll() {
        return ApiResponse.success(userService.findAllUsers());
    }

    @GetMapping("/api/user/bookings")
    public List<Booking> getBookings() {
        return bookingRepository.findAll(); // → truy cập DB booking_hotel
    }
}
