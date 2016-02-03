package net.aimeizi.controller;

import net.aimeizi.domain.Product;
import net.aimeizi.service.JDProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 京东商品搜索
 * Created by Administrator on 2015/10/14.
 */
@Controller
public class JDProductController {

    @Autowired
    JDProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String tosearch() {
        return "jd";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jd");
        String queryString = request.getParameter("queryString");
        // 若什么都不输入，则表示搜索全部商品
        queryString = StringUtils.isEmpty(queryString) ? "*" : queryString;
        String sort = request.getParameter("sort");
        String pageNumber = request.getParameter("pageNumber");
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isEmpty(queryString)) {
            return modelAndView;
        }
        try {
            if (StringUtils.isEmpty(pageNumber) || StringUtils.isEmpty(pageSize)) {
                pageNumber = String.valueOf("1");
                pageSize = String.valueOf("60");
            }
            Map<String, Object> maps = productService.query(queryString, sort, Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
            modelAndView.addObject("queryString", queryString);
            modelAndView.addObject("sort", sort);
            modelAndView.addObject("results", (List<Product>) maps.get("results"));
            long count = (Long) maps.get("totals");
            modelAndView.addObject("count", count);
            modelAndView.addObject("qtime", maps.get("qtime"));
            modelAndView.addObject("pageNumber", pageNumber);
            modelAndView.addObject("pageSize", pageSize);
            modelAndView.addObject("totalPages", count % Integer.parseInt(pageSize) == 0 ? count / Integer.parseInt(pageSize) : count / Integer.parseInt(pageSize) + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

}
