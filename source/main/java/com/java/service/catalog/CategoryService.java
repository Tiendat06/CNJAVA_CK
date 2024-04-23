package com.java.service.catalog;

import com.java.models.Category;
import com.java.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    public CategoryRepository categoryRepository;

    public Category getCategoryNameByCategoryId(int id){
        return categoryRepository.findCategoriesByCategory_id(id);
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name){
        return categoryRepository.getCategoryByName(name);
    }

}
