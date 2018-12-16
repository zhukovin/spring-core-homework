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

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String BIRTHDAY = "birthday";

    private static final String TABLE_COLUMNS = "" +
        FIRST_NAME + " VARCHAR(255), " +
        LAST_NAME + " VARCHAR(255), " +
        EMAIL + " VARCHAR(255) UNIQUE, " +
        BIRTHDAY + " TIMESTAMP";

    private final TicketRegistry ticketRegistry;

    @Autowired
    public UserRegistryImpl(JdbcTemplate db, TicketRegistry ticketRegistry) {
        super(db);
        this.ticketRegistry = ticketRegistry;
    }

    @Override
    String columnsSqlDefinition() {
        return TABLE_COLUMNS;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        if (email == null)
            return Optional.empty();

        return db.query("SELECT * FROM " + tableName() + " WHERE email = ?", new Object[]{email}, this::newEntity)
            .stream().findFirst();
    }

    @Override
    public User save(User user) {
        return existing(user).map(existingUser -> update(existingUser, user)).orElseGet(() -> insertNew(user));
    }

    private Optional<User> existing(User user) {
        return ofNullable(getById(user.getId()).orElseGet(() -> getByEmail(user.getEmail()).orElse(null)));
    }

    private User update(User existingUser, User user) {
        db.update("UPDATE " + tableName() + " SET firstName=?, lastName=?, email=?, birthday=? WHERE id=?",
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            Timestamp.valueOf(user.getBirthday()),
            existingUser.getId());
        return user;
    }

    private User insertNew(User user) {
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
        return user;
    }

    @Override
    User newEntity(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(ID));
        user.setFirstName(rs.getString(FIRST_NAME));
        user.setLastName(rs.getString(LAST_NAME));
        user.setEmail(rs.getString(EMAIL));
        user.setBirthday(rs.getTimestamp(BIRTHDAY).toLocalDateTime());
        ticketRegistry.getByUser(user).forEach(user::addTicket);
        return user;
    }

    @Override
    Class<User> entityType() {
        return User.class;
    }
}
