package com.hainv.booking.entity.user;

import lombok.Data;
import java.util.Set;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String fullName;
    private Set<Long> roleIds;
    private String email;
    private String phone;
}
