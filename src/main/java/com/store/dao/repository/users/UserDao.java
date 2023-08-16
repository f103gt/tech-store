package com.store.dao.repository.users;

import com.store.dao.model.users.User;

public interface UserDao {
    void saveUser(User user);
    User getUserByParameter(String parameterName,String parameter);
    void updateUserInfo(User user);
}
