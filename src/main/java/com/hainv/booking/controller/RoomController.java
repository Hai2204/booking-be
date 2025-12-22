package com.hainv.booking.controller;

import com.hainv.booking.entity.booking.Accommodation;
import com.hainv.booking.entity.booking.Room;
import com.hainv.booking.entity.dto.RoomRequest;
import com.hainv.booking.repository.booking.AccommodationRepository;
import com.hainv.booking.repository.booking.RoomRepository;
import com.hainv.booking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class RoomController {
    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;

    @PostMapping("/room")
    public ApiResponse<Room> createRoom(@RequestBody RoomRequest request) {
        Accommodation accommodation = accommodationRepository.findByAccommodationId(request.getAccommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        Room room = new Room();
        room.setAccommodation(accommodation);
        room.setName(request.getName());
        room.setTypeRoom(request.getTypeRoom());
        room.setPrice(request.getPrice());
        room.setRoomCategory(request.getRoomCategory());
        room.setRoomCode(request.getRoomCode());
        room.setDescription(request.getDescription());
        room.setAmenities(request.getAmenities());
        room.setPolicy(request.getPolicy());

        room = roomRepository.save(room);
        return ApiResponse.success(room);
    }

    @GetMapping("/rooms")
    public ApiResponse<List<Room>> fetchRooms(@RequestParam(value = "category", required = false) String category,
                                              @RequestParam(value = "limit", required = false) Integer limit) {
        Pageable pageable = PageRequest.of(0, limit == null ? Integer.MAX_VALUE : limit);

        return ApiResponse.success(roomRepository.findRooms(category, pageable));
    }

    @GetMapping("/room/{id}")
    public ApiResponse<Room> fetchRooms(@PathVariable Long id) {
        return ApiResponse.success(roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found room")));
    }
}
