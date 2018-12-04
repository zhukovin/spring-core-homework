package com.epam.edu.spring.core.homework.service.discount;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class BirthdayDiscountStrategy implements DiscountStrategy {
    @Override
    public int getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
        if (user == null)
            return 0;

        if (DAYS.between(airDateTime, user.getBirthday()) <= 5) {
            System.out.println("You get 5% discount because it's your birthday!");
            return 5; // 5%
        }
        else
            return 0;
    }
}
