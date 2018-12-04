package com.epam.edu.spring.core.homework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Data
@EqualsAndHashCode(of = {"name"})
public class Auditorium {
    private final String name;
    private final long numberOfSeats;
    private final Set<Long> vipSeats;

    public Auditorium(String name, long numberOfSeats, String vipSeatsCsv) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = asSetOfLong(vipSeatsCsv);
    }

    private Set<Long> asSetOfLong(String vipSeatsCsv) {
        return stream(vipSeatsCsv.split(",")).map(Long::new).collect(toSet());
    }


    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     * 
     * @param seats
     *            Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(vipSeats::contains).count();
    }
}
