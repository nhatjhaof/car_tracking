package vn.car_service.Car_Service.service.impl;

import vn.car_service.Car_Service.common.UserStatus;
import vn.car_service.Car_Service.controller.request.AddressRequest;
import vn.car_service.Car_Service.controller.request.UserCreationRequest;
import vn.car_service.Car_Service.controller.request.UserUpdateRequest;
import vn.car_service.Car_Service.controller.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.car_service.Car_Service.model.Address;
import vn.car_service.Car_Service.model.User;
import org.springframework.stereotype.Service;
import vn.car_service.Car_Service.repository.UserRepository;
import vn.car_service.Car_Service.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public long saveUser(UserCreationRequest userCreationRequest) {
       User user = User.builder()
               .firstName(userCreationRequest.getFirstName())
               .lastName(userCreationRequest.getLastName())
               .gender(userCreationRequest.getGender())
               .date_of_birth(userCreationRequest.getBirthday())
               .phone(userCreationRequest.getPhone())
               .email(userCreationRequest.getEmail())
               .username(userCreationRequest.getUsername())
               .password(userCreationRequest.getPassword())
               .status(userCreationRequest.getStatus())
               .type(userCreationRequest.getType())
               .addresses(convertAddress(userCreationRequest.getAddresses()))
               .build();
       userRepository.save(user);

       log.info("User has saved!");

       return user.getId();
    }

    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest) {

    }

    @Override
    public void changeStatus(long userId, UserStatus userStatus) {

    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public UserResponse getUser(long userId) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUser(int pageNo, int pageSize) {
        return List.of();
    }

    private List<Address> convertAddress(List<AddressRequest> addresses) {
        List<Address> result = new ArrayList<>();
        addresses.forEach(a ->
                result.add(Address
                        .builder()
                        .apartment_number(a.getApartment_number())
                        .floor(a.getFloor()).building(a.getBuilding())
                                .street_number(a.getStreet_number()).street(a.getStreet())
                                .city(a.getCity())
                                .country(a.getCountry())
                                .addressType(a.getAddress_type())
                        .build()));
        return result;
    }
}
