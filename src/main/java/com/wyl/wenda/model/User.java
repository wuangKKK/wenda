package com.wyl.wenda.model;

import java.util.Date;
/*
* 用户类
* */
public class User {
    private int id;//用户ID
    private String username;//用户账号
    private String password;//用户密码
    private String salt;//给密码的杂质，保证安全性
    private String nickname;//昵称
    private String token;//用户的登录凭证，要存进cookie中

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
