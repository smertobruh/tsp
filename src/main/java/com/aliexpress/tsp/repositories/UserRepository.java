package com.aliexpress.tsp.repositories;

import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("from User u where u.id = :id")
    User getUserById(@Param("id") Long id);
}
