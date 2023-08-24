package com.store.repository.users;

import com.store.config.security.model.User;

public interface UserDao {
    void saveUser(User user);
    User getUserByParameter(String parameterName,String parameter);
    void updateUserInfo(User user);
}
