package com.epam.edu.spring.core.homework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends DomainObject {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthday;
    private boolean isLucky;

    @Setter(PRIVATE)
    private List<Ticket> tickets = new ArrayList<>();

    public List<Ticket> getTickets() {
        return Collections.unmodifiableList(tickets);
    }

    public void addTicket(Ticket ticket) {
        if (!tickets.contains(ticket))
            tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }
}
