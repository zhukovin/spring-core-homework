package com.epam.edu.spring.core.homework.service.stats;

import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;

public interface DiscountStatisticsRegistry {
    void incrementNumberOfDiscountsOf(Class<? extends DiscountStrategy> discountType);
    Counter<Class<? extends DiscountStrategy>> getDiscountsCounter();
    void incrementNumberOfDiscountsFor(User user);
    Counter<User> getUserCounter();
}
