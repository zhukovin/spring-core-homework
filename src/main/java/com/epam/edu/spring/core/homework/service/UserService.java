package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     *
     * @param email Email of the user
     * @return found user or <code>null</code>
     */
    @Nullable
    User getUserByEmail(@NonNull String email);

    User createUser(String firstName, String lastName, String email);

}
