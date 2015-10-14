<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <title>solrj example</title>
    <meta name="keywords" content="free icons, icon search, iconfinder, market place"/>
    <meta name="description" content="elasticsearch jest"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link href="/css/pager.css" rel="stylesheet" type="text/css" />
    <script src="/js/jquery.pager.js" type="text/javascript" charset="utf-8"></script>
    <style>
        .bs-docs-footer {
            padding-top: 40px;
            padding-bottom: 40px;
            color: #767676;
            text-align: center;
            border-top: 1px solid #e5e5e5;
        }
        .search{
            text-align: center;
            border-bottom: 1px solid #e5e5e5;
            padding-bottom: 50px;
        }
        em{
            font-style: normal;
            color: red;
        }
        p{
            margin-top: 15px;
            text-align: right;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <div style="width: 276px;">
                    <img src="/images/solr.png"/>
                </div>
            </div>
        </div>
    </div>
    <div class="search">
        <div class="container">
            <section>
                <div class="row-fluid">
                    <div>
                        <form id="searchform" name="searchform" action="/news/search" class="form-inline" method="post">
                            <div class="form-group">
                                <input type="text" class="form-control" name="queryString" value="${queryString}" style="width: 396px;">
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="pageSize" id="pageSize">
                                    <option value="10" <c:if test="${pageSize==10}">selected</c:if>>10</option>
                                    <option value="20" <c:if test="${pageSize==20}">selected</c:if>>20</option>
                                    <option value="50" <c:if test="${pageSize==50}">selected</c:if>>50</option>
                                    <option value="100" <c:if test="${pageSize==100}">selected</c:if>>100</option>
                                </select>
                            </div>
                            <input type="hidden" name="pageNumber" value="${pageNumber}" id="pageNumber">
                            <button class="btn btn-primary" type="submit">搜索</button>
                        </form>
                    </div>
                </div>
            </section>
            <c:if test="${!empty count}">
                <h2><span class="label label-success">搜索关键字<em>${queryString}</em>共检索到<em>${count}</em>条记录，共<em>${totalPages}</em>页，每页显示<em>${pageSize}</em>条。共耗时<em>${qtime}</em>毫秒。</span></h2>
            </c:if>
        </div>
    </div>
</div>
<div class="content">
    <div class="container">
        <c:if test="${empty results}">
            <h2><span class="label label-success">没有要查询的内容</span></h2>
        </c:if>
        <c:forEach items="${results}" var="news">
            <div>
                <h3><a href="${news.url}" target="_blank">${news.title}</a></h3>
                <span>${news.content}</span>
                <p>${news.source}  ${news.pubdate}</p>
            </div>
        </c:forEach>
        <div class="pager" id="pageNav">
        </div>
    </div>
</div>
<footer class="bs-docs-footer">
    <div class="container">
        <h1>solrj example</h1>
    </div>
</footer>
<!--返回顶部开始-->
<div id="full" style="width:0px; height:0px; position:fixed; right:70px; bottom:150px; z-index:100; text-align:center; background-color:transparent; cursor:pointer;">
    <a href="#" onclick="goTop();return false;"><img src="/images/top.png" width="70px;" height="70px;" border=0 alt="回到顶部"></a>
</div>
<script src="/js/top.js" type="text/javascript"></script>
<!--返回顶部结束-->
<script type="text/javascript">
<c:if test="${totalPages!=0}">
$(document).ready(function() {
    $("#pageNav").pager(
            {
                pagenumber:${pageNumber},
                pagecount:${totalPages},
                buttonClickCallback:function(pageCurrent) {
                    document.getElementById('pageNumber').value = pageCurrent;
                    document.getElementById('searchform').submit();
                }
            });
});
</c:if>
$(function(){
    $('select').change(function(){
        document.getElementById('searchform').submit();
    });
});
</script>
</body>
</html>
