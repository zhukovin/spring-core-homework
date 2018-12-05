package com.epam.edu.spring.core.homework.service.stats;

import com.epam.edu.spring.core.homework.domain.Event;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class EventStatisticsCollector {

    private final EventStatisticsRegistry eventStatisticsRegistry;

    @Pointcut("execution(String com.epam.edu.spring.core.homework.domain.Event.getName())")
    private void getEventName() {}

    @Pointcut("execution(double com.epam.edu.spring.core.homework.domain.Event.getBasePrice())")
    private void getEventBasePrice() {}

    @AfterReturning(pointcut = "getEventName()", returning = "eventName")
    public void countGetName(String eventName) {
        System.out.println("Somebody got event's name: " + eventName);
        eventStatisticsRegistry.incrementNumberOfGetNameCalls(eventName);
    }

    @Before("getEventBasePrice()")
    public void countGetBasePrice(JoinPoint joinPoint) {
        System.out.println("Somebody requested event's price.");
        eventStatisticsRegistry.incrementNumberOfGetBasePriceCalls(event(joinPoint).getName());
    }

    private Event event(JoinPoint joinPoint) {
        return (Event) joinPoint.getTarget();
    }
}
