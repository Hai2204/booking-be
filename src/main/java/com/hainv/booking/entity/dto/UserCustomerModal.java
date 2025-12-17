package com.hainv.booking.entity.dto;

import lombok.Data;

@Data
public class UserCustomerModal {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Integer age;
    private String nationalId;
    private String roleName;

    public UserCustomerModal(
            String username,
            String fullName,
            String email,
            String phone,
            Integer age,
            String nationalId,
            String roleName
    ) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.nationalId = nationalId;
        this.roleName = roleName;
    }
}
