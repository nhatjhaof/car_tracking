package vn.car_service.Car_Service.service;

import vn.car_service.Car_Service.common.UserStatus;
import vn.car_service.Car_Service.dto.request.UserCreationRequest;
import vn.car_service.Car_Service.dto.request.UserUpdateRequest;
import vn.car_service.Car_Service.dto.response.PageResponse;
import vn.car_service.Car_Service.dto.response.UserResponse;
import vn.car_service.Car_Service.model.User;

import java.util.List;

public interface UserService {

    long saveUser(UserCreationRequest userCreationRequest);

    User updateUser(long userId, UserUpdateRequest userUpdateRequest);

    void changeStatus(long userId, UserStatus userStatus);

    void deleteUser(long userId);

    UserResponse getUser(long userId);

    PageResponse<?> getAllUser(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUserWithMultipleColumns(int pageNo, int pageSize, String... sorts);

    PageResponse<?> getAllUserWithMultipleColumnsAndSearch(int pageNo, int pageSize, String keyword, String sortBy);
}
