package com.intuit.exchange.rest.service;

import com.intuit.exchange.rest.responseObjects.ItemDetailsResponse;
import com.intuit.exchange.rest.dto.NewItemDetails;
import com.intuit.exchange.rest.entity.Item;

import java.util.List;

/**
 * This service is responsible for actions related to Item
 */
public interface ItemService {
    Item getItemById(Long itemId);

    ItemDetailsResponse addItem(NewItemDetails newItemDetails, Long userId);

    void deleteItemById(Long itemId);

    ItemDetailsResponse updateItem(Item item);

    List<ItemDetailsResponse> getAll();

    List<ItemDetailsResponse> getItemsForAUser(Long userId);

    void unListItems(Long... ids);

}
