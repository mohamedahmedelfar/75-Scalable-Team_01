package com.example.MiniProject1.servicetest;

import com.example.repository.UserRepository;
import com.example.model.User;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;  // Mock the repository

    @InjectMocks
    private UserService userService;  // Inject mock into service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize Mockito
    }

    @Test
    void testAddUser_WhenUserIsValid() {
        // Arrange
        User newUser = Mockito.mock(User.class); // Mock the User object
        Mockito.when(newUser.getId()).thenReturn(UUID.randomUUID()); // Mock getId()
        Mockito.when(newUser.getName()).thenReturn("John Doe"); // Mock getName()

        // Mock the behavior of the repository (even though the method is not implemented)
        Mockito.doReturn(newUser).when(userRepository).addUser(newUser); // Dynamically mock addUser

        // Act
        User result = userService.addUser(newUser);

        // Assert
        assertNotNull(result);
        assertEquals(newUser.getId(), result.getId()); // Verify the mocked getId()
        assertEquals(newUser.getName(), result.getName()); // Verify the mocked getName()

        // Verify that the repository method was called once
        Mockito.verify(userRepository, Mockito.times(1)).addUser(newUser);
    }

    @Test
    void testAddUser_WhenUserAlreadyExists() {
        // Arrange
        User existingUser = Mockito.mock(User.class); // Mock the User object
        Mockito.when(existingUser.getId()).thenReturn(UUID.randomUUID()); // Mock getId()
        Mockito.when(existingUser.getName()).thenReturn("John Doe"); // Mock getName()

        // Mock the behavior of the repository to simulate that the user already exists
        Mockito.when(userRepository.getUsers()).thenReturn((ArrayList<User>) List.of(existingUser));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(existingUser));
        assertEquals("User already exists", exception.getMessage());

        // Verify that the repository method was not called
        Mockito.verify(userRepository, Mockito.never()).addUser(existingUser);
    }

    @Test
    void testAddUser_WhenRepositoryThrowsException() {
        // Arrange
        User newUser = Mockito.mock(User.class); // Mock the User object
        Mockito.when(newUser.getId()).thenReturn(UUID.randomUUID()); // Mock getId()
        Mockito.when(newUser.getName()).thenReturn("John Doe"); // Mock getName()

        // Mock the behavior of the repository to throw an exception
        Mockito.doThrow(new RuntimeException("Database error")).when(userRepository).addUser(newUser); // Dynamically mock addUser to throw an exception

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.addUser(newUser));
        assertEquals("Database error", exception.getMessage());

        // Verify that the repository method was called once
        Mockito.verify(userRepository, Mockito.times(1)).addUser(newUser);
    }
}