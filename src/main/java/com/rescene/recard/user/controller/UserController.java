package com.rescene.recard.user.controller;

import com.rescene.recard.global.response.ApiResponse;
import com.rescene.recard.user.dto.UserCreateRequest;
import com.rescene.recard.user.dto.UserResponse;
import com.rescene.recard.user.dto.UserUpdateRequest;
import com.rescene.recard.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 생성", description = "닉네임으로 새 유저를 생성한다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "INVALID_INPUT: 닉네임이 비었거나 20자 초과"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "DUPLICATE_NICKNAME: 이미 사용 중인 닉네임")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserCreateRequest request) {
        return ApiResponse.created(userService.create(request));
    }

    @Operation(summary = "유저 조회", description = "id로 유저 한 명을 조회한다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "USER_NOT_FOUND: 유저 없음")
    })
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> get(@PathVariable Long id) {
        return ApiResponse.success(userService.get(id));
    }

    @Operation(summary = "닉네임 수정", description = "본인 닉네임을 수정한다. X-User-Id가 경로의 id와 같아야 한다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "INVALID_INPUT: 닉네임 검증 실패 또는 X-User-Id 누락"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "NOT_OWNER: 본인이 아님"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "USER_NOT_FOUND: 유저 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "DUPLICATE_NICKNAME: 이미 사용 중인 닉네임")
    })
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateNickname(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return ApiResponse.success(userService.updateNickname(id, userId, request));
    }
}
