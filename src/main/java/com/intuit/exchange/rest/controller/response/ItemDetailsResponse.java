package com.intuit.exchange.rest.controller.response;


import com.intuit.exchange.rest.entity.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class ItemDetailsResponse {

    private Long categoryId;

    private String description;

    private boolean isListed;

    private Long userId;

    private Long id;

    public static ItemDetailsResponse buildFromItemEntity(Item item) {
        return ItemDetailsResponse.builder()
                .categoryId(item.getCategory().getId())
                .description(item.getDescription())
                .isListed(item.isListed())
                .userId(item.getOwnerUser().getId())
                .id(item.getId())
                .build();
    }

}