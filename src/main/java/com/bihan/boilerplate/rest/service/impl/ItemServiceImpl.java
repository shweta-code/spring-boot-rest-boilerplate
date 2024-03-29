package com.bihan.boilerplate.rest.service.impl;

import com.bihan.boilerplate.rest.dto.NewItemDetails;
import com.bihan.boilerplate.rest.exception.ResourceNotFoundException;
import com.bihan.boilerplate.rest.model.Category;
import com.bihan.boilerplate.rest.model.Item;
import com.bihan.boilerplate.rest.repository.CategoryRepository;
import com.bihan.boilerplate.rest.repository.ItemRepository;
import com.bihan.boilerplate.rest.service.CategoryService;
import com.bihan.boilerplate.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Item getItemById(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.isPresent()) {
            throw new ResourceNotFoundException("Item", "id", itemId.toString());
        }
        return item.get();
    }

    @Override
    // TODO - Check if this is required
    @Transactional
    public NewItemDetails addItem(NewItemDetails newItemDetails) {
        try {
            Long categoryId = newItemDetails.getCategoryId();
            Category category = categoryService.getCategoryById(categoryId);

            // We can use a transformer pattern here that transforms NewItemDetails into Item entity
            // The transformer will take newItemDetails and transform it to Item by mapping fields appropriately
            // and building Item
            Item.ItemBuilder itemBuilder = new Item.ItemBuilder().description(newItemDetails.getDescription())
                    .isListed(newItemDetails.isListed())
                    .category(category);
            Item item = itemBuilder.build();
            itemRepository.save(item);

            // We can create another DTO - NewItemCreatedDetails
            // which has id field and also takes into consideration the state that
            // we want to expose back to user
            return newItemDetails;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteItemById(Long itemId) {
        try {
            itemRepository.deleteById(itemId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item updateItem(Item item) {
        try {
           return itemRepository.save(item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> getAll() {
        try {
            return itemRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}