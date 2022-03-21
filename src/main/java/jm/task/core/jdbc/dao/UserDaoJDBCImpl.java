package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getMySQLConnection();
        } catch (SQLException | ClassNotFoundException | InvocationTargetException
                | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
        public void createUsersTable () {
            boolean result;
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (ID BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                        "NAME varchar(20), LASTNAME varchar(20), AGE tinyint);");

            } catch (SQLException e) {
                System.out.println("Operation failed\n" + e);
            }
        }

        public void dropUsersTable () {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS users;");
            } catch (SQLException e) {
                System.out.println("Operation failed\n" + e);
            }
        }

        public void saveUser(String name, String lastName,byte age){
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(String.format("INSERT users " +
                        "(NAME, LASTNAME, AGE) VALUES (\'%s\', \'%s\', %d);", name, lastName, age));
                System.out.println(String.format("User с именем – %s %s добавлен в базу данных", name, lastName));
            } catch (SQLException e) {
                System.out.println("Operation failed\n" + e);
            }
        }

        public void removeUserById ( long id){
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(String.format("DELETE FROM users WHERE ID=%d;", id));
            } catch (SQLException e) {
                System.out.println("Operation failed\n" + e);
            }
        }

        public List<User> getAllUsers () {
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


        public void cleanUsersTable () {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM users;");
            } catch (SQLException e) {
                System.out.println("Operation failed\n" + e);
            }
        }
    }
