package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.dao.EventRegistry;
import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.EventRating;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final BeanFactory beanFactory;
    private final EventRegistry eventRegistry;

    @Override
    public Event createEvent(String name, float basePrice, EventRating rating) {
        Event event = (Event) beanFactory.getBean("event");
        event.setName(name);
        event.setBasePrice(basePrice);
        event.setRating(rating);
        return eventRegistry.save(event);
    }
}
