package vn.car_service.Car_Service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AddressRequest implements Serializable {
    private String apartment_number;
    private String floor;
    private String building;
    private String street_number;
    private String street;
    private String city;
    private String country;
    private String address_type;
}
