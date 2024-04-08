package com.bihan.boilerplate.rest.service.impl;


import com.bihan.boilerplate.rest.constants.ApplicationConstants;
import com.bihan.boilerplate.rest.dto.ExchangeRequestDetailsResponse;
import com.bihan.boilerplate.rest.model.request.NewExchangeRequestDetails;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestEntity;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestStatus;
import com.bihan.boilerplate.rest.exception.ResourceNotFoundException;
import com.bihan.boilerplate.rest.entity.Item;
import com.bihan.boilerplate.rest.repository.ExchangeRequestRepository;
import com.bihan.boilerplate.rest.service.ExchangeRequestService;
import com.bihan.boilerplate.rest.service.ItemService;
import com.bihan.boilerplate.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * This service is responsible for actions related to ExchangeRequest
 */
@Service
public class ExchangeRequestServiceImpl implements ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    // TODO - Can write UTs, Integrtion test for optimistic locking
    @Override
    @Transactional
    // TODO - Constructor Injection
    public ExchangeRequestDetailsResponse createRequest(NewExchangeRequestDetails newExchangeRequestDetails, Long requesterUserId){

        // Validate if it is a valid request
        // if the musical instruments are valid
        // Check the requested musical instrument is not owned by the user
        // Check the offered musical instrument is owned by the user
        // throw BadRequestException in each case

        // create a request which is in progress
        Item offeredItem = itemService.getItemById(newExchangeRequestDetails.getOfferedItemId());
        Item requestedItem = itemService.getItemById(newExchangeRequestDetails.getRequestedItemId());
        if(!requestedItem.isListed() || !offeredItem.isListed()){
            // throw new ResourceNotAvailableException();
        }

        ExchangeRequestEntity inProgressExchangeRequest = new ExchangeRequestEntity.Builder()
                .withRequesterUser(requestedItem.getOwnerUser())
                .withReceiverUser(offeredItem.getOwnerUser())
                .withRequestedItem(requestedItem)
                .withOfferedItem(offeredItem)
                .withStatus(ExchangeRequestStatus.IN_PROGRESS.getValue())
                .build();
        inProgressExchangeRequest = exchangeRequestRepository.save(inProgressExchangeRequest);


        // update state of items is_listed - false
        // catch optimistic lock exception here
        itemService.unListItems(newExchangeRequestDetails.getRequestedItemId(), newExchangeRequestDetails.getOfferedItemId());

        // update exchange request as created
        inProgressExchangeRequest.setStatus(ExchangeRequestStatus.CREATED.getValue());
        ExchangeRequestEntity exchangeRequestEntity = exchangeRequestRepository.save(inProgressExchangeRequest);

        return ExchangeRequestDetailsResponse.buildFromExchangeRequestEntity(exchangeRequestEntity, requesterUserId);
    }

    /*@Override
    @Transactional
    // TODO - Return actual request
    public NewExchangeRequestDetails acceptRequest(Long exchangeRequestId, Long userId) {
    }*/

    @Override
    public List<ExchangeRequestDetailsResponse> getExchangeRequestsForAUser(Long userId){
        List<ExchangeRequestEntity> exchangeRequests = exchangeRequestRepository.findExchangeRequests(userId);
        List<ExchangeRequestDetailsResponse> exchangeRequestDetailsResponses = new ArrayList<>();
        for (ExchangeRequestEntity exchangeRequest : exchangeRequests) {
            ExchangeRequestDetailsResponse exchangeRequestDetailsResponse = ExchangeRequestDetailsResponse.buildFromExchangeRequestEntity(exchangeRequest, userId);
            exchangeRequestDetailsResponses.add(exchangeRequestDetailsResponse);
        }
        return exchangeRequestDetailsResponses;
    }

    @Override
    public ExchangeRequestDetailsResponse acceptExchangeRequest(Long exchangeRequestId, Long userId){
        // TODO - Validate if user can do this operation
        // TODO - validate if this operation can be done on the request
        exchangeRequestRepository.updateStatusOfRequestForId(ExchangeRequestStatus.ACCEPTED.getValue(), exchangeRequestId);
        Optional<ExchangeRequestEntity> exchangeRequestOptional = exchangeRequestRepository.findById(exchangeRequestId);
        if (exchangeRequestOptional.isPresent()) {
            return ExchangeRequestDetailsResponse.buildFromExchangeRequestEntity(exchangeRequestOptional.get(), userId);
        } else {
            throw new ResourceNotFoundException("Exchange Request not found","id", exchangeRequestId );
        }
    }

    @Override
    public ExchangeRequestDetailsResponse completeExchangeRequest(Long exchangeRequestId, Long userId){
        // TODO - Validate if user can do this operation
        exchangeRequestRepository.updateStatusOfRequestForId(ExchangeRequestStatus.COMPLETED.getValue(), exchangeRequestId);
        Optional<ExchangeRequestEntity> exchangeRequestOptional = exchangeRequestRepository.findById(exchangeRequestId);
        if (exchangeRequestOptional.isPresent()) {
            ExchangeRequestEntity exchangeRequest = exchangeRequestOptional.get();
            Long receiverUserId = exchangeRequest.getReceiverUser().getId();
            Long requesterUserId = exchangeRequest.getRequesterUser().getId();
            userService.rewardsUser(receiverUserId, ApplicationConstants.EXCHANGE_CREDITS);
            userService.rewardsUser(requesterUserId, ApplicationConstants.EXCHANGE_CREDITS);
            return ExchangeRequestDetailsResponse.buildFromExchangeRequestEntity(exchangeRequest, userId);
        } else {
            throw new ResourceNotFoundException("Exchange Request not found","id", exchangeRequestId );
        }
    }

    // 2 major things - Contention
    // Database indexing given the scale


}