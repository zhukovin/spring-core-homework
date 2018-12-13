package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Optional.ofNullable;

@Service
public class UserRegistryImpl extends GenericDomainEntityRegistry<User> implements UserRegistry {

    private final TicketRegistry ticketRegistry;

    @Autowired
    public UserRegistryImpl(JdbcTemplate db, TicketRegistry ticketRegistry) {
        super(db);
        this.ticketRegistry = ticketRegistry;
    }

    @Override
    String columnsSqlDefinition() {
        return "firstName VARCHAR(255), lastName VARCHAR(255), email VARCHAR(255) UNIQUE, birthday TIMESTAMP";
    }

    @Override
    public Optional<User> getByEmail(String email) {
        if (email == null)
            return Optional.empty();

        return ofNullable(db.queryForObject("SELECT * FROM " + tableName() + " WHERE email = ?", new Object[]{email}, this::newEntity));
    }

    @Override
    public User save(User user) {
        Optional<User> persistedUser = getById(user.getId());
        if (persistedUser.isPresent()) {
            db.update("UPDATE " + tableName() + " SET firstName=?, lastName=?, email=?, birthday=? WHERE id=?",
                    user.getFirstName(), user.getLastName(), user.getEmail(), Timestamp.valueOf(user.getBirthday()),
                    persistedUser.get().getId());
        } else {
            GeneratedId generatedId = new GeneratedId();

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
                    generatedId
            );

            user.setId(generatedId.get());
        }

        user.getTickets().forEach(ticketRegistry::save);

        return user;
    }

    @Override
    User newEntity(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(rs.getTimestamp("birthday").toLocalDateTime());
        ticketRegistry.getByUser(user).forEach(user::addTicket);
        return user;
    }

    @Override
    Class<User> entityType() {
        return User.class;
    }
}
