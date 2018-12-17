package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.service.stats.DiscountStatisticsRegistry;
import com.epam.edu.spring.core.homework.service.stats.EventStatisticsRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Map;

@ShellComponent
@RequiredArgsConstructor
public class StatisticsCommands {

    private final EventStatisticsRegistry eventStatisticsRegistry;
    private final DiscountStatisticsRegistry discountStatisticsRegistry;

    @ShellMethod("Get number of Event.getName() calls")
    public Long statEventGetName(String eventName) {
        System.out.println("Number of Event.getName() calls:");
        return eventStatisticsRegistry.numberOfGetNameCalls(eventName);
    }

    @ShellMethod("Get number of Event.getBasePrice() calls")
    public Long statEventGetPrice(String eventName) {
        System.out.println("Number of Event.getBasePrice() calls:");
        return eventStatisticsRegistry.numberOfGetBasePriceCalls(eventName);
    }

    @ShellMethod("Get number of booked tickets")
    public Long statEventTickets(String eventName) {
        System.out.println("Number of booked tickets:");
        return eventStatisticsRegistry.numberOfBookedTickets(eventName);
    }

    @ShellMethod("Get number of discounts per discount type")
    public Map<String, Long> statDiscount() {
        System.out.println("Discounts per type:");
        return discountStatisticsRegistry.getDiscountsCounters();
    }

    @ShellMethod("Get number of discounts per user")
    public Map<String, Long> statUserDiscount() {
        System.out.println("Discounts per user:");
        return discountStatisticsRegistry.getUserCounters();
    }
}
