package com.intelligent.marking.intelligentmarking.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;




public class ltp {
	// webapi接口地址
	private static final String WEBTTS_URL = "https://ltpapi.xfyun.cn/v1/ke";
	// 应用ID
	private static final String APPID = "6c80af0d";
	// 接口密钥
	private static final String API_KEY = "c39175b83ba35c9a82b959b9b01d1561";
	// 文本
	private static final String TEXT = "实现物理页删除";
	

	private static final String TYPE = "dependent";
	
	public static void main(String[] args) throws IOException {
		System.out.println(TEXT.length());
		Map<String, String> header = buildHttpHeader();
		String result = HttpUtil.doPost1(WEBTTS_URL, header, "text=" + URLEncoder.encode(TEXT, "utf-8"));
		System.out.println("itp 接口调用结果：" + result);
	}

	/**
	 * 组装http请求头
	 */
	private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
		String curTime = System.currentTimeMillis() / 1000L + "";
		String param = "{\"type\":\"" + TYPE +"\"}";
		String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
		String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		header.put("X-Param", paramBase64);
		header.put("X-CurTime", curTime);
		header.put("X-CheckSum", checkSum);
		header.put("X-Appid", APPID);
		return header;
	}
}
