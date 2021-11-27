<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>信访举报投诉</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        };
    </script>
</head>
<body>

<form:form id="searchForm" modelAttribute="affairDisciplinaryAction" action="" method="post">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairDisciplinaryAction.year}"/>
    <input id="month" name="month" type="hidden" value="${affairDisciplinaryAction.month}"/>
    <input id="dateStart" name="dateStart" type="hidden" value="${affairDisciplinaryAction.dateStart}"/>
    <input id="dateEnd" name="dateEnd" type="hidden" value="${affairDisciplinaryAction.dateEnd}"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th rowspan="2">序号</th>
        <th colspan="2" style="text-align: center">时间</th>
        <th rowspan="2">姓名</th>
        <th rowspan="2">单位</th>
        <th rowspan="2">职务</th>
        <th rowspan="2">职级</th>
        <th rowspan="2">政治面貌</th>
        <th rowspan="2">问题性质</th>
        <th colspan="5" style="text-align: center">处分及处理方式</th>
        <th rowspan="2">处分单位</th>
        <th rowspan="2">文号</th>
        <th rowspan="2">处分决定时间</th>
        <th rowspan="2">处分期满时间</th>
        <th rowspan="2">是否立案</th>
    </tr>
    <tr>
        <th>发生时间</th>
        <th>受理时间</th>
        <th>党纪处分</th>
        <th>行政处分</th>
        <th>司法处理</th>
        <th>组织处理</th>
        <th>其他方式</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairDisciplinaryAction" varStatus="status">
    <tr>
        <td>
                ${(page.pageNo-1)*page.pageSize+status.index+1}
        </td>
        <td>
            <fmt:formatDate value="${affairDisciplinaryAction.lrDate}" pattern="yyyy-MM-dd"/>
        </td>
        <td>
            <fmt:formatDate value="${affairDisciplinaryAction.chfDate}" pattern="yyyy-MM-dd"/>
        </td>
        <td>
                ${affairDisciplinaryAction.name}
        </td>
        <td>
                ${affairDisciplinaryAction.unit}
        </td>
        <td>
                ${affairDisciplinaryAction.job}
        </td>
        <td>
                ${affairDisciplinaryAction.jobLevel}
        </td>
        <td>
                ${fns:getDictLabel(affairDisciplinaryAction.zhengzhiFace, 'political_status', '')}
        </td>
        <td>
                ${fns:getDictLabel(affairDisciplinaryAction.nature, 'affair_wenti', '')}
        </td>
        <td>
                <%--						${fns:getDictLabel(affairDisciplinaryAction.subOption, 'affair_dj_type', '')}--%>
                ${fns:getDictLabel(affairDisciplinaryAction.zzSubOption, 'affair_dj_sub_option', '')}
        </td>
        <td>
                <%--						${fns:getDictLabel(affairDisciplinaryAction.disciplinaryType, 'affair_xzchufen', '')}--%>
                ${fns:getDictLabel(affairDisciplinaryAction.xzSubOption, 'affair_xz_sub_option', '')}
        </td>
        <td>
                ${affairDisciplinaryAction.sifa}
        </td>
        <td>
                ${affairDisciplinaryAction.zuzhi}
        </td>
        <td>
                ${affairDisciplinaryAction.other}
        </td>
        <td>
                ${fns:getDictLabel(affairDisciplinaryAction.cfUnit, 'affair_cf_unit', '')}
        </td>
        <td>
                ${affairDisciplinaryAction.fileNum}
        </td>
        <td>
            <fmt:formatDate value="${affairDisciplinaryAction.approvalDate}" pattern="yyyy-MM-dd"/>
        </td>
        <td>
            <fmt:formatDate value="${affairDisciplinaryAction.influencePeriod}" pattern="yyyy-MM-dd"/>
        </td>
        <td>
                ${fns:getDictLabel(affairDisciplinaryAction.isLian, 'yes_no', '')}
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>