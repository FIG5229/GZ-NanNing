<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>督察问题库管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            var v = $("#reasons").val();
            $("#reason").val(v);
            $("[data-toggle='popover']").popover();
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        $('#notPass').popover();
    </script>
</head>
<body>

<form:form id="searchForm" modelAttribute="affairDcwtLibrary" action="" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairDcwtLibrary.year}"/>
    <input id="month" name="month" type="hidden" value="${affairDcwtLibrary.month}"/>
    <input id="dateStart" name="dateStart" type="hidden" value="${affairDcwtLibrary.startDate}"/>
    <input id="dateEnd" name="dateEnd" type="hidden" value="${affairDcwtLibrary.endDate}"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>责任单位</th>
        <th>监督单位</th>
        <th>督察方式</th>
        <th>存在不足</th>
        <th>问题概述</th>
        <th>整改情况</th>
        <th>整改说明</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairDcwtLibrary" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                <fmt:formatDate value="${affairDcwtLibrary.time}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                <fmt:formatDate value="${affairDcwtLibrary.finishDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${affairDcwtLibrary.responsibleUnit}
            </td>
            <td>
                    ${fns:getDictLabel(affairDcwtLibrary.supervisoryUnit, 'affair_jdunit', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairDcwtLibrary.dcType, 'affair_dc_type', '')}
            </td>
            <td>
                <c:forEach items="${affairDcwtLibrary.problemCategory.split(',')}" var="arr" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index==0}">${fns:getDictLabel(arr, 'affair_wtlb', '')}</c:when>
                        <c:otherwise>
                            ,${fns:getDictLabel(arr, 'affair_wtlb', '')}
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </td>
            <td>
                <c:choose>
                    <c:when test="${!empty affairDcwtLibrary.foundProblem}">
                        <a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
                           data-content="${affairDcwtLibrary.foundProblem}"  style="cursor: pointer;color: red">详情</a>
                    </c:when>
                    <c:otherwise>
                        ${affairDcwtLibrary.foundProblem}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                    ${fns:getDictLabel(affairDcwtLibrary.rectification, 'affair_zhenggai', '')}
            </td>
            <td>

                <c:choose>
                    <c:when test="${!empty affairDcwtLibrary.processingSituation}">
                        <a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
                           data-content="${affairDcwtLibrary.processingSituation}"  style="cursor: pointer;color: red">详情</a>
                    </c:when>
                    <c:otherwise>
                        ${affairDcwtLibrary.processingSituation}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
<div class="pagination">${page}</div>
</body>
</html>