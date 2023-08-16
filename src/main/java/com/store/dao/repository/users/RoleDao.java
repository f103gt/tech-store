package com.store.dao.repository.users;

import com.store.dao.model.users.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoleDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public RoleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Role findByRole(String role){
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Role where role = :role";
        Query<Role> query = session.createQuery(hql, Role.class);
        query.setParameter("role", role);
        return query.uniqueResult();
    }
}
