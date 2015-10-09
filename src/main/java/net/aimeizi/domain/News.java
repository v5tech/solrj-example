package net.aimeizi.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/8.
 */
public class News {

    @Field("id")
    private String id;
    @Field("title")
    private String title;
    @Field("content")
    private String content;
    @Field("source")
    private String source;
    @Field("pubdate")
    private String pubdate;
    @Field("url")
    private String url;
    @Field("create")
    private Date create;
    @Field("update")
    private Date update;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }
}
