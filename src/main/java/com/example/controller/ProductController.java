package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @GetMapping("/")
    public ArrayList<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable UUID productId){
        return productService.getProductById(productId);
    }

    @PutMapping("/update/{productId}")
    public Product updateProduct(@PathVariable UUID productId, @RequestBody(required = false) Map<String,Object> body){
        Product product = productService.getProductById(productId);
        if(product == null){
            System.out.println("Product not found");
            return null;
        }
        if(body==null|| body.isEmpty()){
            System.out.println("body is null");
            return null;
        }
        System.out.println("Received body: " + body);
        if(body.get("newName")==null){
            System.out.println("name is null");
            return null;
        }
        if(body.get("newPrice")==null){
            System.out.println("price is null");
            return null;
        }
        double newPrice;
        Object priceObject = body.get("newPrice");
        if (priceObject instanceof Number) {
            newPrice = ((Number) priceObject).doubleValue();
        } else if (priceObject instanceof String) {
            try {
                newPrice = Double.parseDouble((String) priceObject);
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
        String newName = body.get("newName").toString();
        return productService.updateProduct(productId, newName, newPrice);
    }

    @PutMapping("/applyDiscount")
    public String applyDiscount(@RequestParam double discount,@RequestBody ArrayList<UUID> productIds){
        productService.applyDiscount(discount,productIds);
        return "Discount applied successfully";
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProductById(@PathVariable UUID productId){
        productService.deleteProductById(productId);
        return "Product deleted successfully";
    }


}
