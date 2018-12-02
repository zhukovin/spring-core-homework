package com.epam.edu.spring.core.homework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"name"})
public class Auditorium {
    private final String name;
    private final long numberOfSeats;
    private final Set<Long> vipSeats;

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
