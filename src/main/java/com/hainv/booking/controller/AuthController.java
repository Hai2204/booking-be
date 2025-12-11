package com.hainv.booking.controller;

import com.hainv.booking.entity.dto.UserLoginModal;
import com.hainv.booking.entity.user.User;
import com.hainv.booking.repository.user.UserRepository;
import com.hainv.booking.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwt;
    @PostMapping("/login")
    public Map<String, Object> loginByUser(@RequestBody User user) throws Exception {
        User u = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found User"));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(user.getPassword(), u.getPassword())) {
            throw new Exception("Incorrect username/password");
        }
        UserLoginModal userModal = new UserLoginModal();
        userModal.setUsername(u.getUsername());
        userModal.setFullName(u.getFullName());
        userModal.setRoleName(u.getRole().getRoleCode());

        Map<String, Object> result = new HashMap<>();
        result.put("user", userModal);
        result.put("token", jwt.generateToken(user.getUsername()));
        return result;

    }
}
