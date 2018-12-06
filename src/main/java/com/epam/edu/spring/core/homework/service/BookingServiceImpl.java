package com.epam.edu.spring.core.homework.service;

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

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final List<PricingStrategy> pricingStrategies;
    private final UserService userService;
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

        System.out.println("Applied total discount of -" + discountAmount);

        return totalPrice - discountAmount;
    }

    private Double totalPrice(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats) {
        return pricingStrategies.stream()
            .map(strategy -> strategy.price(event, dateTime, user, auditorium, seats))
            .reduce(Double::sum).orElse(0d);
    }

    @Override
    public Set<Ticket> reserveTickets(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats) {
        return seats.stream().map(seat -> new Ticket(user.getEmail(), event, dateTime, seat)).collect(toSet());
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        tickets.forEach(ticket -> {
                User user = userService.getByEmail(ticket.getUserEmail());
                if (user != null) {
                    user.getTickets().add(ticket);
                }

                ticket.getEvent().book(ticket);
            }
        );
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime targetDateTime) {
        return event.getTickets().stream() // fake time comparison
            .filter(ticket -> ticket.getDateTime().isBefore(targetDateTime)).collect(toSet());
    }
}
