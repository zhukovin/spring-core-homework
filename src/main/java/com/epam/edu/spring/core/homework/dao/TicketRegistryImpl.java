package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.Ticket;
import com.epam.edu.spring.core.homework.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Service
public class TicketRegistryImpl extends GenericDomainEntityRegistry<Ticket> implements TicketRegistry {

    private static final String TABLE_COLUMNS = "userId INTEGER NOT NULL, eventId INTEGER, eventTime TIMESTAMP, seat INTEGER";

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
        return getById(ticket.getId()).map(existingTicket -> update(existingTicket, ticket)).orElseGet(() -> insertNew(ticket));
    }

    private Ticket insertNew(Ticket ticket) {
        GeneratedId generatedId = new GeneratedId();

        db.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO " + tableName() + " (userId, eventId, eventTime, seat) VALUES (?, ?, ?, ?)",
                            RETURN_GENERATED_KEYS);
                    ps.setLong(1, ticket.getUser().getId());
                    ps.setLong(2, ticket.getEvent().getId());
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
        db.update("UPDATE " + tableName() + " SET userId=?, eventId=?, eventTime=?, seat=? WHERE id=?",
                ticket.getUser().getId(),
                ticket.getEvent().getId(),
                Timestamp.valueOf(ticket.getEventTime()),
                ticket.getSeat(),
                existingTicket.getId());
        return ticket;
    }

    @Override
    Ticket newEntity(ResultSet rs, int rowNum) throws SQLException {
        Ticket ticket = new Ticket(
                rs.getTimestamp("eventTime").toLocalDateTime(),
                rs.getLong("seat")
        );
        ticket.setId(rs.getLong("id"));
        return ticket;
    }

    @Override
    public List<Ticket> getByUser(User user) {
        return db.query("SELECT * FROM " + tableName() + " WHERE userId = ?", new Object[]{user.getId()}, this::newEntity);
    }
}
