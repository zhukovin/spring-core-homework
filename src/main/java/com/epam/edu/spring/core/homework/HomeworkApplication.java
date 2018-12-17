package com.epam.edu.spring.core.homework;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import com.epam.edu.spring.core.homework.service.discount.BirthdayDiscountStrategy;
import com.epam.edu.spring.core.homework.service.discount.DiscountStrategy;
import com.epam.edu.spring.core.homework.service.discount.EveryTenthTicketDiscountStrategy;
import com.epam.edu.spring.core.homework.service.pricing.BasePricingStrategy;
import com.epam.edu.spring.core.homework.service.pricing.MovieRatingPricingStrategy;
import com.epam.edu.spring.core.homework.service.pricing.PricingStrategy;
import com.epam.edu.spring.core.homework.service.pricing.VipSeatPricingStrategy;
import com.epam.edu.spring.core.homework.service.stats.DiscountStatisticsRegistry;
import com.epam.edu.spring.core.homework.service.stats.EventStatisticsRegistry;
import com.epam.edu.spring.core.homework.service.stats.JdbcDiscountStatisticsRegistry;
import com.epam.edu.spring.core.homework.service.stats.JdbcEventStatisticsRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

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
    private final BeanFactory beanFactory;

    @Autowired
    public HomeworkApplication(Environment env, BeanFactory beanFactory) { // we do not use Lombok here to demonstrate understanding of underlying autowiring
        this.env = env;
        this.beanFactory = beanFactory;
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

    @Bean
    public DiscountStatisticsRegistry discountStatisticsRegistry() {
        return new JdbcDiscountStatisticsRegistry(beanFactory.getBean(JdbcTemplate.class)); // <- Manually inject JdbcTemplate
//        return new InMemoryDiscountStatisticsRegistry();
    }

    @Bean
    public EventStatisticsRegistry eventStatisticsRegistry(JdbcTemplate db) { // <- Spring is clever enough to inject JdbcTemplate here
        return new JdbcEventStatisticsRegistry(db);
//        return new InMemoryDiscountStatisticsRegistry();
    }

    private String prop(String name) {
        return env.getProperty(name);
    }
}
