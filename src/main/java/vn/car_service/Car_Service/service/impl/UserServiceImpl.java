package vn.car_service.Car_Service.service.impl;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import vn.car_service.Car_Service.Exception.ResourceNotFound;
import vn.car_service.Car_Service.common.UserStatus;
import vn.car_service.Car_Service.dto.request.AddressRequest;
import vn.car_service.Car_Service.dto.request.UserCreationRequest;
import vn.car_service.Car_Service.dto.request.UserUpdateRequest;
import vn.car_service.Car_Service.dto.response.PageResponse;
import vn.car_service.Car_Service.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.car_service.Car_Service.model.Address;
import vn.car_service.Car_Service.model.User;
import org.springframework.stereotype.Service;
import vn.car_service.Car_Service.repository.SearchRepository;
import vn.car_service.Car_Service.repository.UserRepository;
import vn.car_service.Car_Service.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SearchRepository searchRepository;

    @Override
    public long saveUser(UserCreationRequest userCreationRequest) {
       User user = User.builder()
               .firstName(userCreationRequest.getFirstName())
               .lastName(userCreationRequest.getLastName())
               .gender(userCreationRequest.getGender())
               .birthday(userCreationRequest.getBirthday())
               .phone(userCreationRequest.getPhone())
               .email(userCreationRequest.getEmail())
               .username(userCreationRequest.getUsername())
               .password(userCreationRequest.getPassword())
               .status(userCreationRequest.getStatus())
               .type(userCreationRequest.getType())
               .addresses(convertAddress(userCreationRequest.getAddresses()))
               .build();
                userCreationRequest.getAddresses().forEach(addressRequest -> user.saveAddress(
                        Address.builder()
                                .apartment_number(addressRequest.getApartment_number())
                                .floor(addressRequest.getFloor())
                                .building(addressRequest.getBuilding())
                                .street_number(addressRequest.getStreet_number())
                                .street(addressRequest.getStreet())
                                .city(addressRequest.getCity())
                                .country(addressRequest.getCountry())
                                .addressType(addressRequest.getAddress_type())
                                .build()
                ));
       userRepository.save(user);

       log.info("User has saved!");

       return user.getId();
    }

    @Override
    public User updateUser(long userId,UserUpdateRequest userUpdateRequest) {
        User user = getUserById(userId);
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setGender(userUpdateRequest.getGender());
        user.setBirthday(userUpdateRequest.getBirthday());
        user.setPhone(userUpdateRequest.getPhone());
        if(!userUpdateRequest.getEmail().equals(user.getEmail())) {
            user.setEmail(userUpdateRequest.getEmail());
        }
        user.setUsername(userUpdateRequest.getUsername());
        user.setPassword(userUpdateRequest.getPassword());
        user.setStatus(userUpdateRequest.getStatus());
        user.setType(userUpdateRequest.getType());
        user.setAddresses(convertAddress(userUpdateRequest.getAddresses()));
        log.info("User has updated! ={}" , userId);
        userRepository.save(user);
        return user;
    }

    @Override
    public void changeStatus(long userId, UserStatus userStatus) {
        User user = getUserById(userId);
        user.setStatus(userStatus);
        userRepository.save(user);
        log.info("User has updated! ={}" , userId);
    }

    @Override
    public void deleteUser(long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public UserResponse getUser(long userId)
    {
        User user = getUserById(userId);
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

    @Override
    public PageResponse<?> getAllUser(int pageNo, int pageSize, String sortBy) {
        int page = 0 ;
        if(pageNo > 0){
            page = pageNo - 1;
        }
        List<Sort.Order> sorts = new ArrayList<>();
        //neu sortBy co gia tri
        if(StringUtils.hasLength(sortBy)){
            //kiem tra format field:asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")){
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                }else{
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }
        Pageable pageable = PageRequest.of(page, pageSize,Sort.by(sorts));
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> responses = users.stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build()).toList();
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(users.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    public PageResponse<?> getAllUserWithMultipleColumns(int pageNo, int pageSize, String... sorts) {
        int page = 0 ;
        if(pageNo > 0){
            page = pageNo - 1;
        }
        List<Sort.Order> orders = new ArrayList<>();
       for (String sortBy : sorts){//neu sortBy co gia tri
        if(StringUtils.hasLength(sortBy)){
            //kiem tra format field:asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")){
                    orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                }else{
                    orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }
        }
        Pageable pageable = PageRequest.of(page, pageSize,Sort.by(orders));
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> responses = users.stream().map(user -> UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build()).toList();
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(users.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    public PageResponse<?> getAllUserWithMultipleColumnsAndSearch(int pageNo, int pageSize, String keyword, String sortBy) {
        return searchRepository.getAllUserWithMultipleColumnsAndSearch(pageNo, pageSize, keyword, sortBy);
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
    private User getUserById(long userId) {
       return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User not found"));
    }
}
