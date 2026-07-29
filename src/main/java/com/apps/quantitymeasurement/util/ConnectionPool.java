package com.apps.quantitymeasurement.util;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {

    private static final HikariDataSource dataSource;

    static {

        try {

            Class.forName(
                    ApplicationConfig.getDatabaseDriver()
            );

            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(
                    ApplicationConfig.getDatabaseUrl()
            );

            config.setUsername(
                    ApplicationConfig.getDatabaseUsername()
            );

            config.setPassword(
                    ApplicationConfig.getDatabasePassword()
            );

            config.setMaximumPoolSize(
                    ApplicationConfig.getPoolSize()
            );

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to initialize connection pool",
                    e
            );
        }
    }

    private ConnectionPool() {
    }

    public static Connection getConnection()
            throws SQLException {

        return dataSource.getConnection();
    }

    public static void closePool() {

        if (dataSource != null) {

            dataSource.close();
        }
    }

    public static String getPoolStatistics() {

        return "Active Connections : "
                + dataSource.getHikariPoolMXBean().getActiveConnections()
                + ", Idle Connections : "
                + dataSource.getHikariPoolMXBean().getIdleConnections()
                + ", Total Connections : "
                + dataSource.getHikariPoolMXBean().getTotalConnections();
    }
}