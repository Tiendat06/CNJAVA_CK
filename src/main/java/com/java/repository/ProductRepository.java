package com.java.repository;

import com.java.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT pro " +
            "FROM product pro WHERE pro.product_name like %:character%")
    Page<Product> searchInHome(@Param("character") String character, Pageable pageable);

    @Query("select max (product_id) from product")
    String maxID();

    @Query("select p.product_id, p.product_name, p.product_img, p.quantity_stock, p.retail_price " +
            "from product p, category c where p.category_id = c.category_id and c.category_id = :categoryId")
    Page<Object[]> getAllProductOrderByCategory(Pageable pageable, @Param("categoryId") int categoryId);

    default void updateProduct(String id, Product product){
        findById(id).ifPresent(p -> {
            p.setProduct_name(product.getProduct_name());
            p.setCategory_id(product.getCategory_id());
            p.setProduct_img(product.getProduct_img());
            p.setProduct_price(product.getProduct_price());
            p.setRetail_price(product.getRetail_price());
            p.setQuantity_stock(product.getQuantity_stock());
            p.setDescription(product.getDescription());
            save(p);
        });
    }
//    Page<Product> findAll(Pageable pageable);
}
