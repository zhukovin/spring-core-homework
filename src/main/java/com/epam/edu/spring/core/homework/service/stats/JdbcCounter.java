package com.epam.edu.spring.core.homework.service.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class JdbcCounter {

    private final JdbcTemplate db;
    private final String tableName;
    private final String keyName;
    private final String counterName;

    void increment(String key) {
        set(key, value(key) + 1);
    }

    long value(String key) {
        try {
            return ofNullable(db.queryForObject("SELECT " + counterName + " FROM " + tableName +
                    " WHERE " + keyName + " = ?", new Object[]{key}, Long.class)).orElse(0L);
        } catch (IncorrectResultSizeDataAccessException e) {
            db.update("INSERT INTO " + tableName + " (" + keyName + ") VALUES (?)", key);
            return 0L;
        }
    }

    private void set(String key, long counterValue) {
        assertSingleRowUpdated(
                db.update("UPDATE " + tableName + " SET " + counterName + " = ? WHERE " + keyName + " = ?",
                        counterValue, key));
    }

    private void assertSingleRowUpdated(int numberOfUpdatedRows) {
        if (numberOfUpdatedRows != 1)
            throw new InternalError("Counter update must affect one row exactly");
    }

    public Map<String, Long> getAll() {
        HashMap<String, Long> map = new HashMap<>();
        db.query("SELECT " + keyName + ", " + counterName + " FROM " + tableName, rs -> {
            map.put(rs.getString(keyName), rs.getLong(counterName));
        });
        return map;
    }
}
