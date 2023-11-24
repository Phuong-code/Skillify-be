package com.fdmgroup.skillify.service;

import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.enums.RoleType;
import com.fdmgroup.skillify.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDetailsServiceImplTest {

    @MockBean
    AppUserRepository mockAppUserRepository;

    @Test
    void test_loadUserByUsername_success() {
        // Arrange
        String username = "username";
        AppUser appUser = new AppUser();
        appUser.setEmail(username);
        appUser.setPassword("password");
        appUser.setRole(RoleType.TRAINEE);
        UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl();
        userDetailsServiceImpl.appUserRepository = mockAppUserRepository;
        when(mockAppUserRepository.findByEmail(username)).thenReturn(Optional.of(appUser));

        // Act
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        // Assert
        assertEquals(username, userDetails.getUsername());
        assertEquals(appUser.getPassword(), userDetails.getPassword());
        assertEquals("ROLE_" + appUser.getRole().toString(), userDetails.getAuthorities().toArray()[0].toString());
        verify(mockAppUserRepository).findByEmail(username);
    }

    @Test
    void test_loadUserByUsername_fail() {
        // Arrange
        String username = "username";
        UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl();
        userDetailsServiceImpl.appUserRepository = mockAppUserRepository;
        when(mockAppUserRepository.findByEmail(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername(username));
        verify(mockAppUserRepository).findByEmail(username);
    }
}