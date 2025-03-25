package vn.car_service.Car_Service.dto.request;

import jakarta.validation.constraints.NotNull;
import vn.car_service.Car_Service.Utils.EnumValue;
import vn.car_service.Car_Service.Utils.GenderSubset;
import vn.car_service.Car_Service.Utils.PhoneNumber;
import vn.car_service.Car_Service.common.Gender;
import vn.car_service.Car_Service.common.UserStatus;
import vn.car_service.Car_Service.common.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static vn.car_service.Car_Service.common.Gender.*;

@Getter
@ToString
public class UserCreationRequest implements Serializable {

    @NotBlank(message = "first name must be not blank")
    private String firstName;
    @NotBlank(message = "last name must be not blank")
    private String lastName;
    //@Pattern(regexp = "^male|female|other$", message = "gender must be one in {male, female, other}")
    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    private Gender gender;
    private Date birthday;
    private String username;
    private String password;
    @Email(message = "Email invalid")
    private String email;
    @PhoneNumber
    private String phone;
    @NotNull(message = "type must be not null")
    @EnumValue(name = "type" , enumClass = UserType.class)
    private UserType type;
    @EnumValue(name = "status" , enumClass = UserStatus.class)
    private UserStatus status;
    List<AddressRequest> addresses;
}
