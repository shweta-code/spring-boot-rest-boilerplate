package com.intuit.exchange.rest.responseObjects;


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

    private String requestedItemName;

    private Long offeredItemId;

    private String offeredItemName;

    private Long exchangeRequestId;

    private Long requesterUserId;

    private Set<ExchangeRequestUserActions> possibleActions;

}