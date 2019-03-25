package com.ding.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ding.Application;
import com.ding.model.Employer;
import com.ding.repository.EmployerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class ElasticsearchTest {

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EmployerRepository employerRepository;

    @Test
    public void add(){
        List<Employer> list = new ArrayList<>();
        list.add(new Employer("3","ali","ali",3,"----------",new Date()));
        list.add(new Employer("4","bit","bit",20,"????????",new Date()));
        list.add(new Employer("5","dang","duang",35,"11111111",new Date()));
        list.add(new Employer("6","美团","外买",20," OFO ",new Date()));
        list.add(new Employer("7","spring","spring boot",35,"spring project",new Date()));
        list.add(new Employer("8","add","add",20,"add",new Date()));
        list.add(new Employer("9","update","update",35,"update -------",new Date()));
        list.add(new Employer("10","delete","delete",20,"",new Date()));
        list.add(new Employer("11","query","query",35,"",new Date()));
        list.add(new Employer("12","京东","JD",20,"JD",new Date()));
        list.add(new Employer("13","TC","tc",35,"tc",new Date()));
        list.add(new Employer("14","go","goLang",20,"12.3 version",new Date()));
        employerRepository.saveAll(list);
        log.info("success! "+list);
    }

    @Test
    public void delete(){
        employerRepository.deleteById("14");
        log.info("delete 14 success!");
    }

    @Test
    public void exists(){
       boolean id =  employerRepository.existsById("14");
       log.info(" id retrun :"+id);
    }

    @Test
    public void findAll(){
       Iterable<Employer> iterable =  employerRepository.findAll();
       log.info(JSON.toJSONString(iterable));
    }
}
