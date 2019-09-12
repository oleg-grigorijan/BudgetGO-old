package godev.budgetgo.models;

import godev.budgetgo.models.connection.ConnectionFactory;
import godev.budgetgo.models.connection.MySqlConnectionFactory;

public class Config {
    private static ConnectionFactory connectionFactory = new MySqlConnectionFactory();

    private Config() {
    }

    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
