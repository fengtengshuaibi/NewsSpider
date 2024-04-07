package com.intelligent.marking.intelligentmarking.utils;

import com.alibaba.fastjson.JSON;
import com.intelligent.marking.intelligentmarking.vo.model.Data;
import com.intelligent.marking.intelligentmarking.vo.model.IntelligentMarkingBO;
import com.intelligent.marking.intelligentmarking.vo.model1.IntelligentMarkingBO2;
import com.intelligent.marking.intelligentmarking.vo.model1.SourceData2;
import com.intelligent.marking.intelligentmarking.vo.model2.Newslist;
import lombok.extern.slf4j.Slf4j;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.recognition.impl.NatureRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.*;

@Slf4j
public class AnsjUtils {
    //只关注这些词性的词
    public static final Set<String> expectedNature = new HashSet<String>() {{
        add("n");add("v");add("vd");add("vn");add("vf");
        add("vx");add("vi");add("vl");add("vg");
        add("nt");add("nz");add("nw");add("nl");
        add("ng");add("userDefine");add("wh");
        add("ns");add("nr");add("nrf");
    }};
    public static String analyse(String text){
        return String.valueOf(NlpAnalysis.parse(text));
    }
    public static void main(String[] args) throws Exception {

        String json = HttpUtil.getJsonFromWeb("http://apiv2.nbd.com.cn/article_lists/multi_columns_article_lists.json?app_key=d401a38c50a567882cd71cec43201c78&app_version_name=7.2.5&column_ids=1838&timestamp=1700666476&uuid=9aa9802925b94a5b83b3ffe50e188458");
        IntelligentMarkingBO markingBO = JSON.parseObject(json, IntelligentMarkingBO.class);
        KeyWordComputer kwc = new KeyWordComputer(5);

        List keywordsList=new ArrayList();
        for (Data markingBODatum : markingBO.getData()) {
            String title = markingBODatum.getTitle();
            Collection<Keyword> result = kwc.computeArticleTfidf(title);
            List keywordList=new ArrayList();
            for (Keyword keyword : result) {
                keywordList.add(keyword.getName());
            }
            keywordsList.add(keywordList);
        }
        System.out.println(keywordsList);
    }
    public static IntelligentMarkingBO getSourceData(String url) throws Exception {
        String json = HttpUtil.getJsonFromWeb(url);
        IntelligentMarkingBO markingBO = JSON.parseObject(json, IntelligentMarkingBO.class);
        return markingBO;
    }
    public static List<String> getKeyword(Data markingBODatum) throws Exception {

            KeyWordComputer kwc = new KeyWordComputer(5);
            String title = markingBODatum.getTitle();
            long start = System.currentTimeMillis();
            log.info("开始计算关键词");
            Collection<Keyword> result = kwc.computeArticleTfidf(title);
            long end = System.currentTimeMillis();
            log.info("计算关键词结束，{}",(end-start)/1000);
            List keywordList=new ArrayList();
            for (Keyword keyword : result) {
                keywordList.add(keyword.getName());
            }
        log.info(keywordList.toString());
        return keywordList;
    }
    public static List<String> getKeyword2(SourceData2 markingBODatum) throws Exception {
        KeyWordComputer kwc = new KeyWordComputer(5);
        String title = markingBODatum.getTitle();
        long start = System.currentTimeMillis();
        log.info("开始计算关键词");
        Collection<Keyword> result = kwc.computeArticleTfidf(title);
        long end = System.currentTimeMillis();
        log.info("计算关键词结束，{}",(end-start)/1000);
        List keywordList=new ArrayList();
        List resultList=new ArrayList();
        for (Keyword keyword : result) {
//            if (keyword.getScore()>=7){
                keywordList.add(keyword.getName());
//            }
        }
        List<Term> recognition = new NatureRecognition().recognition(keywordList, 0) ;
        for (Term term : recognition) {
            String natureStr = term.getNatureStr();
            if (expectedNature.contains(natureStr)|| "null".equalsIgnoreCase(natureStr)){
                resultList.add(term.getName());
            }
        }
        log.info(resultList.toString());
        return resultList;
    }

    public static IntelligentMarkingBO2 getSourceData2(String url) throws Exception {
        String json = HttpUtil.getJsonFromWeb(url);
        IntelligentMarkingBO2 markingBO = JSON.parseObject(json, IntelligentMarkingBO2.class);
        return markingBO;
    }

    public static List<String> getKeyword3(Newslist newslist) {
        KeyWordComputer kwc = new KeyWordComputer(5);
        String title = newslist.getTitle();
        long start = System.currentTimeMillis();
        log.info("开始计算关键词");
        Collection<Keyword> result = kwc.computeArticleTfidf(title);
        long end = System.currentTimeMillis();
        log.info("计算关键词结束，{}",(end-start)/1000);
        List keywordList=new ArrayList();
        List resultList=new ArrayList();
        for (Keyword keyword : result) {
//            if (keyword.getScore()>=7){
            keywordList.add(keyword.getName());
//            }
        }
        List<Term> recognition = new NatureRecognition().recognition(keywordList, 0) ;
        for (Term term : recognition) {
            String natureStr = term.getNatureStr();
            if (expectedNature.contains(natureStr)|| "null".equalsIgnoreCase(natureStr)){
                resultList.add(term.getName());
            }
        }
        log.info(resultList.toString());
        return resultList;
    }
}
