package com.rescene.recard.trade.dto;

import com.rescene.recard.trade.entity.TradeRequest;
import com.rescene.recard.trade.entity.TradeStatus;

public record TradeRequestResponse(
        Long id,
        Long requesterId,
        Long offeredCardId,
        Long targetCardId,
        TradeStatus status
) {
    public static TradeRequestResponse from(TradeRequest tradeRequest) {
        return new TradeRequestResponse(
                tradeRequest.getId(),
                tradeRequest.getRequester().getId(),
                tradeRequest.getOfferedCard().getId(),
                tradeRequest.getTargetCard().getId(),
                tradeRequest.getStatus()
        );
    }
}
