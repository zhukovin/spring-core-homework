package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;

@RequiredArgsConstructor
public class UserServiceImpl extends AbstractDomainObjectServiceImpl<User> implements UserService {

    private final BeanFactory beanFactory;

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
        User user = (User) beanFactory.getBean("user");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        save(user);
        return user;
    }
}
