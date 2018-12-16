package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.dao.EventRegistry;
import com.epam.edu.spring.core.homework.dao.TicketRegistry;
import com.epam.edu.spring.core.homework.dao.UserRegistry;
import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.Ticket;
import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.pricing.PricingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final List<PricingStrategy> pricingStrategies;
    private final UserRegistry userRegistry;
    private final EventRegistry eventRegistry;
    private final TicketRegistry ticketRegistry;
    private final DiscountService discountService;

    @Override
    public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats) {
        return discounted(totalPrice(event, dateTime, user, auditorium, seats), user, event, dateTime, seats.size());
    }

    private double discounted(Double totalPrice, User user, Event event, LocalDateTime dateTime, int numberOfTickets) {
        if (totalPrice < 0.01)
            return 0d;

        int discountPercentage = discountService.getDiscount(user, event, dateTime, numberOfTickets);

        double basePrice = event.getBasePrice(); // FIXME: we apply discount to the base price, which is not ideal

        double discountAmount = basePrice / 100d * discountPercentage;

        System.out.println("Applied total discount of -" + discountAmount + " coins");

        return totalPrice - discountAmount;
    }

    private Double totalPrice(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats) {
        return pricingStrategies.stream()
            .map(strategy -> strategy.price(event, dateTime, user, auditorium, seats))
            .reduce(Double::sum).orElse(0d);
    }

    @Override
    public List<Ticket> reserveTickets(
        Event event, LocalDateTime dateTime, String userEmail, Auditorium auditorium, Set<Long> seats) {
        return seats.stream().map(seat -> new Ticket(event.getName(), userEmail, dateTime, seat)).collect(toList());
    }

    @Override
    public void bookTickets(List<Ticket> tickets) {
        tickets.forEach(ticket -> {
                // FIXME: this can be optimized by grouping tickets by user and getting user from registry only once (same holds for updating the user)
                ticketRegistry.save(ticket);
                userRegistry.getByEmail(ticket.getUserEmail()).ifPresent(user -> user.addTicket(ticket));
                eventRegistry.getByName(ticket.getUserEmail()).ifPresent(event -> event.addTicket(ticket));
            }
        );
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime targetDateTime) {
        return event.getTickets().stream() // fake time comparison
            .filter(ticket -> ticket.getEventTime().isBefore(targetDateTime)).collect(toSet());
    }
}
