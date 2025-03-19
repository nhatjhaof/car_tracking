package vn.car_service.Car_Service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "address")
public class Address extends AbstractEntity{


    @Column(name = "apartment_number")
    private String apartment_number;

    @Column(name = "floor")
    private String floor;

    @Column(name = "building")
    private String building;

    @Column(name = "street_number")
    private String street_number;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "address_type")
    private String addressType;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
