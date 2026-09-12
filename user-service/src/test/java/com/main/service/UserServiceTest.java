package com.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.main.model.UserModel;
import com.main.repository.UserDto;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDto dto;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserByIdReturnsUserFromRepository() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setEmail("user@example.com");
        when(dto.findById(1L)).thenReturn(Optional.of(user));

        UserModel result = userService.getUserById(1L);

        assertEquals(user, result);
        verify(dto).findById(1L);
    }

    @Test
    void deleteUserDeletesExistingUser() {
        when(dto.existsById(1L)).thenReturn(true);

        String result = userService.deleteUser(1L);

        assertEquals("Deleted User", result);
        verify(dto).deleteById(1L);
    }

    @Test
    void deleteUserReturnsNotFoundWhenUserDoesNotExist() {
        when(dto.existsById(1L)).thenReturn(false);

        String result = userService.deleteUser(1L);

        assertEquals("User not found", result);
    }

    @Test
    void verifyUserReturnsJwtWhenAuthenticationSucceeds() {
        UserModel user = new UserModel();
        user.setEmail("user@example.com");
        user.setPassword("password");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken("user@example.com")).thenReturn("jwt-token");

        String result = userService.verifyUser(user);

        assertEquals("jwt-token", result);
        ArgumentCaptor<UsernamePasswordAuthenticationToken> tokenCaptor =
            ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(tokenCaptor.capture());
        assertEquals("user@example.com", tokenCaptor.getValue().getName());
        assertEquals("password", tokenCaptor.getValue().getCredentials());
        verify(jwtService).generateToken("user@example.com");
    }
}