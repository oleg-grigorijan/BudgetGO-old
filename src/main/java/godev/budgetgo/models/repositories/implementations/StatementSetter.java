package godev.budgetgo.models.repositories.implementations;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.BiConsumer;

interface StatementSetter extends BiConsumer<PreparedStatement, Integer> {
    static StatementSetter forLong(long val) {
        return (p, i) -> {
            try {
                p.setLong(i, val);
            } catch (SQLException e) {
                e.printStackTrace();
                // TODO: logging
                throw new RuntimeException(e);
            }
        };
    }

    static StatementSetter forString(String val) {
        return (p, i) -> {
            try {
                p.setString(i, val);
            } catch (SQLException e) {
                e.printStackTrace();
                // TODO: logging
                throw new RuntimeException(e);
            }
        };
    }

    static StatementSetter forDate(Date val) {
        return (p, i) -> {
            try {
                p.setDate(i, val);
            } catch (SQLException e) {
                e.printStackTrace();
                // TODO: logging
                throw new RuntimeException(e);
            }
        };
    }
}