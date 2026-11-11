package com.rescene.recard.trade.dto;

import jakarta.validation.constraints.NotNull;

public record CreateTradeRequestRequest(
        @NotNull Long offeredCardId,
        @NotNull Long targetCardId
) {
}
