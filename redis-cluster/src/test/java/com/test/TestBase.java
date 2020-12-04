package com.test;

import com.ding.RedisClusterApp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName TestBase
 * @date 2020-11-12 11:50
 */
@SpringBootTest(classes = {RedisClusterApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)//导入spring测试框架
@AutoConfigureMockMvc
public class TestBase {
}
