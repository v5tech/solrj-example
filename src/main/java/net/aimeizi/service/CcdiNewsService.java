package net.aimeizi.service;

import net.aimeizi.domain.News;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/8.
 */
public interface CcdiNewsService {
    void indexdb();

    void addNews(News news) throws Exception;

    void addNews(List<News> news) throws Exception;

    void addNewsBySolrInputDocument(News news) throws Exception;

    void addNewsBySolrInputDocument(List<News> news) throws Exception;

    void updateNews(News news) throws Exception;

    void updateNews(List<News> news) throws Exception;

    void deleteNews(String id) throws Exception;

    void deleteNews(List<String> ids) throws Exception;

    void deleteNewsByQuery(String queryString) throws Exception;

    Map<String, Object> query(String queryString, int start, int pageSize) throws Exception;

}
