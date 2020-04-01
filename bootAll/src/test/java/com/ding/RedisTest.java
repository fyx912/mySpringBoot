package com.ding;

import com.ding.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName RedisTest
 * @date 2020-03-11 20:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootAllApp.class)
public class RedisTest {
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void Test(){
        boolean flag = redisUtils.set("ding","123456");
        System.out.println(flag);
        System.out.println(redisUtils.get("ding"));
    }

    @Test
    public  void map(){
        Map<String,Object> map = new HashMap();
        map.put("username","fyx912");
        map.put("address","SZ");

        boolean flag =redisUtils.hmset("my_hash",map);
        Map result =redisUtils.hmget("my_hash");
        System.out.println(result);

    }
}
