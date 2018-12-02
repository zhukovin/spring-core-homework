package com.epam.edu.spring.core.homework.service.discount;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;

import java.time.LocalDateTime;

public interface DiscountStrategy {
    /**
     * Returns percentage of the discount (0..100)
     */
    int getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets);
}
