package com.ding.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName EsUtil
 * @date 2020-03-25 18:03
 */
public class EsUtil {
    private static Logger log  = LoggerFactory.getLogger(EsUtil.class);

    /**
     *  更加对象动态创建es中的mapping映射
     * @param obj 对象
     * @return
     */
    public static Map<String,Object> dynamicMappingKey(Object obj) {
       Map<String,Object> map = ObjectReflectUtil.getObjectKeyAndType(obj);

       for (Map.Entry entry : map.entrySet()){

           if (entry.getValue().toString().endsWith("String") ){
               System.out.println(entry.getKey()+"\t"+entry.getValue());
           }
       }
        return null;
    }


    /**
     *  IK Analyze 分词器:smart 粗分,ik_max_word细分
     * @param useSmart  smart 粗分 分词(true)，ik_max_word(false) 细分
     * @param text  文本内容
     * @return
     * @throws IOException
     */
    public static List<String> ikAnalyzeWords(boolean useSmart,String text){
        if (StringUtils.isEmpty(text)){
            throw new IllegalArgumentException("非法参数!");
        }
        List<String>  list = null;
        try {
            list = new ArrayList<>();
            IKAnalyzer analyzer = new IKAnalyzer(useSmart);
            StringReader reader=new StringReader(text);
            TokenStream ts=analyzer.tokenStream("", reader);
//            //获取词元位置属性
//            OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
//            //获取词元文本属性
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            //获取词元文本属性
            TypeAttribute type = ts.addAttribute(TypeAttribute.class);
            ts.reset();
            while (ts.incrementToken()){
//                log.info("{}-{}:{}|{}",offset.startOffset() , offset.endOffset(),term.toString(),type.type());
                if (type.type().toLowerCase().contains("cn")){
                    //去除中文停用词
                    if(StopWordsFileUtil.readStopWords().contains(term.toString())) {
                        continue;
                    }
                }else {
                    //去除英文停用词
                    if(StopWordsFileUtil.readStopWords("en").contains(term.toString())) {
                        continue;
                    }
                }
                list.add(term.toString());
            }
            reader.close();
            ts.end();
        }catch (IOException e){
            log.error("IKAnalyzer error! because:{}",e.getMessage());
            throw  new RuntimeException("IKAnalyzer 解析异常!",e);
        }
        log.info("ik Analyze Words  list:{}",JSON.toJSONString(list));
        return  list;
    }

    /**
     *  计算数据的评分后的json
     * @param source     源数据
     * @param targetList ES目标集合数据
     * @param KeyField   需要匹配分析的字段
     * @return
     */
    public static List scoreCalculation(String source, List targetList, String KeyField){
        boolean useSmart = true;
        List result = new ArrayList();
        List sourceList = ikAnalyzeWords(useSmart,source);
        log.info("sourceList 分词:[{}]",JSON.toJSONString(sourceList));

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(targetList));
        for (int i = 0; i < jsonArray.size(); i++) {
            Object obj = targetList.get(i);
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
//            log.info("targetList[{}],json={}",i,jsonObject);
            String fieldKeyText = jsonObject.getString(KeyField);
            List targetTextList = ikAnalyzeWords(useSmart,fieldKeyText);
            Double score = intersectionSumScore(sourceList,targetTextList);//匹配度
            //去除低分项
            if (deleteLowScore(score,30)){
                jsonObject.put("match",NumberFormat.getPercentInstance().format(score));
                result.add(jsonObject);
            }
        }
        return result;
    }

    /**
     *  计算交集数据的匹配度的评分
     *  算法: key * 2 / (key1+key2)
     *   set1:  取key1+key2交集数据，交集value 为当前key的最小值
     *   set2： 获取各个集合个key的总数
     *   set3： 计算结果
     * @param sourceList   源数据分词后的集合
     * @param targetList   目标数据分词后的集合
     * @return
     */
    public static Double intersectionSumScore(List sourceList,List targetList){
        if (sourceList==null||targetList==null){
            return null;
        }
        Map<String,Integer> sourceMap= listUniqToMapSize(sourceList);//源分词list
        Map<String,Integer>  targetMap= listUniqToMapSize(targetList);//目标分词list
        Map<String,Integer> intersectionMap = new HashMap();//交集分词map

        //取2个map相同key的值
        for (Map.Entry<String,Integer> sourceEntry : sourceMap.entrySet()){
            String sourceKey = sourceEntry.getKey();
            Integer sourceValue =  sourceMap.get(sourceKey);
            if (targetMap.containsKey(sourceKey)){ //当目标Map存在key，则取出2个MAP的最小值
                Integer targetValue =  targetMap.get(sourceKey);
                if (new BigDecimal(sourceValue).compareTo(new BigDecimal(targetValue))==-1){
                    intersectionMap.put(sourceKey,sourceValue);
                }else {
                    intersectionMap.put(sourceKey,targetValue);
                }
            }
        }
        log.info("sourceMap:[{}],targetMap:[{}],intersectionMap:[{}]",sourceMap,targetMap,intersectionMap);
        int sum = getMapSum(intersectionMap) * 2;
        int keyAllSum = getMapSum(sourceMap) + getMapSum(targetMap);

        BigDecimal result = new BigDecimal(sum).divide(new BigDecimal(keyAllSum), 2,RoundingMode.HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();  //建立百分比格式化引用
        log.info("匹配度:[{}],交集map:{},交集key*2={},keyAll={},compatibility:{},",percent.format(result),intersectionMap,sum,keyAllSum);
        sourceMap.clear();
        targetMap.clear();
        intersectionMap.clear();
        return result.doubleValue();
    }

    /**
     * 获取map中value的和
     * @param map
     * @return
     */
    public static int getMapSum(Map<String,Integer> map){
        int sum = 0;
        for (Map.Entry<String,Integer> entry : map.entrySet()){
            sum +=  entry.getValue();
        }
        return sum;
    }


    /**
     *  集合数据去重后，统计key的大小
     * @param list  集合数据
     * @return
     */
    public static Map<String,Integer> listUniqToMapSize(List list){
        Map<String,Integer> map = new HashMap<>();
        Set set = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            int countSize = 0;
            String key = iterator.next().toString();
            for (int i = 0; i < list.size(); i++) {
                if (key.equals(list.get(i))){
                    countSize ++;
                }
            }
            map.put(key,countSize);
        }
        return map;
    }

    /**
     * 去除低分数据
     * @param data 当前分
     * @param lowScore  最低分标准
     * @return false 去除，true保存
     */
    public static boolean deleteLowScore(Double data,int lowScore){
        data *= 100;
       if (data.intValue()>lowScore){
           return true;
       }
       return false;
    }

}
