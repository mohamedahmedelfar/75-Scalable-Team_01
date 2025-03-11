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
class UserTest
{
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




    // --------------------------------- User Tests -------------------------
    @Test
    void testAddUserService() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");

        userService.addUser(testUser);

        boolean found = false;
        for (User u : getUsers()) {
            if (u.getId().equals(testUser.getId())) {
                found = true;
                break;
            }
        }
        System.out.println("Users in service: " + userService.getUsers());
        System.out.println("Looking for user ID: " + testUser.getId());

        assertTrue(found, "User should be added correctly in the service");
    }




    @Test
    void testAddUserWithNullIdService() throws Exception {
        User testUser = new User();
        testUser.setId(null);
        testUser.setName("Test 2User");


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(testUser);
        });
        assertEquals("User id cannot be null", exception.getMessage(),
                "Expected exception message does not match");

    }
    @Test
    void testAddUserWithNoNameService() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(testUser);
        });

        assertEquals("User name cannot be null", exception.getMessage(),
                "Expected exception message does not match");
    }

    @Test
    void testGetUsersWhenEmpty() {
        // Mock repository to return an empty list
        //when(userRepository.getUsers()).thenReturn(new ArrayList<>());

        List<User> users = userService.getUsers();

        assertTrue(users.isEmpty(), "Expected an empty list when no users exist.");
    }

    @Test
    void testGetUsersWithMultipleUsers() {
        // Create sample users
        User user1 = new User();
        User user2 = new User();
        user1.setId(UUID.randomUUID());
        user2.setId(UUID.randomUUID());
        user1.setName("Test User");
        user2.setName("Test User Two");

        userService.addUser(user1);
        userService.addUser(user2);

        List<User> users = userService.getUsers();

        assertEquals(2, users.size(), "Expected two users in the list.");
        assertEquals("Test User", users.get(0).getName());
        assertEquals("Test User Two", users.get(1).getName());
    }



    @Test
    void testGetUsersImmutability() {
        // Create sample users
        User user1 = new User();
        User user2 = new User();
        user1.setId(UUID.randomUUID());
        user2.setId(UUID.randomUUID());
        user1.setName("Test User");
        user2.setName("Test User Two");

        userService.addUser(user1);
        userService.addUser(user2);



        List<User> users = userService.getUsers();
        users.remove(user1);


        assertEquals(2, users.size(), "Expected two users in the list.");
        assertEquals("Test User", users.get(0).getName());
        assertEquals("Test User Two", users.get(1).getName());
    }












    @Test
    void testGetUsersWithExistingId() {
        // Create sample users
        User user1 = new User();
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        user1.setName("Test User");
        user1.setId(userId);

        userService.addUser(user1);

        User user = userService.getUserById(userId);

        assertEquals("Test User", user.getName(), "Expected user name in the list.");
    }
    @Test
    void testGetUsersWithNonExistingId() {
        // Create sample users
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        User user = userService.getUserById(userId);

        assertNull(user, "Expected null as user with given id doesn't exist.");
    }


    @Test
    void testGetUsersWithNullId() {


        User user = userService.getUserById(null);

        assertNull(user, "Expected an Id, cannot be null.");
    }

    @Test
    void testGetOrdersByUserIdReturnsCorrectOrders() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Order> orders = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        orders.add(new Order(UUID.randomUUID(), products, 100.0));
        orders.add(new Order(UUID.randomUUID(), products, 200.0));

        User user = new User(userId, "John Doe", orders);
        userService.addUser(user); // Assume addUser correctly stores the user

        // Act
        List<Order> retrievedOrders = userService.getOrdersByUserId(userId);

        // Assert
        assertNotNull(retrievedOrders, "Orders list should not be null");
        assertEquals(2, retrievedOrders.size(), "User should have exactly 2 orders");
        assertEquals(100, retrievedOrders.get(0).getTotalPrice(), "First order total price should match");
        assertEquals(200, retrievedOrders.get(1).getTotalPrice(), "Second order total price should match");
    }

    @Test
    void testGetOrdersByUserIdReturnsNullForNonexistentUser() {
        // Arrange
        UUID nonExistentUserId = UUID.randomUUID(); // Generate a random user ID

        // Act
        List<Order> retrievedOrders = userService.getOrdersByUserId(nonExistentUserId);

        // Assert
        assertNull(retrievedOrders, "Should return null for a non-existent user");
    }

    @Test
    void testGetOrdersByUserIdReturnsEmptyListForUserWithNoOrders() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Alice", new ArrayList<>()); // No orders
        userService.addUser(user);

        // Act
        List<Order> retrievedOrders = userService.getOrdersByUserId(userId);

        // Assert
        assertNotNull(retrievedOrders, "Orders list should not be null");
        assertEquals(0, retrievedOrders.size(), "Orders list should be empty for a user with no orders");
    }

    @Test
    void testEmptyCartSuccessfullyClearsProducts() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>());
        cart.getProducts().add(new Product("Laptop", 1200.0));
        cart.getProducts().add(new Product("Mouse", 50.0));

        cartService.addCart(cart);

        // Act
        userService.emptyCart(userId);
        Cart updatedCart = cartService.getCartByUserId(userId);

        // Assert
        assertNotNull(updatedCart, "Cart should still exist after emptying");
        assertEquals(0, updatedCart.getProducts().size(), "Cart should have no products after emptying");
    }

    @Test
    void testEmptyCartDoesNothingForEmptyCart() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>()); // Already empty
        cartService.addCart(cart);

        // Act
        userService.emptyCart(userId);
        Cart updatedCart = cartService.getCartByUserId(userId);

        // Assert
        assertNotNull(updatedCart, "Cart should still exist after trying to empty");
        assertEquals(0, updatedCart.getProducts().size(), "Cart should remain empty");
    }

    @Test
    void testEmptyCartDoesNothingForNonexistentCart() {
        // Arrange
        UUID nonExistentUserId = UUID.randomUUID(); // User with no cart

        // Act & Assert (No Exception Should Be Thrown)
        assertDoesNotThrow(() -> userService.emptyCart(nonExistentUserId),
                "Emptying a non-existent cart should not throw an exception");
    }


    @Test
    void testAddOrderToUserSuccessfullyCreatesOrder() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test", new ArrayList<>());
        addUser(user);
        Cart cart = new Cart(userId, new ArrayList<>());
        cart.getProducts().add(new Product("Laptop", 1200.0));
        cart.getProducts().add(new Product("Mouse", 50.0));

        cartService.addCart(cart);

        // Act
        userService.addOrderToUser(userId);
        Order lastOrder = userService.getOrdersByUserId(userId).getFirst();
        Cart updatedCart = cartService.getCartByUserId(userId);

        // Assert
        assertNotNull(lastOrder, "Order should be created successfully");
        assertEquals(1250.0, lastOrder.getTotalPrice(), "Total price should be correctly calculated");
        assertEquals(2, lastOrder.getProducts().size(), "Order should contain 2 products");
        assertEquals(0, updatedCart.getProducts().size(), "Cart should be empty after placing order");
    }

    @Test
    void testAddOrderToUserThrowsExceptionForEmptyCart() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test", new ArrayList<>());
        addUser(user);
        Cart cart = new Cart(userId, new ArrayList<>()); // Empty cart
        cartService.addCart(cart);

        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            userService.addOrderToUser(userId);
        });

        assertEquals("Cannot place an order with an empty cart", exception.getMessage(),
                "Exception message should indicate that cart cannot be empty");
    }

    @Test
    void testAddOrderToUserHandlesMultipleOrders() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test", new ArrayList<>());
        addUser(user);
        Cart cart = new Cart(userId, new ArrayList<>());

        cart.getProducts().add(new Product("Phone", 800.0));
        cart.getProducts().add(new Product("Charger", 50.0));

        cartService.addCart(cart);
        userService.addOrderToUser(userId); // First order
//        List<Product> products = new ArrayList<Product>();
//        products.add(new Product("Headphones", 200.0));
//        cart.setProducts(products);
        cartService.addProductToCart(cart.getId(),new Product("Headphones", 200.0) );
        userService.addOrderToUser(userId); // Second order

        // Act
        List<Order> userOrders = userService.getOrdersByUserId(userId);

        // Assert
        assertEquals(2, userOrders.size(), "User should have two orders placed");
        assertEquals(850.0, userOrders.get(0).getTotalPrice(), "First order total should be correct");
        assertEquals(200.0, userOrders.get(1).getTotalPrice(), "Second order total should be correct");
    }


    @Test
    void testDeleteExistingUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName("Test User");
        userService.addUser(user);

        // Act
        userService.deleteUserById(userId);

        System.out.println("User after deletion: " + userService.getUserById(userId));

        // Assert
        assertNull(userService.getUserById(userId), "User should be deleted and not found");
    }

    @Test
    void testDeleteNonExistingUser() {
        // Arrange
        UUID userId = UUID.randomUUID();

        // Act
        userService.deleteUserById(userId);

        // Assert
        assertNull(userService.getUserById(userId), "User should be deleted and not found");
    }

    @Test
    void testDeleteUserWithNullId() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUserById(null);
        });

        assertEquals("Cannot search for null Id", exception.getMessage(),
                "Expected an IllegalArgumentException for null user ID");
    }






}












