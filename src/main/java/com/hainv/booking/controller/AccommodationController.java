package com.hainv.booking.controller;

import com.hainv.booking.entity.booking.Accommodation;
import com.hainv.booking.entity.booking.Partner;
import com.hainv.booking.entity.dto.AccommodationRequest;
import com.hainv.booking.repository.booking.AccommodationRepository;
import com.hainv.booking.repository.booking.PartnerRepository;
import com.hainv.booking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AccommodationController {

    private final PartnerRepository partnerRepository;
    private final AccommodationRepository accommodationRepository;

    @GetMapping("/accommodations")
    public ApiResponse<List<Accommodation>> listAccommodations(@RequestParam(name = "id", required = false) Long id) throws Exception {
        if (id != null){
            return ApiResponse.success(accommodationRepository.findAllById(Collections.singleton(id)));
        }
        return ApiResponse.success(accommodationRepository.findAll());
    }


    @PostMapping("/accommodation")
    public ApiResponse<Accommodation> createRoom(@RequestBody AccommodationRequest request) throws Exception {
        Partner partner = partnerRepository.findById(request.getPartnerId())
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        Accommodation accommodation = new Accommodation();
        accommodation.setPartner(partner);
        accommodation.setName(request.getName());
        accommodation.setAccommodationType(request.getAccommodationType());
        accommodation.setDescription(request.getDescription());
        accommodation.setAddress(request.getAddress());

        return ApiResponse.success(accommodationRepository.save(accommodation));
    }
}
