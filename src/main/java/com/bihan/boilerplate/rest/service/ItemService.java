package com.bihan.boilerplate.rest.service;

import com.bihan.boilerplate.rest.dto.NewItemDetails;
import com.bihan.boilerplate.rest.entity.Item;

import java.util.List;

public interface ItemService {
    Item getItemById(Long itemId);

    NewItemDetails addItem(NewItemDetails newItemDetails);

    void deleteItemById(Long itemId);

    Item updateItem(Item item);

    List<Item> getAll();

    // TODO- Accept varargs
    void unListItemsForExchangeRequest(Long requestedItemId, Long offeredItemId);
}
