package com.intuit.exchange.rest.controller.response;


import com.intuit.exchange.rest.entity.exchangeRequest.ExchangeRequestUserActions;
import lombok.*;

import java.util.Set;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ExchangeRequestDetailsResponse {

    private Long receiverUserId;

    private Long requestedItemId;

    private Long offeredItemId;

    private Long exchangeRequestId;

    private Long requesterUserId;

    private Set<ExchangeRequestUserActions> possibleActions;

}