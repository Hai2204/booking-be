package com.hainv.booking.entity.dto;

import lombok.Data;

@Data
public class RoomRequest {
    private String name;
    private String roomCategory;
    private Integer price;
    private Integer typeRoom;
    private String description;
    private String amenities;

    private String roomCode;

    private Long accommodationId;
    private String policy;
}
