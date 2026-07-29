package com.apps.quantitymeasurement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static final Properties properties = new Properties();

    static {

        try (InputStream input =
                     ApplicationConfig.class.getClassLoader()
                             .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties not found.");
            }

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load application.properties",
                    e
            );
        }
    }

    private ApplicationConfig() {
    }

    public static String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDatabasePassword() {
        return properties.getProperty("db.password");
    }

    public static String getDatabaseDriver() {
        return properties.getProperty("db.driver");
    }

    public static int getPoolSize() {
        return Integer.parseInt(
                properties.getProperty("db.pool.size")
        );
    }
}