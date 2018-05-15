package com.ding.com.ding.web;

import com.ding.util.HttpClientUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorldCupController {

    @RequestMapping(value = "wcgj",method = RequestMethod.GET)
    public String wcgj(){
        String wcgjUrl = "http://i.sporttery.cn/rank_calculator/get_rank_data?tid[]=104895&&pcode[]=chp&i_callback=getChpDetail";
        String wcgjData = HttpClientUtils.getMethod(wcgjUrl);
        wcgjData = wcgjData.split(")")[0];
        wcgjData = wcgjData.split("(")[1];
        return wcgjData;
    }


    @RequestMapping(value = "wcgyj",method = RequestMethod.GET)
    public String wcgyj(){
        String wcgyjUrl = "http://i.sporttery.cn/rank_calculator/get_rank_data?tid[]=104895&&pcode[]=fnl&i_callback=getChange&_=1526372433248";
        String wcgyjData = HttpClientUtils.getMethod(wcgyjUrl);
        wcgyjData = wcgyjData.split(")")[0];
        wcgyjData = wcgyjData.split("(")[1];
        return wcgyjData;
    }
}
