package com.hainv.booking.service.impl;

import com.hainv.booking.entity.booking.Customer;
import com.hainv.booking.entity.dto.UserCustomerModal;
import com.hainv.booking.entity.user.User;
import com.hainv.booking.repository.booking.CustomerRepository;
import com.hainv.booking.repository.user.UserRepository;
import com.hainv.booking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsernameAll(username).orElseThrow(() -> new UsernameNotFoundException("Not found by user"));
    }

    @Override
    public List<UserCustomerModal> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<Customer> customers = customerRepository.findAll();

        Map<String, Customer> customerMap = customers.stream()
                .filter(c -> c.getUserName() != null)
                .collect(Collectors.toMap(Customer::getUserName, c -> c));

        return users.stream()
                .map(u -> {
                    Customer c = customerMap.get(u.getUsername());
                    return new UserCustomerModal(
                            u.getUsername(),
                            u.getFullName(),
                            c != null ? c.getEmail() : null,
                            c != null ? c.getPhoneNumber() : null,
                            c != null ? c.getAge() : null,
                            c != null ? c.getNationalId() : null,
                            u.getRole().getRoleName()
                    );
                }).toList();
    }
}
