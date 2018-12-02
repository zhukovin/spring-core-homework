package com.epam.edu.spring.core.homework.service.pricing;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.EventRating;
import com.epam.edu.spring.core.homework.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

public class MovieRatingPricingStrategy implements PricingStrategy {
    @Override
    public double price(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats) {
        if (event.getRating() == EventRating.HIGH) {
            int numberOfSeats = seats.size();
            double basePrice = basePriceOf(event);
            double extraCharge = numberOfSeats * 0.2 * basePrice;
            System.out.println("Extra charge for " + numberOfSeats +
                " seats on high rated movie based on base price " + basePrice +
                " is " + extraCharge);
            return extraCharge;
        }
        return 0;
    }
}
