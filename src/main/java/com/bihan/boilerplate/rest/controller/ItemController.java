package com.bihan.boilerplate.rest.controller;

import com.bihan.boilerplate.rest.controller.response.PageableAPISuccessResponseEntity;
import com.bihan.boilerplate.rest.dto.NewItemDetails;
import com.bihan.boilerplate.rest.entity.Item;
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
    public ResponseEntity<NewItemDetails> createNewItem(@RequestHeader("bearerToken") Long bearerToken,@RequestBody NewItemDetails newItemDetails) {
        itemService.addItem(newItemDetails, bearerToken);
        return new ResponseEntity<>(newItemDetails, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItemById(itemId);
        return new ResponseEntity<>("Item with ID " + itemId + " deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/exchange")
    public ResponseEntity<PageableAPISuccessResponseEntity<Item>> getExchangeableItems(@RequestHeader("bearerToken") Integer bearerToken,
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        // We will use the query params to serve paginated response
        // This will keep the latency of APIs in check

        // Will not expose Item entity directly, Rather a ItemResponse object will be
        // constructed from Item object to expose right things
        List<Item> items = itemService.getAll();
        return new ResponseEntity<>(new PageableAPISuccessResponseEntity<Item>(items, page, pageSize), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableAPISuccessResponseEntity<Item>> getMyItems(@RequestHeader("bearerToken") Integer bearerToken,
                                                                                       @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        // We will use the query params to serve paginated response
        // This will keep the latency of APIs in check

        // Will not expose Item entity directly, Rather a ItemResponse object will be
        // constructed from Item object to expose right things
        List<Item> items = itemService.getAll();
        return new ResponseEntity<>(new PageableAPISuccessResponseEntity<Item>(items, page, pageSize), HttpStatus.OK);
    }
}

// TODO
// 1. What should happen if you fail to create a resource.
// Possible Exceptions when inserting a new row in mysql.1. Sample API covering all scenarios
// 2. Best practices of a Spring project
// Get - if user hits post for that, input validation
// Post - input validation
// Compose the code such that it is easy to break it down into microservices.
// Create itemRefNo in the tale
