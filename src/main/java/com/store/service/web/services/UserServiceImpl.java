package com.store.service.web.services;

import com.store.dao.model.users.Role;
import com.store.dao.model.users.RoleEnum;
import com.store.dao.model.users.User;
import com.store.dao.repository.users.RoleDao;
import com.store.dao.repository.users.UserDao;
import com.store.service.web.dto.contact.info.UserContactInfoDTO;
import com.store.service.web.dto.registration.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;


    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {

        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public void saveUser(UserRegistrationDto userRegistration) {
        User user = new User();
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmail(userRegistration.getEmail());
        user.setPassword(userRegistration.getPassword());
        user.setRoles();

        Role role = roleDao.findByRole(RoleEnum.USER.getRole());
        user.addRole(role);

        userDao.saveUser(user);
    }

    @Override
    public void updateUserInfo(String id, UserContactInfoDTO contactInfo) {
        User user = userDao.getUserByParameter("id",id);
        int changesChecker = 0;
        if (contactInfo.getFirstName() != null && !contactInfo.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(contactInfo.getFirstName());
            changesChecker++;
        }
        if (contactInfo.getLastName() != null && !contactInfo.getLastName().equals(user.getLastName())) {
            user.setLastName(contactInfo.getLastName());
            changesChecker++;
        }
        if (contactInfo.getPatronymic() != null && !contactInfo.getPatronymic().equals(user.getPatronymic())) {
            user.setPatronymic(contactInfo.getPatronymic());
            changesChecker++;
        }
        if (contactInfo.getEmail() != null && !contactInfo.getEmail().equals(user.getEmail())) {
            user.setEmail(contactInfo.getEmail());
            changesChecker++;
        }
        if (contactInfo.getPhone() != null && !contactInfo.getPhone().equals(user.getPhone())) {
            user.setPhone(contactInfo.getPhone());
            changesChecker++;
        }
        if (changesChecker != 0){
            userDao.updateUserInfo(user);
        }
    }
}
