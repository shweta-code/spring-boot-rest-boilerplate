package com.bihan.boilerplate.rest.service.impl;


import com.bihan.boilerplate.rest.dto.ExchangeRequest;
import com.bihan.boilerplate.rest.dto.ExchangeRequestDetailsResponse;
import com.bihan.boilerplate.rest.dto.NewExchangeRequestDetails;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestStatus;
import com.bihan.boilerplate.rest.exception.ResourceNotFoundException;
import com.bihan.boilerplate.rest.entity.Item;
import com.bihan.boilerplate.rest.repository.ExchangeRequestRepository;
import com.bihan.boilerplate.rest.service.ExchangeRequestService;
import com.bihan.boilerplate.rest.service.ItemService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * This service is responsible for actions related to ExchangeRequest
 */
@Service
public class ExchangeRequestServiceImpl implements ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    @Autowired
    private ItemService itemService;

    // TODO - Can write UTs, Integrtion test for optimistic locking
    @Override
    @Transactional
    // TODO - Return actual request
    // TODO - NewExchangeRequest will only have item_id details
    // TODO - Create exchange request using hibernate only
    // TODO - Constructor Injection
    public NewExchangeRequestDetails createRequest(NewExchangeRequestDetails newExchangeRequestDetails){


        // Validate if it is a valid request
        // if the musical instruments are valid
        // Check the musical instrument is not owned by the user
        // throw BadRequestException in each case


        // create a request which is in progress
        try {
            exchangeRequestRepository.createOrUpdateExchangeRequest(newExchangeRequestDetails.getRequesterUserId(), newExchangeRequestDetails.getReceiverUserId(),
                    newExchangeRequestDetails.getRequestedItemId(), newExchangeRequestDetails.getOfferedItemId(), ExchangeRequestStatus.IN_PROGRESS.getValue());
        } catch (ConstraintViolationException ex){
            // Constraint Violation Exception may occur if the instrument or user no more exists
            // throw Bad Request Exception
        }

        // read state of items - is_listed if both are true
        Item requestedItem = itemService.getItemById(newExchangeRequestDetails.getRequestedItemId());
        Item offeredItem = itemService.getItemById(newExchangeRequestDetails.getOfferedItemId());

        if(!requestedItem.isListed() || !offeredItem.isListed()){
            // throw new ResourceNotAvailableException();
        }
        // update state of items is_listed - false
        // catch optimistic lock exception here
        try {
            itemService.unListItemsForExchangeRequest(newExchangeRequestDetails.getRequestedItemId(), newExchangeRequestDetails.getOfferedItemId());
        } catch (OptimisticLockException ex){
            System.out.println("Optimistic Lock Exception thrown");
            throw new ResourceNotFoundException("Musical Instrument", "isListed", false);
        }

        // update exchange request as created
        exchangeRequestRepository.createOrUpdateExchangeRequest(newExchangeRequestDetails.getRequesterUserId(), newExchangeRequestDetails.getReceiverUserId(),
                newExchangeRequestDetails.getRequestedItemId(), newExchangeRequestDetails.getOfferedItemId(), ExchangeRequestStatus.CREATED.getValue());

        // TODO - this should be sent with possible action as cancel.
        return newExchangeRequestDetails;
    }

    /*@Override
    @Transactional
    // TODO - Return actual request
    public NewExchangeRequestDetails acceptRequest(Long exchangeRequestId, Long userId) {
    }*/

    // TODO- Override
    @Override
    public List<ExchangeRequestDetailsResponse> listMyExchangeRequests(Long userId){
        List<ExchangeRequest> exchangeRequests = exchangeRequestRepository.findExchangeRequests(userId);
        List<ExchangeRequestDetailsResponse> exchangeRequestDetailsResponses = new ArrayList<>();
        for (ExchangeRequest exchangeRequest : exchangeRequests) {
            exchangeRequestDetailsResponses.add(ExchangeRequestDetailsResponse.buildFrom(exchangeRequest, userId));
        }
        return exchangeRequestDetailsResponses;
    }



}