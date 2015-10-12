package net.aimeizi.webmagic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/12.
 * JD商品抓取
 */
public class JDProductProcessor implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private static final String URL_LIST = "http://list\\.jd\\.com/.*\\.html";

    @Override
    public void process(Page page) {
        // 所有商品分类入口
        if (page.getUrl().regex("http://www\\.jd\\.com/allSort\\.aspx").match()) {
            List<String> links = page.getHtml().links().regex(URL_LIST).all();// 获取商品分类链接匹配URL_LIST的链接信息
            page.addTargetRequests(links);
        } else { //处理商品列表页
            List<String> plinks = page.getHtml().xpath("//span[@class=\"p-num\"]").links().all();
            page.addTargetRequests(plinks);
            List<String> names = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-name']/a/em/text()").all();
            List<String> prices = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-price']/strong[@class='J_price']/i/text()").all();
            List<String> comments = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-commit']/strong/a/text()").all();
            List<String> links = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-img']/a/@href").all();
            List<String> top3Pic = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-img']/a/img/@src").all(); //获取页面初始化的前三张图片地址
            List<String> lazyPic = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-img']/a/img/@data-lazy-img").all(); // 获取懒加载的图片地址
            List<String> pics = new ArrayList<>();
            pics.addAll(top3Pic.subList(0, 3)); //获取前三张图片
            pics.addAll(lazyPic.subList(3, lazyPic.size())); //获取前三张图片
            String category = page.getHtml().xpath("//div[@id='J_selector']/div[@class='s-title']/h3/b/text()").get();
            if ("".equals(category)) {
                category = page.getHtml().xpath("//div[@id='J_selector']/div[@class='s-title']/h3/em/text()").get();
            }
            page.putField("names", names);
            page.putField("prices", prices);
            page.putField("comments", comments);
            page.putField("links", links);
            page.putField("pics", pics);
            page.putField("category", category);
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws Exception {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JDPipeline jdPipeline = (JDPipeline) applicationContext.getBean("jdPipeline");

        String chromeDriverPath = JDProductProcessor.class.getClassLoader().getResource("chromedriver.exe").getFile();

        Spider.create(new JDProductProcessor())
                .addUrl("http://www.jd.com/allSort.aspx")// JD全部分类
                .addPipeline(jdPipeline)
                .setDownloader(new SeleniumDownloader(chromeDriverPath))
                .thread(5)
                .run();
    }

    /**
     * 构建商品列表url
     *
     * @param str
     * @return
     */
    private static String buildUrl(String str) {
        String link = "http://list.jd.com/list.html?cat=";
        str = str.replace("http://list.jd.com/", "").replace(".html", "");
        if (str.contains("-")) {
            str = str.replace("-", ",");
        }
        link += str;
        return link;
    }

}
