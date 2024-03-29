package com.bihan.boilerplate.rest.repository;

import com.bihan.boilerplate.rest.model.Item;
import com.bihan.boilerplate.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

}
