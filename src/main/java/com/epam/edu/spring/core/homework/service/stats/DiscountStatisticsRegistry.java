package com.epam.edu.spring.core.homework.service.stats;

import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;

import java.util.Map;

public interface DiscountStatisticsRegistry {
    void incrementNumberOfDiscountsOf(Class<? extends DiscountStrategy> discountType);

    /**
     * Returns counters of all discount types, e.g. "BirthdayDiscountStrategy -> 15", "EveryTenthTicketDiscountStrategy -> 2".
     */
    Map<String, Long> getDiscountsCounters();

    void incrementNumberOfDiscountsFor(User user);

    /**
     * Returns counters of all users, e.g. "a@z.com -> 4", "m@j.org -> 2".
     */
    Map<String, Long> getUserCounters();
}
