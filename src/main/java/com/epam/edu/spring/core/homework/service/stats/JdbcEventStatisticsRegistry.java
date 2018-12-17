package com.epam.edu.spring.core.homework.service.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class JdbcEventStatisticsRegistry implements EventStatisticsRegistry {

    private static final String EVENT_NAME = "eventName";
    private static final String GET_NAME_COUNTER = "getNameCounter";
    private static final String GET_BASE_PRICE_COUNTER = "getBasePriceCounter";
    private static final String BOOKED_TICKETS_COUNTER = "bookedTicketsCounter";
    private static final String TABLE_NAME = "event_stat";

    private final JdbcTemplate db;

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
        increment(eventName, GET_NAME_COUNTER);
    }

    @Override
    public long numberOfGetNameCalls(String eventName) {
        return counterValue(eventName, GET_NAME_COUNTER);
    }

    @Override
    public void incrementNumberOfGetBasePriceCalls(String eventName) {
        increment(eventName, GET_BASE_PRICE_COUNTER);
    }

    @Override
    public long numberOfGetBasePriceCalls(String eventName) {
        return counterValue(eventName, GET_BASE_PRICE_COUNTER);
    }

    @Override
    public void incrementNumberOfBookedTickets(String eventName) {
        increment(eventName, BOOKED_TICKETS_COUNTER);
    }

    @Override
    public long numberOfBookedTickets(String eventName) {
        return counterValue(eventName, BOOKED_TICKETS_COUNTER);
    }

    private void increment(String eventName, String counterName) {
        setCounter(eventName, counterName, counterValue(eventName, counterName) + 1);
    }

    private void setCounter(String eventName, String counterName, long value) {
        assertSingleRowUpdated(
                db.update("UPDATE " + TABLE_NAME + " SET " + counterName + " = ? WHERE " + EVENT_NAME + " = ?",
                        value, eventName));
    }

    private void assertSingleRowUpdated(int numberOfUpdatedRows) {
        if (numberOfUpdatedRows != 1)
            throw new InternalError("Counter update must affect one row exactly");
    }

    private long counterValue(String eventName, String counterName) {
        try {
            return ofNullable(db.queryForObject("SELECT " + counterName + " FROM " + TABLE_NAME +
                    " WHERE " + EVENT_NAME + " = ?", new Object[]{eventName}, Long.class)).orElse(0L);
        } catch (IncorrectResultSizeDataAccessException e) {
            db.update("INSERT INTO " + TABLE_NAME + " (" + EVENT_NAME + ") VALUES (?)", eventName);
            return 0;
        }
    }
}
