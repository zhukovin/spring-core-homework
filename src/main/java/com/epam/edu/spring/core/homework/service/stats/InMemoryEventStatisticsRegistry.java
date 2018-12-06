package com.epam.edu.spring.core.homework.service.stats;

import org.springframework.stereotype.Service;

@Service
public class InMemoryEventStatisticsRegistry implements EventStatisticsRegistry {

    private Counter<String> getNameCounter = new Counter<>();
    private Counter<String> getBasePriceCounter = new Counter<>();
    private Counter<String> bookedTicketsCounter = new Counter<>();

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
