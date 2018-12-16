package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.Ticket;
import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Optional.ofNullable;

@Service
public class TicketRegistryImpl extends GenericDomainEntityRegistry<Ticket> implements TicketRegistry {

    private static final String USER_EMAIL = "userEmail";
    private static final String EVENT_NAME = "eventName";
    private static final String EVENT_TIME = "eventTime";
    private static final String SEAT = "seat";

    private static final String TABLE_COLUMNS = "" +
        USER_EMAIL + " VARCHAR(255), " +
        EVENT_NAME + " VARCHAR(255) NOT NULL, " +
        EVENT_TIME + " TIMESTAMP, " +
        SEAT + " INTEGER";


    @Lazy
    protected TicketRegistryImpl(JdbcTemplate db) {
        super(db);
    }

    @Override
    Class<Ticket> entityType() {
        return Ticket.class;
    }

    @Override
    String columnsSqlDefinition() {
        return TABLE_COLUMNS;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return existing(ticket)
            .map(existingTicket -> update(existingTicket, ticket))
            .orElseGet(() -> insertNew(ticket));
    }

    private Optional<Ticket> existing(Ticket ticket) {
        return ofNullable(getById(ticket.getId()).orElseGet(() -> getByUserAndEvent(ticket).orElse(null)));
    }

    private Optional<Ticket> getByUserAndEvent(Ticket ticket) {
        return db.query("SELECT * FROM " + tableName() + " WHERE userEmail = ? AND eventName = ? AND seat = ?",
            new Object[]{ticket.getUserEmail(), ticket.getEventName(), ticket.getSeat()}, this::newEntity).stream().findFirst();
    }

    private Ticket insertNew(Ticket ticket) {
        GeneratedId generatedId = new GeneratedId();

        db.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO " + tableName() + " (userEmail, eventName, eventTime, seat) VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS);
                ps.setString(1, ticket.getUserEmail());
                ps.setString(2, ticket.getEventName());
                ps.setTimestamp(3, Timestamp.valueOf(ticket.getEventTime()));
                ps.setLong(4, ticket.getSeat());
                return ps;
            },
            generatedId
        );

        ticket.setId(generatedId.get());

        return ticket;
    }

    private Ticket update(Ticket existingTicket, Ticket ticket) {
        db.update("UPDATE " + tableName() + " SET userEmail=?, eventName=?, eventTime=?, seat=? WHERE id=?",
            ticket.getUserEmail(),
            ticket.getEventName(),
            Timestamp.valueOf(ticket.getEventTime()),
            ticket.getSeat(),
            existingTicket.getId());
        return ticket;
    }

    @Override
    Ticket newEntity(ResultSet rs, int rowNum) throws SQLException {
        Ticket ticket = new Ticket(
            rs.getString(USER_EMAIL),
            rs.getString(EVENT_NAME),
            rs.getTimestamp(EVENT_TIME).toLocalDateTime(),
            rs.getLong(SEAT)
        );
        ticket.setId(rs.getLong(ID));
        return ticket;
    }

    @Override
    public List<Ticket> getByUser(User user) {
        return getAllByStringColumn(USER_EMAIL, user.getEmail());
    }

    @Override
    public List<Ticket> getByEvent(Event event) {
        return getAllByStringColumn(EVENT_NAME, event.getName());
    }
}
