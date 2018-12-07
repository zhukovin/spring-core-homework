package com.epam.edu.spring.core.homework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.TreeSet;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends DomainObject {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthday;
    private NavigableSet<Ticket> tickets = new TreeSet<>();
    private boolean isLucky;
}
