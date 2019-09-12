package godev.budgetgo.models.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnectionFactory implements ConnectionFactory {
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            URL resourceUrl = MySqlConnectionFactory.class
                    .getClassLoader().getResource("db.local.properties");
            if (resourceUrl == null) {
                throw new FileNotFoundException("Can't find db.local.properties");
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
        }

        // TODO: db tables initialization
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
}
