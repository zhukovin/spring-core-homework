package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.service.stats.EventStatisticsRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class StatisticsCommands {

    private final EventStatisticsRegistry eventStatisticsRegistry;

    @ShellMethod("Get number of Event.getName() calls")
    public Long statEventGetName(String eventName) {
        return eventStatisticsRegistry.numberOfGetNameCalls(eventName);
    }

    @ShellMethod("Get number of Event.getBasePrice() calls")
    public Long statEventGetPrice(String eventName) {
        return eventStatisticsRegistry.numberOfGetBasePriceCalls(eventName);
    }

    @ShellMethod("Get number of booked tickets")
    public Long statEventTickets(String eventName) {
        return eventStatisticsRegistry.numberOfBookedTickets(eventName);
    }
}
