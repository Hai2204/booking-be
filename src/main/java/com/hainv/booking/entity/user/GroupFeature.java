package com.hainv.booking.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_feature")
@Data
public class GroupFeature {

    @EmbeddedId
    private GroupFeatureId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private PermissionGroup group;

    @ManyToOne
    @MapsId("featureId")
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
}

