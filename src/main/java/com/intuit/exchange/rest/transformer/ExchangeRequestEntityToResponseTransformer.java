package com.intuit.exchange.rest.transformer;

import com.intuit.exchange.rest.responseObjects.ExchangeRequestDetailsResponse;
import com.intuit.exchange.rest.entity.exchangeRequest.ExchangeRequestEntity;
import com.intuit.exchange.rest.entity.exchangeRequest.ExchangeRequestStatus;
import com.intuit.exchange.rest.entity.exchangeRequest.ExchangeRequestUserActions;
import com.intuit.exchange.rest.exception.ForbiddenActionException;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class ExchangeRequestEntityToResponseTransformer implements Transformer<ExchangeRequestEntity, ExchangeRequestDetailsResponse>{

    private final Long userId;
    @Override
    public ExchangeRequestDetailsResponse transform(ExchangeRequestEntity exchangeRequest) {
        Set<ExchangeRequestUserActions> possibleActions;
        if (Objects.equals(exchangeRequest.getReceiverUser().getId(), userId)) {
            ExchangeRequestStatus exchangeRequestStatus = ExchangeRequestStatus.fromValue(exchangeRequest.getStatus());
            possibleActions = exchangeRequestStatus.getNextActionsOnReceiverSide();
        } else if (Objects.equals(exchangeRequest.getRequesterUser().getId(), userId)) {
            ExchangeRequestStatus exchangeRequestStatus = ExchangeRequestStatus.fromValue(exchangeRequest.getStatus());
            possibleActions = exchangeRequestStatus.getNextActionsOnRequesterSide();
        } else {
            System.out.println("Invalid ExchangeRequest");
            // TODO - May be create constant , this is duplicate
            throw new ForbiddenActionException(String.valueOf(userId), "Exchange Request", exchangeRequest.getId());
        }

        return ExchangeRequestDetailsResponse.builder()
                .receiverUserId(exchangeRequest.getReceiverUser().getId())
                .requestedItemId(exchangeRequest.getRequestedItem().getId())
                .requestedItemName(exchangeRequest.getRequestedItem().getDescription())
                .offeredItemId(exchangeRequest.getOfferedItem().getId())
                .offeredItemName(exchangeRequest.getOfferedItem().getDescription())
                .requesterUserId(exchangeRequest.getRequesterUser().getId())
                .exchangeRequestId(exchangeRequest.getId())
                .possibleActions(possibleActions)
                .build();
    }
}

