package com.rescene.recard.trade.dto;

import com.rescene.recard.trade.entity.TradeStatus;
import jakarta.validation.constraints.NotNull;

public record TradeRequestUpdateRequest(@NotNull TradeStatus status) {
}
