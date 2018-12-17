package com.epam.edu.spring.core.homework.service.stats;

import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;

import java.util.Map;

public class InMemoryDiscountStatisticsRegistry implements DiscountStatisticsRegistry {

    private Counter<Class<? extends DiscountStrategy>> discountTypeCounter = new Counter<>();
    private Counter<User> discountPerUserCounter = new Counter<>();

    @Override
    public void incrementNumberOfDiscountsOf(Class<? extends DiscountStrategy> discountType) {
        discountTypeCounter.increment(discountType);
    }

    @Override
    public Map<String, Long> getDiscountsCounters() {
        return discountTypeCounter.getAll();
    }

    @Override
    public void incrementNumberOfDiscountsFor(User user) {
        discountPerUserCounter.increment(user);
    }

    @Override
    public Map<String, Long> getUserCounters() {
        return discountPerUserCounter.getAll();
    }
}
