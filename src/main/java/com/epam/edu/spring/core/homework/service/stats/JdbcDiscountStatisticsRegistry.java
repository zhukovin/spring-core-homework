package com.epam.edu.spring.core.homework.service.stats;

import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

public class JdbcDiscountStatisticsRegistry implements DiscountStatisticsRegistry {

    private static final String DISCOUNT_TYPE_TABLE_NAME = "discount_type";
    private static final String TYPE_NAME = "typeName";
    private static final String COUNTER = "counter";

    private static final String DISCOUNT_PER_USER_TABLE_NAME = "discount_per_user";
    private static final String EMAIL = "email";

    private final JdbcTemplate db;
    private final JdbcCounter discountTypeCounter;
    private final JdbcCounter discountPerUserCounter;

    public JdbcDiscountStatisticsRegistry(JdbcTemplate db) {
        this.db = db;
        this.discountTypeCounter = new JdbcCounter(db, DISCOUNT_TYPE_TABLE_NAME, TYPE_NAME, COUNTER);
        this.discountPerUserCounter = new JdbcCounter(db, DISCOUNT_PER_USER_TABLE_NAME, EMAIL, COUNTER);
    }

    @PostConstruct
    private void createTableIfNeeded() {
        db.execute("create table if not exists " + DISCOUNT_TYPE_TABLE_NAME + " (" +
                TYPE_NAME + " VARCHAR(255) NOT NULL, " + COUNTER + " INTEGER)");
        db.execute("create table if not exists " + DISCOUNT_PER_USER_TABLE_NAME + " (" +
                EMAIL + " VARCHAR(255) NOT NULL, " + COUNTER + " INTEGER)");
    }


    @Override
    public void incrementNumberOfDiscountsOf(Class<? extends DiscountStrategy> discountType) {
        discountTypeCounter.increment(discountType.getSimpleName());
    }

    @Override
    public Map<String, Long> getDiscountsCounters() {
        return discountTypeCounter.getAll();
    }

    @Override
    public void incrementNumberOfDiscountsFor(User user) {
        discountPerUserCounter.increment(user.getEmail());
    }

    @Override
    public Map<String, Long> getUserCounters() {
        return discountPerUserCounter.getAll();
    }
}
