package com.epam.edu.spring.core.homework.domain;

import java.util.Arrays;
import java.util.HashSet;

import static java.util.stream.Collectors.toSet;

public class VipSeats extends HashSet<Long> {
    public VipSeats(String list) {
        super(Arrays.stream(list.split(",")).map(Long::new).collect(toSet()));
    }
}
