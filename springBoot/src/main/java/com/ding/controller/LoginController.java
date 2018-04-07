package com.ding.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ding.Utils.CodeJson;
import com.ding.domain.User;
import com.ding.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String login(@RequestBody String json, HttpServletRequest request) {
        System.out.println(request.getContentType());
        System.out.println("json=" + json);
        if (request.getContentType().contains("application/json")) {
            try {
                JSONObject jsonObject = JSON.parseObject(json);
                String userName = jsonObject.getString("userName");
                String password = jsonObject.getString("password");
                String code = jsonObject.getString("code");
                String sessionCode = (String) request.getSession().getAttribute("code");
                if (StringUtils.isNotEmpty(code)&&code.equalsIgnoreCase(sessionCode)){
                    System.out.println(" str userName= " + userName + "\t password=" + password);
//                System.out.println(" userName= " + user.getUserName() + "\t password=" + user.getPassword());
                    if (StringUtils.isNotEmpty(userName)) {
                        if (StringUtils.isNotEmpty(password)) {
                            User user = this.userService.isLogin(userName, password);
                            if (user!=null&&user.getUserName().equals(userName)&&user.getPassword().equals(password)){
                                request.getSession().setAttribute("userName",userName);
                                return CodeJson.success();
                            }
                            return CodeJson.failed("用户或密码错误!");
                        } else {
                            return CodeJson.failed("密码错误!");
                        }
                    } else {
                        return CodeJson.failed("userName错误!");
                    }
                }else {
                    return CodeJson.failed("验证码错误!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return CodeJson.failed("Json 异常!");
            }
        } else {
            return CodeJson.failed("参数不是JSON格式!");
        }
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String signIn() {
        return "login";
    }

    @RequestMapping("index")
    public String index() {
        System.out.println("index");
        return "index";
    }
}
