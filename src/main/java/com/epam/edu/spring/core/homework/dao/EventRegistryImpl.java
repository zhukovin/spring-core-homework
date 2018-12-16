package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.EventRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Service
public class EventRegistryImpl extends GenericDomainEntityRegistry<Event> implements EventRegistry {

    private static final String TABLE_COLUMNS = "name VARCHAR(255), airDates VARCHAR(255), basePrice DOUBLE, rating VARCHAR(15)";

    private final TicketRegistry ticketRegistry;

    @Autowired
    public EventRegistryImpl(JdbcTemplate db, TicketRegistry ticketRegistry) {
        super(db);
        this.ticketRegistry = ticketRegistry;
    }

    @Override
    String columnsSqlDefinition() {
        return TABLE_COLUMNS;
    }

    @Override
    public Optional<Event> getByName(String name) {
        if (name == null)
            return Optional.empty();

        return db.query("SELECT * FROM " + tableName() + " WHERE name = ?", new Object[]{name}, this::newEntity)
            .stream().findFirst();
    }

    @Override
    public Event save(Event event) {
        getById(event.getId()).map(existingEvent -> update(existingEvent, event)).orElseGet(() -> insertNew(event))
        ;//        .getTickets().forEach(ticketRegistry::save);
        return event;
    }

    private Event update(Event existingEvent, Event event) {
        db.update("UPDATE " + tableName() + " SET name=?, airDates=?, basePrice=?, rating=? WHERE id=?",
                event.getName(), event.getAirDates().toString(), event.getBasePrice(), event.getRating().name(),
                existingEvent.getId());
        return event;
    }

    private Event insertNew(Event event) {
        GeneratedId generatedId = new GeneratedId();

        db.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO " + tableName() + " (name, airDates, basePrice, rating) VALUES (?, ?, ?, ?)",
                            RETURN_GENERATED_KEYS);
                    ps.setString(1, event.getName());
                    ps.setString(2, event.getAirDates().toString());
                    ps.setDouble(3, event.getBasePrice());
                    ps.setString(4, event.getRating().name());
                    return ps;
                },
                generatedId
        );

        event.setId(generatedId.get());
        return event;
    }

    @Override
    Event newEntity(ResultSet rs, int rowNum) throws SQLException {
        Event event = new Event();
        event.setId(rs.getLong("id"));
        event.setName(rs.getString("name"));
        event.setAirDates(listOfDates(rs.getString("airDates")));
        event.setBasePrice(rs.getDouble("basePrice"));
        event.setRating(EventRating.valueOf(rs.getString("rating")));
        ticketRegistry.getByEvent(event).forEach(event::addTicket);
        return event;
    }

    private List<LocalDateTime> listOfDates(String ignored) {
        // FIXME: implement proper parsing (or store air dates in a separate linked table
        return Collections.singletonList(LocalDateTime.now());
    }

    @Override
    Class<Event> entityType() {
        return Event.class;
    }
}
