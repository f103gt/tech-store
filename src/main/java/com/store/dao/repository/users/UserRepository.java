package com.store.dao.repository.users;

import com.store.dao.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface UserRepository extends JpaRepository<User, BigInteger> {
    User getUserByEmail(String email);
    User getUserByPassword(String password);
}
