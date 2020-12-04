package com.test;

import com.ding.config.RedisUtils;
import com.ding.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName RedisTest
 * @date 2020-11-10 13:51
 */
public class RedisTest extends TestBase {
    @Resource
    private RedisUtils redisUtils;

    @Test
    public void  test() throws JsonProcessingException {
        User user = new User();
        user.setId(1);
        user.setUserName("admin");
        user.setPassword("pwd123456");
        user.setAge(20);
        user.setGender(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        System.out.println("json==" + json);

        String userKey = "user#admin";
        String userJsonKey = "userJson#admin";
        redisUtils.set(userKey,user);
        redisUtils.set(userJsonKey,json);

        User resultUser = (User) redisUtils.get(userKey);
        System.out.println("get redis data ==" + resultUser);

        String resultJson = (String) redisUtils.get(userJsonKey);
        System.out.println("get redis data json ==" + resultJson);
    }

    @Test
    public void map() throws JsonProcessingException {
        String key = "mapKey";
        Map<String,Object> map = new HashMap();
        map.put("name","ding");
        map.put("username","root");
        map.put("password","pwd123456");
        redisUtils.hmset(key,map);
        Map<Object, Object> result =  redisUtils.hmget(key);
        System.out.println("json= "+new ObjectMapper().writeValueAsString(result));
    }

    @Test
    public void list() throws JsonProcessingException {
        String key = "userList";
        redisUtils.lSet(key,"张三");
        redisUtils.lSet(key,"李四");
        List<Object> data =  redisUtils.lGet(key,0,2);
        System.out.println("size = "+data.size());
        System.out.println("json= "+new ObjectMapper().writeValueAsString(data));
    }

}
