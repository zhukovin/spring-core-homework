package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.dao.EventRegistry;
import com.epam.edu.spring.core.homework.dao.UserRegistry;
import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.Ticket;
import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.AuditoriumService;
import com.epam.edu.spring.core.homework.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

@RequiredArgsConstructor
@ShellComponent
public class BookingCommands {

    private final BookingService bookingService;
    private final EventRegistry eventRegistry;
    private final UserRegistry userRegistry;
    private final AuditoriumService auditoriumService;

    private List<Ticket> shoppingCart;

    @ShellMethod("Get tickets price")
    public double getPrice(String eventName, String auditoriumName, String userEmail) {
        return event(eventName)
                .map(event -> bookingService.getTicketsPrice(
                        event,
                        LocalDateTime.now(),
                        user(userEmail),
                        auditorium(auditoriumName),
                        fixedSeats()))
                .orElse(0d);
    }

    @ShellMethod("Reserve tickets (put them into shopping cart)")
    public List<Ticket> reserveTickets(String eventName, String auditoriumName, String userEmail) {
        return shoppingCart = event(eventName)
                .map(event -> bookingService.reserveTickets(
                        event,
                        LocalDateTime.now(),
                        userEmail,
                        auditorium(auditoriumName),
                        fixedSeats()))
                .orElse(emptyList());
    }

    @ShellMethod("Book tickets (that are in the shopping cart)")
    public List<Ticket> bookTickets() {
        bookingService.bookTickets(shoppingCart);
        System.out.println("Booked tickets:");
        return shoppingCart;
    }

    @ShellMethod("Get event tickets for a given date")
    public Set<Ticket> getEventTickets(String eventName/*, LocalDateTime dateTime*/) {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("Booked tickets for " + eventName + " event at " + dateTime);
        return event(eventName).map(event -> bookingService.getPurchasedTicketsForEvent(event, dateTime)).orElse(emptySet());
    }


    private Auditorium auditorium(String auditoriumName) {
        return auditoriumService.getByName(auditoriumName);
    }

    private User user(String userEmail) {
        return userRegistry.getByEmail(userEmail).orElseThrow(InternalError::new);
    }

    private Optional<Event> event(String eventName) {
        return eventRegistry.getByName(eventName);
    }

    /**
     * Shortcut method to not ask user to enter lists etc.
     */
    private HashSet<Long> fixedSeats() {
        return new HashSet<>(asList(1L, 2L, 3L));
    }
}
