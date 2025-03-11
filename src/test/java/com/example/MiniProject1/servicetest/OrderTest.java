package com.example.MiniProject1.servicetest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
class OrderTest {
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
    void testAddValidOrder() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(), "Product1", 10.0));
        products.add(new Product(UUID.randomUUID(), "Product2", 15.5));

        Order order = new Order(userId, new ArrayList<>(products), 25.5);

        // Act
        orderService.addOrder(order); // Since addOrder is void, just call it

        // Retrieve the order (assuming a method exists)
        Order addedOrder = orderService.getOrderById(order.getId()); // FIX: Retrieve the order after adding

        // Assert
        assertNotNull(addedOrder, "Order should be successfully added");
        assertEquals(userId, addedOrder.getUserId(), "User ID should match");
        assertEquals(products.size(), addedOrder.getProducts().size(), "Product list should match");
        assertEquals(25.5, addedOrder.getTotalPrice(), 0.001, "Total price should match");
    }

    @Test
    void testAddOrderWithNoProducts() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Product> products = new ArrayList<>();
        Order order = new Order(userId, products, 0.0);

        // Act
        orderService.addOrder(order);

        Order addedOrder = orderService.getOrderById(order.getId());

        // Assert
        assertNotNull(addedOrder, "Order should be successfully added");
        assertEquals(userId, addedOrder.getUserId(), "User ID should match");
        assertTrue(addedOrder.getProducts().isEmpty(), "Product list should be empty");
        assertEquals(0.0, addedOrder.getTotalPrice(), 0.001, "Total price should be zero");
    }

    @Test
    void testAddOrderWithNegativeTotalPrice() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(), "Product1", 10.0));

        Order order = new Order(userId, products, -5.0); // Invalid negative total price

        // Assert & Act: Expect IllegalStateException with the correct message
        Exception exception = assertThrows(IllegalStateException.class, () -> orderService.addOrder(order));

        // Verify the exception message
        assertEquals("Cannot place an order with a negative price value", exception.getMessage());
    }





    @Test
    void testGetOrdersWhenNoOrdersExist() {
        List<Order> orders = orderService.getOrders();

        assertTrue(orders.isEmpty(), "Expected an empty list when no orders exist.");


    }

    @Test
    void testGetOrdersForSingleUserWithMultipleOrders() {
        // Arrange - Create a user and assign multiple orders to them
        UUID userId = UUID.randomUUID();
        Order order1 = new Order(userId, new ArrayList<>(), 20.0);
        Order order2 = new Order(userId, new ArrayList<>(), 50.0);

        orderService.addOrder(order1);
        orderService.addOrder(order2);

        // Act - Retrieve orders for this specific user
        List<Order> orders = orderService.getOrders().stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());

        // Assert - Ensure the user has exactly two orders
        assertEquals(2, orders.size(), "Expected two orders for the same user.");
        assertTrue(orders.contains(order1) && orders.contains(order2), "Both orders should be present for the user.");
    }

    @Test
    void testGetOrdersWithMultipleOrdersFromDifferentUsers() {
        // Arrange - Create orders from different users
        Order order1 = new Order(UUID.randomUUID(), new ArrayList<>(), 20.0);
        Order order2 = new Order(UUID.randomUUID(), new ArrayList<>(), 50.0);
        Order order3 = new Order(UUID.randomUUID(), new ArrayList<>(), 30.0);

        orderService.addOrder(order1);
        orderService.addOrder(order2);
        orderService.addOrder(order3);

        // Act - Retrieve all orders
        List<Order> orders = orderService.getOrders();

        // Assert - Ensure all orders are retrieved
        assertEquals(3, orders.size(), "Expected three orders in the system.");
        assertTrue(orders.contains(order1) && orders.contains(order2) && orders.contains(order3),
                "All added orders should be present in the list.");
    }



    @Test
    void testGetOrderById_ExistingOrder() {
        // Arrange - Create and add an order
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, UUID.randomUUID(), 30.0, new ArrayList<>());
        orderService.addOrder(order);

        // Act - Retrieve the order by its ID
        Order retrievedOrder = orderService.getOrderById(orderId);

        // Assert - Ensure the retrieved order matches the original
        assertNotNull(retrievedOrder, "Order should be found.");
        assertEquals(orderId, retrievedOrder.getId(), "Retrieved order ID should match.");
    }
    @Test
    void testGetOrderWithNullId() {


        Order order = orderService.getOrderById(null);

        assertNull(order, "Expected an Id, cannot be null.");
    }

    @Test
    void testGetOrderById_WithProducts() {
        // Arrange - Create an order with products
        UUID orderId = UUID.randomUUID();
        List<Product> products = new ArrayList<>();
        products.add(new Product(UUID.randomUUID(), "Product A", 15.0));
        products.add(new Product(UUID.randomUUID(), "Product B", 25.0));

        Order order = new Order(orderId, UUID.randomUUID(), 40.0, products);
        orderService.addOrder(order);

        // Act - Retrieve the order by its ID
        Order retrievedOrder = orderService.getOrderById(orderId);

        // Assert - Ensure the retrieved order contains the correct products
        assertNotNull(retrievedOrder, "Order should be found.");
        assertEquals(orderId, retrievedOrder.getId(), "Retrieved order ID should match.");
        assertEquals(2, retrievedOrder.getProducts().size(), "Order should contain two products.");
        assertEquals(40.0, retrievedOrder.getTotalPrice(), "Total price should match.");
    }
    @Test
    void testGetOrderById_EmptyProductList() {
        // Arrange - Create an order with no products
        UUID userId = UUID.randomUUID();
        Order emptyOrder = new Order(userId, new ArrayList<>(), 0.0);
        orderService.addOrder(emptyOrder);

        // Act
        Order retrievedOrder = orderService.getOrderById(emptyOrder.getId());

        // Assert
        assertNotNull(retrievedOrder, "Order should be retrieved successfully.");
        assertEquals(emptyOrder.getId(), retrievedOrder.getId(), "Order ID should match.");
        assertTrue(retrievedOrder.getProducts().isEmpty(), "The product list should be empty.");
        assertEquals(0.0, retrievedOrder.getTotalPrice(), "Total price should be zero.");
    }











    @Test
    void testDeleteOrderWithNullId() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.deleteOrderById(null);
        });

        assertEquals("Cannot search for null Id", exception.getMessage(),
                "Expected an IllegalArgumentException for null order ID");
    }
    @Test
    void testDeleteExistingOrder() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, UUID.randomUUID(), 50.0, new ArrayList<>());
        orderService.addOrder(order);

        // Act
        orderService.deleteOrderById(orderId);

        System.out.println("Order after deletion: " + orderService.getOrderById(orderId));

        // Assert

        assertNull(orderService.getOrderById(orderId), "Order should be deleted and not found");
       // assertEquals("Order deleted successfully", "Order deleted successfully");

    }


    @Test
    void testDeleteOrderWhenNoOrdersExist() {
        // Arrange - Ensure there are no orders
        List<Order> existingOrders = orderService.getOrders();
        existingOrders.clear(); // Simulate an empty order list

        UUID randomOrderId = UUID.randomUUID();

        // Act
        orderService.deleteOrderById(randomOrderId);

        // Assert - Ensure the order list is still empty
        assertTrue(orderService.getOrders().isEmpty(), "Order list should remain empty after attempting deletion.");
    }

    //just testing the function exists
    @Test
    public void testDeleteOrderByIdFunctionExists() {
        Class<?> clazz =OrderService.class;

        String mn="deleteOrderById";
        Class<?>[] pt = {UUID.class};
        try {
            Method m=clazz.getDeclaredMethod(mn,pt);

        }
        catch (
                NoSuchMethodException e
        ){
            throw new AssertionError("Method not found");
        }
    }



}