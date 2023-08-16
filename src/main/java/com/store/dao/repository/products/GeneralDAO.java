package com.store.dao.repository.products;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class GeneralDAO {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GeneralDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Transactional
    public Object getObjectByParameter(String parameterName, String parameter,
                                       String className, Class<?> clazz) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from " + className + " where " + parameterName + " = :" + parameterName;
        Query<?> query = session.createQuery(hql, clazz);
        query.setParameter(parameterName, parameter);
        return query.uniqueResult();
    }

    @Transactional
    public List<?> getAllInstances(String className, Class<?> clazz) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from " + className;
        Query<?> query = session.createQuery(hql, clazz);
        return query.getResultList();
    }

    @Transactional
    public List<?> getAllInstancesWhere(String parameterName, String parameter
            , String className, Class<?> clazz) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from " + className + " where " + parameterName + " = :" + parameterName;
        Query<?> query = session.createQuery(hql, clazz);
        query.setParameter(parameterName, parameter);
        return query.getResultList();
    }

    /*public String getParameterByBaseParameter(String desiredParamName, String baseParamName,
                                              String baseParam, String className) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "select " + desiredParamName + " from " + className + " where " +
                baseParamName + " = :" + baseParam;
        Query<String> query = session.createQuery(hql, String.class);
        query.setParameter(baseParamName, baseParam);
        return query.uniqueResult();
    }*/


    @Transactional
    public String getParameterByBaseParameter(String parameterToSelect,
                                              String baseParameterName,
                                              String baseParameterValue,
                                              String entityName) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "select p." + parameterToSelect +
                " from " + entityName + " p " +
                " where p." + baseParameterName + " = :baseParameterValue";

        Query<Object> query = session.createQuery(hql, Object.class);
        query.setParameter("baseParameterValue", baseParameterValue);
        return query.uniqueResult().toString();
    }

    /*@Transactional
    public void saveInstance(Object instance,Class<Object> clazz) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "insert into SpecificProduct(sku,quantitiy,productId) " +
                "values(:sku,:quantity, :product_id)";
        Query<User> query = session.createNativeQuery(sql, User.class);
        query.setParameter("sku", specificProduct.getSku());
        query.setParameter("quantity", specificProduct.getQuantity());
        query.setParameter("product_id", specificProduct.getProduct().getId());
        query.executeUpdate();
    }*/
}
