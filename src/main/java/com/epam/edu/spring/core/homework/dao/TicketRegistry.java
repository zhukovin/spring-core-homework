package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.Ticket;
import com.epam.edu.spring.core.homework.domain.User;

import java.util.List;

public interface TicketRegistry extends DomainEntityRegistry<Ticket> {
    List<Ticket> getByUser(User user);
    List<Ticket> getByEvent(Event event);
}
