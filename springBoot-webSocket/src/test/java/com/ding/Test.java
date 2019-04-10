package com.ding;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test {
    private static Map<String,String> map = new ConcurrentHashMap<>();
    public static void main(String[] args){
        map.put("hello","hello");
        System.out.println(map.get("hello"));
    }


}
