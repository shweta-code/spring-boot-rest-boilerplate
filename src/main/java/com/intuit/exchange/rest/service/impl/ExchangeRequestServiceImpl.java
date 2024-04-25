package com.intuit.exchange.rest.service.impl;


import com.intuit.exchange.rest.constants.ApplicationConstants;
import com.intuit.exchange.rest.constants.EntityConstants;
import com.intuit.exchange.rest.constants.ErrorConstants;
import com.intuit.exchange.rest.responseObjects.ExchangeRequestDetailsResponse;
import com.intuit.exchange.rest.entity.Item;
import com.intuit.exchange.rest.entity.exchangeRequest.ExchangeRequestEntity;
import com.intuit.exchange.rest.entity.exchangeRequest.ExchangeRequestStatus;
import com.intuit.exchange.rest.exception.BadRequestException;
import com.intuit.exchange.rest.exception.ForbiddenActionException;
import com.intuit.exchange.rest.exception.ResourceNotFoundException;
import com.intuit.exchange.rest.model.request.NewExchangeRequestDetails;
import com.intuit.exchange.rest.repository.ExchangeRequestRepository;
import com.intuit.exchange.rest.service.ExchangeRequestService;
import com.intuit.exchange.rest.service.ItemService;
import com.intuit.exchange.rest.service.UserService;
import com.intuit.exchange.rest.transformer.ExchangeRequestEntityToResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    @Transactional
    public ExchangeRequestDetailsResponse createRequest(NewExchangeRequestDetails newExchangeRequestDetails, Long requesterUserId){

        Item offeredItem = itemService.getItemById(newExchangeRequestDetails.getOfferedItemId());
        Item requestedItem = itemService.getItemById(newExchangeRequestDetails.getRequestedItemId());

        if (!Objects.equals(offeredItem.getOwnerUser().getId(), requesterUserId)) {
            throw new BadRequestException("The offered item must belong to the requester user.");
        }
        if (Objects.equals(requestedItem.getOwnerUser().getId(), requesterUserId)) {
            throw new BadRequestException("The requested item does belongs  to the requester user");
        }

        if(!requestedItem.isListed()){
            throw new ResourceNotFoundException("Item", EntityConstants.ENTITY_FIELD_ID, requestedItem.getId() );
        }
        if(!offeredItem.isListed()){
            throw new ResourceNotFoundException("Item", EntityConstants.ENTITY_FIELD_ID, offeredItem.getId() );
        }

        ExchangeRequestEntity inProgressExchangeRequest = new ExchangeRequestEntity.Builder()
                .withRequesterUser(offeredItem.getOwnerUser())
                .withReceiverUser(requestedItem.getOwnerUser())
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

        return new ExchangeRequestEntityToResponseTransformer(requesterUserId).transform(exchangeRequestEntity);
    }


    @Override
    public List<ExchangeRequestDetailsResponse> getExchangeRequestsForAUser(Long userId){
        List<ExchangeRequestEntity> exchangeRequests = exchangeRequestRepository.findExchangeRequests(userId);
        List<ExchangeRequestDetailsResponse> exchangeRequestDetailsResponses = new ArrayList<>();
        ExchangeRequestEntityToResponseTransformer exchangeRequestEntityToResponseTransformer = new ExchangeRequestEntityToResponseTransformer(userId);
        for (ExchangeRequestEntity exchangeRequest : exchangeRequests) {
            ExchangeRequestDetailsResponse exchangeRequestDetailsResponse = exchangeRequestEntityToResponseTransformer.transform(exchangeRequest);
            exchangeRequestDetailsResponses.add(exchangeRequestDetailsResponse);
        }
        return exchangeRequestDetailsResponses;
    }

    @Override
    public ExchangeRequestDetailsResponse acceptExchangeRequest(Long exchangeRequestId, Long userId){
        Optional<ExchangeRequestEntity> exchangeRequestEntityOptional = exchangeRequestRepository.findById(exchangeRequestId);
        if (exchangeRequestEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException(EntityConstants.ENTITY_NAME_EXCHANGE_REQUEST, EntityConstants.ENTITY_FIELD_ID, exchangeRequestId);
        } else {
            ExchangeRequestEntity exchangeRequest = exchangeRequestEntityOptional.get();
            if (!Objects.equals(exchangeRequest.getReceiverUser().getId(), userId)) {
                throw new ForbiddenActionException(String.valueOf(userId), "Exchange Request", exchangeRequestId);
            }
            // TODO - Create constant
            if (!Objects.equals(exchangeRequest.getStatus(), ExchangeRequestStatus.CREATED.getValue())) {
                throw new BadRequestException(ErrorConstants.BAD_REQUEST_ERROR_MESSAGE);
            }

            exchangeRequest.setStatus(ExchangeRequestStatus.ACCEPTED.getValue());
            ExchangeRequestEntity savedExchangeRequestEntity = exchangeRequestRepository.save(exchangeRequest);
            return new ExchangeRequestEntityToResponseTransformer(userId).transform(savedExchangeRequestEntity);
        }
    }

    @Override
    public ExchangeRequestDetailsResponse completeExchangeRequest(Long exchangeRequestId, Long userId){
        Optional<ExchangeRequestEntity> exchangeRequestEntityOptional = exchangeRequestRepository.findById(exchangeRequestId);
        if (exchangeRequestEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException(EntityConstants.ENTITY_NAME_EXCHANGE_REQUEST, EntityConstants.ENTITY_FIELD_ID, exchangeRequestId);
        } else {
            ExchangeRequestEntity exchangeRequest = exchangeRequestEntityOptional.get();
            if (!Objects.equals(exchangeRequest.getRequesterUser().getId(), userId)) {
                throw new ForbiddenActionException(String.valueOf(userId), "Exchange Request", exchangeRequestId);
            }
            if (!Objects.equals(exchangeRequest.getStatus(), ExchangeRequestStatus.ACCEPTED.getValue())) {
                throw new BadRequestException(ErrorConstants.BAD_REQUEST_ERROR_MESSAGE);
            }

            exchangeRequest.setStatus(ExchangeRequestStatus.COMPLETED.getValue());
            ExchangeRequestEntity savedExchangeRequestEntity = exchangeRequestRepository.save(exchangeRequest);
            rewardCreditsToUserForExchangeRequest(exchangeRequest);

            return new ExchangeRequestEntityToResponseTransformer(userId).transform(savedExchangeRequestEntity);
        }
    }


    private void rewardCreditsToUserForExchangeRequest(ExchangeRequestEntity exchangeRequest){
        Long receiverUserId = exchangeRequest.getReceiverUser().getId();
        Long requesterUserId = exchangeRequest.getRequesterUser().getId();
        userService.rewardsUser(receiverUserId, ApplicationConstants.EXCHANGE_CREDITS);
        userService.rewardsUser(requesterUserId, ApplicationConstants.EXCHANGE_CREDITS);
    }

}