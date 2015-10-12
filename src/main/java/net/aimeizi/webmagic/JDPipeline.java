package net.aimeizi.webmagic;

import net.aimeizi.dao.ProductDao;
import net.aimeizi.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/12.
 */
@Component("jdPipeline")
public class JDPipeline implements Pipeline {

    @Autowired
    ProductDao productDao;

    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> items = resultItems.getAll();
        if (resultItems != null && resultItems.getAll().size() > 0) {
            List<String> names = (List<String>) items.get("names");
            List<String> prices = (List<String>) items.get("prices");
            List<String> comments = (List<String>) items.get("comments");
            List<String> links = (List<String>) items.get("links");
            List<String> pics = (List<String>) items.get("pics");
            String category = (String) items.get("category");
            for (int i = 0; i < names.size(); i++) {
                Product p = new Product();
                p.setName(names.get(i));
                p.setPic(pics.get(i));
                p.setPrice(Double.parseDouble(prices.get(i)));
                p.setComment(Long.parseLong(comments.get(i)));
                p.setUrl(links.get(i));
                p.setCategory(category);
                p.setCreate(new Date());
                p.setUpdate(new Date());
                productDao.save(p);
            }
        }
    }

}
