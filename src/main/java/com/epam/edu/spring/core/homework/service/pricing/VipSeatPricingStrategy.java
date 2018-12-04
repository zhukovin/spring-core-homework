package com.epam.edu.spring.core.homework.service.pricing;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class VipSeatPricingStrategy implements PricingStrategy {
    @Override
    public double price(Event event, LocalDateTime dateTime, User user, Auditorium auditorium, Set<Long> seats) {
        // Add basePrice on top of the standard one to get 2x more expensive VIP tickets
        long numberOfVipSeats = auditorium.countVipSeats(seats);
        double basePrice = basePriceOf(event);
        double extraCharge = numberOfVipSeats * basePrice;
        System.out.println("Extra charge for " + numberOfVipSeats +
            " VIP seats based on base price " + basePrice +
            " is " + extraCharge);
        return extraCharge;
    }
}
