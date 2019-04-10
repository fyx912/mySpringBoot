package com.ding.web;

import com.ding.model.Student;
import com.ding.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @RequestMapping(value = {"","/","index"},method = RequestMethod.GET)
    public String index(Model model){
        List<Student> list = new ArrayList<>();
        list.add(new Student("张三", 20, "北京"));
        list.add(new Student("李四", 30, "上海"));
        list.add(new Student("王五", 40, "河北"));
        list.add(new Student("赵六", 50, "山西"));
        model.addAttribute("list",list);
        return "/index";
    }

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(UserInfo user){
        UserInfo.map.put(user.getPhone(),user);
        return "redirect:/chat/list?token="+user.getPhone();
    }
}
