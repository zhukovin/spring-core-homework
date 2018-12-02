package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.EventRating;
import com.epam.edu.spring.core.homework.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Collection;

@RequiredArgsConstructor
@ShellComponent
public class EventCommands {

    private final EventService eventService;

    @ShellMethod("Add new event")
    public Event addEvent(String name, float basePrice, EventRating rating) {
        Event event = eventService.createEvent(name, basePrice, rating);
        System.out.println("New event created:");
        return event;
    }

    @ShellMethod("Find event by id")
    public Event findEvent(Long id) {
        return reportIfNotFound(eventService.getById(id));
    }

    @ShellMethod("Find event by name")
    public Event findEventByName(String name) {
        return reportIfNotFound(eventService.getByName(name));
    }

    @ShellMethod("Remove event by id")
    public Event removeEvent(Long id) {
        Event event = findEvent(id);
        if (event == null) {
            return null;
        }
        eventService.remove(event);
        System.out.println("Event removed:");
        return event;
    }

    @ShellMethod("Show the list of all events")
    public Collection<Event> listEvents() {
        return eventService.getAll();
    }

    private Event reportIfNotFound(Event event) {
        if (event == null) {
            System.out.println("Event not found");
            return null;
        }
        return event;
    }
}
