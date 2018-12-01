package com.epam.edu.spring.core.homework.shell;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

import static java.util.Arrays.asList;

@ShellComponent
public class TheaterCommands {

    @ShellMethod("Show the list of all theaters")
    public List<String> listTheaters() {
        return asList("Broadway 5", "Main Street 17");
    }
}
