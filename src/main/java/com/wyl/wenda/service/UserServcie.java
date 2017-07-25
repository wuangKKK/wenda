package com.wyl.wenda.service;

import com.wyl.wenda.dao.TokenDao;
import com.wyl.wenda.dao.UserDao;
import com.wyl.wenda.model.Token;
import com.wyl.wenda.model.User;
import com.wyl.wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServcie {
    @Autowired
    UserDao userDao;
    @Autowired
    TokenDao tokenDao;
    public Map<String,Object> addUser(User user){
        Map<String,Object> map=new HashMap<>();
        userDao.addUser(user);
        user=selectByUsername(user.getUsername());
        String token=addLoginTicket(user.getId());
        map.put("token",token);
        map.put("user",user);
        return map;
    }

    public User selectByUsername(String username){

        return userDao.selectByUsername(username);
    }
    public Map<String,Object> selectByUsernameAndPassword(String username, String password){
        Map<String,Object> map=new HashMap<>();
        User user=userDao.selectByUsername(username);
        if(user==null){
            map.put("msg","用户不存在");
        }
        String salt=user.getSalt();
        password= WendaUtil.MD5(password+salt);
        if(user.getPassword()!=password){
            map.put("msg","密码错误");
        }
        String token=addLoginTicket(user.getId());
        map.put("token",token);
        map.put("user",user);
        return map;
    }
    /*增加登录令牌*/
    private String addLoginTicket(int userId) {
        Token token = new Token();
        token.setUserId(userId);
        token.setStatus(0);
        token.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        tokenDao.addToken(token);
        return token.getToken();
    }
    /*用户登出*/
    public void logout(String token){
        tokenDao.updateStatus(1,token);
    }


}
