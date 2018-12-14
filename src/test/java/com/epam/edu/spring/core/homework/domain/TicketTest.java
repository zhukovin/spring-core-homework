package com.epam.edu.spring.core.homework.domain;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class TicketTest {

    @Test
    public void ticketsAreEqualWhenTheirIdsAreEqual() {
        Ticket ticket = new Ticket(LocalDateTime.now(), 1);
        ticket.setId(1L);
        Ticket ticket2 = new Ticket(LocalDateTime.of(1999, Month.APRIL, 1, 0, 0), 7);
        ticket2.setId(1L);
        assertThat(ticket).isEqualTo(ticket2);
        ticket2.setId(2L);
        assertThat(ticket).isNotEqualTo(ticket2);
    }
}
