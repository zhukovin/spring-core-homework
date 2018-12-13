package com.epam.edu.spring.core.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Ticket extends DomainObject implements Comparable<Ticket> {
    private User user;
    private Event event;
    private final LocalDateTime eventTime;
    private final long seat;

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }
        int result = eventTime.compareTo(other.getEventTime());

        if (result == 0) {
            result = event.getName().compareTo(other.getEvent().getName());
        }
        if (result == 0) {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + getId() +
                ", userEmail='" + ofNullable(user).map(User::getEmail).orElse("null") + '\'' +
                ", event=" + ofNullable(event).map(Event::getName).orElse("null") +
                ", eventTime=" + eventTime +
                ", seat=" + seat +
                '}';
    }
}
