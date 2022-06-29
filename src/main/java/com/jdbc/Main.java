package com.jdbc;

import com.jdbc.service.UserService;
import com.jdbc.service.UserServiceImpl;
import com.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Конченый","Аутист",(byte)16);
        userService.saveUser("Отбитый","Даун",(byte)30);
        userService.saveUser("Старый","Мудень",(byte)60);
        userService.saveUser("Мертвый","Пенсер",(byte)70);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.close();
    }
}
