package com.bihan.boilerplate.rest.dto;


import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestEntity;
import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestStatus;
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

    private Set<ExchangeRequestEntity.ExchangeRequestUserActions> possibleActions;

    // Builder class
    public static class Builder {
        private Long receiverUserId;
        private Long requestedItemId;
        private Long offeredItemId;
        private Set<ExchangeRequestEntity.ExchangeRequestUserActions> possibleActions;

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

        public Builder possibleActions(Set<ExchangeRequestEntity.ExchangeRequestUserActions> possibleActions) {
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

    public static ExchangeRequestDetailsResponse buildFrom(ExchangeRequest exchangeRequest, Long userId) {
        Set<ExchangeRequestEntity.ExchangeRequestUserActions> possibleActions = new HashSet<>();
        if (Objects.equals(exchangeRequest.getRequesterId(), userId)) {
            ExchangeRequestStatus exchangeRequestStatus = ExchangeRequestStatus.fromValue(exchangeRequest.getStatus());
            possibleActions = exchangeRequestStatus.getNextActionsOnRequesterSide();
        }

        if (Objects.equals(exchangeRequest.getReceiverId(), userId)) {
            ExchangeRequestStatus exchangeRequestStatus = ExchangeRequestStatus.fromValue(exchangeRequest.getStatus());
            possibleActions = exchangeRequestStatus.getNextActionsOnRequesterSide();
        }

        ExchangeRequestDetailsResponse response = new ExchangeRequestDetailsResponse.Builder()
                .receiverUserId(exchangeRequest.getReceiverId())
                .requestedItemId(exchangeRequest.getRequestedItemId())
                .offeredItemId(exchangeRequest.getOfferedItemId())
                .possibleActions(possibleActions)
                .build();
        return response;
    }

}