package com.rescene.recard.trade.service.impl;

import com.rescene.recard.global.exception.BusinessException;
import com.rescene.recard.global.exception.ErrorCode;
import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.photocard.repository.PhotocardRepository;
import com.rescene.recard.trade.dto.CreateTradeRequestRequest;
import com.rescene.recard.trade.dto.TradeRequestResponse;
import com.rescene.recard.trade.entity.TradeRequest;
import com.rescene.recard.trade.repository.TradeRequestRepository;
import com.rescene.recard.trade.service.TradeRequestService;
import com.rescene.recard.user.entity.User;
import com.rescene.recard.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TradeRequestServiceImpl implements TradeRequestService {

    private final UserRepository userRepository;
    private final PhotocardRepository photocardRepository;
    private final TradeRequestRepository tradeRequestRepository;

    public TradeRequestServiceImpl(UserRepository userRepository,
                                   PhotocardRepository photocardRepository,
                                   TradeRequestRepository tradeRequestRepository) {
        this.userRepository = userRepository;
        this.photocardRepository = photocardRepository;
        this.tradeRequestRepository = tradeRequestRepository;
    }

    @Override
    @Transactional
    public TradeRequestResponse create(Long requesterId, CreateTradeRequestRequest request) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Photocard offeredCard = photocardRepository.findById(request.offeredCardId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CARD_NOT_FOUND));
        Photocard targetCard = photocardRepository.findById(request.targetCardId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CARD_NOT_FOUND));

        if (!requesterId.equals(offeredCard.getOwner().getId())) {
            throw new BusinessException(ErrorCode.NOT_OWNER);
        }
        if (requesterId.equals(targetCard.getOwner().getId())) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        TradeRequest tradeRequest = tradeRequestRepository.save(
                new TradeRequest(requester, offeredCard, targetCard));
        return TradeRequestResponse.from(tradeRequest);
    }
}
