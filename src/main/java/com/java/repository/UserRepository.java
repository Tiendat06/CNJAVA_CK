package com.java.repository;

import com.java.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select max(user_id) from user ")
    String maxID();

    @Query("select u.account_id from user u where u.user_id = :user_id")
    String accID(@Param("user_id") String user_id);

    Page<User> findAll(Pageable pageable);

    default void updateUser(String id, User user){
        findById(id).ifPresent(u -> {
            u.setFirst_name(user.getFirst_name());
            u.setLast_name(user.getLast_name());
            u.setEmail(user.getEmail());
            u.setPhone_number(user.getPhone_number());
            u.setAddress(user.getAddress());
            u.setBirthday(user.getBirthday());
            u.setGender(user.getGender());

            save(u);
        });
    }

    User findByEmail(String email);

}
