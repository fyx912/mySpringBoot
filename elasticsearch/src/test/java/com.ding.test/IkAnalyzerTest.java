package com.ding.test;

import com.ding.utils.StopWordsFileUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName IkAnalyzerTest
 * @date 2020-03-31 11:28
 */
public class IkAnalyzerTest {

    public static  void main(String[] args) throws IOException {
        String text="同时，这次疫情也暴露出我国在重大疫情防控体制机制、公共卫生应急管理体系等方面存在明显短板。推进我国公共卫生事件应急管理体系和能力现代化，任务更显紧迫！IKAnalyer can analysis english text too";
        IKAnalyzer analyzer = new IKAnalyzer(true);
        StringReader reader=new StringReader(text);
        TokenStream ts=analyzer.tokenStream("", reader);
        //获取词元位置属性
        OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
        //获取词元文本属性
        CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
        //获取词元文本属性
        TypeAttribute type = ts.addAttribute(TypeAttribute.class);
        ts.reset();
        while (ts.incrementToken()){
            System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
        }
        ts.end();
        reader.close();
        System.out.println("----------去除停用词-----------");

        stopWord(text);
        NumberFormat nf = NumberFormat.getPercentInstance();
        System.out.println(nf.format(0.24));
        BigDecimal data = new BigDecimal(0.24);
        BigDecimal result = data.multiply(new BigDecimal(100));

        System.out.println(result.scale());
    }


    public static void stopWord(String text) throws IOException {
        StringReader reader=new StringReader(text);
        IKAnalyzer analyzerStop = new IKAnalyzer(true);
        TokenStream tsStop=analyzerStop.tokenStream("", reader);
        //获取词元位置属性
        OffsetAttribute offsetStop = tsStop.addAttribute(OffsetAttribute.class);
        //获取词元文本属性
        CharTermAttribute termStop = tsStop.addAttribute(CharTermAttribute.class);
        //获取词元文本属性
        TypeAttribute typeStop = tsStop.addAttribute(TypeAttribute.class);

        tsStop.reset();
        while (tsStop.incrementToken()){
            if (typeStop.type().toLowerCase().contains("cn")){
                //去除停用词
                if(StopWordsFileUtil.readStopWords().contains(termStop.toString())) {
                    continue;
                }
            }else {
                //去除停用词
                if(StopWordsFileUtil.readStopWords("en").contains(termStop.toString())) {
                    continue;
                }
            }
            System.out.println(offsetStop.startOffset() + " - " + offsetStop.endOffset() + " : " + termStop.toString() + " | " + typeStop.type());
        }
        tsStop.end();
        reader.close();
    }

}
