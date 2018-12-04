package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.EventRating;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
public class EventServiceImpl extends AbstractDomainObjectServiceImpl<Event> implements EventService {

    public EventServiceImpl(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public Event getByName(String name) {
        return storage.values()
            .stream()
            .filter(event -> event.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

    @Override
    public Event createEvent(String name, float basePrice, EventRating rating) {
        Event event = (Event) beanFactory.getBean("event");
        event.setName(name);
        event.setBasePrice(basePrice);
        event.setRating(rating);
        save(event);
        return event;
    }
}
