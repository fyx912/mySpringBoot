package com.ding.test;

import com.ding.common.utils.ProfileResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther: ding
 * @Date: 2020-01-02 11:16
 * @Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CodeStatusTest {
    @Autowired
    private ProfileResolver resolver;

    @Test
    public void test(){
        System.out.println(resolver.code("0"));
    }
}
