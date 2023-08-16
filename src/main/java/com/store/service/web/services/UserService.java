package com.store.service.web.services;

import com.store.service.web.dto.contact.info.UserContactInfoDTO;
import com.store.service.web.dto.registration.UserRegistrationDto;

public interface UserService {
    void saveUser(UserRegistrationDto userRegistration);
    void updateUserInfo(String id, UserContactInfoDTO contactInfo);
}
