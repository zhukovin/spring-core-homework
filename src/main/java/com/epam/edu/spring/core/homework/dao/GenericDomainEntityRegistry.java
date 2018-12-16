package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.DomainObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Service
abstract class GenericDomainEntityRegistry<T extends DomainObject> implements DomainEntityRegistry<T> {

    final JdbcTemplate db;

    protected GenericDomainEntityRegistry(JdbcTemplate db) {
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
        return db.query("SELECT * FROM " + tableName() + " WHERE id = ?", new Object[]{id}, this::newEntity).stream().findFirst();
    }

    @Override
    public Collection<T> getAll() {
        return db.query("SELECT * FROM " + tableName(), this::newEntity);
    }

    abstract T newEntity(ResultSet rs, int rowNum) throws SQLException;

    List<T> getAllByStringColumn(String name, String value) {
        return db.query("SELECT * FROM " + tableName() + " WHERE " + name + " = ?", new Object[]{value}, this::newEntity);
    }
}
