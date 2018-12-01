package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.User;

public class UserServiceImpl extends AbstractDomainObjectServiceImpl<User> implements UserService {
    @Override
    public User getUserByEmail(String email) {
        return storage.values()
            .stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }

    @Override
    public User createUser(String firstName, String lastName, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        save(user);
        return user;
    }
}
