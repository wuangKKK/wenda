package com.wyl.wenda.service;

import com.wyl.wenda.dao.UserDao;
import com.wyl.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServcie {
    @Autowired
    UserDao userDao;
    public int addUser(User user){
        return userDao.addUser(user);
    }
    public User selectByUsername(User user){
        return userDao.selectByUsername(user);
    }
    public User selectByUsernameAndPassword(User user){

        return userDao.selectByUsernameAndPassword(user);

    }


}
