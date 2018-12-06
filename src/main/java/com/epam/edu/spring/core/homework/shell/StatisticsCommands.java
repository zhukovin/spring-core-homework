package com.epam.edu.spring.core.homework.shell;

import com.epam.edu.spring.core.homework.service.stats.Counter;
import com.epam.edu.spring.core.homework.service.stats.DiscountStatisticsRegistry;
import com.epam.edu.spring.core.homework.service.stats.EventStatisticsRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class StatisticsCommands {

    private final EventStatisticsRegistry eventStatisticsRegistry;
    private final DiscountStatisticsRegistry discountStatisticsRegistry;

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

    @ShellMethod("Get number of discounts per discount type")
    public Counter<?> statDiscount() {
        System.out.println("Discounts per type");
        return discountStatisticsRegistry.getDiscountsCounter();
    }

    @ShellMethod("Get number of discounts per user")
    public Counter<?> statUserDiscount() {
        System.out.println("Discounts per user");
        return discountStatisticsRegistry.getUserCounter();
    }
}
