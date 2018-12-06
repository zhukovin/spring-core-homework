package com.epam.edu.spring.core.homework.service.stats;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryEventStatisticsRegistry implements EventStatisticsRegistry {

    private static final long STARTING_VALUE = 0L;
    private Map<String, Long> getNameCounter = new HashMap<>();
    private Map<String, Long> getBasePriceCounter = new HashMap<>();
    private Map<String, Long> bookedTicketsCounter = new HashMap<>();

    @Override
    public void incrementNumberOfGetNameCalls(String eventName) {
        increment(getNameCounter, eventName);
    }

    @Override
    public long numberOfGetNameCalls(String eventName) {
        return getNameCounter.getOrDefault(eventName, STARTING_VALUE);
    }

    @Override
    public void incrementNumberOfGetBasePriceCalls(String eventName) {
        increment(getBasePriceCounter, eventName);
    }

    @Override
    public long numberOfGetBasePriceCalls(String eventName) {
        return getBasePriceCounter.getOrDefault(eventName, STARTING_VALUE);
    }

    @Override
    public void incrementNumberOfBookedTickets(String eventName) {
        increment(bookedTicketsCounter, eventName);
    }

    @Override
    public long numberOfBookedTickets(String eventName) {
        return bookedTicketsCounter.getOrDefault(eventName, STARTING_VALUE);
    }

    private void increment(Map<String, Long> counters, String key) {
        counters.put(key, counters.getOrDefault(key, STARTING_VALUE) + 1);
    }
}
