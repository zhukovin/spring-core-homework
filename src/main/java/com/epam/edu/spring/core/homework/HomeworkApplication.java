package com.epam.edu.spring.core.homework;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.service.discount.BirthdayDiscountStrategy;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;
import com.epam.edu.spring.core.homework.service.discount.EveryTenthTicketDiscountStrategy;
import com.epam.edu.spring.core.homework.service.pricing.BasePricingStrategy;
import com.epam.edu.spring.core.homework.service.pricing.MovieRatingPricingStrategy;
import com.epam.edu.spring.core.homework.service.pricing.PricingStrategy;
import com.epam.edu.spring.core.homework.service.pricing.VipSeatPricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@SpringBootApplication(
        scanBasePackages = "com.epam.edu.spring.core.homework"
)
@PropertySources({
        @PropertySource("classpath:auditorium01.properties"),
        @PropertySource("classpath:auditorium02.properties")
})
public class HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

    private final Environment env;

    @Autowired
    public HomeworkApplication(Environment env) { // we do not use Lombok here to demonstrate understanding of underlying autowiring
        this.env = env;
    }

    @Bean
    public Set<Auditorium> auditoria() {
        Set<Auditorium> auditoria = new HashSet<>();
        auditoria.add(new Auditorium(prop("a1.name"), Long.valueOf(prop("a1.seats")), prop("a1.vipSeats")));
        auditoria.add(new Auditorium(prop("a2.name"), Long.valueOf(prop("a2.seats")), prop("a2.vipSeats")));
        return auditoria;
    }

    @Bean
    public List<PricingStrategy> pricingStrategies(
            BasePricingStrategy basePricingStrategy,
            VipSeatPricingStrategy vipSeatPricingStrategy,
            MovieRatingPricingStrategy movieRatingPricingStrategy
    ) {
        return asList(basePricingStrategy, vipSeatPricingStrategy, movieRatingPricingStrategy);
    }

    @Bean
    public List<DiscountStrategy> discountStrategies(
            BirthdayDiscountStrategy birthdayDiscountStrategy,
            EveryTenthTicketDiscountStrategy everyTenthTicketDiscountStrategy
    ) {
        return asList(birthdayDiscountStrategy, everyTenthTicketDiscountStrategy);
    }

    private String prop(String name) {
        return env.getProperty(name);
    }
}
