package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.DomainObject;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author Yuriy_Tkach
 *
 * @param <T>
 *            DomainObject subclass
 */
public interface AbstractDomainObjectService<T extends DomainObject> {

    /**
     * Saving new object to storage or updating existing one
     * 
     * @param object
     *            Object to save
     * @return saved object with assigned id
     */
    T save(@NonNull T object);

    /**
     * Removing object from storage
     * 
     * @param object
     *            Object to remove
     */
    void remove(@NonNull T object);

    /**
     * Getting object by id from storage
     * 
     * @param id
     *            id of the object
     * @return Found object or <code>null</code>
     */
    T getById(@NonNull Long id);

    /**
     * Getting all objects from storage
     * 
     * @return collection of objects
     */
    @NonNull Collection<T> getAll();
}
