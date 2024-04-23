package com.java.service.catalog.command;

import com.java.models.Product;
import com.java.service.catalog.ProductService;

public class EditProductCommand implements ICommand{
    private ProductService productService;
    private Product product;
    public EditProductCommand(ProductService service, Product p){
        this.productService = service;
        this.product = p;
    }
    @Override
    public void execute() {
        productService.updateProduct(product.getProduct_id(), product);
    }

    @Override
    public void undo() {
        productService.updateProduct(product.getProduct_id() , product);
    }

}
