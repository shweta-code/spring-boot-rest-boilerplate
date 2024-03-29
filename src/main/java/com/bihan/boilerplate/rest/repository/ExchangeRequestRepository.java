package com.bihan.boilerplate.rest.repository;

import com.bihan.boilerplate.rest.model.ExchangeRequest;
import com.bihan.boilerplate.rest.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ExchangeRequestRepository extends JpaRepository<Item, Long> {
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO exchange_request (requester_id, receiver_id, requested_item_id, offered_item_id, status) " +
            "VALUES (?1, ?2, ?3, ?4, ?5)")
    void createOrUpdateExchangeRequest(Long requesterUserId, Long receiverUserId, Long requestedItemId, Long offeredItemId, String status);
}
