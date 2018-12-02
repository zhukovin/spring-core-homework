package com.epam.edu.spring.core.homework.service.pricing;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

public interface PricingStrategy {
    double price(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats);

    default double basePriceOf(Event event) {
        return event.getBasePrice();
    }
}
