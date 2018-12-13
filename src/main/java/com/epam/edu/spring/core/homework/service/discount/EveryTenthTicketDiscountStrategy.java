package com.epam.edu.spring.core.homework.service.discount;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class EveryTenthTicketDiscountStrategy implements DiscountStrategy {

    @Override
    public int getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        // FIXME: this is wrong:
        return (int) ((alreadyBoughtTicketsEligibleForDiscount(user) + numberOfTickets) / 10) * 50; // can run over 100% - it's OK
    }

    private int alreadyBoughtTicketsEligibleForDiscount(User user) {
        return ofNullable(user).map(User::getTickets).map(List::size).map(n -> n % 10).orElse(0);
    }
}
