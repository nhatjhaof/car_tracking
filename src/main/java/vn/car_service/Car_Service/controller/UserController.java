package vn.car_service.Car_Service.controller;

import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import vn.car_service.Car_Service.Exception.ResourceNotFound;
import vn.car_service.Car_Service.dto.request.UserCreationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import vn.car_service.Car_Service.dto.request.UserUpdateRequest;
import vn.car_service.Car_Service.dto.response.PageResponse;
import vn.car_service.Car_Service.dto.response.UserResponse;
import vn.car_service.Car_Service.service.UserService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
@Tag(name = "User-controller")
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user" , description = "API get user")
    @GetMapping("/{userId}")
    public Map<String,Object> getUser(@PathVariable long userId) {
        UserResponse userResponse = userService.getUser(userId);
        log.info("get detail user id: {}", userId);
        try{
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message", "Get user successfully");
            result.put("data", userResponse);
            return result;
        }catch (ResourceNotFound e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.BAD_REQUEST.value());
            result.put("message", e.getMessage());
            return result;
        }
    }
    @Operation(summary = "Get list user" , description = "API get list user")
    @GetMapping("/getUserList")
    public Map<String,Object> getUserList(@RequestParam(defaultValue = "0",  required = false) int pageNo,
                                          @Min(10) @RequestParam(defaultValue = "10", required = false) int pageSize,
                                          @RequestParam(required = false) String sortBy){
        log.info("get list user for field: {}", sortBy);

        PageResponse<?> userResponse = userService.getAllUser(pageNo, pageSize, sortBy);
        try {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message", "Get user successfully");
            result.put("data", userResponse);
            return result;
        }catch (ResourceNotFound e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.BAD_REQUEST.value());
            result.put("message", e.getMessage());
            return result;
        }
    }

    @Operation(summary = "Get list user" , description = "API get list user multiple columns")
    @GetMapping("/getUserListColumn")
    public Map<String,Object> getAllUserWithMultipleColumns(@RequestParam(defaultValue = "0",  required = false) int pageNo,
                                          @Min(10) @RequestParam(defaultValue = "10", required = false) int pageSize,
                                          @RequestParam(required = false) String... sorts){
        log.info("get list user for multiple field: {}", sorts);

        PageResponse<?> userResponse = userService.getAllUserWithMultipleColumns(pageNo, pageSize, sorts);
        try {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message", "Get user successfully");
            result.put("data", userResponse);
            return result;
        }catch (ResourceNotFound e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.BAD_REQUEST.value());
            result.put("message", e.getMessage());
            return result;
        }
    }

    @Operation(summary = "Get list user" , description = "API get list user search columns")
    @GetMapping("/getUserListBySearch")
    public Map<String,Object> getAllUserWithMultipleColumnsAndSearch(@RequestParam(defaultValue = "0",  required = false) int pageNo,
                                                            @Min(10) @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                                     @RequestParam(required = false) String keyword,
                                                                     @RequestParam(required = false) String sortBy){
        log.info("get list user for search field: {}", keyword);

        PageResponse<?> userResponse = userService.getAllUserWithMultipleColumnsAndSearch(pageNo, pageSize,keyword, sortBy);
        try {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.OK.value());
            result.put("message", "Get user successfully");
            result.put("data", userResponse);
            return result;
        }catch (ResourceNotFound e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("status", HttpStatus.BAD_REQUEST.value());
            result.put("message", e.getMessage());
            return result;
        }
    }

    @Operation(summary = "Create user" , description = "API add user")
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreationRequest request, SessionStatus sessionStatus) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "Add user successfully");
        result.put("data", userService.saveUser(request));
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }
    @PatchMapping("/updateUser/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable long userId, @Valid @RequestBody UserUpdateRequest request) {
        log.info("update user id: {}", userId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "Update user successfully");
        result.put("data", userService.updateUser(userId, request));
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable long userId) {
        log.info("delete user id: {}", userId);
        userService.deleteUser(userId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "Delete user successfully");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
