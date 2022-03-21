package jm.task.core.jdbc.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String hostName = "localhost";
    private static final String dbName = "exercisebase";
    private static final String userName = "Username";
    private static final String password = "Supermicro88";
    private static final String url = "jdbc:mysql://" + hostName + "/" + dbName;

    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        Connection connection = DriverManager.getConnection(url, userName, password);
        System.out.println("Connection to DB established");
        return connection;
    }
}
