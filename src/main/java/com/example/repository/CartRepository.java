package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class CartRepository extends MainRepository<Cart> {
    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/carts.json";
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
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
    public void addProductToCart(UUID cartId, Product product){
        Cart cart = getCartById(cartId);
        if(cart != null){
            cart.getProducts().add(product);
            deleteCartById(cartId);
            addCart(cart);
        }
    }
    public void deleteProductFromCart(UUID cartId, Product product){
        Cart cart = getCartById(cartId);
        if(cart != null){
            ArrayList<Product> products = cart.getProducts();
            int index=0;
            for(Product tempProduct:products){
                if (tempProduct.getId().equals(product.getId())){
                    products.remove(index);
                    break;
                }
                index++;
            }
            deleteCartById(cartId);
            addCart(cart);
        }
    }
    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = getCarts();
        carts.removeIf(cart -> cart.getId().equals(cartId));
        saveAll(carts);
    }
}