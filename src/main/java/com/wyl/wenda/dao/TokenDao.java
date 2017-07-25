package com.wyl.wenda.dao;

import com.wyl.wenda.model.Token;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value ="tokenDao")/*该注解解决注入UserDao时报错的问题*/
public interface TokenDao {
    String TABLE_NAME="token";
    String INSERT_FIELDS="userId,token,status";
    String SELECT_FIELDS="id,"+INSERT_FIELDS;
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values (#{userId},#{token},#{status})"})
    int addToken(Token token);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where token=#{ticket}"})
    Token selectByToken(String token);
    @Update({"update",TABLE_NAME,"set status=#{status} where token=#{token}"})
    void updateStatus(@Param("status") int status,@Param("token") String token);

}
