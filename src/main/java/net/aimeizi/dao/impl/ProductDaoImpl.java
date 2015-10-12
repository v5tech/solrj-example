package net.aimeizi.dao.impl;

import net.aimeizi.dao.ProductDao;
import net.aimeizi.domain.Product;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2015/10/8.
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from news", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setPic(rs.getString("pic"));
                product.setPrice(rs.getDouble("price"));
                product.setComment(rs.getLong("comment"));
                product.setUrl(rs.getString("url"));
                product.setCategory(rs.getString("category"));
                product.setCreate(rs.getDate("create"));
                product.setUpdate(rs.getDate("update"));
                return product;
            }
        });
    }

    public void save(final Product product) {

        String sql = "INSERT INTO product (`name`,pic,price,comment,url,category,`create`,`update`) VALUES (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                // 获取src/main/webapp/images 绝对路径
                String filepath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "images";
                String pic = product.getPic();
                if (StringUtils.isNotEmpty(pic)) {
                    String filename = FilenameUtils.getName(pic);
                    try {
                        File file = new File(filepath, filename);
                        FileUtils.copyURLToFile(new URL("http:" + pic), file); //将文件写入到磁盘中
                        pic = "images/" + filename;
                    } catch (Exception e) {

                    }
                }
                ps.setString(1, product.getName());
                ps.setString(2, pic);
                ps.setDouble(3, product.getPrice());
                ps.setLong(4, product.getComment());
                ps.setString(5, product.getUrl());
                ps.setString(6, product.getCategory());
                ps.setDate(7, new Date(product.getCreate().getTime()));
                ps.setDate(8, new Date(product.getUpdate().getTime()));
            }
        });
    }
}
