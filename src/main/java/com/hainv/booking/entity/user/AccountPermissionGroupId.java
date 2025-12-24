package com.hainv.booking.entity.user;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPermissionGroupId implements Serializable {

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "group_id")
    private String groupId;
}
