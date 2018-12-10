package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.DomainObject;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

public interface AbstractDomainObjectRegistry<T extends DomainObject> {

    /**
     * Saving new entity to storage or updating existing one
     * 
     * @param entity
     *            Object to save
     * @return saved entity with assigned id
     */
    T save(@NonNull T entity);

    /**
     * Removing entity from storage
     * 
     * @param entity
     *            Object to remove
     */
    void remove(@NonNull T entity);

    /**
     * Getting object by id from storage
     * 
     * @param id
     *            id of the object
     * @return Found object or <code>null</code>
     */
    Optional<T> getById(@NonNull Long id);

    /**
     * Getting all objects from storage
     * 
     * @return collection of objects
     */
    @NonNull Collection<T> getAll();
}
