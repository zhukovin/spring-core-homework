package com.epam.edu.spring.core.homework.service.stats;

public interface EventStatisticsRegistry {
    void incrementNumberOfGetNameCalls(String eventName);
    long numberOfGetNameCalls(String eventName);
    void incrementNumberOfGetBasePriceCalls(String eventName);
    long numberOfGetBasePriceCalls(String eventName);
}
