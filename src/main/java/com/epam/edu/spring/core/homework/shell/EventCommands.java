package com.epam.edu.spring.core.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;

@RequiredArgsConstructor
@ShellComponent
public class EventCommands {
/*
    private final EventService eventService;

    @ShellMethod("Add new event")
    public Event addEvent(String firstName, String lastName, String email) {
        User user = eventService.createUser(firstName, lastName, email);
        System.out.println("New user created:");
        return user;
    }

    @ShellMethod("Find user by id")
    public User findUser(Long userId) {
        return reportUserFound(eventService.getById(userId));
    }

    @ShellMethod("Find user by email")
    public User findUserByEmail(String email) {
        return reportUserFound(eventService.getUserByEmail(email));
    }

    @ShellMethod("Remove user by id")
    public User removeUser(Long userId) {
        User user = findUser(userId);
        if (user == null) {
            return null;
        }
        eventService.remove(user);
        System.out.println("User removed:");
        return user;
    }

    @ShellMethod("Show the list of all users")
    public Collection<User> listUsers() {
        return eventService.getAll();
    }

    private User reportUserFound(User user) {
        if (user == null) {
            System.out.println("User not found");
            return null;
        }
        return user;
    }
    */
}
