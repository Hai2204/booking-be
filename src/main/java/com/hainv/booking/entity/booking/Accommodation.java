package com.hainv.booking.entity.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "accommodation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long accommodationId;

    @ManyToOne
    @JoinColumn(name = "partner_id", referencedColumnName = "partner_id")
    private Partner partner;

    @Column
    private String name;

    @Column(name = "accommodation_type", length = 20)
    private String accommodationType; // hotel / villa / homestay

    @Column
    private String description;

    @Column
    private String address;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Room> rooms;

}
