package com.example.MiniProject1.servicetest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.example.repository.*;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
class CartTest {
    @Value("${spring.application.userDataPath}")
    private String userDataPath;
    @Value("${spring.application.productDataPath}")
    private String productDataPath;
    @Value("${spring.application.orderDataPath}")
    private String orderDataPath;
    @Value("${spring.application.cartDataPath}")
    private String cartDataPath;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    public void overRideAll() {
        try {
            objectMapper.writeValue(new File(userDataPath), new ArrayList<User>());
            objectMapper.writeValue(new File(productDataPath), new ArrayList<Product>());
            objectMapper.writeValue(new File(orderDataPath), new ArrayList<Order>());
            objectMapper.writeValue(new File(cartDataPath), new ArrayList<Cart>());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public Object find(String typeString, Object toFind) {
        switch (typeString) {
            case "User":
                ArrayList<User> users = getUsers();
                for (User user : users) {
                    if (user.getId().equals(((User) toFind).getId())) {
                        return user;
                    }
                }
                break;
            case "Product":
                ArrayList<Product> products = getProducts();
                for (Product product : products) {
                    if (product.getId().equals(((Product) toFind).getId())) {
                        return product;
                    }
                }
                break;
            case "Order":
                ArrayList<Order> orders = getOrders();
                for (Order order : orders) {
                    if (order.getId().equals(((Order) toFind).getId())) {
                        return order;
                    }
                }
                break;
            case "Cart":
                ArrayList<Cart> carts = getCarts();
                for (Cart cart : carts) {
                    if (cart.getId().equals(((Cart) toFind).getId())) {
                        return cart;
                    }
                }
                break;
        }
        return null;
    }

    public Product addProduct(Product product) {
        try {
            File file = new File(productDataPath);
            ArrayList<Product> products;
            if (!file.exists()) {
                products = new ArrayList<>();
            } else {
                products = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
            }
            products.add(product);
            objectMapper.writeValue(file, products);
            return product;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public ArrayList<Product> getProducts() {
        try {
            File file = new File(productDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Product>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public User addUser(User user) {
        try {
            File file = new File(userDataPath);
            ArrayList<User> users;
            if (!file.exists()) {
                users = new ArrayList<>();
            } else {
                users = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));
            }
            users.add(user);
            objectMapper.writeValue(file, users);
            return user;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public ArrayList<User> getUsers() {
        try {
            File file = new File(userDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<User>(Arrays.asList(objectMapper.readValue(file, User[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public Cart addCart(Cart cart) {
        try {
            File file = new File(cartDataPath);
            ArrayList<Cart> carts;
            if (!file.exists()) {
                carts = new ArrayList<>();
            } else {
                carts = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
            }
            carts.add(cart);
            objectMapper.writeValue(file, carts);
            return cart;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public ArrayList<Cart> getCarts() {
        try {
            File file = new File(cartDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Cart>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public Order addOrder(Order order) {
        try {
            File file = new File(orderDataPath);
            ArrayList<Order> orders;
            if (!file.exists()) {
                orders = new ArrayList<>();
            } else {
                orders = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
            }
            orders.add(order);
            objectMapper.writeValue(file, orders);
            return order;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }

    public ArrayList<Order> getOrders() {
        try {
            File file = new File(orderDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Order>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    private UUID userId;
    private User testUser;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        testUser = new User();
        testUser.setId(userId);
        testUser.setName("Test User");
        overRideAll();
    }


    @Test
    void testAddValidCart() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product1", 10.0));

        Cart cart = new Cart(userId, new ArrayList<>(products));

        // Act
        Cart addedCart = cartService.addCart(cart);

        // Assert
        assertNotNull(addedCart, "Cart should be successfully added");
        assertEquals(userId, addedCart.getUserId(), "User ID should match");
        assertEquals(products.size(), addedCart.getProducts().size(), "Product list should match");
    }

    @Test
    void testAddCartWithExistingId() {
        // Arrange
        UUID existingCartId = UUID.randomUUID();
        Cart cart1 = new Cart(existingCartId, UUID.randomUUID(), new ArrayList<>());
        cartService.addCart(cart1);

        Cart cart2 = new Cart(existingCartId, UUID.randomUUID(), new ArrayList<>());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addCart(cart2);
        });

        assertEquals("Cart with this ID already exists", exception.getMessage(), "Expected error message mismatch");
    }

    @Test
    void testAddCartWithNullUserId() {
        // Arrange
        Cart cart = new Cart(null, new ArrayList<>());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addCart(cart);
        });

        assertEquals("User ID cannot be null", exception.getMessage(), "Expected error message mismatch");
    }

    @Test
    void testAddNullCart() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addCart(null);
        });

        assertEquals("Cart cannot be null", exception.getMessage());
    }





    @Test
    void testGetCartsWhenNoCartsExist() {
        List<Cart> carts = cartService.getCarts();

        assertTrue(carts.isEmpty(), "Expected an empty list when no users exist.");


    }

    @Test
    void testGetCartsWithMultipleCarts() {
        // Create sample carts
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        cart1.setId(UUID.randomUUID());
        cart2.setId(UUID.randomUUID());
        cart1.setUserId(UUID.randomUUID());
        cart2.setUserId(UUID.randomUUID());

        cartService.addCart(cart1);
        cartService.addCart(cart2);

        // Act
        List<Cart> carts = cartService.getCarts();

        // Assert
        assertEquals(2, carts.size(), "Expected two carts in the list.");
        assertTrue(carts.contains(cart1) && carts.contains(cart2), "Both carts should be in the list.");
    }

    //This test ensures that multiple carts can exist for the same user but with different cart IDs.
    @Test
    void testGetCartsWithSameUserId() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart1 = new Cart(UUID.randomUUID(), userId, new ArrayList<>()); // Unique cart ID, same user
        Cart cart2 = new Cart(UUID.randomUUID(), userId, new ArrayList<>()); // Another unique cart ID, same user

        cartService.addCart(cart1);
        cartService.addCart(cart2);

        // Act
        List<Cart> carts = cartService.getCarts();

        // Assert
        long count = carts.stream().filter(cart -> cart.getUserId().equals(userId)).count();
        assertEquals(2, count, "A user should be able to have multiple carts with different IDs");
    }

}