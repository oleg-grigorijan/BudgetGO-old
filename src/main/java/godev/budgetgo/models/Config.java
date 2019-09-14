package godev.budgetgo.models;

import godev.budgetgo.models.connection.ConnectionFactory;
import godev.budgetgo.models.connection.MySqlConnectionFactory;
import godev.budgetgo.models.repositories.OperationsRepository;
import godev.budgetgo.models.repositories.StoragesRepository;
import godev.budgetgo.models.repositories.UsersRepository;
import godev.budgetgo.models.repositories.implementations.MySqlOperationsRepository;
import godev.budgetgo.models.repositories.implementations.MySqlStoragesRepository;
import godev.budgetgo.models.repositories.implementations.MySqlUsersRepository;

public class Config {
    private static final String DB_PROPERTIES_PATH = "db.local.properties";
    private static final String DB_TEST_PROPERTIES_PATH = "dbtest.local.properties";
    private static boolean isTestMode = false;

    private static final MySqlConnectionFactory connectionFactory =
            new MySqlConnectionFactory(DB_PROPERTIES_PATH);
    private static final UsersRepository usersRepository = new MySqlUsersRepository();
    private static final StoragesRepository storagesRepository = new MySqlStoragesRepository();
    private static final OperationsRepository operationsRepository = new MySqlOperationsRepository();

    private Config() {
    }

    public static void runTestMode() {
        if (!isTestMode) {
            connectionFactory.setDbProperties(DB_TEST_PROPERTIES_PATH);
            isTestMode = true;
        }
    }

    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public static UsersRepository getUsersRepository() {
        return usersRepository;
    }

    public static StoragesRepository getStoragesRepository() {
        return storagesRepository;
    }

    public static OperationsRepository getOperationsRepository() {
        return operationsRepository;
    }
}
