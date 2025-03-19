package vn.car_service.Car_Service.controller;

import vn.car_service.Car_Service.controller.request.UserCreationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.car_service.Car_Service.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User-controller")
@Validated
public class UserController {
    private final UserService userService;


    @Operation(summary = "Create user" , description = "API add user")
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreationRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "Add user successfully");
        result.put("data", userService.saveUser(request));
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }
}
