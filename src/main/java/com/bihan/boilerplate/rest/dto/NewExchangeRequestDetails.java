package com.bihan.boilerplate.rest.dto;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewExchangeRequestDetails {

    // TODO - JSON Writing standards
    private Long requesterUserId;

    private Long receiverUserId;

    private Long requestedItemId;

    private Long offeredItemId;
}