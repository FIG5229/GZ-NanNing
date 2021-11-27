<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>文章管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        function viewComment(href) {
            top.$.jBox.open('iframe:' + href, '查看评论', $(top.document).width() - 220, $(top.document).height() - 120, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                    $(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
                    $("body", h.find("iframe").contents()).css("margin", "10px");
                }
            });
            return false;
        }

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/cms/article/columnStatistics">工作统计</a></li>
</ul>
<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/columnStatistics" method="post"
           class="breadcrumb form-search">
<%--    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
<%--    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
<%--    <label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;--%>
    <li><label class="width120">时间：</label>
        <input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate value="${article.beginDate}" pattern="yyyy-MM-dd"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        --
        <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate value="${article.endDate}" pattern="yyyy-MM-dd"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </li>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>部门</th>
        <th>调研文章统计</th>
        <th>政工简报统计</th>
        <th>宣传教育统计</th>
        <th>总数</th>
<%--        <th>更新时间</th>--%>
<%--        <th>操作</th>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page}" var="result" varStatus="status">
        <tr>
<%--           无分页 暂时不用 <td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>--%>
            <td>${status.index+1}</td>
           <td>${result.label}</td>
           <td>${result.survey}</td>
           <td>${result.report}</td>
           <td>${result.xuanjiao}</td>
           <td>${result.total}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<div class="pagination">${page}</div>--%>
</body>
</html>