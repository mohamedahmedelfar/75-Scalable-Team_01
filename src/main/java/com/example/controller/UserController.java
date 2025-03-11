package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.service.CartService;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Order;
import com.example.model.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/")
    public ArrayList<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);
    }
    
    @GetMapping("/{userId}/orders")
    public List<Order> getOrdersByUserId(@PathVariable UUID userId){
        return userService.getOrdersByUserId(userId);
    }

    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId){
        userService.removeOrderFromUser(userId, orderId);
        return "Order removed successfully";
    }

    @DeleteMapping("/{userId}/emptyCart")
    public String emptyCart(@PathVariable UUID userId){
        userService.emptyCart(userId);
        return "Cart emptied successfully";
    
    }

    @PostMapping("/{userId}/checkout")
    public String addOrderToUser(@PathVariable UUID userId){
        userService.addOrderToUser(userId);
        return "Order added successfully";
    }

    @PutMapping("/addProductToCart")
    public String addProductToCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "User not found";
        }
        Cart cart = cartService.getCartByUserId(userId);
        if(cart == null){
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProducts(new ArrayList<>());
            cartService.addCart(cart);
        }
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "Product not found";
        }
        UUID cartId = cartService.getCartByUserId(userId).getId();
        cartService.addProductToCart(cartId, product);
        return "Product added to cart";
    }

    @PutMapping("/deleteProductFromCart")
    public String deleteProductFromCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null){
            return "Cart is empty";
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            return "Product deleted from cart";
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            return "User not found";
        }
        if(!(cartService.getCartByUserId(userId).getProducts().contains(product))){
            return "Product deleted from cart";
        }
        UUID cartId = cartService.getCartByUserId(userId).getId();
        cartService.deleteProductFromCart(cartId, product);
        return "Product deleted from cart";
    }
    
    @DeleteMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable UUID userId){
        if(userService.getUserById(userId) == null){
            return "User not found";
        }
        userService.deleteUserById(userId);
        return "User deleted successfully";
    }


}
