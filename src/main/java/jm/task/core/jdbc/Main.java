package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {


    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Вася", "Пупкин", (byte) 21);
        userService.saveUser("Иван", "Петров", (byte) 22);
        userService.saveUser("Петр", "иванов", (byte) 23);
        userService.saveUser("Федор", "Кикнадзе", (byte) 24);
        userService.removeUserById(1L);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
