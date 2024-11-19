package ru.vsu.cs.baklanova.database_interaction.postgre_db;

import ru.vsu.cs.baklanova.database_interaction.IDataBaseConnector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnector implements IDataBaseConnector {

    private String url;
    private String user;
    private String password;

    public DataBaseConnector() {
        loadConfig();
    }

    private void loadConfig() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnect() {
        return createConnection();
    }
}

