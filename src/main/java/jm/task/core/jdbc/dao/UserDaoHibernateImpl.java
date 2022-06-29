package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.*;
import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.util.*;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sFactory;

    static {
        sFactory = getSessionFactory();
    }

    public UserDaoHibernateImpl() {
    }

    public void createUsersTable() {
        try (Session session = sFactory.openSession()) {
            Object result = session.createNativeQuery("create table user\n" +
                    "(\n" +
                    "    id       bigint      not null AUTO_INCREMENT primary key,\n" +
                    "    name     varchar(45) not null,\n" +
                    "    lastName varchar(45) null,\n" +
                    "    age      tinyint     not null,\n" +
                    "    constraint id_UNIQUE unique (id)\n" +
                    ");").getSingleResultOrNull();
            System.out.println("ХЗ что "+result);
            result = session.createNativeQuery("select version()").getSingleResultOrNull();
            System.out.println("Создана база "+result);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        Transaction tr = null;
        try (Session session = sFactory.openSession()) {
            tr = session.beginTransaction();
            Query query = session.createNativeQuery("DROP TABLE IF EXISTS user", User.class);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            System.out.println(e.getMessage());
        }

    }


    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        try (Session session = sFactory.openSession()) {
            tr = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.merge(user);
            tr.commit();
        } catch (Exception e) {
            //if (tr != null) tr.rollback();
            System.out.println(e.getMessage());
        }
    }


    public void removeUserById(long id) {

    }


    public List<User> getAllUsers() {
        List<User> res;
        try (Session session = sFactory.openSession()) {

            res = session.createNativeQuery("SELECT * FROM user;", User.class).stream().toList();
        } catch (HibernateException e) {
            //if (tr != null) tr.rollback();
            System.out.println(e.getMessage());
            return null;
        }
        return res;
    }


    public void cleanUsersTable() {

    }
}
