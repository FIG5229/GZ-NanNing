<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新闻宣传管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
        });
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
<form:form id="searchForm" modelAttribute="affairNewsCount" action="${ctx}/affair/affairNewsCount/countByUnit" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>中央级（中央电视台、新华社/中新社、中央人民广播电台、中央级报纸）</th>
        <th>省部级（省卫视、专题、其他）</th>
        <th>地市级</th>
        <th>各大网络新媒体平台</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${page.list}" var="affairNewsCount" varStatus="status" >
            <tr>
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
            </tr>
        </c:forEach>

    </tbody>
</table>
</body>
</html>