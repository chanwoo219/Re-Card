package com.rescene.recard.trade.repository;

import com.rescene.recard.trade.entity.TradeRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRequestRepository extends JpaRepository<TradeRequest, Long> {

    List<TradeRequest> findAllByTargetCard_Owner_Id(Long ownerId);

    boolean existsByOfferedCard_IdOrTargetCard_Id(Long offeredCardId, Long targetCardId);
}
