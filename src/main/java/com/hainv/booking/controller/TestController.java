package com.hainv.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/api")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String sayHI() throws Exception {
        return "result";
    }
    @GetMapping("/api1/{pass}")
    public String sayHI1(@PathVariable String pass) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pass);
    }
}
