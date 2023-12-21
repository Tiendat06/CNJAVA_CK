package com.java.repository;

import com.java.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from category c where c.category_id = :id")
    Category findCategoriesByCategory_id(@Param("id") int id);

    @Query("select c from category c where c.name = :name")
    Category getCategoryByName(@Param("name") String name);

    List<Category> findAll();
}
