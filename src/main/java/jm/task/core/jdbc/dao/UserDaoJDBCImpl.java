package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.ROOT;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection conn;
    static {
        conn = Util.util();
    }

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement();) {
            statement.executeUpdate("create table users\n" +
                "(\n" +
                "    id       bigint      not null AUTO_INCREMENT\n" +
                "        primary key,\n" +
                "    name     varchar(45) not null,\n" +
                "    lastName varchar(45) null,\n" +
                "    age      tinyint     not null,\n" +
                "    constraint id_UNIQUE\n" +
                "        unique (id)\n" +
                ");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            ResultSet res = statement.executeQuery("SELECT Count(*) FROM information_schema.tables WHERE table_name = 'users' LIMIT 1");
            res.next();
            if (2 == res.getInt(1)) {
                statement.executeUpdate("drop table `users`;");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement =
                     conn.prepareStatement("INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println(String.format(ROOT,"User с именем – %s добавлен в базу данных",name));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = conn.createStatement()) {
            ResultSet res = statement.executeQuery("SELECT Count(*) FROM information_schema.tables WHERE table_name = 'users' LIMIT 1");
            res.next();
            if (2 == res.getInt(1)) {
                PreparedStatement preparedStatement =
                        conn.prepareStatement("DELETE FROM users WHERE id=?");
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            String SQL = "SELECT * FROM users";
            ResultSet res = statement.executeQuery(SQL);
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("lastName"));
                user.setAge(res.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            ResultSet res = statement.executeQuery("SELECT Count(*) FROM information_schema.tables WHERE table_name = 'users' LIMIT 1");
            res.next();
            if (2 == res.getInt(1)) {
                String SQL = "TRUNCATE TABLE users;";
                statement.executeUpdate(SQL);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
