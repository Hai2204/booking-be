package com.hainv.booking.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "feature")
@Data
public class Feature {

    @Id
    @Column(name = "code_id", length = 50)
    private String codeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
