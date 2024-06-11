package com.aliexpress.tsp.services;

import com.aliexpress.tsp.exceptions.NotAuthorizationException;
import com.aliexpress.tsp.model.User;
import com.aliexpress.tsp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email: " + email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким email не найден"));
    }


}
