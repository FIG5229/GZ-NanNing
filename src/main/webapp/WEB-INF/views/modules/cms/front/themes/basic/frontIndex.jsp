<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="cms_default_${site.theme}"/>
    <meta name="description" content="${site.description}"/>
    <meta name="keywords" content="${site.keywords}"/>
    <style>
        .container {
            width: 1200px;
        }
    </style>
</head>
<body>
<c:if test="${fnc:getArticleList(site.id, 'ab072f70afdc43cea97ef0d187f48f92', 1, '').size()>0}">
    <c:forEach items="${fnc:getArticleList(site.id, 'ab072f70afdc43cea97ef0d187f48f92', 1, '')}" var="article"
                       varStatus="status">
        <div id="img1"
                style="z-index: 98; border: 1px solid rgb(204, 204, 204); width: ${article.description}px; background-color: red; height: 65px; position: fixed; top: 29px; left: 1230px; font-size: 13px;">
                <div style="font-size: 28px;text-align: center;line-height: 65px">
                <a href="${article.url}" target="_blank" style="text-decoration:none">
                    <font color="yellow"><strong
                            style="font-family: FZXiaoBiaoSong-B05S,Microsoft YaHei,sans-serif;">${fns:abbr(article.title,40)}</strong>
                        <font>
                </a><br>
                </div>
        </div>
    </c:forEach>
</c:if>

<div class="main">
    <%--主页浮动导航	--%>
    <div class="float-block">
        <a target="_blank" href="${ctx}/list-c55f7521049a4a1bbaf9031c929fc142${urlSuffix}">
            <div class="float-item float-item1">&nbsp;廉政教育</div>
        </a>
        <%--21.09.06 取消民警必读、他山之石（转移到公安局主页网）--%>
        <%--<a target="_blank" href="${ctx}/list-e8b9380de81048519e3c96c911e0118b${urlSuffix}">
            <div class="float-item float-item2">&nbsp;民警必读</div>
        </a>--%>
        <a target="_blank" href="${ctx}/list-4b0037eaba45452f935731d0330b832b${urlSuffix}">
            <div class="float-item float-item3">&nbsp;网上书屋</div>
        </a>
        <%--<a target="_blank" href="${ctx}/list-c0a6b0c5c94b406abcb86a76d11a5470${urlSuffix}">
            <div class="float-item float-item4">&nbsp;他山之石</div>
        </a>--%>
        <a target="_blank" href="${ctx}/list-b7c5372754f04fddbe2d5935de0bf431${urlSuffix}">
            <div class="float-item float-item5">&nbsp;表扬点赞</div>
        </a>
        <a target="_blank" href="http://10.3.240.160/">
            <div class="float-item float-item6">&nbsp;OA办公</div>
        </a>
        <a target="_blank" href="http://10.3.240.6:3000/">
            <div class="float-item float-item7">&nbsp;&nbsp;电子邮箱</div>
        </a>
    </div>
    <div class="block">
        <div class="banner">
            <div class="swiper-container swiper-container1">
                <div class="swiper-wrapper swiper-wrapper1">
                    <c:forEach items="${fnc:getArticleList(site.id, '04323757d2fd42a29d6347ad04608408', 8, '')}"
                               var="article">
                        <div class="swiper-slide swiper-slide1">
                            <a href="${article.url}" target="_blank">
                                <img src="${article.appendfile}">
                                <div class="swiper-item-title">${article.title}</div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
                <!-- 如果需要分页器 -->
                <div class="swiper-pagination swiper-pagination1"></div>
            </div>
        </div>
        <%--<div class="zgdt-wrap">
            <h3 class="block-title-bg block-title-icon-zhenggongyaowen"><a
                    href="${ctx}/list-5d7f5d61b84243ddb53ce5c359e6c2f2${urlSuffix}" style="color: rgba(255,255,255,1);">政工要闻</a>
            </h3>
            <ul class="list-commom">
                <c:forEach items="${fnc:getArticleList(site.id, '5d7f5d61b84243ddb53ce5c359e6c2f2', 10, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)} <span><fmt:formatDate
                                value="${article.updateDate}" pattern="yyyy/MM/dd"/></span></li>
                    </a>
                </c:forEach>
            </ul>
        </div>--%>
        <div class="zgdt-wrap" style="height: 446px">
            <h3 class="block-title-bg block-title-icon-zhenggongdongtai"><a
                    href="${ctx}/list-127650291835482991a8b19ddd29029c${urlSuffix}" style="color: rgba(255,255,255,1);">政工信息</a>
            </h3>
            <ul class="list-commom" style="height: 428px;">
                <c:forEach items="${fnc:getArticleList(site.id, '127650291835482991a8b19ddd29029c', 20, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)} <span><fmt:formatDate
                                value="${article.updateDate}" pattern="yyyy/MM/dd"/></span></li>
                    </a>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="block">
        <div class="tz-title block-title-big-bg block-title-icon-tongzhigonggao">
            <h3>通知公告</h3>
            <marquee behavior="scroll" direction="left" onMouseOut="this.start();" onMouseOver="this.stop();">
                <ul class="line1Hidden">
                    <c:forEach items="${fnc:getArticleList(site.id, '3cc894f99fda44669f6a07370c00500b', 5, '')}"
                               var="article">
                        <a href="${article.url}" target="_blank">
                            <li>${fns:abbr(article.title,46)}</li>
                        </a>
                    </c:forEach>
                </ul>
            </marquee>
            <a href="${ctx}/list-3cc894f99fda44669f6a07370c00500b${urlSuffix}">
                <div class="tz-title-after"></div>
            </a>
        </div>
    </div>
    <div class="block">
        <div class="nav-child">
            <c:forEach items="${fnc:getArticleList(site.id, '20c8c197133f4d9bac39351c207b7559', 8, '')}" var="article"
                       varStatus="status">
                <c:if test="${status.index ==0}">
                    <p><img src="${article.appendfile}"></p><span>${fns:abbr(article.title,30)}</span>
                </c:if>
            </c:forEach>
            <div class="nav-child-float">
                <c:forEach items="${fnc:getArticleList(site.id, '0c1f52c6aa9c4602a32722456a653271', 8, '')}"
                           var="article" varStatus="status">
                    <a target="_blank" href="${article.url}">${fns:abbr(article.title,30)}</a>
                </c:forEach>
            </div>
        </div>
        <c:forEach items="${fnc:getArticleList(site.id, '20c8c197133f4d9bac39351c207b7559', 8, '')}" var="article"
                   varStatus="status">
            <c:choose>
                <c:when test="${(status.index+1)%4==0 && status.index !=0}">
                    <a href="${article.url}" target="_blank">
                        <div class="nav-child mr0"><p><img src="${article.appendfile}"></p>
                            <span>${fns:abbr(article.title,30)}</span></div>
                    </a>
                </c:when>
                <c:when  test="${status.index !=0}">
                    <a href="${article.url}" target="_blank">
                        <div class="nav-child"><p><img src="${article.appendfile}"></p>
                            <span>${fns:abbr(article.title,30)}</span></div>
                    </a>
                </c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
    <div class="block">
        <div class="tz-list">
            <h3 class="block-title-bg block-title-icon-dangjianxinxi"
                onclick="window.location.href='${ctx}/list-ca4e51b1653e4c18b2724d446297db41${urlSuffix}'">党建信息</h3>
            <ul class="list-commom">
                <c:forEach items="${fnc:getArticleList(site.id, 'ca4e51b1653e4c18b2724d446297db41', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
            </ul>
        </div>
        <div class="tz-list">
            <h3 class="block-title-bg block-title-icon-dangjianxinxi"
                onclick="window.location.href='${ctx}/list-f72a5b908848442cb5606c32c65d4632${urlSuffix}'">通知通报</h3>
            <ul class="list-commom">
                <c:forEach items="${fnc:getArticleList(site.id, 'f72a5b908848442cb5606c32c65d4632', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
            </ul>
            <%--21年9月6日 取消调研交流、政工简报（均转移到公安局主页网）--%>
           <%-- <div class="tz-tab">
                <span class="tz-tab-title active">通知通报</span>
                <span class="tz-tab-title">调研交流</span>
                <span class="tz-tab-title">政工简报</span>
            </div>--%>
            <%--<ul class="list-commom list-commom-tab list-commom-tab-active">
                &lt;%&ndash;
                <c:forEach items="${indexNoticeList}" var="notice">
                    <a href="${ctx}/noticeView?noticeId=${notice.id}" target="_blank"><li class="line1Hidden">${fns:abbr(notice.title,46)}</li></a>
                </c:forEach>
                <a class="list-more-style list-more-style-active" href="${ctx}/list-notice"></a>
                &ndash;%&gt;
                <c:forEach items="${fnc:getArticleList(site.id, 'f72a5b908848442cb5606c32c65d4632', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
                <a class="list-more-style" href="${ctx}/list-f72a5b908848442cb5606c32c65d4632${urlSuffix}"></a>
            </ul>
            <ul class="list-commom list-commom-tab">
                <c:forEach items="${fnc:getArticleList(site.id, '3cac8ac633dd43e2845f6c45b0c9c4ea', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
                <a class="list-more-style" href="${ctx}/list-3cac8ac633dd43e2845f6c45b0c9c4ea${urlSuffix}"></a>
            </ul>
            <ul class="list-commom list-commom-tab">
                <c:forEach items="${fnc:getArticleList(site.id, 'd82d42b9d0ab4c34b5405dbb8e398020', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
                <a class="list-more-style" href="${ctx}/list-d82d42b9d0ab4c34b5405dbb8e398020${urlSuffix}"></a>
            </ul>--%>
        </div>
        <div class="tz-list tz-list-echarts tz-list-echarts1 mr0">
            <h3 class="block-title-bg block-title-icon-kaoping"
                onclick="window.location.href='/politics/a?firstMenu=统计分析&target=绩效考核情况'">考评情况</h3>
            <div id="echarts1" class="list-commom" style="padding: 5px;"></div>
        </div>
    </div>
    <div class="block">
        <div class="tz-list">
            <h3 class="block-title-bg block-title-icon-wuxing"
                onclick="window.location.href='${ctx}/list-220c0482c9a5442b9c6317ffdefc7381${urlSuffix}'">青年阵地</h3>
            <ul class="list-commom">
                <c:forEach items="${fnc:getArticleList(site.id, '220c0482c9a5442b9c6317ffdefc7381', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
            </ul>
        </div>
        <div class="tz-list">
            <div class="tz-tab">
                <span class="tz-tab-title active">纪委工作</span>
                <span class="tz-tab-title">警务督察</span>
                <span class="tz-tab-title">保密工作</span>
            </div>
            <ul class="list-commom list-commom-tab list-commom-tab-active">
                <c:forEach items="${fnc:getArticleList(site.id, '44c5b3f7d9e94c9897af22ef67d02292', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
                <a class="list-more-style list-more-style-active"
                   href="${ctx}/list-44c5b3f7d9e94c9897af22ef67d02292${urlSuffix}"></a>
            </ul>
            <ul class="list-commom list-commom-tab">
                <c:forEach items="${fnc:getArticleList(site.id, 'f0db6af079714a65a8ec11e3b08329c6', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
                <a class="list-more-style list-more-style-active"
                   href="${ctx}/list-f0db6af079714a65a8ec11e3b08329c6${urlSuffix}"></a>
            </ul>
            <ul class="list-commom list-commom-tab">
                <c:forEach items="${fnc:getArticleList(site.id, '96c02b11a0814f258210ee2d7f128d5f', 3, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
                <c:forEach items="${fnc:getArticleList(site.id, 'c749a29cdfef44279339b3bdee2a5bff', 2, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
                <a class="list-more-style list-more-style-active"
                   href="${ctx}/list-96c02b11a0814f258210ee2d7f128d5f${urlSuffix}"></a>
            </ul>
        </div>

        <%--		<div class="tz-list">--%>
        <%--			<h3 onclick="window.location.href='${ctx}/list-44c5b3f7d9e94c9897af22ef67d02292${urlSuffix}'">纪检监察</h3>--%>
        <%--			<ul class="list-commom">--%>
        <%--                <c:forEach items="${fnc:getArticleList(site.id, '44c5b3f7d9e94c9897af22ef67d02292', 6, '')}" var="article">--%>
        <%--                    <a href="${article.url}" target="_blank"><li class="line1Hidden">${fns:abbr(article.title,46)}</li></a>--%>
        <%--                </c:forEach>--%>
        <%--			</ul>--%>
        <%--		</div>--%>
        <%--		<div class="tz-list tz-list-echarts tz-list-echarts2 mr0">--%>
        <%--			<h3 onclick="window.location.href='/politics/a?firstMenu=统计分析&target=绩效考核情况'">统计情况</h3>--%>
        <%--			<div id="echarts2" class="list-commom" style="padding: 5px;"></div>--%>
        <%--		</div>--%>
        <div class="tz-list mr0">
            <h3 class="block-title-bg block-title-icon-gonghui"
                onclick="window.location.href='${ctx}/list-335c69c108d243ca9fded21331dcad52${urlSuffix}'">工会保障</h3>
            <ul class="list-commom">
                <c:forEach items="${fnc:getArticleList(site.id, '335c69c108d243ca9fded21331dcad52', 5, '')}"
                           var="article">
                    <a href="${article.url}" target="_blank">
                        <li class="line1Hidden">${fns:abbr(article.title,46)}</li>
                    </a>
                </c:forEach>
            </ul>
        </div>
    </div>
    <%--警营文化	--%>
    <div class="block">
        <div class="block-title-new"><span>警营文化</span></div>
        <div class="jy-list">
            <div class="jy-tab">
                <div class="jy-tab-wrap active"><span class="jy-tab-title">优质品牌</span></div>
                <div class="jy-tab-wrap"><span class="jy-tab-title">文艺作品</span></div>
                <div class="jy-tab-wrap"><span class="jy-tab-title">读书活动</span></div>
                <div class="jy-tab-wrap"><span class="jy-tab-title">魅力警营</span></div>
            </div>
            <ul class="jy-commom jy-commom-tab jy-commom-tab-active">
                <c:forEach items="${fnc:getArticleList(site.id, '369bbb29d3734a86a39352ddae2ea352', 5, '')}"
                           var="article" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index==4}">
                            <li class="mr0"><a href="${article.url}" target="_blank"><img src="${article.image}" alt=""></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${article.url}" target="_blank"><img src="${article.image}" alt=""></a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
            <ul class="jy-commom jy-commom-tab">
                <c:forEach items="${fnc:getArticleList(site.id, '5dd8b3a5aebb4ec789fc01d50f24c6d0', 5, '')}"
                           var="article" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index==4}">
                            <li class="mr0"><a href="${article.url}" target="_blank"><img src="${article.image}" alt=""></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${article.url}" target="_blank"><img src="${article.image}" alt=""></a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
            <ul class="jy-commom jy-commom-tab">
                <c:forEach items="${fnc:getArticleList(site.id, '6a0c50ab15d04939a2d3b30ae78fafc6', 5, '')}"
                           var="article" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index==4}">
                            <li class="mr0"><a href="${article.url}" target="_blank"><img src="${article.image}" alt=""></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${article.url}" target="_blank"><img src="${article.image}" alt=""></a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
            <ul class="jy-commom jy-commom-tab">
                <c:forEach items="${fnc:getArticleList(site.id, '0b452d0382cb458d81af3e5bf99f93b5', 5, '')}"
                           var="article" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index==4}">
                            <li class="mr0"><a href="${article.url}" target="_blank"><img src="${article.image}" alt=""></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${article.url}" target="_blank"><img src="${article.image}" alt=""></a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
    </div>

    <%-- 宁铁传媒	--%>
    <div class="block">
        <div class="block-title-new"><span>宁铁传媒</span></div>
        <ul class="nt-list">
            <c:forEach items="${fnc:getArticleList(site.id, '20ccf8eca0f944e9b712834398094bc6', 4, '')}" var="article"
                       varStatus="status">
                <c:choose>
                    <c:when test="${status.index<2}">
                        <a href="${article.url}" target="_blank">
                            <li><img src="${article.image}" alt=""><span>${fns:abbr(article.title,16)}</span></li>
                        </a>
                    </c:when>
                    <c:when test="${status.index==3}">
                        <a href="${article.url}" target="_blank">
                            <li class="mr0"><img src="${article.image}"
                                                 alt=""><span>${fns:abbr(article.title,16)}</span></li>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${article.url}" target="_blank">
                            <li><img src="${article.image}" alt=""><span>${fns:abbr(article.title,16)}</span></li>
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>
    <%--
<div class="block">
    <div class="block-title">功能区</div>
    <div class="gn-wrap">
        <div class="gn-left">
            <div class="gn-left-item active"  onclick="window.location.href='${ctx}/list-4b0037eaba45452f935731d0330b832b${urlSuffix}'">网上书屋</div>
            <div class="gn-left-item"  onclick="window.location.href='${ctx}/list-c55f7521049a4a1bbaf9031c929fc142${urlSuffix}'">廉政教育</div>
            <div class="gn-left-item" onclick="window.location.href='${ctx}/list-c0a6b0c5c94b406abcb86a76d11a5470${urlSuffix}'">他山之石</div>
            <div class="gn-left-item" onclick="window.location.href='${ctx}/list-e8b9380de81048519e3c96c911e0118b${urlSuffix}'">民警必读</div>
        </div>
        <div class="gn-right">
            <div class="gn-right-wrap">
                <a class="more more2" href="${ctx}/list-d6ce6b47645446dbb7aa950e15438f57${urlSuffix}"></a>
                <a class="more more3" href="${ctx}/list-f2fa306bb4f446fc9c1d6f38e7033488${urlSuffix}"></a>
                <div class="swiper-container swiper-container2">
                    <div class="swiper-wrapper swiper-wrapper2">

                        <div class="swiper-slide swiper-slide2">
                            <c:forEach items="${fnc:getArticleList(site.id, 'd6ce6b47645446dbb7aa950e15438f57', 20, '')}" var="article">
                                <div class="gn-video-item"><a href="${pageContext.request.contextPath}/a/cms/article/video?path=${article.appendfile}" target="_blank"><img src="${article.image}"></a> </div>
                            </c:forEach>
                        </div>
                        <div class="swiper-slide swiper-slide2">
                            <c:forEach items="${fnc:getArticleList(site.id, 'd6ce6b47645446dbb7aa950e15438f57', 20, '')}" var="article">
                                <div class="gn-video-item"><a href="${pageContext.request.contextPath}/a/cms/article/video?path=${article.appendfile}" target="_blank"><img src="${article.image}"></a> </div>
                            </c:forEach>
                        </div>
                    </div>
                    <!-- 如果需要分页器 -->
                    <div class="swiper-pagination swiper-pagination2"></div>
                </div>

                <div class="pic-line"></div>

                <div class="swiper-container swiper-container3">
                    <div class="swiper-wrapper swiper-wrapper3">
                        <div class="swiper-slide swiper-slide3">
                            <c:forEach items="${fnc:getArticleList(site.id, 'f2fa306bb4f446fc9c1d6f38e7033488', 2, '')}" var="article">
                                <div class="gn-book-item"><a href="${pageContext.request.contextPath}/a/cms/article/pics?paths=${article.appendfile.replaceAll('\\|',';')}" target="_blank"><img src="${article.image}"></a> </div>
                            </c:forEach>
                        </div>
                        <div class="swiper-slide swiper-slide3">
                            <c:forEach items="${fnc:getArticleList(site.id, 'f2fa306bb4f446fc9c1d6f38e7033488', 2, '')}" var="article">
                                <div class="gn-book-item"><a href="${pageContext.request.contextPath}/a/cms/article/pics?paths=${article.appendfile.replaceAll('\\|',';')}" target="_blank"><img src="${article.image}"></a> </div>
                            </c:forEach>
                        </div>
                    </div>
                    <!-- 如果需要分页器 -->
                    <div class="swiper-pagination swiper-pagination3"></div>
                </div>
            </div>
        </div>
    </div>
</div>
--%>
</div>
<script src="${ctxStatic}/swiper/js/swiper.min.js"></script>
<script src="${ctxStatic}/echart/echarts.min.js.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    // banner
    var mySwiper1 = new Swiper('.swiper-container1', {
        loop: true, // 循环模式选项
        autoplay: {
            delay: 3000,
            disableOnInteraction: false
        },
        pagination: {
            el: '.swiper-pagination1',
            clickable: true,
            renderBullet: function (index, className) {
                return '<span class="' + className + '">' + (index + 1) + '</span>';
            },
        }
    })
    var mySwiper2 = new Swiper('.swiper-container2', {
        loop: true, // 循环模式选项
        autoplay: {
            delay: 3000,
            disableOnInteraction: false
        },
        pagination: {
            el: '.swiper-pagination2',
        }
    })
    var mySwiper3 = new Swiper('.swiper-container3', {
        loop: true, // 循环模式选项
        autoplay: {
            delay: 3000,
            disableOnInteraction: false
        },
        pagination: {
            el: '.swiper-pagination3',
        }
    })


    // 飘窗
    var obj = document.getElementById("img1");

    function ggRollFn(x, y, o) {
        return {
            roll: o,
            speed: 16,
            statusX: document.documentElement.clientWidth - document.body.clientWidth,
            statusY: document.documentElement.clientHeight - document.body.clientHeight,
            x: x,
            y: y,
            winW: document.documentElement.clientWidth - obj.offsetWidth * 2,
            winH: document.documentElement.clientHeight - obj.offsetHeight,
            Go: function () {
                this.roll.style.left = this.x + 'px';
                this.roll.style.top = this.y + 'px';

                this.x = this.x + (this.statusX ? -1 : 1);
                if (this.x < 0) {
                    this.statusX = 0;
                }
                if (this.x > this.winW) {
                    this.statusX = 1;
                }

                this.y = this.y + (this.statusY ? -1 : 1);
                if (this.y < 0) {
                    this.statusY = 0;
                }
                if (this.y > this.winH) {
                    this.statusY = 1;
                }
            }
        }
    }

    var ggRoll = ggRollFn(200, 400, obj)
    var intervaly = setInterval("ggRoll.Go()", ggRoll.speed);
    ggRoll.roll.onmouseover = function () {
        clearInterval(intervaly);
    };
    ggRoll.roll.onmouseout = function () {
        intervaly = setInterval("ggRoll.Go()", ggRoll.speed);
    };


    // 警营文化tab
    $('.jy-tab-wrap').click(function () {
        $('.jy-tab-wrap').removeClass('active')
        $(this).addClass('active')
        var $index = $(this).index()
        console.log($index)
        $('.jy-commom-tab').removeClass('jy-commom-tab-active')
        $('.jy-commom-tab').eq($index).addClass('jy-commom-tab-active')
    })

    // echarts
    echartsFn();

    // echarts1
    function echartsFn() {
        //考评情况
        $.get('${ctx}/indexExamEcharts', function (data) {
            if (data.unitNameList != null) {//用data.unitNameList做判断，用data做判断有问题
                var labelData = [];
                var countData = [];
                labelData = data.unitNameList;
                countData = data.scoreList;
                var myChart1 = echarts.init(document.getElementById('echarts1'));
                option = {
                    color: ['#F92400'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: labelData,
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '考评',
                            type: 'bar',
                            barWidth: '60%',
                            data: countData
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                if (labelData) {
                    myChart1.setOption(option);
                }
            }
        });
        //统计情况
        $.get('${ctx}/indexStatistics', function (data) {
            var labelData = [];
            var countData = [];
            for (var i = 0; i < data.length; i++) {
                labelData.push(data[i].label);
                countData.push(data[i].count);
                var pie = {};
                pie.value = data[i].count;
                pie.name = data[i].label;
            }
            // 基于准备好的dom，初始化echarts实例
            var myChart2 = echarts.init(document.getElementById('echarts2'));
            // 指定图表的配置项和数据
            var option2 = {
                color: ['#F92400'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: labelData,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '政治面貌情况',
                        type: 'bar',
                        barWidth: '60%',
                        data: countData,
                        label: {
                            show: true,
                            position: "top"
                        }
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            if (labelData) {
                myChart2.setOption(option2);
            }
        });
    }

    $('.gn-left-item').click(function () {
        $('.gn-left-item').removeClass('active');
        $(this).addClass('active');
    })

    // 通知公告tab
    $('.tz-tab').find('.tz-tab-title').click(function () {
        $(this).siblings('.tz-tab-title').removeClass('active');
        $(this).addClass('active');
        var tabIndex = $(this).index()
        $(this).parent('.tz-tab').siblings('.list-commom-tab').removeClass('list-commom-tab-active');
        $(this).parent('.tz-tab').siblings('.list-commom-tab').eq(tabIndex).addClass('list-commom-tab-active');

        // $('.tz-list').find('.list-more-style').removeClass('list-more-style-active');
        // $('.tz-list').find('.list-more-style').eq(tabIndex).addClass('list-more-style-active');
    })
</script>
</body>
</html>
