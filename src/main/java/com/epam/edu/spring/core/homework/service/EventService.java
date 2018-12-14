package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.EventRating;

public interface EventService {
    Event createEvent(String name, float basePrice, EventRating rating);
}
