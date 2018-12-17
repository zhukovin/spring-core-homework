package com.epam.edu.spring.core.homework.service.stats;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

public class JdbcEventStatisticsRegistry implements EventStatisticsRegistry {

    private static final String TABLE_NAME = "event_stat";
    private static final String EVENT_NAME = "eventName";

    private static final String GET_NAME_COUNTER = "getNameCounter";
    private static final String GET_BASE_PRICE_COUNTER = "getBasePriceCounter";
    private static final String BOOKED_TICKETS_COUNTER = "bookedTicketsCounter";

    private final JdbcTemplate db;
    private final JdbcCounter getNameCounter;
    private final JdbcCounter getBasePriceCounter;
    private final JdbcCounter bookedTicketsCounter;

    public JdbcEventStatisticsRegistry(JdbcTemplate db) {
        this.db = db;
        this.getNameCounter = newCounter(db, GET_NAME_COUNTER);
        this.getBasePriceCounter =  newCounter(db, GET_BASE_PRICE_COUNTER);
        this.bookedTicketsCounter =  newCounter(db, BOOKED_TICKETS_COUNTER);
    }

    @NotNull
    private JdbcCounter newCounter(JdbcTemplate db, String counterName) {
        return new JdbcCounter(db, TABLE_NAME, EVENT_NAME, counterName);
    }

    @PostConstruct
    private void createTableIfNeeded() {
        db.execute("create table if not exists " + TABLE_NAME + " (" +
                EVENT_NAME + " VARCHAR(255) NOT NULL, " +
                GET_NAME_COUNTER + " INTEGER, " +
                GET_BASE_PRICE_COUNTER + " INTEGER, " +
                BOOKED_TICKETS_COUNTER + " INTEGER " +
                ")");
    }

    @Override
    public void incrementNumberOfGetNameCalls(String eventName) {
        getNameCounter.increment(eventName);
    }

    @Override
    public long numberOfGetNameCalls(String eventName) {
        return getNameCounter.value(eventName);
    }

    @Override
    public void incrementNumberOfGetBasePriceCalls(String eventName) {
        getBasePriceCounter.increment(eventName);
    }

    @Override
    public long numberOfGetBasePriceCalls(String eventName) {
        return getBasePriceCounter.value(eventName);
    }

    @Override
    public void incrementNumberOfBookedTickets(String eventName) {
        bookedTicketsCounter.increment(eventName);
    }

    @Override
    public long numberOfBookedTickets(String eventName) {
        return bookedTicketsCounter.value(eventName);
    }
}
