/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: EmployeController
 * Author:   THINK
 * Date:     2019/3/21 17:10
 * Description: 雇员信息Controller
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ding.web;

import com.ding.model.Employer;
import com.ding.repository.EmployerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 
 * 〈雇员信息Controller〉
 *
 * @author THINK
 * @create 2019/3/21
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("es")
public class EmployerController {

    @Autowired
    private EmployerRepository repository;

    @GetMapping("add")
    public String add(){
        Employer employer = new Employer();
        employer.setId("1");
        employer.setFirstName("ding");
        employer.setFirstName("yi");
        employer.setAge(28);
        employer.setAbout(" I am in peking!");
        repository.save(employer);
        log.info(" add a Object!");
        return "success";
    }

    @GetMapping("query")
    public String query(){
        Employer employer = repository.queryEmployeeById("1");
        log.info(employer.toString());
        return  employer.toString();
    }

}
