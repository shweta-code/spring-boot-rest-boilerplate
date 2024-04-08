package com.bihan.boilerplate.rest.dto;


import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestEntity;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestStatus;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestUserActions;
import com.bihan.boilerplate.rest.exception.BadRequestException;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExchangeRequestDetailsResponse {

    private Long receiverUserId;

    private Long requestedItemId;

    private Long offeredItemId;

    private Set<ExchangeRequestUserActions> possibleActions;

    // Builder class
    public static class Builder {
        private Long receiverUserId;
        private Long requestedItemId;
        private Long offeredItemId;
        private Set<ExchangeRequestUserActions> possibleActions;

        // Setter methods for builder
        public Builder receiverUserId(Long receiverUserId) {
            this.receiverUserId = receiverUserId;
            return this;
        }

        public Builder requestedItemId(Long requestedItemId) {
            this.requestedItemId = requestedItemId;
            return this;
        }

        public Builder offeredItemId(Long offeredItemId) {
            this.offeredItemId = offeredItemId;
            return this;
        }

        public Builder possibleActions(Set<ExchangeRequestUserActions> possibleActions) {
            this.possibleActions = possibleActions;
            return this;
        }

        // Build method to create ExchangeRequestDetailsResponse instance
        public ExchangeRequestDetailsResponse build() {
            ExchangeRequestDetailsResponse response = new ExchangeRequestDetailsResponse();
            response.receiverUserId = this.receiverUserId;
            response.requestedItemId = this.requestedItemId;
            response.offeredItemId = this.offeredItemId;
            response.possibleActions = this.possibleActions;
            return response;
        }
    }

    // TODO - remove this
    public static ExchangeRequestDetailsResponse buildFromExchangeRequest(ExchangeRequest exchangeRequest, Long userId) {
        Set<ExchangeRequestUserActions> possibleActions = new HashSet<>();
        if (Objects.equals(exchangeRequest.getRequesterId(), userId)) {
            ExchangeRequestStatus exchangeRequestStatus = ExchangeRequestStatus.fromValue(exchangeRequest.getStatus());
            possibleActions = exchangeRequestStatus.getNextActionsOnRequesterSide();
        } else if (Objects.equals(exchangeRequest.getReceiverId(), userId)) {
            ExchangeRequestStatus exchangeRequestStatus = ExchangeRequestStatus.fromValue(exchangeRequest.getStatus());
            possibleActions = exchangeRequestStatus.getNextActionsOnReceiverSide();
        } else {
            //TODO - Log4j
            System.out.println("Invalid ExchangeRequest");
            // TODO - Throw Forbidden Exception
        }

        ExchangeRequestDetailsResponse response = new ExchangeRequestDetailsResponse.Builder()
                .receiverUserId(exchangeRequest.getReceiverId())
                .requestedItemId(exchangeRequest.getRequestedItemId())
                .offeredItemId(exchangeRequest.getOfferedItemId())
                .possibleActions(possibleActions)
                .build();
        return response;
    }

    public static ExchangeRequestDetailsResponse buildFromExchangeRequestEntity(ExchangeRequestEntity exchangeRequest, Long userId) {
        Set<ExchangeRequestUserActions> possibleActions = new HashSet<>();
        if (Objects.equals(exchangeRequest.getReceiverUser().getId(), userId)) {
            ExchangeRequestStatus exchangeRequestStatus = ExchangeRequestStatus.fromValue(exchangeRequest.getStatus());
            possibleActions = exchangeRequestStatus.getNextActionsOnRequesterSide();
        } else if (Objects.equals(exchangeRequest.getRequesterUser().getId(), userId)) {
            ExchangeRequestStatus exchangeRequestStatus = ExchangeRequestStatus.fromValue(exchangeRequest.getStatus());
            possibleActions = exchangeRequestStatus.getNextActionsOnReceiverSide();
        } else {
            System.out.println("Invalid ExchangeRequest");
            // TODO - Throw Forbidden Exception
        }

        ExchangeRequestDetailsResponse response = new ExchangeRequestDetailsResponse.Builder()
                .receiverUserId(exchangeRequest.getReceiverUser().getId())
                .requestedItemId(exchangeRequest.getRequestedItem().getId())
                .offeredItemId(exchangeRequest.getOfferedItem().getId())
                .possibleActions(possibleActions)
                .build();
        return response;
    }

}