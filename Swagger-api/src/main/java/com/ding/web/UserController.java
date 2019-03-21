package com.ding.web;

import com.ding.domain.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Cacheable(value = "user.all")
    @ApiOperation(value = "获取所有用户信息",notes = "获取所有用户信息")
    @RequestMapping(value = "user",method = RequestMethod.GET)
    public List<User> user(){
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("123456");
        user.setName("tinTin");
        user.setAddress("shenZhen");
        list.add(user);
        try {
            Thread.sleep(1000)
            ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @ApiOperation(value = "根据ID获取用户信息",notes = "根据ID获取用户信息")
    @ApiImplicitParam(dataType = "int",name = "id",value = "id",required = true,paramType = "path")
    @RequestMapping(value = "user{id}",method = RequestMethod.GET)
    public User user(@PathVariable Integer id){
        User user = new User();
        user.getId();
        user.setUsername("admin");
        user.setPassword("123456");
        user.setName("tinTin");
        user.setAddress("shenZhen");
        return user;
    }

    @ApiOperation(value = "根据ID修改用户信息")
    @ApiImplicitParam(dataType = "int",name = "id",value = "id",required = true,paramType = "path")
    @RequestMapping(value = "user{id}",method = RequestMethod.PUT)
    public User updateUser(@PathVariable Integer id){
        User user = new User();
        user.getId();
        user.setUsername("admin");
        user.setPassword("123456");
        user.setName("tinTin");
        user.setAddress("shenZhen");
        return user;
    }

    @ApiOperation(value = "根据ID删除用户信息")
    @ApiImplicitParam(dataType = "int",name = "id",value = "id",required = true,paramType = "path")
    @RequestMapping(value = "user{id}",method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Integer id){
        return "ok";
    }

    @ApiOperation(value = "保存除用户信息")
    @ApiImplicitParam(dataType = "int",name = "id",value = "id",required = true,paramType = "path")
    @RequestMapping(value = "user",method = RequestMethod.POST)
    public String deleteUser(){
        return "ok";
    }
}
