package vn.car_service.Car_Service.service;

import vn.car_service.Car_Service.common.UserStatus;
import vn.car_service.Car_Service.controller.request.UserCreationRequest;
import vn.car_service.Car_Service.controller.request.UserUpdateRequest;
import vn.car_service.Car_Service.controller.response.UserResponse;

import java.util.List;

public interface UserService {

    long saveUser(UserCreationRequest userCreationRequest);

    void updateUser(UserUpdateRequest userUpdateRequest);

    void changeStatus(long userId, UserStatus userStatus);

    void deleteUser(long userId);

    UserResponse getUser(long userId);

    List<UserResponse> getAllUser(int pageNo , int pageSize);
}
