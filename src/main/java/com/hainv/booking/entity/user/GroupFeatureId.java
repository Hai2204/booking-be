package com.hainv.booking.entity.user;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupFeatureId implements Serializable {

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "feature_id")
    private String featureId;
}
