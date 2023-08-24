package com.store.repository.users;

import com.store.config.security.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@ComponentScan(basePackages = {"com.store.model", "com.store.repository"})
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*@Transactional
    @Override
    public User findEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User where email = :email";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }*/

    @Transactional
    @Override
    public User getUserByParameter(String parameterName, String parameter) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User where " + parameterName + " = :" + parameterName;
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter(parameterName, parameter);
        return query.uniqueResult();
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "insert into client(first_name,last_name,email,password) " +
                "values(:firstName,:lastName, :email, :password)";
        Query<User> query = session.createNativeQuery(sql, User.class);
        query.setParameter("firstName", user.getFirstName());
        query.setParameter("lastName", user.getLastName());
        query.setParameter("email", user.getEmail());
        query.setParameter("password", user.getPassword());
        query.executeUpdate();
    }

    //Optional<User> user = Optional.ofNullable(userDao.findEmail(userLogin.getEmail()));
   /* public Optional<User> findUserByPassword(String password){
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User where password = :password";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("password", password);
        return Optional.ofNullable(query.uniqueResult());
    }*/

    @Transactional
    @Override
    public void updateUserInfo(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }
}
