package com.ding.com.ding.web;

import com.ding.util.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class WorldCup {
    public static void  main(String [] args){
        String uri = "http://info.sporttery.cn/rankodds/WorldCupCal.html";
        String result = HttpClientUtils.getMethod(uri);
//        System.out.println(" get html :" +result);
        Document doc = Jsoup.parse(result);
        Element headElement = doc.head();

        Elements element =headElement.getElementsByTag("meta");
        System.out.println(element.get(0).toString());
        System.out.println(element.get(1).toString());
        String context = headElement.getElementsByTag("meta").last().attr("content");
        String charset = context.split("=")[1];
        System.out.println("获取编码:"+charset);

        Element bodyElement = doc.body();
        String guanjun = bodyElement.select("#wcselgj.W_sel.unsel.FloatL").first().text();
        System.out.println(guanjun);
        String yj = bodyElement.select("#wcselgyj.W_sel.sel.FloatL").first().text();
        System.out.println(yj);


        Elements wcgj = bodyElement.select("div#wcgj.WorldCup_con");
        Elements wcgjTable = wcgj.select("table#mainTbl");
        System.out.println(wcgjTable);
        Elements wcgjTableTr = wcgjTable.select("tr.listTr.selling.tosort");
        System.out.println(wcgjTableTr.size());

//        Elements wcgjOne =  wcgj.get(0).select("div.qiudui.FloatL").select("table#mainTbl").select("tbody#chp_1");

        String wcgjUrl = "http://i.sporttery.cn/rank_calculator/get_rank_data?tid[]=104895&&pcode[]=chp&i_callback=getChpDetail";
        String wcgjData = HttpClientUtils.getMethod(wcgjUrl);

        System.out.println("冠军数据："+wcgjData);

        String wcgyjUrl = "http://i.sporttery.cn/rank_calculator/get_rank_data?tid[]=104895&&pcode[]=fnl&i_callback=getChange&_=1526372433248";
        String wcgyjData = HttpClientUtils.getMethod(wcgyjUrl);

        System.out.println("冠亚军数据："+wcgjData);

    }

}


