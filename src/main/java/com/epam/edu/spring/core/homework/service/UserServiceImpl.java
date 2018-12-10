package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.dao.UserRegistry;
import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;


@Service
public class UserServiceImpl extends AbstractDomainObjectServiceImpl<User> implements UserService {

    private final UserRegistry userRegistry;

    public UserServiceImpl(BeanFactory beanFactory, UserRegistry userRegistry) {
        super(beanFactory);
        this.userRegistry = userRegistry;
    }

    @Override
    public User createUser(String firstName, String lastName, String email) {
        User user = (User) beanFactory.getBean("user");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthday(now()); // fake birthday to ease testing
        userRegistry.save(user);
        return user;
    }
}
