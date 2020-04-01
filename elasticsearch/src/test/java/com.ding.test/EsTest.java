package com.ding.test;

import com.alibaba.fastjson.JSONObject;
import com.ding.Application;
import com.ding.utils.EsUtil;
import com.ding.utils.EsUtilService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName EsTest
 * @date 2020-03-31 10:06
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class EsTest {

    @Autowired
    private EsUtilService service;

    @Test
    public void select() throws IOException {
        String text = "同时，这次疫情也暴露出我国在重大疫情防控体制机制、公共卫生应急管理体系等方面存在明显短板。推进我国公共卫生事件应急管理体系和能力现代化，任务更显紧迫";
        List list = service.select("book","content",text,0,5);
        System.out.println(JSONObject.toJSON(list));

        List result =  EsUtil.scoreCalculation(text,list,"content");

        System.out.println(JSONObject.toJSON(result));
    }

    @Test
    public void count() throws IOException {
        long count = service.count("book");
        System.out.println(count);
    }
}
