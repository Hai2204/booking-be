package com.hainv.booking.service.impl;

import com.hainv.booking.entity.user.User;
import com.hainv.booking.repository.user.UserRepository;
import com.hainv.booking.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsernameAll(username).orElseThrow(() -> new UsernameNotFoundException("Not found by user"));
    }
}
