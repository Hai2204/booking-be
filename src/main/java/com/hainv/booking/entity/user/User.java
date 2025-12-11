package com.hainv.booking.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "users")
@Data
@SQLRestriction(" IS_ACTIVE = 1")
@SQLDelete(sql = " UPDATE users SET IS_ACTIVE = 0 WHERE id = ? ")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "role_id")   // tạo cột ROLE_ID trong bảng USERS
    private Role role;

    @Column(name = "is_active", nullable = false)
    private int isActive = 1;
}
