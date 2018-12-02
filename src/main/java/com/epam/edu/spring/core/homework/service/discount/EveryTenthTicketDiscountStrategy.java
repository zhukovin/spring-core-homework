package com.epam.edu.spring.core.homework.service.discount;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.Optional.ofNullable;

public class EveryTenthTicketDiscountStrategy implements DiscountStrategy {

    @Override
    public int getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        // FIXME: this is wrong:
        return (int) ((alreadyBoughtTicketsEligibleForDiscount(user) + numberOfTickets) / 10) * 50; // can run over 100% - it's OK
    }

    private int alreadyBoughtTicketsEligibleForDiscount(User user) {
        return ofNullable(user).map(User::getTickets).map(Set::size).map(n -> n % 10).orElse(0);
    }
}
