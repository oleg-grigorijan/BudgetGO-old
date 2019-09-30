package godev.budgetgo.models.dbfactory;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection();
}
