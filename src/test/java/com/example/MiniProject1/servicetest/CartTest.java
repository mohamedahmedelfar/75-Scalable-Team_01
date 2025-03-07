package com.example.MiniProject1.servicetest;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartTest {

    @Mock
    private CartRepository cartRepository;  // Mock the CartRepository

    @InjectMocks
    private CartService cartService;  // Inject mock into CartService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize Mockito
    }

    @Test
    void testAddCart() {
        // Arrange
        Cart cart = new Cart();
        when(cartRepository.addCart(cart)).thenReturn(cart); // Mock the repository method

        // Act
        Cart result = cartService.addCart(cart);

        // Assert
        assertNotNull(result);
        assertEquals(cart, result); // Verify the returned cart matches the input
        verify(cartRepository, times(1)).addCart(cart); // Verify the repository method was called
    }

    @Test
    void testGetCarts() {
        // Arrange
        List<Cart> mockCarts = new ArrayList<>();
        mockCarts.add(new Cart());
        mockCarts.add(new Cart());

        when(cartRepository.getCarts()).thenReturn((ArrayList<Cart>) mockCarts); // Mock the repository method

        // Act
        ArrayList<Cart> result = cartService.getCarts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); // Verify the number of carts returned
        verify(cartRepository, times(1)).getCarts(); // Verify the repository method was called
    }

    @Test
    void testGetCartById() {
        // Arrange
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart();
        when(cartRepository.getCartById(cartId)).thenReturn(cart); // Mock the repository method

        // Act
        Cart result = cartService.getCartById(cartId);

        // Assert
        assertNotNull(result);
        assertEquals(cart, result); // Verify the returned cart matches the mock
        verify(cartRepository, times(1)).getCartById(cartId); // Verify the repository method was called
    }

    @Test
    void testGetCartByUserId() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart();
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart); // Mock the repository method

        // Act
        Cart result = cartService.getCartByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(cart, result); // Verify the returned cart matches the mock
        verify(cartRepository, times(1)).getCartByUserId(userId); // Verify the repository method was called
    }

    @Test
    void testAddProductToCart() {
        // Arrange
        UUID cartId = UUID.randomUUID();
        Product product = new Product();

        // Mock the repository method (void method)
        doNothing().when(cartRepository).addProductToCart(cartId, product);

        // Act
        cartService.addProductToCart(cartId, product);

        // Assert
        verify(cartRepository, times(1)).addProductToCart(cartId, product); // Verify the repository method was called
    }

    @Test
    void testDeleteProductFromCart() {
        // Arrange
        UUID cartId = UUID.randomUUID();
        Product product = new Product();

        // Mock the repository method (void method)
        doNothing().when(cartRepository).deleteProductFromCart(cartId, product);

        // Act
        cartService.deleteProductFromCart(cartId, product);

        // Assert
        verify(cartRepository, times(1)).deleteProductFromCart(cartId, product); // Verify the repository method was called
    }

    @Test
    void testDeleteCartById() {
        // Arrange
        UUID cartId = UUID.randomUUID();

        // Mock the repository method (void method)
        doNothing().when(cartRepository).deleteCartById(cartId);

        // Act
        cartService.deleteCartById(cartId);

        // Assert
        verify(cartRepository, times(1)).deleteCartById(cartId); // Verify the repository method was called
    }
}