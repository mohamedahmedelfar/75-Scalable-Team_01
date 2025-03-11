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
class UserTest {
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


    @Test
    void testGetOrdersByUserId_ValidUserWithMultipleOrders() {

        UUID userId = UUID.randomUUID();
        List<Product> products = new ArrayList<>();
        Order order1 = new Order(UUID.randomUUID(), userId, 100.0, products);
        Order order2 = new Order(UUID.randomUUID(), userId, 150.0, products);

        User user = new User(userId, "John Doe", new ArrayList<>());
        userService.addUser(user);

        userRepository.addOrderToUser(userId, order1);
        userRepository.addOrderToUser(userId, order2);

        // Verify the user was added successfully
        List<User> users = userService.getUsers();
        System.out.println("Users in repository: " + users);

        assertNotNull(users, "Users list should not be null.");
        assertFalse(users.isEmpty(), "Users list should not be empty.");

        assertTrue(users.contains(user), "User should be in the repository.");

        System.out.println("Orders for user before retrieval: " + user.getOrders());


        // Act
        List<Order> retrievedOrders = userService.getOrdersByUserId(userId);

        // Assert
        assertNotNull(retrievedOrders, "Orders should not be null for a valid user.");
        assertEquals(2, retrievedOrders.size(), "Expected two orders in the list.");
        assertTrue(retrievedOrders.contains(order1), "Order list should contain the first order.");
        assertTrue(retrievedOrders.contains(order2), "Order list should contain the second order.");
    }


    @Test
    void testGetOrdersByUserId_UserWithNoOrders() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "Alice Smith", new ArrayList<>());
        userService.addUser(user); // Add user without orders

        // Verify user is stored but has no orders
        List<User> users = userService.getUsers();
        System.out.println("Users in repository: " + users);

        assertNotNull(users, "Users list should not be null.");
        assertFalse(users.isEmpty(), "Users list should not be empty.");
        assertTrue(users.contains(user), "User should be in the repository.");

        System.out.println("Orders for user before retrieval: " + user.getOrders());

        // Act
        List<Order> retrievedOrders = userService.getOrdersByUserId(userId);

        // Assert
        assertNotNull(retrievedOrders, "Orders should not be null even if user has no orders.");
        assertTrue(retrievedOrders.isEmpty(), "Expected empty list for user with no orders.");
    }


    @Test
    void testGetOrdersByUserId_NonExistentUserAfterAddingValidUser() {
        // Arrange
        UUID existingUserId = UUID.randomUUID();
        UUID nonExistentUserId = UUID.randomUUID(); // A different user ID that does not exist

        List<Product> products = new ArrayList<>();
        Order order1 = new Order(UUID.randomUUID(), existingUserId, 100.0, products);
        Order order2 = new Order(UUID.randomUUID(), existingUserId, 200.0, products);

        User validUser = new User(existingUserId, "Alice Doe", new ArrayList<>());
        userService.addUser(validUser);

        userRepository.addOrderToUser(existingUserId, order1);
        userRepository.addOrderToUser(existingUserId, order2);

        // Verify that orders were correctly assigned to the valid user
        List<Order> userOrders = userService.getOrdersByUserId(existingUserId);
        System.out.println("Orders for existing user: " + userOrders);

        assertNotNull(userOrders, "Orders for the existing user should not be null.");
        assertEquals(2, userOrders.size(), "Expected two orders for the valid user.");

        // Act - Try fetching orders for a non-existent user
        List<Order> retrievedOrders = userService.getOrdersByUserId(nonExistentUserId);

        // Assert
        assertNull(retrievedOrders, "Expected null for a non-existent user.");
        //assertTrue(retrievedOrders.isEmpty(), "Expected empty list for non-existent user.");
    }
}









