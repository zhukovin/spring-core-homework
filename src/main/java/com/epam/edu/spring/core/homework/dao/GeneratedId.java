package com.epam.edu.spring.core.homework.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import static java.util.Optional.ofNullable;

public class GeneratedId extends GeneratedKeyHolder {
    @NotNull
    public Long get() {
        return ofNullable(getKey()).map(Number::longValue).orElseThrow(InternalError::new);
    }
}
