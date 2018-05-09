package com.ding.controller;

import com.ding.Utils.CodeJson;
import com.ding.domain.User;
import com.ding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "user",method = RequestMethod.GET)
    @ResponseBody
    public String userAll(){
        long startTime = System.currentTimeMillis();
        List<User> list = this.userService.getUser();
        long endTime = System.currentTimeMillis()-startTime;
        System.out.println("endTime=====>"+endTime);
        return  CodeJson.success(list);
    }

    @RequestMapping(value = "user/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String user(@PathVariable int id ){
        User user = this.userService.getUserById(id);
        String str =  CodeJson.success(user);
        System.out.println("str=="+str);
        return str;
    }

    @RequestMapping(value = "user",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String user(User user){
        System.out.println(user);
        if (user != null){
            System.out.println(user.getId()+user.getUsername());
            return  this.userService.insertUser(user).toString();
        }else {
            return "参数为空";
        }
    }

    @RequestMapping(value = "user/{id}",method = RequestMethod.PUT )
    @ResponseBody
    public int updateUser(@PathVariable int id,@RequestBody  User user){
        user.setId(id);
        return  this.userService.updateUser(user);
    }

    @RequestMapping(value = "user/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public String updateUser(@PathVariable int id){
        Integer getId = this.userService.getUserId(id);
        if (getId!=null&&getId==id){
            this.userService.deleteUser(id);
            return  CodeJson.success();
        } else {
            return CodeJson.failed();
        }
    }
}
