package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRegistry extends DomainEntityRegistry<User> {
    /**
     * Finding user by email
     *
     * @param email Email of the user
     * @return found user or <code>null</code>
     */
    Optional<User> getByEmail(@NonNull String email);
}
