package com.epam.edu.spring.core.homework.service.stats;

import com.epam.edu.spring.core.homework.domain.User;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class DiscountStatisticsCollector {

    private final DiscountStatisticsRegistry discountStatisticsRegistry;

    @Pointcut("execution(int com.epam.edu.spring.core.homework.service.discount.DiscountStrategy.getDiscount(..)) && args(user,..)")
    private void getDiscount(User user) {
    }

    @AfterReturning(pointcut = "getDiscount(user)", argNames = "joinPoint, user, discount", returning = "discount")
    public void countDiscount(JoinPoint joinPoint, User user, int discount) {
        if (discount <= 0)
            return;
        System.out.println(user.getFirstName() + "'s got " + discount + "% discount");
        discountStatisticsRegistry.incrementNumberOfDiscountsOf(discountType(joinPoint));
        discountStatisticsRegistry.incrementNumberOfDiscountsFor(user);
    }

    private Class<? extends DiscountStrategy> discountType(JoinPoint joinPoint) {
        return (Class<? extends DiscountStrategy>) joinPoint.getTarget().getClass();
    }
}
