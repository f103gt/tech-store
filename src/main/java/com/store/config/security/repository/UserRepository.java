package com.store.config.security.repository;

import com.store.config.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {
    User getUserByEmail(String email);

    @Query("select u from User u where u.email = :email")
    Optional<User> getUserByUsername(@Param("email") String email);

    User getUserByPassword(String password);

    User getUserById(BigInteger userId);

    Optional<User> findUserById(BigInteger userId);
}
