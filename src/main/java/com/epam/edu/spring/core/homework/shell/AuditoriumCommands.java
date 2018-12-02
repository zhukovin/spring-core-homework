package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.service.AuditoriumService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Collection;

@RequiredArgsConstructor
@ShellComponent
public class AuditoriumCommands {

    private final AuditoriumService auditoriumService;

    @ShellMethod("Find auditorium by name")
    public Auditorium findAudByName(String name) {
        return reportIfNotFound(auditoriumService.getByName(name));
    }

    @ShellMethod("Show the list of all auditoria")
    public Collection<Auditorium> listAuds() {
        return auditoriumService.getAll();
    }

    private Auditorium reportIfNotFound(Auditorium event) {
        if (event == null) {
            System.out.println("Auditorium not found");
            return null;
        }
        return event;
    }
}
