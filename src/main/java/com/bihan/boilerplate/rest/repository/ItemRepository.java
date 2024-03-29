package com.bihan.boilerplate.rest.repository;

import com.bihan.boilerplate.rest.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {

    //TODO - Implement a method to get items by category id.
    // For example - musical instruments if musical instrument is asked
}
