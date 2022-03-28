package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;


    private UserDaoHibernateImpl() {
        sessionFactory = Util.getHibernateSessionFactory();
    }

    public static UserDaoHibernateImpl makeUserDaoHibernateImpl() {
        return new UserDaoHibernateImpl();
    }

    public Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    @Override
    public void createUsersTable() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS user (ID BIGINT PRIMARY KEY AUTO_INCREMENT, "
                + "NAME varchar(20), LASTNAME varchar(20), AGE tinyint);";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS user;";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from User u where u.id = :id1";
        Query query = session.createQuery(hql).setParameter("id1", id);
        query.getResultList().forEach(session::delete);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from User u";
        Query query = session.createQuery(hql);
        List<User> list = query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "delete from User";
        session.createQuery(hql).executeUpdate();
        transaction.commit();
        session.close();
    }
}
