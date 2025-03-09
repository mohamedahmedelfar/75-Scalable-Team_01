package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class ProductRepository extends MainRepository<Product> {

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/products.json";
    }

    @Override
    protected Class<Product[]> getArrayType() {
        return Product[].class;
    }

    public ProductRepository() {}

    public Product addProduct(Product product){
        save(product);
        return product;
    }
    public ArrayList<Product> getProducts(){
        return findAll();
    }

    public Product getProductById(UUID productId){
        ArrayList<Product> products = findAll();
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst().orElse(null);
    }

    public Product updateProduct(UUID productId, String newName, double newPrice) {
        Product productFound = getProductById(productId);
        if (productFound == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }
        productFound.setName(newName);
        productFound.setPrice(newPrice);
        System.out.println(productFound.getPrice());
        save(productFound);
        return productFound;
    }
    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        for (UUID productId : productIds) {
            Product product = getProductById(productId);
            if (product != null) {
                double currentPrice = product.getPrice();
                double discountedPrice = currentPrice * (1 - (discount / 100));
                System.out.println(discountedPrice);
                Product updated = updateProduct(productId, product.getName(), discountedPrice);
                System.out.println(updated.getPrice());
            }
        }
    }
    public void deleteProductById(UUID productId) {
        ArrayList<Product> products = findAll();
        boolean removed = products.removeIf(product -> product.getId().equals(productId));
        if (removed==true) {
            saveAll(products);
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }
    }










}