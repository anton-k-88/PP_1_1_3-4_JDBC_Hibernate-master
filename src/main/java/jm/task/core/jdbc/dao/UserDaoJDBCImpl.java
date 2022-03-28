package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    private UserDaoJDBCImpl() {
        try {
            connection = Util.getMySQLConnection();
        } catch (SQLException | ClassNotFoundException | InvocationTargetException
                | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static UserDaoJDBCImpl makeUserDaoJDBCImpl() {
        return new UserDaoJDBCImpl();
    }

    public void createUsersTable() {
        boolean result;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (ID BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME varchar(20), LASTNAME varchar(20), AGE tinyint);");

        } catch (SQLException e) {
            System.out.println("Operation failed\n" + e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            System.out.println("Operation failed\n" + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String sql = "INSERT users (NAME, LASTNAME, AGE) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println(String.format("User с именем – %s %s добавлен в базу данных", name, lastName));
        } catch (SQLException e) {
            System.out.println("Operation failed\n" + e);
        }
    }

    public void removeUserById(long id) {
        try {
            String sql = "DELETE FROM users WHERE ID=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Operation failed\n" + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new LinkedList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("name"), resultSet.getString("lastname"), (byte) resultSet.getInt("age")));
            }
        } catch (SQLException e) {
            System.out.println("Operation failed\n" + e);
        }
        return userList;
    }


    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users;");
        } catch (SQLException e) {
            System.out.println("Operation failed\n" + e);
        }
    }
}
