package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email);
}
