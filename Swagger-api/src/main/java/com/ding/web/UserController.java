package com.ding.web;

import com.ding.common.exception.UserException;
import com.ding.domain.User;
import com.ding.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Cacheable(value = "user")
    @ApiOperation(value = "获取所有用户信息",notes = "获取所有用户信息")
    @RequestMapping(value = "user",method = RequestMethod.GET)
    public List<User> userAll(){
        this.stop();
        return userService.findUserAll();
    }

    @Cacheable(value = "user",key="#id")
    @ApiOperation(value = "根据ID获取用户信息",notes = "根据ID获取用户信息")
    @ApiImplicitParam(dataType = "int",name = "id",value = "id",required = true,paramType = "path")
    @RequestMapping(value = "user/{id}",method = RequestMethod.GET)
    public User findUser(@PathVariable Integer id){
        stop();
        return userService.findUserById(id);
    }

//    @CachePut(value = "user",key="#id")
    @ApiOperation(value = "根据ID修改用户信息")
    @ApiImplicitParam(dataType = "int",name = "id",value = "id",required = true,paramType = "path")
    @RequestMapping(value = "user/{id}",method = RequestMethod.PUT)
    public User updateUser(@PathVariable Integer id){
        stop();
        return userService.updateUserById(id);
    }

    @RequestMapping(value = "user/update/{id}")
    public User updateUsers(@PathVariable Integer id){
        stop();
        return userService.updateUserById(id);
    }

    //清除一条缓存，key为要清空的数据
//    @CacheEvict(value = "user",key="#id")
    @ApiOperation(value = "根据ID删除用户信息")
    @ApiImplicitParam(dataType = "int",name = "id",value = "id",required = true,paramType = "path")
    @RequestMapping(value = "user/{id}",method = RequestMethod.DELETE)
    public String user(@PathVariable Integer id){
        if (id==null){
            throw new UserException("id not is null!");
        }
        stop();
        return userService.deleteUser(id);
    }

    @RequestMapping(value = "user/delete/{id}")
    public String deleteUser(@PathVariable Integer id){
        if (id==null){
            throw new UserException("id not is null!");
        }
        stop();
        return userService.deleteUser(id);
    }

    //方法调用后清空所有缓存
//    @CacheEvict(value = "user",allEntries = true)
    //方法调用前清空所有缓存
//    @CacheEvict(value = "user",beforeInvocation = true)
    @ApiOperation(value = "删除所有用户信息")
    @ApiImplicitParam(dataType = "int",name = "id",value = "id",required = true,paramType = "path")
    @RequestMapping(value = "user",method = RequestMethod.DELETE)
    public String user(){
        stop();
        return userService.deleteAll();
    }

    @RequestMapping(value = "user/delete")
    public String deleteUser(){
        stop();
        return userService.deleteAll();
    }

    public void stop(){
        try {
            Thread.sleep(1000)
            ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
