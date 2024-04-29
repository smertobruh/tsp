package com.aliexpress.tsp.services;

import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.User;
import com.aliexpress.tsp.model.enums.Role;
import com.aliexpress.tsp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user){
        String email = user.getEmail();
        if(userRepository.findByEmail(email) != null) return false;
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("Saved new user with email: {}", email);
        return true;
    }

    public Optional<User> findUserByID(Long id){
        return userRepository.findById(id);
    }
}
