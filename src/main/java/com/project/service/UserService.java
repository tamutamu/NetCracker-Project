package com.project.service;

import com.project.database.dao.SubscriptionDAO;
import com.project.database.dao.UserDAO;
import com.project.model.Subscription;
import com.project.model.Token;
import com.project.model.User;
import com.project.communication.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jedaka on 10.11.2015.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SubscriptionDAO subscriptionDAO;

    public int save(User user){
        return userDAO.create(user);
    }

    @Transactional(readOnly = true)
    public User read(int id){
        return userDAO.read(id);
    }

    public void update(User user){
        userDAO.update(user);
    }

    public void delete(User user){
        userDAO.delete(user);
    }

    public User findByEmail(String email){
        return userDAO.findByEmail(email);
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addSubscritionForUser(User user, Token token) {
        subscriptionDAO.create(new Subscription(user, token));
    }

    public void removeSubscriptionFromUser(User user, Token token) {
        subscriptionDAO.delete(subscriptionDAO.findByUserAndToken(user, token));
    }

    public void addSubscritionForCurrentUser(Token token) {
        User user = this.getCurrentUser();
        subscriptionDAO.create(new Subscription(user, token));
    }

    public void removeSubscriptionFromCurrentUser(Token token) {
        User user = this.getCurrentUser();
        subscriptionDAO.delete(subscriptionDAO.findByUserAndToken(user, token));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return findByEmail(email);
    }

    public Boolean changePassword(ChangePasswordRequest passwordForm) {
        User user = getCurrentUser();
        if (user.getPassword().equals(passwordForm.getOldPassword())) {
            user.setPassword(passwordForm.getNewPassword());
            save(user);
            return true;
        }
        return false;
    }
}
