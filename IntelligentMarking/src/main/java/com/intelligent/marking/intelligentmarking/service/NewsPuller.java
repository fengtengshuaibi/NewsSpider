package com.intelligent.marking.intelligentmarking.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public interface NewsPuller {

    void pullNews();

	// url:即新闻首页url
	// useHtmlUnit:是否使用htmlunit
    default Document getHtmlFromUrl(String url, boolean useHtmlUnit) throws Exception {
        if (!useHtmlUnit) {
            return Jsoup.connect(url)
                    // 更新为现代浏览器的User-Agent
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(10 * 1000) // 设置超时时间为10秒
                    .get();
        } else {
            WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setTimeout(10000);
            HtmlPage htmlPage = null;
            try {
                htmlPage = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(10000);
                String htmlString = htmlPage.asXml();
                return Jsoup.parse(htmlString);
            } finally {
                webClient.close();
            }
        }
    }

}
