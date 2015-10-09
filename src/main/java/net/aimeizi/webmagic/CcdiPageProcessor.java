package net.aimeizi.webmagic;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/8.
 * 爬取纪检委网站信息
 */
public class CcdiPageProcessor implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private static final String URL_LIST = "http://www\\.ccdi\\.gov\\.cn/.+/index[_0-9]*\\.html";
    private static final String URL_POST = "http://www\\.ccdi\\.gov\\.cn/.+/\\d+/.*\\.html";

    public void process(Page page) {
        List<String> urllist = new ArrayList<String>();
        String urls;
        // 要闻url
        for (int i = 1; i < 50; i++) {
            urls = "http://www.ccdi.gov.cn/yw/index_" + i + ".html";
            urllist.add(urls);
        }
        // 高层声音url
        for (int i = 1; i < 15; i++) {
            urls = "http://www.ccdi.gov.cn/ldhd/gcsy/index_" + i + ".html";
            urllist.add(urls);
        }
        // 委部领导url
        for (int i = 1; i < 7; i++) {
            urls = "http://www.ccdi.gov.cn/ldhd/wbld/index_" + i + ".html";
            urllist.add(urls);
        }
        // 头条url
        for (int i = 1; i < 50; i++) {
            urls = "http://www.ccdi.gov.cn/xwtt/index_" + i + ".html";
            urllist.add(urls);
        }
        // 法规释义url
        for (int i = 1; i < 9; i++) {
            urls = "http://www.ccdi.gov.cn/djfg/fgsy/index_" + i + ".html";
            urllist.add(urls);
        }
        // 业务顾问url
        for (int i = 1; i < 9; i++) {
            urls = "http://www.ccdi.gov.cn/djfg/ywgw/index_" + i + ".html";
            urllist.add(urls);
        }
        // 党风政风url
        for (int i = 1; i < 42; i++) {
            urls = "http://www.ccdi.gov.cn/gzdt/dfzf/index_" + i + ".html";
            urllist.add(urls);
        }
        // 巡视工作url
        for (int i = 1; i < 25; i++) {
            urls = "http://www.ccdi.gov.cn/gzdt/xsgz/index_" + i + ".html";
            urllist.add(urls);
        }
        // 国际合作url
        for (int i = 1; i < 2; i++) {
            urls = "http://www.ccdi.gov.cn/gzdt/gjhz/index_" + i + ".html";
            urllist.add(urls);
        }
        // 信访举报url
        for (int i = 1; i < 20; i++) {
            urls = "http://www.ccdi.gov.cn/gzdt/xfjb/index_" + i + ".html";
            urllist.add(urls);
        }
        // 纪律审查url
        for (int i = 1; i < 65; i++) {
            urls = "http://www.ccdi.gov.cn/gzdt/jlsc/index_" + i + ".html";
            urllist.add(urls);
        }
        // 宣传工作url
        for (int i = 1; i < 24; i++) {
            urls = "http://www.ccdi.gov.cn/gzdt/xcgz/index_" + i + ".html";
            urllist.add(urls);
        }
        // 组织人事url
        for (int i = 1; i < 15; i++) {
            urls = "http://www.ccdi.gov.cn/gzdt/zzrs/index_" + i + ".html";
            urllist.add(urls);
        }
        // 基层风采url
        for (int i = 1; i < 13; i++) {
            urls = "http://www.ccdi.gov.cn/gzdt/jcfc/index_" + i + ".html";
            urllist.add(urls);
        }

        // 宣传教育-勤廉楷模url
        for (int i = 1; i < 22; i++) {
            urls = "http://www.ccdi.gov.cn/xcjy/qlkm/index_" + i + ".html";
            urllist.add(urls);
        }

        // 宣传教育-以案警示url
        for (int i = 1; i < 11; i++) {
            urls = "http://www.ccdi.gov.cn/xcjy/yajs/index_" + i + ".html";
            urllist.add(urls);
        }

        // 宣传教育-清风文苑url
        for (int i = 1; i < 31; i++) {
            urls = "http://www.ccdi.gov.cn/xcjy/qfwy/index_" + i + ".html";
            urllist.add(urls);
        }

        // 宣传教育-廉史镜鉴url
        for (int i = 1; i < 24; i++) {
            urls = "http://www.ccdi.gov.cn/xcjy/lsjj/index_" + i + ".html";
            urllist.add(urls);
        }

        // 宣传教育-海外观察url
        for (int i = 1; i < 13; i++) {
            urls = "http://www.ccdi.gov.cn/xcjy/hwgc/index_" + i + ".html";
            urllist.add(urls);
        }

        // 判断是否是列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            page.addTargetRequests(page.getHtml().xpath("//div[@class='other_center pub_center']/ul[@class='list_news_dl fixed']").links().regex(URL_POST).all());
            page.addTargetRequests(urllist);
        } else {// 抽取内容详情页
            String title = page.getHtml().xpath("//div[@class='Article_61']/h2[@class='tit']/text()").get();
            String content = page.getHtml().xpath("//div[@class='content']/div[@class='TRS_Editor']/div[@class='TRS_Editor']").get();
            String pubdate = page.getHtml().xpath("//h3[@class='daty']/div[@class='daty_con']/em[@class='e e2']/text()").get();
            if (StringUtils.isNotEmpty(pubdate)) {
                pubdate = pubdate.replace("发布时间：", "");
            }
            String source = page.getHtml().xpath("//h3[@class='daty']/div[@class='daty_con']/em[@class='e e1']/text()").get();
            if (StringUtils.isNotEmpty(source)) {
                source = source.replace("来源：", "");
            }
            page.putField("title", title);
            page.putField("content", replaceHTML(content));
            page.putField("pubdate", pubdate);
            page.putField("source", source);
            page.putField("url", page.getUrl().get());
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcPipeline jdbcPipeline = (JdbcPipeline) applicationContext.getBean("jdbcPipeline");

        Spider.create(new CcdiPageProcessor())
                .addUrl("http://www.ccdi.gov.cn/yw/index.html")// 要闻
                .addUrl("http://www.ccdi.gov.cn/ldhd/gcsy/index.html")// 领导活动-高层声音
                .addUrl("http://www.ccdi.gov.cn/ldhd/wbld/index.html")// 领导活动-委部领导
                .addUrl("http://www.ccdi.gov.cn/xwtt/index.html")     // 头条
                .addUrl("http://www.ccdi.gov.cn/djfg/fgsy/index.html")// 法规释义
                .addUrl("http://www.ccdi.gov.cn/djfg/ywgw/index.html")// 业务顾问
                .addUrl("http://www.ccdi.gov.cn/gzdt/dfzf/index.html")// 工作动态-党风政风
                .addUrl("http://www.ccdi.gov.cn/xsgz/index.html")     // 工作动态-巡视工作
                .addUrl("http://www.ccdi.gov.cn/gzdt/gjhz/index.html")// 工作动态-国际合作
                .addUrl("http://www.ccdi.gov.cn/gzdt/xfjb/index.html")// 工作动态-信访举报
                .addUrl("http://www.ccdi.gov.cn/jlsc/index.html")     // 工作动态-纪律审查
                .addUrl("http://www.ccdi.gov.cn/gzdt/xcgz/index.html")// 工作动态-宣传工作
                .addUrl("http://www.ccdi.gov.cn/gzdt/zzrs/index.html")// 工作动态-组织人事
                .addUrl("http://www.ccdi.gov.cn/gzdt/jcfc/index.html")// 工作动态-基层风采
                .addUrl("http://www.ccdi.gov.cn/xcjy/qlkm/index.html")// 宣传教育-勤廉楷模
                .addUrl("http://www.ccdi.gov.cn/xcjy/yajs/index.html")// 宣传教育-以案警示
                .addUrl("http://www.ccdi.gov.cn/xcjy/qfwy/index.html")// 宣传教育-清风文苑
                .addUrl("http://www.ccdi.gov.cn/xcjy/lsjj/index.html")// 宣传教育-廉史镜鉴
                .addUrl("http://www.ccdi.gov.cn/xcjy/hwgc/index.html")// 宣传教育-海外观察
                .addPipeline(jdbcPipeline)
                .thread(5)
                .run();
    }

    /**
     * html字符过滤
     *
     * @param str
     * @return
     */
    public static String replaceHTML(String str) {
        return str != null ? str.replaceAll("\\<.*?>", "").replaceAll("&nbsp;", "") : "";
    }

}
