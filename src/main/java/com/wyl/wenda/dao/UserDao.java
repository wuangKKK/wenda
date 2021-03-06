package com.wyl.wenda.dao;

import com.wyl.wenda.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component(value ="userMapper")/*该注解解决注入UserDao时报错的问题*/
public interface UserDao {
    String TABLE_NAME="user";
    String INSERT_FIELDS="username,password,salt,nickname";
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values (#{username},#{password},#{salt},#{nickname})"})
    int addUser(User user);
    @Select({"select * from",TABLE_NAME,"where username=#{username}"})
    User selectByUsername(String username);
    @Select({"select * from",TABLE_NAME,"where id=#{id}"})
    User selectUserById(int id);
    @Select({"select * from",TABLE_NAME,"where token=#{token}"})
    User selectByToken(User user);


}
