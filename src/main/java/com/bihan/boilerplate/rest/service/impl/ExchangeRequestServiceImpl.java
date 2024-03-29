package com.bihan.boilerplate.rest.service.impl;


import com.bihan.boilerplate.rest.dto.NewExchangeRequestDetails;
import com.bihan.boilerplate.rest.exception.ResourceNotFoundException;
import com.bihan.boilerplate.rest.model.ExchangeRequest;
import com.bihan.boilerplate.rest.model.Item;
import com.bihan.boilerplate.rest.repository.ExchangeRequestRepository;
import com.bihan.boilerplate.rest.repository.ItemRepository;
import com.bihan.boilerplate.rest.repository.UserRepository;
import com.bihan.boilerplate.rest.service.CategoryService;
import com.bihan.boilerplate.rest.service.ExchangeRequestService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

@Service
public class ExchangeRequestServiceImpl implements ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    // TODO - Can write UTs
    @Override
    @Transactional
    // TODO - Return actual request
    public NewExchangeRequestDetails createRequest(NewExchangeRequestDetails newExchangeRequestDetails){


        // Validate if requested item has other exchange requests as requestedItemId or offeredItemId
        // If there are any , respond back to the user throwing ResourceNotAvailableException

        // create a request which is in progress
        try {
            exchangeRequestRepository.createOrUpdateExchangeRequest(newExchangeRequestDetails.getRequesterUserId(), newExchangeRequestDetails.getReceiverUserId(),
                    newExchangeRequestDetails.getRequestedItemId(), newExchangeRequestDetails.getOfferedItemId(), ExchangeRequest.ExchangeRequestStatus.IN_PROGRESS.getValue());
        } catch (ConstraintViolationException ex){
            // throw Bad Request Exception
        }

        // read state of items - is_listed if both are true
        Item requestedItem = itemRepository.getById(newExchangeRequestDetails.getRequestedItemId());
        Item offeredItem = itemRepository.getById(newExchangeRequestDetails.getOfferedItemId());

        if(!requestedItem.isListed() || !offeredItem.isListed()){
            // throw new ResourceNotAvailableException();
        }

        // update state of items is_listed - false
        // catch optimistic lock exception here
        try {
            itemRepository.unListItems(newExchangeRequestDetails.getRequestedItemId(), newExchangeRequestDetails.getOfferedItemId());
        } catch (OptimisticLockException ex){
            // throw TryAgainLaterException();
            System.out.println("Optimistic Lock Exception thrown");
        }

        // update exchange request as created
        exchangeRequestRepository.createOrUpdateExchangeRequest(newExchangeRequestDetails.getRequesterUserId(), newExchangeRequestDetails.getReceiverUserId(),
                newExchangeRequestDetails.getRequestedItemId(), newExchangeRequestDetails.getOfferedItemId(), ExchangeRequest.ExchangeRequestStatus.CREATED.getValue());

        return newExchangeRequestDetails;
    }


}