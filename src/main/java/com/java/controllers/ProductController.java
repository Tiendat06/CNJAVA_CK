package com.java.controllers;

import com.java.models.Category;
import com.java.models.MyUserDetail;
import com.java.models.Product;
import com.java.service.catalog.CategoryService;
import com.java.service.catalog.ProductService;
import com.java.service.image.ImageService;
import com.java.service.order.OrderDetailsService;
import com.java.service.order.OrderFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
//import org.apache.commons.codec.binary.Base64;
import java.util.List;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    public ProductService productService;
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public ImageService imageService;
    @Autowired
    public OrderDetailsService orderDetailsService;
    @Autowired
    public OrderFacade orderFacade;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "product");
        return "redirect:/product/1";
    }

    @PostMapping("/add")
    public void addProduct_POST(@RequestParam("img-add") MultipartFile imageFile ,HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        System.out.println("hiiii");
        String id = productService.AUTO_PRO_ID();
        String name = req.getParameter("name-add");
        String des = req.getParameter("des-add");
        int quan = Integer.parseInt(req.getParameter("quan-add"));
        float import_price = Float.parseFloat(req.getParameter("Iprice-add"));
        int category_id = Integer.parseInt(req.getParameter("type"));

        byte[] img = imageFile.getBytes();
        imageService.saveImage(img, id+".png");
        float retail_price = Float.parseFloat(req.getParameter("Rprice-add"));
        long currentTime = System.currentTimeMillis();
        Timestamp date_created = new Timestamp(currentTime);
        byte[] barcode = productService.convertBarCodeImgToByte(id);
        imageService.saveImage(barcode, id+"_barcode.png");

        productService.productRepository.save(new Product(id, name, des, quan,
                import_price, category_id, id+".png", retail_price, date_created, id+"_barcode.png"));

        resp.sendRedirect("/product/1");
    }

    @PostMapping("/edit")
    public void updateProduct_POST(@RequestParam("img-edit") MultipartFile imgFile ,HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getParameter("type-edit").equals("none")){
            resp.sendRedirect("/product/1");
        }

        String id = req.getParameter("productIdEdit");
        String name = req.getParameter("name-edit");
        int type = Integer.parseInt(req.getParameter("type-edit"));
        System.out.println(type);
        if (!imgFile.isEmpty()){
            byte[] img = imgFile.getBytes();
            imageService.saveImage(img, id+".png");
        }
        float import_price = Float.parseFloat(req.getParameter("Iprice-edit"));
        float retail_price = Float.parseFloat(req.getParameter("Rprice-edit"));
        int quan = Integer.parseInt(req.getParameter("quan-edit"));
        String des = req.getParameter("des-add");

        byte[] barcode = productService.convertBarCodeImgToByte(id);
        imageService.saveImage(barcode, id+"_barcode.png");

        productService.updateProduct(id, new Product(id, name, des, quan,
                import_price, type, id+".png", retail_price, null, id+"_barcode.png"));

        resp.sendRedirect("/product/1");
    }

    @PostMapping("/delete")
    public void deleteProduct_POST(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("productIdDelete");

        if (orderFacade.getAllOrderDetails(id).isEmpty()){
//            System.out.println("noooo");
            productService.deleteProduct(id);
        }
        resp.sendRedirect("/product/1");
    }

    @GetMapping("/{pageNo}")
    public String getUserPagination(@PathVariable int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize, Model model){
        Page<Product> productList = productService.getAllProductPagination(pageNo - 1, pageSize);
        model.addAttribute("content", "product");
        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", new ProductUtils());
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userImg", myUserDetail.getCombinedUser().getUser().getImage());

        List<Category> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryListModal", categoryList);
        model.addAttribute("imgUtil", new ProductUtils());

        return "index";
    }

    @GetMapping("/{pageNo}/ajax")
    public String getUserPagination_AJAX(@PathVariable int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize, Model model){
        Page<Product> productList = productService.getAllProductPagination(pageNo - 1, pageSize);
        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", new ProductUtils());
        return "/product/product_list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportProductData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> productList = productService.getAllProducts();
        String extraChoice = req.getParameter("id-export-product");
        System.out.println(extraChoice);
        System.out.println("hello world");

         return productService.exportProductData(productList);
    }

    public class ProductUtils{
        public String getCategory(int id){
            return categoryService.getCategoryNameByCategoryId(id).getName();
        }

        public String convertToBase64(byte[] imageData) {
            return Base64.getEncoder().encodeToString(imageData);
        }
    }
}
