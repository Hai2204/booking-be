package com.hainv.booking.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "account_permission_group")
public class AccountPermissionGroup {

    @EmbeddedId
    private AccountPermissionGroupId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private PermissionGroup group;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
}

