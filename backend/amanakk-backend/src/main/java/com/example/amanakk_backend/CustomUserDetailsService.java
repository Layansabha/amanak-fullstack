package com.example.amanakk_backend;

import com.example.amanakk_backend.UserAccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Primary

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountsRepository userAccountsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountsRepository.findByPhoneNumberOrNationalId(username, username)
                .map(user -> new User(user.getPhoneNumber(), user.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
