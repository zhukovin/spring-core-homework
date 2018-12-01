package com.epam.edu.spring.core.homework.service;

import com.epam.edu.spring.core.homework.domain.DomainObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AbstractDomainObjectServiceImpl<T extends DomainObject> implements AbstractDomainObjectService<T> {

    protected final Map<Long, T> storage = new HashMap<>();

    @Override
    public T save(T object) {
        long id = object.hashCode();
        object.setId(id);
        storage.put(id, object);
        return object;
    }

    @Override
    public void remove(T object) {
        storage.remove(object.getId());
    }

    @Override
    public T getById(Long id) {
        return storage.get(id);
    }

    @Override
    public Collection<T> getAll() {
        return storage.values();
    }
}
