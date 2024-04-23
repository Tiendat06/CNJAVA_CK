package com.java.service.catalog.command;

import com.java.models.Product;
import com.java.service.catalog.ProductService;

public class DeleteProductCommand implements ICommand{

    private ProductService productService;
    private Product product;
    public DeleteProductCommand(ProductService service, Product p){
        this.productService = service;
        this.product = p;
    }
    @Override
    public void execute() {
        productService.deleteProduct(product.getProduct_id());
    }

    @Override
    public void undo() {
        productService.addProduct(product);
    }
}
