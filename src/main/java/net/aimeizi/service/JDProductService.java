package net.aimeizi.service;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/14.
 */
public interface JDProductService {
    Map<String, Object> query(String queryString, String sort, int start, int pageSize) throws Exception;
}
