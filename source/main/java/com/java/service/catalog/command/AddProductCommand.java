package com.java.service.catalog.command;

import com.java.models.Product;
import com.java.service.catalog.ProductService;

public class AddProductCommand implements ICommand {

    private ProductService productService;
    private Product product;
    public AddProductCommand(ProductService service, Product p){
        this.productService = service;
        this.product = p;
    }

    @Override
    public void execute() {
        productService.addProduct(product);
    }

    @Override
    public void undo() {
        String id = product.getProduct_id();
        productService.deleteProduct(id);
    }
}
