package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public abstract class CartRepository extends MainRepository<Cart> {
    @Override
    protected String getDataPath() {
        return "";
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return null;
    }

    public CartRepository(){}

    public Cart addCart(Cart cart) {
        save(cart);
        return cart;
    }
    public ArrayList<Cart> getCarts() {
        return findAll();
    }
    public Cart getCartById(UUID cartId){
        return getCarts().stream().filter(cart -> cart.getId().equals(cartId)).findFirst().orElse(null);
    }
    public Cart getCartByUserId(UUID userId) {
        return getCarts().stream().filter(cart -> cart.getUserId().equals(userId)).findFirst().orElse(null);
    }
    public abstract void addProductToCart(UUID cartId, Product product);
    public abstract void deleteProductFromCart(UUID cartId, Product product);
    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = getCarts();
        carts.removeIf(cart -> cart.getId().equals(cartId));
        saveAll(carts);
    }
}