package com.intuit.exchange.rest.repository;

import com.intuit.exchange.rest.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Modifying
    @Query(value = "Update Item item set item.isListed = false " +
            "where item.id in (:itemIds)")
    int unListItems(Long... itemIds);

    @Query(nativeQuery = true, value = "select id, description from item where owner_id != ?1 order by id limit ?2 OFFSET ?3")
    void findInstrumentsForExchange(Long userId, int pageSize, int offset);

    @Query(value = "Select item from Item item where item.ownerUser.id = :userId")
    List<Item> itemsOfAUser(Long userId);

    // TODO -Highlight - Unauthorized access
    // TODO - Highlight - Rate Limiting
    // TODO - Highlight Transformer Pattern

    // TODO - Create a constant for query
    // TODO - Delete Category, student, course controller
    // TODO - We can write an AdminItemController
    // TODO - Db indexing, Created indexes
    // TODO - Constructor Injection
    // TODO - Can write UTs, Integration test for optimistic locking
    // TODO - validate input
    // TODO - Write in doc , for Authorizaation , we will do API level access
    // TODO - Include roles table in db schema
    // TODO - Data size validation , min-max validation
    // TODO - Input validations

    // TODO - Add other methods to change the status of ExchangeRequest
    // TODO - Add some code for pessimistic locking at abstract level
    // TODO - Document which parts of the application will be good with pessimistic locking
    // TODO - and optimistic locking
    // TODO -

    // TODO
// 1. What should happen if you fail to create a resource.
// Possible Exceptions when inserting a new row in mysql.
// 2. Best practices of a Spring project
// 3. Check for validity of inputs and validity before inserting into database in one sample API
// TODO - Create exchnage request ref no n table exchnageRequest, split exchangeRequest in three tables
// TODO - Use Log4j, Swagge
}