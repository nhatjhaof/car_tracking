package vn.car_service.Car_Service.dto.response;

import lombok.*;
import vn.car_service.Car_Service.common.Gender;

import java.io.Serializable;
import java.util.Date;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
}
