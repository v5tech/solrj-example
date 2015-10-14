<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta charset="utf-8"/>
    <title>Solr之JD商品搜索</title>
    <link rel="stylesheet" type="text/css" href="/css/360buyimg.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/list.css" media="all"/>
    <link href="/css/pager.css" rel="stylesheet" type="text/css"/>
    <meta name="keywords" content="清华大学出版社  图书  报价、促销、新闻、评论、导购、图片"/>
    <meta name="description"
          content="京东JD.COM是国内最专业的 清华大学出版社  图书  报价、促销、新闻、评论、导购、图片 网上购物商城，本频道提供清华大学出版社  图书  报价、促销、新闻、评论、导购、图片的最新报价、促销、评论、导购、图片等相关信息"/>
    <style type="text/css">
        .p-name em em {
            color: red;
        }

        .goods-list-v1 .gl-item .p-name em, .goods-list-v1 .gl-item .p-name i {
            display: inline;
        }
    </style>
</head>
<body>
<div class="w" style="margin-top: 50px;">
    <img src="/images/solr.png" style="float: right;"/>

    <div id="logo-2014">
        <a href="/" class="logo">京东</a>
    </div>
    <div id="search-2014">
        <div class="form">
            <form action="/search" method="post" name="searchform" id="searchform">
                <input type="hidden" name="pageNumber" value="${pageNumber}" id="pageNumber"/>
                <input type="hidden" name="pageSize" value="${pageSize}" id="pageSize"/>
                <input type="hidden" name="sort" value="${sort}" id="sort"/>
                <input type="text" name="queryString" value="${queryString}"
                       onkeydown="javascript:if(event.keyCode==13) search('comment');" id="key" class="text"/>
                <button onclick="search('comment');return false;" class="button cw-icon"><i></i>搜索</button>
            </form>
        </div>
    </div>
    <c:if test="${not empty queryString}">
        <div id="hotwords-2014">
            查询关键字<em>${queryString}</em>，共搜索到<em>${count}</em>条记录，每页显示<em>${pageSize}</em>条，共<em>${totalPages}</em>页，总耗时<em>${qtime}</em>毫秒
        </div>
    </c:if>
    <span class="clr"></span>
</div>
<div class="w" id="J_searchWrap">
    <div class="container" id="J_container">
        <div id="J_main" class="g-main2">
            <c:if test="${not empty results}">
                <div class="m-list" style="width: 1250px;">
                    <div class="ml-wrap">
                        <div class="filter" id="J_filter">
                            <div class="f-line top">
                                <div class="f-sort">
                                    <a href="javascript:search('comment')"
                                       class="<c:if test="${sort=='comment'}">curr</c:if>">评论数<i></i></a>
                                    <a href="javascript:search('price')"
                                       class="<c:if test="${sort=='price'}">curr</c:if>">价格<i></i></a>
                                </div>
                                <div class="f-pager" id="J_topPage">
                                    <span class="fp-text"><b>${pageNumber}</b><em>/</em><i>${totalPages}</i></span>
                                </div>
                                <div id="J_viewType" class="f-type" data-ref="0">
                                    <a class="ft-item list" href="javascript:changeView(1);" data-value="1"><i
                                            class="i-list"></i>列表</a>
                                    <a class="ft-item grid selected" href="javascript:changeView(2);" data-value="2"><i
                                            class="i-grid"></i>网格</a>
                                </div>
                                <span class="clr"></span>
                            </div>
                        </div>
                        <div class="clr"></div>
                        <div id="plist" class="goods-list-v1 gl-type-4 J-goods-list">
                            <ul class="gl-warp clearfix" style="width: 1250px;">
                                <c:forEach items="${results}" var="product">
                                    <li class="gl-item">
                                        <div class="gl-i-wrap j-sku-item">
                                            <div class="p-img">
                                                <a target="_blank" href="${product.url}">
                                                    <img class="err-product" src="${product.pic}"/>
                                                </a>
                                            </div>
                                            <div class="p-price">
                                                <strong class="J_price"
                                                        data-istsyx="n"><em>&yen;</em><i><fmt:formatNumber value="${product.price}" type="currency"/></i></strong>

                                                <div class="p-icons">
                                                    <i class="goods-icons-s1" title="该商品支持货到付款">货到付款</i>
                                                </div>
                                            </div>
                                            <div class="p-name">
                                                <a target="_blank" title="${product.name}" href="${product.url}">
                                                    <em>${product.name}</em> <i class="promo-words"></i>
                                                </a>
                                            </div>
                                            <div class="p-commit">
                                                <strong> 已有<a target="_blank" href="null">${product.comment}</a>
                                                    人评价</strong>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                            <div class="clr"></div>
                            <div class="pager" id="pageNav"/>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
<!--返回顶部开始-->
<div id="full"
     style="width:0px; height:0px; position:fixed; right:70px; bottom:150px; z-index:100; text-align:center; background-color:transparent; cursor:pointer;">
    <a href="#" onclick="goTop();return false;">
        <img src="/images/top.png" width="70px;" height="70px;" border=0 alt="回到顶部">
    </a>
</div>
<script src="/js/top.js" type="text/javascript"></script>
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.js"></script>
<script src="/js/jquery.pager.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    function search(sort) {
        document.getElementById('sort').value = sort;
        document.getElementById('searchform').submit();
    }
    function changeView(num) {
        if (num == 1) {
            $('#plist').attr('class', 'goods-list-v1 gl-type-5 J-goods-list');
            $('#J_viewType').find('a[data-value="1"]').addClass('selected');
            $('#J_viewType').find('a[data-value="2"]').removeClass('selected');
        } else {
            $('#plist').attr('class', 'goods-list-v1 gl-type-4 J-goods-list');
            $('#J_viewType').find('a[data-value="2"]').addClass('selected');
            $('#J_viewType').find('a[data-value="1"]').removeClass('selected');
        }
    }
    <c:if test="${not empty results}">
    $(document).ready(function () {
        $("#pageNav").pager(
            {
                pagenumber:${pageNumber},
                pagecount:${totalPages},
                buttonClickCallback: function (pageCurrent) {
                    document.getElementById('pageNumber').value = pageCurrent;
                    document.getElementById('searchform').submit();
                }
            });
    });
    </c:if>
</script>
</body>
</html>