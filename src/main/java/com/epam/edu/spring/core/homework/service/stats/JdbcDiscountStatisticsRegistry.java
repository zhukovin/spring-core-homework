package com.epam.edu.spring.core.homework.service.stats;

import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class JdbcDiscountStatisticsRegistry implements DiscountStatisticsRegistry {

    private final JdbcTemplate jdbcTemplate;

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
