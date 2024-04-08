package com.bihan.boilerplate.rest.repository;

import com.bihan.boilerplate.rest.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {

    //TODO - Implement a method to get items by category id.
    // For example - musical instruments if musical instrument is asked

    // TODO - Create a constant for query
    @Modifying
    @Query(value = "Update Item item set item.isListed = false " +
            "where item.id in (:itemIds)")
    int unListItems(Long... itemIds);

    @Query(nativeQuery = true, value = "select id, description from item where owner_id != ?1 order by id limit ?2 OFFSET ?3")
    void findInstrumentsForExchange(Long userId, int pageSize, int offset);

    // Do Logging for debugging purposes
    // Unuthorized access
    // Rate Limiting
}