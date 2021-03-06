package com.project.database.dao;

import com.project.model.Token;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by jedaka on 12.11.2015.
 *
 */
@Repository
public class TokenDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int create(Token object) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save(object);
    }

    public Set<Token> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Set<Token> resultSet = new TreeSet<Token>(session.createCriteria(Token.class).list());
        return resultSet;
    }

    public List<Token> getAllOUsersToken(){
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Token.class).list();
    }


    public Token read(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Token.class, id);
    }

    public Token findByToken(String token){
        Session session = sessionFactory.getCurrentSession();
        return (Token) session.createCriteria(Token.class).add(Restrictions.eq("token", token)).uniqueResult();
    }

    public void update(Token object) {
        Session session = sessionFactory.getCurrentSession();
        session.update(object);
    }

    public void delete(Token object) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
