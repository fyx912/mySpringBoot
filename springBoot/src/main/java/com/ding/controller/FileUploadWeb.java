package com.ding.controller;

import com.alibaba.fastjson.JSONObject;
import com.ding.common.utils.FileUtils;
import com.ding.common.utils.JsonResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Auther: ding
 * @Date: 2019-06-21 14:58
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("file")
public class FileUploadWeb {
    private String nasBasePathDir="/Users/ding/app/nas";

    @GetMapping("")
    public String file(){
        return "file";
    }

    /**
     * 文件上传
     * @param file 文件
     * @param type 1、图片；2、视频
     * @Parma value:Md5
     * @return
     */
    @PostMapping(value = "upload",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam(value = "type",required = false) String type, @RequestParam("value") String value){
        log.info("type:{},file:{},md5:{}",type,file.getSize(),value);
        if (!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            try{
                String md5FileName = FileUtils.getFileMD5(file.getInputStream());
                log.info("file get md5 value:{}",md5FileName);
                if (value.equalsIgnoreCase(md5FileName)){
                    log.info("file md5 validate success!");
                    String suffixName = FileUtils.suffixName(fileName);
                    if (FileUtils.APP.contains(suffixName)|| FileUtils.IMAGES_TYPE.contains(suffixName)||FileUtils.DOC.contains(suffixName)){
                        String newFileName = md5FileName+"."+suffixName;
                        String result = FileUtils.saveFile(file.getInputStream(),newFileName,nasBasePathDir);
                        log.info("file upload success:{}",result);
                        JSONObject json = JSONObject.parseObject(result);
                        json.put("url",json.getString("data"));
                        log.info("file upload success2:{}",json);
                        return json.toJSONString();
                    }else {
                        log.info("file 不支持的文件格式!!");
                        return JsonResultUtils.failed("不支持的文件格式!");
                    }
                }else {
                    log.info("file md5 validate failed!");
                    return JsonResultUtils.failed("文件校验不一致,请检查文件!");
                }
            }catch (IOException e){
                log.error("file upload failed:{}",e);
                e.printStackTrace();
                return JsonResultUtils.failed(e.getMessage());
            }
        }else {
            return JsonResultUtils.failed();
        }
    }
}
