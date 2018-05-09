package com.ding.controller;

import com.ding.Utils.ValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ValidateCodeController {

    @RequestMapping(value="validateCode")
    public String validateCode(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",0);

        HttpSession  session = request.getSession();
        ValidateCode validateCode = new ValidateCode(80,30,4,30);
        session.setAttribute("code",validateCode.getCode());
        try {
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
