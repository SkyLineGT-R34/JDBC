package com.jdbc.service;

import com.jdbc.dao.UserDao;
import com.jdbc.model.User;

import java.util.List;

public interface UserService extends UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
