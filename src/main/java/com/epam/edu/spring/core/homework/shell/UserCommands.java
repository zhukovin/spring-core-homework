package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Collection;

@RequiredArgsConstructor
@ShellComponent
public class UserCommands {

    private final UserService userService;

    @ShellMethod("Add new user")
    public User addUser(String firstName, String lastName, String email) {
        User user = userService.createUser(firstName, lastName, email);
        System.out.println("New user created:");
        return user;
    }

    @ShellMethod("Find user by id")
    public User findUser(Long userId) {
        return reportUserFound(userService.getById(userId));
    }

    @ShellMethod("Find user by email")
    public User findUserByEmail(String email) {
        return reportUserFound(userService.getUserByEmail(email));
    }

    @ShellMethod("Remove user by id")
    public User removeUser(Long userId) {
        User user = findUser(userId);
        if (user == null) {
            return null;
        }
        userService.remove(user);
        System.out.println("User removed:");
        return user;
    }

    @ShellMethod("Show the list of all users")
    public Collection<User> listUsers() {
        return userService.getAll();
    }

    private User reportUserFound(User user) {
        if (user == null) {
            System.out.println("User not found");
            return null;
        }
        return user;
    }
}
