package com.rescene.recard.trade.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.rescene.recard.global.exception.BusinessException;
import com.rescene.recard.global.exception.ErrorCode;
import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.photocard.entity.PhotocardMember;
import com.rescene.recard.photocard.repository.PhotocardRepository;
import com.rescene.recard.trade.dto.TradeRequestUpdateRequest;
import com.rescene.recard.trade.entity.TradeRequest;
import com.rescene.recard.trade.entity.TradeStatus;
import com.rescene.recard.trade.repository.TradeRequestRepository;
import com.rescene.recard.user.entity.User;
import com.rescene.recard.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TradeRequestServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhotocardRepository photocardRepository;

    @Mock
    private TradeRequestRepository tradeRequestRepository;

    @InjectMocks
    private TradeRequestServiceImpl tradeRequestService;

    @Test
    void acceptTradeSwapsPhotocardOwners() {
        User requester = mock(User.class);
        User receiver = mock(User.class);
        given(requester.getId()).willReturn(1L);
        given(receiver.getId()).willReturn(2L);

        Photocard offeredCard = new Photocard(requester, PhotocardMember.WONI, "Album A", null);
        Photocard targetCard = new Photocard(receiver, PhotocardMember.LIV, "Album B", null);
        TradeRequest tradeRequest = new TradeRequest(requester, offeredCard, targetCard);
        given(userRepository.findById(2L)).willReturn(Optional.of(receiver));
        given(tradeRequestRepository.findById(10L)).willReturn(Optional.of(tradeRequest));

        var response = tradeRequestService.update(
                10L, 2L, new TradeRequestUpdateRequest(TradeStatus.ACCEPTED));

        assertThat(offeredCard.getOwner()).isSameAs(receiver);
        assertThat(targetCard.getOwner()).isSameAs(requester);
        assertThat(response.status()).isEqualTo(TradeStatus.ACCEPTED);
    }

    @Test
    void acceptingAlreadyAcceptedTradeThrowsTradeAlreadyDone() {
        User requester = mock(User.class);
        User receiver = mock(User.class);

        Photocard offeredCard = new Photocard(requester, PhotocardMember.WONI, "Album A", null);
        Photocard targetCard = new Photocard(receiver, PhotocardMember.LIV, "Album B", null);
        TradeRequest tradeRequest = new TradeRequest(requester, offeredCard, targetCard);
        tradeRequest.accept();
        given(userRepository.findById(2L)).willReturn(Optional.of(receiver));
        given(tradeRequestRepository.findById(10L)).willReturn(Optional.of(tradeRequest));

        assertThatThrownBy(() -> tradeRequestService.update(
                10L, 2L, new TradeRequestUpdateRequest(TradeStatus.ACCEPTED)))
                .isInstanceOfSatisfying(BusinessException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ErrorCode.TRADE_ALREADY_DONE));
        assertThat(offeredCard.getOwner()).isSameAs(requester);
        assertThat(targetCard.getOwner()).isSameAs(receiver);
    }
}
