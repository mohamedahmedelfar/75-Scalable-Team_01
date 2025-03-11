package com.example.MiniProject1.servicetest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.controller.ProductController;
import com.example.repository.*;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.jayway.jsonpath.internal.function.text.Length;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
class ProductTest
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

// ---------------------------Product Tests----------------------------------------

    @Test
    void testAddProductWithMinimumValidPrice() throws Exception {
        Product testProduct = new Product();
        testProduct.setId(UUID.randomUUID());
        testProduct.setName("Test Product");
        testProduct.setPrice(0.01); // Minimum valid price

        mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testProduct.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(0.01));
    }
    @Test
    void testAddProductWithSpecialCharactersInName() throws Exception {
        Product testProduct = new Product();
        testProduct.setId(UUID.randomUUID());
        testProduct.setName("Test Product @#$%^&*()");
        testProduct.setPrice(10.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testProduct.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product @#$%^&*()"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0));
    }
    @Test
    void testBulkProductAddition() throws Exception {
        int numberOfProducts = 1000;
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < numberOfProducts; i++) {
            Product product = new Product();
            product.setId(UUID.randomUUID());
            product.setName("Product " + i);
            product.setPrice(i + 1.0);
            products.add(product);
        }

        for (Product product : products) {
            mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(product)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        assertEquals(numberOfProducts, getProducts().size(), "All products should be added successfully");
    }
    @Test
    void testApplyDiscountToSingleProductSuccessfully() throws Exception {
        Product testProduct = new Product();
        testProduct.setId(UUID.randomUUID());
        testProduct.setName("Test Product");
        testProduct.setPrice(10.0);
        productService.addProduct(testProduct);

        ArrayList<UUID> productIds = new ArrayList<>();
        productIds.add(testProduct.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/product/applyDiscount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("discount", "10.0")
                        .content(objectMapper.writeValueAsString(productIds)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Discount applied successfully"));

        assertEquals(9.0, productService.getProductById(testProduct.getId()).getPrice(), "Product price should be updated correctly");
    }

    @Test
    void testApplyZeroDiscount() throws Exception {
        Product product = new Product(UUID.randomUUID(), "Test Product", 50.0);
        productService.addProduct(product);

        ArrayList<UUID> productIds = new ArrayList<>();
        productIds.add(product.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/product/applyDiscount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("discount", "0.0")
                        .content(objectMapper.writeValueAsString(productIds)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Discount applied successfully"));

        assertEquals(50.0, productService.getProductById(product.getId()).getPrice(), "Price should remain unchanged");
    }


    @Test
    void testApplyDiscountWithNullProductList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/product/applyDiscount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("discount", "10.0")
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void testGetProductByIdTwiceConsistencyCheck() throws Exception {
        Product testProduct = new Product(UUID.randomUUID(), "Test Product", 25.50);
        addProduct(testProduct);

        for (int i = 0; i < 2; i++) {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/" + testProduct.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String responseContent = result.getResponse().getContentAsString();
            Product responseProduct = objectMapper.readValue(responseContent, Product.class);

            assertEquals(testProduct.getId(), responseProduct.getId(), "Product ID should remain consistent across multiple fetches");
            assertEquals(testProduct.getName(), responseProduct.getName(), "Product name should remain consistent across multiple fetches");
            assertEquals(testProduct.getPrice(), responseProduct.getPrice(), "Product price should remain consistent across multiple fetches");
        }
    }
    @Test
    void testGetProductByUUIDWithWhitespaces() throws Exception {
        Product testProduct = new Product(UUID.randomUUID(), "Product Name", 40.0);
        addProduct(testProduct);

        String uuidWithSpaces = " " + testProduct.getId().toString() + " ";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/" + uuidWithSpaces.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Product responseProduct = objectMapper.readValue(responseContent, Product.class);

        assertEquals(testProduct.getId(), responseProduct.getId(), "Product ID should match even with trimmed spaces");
    }

    @Test
    void testGetProductByNullUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/null")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testGetProductsWhenNoProductsExist() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});

        assertTrue(responseProducts.isEmpty(), "Product list should be empty if no products exist");
    }

    @Test
    void testGetProductsMaintainsOrderConsistency() throws Exception {
        Product product1 = new Product(UUID.randomUUID(), "Product A", 10.0);
        Product product2 = new Product(UUID.randomUUID(), "Product B", 20.0);
        addProduct(product1);
        addProduct(product2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});

        assertEquals(product1.getId(), responseProducts.get(0).getId(), "First product should match the expected order");
        assertEquals(product2.getId(), responseProducts.get(1).getId(), "Second product should match the expected order");
    }

    @Test
    void testGetProductsResponseFormatConsistency() throws Exception {
        Product product1 = new Product(UUID.randomUUID(), "Product 1", 5.0);
        Product product2 = new Product(UUID.randomUUID(), "Product 2", 15.0);
        addProduct(product1);
        addProduct(product2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.startsWith("["), "Response should be a JSON array");
        assertTrue(responseContent.endsWith("]"), "Response should be a JSON array");
    }

    @Test
    void testUpdateNonExistingProduct() throws Exception {
        UUID nonExistingId = UUID.randomUUID();
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("newName", "Updated Name");
        updateData.put("newPrice", 30.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void testUpdateProductWithEmptyRequestBody() throws Exception {
        Product testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
        addProduct(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/" + testProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @Test
    void testUpdateProductWithoutName() throws Exception {
        Product testProduct = new Product(UUID.randomUUID(), "Test Product", 15.0);
        addProduct(testProduct);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("newPrice", 18.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/" + testProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @Test
    void testUpdateProductWithInvalidPrice() throws Exception {
        Product testProduct = new Product(UUID.randomUUID(), "Test Product", 20.0);
        addProduct(testProduct);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("newName", "Updated Name");
        updateData.put("newPrice", "InvalidPrice");

        mockMvc.perform(MockMvcRequestBuilders.put("/product/update/" + testProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void testDeleteProductWithInvalidUUID() throws Exception {
        String invalidUUID = "invalid-uuid";

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/" + invalidUUID))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testDeleteProductWithNullUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/null"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testDeleteAllProductsOneByOne() throws Exception {
        Product product1 = new Product(UUID.randomUUID(), "Product1", 10.0);
        Product product2 = new Product(UUID.randomUUID(), "Product2", 20.0);
        addProduct(product1);
        addProduct(product2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/" + product1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/" + product2.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertTrue(getProducts().isEmpty(), "All products should be deleted");
    }




























}