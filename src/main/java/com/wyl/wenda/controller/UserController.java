package com.wyl.wenda.controller;

import com.wyl.wenda.model.HostHolder;
import com.wyl.wenda.model.User;
import com.wyl.wenda.service.UserServcie;
import com.wyl.wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserServcie userServcie;
    @Autowired
    HostHolder hostHolder;//存当前线程内的用户的类
    /*注册操作*/
    @RequestMapping("/register.action")
    public String register(String username, String password, String nickname, Map<String,Object> map, HttpServletResponse response){
        User user=new User();
        user.setUsername(username);
        User exit=userServcie.selectByUsername(username);
        if(exit!=null){
            map.put("msg","用户名已存在");
            return "login";
        }
        String salt=UUID.randomUUID().toString().substring(0,6);//给密码加点杂质，保证安全性
        password= WendaUtil.MD5(password+salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setNickname(nickname);
        map=userServcie.addUser(user);
        if (map.containsKey("token")) {
            Cookie cookie = new Cookie("token", map.get("token").toString());
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:index.html";
    }
    /*
    * 登录操作
    * */
    @RequestMapping("/login.action")
    public String login(String username,String password,Map<String,Object> map,HttpServletResponse response){
        /*先重cookie中取出用户，查看是否已经有用户登录。如果有则不用走登录流程，直接登录*/
        /*判断username是因为解决利用后退键退回登录界面再登录的时候，
        会直接取cookie中的登录令牌而不是进行当前用户的登录操作*/
        if(hostHolder.getUser()!=null&&hostHolder.getUser().getUsername()==username){
            return "redirect:index.html";
        }
        map=userServcie.selectByUsernameAndPassword(username,password);

        if (map.containsKey("token")) {
            Cookie cookie = new Cookie("token", map.get("token").toString());
            cookie.setPath("/");
            response.addCookie(cookie);
        }else{
            return "login";
        }
        return "redirect:index.html";
    }
    @RequestMapping("/logout.action")
    public String logout(@CookieValue("token") String token){
        userServcie.logout(token);
        return"redirect:index.html";
    }
}
