package com.intuit.exchange.rest.model.request;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewExchangeRequestDetails {

    private Long requestedItemId;

    private Long offeredItemId;
}