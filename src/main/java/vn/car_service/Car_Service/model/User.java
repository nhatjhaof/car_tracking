package vn.car_service.Car_Service.model;

import vn.car_service.Car_Service.common.Gender;
import vn.car_service.Car_Service.common.UserStatus;
import vn.car_service.Car_Service.common.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_user")
public class User extends AbstractEntity{


    @Column(name = "firstName", length = 255)
    private String firstName;

    @Column(name = "lastName", length = 255)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender", length = 255)
    private Gender gender;

    @Column(name = "date_of_birth" , length = 255)
    @Temporal(TemporalType.DATE)
    private Date date_of_birth;

    @Column(name = "phone" , length = 15)
    private String phone;

    @Column(name = "email" , length = 255)
    private String email;

    @Column(name = "username" , length = 255)
    private String username;

    @Column(name = "password" , length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status" , length = 255)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type" , length = 255)
    private UserType type;



    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER , mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

    public void saveAddress(Address address) {
        if(address != null) {
            if(addresses == null){
                addresses = new ArrayList<>();
            }
            addresses.add(address);
            address.setUser(this);// save user id
        }
    }
}
