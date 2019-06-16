package com.ding.mapper;


import com.ding.domain.User;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class UserSqlFactory {
    public String insertUser(Map<String ,User> para){
        User user = para.get("user");
        SQL sql = new SQL();
        sql.INSERT_INTO("User");

        if (StringUtils.isNotEmpty(user.getId().toString())){
            sql.VALUES("id",user.getId().toString());
        }
        if (StringUtils.isNotEmpty(user.getUsername())){
            sql.VALUES("userName",sqlStringColumn(user.getUsername()));
        }
        if (StringUtils.isNotEmpty(user.getPassword())){
            sql.VALUES("password",sqlStringColumn(user.getPassword()));
        }
        if (StringUtils.isNotEmpty(user.getName())){
            sql.VALUES("name",sqlStringColumn(user.getName()));
        }
        if (StringUtils.isNotEmpty(user.getAge().toString())&&user.getAge()>0&&user.getAge()<150){
            sql.VALUES("age",user.getAge().toString());
        }
        if (StringUtils.isNotEmpty(user.getPhone())){
            sql.VALUES("phone",sqlStringColumn(user.getPhone()));
        }
        sql.VALUES("date",sqlStringColumn(new Date().toString()));
        return  sql.toString();
    }

    public  String updateUser(Map<String,User> param){
        User user = param.get("user");
        SQL sql = new SQL();
        sql.UPDATE("User");
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(user.getUsername())) {
            sb.append("userName="+sqlStringColumn(user.getUsername())+",");
        }
        if (StringUtils.isNotEmpty(user.getPassword())){
            sb.append("password="+sqlStringColumn(user.getPassword())+",");
        }
        if (StringUtils.isNotEmpty(user.getName())){
            sb.append("name="+sqlStringColumn(user.getName())+",");
        }
        if (StringUtils.isNotEmpty(user.getAge().toString())&&user.getAge()>0&&user.getAge()<150){
            sb.append("age="+user.getAge()+",");
        }
        if (StringUtils.isNotEmpty(user.getPhone())){
            sb.append("phone="+sqlStringColumn(user.getPhone())+",");
        }
        sb.append("date='"+LocalDate.now().toString() +"',");
        sql.SET(sb.toString().substring(0,sb.length()-1));
        sql.WHERE("id = "+user.getId());
        return sql.toString();
    }

    String sqlStringColumn(String column){
        StringBuilder sb = new StringBuilder();
        sb.append("\'");
        sb.append(column);
        sb.append("\'");
        return sb.toString();
    }

}
