package com.rescene.recard.trade.service;

import com.rescene.recard.trade.dto.CreateTradeRequestRequest;
import com.rescene.recard.trade.dto.TradeRequestResponse;
import java.util.List;

public interface TradeRequestService {
    TradeRequestResponse create(Long requesterId, CreateTradeRequestRequest request);

    List<TradeRequestResponse> findReceived(Long ownerId);
}
