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

        assertTrue(carts.isEmpty(), "Expected an empty list when no carts exist.");


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

    @Test
    void testGetCartsMaintainsCartData() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Product> products = new ArrayList<>();
        products.add(new Product("TestProduct", 25.0));

        Cart cart = new Cart(userId, new ArrayList<>(products));
        cartService.addCart(cart);

        // Act
        List<Cart> carts = cartService.getCarts();
        Cart retrievedCart = carts.stream()
                .filter(c -> c.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

        // Assert
        assertNotNull(retrievedCart, "Retrieved cart should not be null");
        assertEquals(userId, retrievedCart.getUserId(), "User ID should match");
        assertEquals(products.size(), retrievedCart.getProducts().size(), "Product list size should match");
        assertEquals("TestProduct", retrievedCart.getProducts().get(0).getName(), "Product name should match");
    }

    @Test
    void testGetCartByIdReturnsCorrectCart() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>());
        cartService.addCart(cart);

        UUID cartId = cart.getId(); // Retrieve the assigned UUID

        // Act
        Cart retrievedCart = cartService.getCartById(cartId);

        // Assert
        assertNotNull(retrievedCart, "Cart should be found");
        assertEquals(cartId, retrievedCart.getId(), "Cart ID should match");
        assertEquals(userId, retrievedCart.getUserId(), "User ID should match");
    }

    @Test
    void testGetCartByIdReturnsNullForNonexistentCart() {
        // Arrange
        UUID nonExistentCartId = UUID.randomUUID(); // Generate a random cart ID

        // Act
        Cart retrievedCart = cartService.getCartById(nonExistentCartId);

        // Assert
        assertNull(retrievedCart, "Should return null for a non-existent cart");
    }

    @Test
    void testGetCartByIdHandlesMultipleCarts() {
        // Arrange
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        Cart cart1 = new Cart(userId1, new ArrayList<>());
        Cart cart2 = new Cart(userId2, new ArrayList<>());

        cartService.addCart(cart1);
        cartService.addCart(cart2);

        UUID cartId1 = cart1.getId();
        UUID cartId2 = cart2.getId();

        // Act
        Cart retrievedCart1 = cartService.getCartById(cartId1);
        Cart retrievedCart2 = cartService.getCartById(cartId2);

        // Assert
        assertNotNull(retrievedCart1, "First cart should be found");
        assertNotNull(retrievedCart2, "Second cart should be found");

        assertEquals(cartId1, retrievedCart1.getId(), "First cart ID should match");
        assertEquals(cartId2, retrievedCart2.getId(), "Second cart ID should match");
    }

    @Test
    void testGetCartByUserIdReturnsCorrectCart() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>());
        cartService.addCart(cart);

        // Act
        Cart retrievedCart = cartService.getCartByUserId(userId);

        // Assert
        assertNotNull(retrievedCart, "Cart should be found");
        assertEquals(userId, retrievedCart.getUserId(), "User ID should match");
    }

    @Test
    void testGetCartByUserIdReturnsNullForNonexistentUser() {
        // Arrange
        UUID nonExistentUserId = UUID.randomUUID(); // Generate a random user ID

        // Act
        Cart retrievedCart = cartService.getCartByUserId(nonExistentUserId);

        // Assert
        assertNull(retrievedCart, "Should return null for a non-existent user");
    }

    @Test
    void testGetCartByUserIdHandlesMultipleCarts() {
        // Arrange
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        Cart cart1 = new Cart(userId1, new ArrayList<>());
        Cart cart2 = new Cart(userId2, new ArrayList<>());

        cartService.addCart(cart1);
        cartService.addCart(cart2);

        // Act
        Cart retrievedCart1 = cartService.getCartByUserId(userId1);
        Cart retrievedCart2 = cartService.getCartByUserId(userId2);

        // Assert
        assertNotNull(retrievedCart1, "First user's cart should be found");
        assertNotNull(retrievedCart2, "Second user's cart should be found");

        assertEquals(userId1, retrievedCart1.getUserId(), "First user's cart should match user ID");
        assertEquals(userId2, retrievedCart2.getUserId(), "Second user's cart should match user ID");
    }

    @Test
    void testAddProductToCartSuccessfullyAddsProduct() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>());
        cartService.addCart(cart);
        UUID cartId = cart.getId();

        Product product = new Product("Laptop", 1200.0);

        // Act
        cartService.addProductToCart(cartId, product);
        Cart updatedCart = cartService.getCartById(cartId);

        // Assert
        assertNotNull(updatedCart, "Cart should not be null after adding a product");
        assertEquals(1, updatedCart.getProducts().size(), "Cart should contain one product");
        assertEquals("Laptop", updatedCart.getProducts().get(0).getName(), "Product name should match");
        assertEquals(1200.0, updatedCart.getProducts().get(0).getPrice(), "Product price should match");
    }

    @Test
    void testAddProductToCartFailsForNonexistentCart() {
        // Arrange
        UUID nonExistentCartId = UUID.randomUUID();
        Product product = new Product("Smartphone", 800.0);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartService.addProductToCart(nonExistentCartId, product);
        });

        assertEquals("Cart not found!", exception.getMessage(), "Exception message should indicate missing cart");
    }

    @Test
    void testAddMultipleProductsToCart() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>());
        cartService.addCart(cart);
        UUID cartId = cart.getId();

        Product product1 = new Product("Headphones", 150.0);
        Product product2 = new Product("Mouse", 50.0);

        // Act
        cartService.addProductToCart(cartId, product1);
        cartService.addProductToCart(cartId, product2);
        Cart updatedCart = cartService.getCartById(cartId);

        // Assert
        assertNotNull(updatedCart, "Cart should not be null after adding multiple products");
        assertEquals(2, updatedCart.getProducts().size(), "Cart should contain two products");

        assertEquals("Headphones", updatedCart.getProducts().get(0).getName(), "First product name should match");
        assertEquals(150.0, updatedCart.getProducts().get(0).getPrice(), "First product price should match");

        assertEquals("Mouse", updatedCart.getProducts().get(1).getName(), "Second product name should match");
        assertEquals(50.0, updatedCart.getProducts().get(1).getPrice(), "Second product price should match");
    }

    @Test
    void testDeleteProductFromCartSuccessfullyRemovesProduct() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>());
        cartService.addCart(cart);
        UUID cartId = cart.getId();

        Product product = new Product("Laptop", 1200.0);
        cartService.addProductToCart(cartId, product);

        // Act
        cartService.deleteProductFromCart(cartId, product);
        Cart updatedCart = cartService.getCartById(cartId);

        // Assert
        assertNotNull(updatedCart, "Cart should not be null after deleting a product");
        assertEquals(0, updatedCart.getProducts().size(), "Cart should be empty after product removal");
    }

    @Test
    void testDeleteProductFromCartFailsForNonexistentCart() {
        // Arrange
        UUID nonExistentCartId = UUID.randomUUID();
        Product product = new Product("Smartphone", 800.0);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartService.deleteProductFromCart(nonExistentCartId, product);
        });

        assertEquals("Cart not found!", exception.getMessage(), "Exception message should indicate missing cart");
    }

    @Test
    void testDeleteProductFromCartFailsForNonexistentProduct() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>());
        cartService.addCart(cart);
        UUID cartId = cart.getId();

        Product productInCart = new Product("Laptop", 1200.0);
        cartService.addProductToCart(cartId, productInCart);
        Product productNotInCart = new Product("Tablet", 600.0);

        // Act
        cartService.deleteProductFromCart(cartId, productNotInCart);
        Cart updatedCart = cartService.getCartById(cartId);

        // Assert
        assertNotNull(updatedCart, "Cart should not be null after trying to remove a non-existent product");
        assertEquals(1, updatedCart.getProducts().size(), "Cart should remain with 1 product");
    }

    @Test
    void testDeleteCartByIdSuccessfullyRemovesCart() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart(userId, new ArrayList<>());
        cartService.addCart(cart);
        UUID cartId = cart.getId();

        // Act
        cartService.deleteCartById(cartId);
        Cart retrievedCart = cartService.getCartById(cartId);

        // Assert
        assertNull(retrievedCart, "Cart should be removed successfully");
    }

    @Test
    void testDeleteCartByIdFailsForNonexistentCart() {
        // Arrange
        UUID nonExistentCartId = UUID.randomUUID(); // Generate a random cart ID

        // Act & Assert (No Exception Should Be Thrown)
        assertDoesNotThrow(() -> cartService.deleteCartById(nonExistentCartId),
                "Deleting a non-existent cart should not throw an exception");
    }

    @Test
    void testDeleteCartByIdReducesCartCount() {
        // Arrange
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        Cart cart1 = new Cart(userId1, new ArrayList<>());
        Cart cart2 = new Cart(userId2, new ArrayList<>());

        cartService.addCart(cart1);
        cartService.addCart(cart2);

        int initialCartCount = cartService.getCarts().size();
        UUID cartIdToDelete = cart1.getId();

        // Act
        cartService.deleteCartById(cartIdToDelete);
        int newCartCount = cartService.getCarts().size();

        // Assert
        assertEquals(initialCartCount - 1, newCartCount, "Cart count should decrease after deletion");
    }

}