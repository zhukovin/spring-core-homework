package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.DomainObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Service
abstract class AbstractDomainObjectRegistryImpl<T extends DomainObject> implements AbstractDomainObjectRegistry<T> {

    final JdbcTemplate db;

    protected AbstractDomainObjectRegistryImpl(JdbcTemplate db) {
        this.db = db;
        createTableIfNeeded();
    }

    abstract Class<T> entityType();

    abstract String columnsSqlDefinition();

    String tableName() {
        return entityType().getSimpleName();
    }

    private void createTableIfNeeded() {
        db.execute("create table if not exists " + tableName() +
                " (id INTEGER NOT NULL AUTO_INCREMENT, " +
                columnsSqlDefinition() +
                ", PRIMARY KEY (id))");
    }

    @Override
    public void remove(T entity) {
        db.update("DELETE FROM " + tableName() + " WHERE id = ?", entity.getId());
    }

    @Override
    public Optional<T> getById(Long id) {
        if (id == null)
            return Optional.empty();
        return Optional.ofNullable(db.queryForObject("SELECT * FROM " + tableName() + " WHERE id = ", new Object[]{id}, entityType()));
    }
}
