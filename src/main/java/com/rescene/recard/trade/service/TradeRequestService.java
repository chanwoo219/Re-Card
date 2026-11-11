package com.rescene.recard.trade.service;

import com.rescene.recard.trade.dto.CreateTradeRequestRequest;
import com.rescene.recard.trade.dto.TradeRequestResponse;

public interface TradeRequestService {
    TradeRequestResponse create(Long requesterId, CreateTradeRequestRequest request);
}
