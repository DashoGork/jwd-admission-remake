package com.jwd_admission.byokrut.connection;

import com.jwd_admission.byokrut.util.DbConnectionProperties;
import com.jwd_admission.byokrut.util.PropertyReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool  {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenAwayConnections;

    private final int DEFAULT_POOL_SIZE = Integer.valueOf
            (PropertyReaderUtil.INSTANSE.getPropertyByName(DbConnectionProperties.defaultPoolSize.name()));
    private final String URL = PropertyReaderUtil.INSTANSE.getPropertyByName(DbConnectionProperties.connectionUrl.name());
    private final String LOGIN = PropertyReaderUtil.INSTANSE.getPropertyByName(DbConnectionProperties.login.name());
    private final String PASSWORD = PropertyReaderUtil.INSTANSE.getPropertyByName(DbConnectionProperties.password.name());


    {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException throwables) {
            LogManager.getLogger().error(throwables);
        }

        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.add(new ProxyConnection(DriverManager.getConnection(URL,
                        LOGIN, PASSWORD)));
            } catch (SQLException throwables) {
                LogManager.getLogger().error(throwables);
            }
        }
    }


    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass().equals(ProxyConnection.class)) {
            givenAwayConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else try {
            throw new SQLException();
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().hardClose();
            } catch (SQLException throwables) {
                logger.error(throwables);
            } catch (InterruptedException e) {
                logger.error(e);
            }

        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException throwables) {
                logger.error(throwables);

            }
        });
    }



}
