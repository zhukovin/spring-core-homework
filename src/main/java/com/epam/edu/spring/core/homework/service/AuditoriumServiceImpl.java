package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService {

    // Injected by Spring
    private final Set<Auditorium> auditoria;

    @Override
    public Set<Auditorium> getAll() {
        return auditoria;
    }

    @Override
    public Auditorium getByName(String name) {
        return auditoria.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(null);
    }
}
