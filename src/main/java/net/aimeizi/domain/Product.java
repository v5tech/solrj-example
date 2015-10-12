package net.aimeizi.domain;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/10.
 */
public class Product {

    @Field
    private String id;
    @Field
    private String name;
    @Field
    private String pic;
    @Field
    private double price;
    @Field
    private long comment;
    @Field
    private String url;
    @Field
    private Date create;
    @Field
    private Date update;

    @Field
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getComment() {
        return comment;
    }

    public void setComment(long comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
