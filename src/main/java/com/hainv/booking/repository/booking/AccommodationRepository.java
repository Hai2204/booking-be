package com.hainv.booking.repository.booking;

import com.hainv.booking.entity.booking.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation , Long> {
    Optional<Accommodation> findByAccommodationId(Long accommodationId);
}
