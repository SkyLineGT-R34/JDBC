package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService users = new UserServiceImpl();
        users.createUsersTable();
        users.saveUser("Конченый","Аутист",(byte)16);
        users.saveUser("Отбитый","Даун",(byte)30);
        users.saveUser("Старый","Мудень",(byte)60);
        users.saveUser("Мертвый","Пенсер",(byte)70);
        users.getAllUsers().forEach(System.out::println);
        users.cleanUsersTable();
        users.dropUsersTable();
        Util.close();
    }
}
