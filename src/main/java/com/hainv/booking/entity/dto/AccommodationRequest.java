package com.hainv.booking.entity.dto;

import lombok.Data;

@Data
public class AccommodationRequest {
    private Long partnerId;
    private String name;
    private String accommodationType;
    private String description;
    private String address;
}

