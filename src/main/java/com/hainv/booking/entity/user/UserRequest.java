package com.hainv.booking.entity.user;

import lombok.Data;
import java.util.Set;

@Data
public class UserRequest {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Integer age;
    private String nationalId;
    private String roleName;
    private String password;
}
