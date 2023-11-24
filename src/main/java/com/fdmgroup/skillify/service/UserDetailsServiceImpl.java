package com.fdmgroup.skillify.service;

import com.fdmgroup.skillify.entity.AppUser;
import com.fdmgroup.skillify.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class implements the UserDetailsService interface.
 * It is used to load user-specific data from the database for Spring Security.
 * It is used by the JwtRequestFilter class.
 * @see com.fdmgroup.skillify.config.JwtRequestFilter
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AppUserRepository appUserRepository;

    /**
     * This method is used to load user-specific data from the database for Spring Security.
     * @param username The username of the user.
     * @return UserDetails object containing the user-specific data.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + username));
        UserDetails userDetails = User.builder()
                .username(appUser.getEmail())
                .password(appUser.getPassword())
                .roles(appUser.getRole().toString())
                .build();
        return userDetails;
    }
}
