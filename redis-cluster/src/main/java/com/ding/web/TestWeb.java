package com.ding.web;

import com.ding.domain.User;
import com.ding.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName TestWeb
 * @date 2020-11-12 15:51
 */
@RestController
@RequestMapping("/user")
public class TestWeb {
    @Resource
    private UserService userService;

    /**
     *  查找所有
     * @return
     */
    @GetMapping("/getAll")
    public String getAll(){
        return userService.getAll();
    }
    /**
     * 根据id获取用户
     * @return
     */
    @GetMapping("/getOne")
    public User getOne(Long id){
        return userService.getOne(id);
    }
    /**
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping("/insertUser")
    public String insertUser(User user) {
        userService.insertUser(user);
        return "insert success";
    }
    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "update success";
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping("/deleteUser")
    public String deleteOne(@RequestParam("id") Integer id) {
        userService.deleteOne(id);
        return "delete success";
    }
}
