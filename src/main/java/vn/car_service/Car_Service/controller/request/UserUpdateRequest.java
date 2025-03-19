package vn.car_service.Car_Service.controller.request;

import vn.car_service.Car_Service.Utils.EnumValue;
import vn.car_service.Car_Service.Utils.PhoneNumber;
import vn.car_service.Car_Service.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserUpdateRequest implements Serializable {

    @NotNull(message = "Id must be not null")
    @Min(value = 1 , message = "userId must be equal or than 1")
    private Long id;
    @NotBlank(message = "first name must be not blank")
    private String firstName;
    @NotBlank(message = "last name must be not blank")
    private String lastName;
    @EnumValue(name = "gender" , enumClass = Gender.class)
    private Gender gender;
    private Date birthday;
    private String username;
    @Email(message = "Email invalid")
    private String email;
    @PhoneNumber
    private String phone;
    private List<AddressRequest> addresses;
}
