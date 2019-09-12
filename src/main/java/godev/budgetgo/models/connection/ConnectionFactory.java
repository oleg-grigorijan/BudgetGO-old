package godev.budgetgo.models.connection;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection();
}
