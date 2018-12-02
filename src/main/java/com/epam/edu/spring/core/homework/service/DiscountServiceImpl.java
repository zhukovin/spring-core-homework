package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private static final int NO_DISCOUNT = 0;
    private final List<DiscountStrategy> discountStrategies;

    @Override
    public int getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        return discountStrategies.stream()
            .map(toDiscount(user, event, airDateTime, numberOfTickets))
            .reduce(Integer::max)
            .orElse(NO_DISCOUNT);
    }

    private Function<DiscountStrategy, Integer> toDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        return strategy -> strategy.getDiscount(user, event, airDateTime, numberOfTickets);
    }
}
