package com.hainv.booking.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "permission_group")
public class PermissionGroup {

    @Id
    @Column(name = "code_id", length = 50)
    private String codeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "application", nullable = false)
    private String application;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}

