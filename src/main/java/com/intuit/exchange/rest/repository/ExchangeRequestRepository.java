package com.intuit.exchange.rest.repository;

import com.intuit.exchange.rest.entity.exchangeRequest.ExchangeRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequestEntity, Long> {

    @Query(value = "select er \n" +
            "        from ExchangeRequestEntity er \n" +
            "          where er.requesterUser.id = :userId or er.receiverUser.id = :userId")
    List<ExchangeRequestEntity> findExchangeRequests(Long userId);

    // TODO - we can add receiver id in the where clause and status in the clause and see the no of rows updated
    @Query(value = "update ExchangeRequestEntity er \n" +
            "        set er.status = :status \n" +
            "          where er.id = :exchangeRequestId")
    int updateStatusOfRequestForId(String status, Long exchangeRequestId);

}
