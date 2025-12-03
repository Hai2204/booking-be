package com.hainv.booking.repository.user;

import com.hainv.booking.entity.user.User;
import com.hainv.booking.entity.user.UserSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsernameAll(@Param("username") String username);

    // Trả ra projection không có password
    Optional<UserSummary> findProjectedByUsername(String username);
}
