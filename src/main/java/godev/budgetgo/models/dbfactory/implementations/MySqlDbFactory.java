package godev.budgetgo.models.dbfactory.implementations;

import godev.budgetgo.models.Config;
import godev.budgetgo.models.dbfactory.DbFactory;
import godev.budgetgo.models.repositories.OperationsRepository;
import godev.budgetgo.models.repositories.StoragesRepository;
import godev.budgetgo.models.repositories.UsersRepository;
import godev.budgetgo.models.repositories.implementations.MySqlOperationsRepository;
import godev.budgetgo.models.repositories.implementations.MySqlStoragesRepository;
import godev.budgetgo.models.repositories.implementations.MySqlUsersRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MySqlDbFactory implements DbFactory {
    private String url;
    private String user;
    private String password;
    private final UsersRepository usersRepository;
    private final StoragesRepository storagesRepository;
    private final OperationsRepository operationsRepository;

    public MySqlDbFactory() {
        setDbProperties(Config.getDbPropertiesFileName());
        initTables(Config.getTablesInitFileName());
        usersRepository = new MySqlUsersRepository(this);
        storagesRepository = new MySqlStoragesRepository(this);
        operationsRepository = new MySqlOperationsRepository(this);
    }

    @Override
    public Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public UsersRepository getUsersRepository() {
        return usersRepository;
    }

    @Override
    public StoragesRepository getStoragesRepository() {
        return storagesRepository;
    }

    @Override
    public OperationsRepository getOperationsRepository() {
        return operationsRepository;
    }

    private void setDbProperties(String fileName) {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(fileName);

            if (resourceUrl == null) {
                throw new FileNotFoundException("Can't find db properties resource file");

            } else {
                String propertiesPath = resourceUrl.getPath();
                Properties properties = new Properties();
                properties.load(new FileInputStream(propertiesPath));

                url = properties.getProperty("ulr");
                user = properties.getProperty("user");
                password = properties.getProperty("password");
            }

        } catch (IOException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        }
    }

    private void initTables(String fileName) {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(fileName);

            if (resourceUrl == null) {
                throw new FileNotFoundException("Can't find tables.sql resource file");

            } else {
                String queries = new String(Files.readAllBytes(Paths.get(resourceUrl.toURI())));

                try (Connection connection = createConnection();
                     Statement statement = connection.createStatement()) {
                    for (String query : queries.split(";")) statement.execute(query);

                } catch (SQLException e) {
                    e.printStackTrace();
                    // TODO: logging
                    throw new RuntimeException(e);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // TODO: logging
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
