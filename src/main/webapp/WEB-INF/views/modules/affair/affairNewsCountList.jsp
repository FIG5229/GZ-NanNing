<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻宣传管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            /*导出数据*/
            $("#export").click(
                function () {
                    var submit = function (v, h, f) {
                        //debugger;er
                        if (v == 'all') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairNewsCount/export?flagOne=${flagOne}");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairNewsCount/countByUnit");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairNewsCount/export?flags=true&flagOne=${flagOne}");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairNewsCount/countByUnit");
                        }
                        if (v == 'cancel') {
                            $.jBox.tip('已取消');
                        }
                        return true;
                    };
                    $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, {
                        buttons: {
                            '导出当前页面数据': 'part',
                            '取消': 'cancel'
                        }
                    });
                }
            );
        });
        function page(n,s){
            $("#searchForm").submit();
            return false;
        }
        function openDialog(url) {
            top.$.jBox.open("iframe:"+url, "刊稿排名",1200,600,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairNewsCount/countByUnit"}
            });
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/affair/affairNews/">刊稿稿件</a></li>
    <li class="active"><a href="${ctx}/affair/affairNewsCount/countByUnit">刊稿排名</a></li>
</ul>
<form:form id="searchForm" modelAttribute="affairNewsCount" action="${ctx}/affair/affairNewsCount/countByUnit" method="post" class="breadcrumb form-search">
    <input id="fileName" name="fileName" type="hidden" value="刊稿排名.xlsx"/>
    <ul class="ul-form">
        <li><label>年度：</label>
            <input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${date}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        </li>

        <c:if test="${'1'== fns:getUser().company.id}">
            <li><label>统计分类：</label>
                    <form:select path="flagTwo" class="input-medium">

                        <form:options items="${fns:getDictList('affair_news_flagTwo')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
        </c:if>
        <c:if test="${'1'!= fns:getUser().company.id}">
                <li><label>统计分类：</label>
                    <form:select path="flag" class="input-medium">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('affair_news_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
        </c:if>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>排名</th>
        <th>单位/作者/所属人</th>
        <th>中央级（中央电视台、新华社/中新社、中央人民广播电台、中央级报纸）</th>
        <th>省部级（省卫视、专题、其他）</th>
        <th>地市级</th>
        <th>各大网络</th>
        <th>新媒体平台</th>
        <th>数量</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${'1'.equals(affairNewsCount.flag)}">
        <c:forEach items="${page.list}" var="affairNewsCount" varStatus="status" >
                <tr>
                    <td>
                            ${(page.pageNo-1)*page.pageSize+status.index+1}
                    </td>
                   <%-- <td><a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairNewsCount/detailCountByUnit?unitId=${affairNewsCount.unitId}')">
                            ${affairNewsCount.unit}
                    </a> </td>--%>
                    <td>
                            ${affairNewsCount.unit}
                    </td>

                    <td>
                            ${affairNewsCount.sum1}
                    </td>
                    <td>
                            ${affairNewsCount.sum2}
                    </td>
                    <td>
                            ${affairNewsCount.sum3}
                    </td>
                    <td>
                            ${affairNewsCount.sum4}
                    </td>
                    <td>
                            ${affairNewsCount.sum5}
                    </td>
                    <td>
                            ${affairNewsCount.sum}
                    </td>
                </tr>
        </c:forEach>
    </c:if>
    <c:if test="${'2'.equals(affairNewsCount.flag)}">
        <c:forEach items="${page.list}" var="affairNewsCount" varStatus="status" >
            <tr>
                <td>
                        ${(page.pageNo-1)*page.pageSize+status.index+1}
                </td>
               <%-- <td><a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairNewsCount/detailCountByAuthor?author=${affairNewsCount.author}')">
                        ${affairNewsCount.author}
                </a></td>--%>
                <td>
                        ${affairNewsCount.author}
                </td>

                    <td>
                            ${affairNewsCount.sum1}
                    </td>
                    <td>
                            ${affairNewsCount.sum2}
                    </td>
                    <td>
                            ${affairNewsCount.sum3}
                    </td>
                    <td>
                            ${affairNewsCount.sum4}
                    </td>
                    <td>
                            ${affairNewsCount.sum5}
                    </td>
                    <td>
                            ${affairNewsCount.sum}
                    </td>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${'3'.equals(affairNewsCount.flag)}">
        <c:forEach items="${page.list}" var="affairNewsCount" varStatus="status" >
            <tr>
                <td>
                        ${(page.pageNo-1)*page.pageSize+status.index+1}
                </td>
                <%--<td><a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairNewsCount/detailCountByName?name=${affairNewsCount.name}')">
                        ${affairNewsCount.name}
                </a>  </td>--%>
                <td>
                        ${affairNewsCount.name}
                </td>

                    <td>
                            ${affairNewsCount.sum1}
                    </td>
                    <td>
                            ${affairNewsCount.sum2}
                    </td>
                    <td>
                            ${affairNewsCount.sum3}
                    </td>
                    <td>
                            ${affairNewsCount.sum4}
                    </td>
                    <td>
                            ${affairNewsCount.sum5}
                    </td>
                    <td>
                            ${affairNewsCount.sum}
                    </td>
            </tr>
        </c:forEach>
    </c:if>


    </tbody>
</table>
</body>
</html>