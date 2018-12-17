package com.epam.edu.spring.core.homework.dao;

import com.epam.edu.spring.core.homework.domain.Event;
import com.epam.edu.spring.core.homework.domain.EventRating;
import org.springframework.beans.factory.BeanFactory;
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

    private static final String NAME = "name";
    private static final String AIR_DATES = "airDates";
    private static final String BASE_PRICE = "basePrice";
    private static final String RATING = "rating";

    private static final String TABLE_COLUMNS = "" +
        NAME + " VARCHAR(255), " +
        AIR_DATES + " VARCHAR(255), " +
        BASE_PRICE + " DOUBLE, " +
        RATING + " VARCHAR(15)";

    private final TicketRegistry ticketRegistry;
    private final BeanFactory beanFactory;

    @Autowired
    public EventRegistryImpl(JdbcTemplate db, TicketRegistry ticketRegistry, BeanFactory beanFactory) {
        super(db);
        this.ticketRegistry = ticketRegistry;
        this.beanFactory = beanFactory;
    }

    @Override
    String columnsSqlDefinition() {
        return TABLE_COLUMNS;
    }

    @Override
    public Optional<Event> getByName(String name) {
        return getAllByStringColumn(NAME, name).stream().findFirst();
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
        Event event = beanFactory.getBean(Event.class);
        event.setId(rs.getLong(ID));
        event.setName(rs.getString(NAME));
        event.setAirDates(listOfDates(rs.getString(AIR_DATES)));
        event.setBasePrice(rs.getDouble(BASE_PRICE));
        event.setRating(EventRating.valueOf(rs.getString(RATING)));
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
