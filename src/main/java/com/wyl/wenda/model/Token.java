package com.wyl.wenda.model;

import java.util.Date;

/*
* 存入cookie中的用户登录凭证
* */
public class Token {
    private int id;/*凭证ID*/
    private int user_id;//关联的用户ID
    private String token;//凭证串
    private Date update;//生成的时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }
}
