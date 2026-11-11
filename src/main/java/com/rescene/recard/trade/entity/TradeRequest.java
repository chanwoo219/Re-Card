package com.rescene.recard.trade.entity;

import com.rescene.recard.global.entity.BaseEntity;
import com.rescene.recard.global.exception.BusinessException;
import com.rescene.recard.global.exception.ErrorCode;
import com.rescene.recard.photocard.entity.Photocard;
import com.rescene.recard.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TradeRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offered_card_id", nullable = false)
    private Photocard offeredCard;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_card_id", nullable = false)
    private Photocard targetCard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeStatus status = TradeStatus.REQUESTED;

    protected TradeRequest() {
    }

    public TradeRequest(User requester, Photocard offeredCard, Photocard targetCard) {
        this.requester = requester;
        this.offeredCard = offeredCard;
        this.targetCard = targetCard;
    }

    public void accept() {
        ensureRequested();
        status = TradeStatus.ACCEPTED;
    }

    public void reject() {
        ensureRequested();
        status = TradeStatus.REJECTED;
    }

    public Long getId() {
        return id;
    }

    public User getRequester() {
        return requester;
    }

    public Photocard getOfferedCard() {
        return offeredCard;
    }

    public Photocard getTargetCard() {
        return targetCard;
    }

    public TradeStatus getStatus() {
        return status;
    }

    private void ensureRequested() {
        if (status != TradeStatus.REQUESTED) {
            throw new BusinessException(ErrorCode.TRADE_ALREADY_DONE);
        }
    }
}
