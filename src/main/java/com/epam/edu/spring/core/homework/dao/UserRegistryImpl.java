package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class UserRegistryImpl extends AbstractDomainObjectRegistryImpl<User> implements UserRegistry {

    @Autowired
    public UserRegistryImpl(JdbcTemplate db) {
        super(db);
    }

    @Override
    String columnsSqlDefinition() {
        return "firstName VARCHAR(255), lastName VARCHAR(255), email VARCHAR(255), birthday TIMESTAMP";
    }

    @Override
    public Optional<User> getByEmail(String email) {
        if (email == null)
            return Optional.empty();

        return ofNullable(db.queryForObject("SELECT * FROM " + tableName() + " WHERE email = ?", new Object[]{email}, this::newUser));
    }

    @Override
    public User save(User desiredUser) {
        Optional<User> persistedUser = getById(desiredUser.getId());
        if (persistedUser.isPresent()) {
            db.update("UPDATE " + tableName() + " SET firstName=?, lastName=?, email=? WHERE id=?",
                    desiredUser.getFirstName(), desiredUser.getLastName(), desiredUser.getEmail(), persistedUser.get().getId());
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            db.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO " + tableName() + " (firstName, lastName, email, birthday) VALUES (?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, desiredUser.getFirstName());
                        ps.setString(2, desiredUser.getLastName());
                        ps.setString(3, desiredUser.getEmail());
                        ps.setTimestamp(4, Timestamp.valueOf(desiredUser.getBirthday()));
                        return ps;
                    },
                    keyHolder
            );

            Long id = ofNullable(keyHolder.getKey()).map(Number::longValue).orElse(-1L);
            desiredUser.setId(id);
        }
        return desiredUser;
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
