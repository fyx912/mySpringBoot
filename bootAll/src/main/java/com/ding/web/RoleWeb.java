package com.ding.web;

import com.ding.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName RoleWeb
 * @date 2020-03-09 12:28
 */
@RestController
@RequestMapping("role")
public class RoleWeb {

    @Autowired
    private SysRoleService roleService;

    @GetMapping("/")
    public String findAll(){
        return  roleService.findAll();
    }
}
