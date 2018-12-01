package com.epam.edu.spring.core.homework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.NavigableSet;
import java.util.TreeSet;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends DomainObject {
    private String firstName;
    private String lastName;
    private String email;
    private NavigableSet<Ticket> tickets = new TreeSet<>();
}
