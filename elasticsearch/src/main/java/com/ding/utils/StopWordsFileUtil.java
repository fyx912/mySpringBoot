package com.ding.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName FileUtil
 * @date 2020-04-01 11:11
 */
@Slf4j
public class StopWordsFileUtil {
    public static  String CONFIG_Name="config";
    public static String FILE_NAME= "cn_word";
    public static String fileSuffix= ".txt";//文件名，后缀

    /**
     *  读取停用词
     * @param type  中文=cn ，英文=en
     * @return
     */
    public static Set<String> readStopWords(String type){
        if (StringUtils.isEmpty(type)){
            return  readStopWords();
        }
        String filePath = StopWordsFileUtil.class.getClassLoader().getResource(CONFIG_Name).getPath();
        filePath += File.separator;
        if (type.equalsIgnoreCase("en")){
            filePath += "english"+fileSuffix;
        }else {
            filePath += FILE_NAME+fileSuffix;
        }
        return read(filePath);
    }

    /**
     *  读取停用词,默认中文停用词
     * @return
     */
    public static Set<String> readStopWords(){
        String filePath = StopWordsFileUtil.class.getClassLoader().getResource(CONFIG_Name).getPath();
        filePath += File.separator;
        filePath += FILE_NAME+fileSuffix;
        return read(filePath);
    }

    /**
     * 可读取默认或指定路径文件停用词
     * @param path 文件路径
     * @param fileName 文件名
     * @return
     */
    public static Set<String> readStopWords(String path ,String fileName){
        String filePath = "";
        if (StringUtils.isNotEmpty(path)){
            filePath = path;
        }else {
            filePath = StopWordsFileUtil.class.getClassLoader().getResource(CONFIG_Name).getPath();
        }
        filePath += File.separator;
        if (StringUtils.isNotEmpty(fileName)) {
            filePath += fileName;
        }else {
            filePath += FILE_NAME+fileSuffix;
        }

        return read(filePath);
    }

    /**
     * 文件路径
     * @param filePath
     * @return
     */
    public static Set<String> read(String filePath){
        Set<String> stopWordSet = new HashSet<>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
            //初如化停用词集
            String stopWord = null;
            for(; (stopWord = in.readLine()) != null;){
                stopWordSet.add(stopWord);
            }
            in.close();
        }catch (IOException e){
            log.error("停用词表读取失败, because:{}",e);
        }
        return  stopWordSet;
    }
}
