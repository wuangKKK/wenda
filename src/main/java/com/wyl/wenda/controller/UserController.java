package com.wyl.wenda.controller;

import com.wyl.wenda.model.User;
import com.wyl.wenda.service.UserServcie;
import com.wyl.wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserServcie userServcie;
    /*注册操作*/
    @RequestMapping("/register")
    public String login(String username, String password, String nickname, Map<String,Object> map, HttpServletResponse response){
        User user=new User();
        user.setUsername(username);
        User exit=userServcie.selectByUsername(user);
        if(exit!=null){
            map.put("msg","用户名已存在");
            return "login";
        }
        String salt=UUID.randomUUID().toString().substring(0,6);//给密码加点杂质，保证安全性
        password= WendaUtil.MD5(password+salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setToken(UUID.randomUUID().toString().replaceAll("-",""));
        Cookie cookie=new Cookie("token",user.getToken());
        response.addCookie(cookie);
        userServcie.addUser(user);
        map.put("user",user);
        return "index";
    }
    /*
    * 登录操作
    * */
    @RequestMapping("/login")
    public String login(String username,String password,Map<String,Object> map,HttpServletResponse response){
        User user=new User();
        user.setUsername(username);
        user=userServcie.selectByUsername(user);
        if(user==null){
            map.put("msg","用户名或密码错误");
            return "login";
        }
        String salt=user.getSalt();
        password=WendaUtil.MD5(password+salt);
        user.setPassword(password);
        user=userServcie.selectByUsernameAndPassword(user);
        if(user==null){
            map.put("msg","用户名或密码错误");
            return "login";
        }
        Cookie cookie=new Cookie("token",user.getToken());
        response.addCookie(cookie);
        map.put("user",user);
        return "index";
    }
}
