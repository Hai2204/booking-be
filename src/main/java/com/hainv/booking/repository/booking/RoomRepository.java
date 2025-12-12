package com.hainv.booking.repository.booking;

import com.hainv.booking.entity.booking.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
       SELECT r FROM Room r
       WHERE (:category IS NULL OR r.roomCategory = :category)
       """)
    List<Room> findRooms(@Param("category") String category, Pageable pageable);
}
