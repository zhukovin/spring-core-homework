package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.Ticket;
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
import java.util.Set;

import static java.util.Arrays.asList;

@RequiredArgsConstructor
@ShellComponent
public class BookingCommands {

    private final BookingService bookingService;
    private final EventService eventService;
    private final UserService userService;
    private final AuditoriumService auditoriumService;

    private Set<Ticket> shoppingCart;

    @ShellMethod("Get tickets price")
    public double getPrice(String eventName, String auditoriumName, String userEmail) {
        return bookingService.getTicketsPrice(
            event(eventName),
            LocalDateTime.now(),
            user(userEmail),
            auditorium(auditoriumName),
            fixedSeats());
    }

    @ShellMethod("Reserve tickets (put them into shopping cart)")
    public Set<Ticket> reserveTickets(String eventName, String auditoriumName, String userEmail) {
        return shoppingCart = bookingService.reserveTickets(
            event(eventName),
            LocalDateTime.now(),
            user(userEmail),
            auditorium(auditoriumName),
            fixedSeats());
    }

    @ShellMethod("Book tickets")
    public Set<Ticket> bookTickets() {
        bookingService.bookTickets(shoppingCart);
        System.out.println("Booked tickets:");
        return shoppingCart;
    }

    @ShellMethod("Get event tickets for a given date")
    public Set<Ticket> getEventTickets(String eventName/*, LocalDateTime dateTime*/) {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("Booked tickets for " + eventName + " event at " + dateTime);
        return bookingService.getPurchasedTicketsForEvent(event(eventName), dateTime);
    }



    private Auditorium auditorium(String auditoriumName) {
        return auditoriumService.getByName(auditoriumName);
    }

    private User user(String userEmail) {
        return userService.getByEmail(userEmail);
    }

    private Event event(String eventName) {
        return eventService.getByName(eventName);
    }

    /**
     * Shortcut method to not ask user to enter lists etc.
     */
    private HashSet<Long> fixedSeats() {
        return new HashSet<>(asList(1L, 2L, 3L));
    }
}
