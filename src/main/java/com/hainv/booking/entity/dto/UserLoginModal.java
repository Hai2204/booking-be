package com.hainv.booking.entity.dto;

import lombok.Data;
@Data
public class UserLoginModal {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private Integer age;
    private String nationalId;
    private String roleName;
}
