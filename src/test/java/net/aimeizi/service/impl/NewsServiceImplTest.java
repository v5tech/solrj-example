package net.aimeizi.service.impl;

import net.aimeizi.domain.News;
import net.aimeizi.service.CcdiNewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/9.
 * solr查询语法参考
 * https://wiki.apache.org/solr/SolrQuerySyntax
 * http://www.solrtutorial.com/solr-query-syntax.html
 * http://www.lucenetutorial.com/lucene-query-syntax.html
 * https://lucene.apache.org/core/4_10_4/queryparser/org/apache/lucene/queryparser/classic/package-summary.html
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class NewsServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    CcdiNewsService newsService;

    /**
     * 测试创建索引
     */
    @Test
    public void indexdb() {
        long start = Calendar.getInstance().getTimeInMillis();
        newsService.indexdb();
        long end = Calendar.getInstance().getTimeInMillis();
        long times = (end - start) / 1000;
        System.out.println("索引创建完毕.本次创建索引共耗时:" + times + "秒");
    }


    /**
     * 测试查询
     */
    @Test
    public void query() throws Exception {
        String queryString = "王岐山"; // term查询
//        queryString = "title:王岐山"; // field查询
//        queryString = "title:(王岐山 AND 四川)";
//        queryString = "title:\"王岐山\" AND title:\"四川\"";
//        queryString = "title:(王?山 AND 四川)";
//        queryString = "title:(王岐山 OR 四川)";
//        queryString = "title:(王?山 OR 四川)";
//        queryString = "title:王岐山 -title:四川";
        Map<String, Object> maps = newsService.query(queryString, 1, 50);
        if (!maps.isEmpty()) {
            System.out.println("本次查询共耗时:" + maps.get("qtime") + "秒");
            System.out.println("查询关键字"+queryString+"共查询到:" + maps.get("totals") + "条记录");
            List<News> newsList = (List<News>) maps.get("results");
            for (int i = 0; i < newsList.size(); i++) {
                System.out.println("id:" + newsList.get(i).getId());
                System.out.println("title:" + newsList.get(i).getTitle());
                System.out.println("content:" + newsList.get(i).getContent());
                System.out.println("source:" + newsList.get(i).getSource());
                System.out.println("url:" + newsList.get(i).getUrl());
                System.out.println("pubdate:" + newsList.get(i).getPubdate());
                System.out.println("--------------------------------------------------------------------------------------");
            }
        }
    }


    /**
     * 清空索引
     *
     * @throws Exception
     */
    @Test
    public void destroy() throws Exception {
        newsService.deleteNewsByQuery("*:*");
    }
}
