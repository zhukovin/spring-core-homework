package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.AuditoriumService;
import com.epam.edu.spring.core.homework.service.BookingService;
import com.epam.edu.spring.core.homework.service.EventService;
import com.epam.edu.spring.core.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.util.HashSet;

import static java.util.Arrays.asList;

@RequiredArgsConstructor
@ShellComponent
public class BookingCommands {

    private final BookingService bookingService;
    private final EventService eventService;
    private final UserService userService;
    private final AuditoriumService auditoriumService;

    @ShellMethod("Get tickets price")
    public double getPrice(String eventName, String auditoriumName, String userEmail) {

        Event event = eventService.getByName(eventName);
        User user = userService.getByEmail(userEmail);
        Auditorium auditorium = auditoriumService.getByName(auditoriumName);

        return bookingService.getTicketsPrice(event, LocalDateTime.now(), user, auditorium, new HashSet<>(asList(1L,2L,3L)));
    }
}
