package com.bihan.boilerplate.rest.repository;

import com.bihan.boilerplate.rest.dto.ExchangeRequest;
import com.bihan.boilerplate.rest.entity.Item;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ExchangeRequestRepository extends JpaRepository<Item, Long> {
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO exchange_request (requester_id, receiver_id, requested_item_id, offered_item_id, status) " +
            "VALUES (?1, ?2, ?3, ?4, ?5)")
    void createOrUpdateExchangeRequest(Long requesterUserId, Long receiverUserId, Long requestedItemId, Long offeredItemId, String status);

    // TODO - Selective  Columns
    // TODO - status filtering
    // select id, offered_item_id, requested_item_id, receiver_id, requester_id
    // from exchange_request
    // where receiver_id = ? and status in ( 'CREATED', 'COMPLETE', 'PARTIALLY_COMPLETE')
    @Query(value = "select new com.bihan.boilerplate.rest.dto.ExchangeRequest(er.id, er.requesterUser.id, er.receiverUser.id, er.requestedItem.id, er.offeredItem.id, er.status) \n" +
            "        from ExchangeRequestEntity er \n" +
            "          where er.requesterUser.id = :userId or er.receiverUser.id = :userId")
    List<ExchangeRequest> findExchangeRequests(Long userId);

}
