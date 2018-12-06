package com.epam.edu.spring.core.homework.service.stats;

import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;
import org.springframework.stereotype.Service;

@Service
public class InMemoryDiscountStatisticsRegistry implements DiscountStatisticsRegistry {

    private Counter<Class<? extends DiscountStrategy>> discountTypeCounter = new Counter<>();
    private Counter<User> discountPerUserCounter = new Counter<>();

    @Override
    public void incrementNumberOfDiscountsOf(Class<? extends DiscountStrategy> discountType) {
        discountTypeCounter.increment(discountType);
    }

    @Override
    public Counter<Class<? extends DiscountStrategy>> getDiscountsCounter() {
        return discountTypeCounter;
    }

    @Override
    public void incrementNumberOfDiscountsFor(User user) {
        discountPerUserCounter.increment(user);
    }

    @Override
    public Counter<User> getUserCounter() {
        return discountPerUserCounter;
    }
}
