package com.epam.edu.spring.core.homework.service.lucky;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.User;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.System.currentTimeMillis;

@Aspect
@Component
@RequiredArgsConstructor
public class LuckyWinnerDiscounter {

    @Pointcut(
            value = "execution(double com.epam.edu.spring.core.homework.service.BookingService.getTicketsPrice(..)) && args(event,*,user,..)",
            argNames = "event,user")
    private void getTicketsPrice(Event event, User user) {
    }

    @Around(value = "getTicketsPrice(event, user)", argNames = "joinPoint, event, user")
    public Object giveLuckyDiscount(ProceedingJoinPoint joinPoint, Event event, User user) throws Throwable {
        double price = (double) joinPoint.proceed();
        double basePrice = event.getBasePrice();
        if (price < basePrice)
            return price;

        user.setLucky(userIsLucky());

        if (user.isLucky()) {
            System.out.println("Wow! You got lucky to get -" + basePrice + " coins discount! Congrats!");
            price -= basePrice;
        }

        return price;
    }

    private boolean userIsLucky() {
        return new Random(currentTimeMillis()).nextBoolean();
    }
}
