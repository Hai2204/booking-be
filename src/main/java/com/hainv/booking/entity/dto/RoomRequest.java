package com.hainv.booking.entity.dto;

import lombok.Data;

@Data
public class RoomRequest {
    private Long accommodationId;
    private String name;
    private String typeRoom;
    private Integer price;
}
