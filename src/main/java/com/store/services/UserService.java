package com.store.services;

import com.store.dto.contact.info.UserContactInfoDTO;
import com.store.dto.registration.UserRegistrationDto;

public interface UserService {
    void saveUser(UserRegistrationDto userRegistration);
    void updateUserInfo(String id, UserContactInfoDTO contactInfo);
}
