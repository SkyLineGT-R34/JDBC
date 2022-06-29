package com.jdbc.service;

import com.jdbc.dao.UserDao;
import com.jdbc.dao.UserDaoHibernateImpl;
import com.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserDao dao = new UserDaoHibernateImpl();
    public void createUsersTable() {
        dao.createUsersTable();
    }

    public void dropUsersTable() {
        dao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        dao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public void cleanUsersTable() {
        dao.cleanUsersTable();
    }
}
