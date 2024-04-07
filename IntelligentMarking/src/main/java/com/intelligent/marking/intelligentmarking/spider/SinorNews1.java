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
 * 新浪新闻-新闻列表采集
 */
public class SinorNews1 {
    //采集新浪的 娱乐-明星 的列表新闻

    public static void main(String[] args) {
        //每次 采集20页
        for (int i = 1; i < 20; i++) {
            long currentTimeMillis = System.currentTimeMillis();
            String timestamp = String.valueOf(currentTimeMillis);
            String url="https://uifs.sina.cn/api/flow?_ukey=i-interface-wap_api-layout_col&showcid=34978&col=34978&level=1%2C2&show_num=20&page="+i+"&act=more&_="+timestamp;
            String s = get(url);
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONObject("data").getJSONArray("list");
            for (Object o : jsonArray) {
                JSONObject jsonObject1 = JSONObject.parseObject(o.toString());
                //标题
                String topic = jsonObject1.getString("title");
                //作者
                String source = jsonObject1.getString("source");
                //源站id
                String sourceid = jsonObject1.getString("_id");
                //新闻时间
                String datetime = jsonObject1.getString("cdateTime");
                //新闻内容地址
                String sourceurl = jsonObject1.getString("URL");
                //新闻评论数
                Object like_count =  jsonObject1.getString("comment");

                System.out.println("标题："+topic);
                System.out.println("新闻内容地址："+sourceurl);

            }


        }

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
