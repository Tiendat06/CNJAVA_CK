package com.java.repository;

import com.java.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, String> {
    // Custom query to retrieve email, password, and role based on email
}
