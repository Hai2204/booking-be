package com.hainv.booking.repository.booking;

import com.hainv.booking.entity.booking.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation , Long> {
}
