package com.rescene.recard.user.controller;

import com.rescene.recard.global.response.ApiResponse;
import com.rescene.recard.user.dto.UserCreateRequest;
import com.rescene.recard.user.dto.UserResponse;
import com.rescene.recard.user.dto.UserUpdateRequest;
import com.rescene.recard.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserCreateRequest request) {
        return ApiResponse.created(userService.create(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> get(@PathVariable Long id) {
        return ApiResponse.success(userService.get(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateNickname(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return ApiResponse.success(userService.updateNickname(id, userId, request));
    }
}
