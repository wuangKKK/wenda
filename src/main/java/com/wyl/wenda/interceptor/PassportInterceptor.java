package com.wyl.wenda.interceptor;

import com.wyl.wenda.dao.TokenDao;
import com.wyl.wenda.dao.UserDao;
import com.wyl.wenda.model.HostHolder;
import com.wyl.wenda.model.Token;
import com.wyl.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    private UserDao userDAO;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null) {
            Token token1=tokenDao.selectByToken(token);
            if(token1==null||token1.getStatus()!=0){
                return true;
            }
            int userId=token1.getUserId();
            User user=userDAO.selectUserById(userId);
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
