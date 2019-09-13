package godev.budgetgo.models.connection;

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

public class MySqlConnectionFactory implements ConnectionFactory {
    private String url;
    private String user;
    private String password;

    public MySqlConnectionFactory(String propertiesFileName) {
        setDbProperties(propertiesFileName);
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

    public void setDbProperties(String fileName) {
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

        initTables();
    }

    private void initTables() {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("tables.sql");

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
