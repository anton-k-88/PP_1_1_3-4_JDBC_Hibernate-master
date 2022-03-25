package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String hostName = "localhost";
    private static final String dbName = "exercisebase";
    private static final String dbNameHibernate = "hibernatebase";
    private static final String userName = "Username";
    private static final String password = "Supermicro88";
    private static final String url = "jdbc:mysql://" + hostName + "/" + dbName;
    private static final String urlHibernate = "jdbc:mysql://" + hostName + "/" + dbNameHibernate;



    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        Connection connection = DriverManager.getConnection(url, userName, password);
        System.out.println("Connection to DB established");
        return connection;
    }

    public static SessionFactory getHibernateSessionFactory() {

        Configuration configuration = new Configuration();

        configuration.setProperty(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
        configuration.setProperty(AvailableSettings.URL, urlHibernate);
        configuration.setProperty(AvailableSettings.USER, userName);
        configuration.setProperty(AvailableSettings.PASS, password);
        configuration.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        configuration.setProperty(AvailableSettings.SHOW_SQL, "true");
        configuration.setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        configuration.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
        configuration.addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}
