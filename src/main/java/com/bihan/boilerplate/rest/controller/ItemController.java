package com.bihan.boilerplate.rest.controller;

import com.bihan.boilerplate.rest.dto.NewItemDetails;
import com.bihan.boilerplate.rest.model.Item;
import com.bihan.boilerplate.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.getItemById(itemId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NewItemDetails> createNewItem(@RequestBody NewItemDetails newItemDetails) {
        itemService.addItem(newItemDetails);
        return new ResponseEntity<>(newItemDetails, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItemById(itemId);
        return new ResponseEntity<>("Item with ID " + itemId + " deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(itemService.getAll(), HttpStatus.OK);
    }
}

// TODO
// 1. What should happen if you fail to create a resource.
// Possible Exceptions when inserting a new row in mysql.1. Sample API covering all scenarios
// 2. Best practices of a Spring project
// Get - if user hits post for that, input validation
// Post - input validation
// Compose the code such that it is easy to break it down into microservices.
