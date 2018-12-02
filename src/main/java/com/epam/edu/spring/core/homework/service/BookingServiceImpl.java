package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.Ticket;
import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.pricing.PricingStrategy;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final List<PricingStrategy> pricingStrategies;

    @Override
    public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats) {
        return pricingStrategies.stream()
            .map(strategy -> strategy.price(event, dateTime, user, auditorium, seats))
            .reduce(0d, Double::sum);
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {

    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        return null;
    }
}
