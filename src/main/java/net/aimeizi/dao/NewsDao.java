package net.aimeizi.dao;

import net.aimeizi.domain.News;

import java.util.List;

/**
 * Created by Administrator on 2015/10/8.
 */
public interface NewsDao {
    void save(News news);

    List<News> findAll();
}
