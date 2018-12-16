package com.epam.edu.spring.core.homework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class Ticket extends DomainObject {
    private final String eventName;
    private final String userEmail;
    private final LocalDateTime eventTime;
    private final long seat;

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", userEmail='" + userEmail + '\'' +
            ", eventName=" + eventName +
            ", eventTime=" + eventTime +
            ", seat=" + seat +
            '}';
    }
}
