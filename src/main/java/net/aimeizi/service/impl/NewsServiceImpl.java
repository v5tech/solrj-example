package net.aimeizi.service.impl;

import net.aimeizi.dao.NewsDao;
import net.aimeizi.domain.News;
import net.aimeizi.service.NewsService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2015/10/8.
 * http://wiki.apache.org/solr/Solrj
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    SolrServer solrServer;

    public void setSolrServer(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    /**
     * 使用代码的方式创建HttpSolrServer
     *
     * @return
     */
    public SolrServer createSolrServer() {
        String url = "http://127.0.0.1:8080/solr/";
        HttpSolrServer solrServer = new HttpSolrServer(url);
        solrServer.setMaxRetries(1);
        solrServer.setConnectionTimeout(5000);
        solrServer.setSoTimeout(1000);
        solrServer.setDefaultMaxConnectionsPerHost(100);
        solrServer.setMaxTotalConnections(100);
        solrServer.setFollowRedirects(false);
        solrServer.setAllowCompression(true);
        return solrServer;
    }


    /**
     * 使用代码的方式创建CloudSolrServer
     *
     * @return
     */
    public SolrServer createCloudSolrServer() {
        CloudSolrServer cloudSolrServer = new CloudSolrServer("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
        cloudSolrServer.setDefaultCollection("mycollection_shard1_replica1");//设置默认的Collection
        cloudSolrServer.setZkClientTimeout(12000);
        cloudSolrServer.setZkConnectTimeout(12000);
        return cloudSolrServer;
    }

    /**
     * 索引数据库
     */
    @Override
    public void indexdb() {
        List<News> newsList = newsDao.findAll();
        try {
            addNews(newsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 索引bean
     *
     * @param news
     * @throws Exception
     */
    public void addNews(News news) throws Exception {
        solrServer.addBean(news);
        solrServer.optimize();
        solrServer.commit();
    }


    /**
     * 批量索引bean
     *
     * @param news
     * @throws Exception
     */
    public void addNews(List<News> news) throws Exception {
        solrServer.addBeans(news);
        solrServer.optimize();
        solrServer.commit();
    }


    /**
     * 使用SolrInputDocument方式添加索引
     *
     * @param news
     * @throws Exception
     */
    public void addNewsBySolrInputDocument(News news) throws Exception {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id", news.getId());
        solrInputDocument.addField("title", news.getTitle());
        solrInputDocument.addField("content", news.getContent());
        solrInputDocument.addField("source", news.getSource());
        solrInputDocument.addField("pubdate", news.getPubdate());
        solrInputDocument.addField("url", news.getUrl());
        solrInputDocument.addField("create", news.getCreate());
        solrInputDocument.addField("update", news.getUpdate());
        solrServer.add(solrInputDocument);
        solrServer.optimize();
        solrServer.commit();
    }


    /**
     * 使用SolrInputDocument方式批量添加索引
     *
     * @param news
     * @throws Exception
     */
    public void addNewsBySolrInputDocument(List<News> news) throws Exception {
        List<SolrInputDocument> solrInputDocuments = new ArrayList<>();
        for (int i = 0; i < news.size(); i++) {
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            solrInputDocument.addField("id", news.get(i).getId());
            solrInputDocument.addField("title", news.get(i).getTitle());
            solrInputDocument.addField("content", news.get(i).getContent());
            solrInputDocument.addField("source", news.get(i).getSource());
            solrInputDocument.addField("pubdate", news.get(i).getPubdate());
            solrInputDocument.addField("url", news.get(i).getUrl());
            solrInputDocument.addField("create", news.get(i).getCreate());
            solrInputDocument.addField("update", news.get(i).getUpdate());
            solrInputDocuments.add(solrInputDocument);
        }
        solrServer.add(solrInputDocuments);
        solrServer.optimize();
        solrServer.commit();
    }


    /**
     * 根据id修改索引
     *
     * @param news
     * @throws Exception
     */
    public void updateNews(News news) throws Exception {
        solrServer.deleteById(String.valueOf(news.getId()));
        solrServer.addBean(news);
        solrServer.optimize();
        solrServer.commit();
    }

    /**
     * 批量修改索引
     *
     * @param news
     * @throws Exception
     */
    public void updateNews(List<News> news) throws Exception {
        List<String> ids = Collections.emptyList();
        for (int i = 0; i < news.size(); i++) {
            ids.add(news.get(i).getId());
        }
        //批量删除索引
        solrServer.deleteById(ids);
        // 添加索引
        solrServer.addBeans(news);
        solrServer.optimize();
        solrServer.commit();
    }


    /**
     * 根据id删除索引
     *
     * @param id
     * @throws Exception
     */
    public void deleteNews(String id) throws Exception {
        solrServer.deleteById(id);
        solrServer.optimize();
        solrServer.commit();
    }

    /**
     * 根据ids批量删除索引
     *
     * @param ids
     * @throws Exception
     */
    public void deleteNews(List<String> ids) throws Exception {
        solrServer.deleteById(ids);
        solrServer.optimize();
        solrServer.commit();
    }

    /**
     * 根据queryString删除索引
     *
     * @param queryString
     * @throws Exception
     */
    public void deleteNewsByQuery(String queryString) throws Exception {
        solrServer.deleteByQuery(queryString);
        solrServer.optimize();
        solrServer.commit();
    }

    /**
     * 根据queryString分页检索
     *
     * @param queryString
     * @param start
     * @param pageSize
     * @return
     * @throws Exception
     */
    public Map<String, Object> query(String queryString, int start, int pageSize) throws Exception {
        Map<String, Object> maps = new HashMap<String, Object>();
        List<News> newsList = new ArrayList<>();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(queryString); // *:*全部查询
        start = (start - 1) * pageSize;
        solrQuery.setStart(start); //设置起始位置
        solrQuery.setRows(pageSize); // 设置页大小
        solrQuery.setHighlight(true); //启用高亮
        solrQuery.addHighlightField("title"); //设置高亮字段
        solrQuery.addHighlightField("content"); //设置高亮字段
        solrQuery.setHighlightFragsize(200); // 设置高亮内容大小
        solrQuery.setHighlightSimplePre("<em>"); //设置高亮前缀
        solrQuery.setHighlightSimplePost("</em>"); //设置高亮后缀
        solrQuery.addSort("score", SolrQuery.ORDER.desc); //设置排序 按score降序排序
        QueryResponse queryResponse = solrServer.query(solrQuery);
        int qtime = queryResponse.getQTime();//查询花费时间
        SolrDocumentList solrDocumentList = queryResponse.getResults();// 获取查询结果集
        // 获取高亮内容 第一个Map的键是文档的ID，第二个Map的键是高亮显示的字段名
        Map<String, Map<String, List<String>>> highlightingMaps = queryResponse.getHighlighting();
        long totals = solrDocumentList.getNumFound();// 查询到的总记录数
        if (!solrDocumentList.isEmpty()) {
            Iterator<SolrDocument> it = solrDocumentList.iterator();
            while (it.hasNext()) {
                SolrDocument solrDocument = it.next();
                // 获取文档id
                String id = solrDocument.getFieldValue("id").toString();
                // 处理高亮
                Map<String, List<String>> highlightFieldMap = highlightingMaps.get(id);
                if (!highlightFieldMap.isEmpty()) {
                    List<String> highlightTitle = highlightFieldMap.get("title");
                    List<String> highlightContent = highlightFieldMap.get("content");
                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                        String title = highlightTitle.get(0);
                        // 将文档结果集中的title设置为高亮后的title
                        solrDocument.setField("title", title);
                    }
                    if (highlightContent != null && !highlightContent.isEmpty()) {
                        String content = highlightContent.get(0);
                        // 将文档结果集中的content设置为高亮后的content
                        solrDocument.setField("content", content);
                    }
                }
                // 调用solrDocument转java bean
                News news = doc2bean(solrDocument);
                newsList.add(news);
            }
        }
        maps.put("qtime", qtime);
        maps.put("totals", totals);
        maps.put("results", newsList);
        return maps;
    }

    /**
     * solrDocument与java bean转换
     *
     * @param solrDocument
     * @return
     */
    private News doc2bean(SolrDocument solrDocument) {
        DocumentObjectBinder binder = new DocumentObjectBinder();
        News news = binder.getBean(News.class, solrDocument);
        return news;
    }

    /**
     * solrDocument与java bean 集合转换
     *
     * @param solrDocumentList
     * @return
     */
    private List<News> doc2beans(SolrDocumentList solrDocumentList) {
        DocumentObjectBinder binder = new DocumentObjectBinder();
        List<News> newsList = binder.getBeans(News.class, solrDocumentList);
        return newsList;
    }

    /**
     * bean与SolrInputDocument转换
     *
     * @param news
     * @return
     */
    private SolrInputDocument bean2doc(News news) {
        DocumentObjectBinder binder = new DocumentObjectBinder();
        SolrInputDocument solrInputDocument = binder.toSolrInputDocument(news);
        return solrInputDocument;
    }

}