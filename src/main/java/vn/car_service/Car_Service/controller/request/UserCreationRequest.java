package vn.car_service.Car_Service.controller.request;

import vn.car_service.Car_Service.Utils.EnumValue;
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

@Getter
@ToString
public class UserCreationRequest implements Serializable {

    @NotBlank(message = "first name must be not blank")
    private String firstName;
    @NotBlank(message = "last name must be not blank")
    private String lastName;
    @EnumValue(name = "gender" , enumClass = Gender.class)
    private Gender gender;
    private Date birthday;
    private String username;
    private String password;
    @Email(message = "Email invalid")
    private String email;
    @PhoneNumber
    private String phone;
    @EnumValue(name = "type" , enumClass = UserType.class)
    private UserType type;
    @EnumValue(name = "status" , enumClass = UserStatus.class)
    private UserStatus status;
    List<AddressRequest> addresses;
}
