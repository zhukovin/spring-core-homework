package com.epam.edu.spring.core.homework.service;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.edu.spring.core.homework.domain.Auditorium;

/**
 * @author Yuriy_Tkach
 */
public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     * 
     * @return set of all auditoriums
     */
    public @Nonnull Set<Auditorium> getAll();

    /**
     * Finding auditorium by name
     * 
     * @param name
     *            Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public @Nullable Auditorium getByName(@Nonnull String name);

}
