package com.intelligent.marking.intelligentmarking.service;

import com.intelligent.marking.intelligentmarking.vo.JsonData;

import java.io.UnsupportedEncodingException;

public interface IntelligentMarkingService {
    JsonData intelligentMarking(String URL) throws Exception;

    JsonData intelligentMarking2(String url2) throws Exception;

    JsonData intelligentMarking3(String url);

    JsonData getnewstypelist(String url4) throws UnsupportedEncodingException;

    JsonData gettypenews(String url5, String typeId, String page) throws UnsupportedEncodingException;

    JsonData getnewsdetail(String url6, String newsId) throws UnsupportedEncodingException;

    JsonData getsourceintodatabase(String url4, String url5, String url6);
}
