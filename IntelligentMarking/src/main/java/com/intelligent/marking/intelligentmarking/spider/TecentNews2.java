package com.intelligent.marking.intelligentmarking.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 腾讯新闻-新闻内容采集
 */
public class TecentNews2 {
    //采集腾讯内容

    public static void main(String[] args) {
        String s = get("https://xw.qq.com/cmsid/20210630A09TUE00");

        String[] split = s.split("json_content\":");
        if (split.length<=1){
            return;
        }
        String[] split1 = split[1].split("]");
        JSONArray array = JSONObject.parseArray(split1[0] + "]");
        StringBuilder contentBuffer = new StringBuilder(); //得到主体内容
        for (Object o : array) {
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(o));
            String value = jsonObject.getString("value");
            Integer type = jsonObject.getInteger("type");
            if (type==1){
                contentBuffer.append("<p>" + value + "</p>");
                System.out.println("文本："+value);
            }else if(type==2){
                System.out.println("图片："+value);
                contentBuffer.append("<img src=\"" + value + "\">");
            }

        }

        //整理后的内容
        System.out.println(contentBuffer);

    }



    //get请求的工具方法
    private static String get(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection(); // 打开和URL之间的连接
            // 设置通用的请求属性
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("content-type", "text/html; charset=utf-8");
            connection.setRequestProperty("accept", "*/*");
            //connection.setRequestProperty("Cookie", "tt_webid=20 B, session, HttpOnly www.toutiao.com/");
            connection.setRequestProperty("Cookie", "utm_source=vivoliulanqi; webpSupport=%7B%22lossy%22%3Atrue%2C%22animation%22%3Atrue%2C%22alpha%22%3Atrue%7D; tt_webid=6977609332415530509; ttcid=1b2305f8baa44c8f929093024ae40dbf62; csrftoken=f8363c5a04097f7fd5d2ee36cf5bbd40; s_v_web_id=verify_kqbxnll7_QA9Z6n7G_LFul_4hTP_9jZf_zgZYUK3ySQOT; _ga=GA1.2.2038365076.1624601292; _gid=GA1.2.2124270427.1624601292; MONITOR_WEB_ID=518b84ad-98d5-4cb4-9e4e-4e3c3ec3ffe2; tt_webid=6977609332415530509; __ac_nonce=060d5aa4200b3672b2734; __ac_signature=_02B4Z6wo00f010CALQgAAIDA8HHBwRR4FntApCmAALEAeRZEDep7WW-RzEt50sUvtrkCpbRJMhboWeZNJ2s66iti2ZD-7sSiClTqpKs6b7ppQUp1vD8JHANxzSZ1srY4FF1y1iQitM1bQvYIf3; ttwid=1%7CTBE591UU7daDw3rsqkr6wXM1DqlOA3iyjUnPK-W6ThQ%7C1624615515%7Ccb0f077482096b50d19757a23f71240547d6b0c767bf9ab90fa583d022f47745; tt_scid=af-M9Xg-rmZAnPsCXhZu.2.DfKZe95AyPKJFzU0cL1KarDLfV3JYeIf.G28mIwhI57a0");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.106 Safari/537.36");
            connection.connect(); // 建立实际的连接
            Map<String, List<String>> map = connection.getHeaderFields(); // 获取所有响应头字段

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
