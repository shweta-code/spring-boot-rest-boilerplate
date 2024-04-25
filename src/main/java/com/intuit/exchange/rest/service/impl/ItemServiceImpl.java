package com.intuit.exchange.rest.service.impl;

import com.intuit.exchange.rest.constants.EntityConstants;
import com.intuit.exchange.rest.responseObjects.ItemDetailsResponse;
import com.intuit.exchange.rest.dto.NewItemDetails;
import com.intuit.exchange.rest.entity.Category;
import com.intuit.exchange.rest.entity.Item;
import com.intuit.exchange.rest.entity.User;
import com.intuit.exchange.rest.exception.ResourceNotFoundException;
import com.intuit.exchange.rest.repository.ItemRepository;
import com.intuit.exchange.rest.service.CategoryService;
import com.intuit.exchange.rest.service.ItemService;
import com.intuit.exchange.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Override
    public Item getItemById(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new ResourceNotFoundException("Item", "id", itemId.toString());
        }
        return item.get();
    }

    @Override
    public ItemDetailsResponse addItem(NewItemDetails newItemDetails, Long userId) {
        Long categoryId = newItemDetails.getCategoryId();
        Category category = categoryService.getCategoryById(categoryId);
        User ownerUser = userService.getUserEntityById(userId);

        // We can use a transformer pattern here that transforms NewItemDetails into Item entity
        // The transformer will take newItemDetails and transform it to Item by mapping fields appropriately
        // and building Item
        Item.ItemBuilder itemBuilder = new Item.ItemBuilder().description(newItemDetails.getDescription())
                .isListed(newItemDetails.isListed())
                .category(category)
                .ownerUser(ownerUser);
        Item item = itemBuilder.build();
        Item savedItem = itemRepository.save(item);

        return ItemDetailsResponse.buildFromItemEntity(savedItem);
    }

    @Override
    public void deleteItemById(Long itemId) {
        //TODO - May be we can first check here is item exists or not
        itemRepository.deleteById(itemId);
    }

    @Override
    public ItemDetailsResponse updateItem(Item item) {
        Item savedItem = itemRepository.save(item);
        return ItemDetailsResponse.buildFromItemEntity(savedItem);
    }

    @Override
    public List<ItemDetailsResponse> getAll() {
        List<Item> allItems = itemRepository.findAll(sortByIdAsc());
        List<ItemDetailsResponse> responseList = new ArrayList<>();
        for (Item savedItem : allItems) {
            responseList.add(ItemDetailsResponse.buildFromItemEntity(savedItem));
        }
        return responseList;
    }

    @Override
    public List<ItemDetailsResponse> getItemsForAUser(Long userId) {
        List<Item> items = itemRepository.itemsOfAUser(userId);
        List<ItemDetailsResponse> responseList = new ArrayList<>();
        for (Item savedItem : items) {
            responseList.add(ItemDetailsResponse.buildFromItemEntity(savedItem));
        }
        return responseList;
    }

    @Override
    public void unListItems(Long... itemIds) {
        itemRepository.unListItems(itemIds);
    }

    // TODO - Can create  Sort Factory that expose a static Sort factory method, that provides these sorts
    private Sort sortByIdAsc() {
        return Sort.by(Sort.Direction.ASC, EntityConstants.ENTITY_FIELD_ID);
    }

}