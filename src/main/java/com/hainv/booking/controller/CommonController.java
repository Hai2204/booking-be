package com.hainv.booking.controller;

import com.hainv.booking.entity.booking.Accommodation;
import com.hainv.booking.entity.booking.Customer;
import com.hainv.booking.entity.booking.Partner;
import com.hainv.booking.entity.booking.Room;
import com.hainv.booking.entity.dto.AccommodationRequest;
import com.hainv.booking.entity.dto.RoomRequest;
import com.hainv.booking.repository.booking.AccommodationRepository;
import com.hainv.booking.repository.booking.CustomerRepository;
import com.hainv.booking.repository.booking.PartnerRepository;
import com.hainv.booking.repository.booking.RoomRepository;
import com.hainv.booking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CommonController {

    private final PartnerRepository partnerRepository;
    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;
    private final CustomerRepository customerRepository;

    @PostMapping("/partner")
    public ApiResponse<Partner> createPartner(@RequestBody Partner partner) throws Exception {
        return ApiResponse.success(partnerRepository.save(partner));
    }
    @GetMapping("/partner")
    public ApiResponse<List<Partner>> listPartners(@RequestParam(name = "Id", required = false) Long id) throws Exception {
        if (id != null){
            return ApiResponse.success(partnerRepository.findAllById(Collections.singleton(id)));
        }
        return ApiResponse.success(partnerRepository.findAll());
    }



    @PostMapping("/room")
    public ApiResponse<Room> createRoom(@RequestBody RoomRequest request) {
        Accommodation accommodation = accommodationRepository.findById(request.getAccommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        Room room = new Room();
        room.setAccommodation(accommodation);
        room.setName(request.getName());
        room.setTypeRoom(request.getTypeRoom());
        room.setPrice(request.getPrice());

        room = roomRepository.save(room);
        return ApiResponse.success(room);
    }

    @GetMapping("/rooms")
    public ApiResponse<List<Room>> fetchRooms() {
        return ApiResponse.success(roomRepository.findAll());
    }

    @GetMapping("/room/{id}")
    public ApiResponse<Room> fetchRooms(@PathVariable Long id) {
        return ApiResponse.success(roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found room")));
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

    @PostMapping("/customer")
    public ApiResponse<Customer> createCustomer(@RequestBody Customer request) throws Exception {
        Optional<Customer> customer = customerRepository.findByNationalId(request.getNationalId());

        if (customer.isPresent()){
           throw new Exception("Khách hàng đã tồn tại");
        }

        return ApiResponse.success(customerRepository.save(request));
    }
}
