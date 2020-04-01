package com.ding.web;

import com.ding.utils.EsUtil;
import com.ding.utils.EsUtilService;
import com.ding.utils.JsonResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName EsWeb
 * @date 2020-04-01 15:28
 */
@Slf4j
@RestController
public class EsWeb {

    @Autowired
    private EsUtilService service;

    /**
     *  ES 当前index总数
     * @param index
     * @return
     */
    @GetMapping("count/{index}")
    public String count(@PathVariable String index){
        long   count = service.count(index);
        return  JsonResultUtils.success(count);
    }

    /**
     *  查找 ES 数据
     * @param index es index
     * @param field 查找字段
     * @param text  查找的文本
     * @param startRow  起始页
     * @param size  页面大小
     * @return
     */
    @GetMapping("search/{index}")
    public String select(@PathVariable String index,String field,String text ,Integer startRow,Integer size){
        if (!this.checkParams(index,field,text)){
            return JsonResultUtils.failed("非法参数!");
        }
        List list = service.select(index,field,text,startRow,size);
        List result =  EsUtil.scoreCalculation(text,list,field);
        log.info("result:[{}]",JsonResultUtils.success(result));
        return  JsonResultUtils.success(result);
    }


    /**
     * 参数检验
     * @param params 字符串数组
     * @return
     */
    private boolean checkParams(String ... params){
        for(String param:params) {
            if (param == "" || param == null || param.isEmpty()) {
                return false;
            }
        }
        return  true;
    }
}
