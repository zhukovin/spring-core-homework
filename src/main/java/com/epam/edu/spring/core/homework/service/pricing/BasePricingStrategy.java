package com.epam.edu.spring.core.homework.service.pricing;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class BasePricingStrategy implements PricingStrategy {
    @Override
    public double price(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats) {
        int numberOfSeats = seats.size();
        double basePrice = basePriceOf(event);
        double standardPrice = numberOfSeats * basePrice;
        System.out.println("Standard charge for " + numberOfSeats +
            " seats based on base price " + basePrice +
            " is " + standardPrice);
        return standardPrice;
    }
}
