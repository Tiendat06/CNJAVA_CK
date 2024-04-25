package com.java.controllers.Decorative;

import com.java.models.Product;
import com.java.models.User;

import java.util.List;

public interface Export {
     byte[] export(List<Product> productList);
}
