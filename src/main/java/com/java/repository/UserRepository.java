package com.java.repository;

import com.java.models.Account;
import com.java.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, String> {


    @Query("select max(user_id) from user ")
    String maxID();

    @Query("select u from user u where u.user_id = :user_id")
    User findUserByID(@Param("user_id") String user_id);

    @Query("select u from user u where u.account_id = :account_id")
    User findUserByAccount_id(@Param("account_id") String account_id);

    @Query("select u.first_name, u.last_name, u.gender, u.birthday, u.email, u.address, u.phone_number, u.image, " +    // 7
            "r.role_id, r.role_name, u.user_id from user u, account a, role r where u.account_id = a.account_id and a.role_id = r.role_id " +
            "and u.user_id = :user_id")    // 9
    List<Object[]> findUserProfileByUserId(@Param("user_id") String user_id);
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

    default void updateProfile(String id, User user){
        findById(id).ifPresent(u -> {
            u.setFirst_name(user.getFirst_name());
            u.setLast_name(user.getLast_name());
            u.setEmail(user.getEmail());
            u.setPhone_number(user.getPhone_number());
            u.setAddress(user.getAddress());
            u.setBirthday(user.getBirthday());
            u.setGender(user.getGender());
            u.setImage(user.getImage());

            save(u);
        });
    }

    User findByEmail(String email);

}