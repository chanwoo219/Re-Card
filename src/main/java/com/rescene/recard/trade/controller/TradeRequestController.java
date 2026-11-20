package com.rescene.recard.trade.controller;

import com.rescene.recard.global.response.ApiResponse;
import com.rescene.recard.trade.dto.CreateTradeRequestRequest;
import com.rescene.recard.trade.dto.TradeRequestUpdateRequest;
import com.rescene.recard.trade.dto.TradeRequestResponse;
import com.rescene.recard.trade.service.TradeRequestService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/trades")
public class TradeRequestController {

    private final TradeRequestService tradeRequestService;

    public TradeRequestController(TradeRequestService tradeRequestService) {
        this.tradeRequestService = tradeRequestService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TradeRequestResponse>> create(
            @RequestHeader("X-User-Id") Long requesterId,
            @Valid @RequestBody CreateTradeRequestRequest request) {
        TradeRequestResponse response = tradeRequestService.create(requesterId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @GetMapping("/received")
    public ApiResponse<List<TradeRequestResponse>> findReceived(
            @RequestHeader("X-User-Id") Long ownerId) {
        return ApiResponse.success(tradeRequestService.findReceived(ownerId));
    }

    @PutMapping("/{id}")
    public ApiResponse<TradeRequestResponse> update(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId,
            @Valid @RequestBody TradeRequestUpdateRequest request) {
        return ApiResponse.success(tradeRequestService.update(id, ownerId, request));
    }
}
