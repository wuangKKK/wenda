package com.wyl.wenda.controller;

import com.wyl.wenda.model.HostHolder;
import com.wyl.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    HostHolder hostHolder;
    @RequestMapping("/index")
    public String login(Map<String,Object> map){
        if(hostHolder.getUser()!=null){
            User user=hostHolder.getUser();
            map.put("user",user);
        }
        return "index";
    }
}
