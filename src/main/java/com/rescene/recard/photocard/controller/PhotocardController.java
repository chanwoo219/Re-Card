package com.rescene.recard.photocard.controller;

import com.rescene.recard.global.response.ApiResponse;
import com.rescene.recard.photocard.dto.PhotocardCreateRequest;
import com.rescene.recard.photocard.dto.PhotocardResponse;
import com.rescene.recard.photocard.dto.PhotocardUpdateRequest;
import com.rescene.recard.photocard.service.PhotocardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/photocards")
public class PhotocardController {

    private final PhotocardService photocardService;

    @PostMapping
    public ResponseEntity<ApiResponse<PhotocardResponse>> create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody PhotocardCreateRequest request) {
        return ApiResponse.created(photocardService.create(userId, request));
    }

    @GetMapping("/{id}")
    public ApiResponse<PhotocardResponse> get(@PathVariable Long id) {
        return ApiResponse.success(photocardService.get(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<PhotocardResponse> update(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody PhotocardUpdateRequest request) {
        return ApiResponse.success(photocardService.update(id, userId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        photocardService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
