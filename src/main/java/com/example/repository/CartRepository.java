package com.example.repository;

import com.example.model.Cart;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;
//import com.example.data.*;

@Repository
@SuppressWarnings("rawtypes")
public class CartRepository extends MainRepository<Cart> {
    public CartRepository(){}

    public Cart addCart(Cart cart) {
        save(cart, "carts.json");
        return cart;
    }
    public ArrayList<Cart> getCarts() {
        return load(Cart.class, "carts.json");
    }
    public Cart getCartById(UUID cartId) {
        return getCarts().stream().filter(cart -> cart.getId().equals(cartId)).findFirst().orElse(null);
    }
    public Cart getCartById(UUID cartId){
        return getCarts().stream().filter(cart -> cart.getId().equals(cartId)).findFirst().orElse(null);
    }
    public Cart getCartByUserId(UUID userId) {
        return getCarts().stream().filter(cart -> cart.getUserId().equals(userId)).findFirst().orElse(null);
    }
    public void addProductToCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = getCarts();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cart.addProduct(product);
                saveAll(carts, "carts.json");
                return;
            }
        }
    }
    public void deleteProductFromCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = getCarts();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cart.removeProduct(product);
                saveAll(carts, "carts.json");
                return;
            }
        }
    }
    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = getCarts();
        carts.removeIf(cart -> cart.getId().equals(cartId));
        saveAll(carts, "carts.json");
    }
}