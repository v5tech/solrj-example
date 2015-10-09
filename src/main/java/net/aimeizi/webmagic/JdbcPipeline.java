package net.aimeizi.webmagic;

import net.aimeizi.dao.NewsDao;
import net.aimeizi.domain.News;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/8.
 */
@Component("jdbcPipeline")
public class JdbcPipeline implements Pipeline {
    @Autowired
    NewsDao newsDao;

    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> items = resultItems.getAll();
        if (resultItems != null && resultItems.getAll().size() > 0) {
            News news = new News();
            news.setTitle((String) items.get("title"));
            news.setContent(replaceHTML((String) items.get("content")));
            news.setUrl((String) items.get("url"));
            String source = (String) items.get("source");
            if (StringUtils.isNotEmpty(source)) {
                source = source.replace("来源：", "");
            }
            news.setSource(source);
            String pubdate = (String) items.get("pubdate");
            if (StringUtils.isNotEmpty(pubdate)) {
                pubdate = pubdate.replace("发布时间：", "");
            }
            news.setPubdate(pubdate);
            news.setCreate(new Date());
            news.setUpdate(new Date());
            newsDao.save(news);
        }
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
