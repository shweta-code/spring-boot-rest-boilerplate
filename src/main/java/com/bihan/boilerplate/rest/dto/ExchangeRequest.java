package com.bihan.boilerplate.rest.dto;


import com.bihan.boilerplate.rest.entity.exchangeRequest.ExchangeRequestEntity;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExchangeRequest {

    // TODO - JSON Writing standards
    private Long id;

    private Long requesterId;

    private Long receiverId;

    private Long requestedItemId;

    private Long offeredItemId;

    private String status;

}