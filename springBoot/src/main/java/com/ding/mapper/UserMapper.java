package com.ding.mapper;

import com.ding.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from User")
    List<User> getUser();

    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);

    @Select("select id from user where id = #{id}")
    Integer getUserId(@Param("id") int id);


    @Select("select userName,password from user where userName = #{userName} and password = #{password}")
    User isLogin(@Param("userName") String userName,@Param("password") String password);

//    @InsertProvider(type = UserSqlFacttory.class,method = "insertUser")
    @Insert("INSERT INTO user  (id, userName, name,password, age, phone,date) " +
            "VALUES (#{user.id},#{user.userName},#{user.name},#{user.password},#{user.age},#{user.phone},#{user.date})")
    @Options(useGeneratedKeys = true,keyProperty = "user.id",keyColumn = "id")
    Integer insertUser(@Param("user") User user);

    @UpdateProvider(type=UserSqlFactory.class,method = "updateUser")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer updateUser(@Param("user") User user);

    @Delete("delete from user where id = #{id}")
    @Options(useGeneratedKeys = true,keyProperty = "User.id",keyColumn = "id")
    Integer deleteUser(@Param("id") int id);
}
