package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.Event;

import java.util.Optional;

public interface EventRegistry extends DomainEntityRegistry<Event> {
    Optional<Event> getByName(String name);
}
