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
    void testAddUserWithInvalidNameService() throws Exception {
        User testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test 2User");


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(testUser);
        });

        assertEquals("User name cannot contain numbers", exception.getMessage(),
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

        //List<User> userList = Arrays.asList(user1, user2);

        // Mock repository behavior
        //when(userRepository.getUsers()).thenReturn(new ArrayList<>(userList));

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






//    @Test
//    void testGetUsersImmutability() {
//        // Create sample user
//        User user1 = new User();
//
//
//        List<User> userList = new ArrayList<>();
//        userList.add(user1);
//
//        // Mock repository behavior
//        when(userRepository.getUsers()).thenReturn(new ArrayList<>(userList));
//
//        List<User> users = userService.getUsers();
//
//        // Try modifying the returned list
//        users.add(new User(2, "David"));
//
//        // Fetch users again to verify the repository remains unchanged
//        List<User> usersAfterModification = userService.getUsers();
//
//        assertEquals(1, usersAfterModification.size(), "Modifying the returned list should not affect the repository.");
//        assertEquals("Charlie", usersAfterModification.get(0).getName());
//    }













}












