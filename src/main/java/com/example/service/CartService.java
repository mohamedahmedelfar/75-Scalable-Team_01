package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class CartService extends MainService<Cart>{

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (cart.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (cartRepository.getCartById(cart.getId()) != null) {
            throw new IllegalArgumentException("Cart with this ID already exists");
        }
        if (cartRepository.getCartByUserId(cart.getUserId()) != null) {
            throw new IllegalArgumentException("Cart with this user ID already exists");
        }
        return cartRepository.addCart(cart);
    }

    public ArrayList<Cart> getCarts() {
        return cartRepository.getCarts();
    }

    public Cart getCartById(UUID cartId) {
        return cartRepository.getCartById(cartId);
    }

    public Cart getCartByUserId(UUID userId) {
        return cartRepository.getCartByUserId(userId);
    }

    public void addProductToCart(UUID cartId, Product product) {
        if(getCartById(cartId)==null)
            throw new RuntimeException("Cart not found!");
        cartRepository.addProductToCart(cartId,product);
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        if(getCartById(cartId)==null)
            throw new RuntimeException("Cart not found!");
        cartRepository.deleteProductFromCart(cartId, product);
    }

    public void deleteCartById(UUID cartId) {
        cartRepository.deleteCartById(cartId);
    }
}