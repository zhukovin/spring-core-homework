package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.Auditorium;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService {

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
