package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Optional.ofNullable;

@Service
public class UserRegistryImpl extends GenericDomainEntityRegistry<User> implements UserRegistry {

    @Autowired
    public UserRegistryImpl(JdbcTemplate db) {
        super(db);
    }

    @Override
    String columnsSqlDefinition() {
        return "firstName VARCHAR(255), lastName VARCHAR(255), email VARCHAR(255) UNIQUE, birthday TIMESTAMP";
    }

    @Override
    public Optional<User> getByEmail(String email) {
        if (email == null)
            return Optional.empty();

        return ofNullable(db.queryForObject("SELECT * FROM " + tableName() + " WHERE email = ?", new Object[]{email}, this::newUser));
    }

    @Override
    public User save(User user) {
        Optional<User> persistedUser = getById(user.getId());
        if (persistedUser.isPresent()) {
            db.update("UPDATE " + tableName() + " SET firstName=?, lastName=?, email=? WHERE id=?",
                    user.getFirstName(), user.getLastName(), user.getEmail(), persistedUser.get().getId());
        } else {
            IdHolder idHolder = new IdHolder();

            db.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO " + tableName() + " (firstName, lastName, email, birthday) VALUES (?, ?, ?, ?)",
                                RETURN_GENERATED_KEYS);
                        ps.setString(1, user.getFirstName());
                        ps.setString(2, user.getLastName());
                        ps.setString(3, user.getEmail());
                        ps.setTimestamp(4, Timestamp.valueOf(user.getBirthday()));
                        return ps;
                    },
                    idHolder
            );

            user.setId(idHolder.generatedId());
        }
        return user;
    }

    private class IdHolder extends GeneratedKeyHolder {
        @NotNull
        private Long generatedId() {
            return ofNullable(getKey()).map(Number::longValue).orElseThrow(InternalError::new);
        }
    }

    @Override
    public Collection<User> getAll() {
        return db.query("SELECT * FROM " + tableName(), this::newUser);
    }

    @NotNull
    private User newUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(rs.getTimestamp("birthday").toLocalDateTime());
        return user;
    }

    @Override
    Class<User> entityType() {
        return User.class;
    }
}
