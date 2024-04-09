package com.intuit.exchange.rest.controller;

import com.intuit.exchange.rest.controller.response.ItemDetailsResponse;
import com.intuit.exchange.rest.controller.response.PageableAPISuccessResponseEntity;
import com.intuit.exchange.rest.dto.NewItemDetails;
import com.intuit.exchange.rest.entity.Item;
import com.intuit.exchange.rest.service.ItemService;
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
    public ResponseEntity<Item> getItemById(@RequestHeader("bearerToken") Long bearerToken, @PathVariable("itemId") Long itemId) {
        return new ResponseEntity<>(itemService.getItemById(itemId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDetailsResponse> createNewItem(@RequestHeader("bearerToken") Long bearerToken,@RequestBody NewItemDetails newItemDetails) {
        ItemDetailsResponse itemDetailsResponse = itemService.addItem(newItemDetails, bearerToken);
        return new ResponseEntity<>(itemDetailsResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{itemId}")
    public ResponseEntity<String> deleteItem(@RequestHeader("bearerToken") Long bearerToken, @PathVariable("itemId") Long itemId) {
        itemService.deleteItemById(itemId);
        return new ResponseEntity<>("Item with ID " + itemId + " deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<PageableAPISuccessResponseEntity<ItemDetailsResponse>> getMyItems(@RequestHeader("bearerToken") Long bearerToken,
                                                                                       @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        // We will use the query params to serve paginated response
        // This will keep the latency of APIs in check

        List<ItemDetailsResponse> items = itemService.getItemsForAUser(bearerToken);
        return new ResponseEntity<>(new PageableAPISuccessResponseEntity<ItemDetailsResponse>(items, page, pageSize, items.size()), HttpStatus.OK);
    }
}

// TODO

// 2. Best practices of a Spring project
// Get - if user hits post for that, input validation
// Post - input validation
// Compose the code such that it is easy to break it down into microservices.
// Create itemRefNo in the tale
