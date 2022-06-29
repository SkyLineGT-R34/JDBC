package com.jdbc.dao;

import com.jdbc.model.User;
import com.jdbc.util.Util;
import jakarta.persistence.EntityManager;
import org.hibernate.*;

import java.util.*;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    public void createUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            String sql = "create table user(" +
                    "id       bigint      not null AUTO_INCREMENT primary key,\n" +
                    "name     varchar(45) not null,\n" +
                    "lastName varchar(45) null,\n" +
                    "age      tinyint     not null,\n" +
                    ");";
            session.createNativeQuery(sql, User.class);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createNativeQuery("DROP TABLE USER", User.class);
            tr.commit();
            Util.getSessionFactory().close();
        } catch (Exception e) {
            //if (tr != null) tr.rollback();
            System.out.println(e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.remove(user);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            System.out.println(e.getMessage());
        }

    }

    public List<User> getAllUsers() {
        Transaction tr = null;
        List<User> res = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            res = session.createQuery("from User",User.class).list();
            tr.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return res;
        }
        return res;
    }

    public void cleanUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            getAllUsers().forEach((u) -> removeUserById(u.getId()));
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            System.out.println(e.getMessage());
        }
    }
}
