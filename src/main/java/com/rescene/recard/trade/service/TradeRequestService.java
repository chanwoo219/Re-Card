package com.rescene.recard.trade.service;

import com.rescene.recard.trade.dto.CreateTradeRequestRequest;
import com.rescene.recard.trade.dto.TradeRequestResponse;
import com.rescene.recard.trade.dto.TradeRequestUpdateRequest;
import java.util.List;

public interface TradeRequestService {
    TradeRequestResponse create(Long requesterId, CreateTradeRequestRequest request);

    List<TradeRequestResponse> findReceived(Long ownerId);

    TradeRequestResponse update(Long tradeRequestId, Long ownerId, TradeRequestUpdateRequest request);
}
