package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.dao.UserRegistry;
import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@ShellComponent
public class UserCommands {

    private final UserService userService;
    private final UserRegistry userRegistry;

    @ShellMethod("Add new user")
    public User addUser(String firstName, String lastName, String email) {
        User user = userService.createUser(firstName, lastName, email);
        System.out.println("New user created:");
        return user;
    }

    @ShellMethod("Find user by id")
    public Optional<User> findUser(Long userId) {
        return reportUserFound(userRegistry.getById(userId));
    }

    @ShellMethod("Find user by email")
    public Optional<User> findUserByEmail(String email) {
        return reportUserFound(userRegistry.getByEmail(email));
    }

    @ShellMethod("Remove user by id")
    public Optional<User> removeUser(Long userId) {
        Optional<User> user = findUser(userId);
        if (!user.isPresent()) {
            return Optional.empty();
        }
        userRegistry.remove(user.get());
        System.out.println("User removed:");
        return user;
    }

    @ShellMethod("Show the list of all users")
    public Collection<User> listUsers() {
        return userRegistry.getAll();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<User> reportUserFound(Optional<User> user) {
        if (!user.isPresent()) {
            System.out.println("User not found");
        }
        return user;
    }
}
