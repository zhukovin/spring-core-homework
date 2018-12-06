package com.epam.edu.spring.core.homework.service.stats;

import java.util.HashMap;

public class Counter<T> extends HashMap<T, Long> {

    public void increment(T key) {
        put(key, value(key) + 1);
    }

    public Long value(T key) {
        return getOrDefault(key, 0L);
    }
}
