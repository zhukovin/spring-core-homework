package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;


@Service
public class UserServiceImpl extends AbstractDomainObjectServiceImpl<User> implements UserService {

    public UserServiceImpl(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public User getByEmail(String email) {
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
        user.setBirthday(now()); // fake birthday to ease testing
        save(user);
        return user;
    }
}
