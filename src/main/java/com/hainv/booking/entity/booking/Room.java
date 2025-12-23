package com.hainv.booking.entity.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction(" ACTIVE = 1")
@SQLDelete(sql = " UPDATE Room SET ACTIVE = 0 WHERE id = ? ")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", referencedColumnName = "accommodation_id")
    private Accommodation accommodation;

    @Column(length = 50, nullable = false)
    private String name;

    @Column
    private String roomCode;

    @Column
    private String roomCategory;

    @Column(name = "type_room")
    private Integer typeRoom; // 2_person / 4_person / the_whole_house

    private Integer price;

    @Column(nullable = false)
    private Short active = 1;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;

    @Column(length = 2000)
    private String description;

    @Column
    private String amenities;

    @Column
    private String policy;
}
