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

<form:form id="searchForm" modelAttribute="affairTousujubaoguanli" action="" method="post">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairTousujubaoguanli.year}"/>
    <input id="month" name="month" type="hidden" value="${affairTousujubaoguanli.month}"/>
    <input id="dateStart" name="dateStart" type="hidden" value="${affairTousujubaoguanli.dateStart}"/>
    <input id="dateEnd" name="dateEnd" type="hidden" value="${affairTousujubaoguanli.dateEnd}"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>反映人</th>
        <th>媒介类型</th>
        <th>被反映人</th>
        <%--				<th>被反映人单位</th>--%>
        <th>问题类型</th>
        <%--        <th>批转单位</th>--%>
        <th>收到单位</th>
        <th>收到时间</th>
        <th>是否重复</th>
        <th>查处单位</th>
        <th>查办状态</th>
        <th>是否纪律处分</th>
        <th>转办状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairTousujubaoguanli" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairTousujubaoguanli.informer}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.complaintWay, 'affair_tousu', '')}
            </td>
            <td>
                    ${affairTousujubaoguanli.repoter}
            </td>
                <%--<td>
                    ${affairTousujubaoguanli.repoterUnit}
                </td>--%>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.questionType, 'affair_qtType', '')}
                <c:if test="${affairTousujubaoguanli.questionType == '1'}">
                    ${fns:getDictLabel(affairTousujubaoguanli.zjType, 'affair_zjType', '')}
                </c:if>
                <c:if test="${affairTousujubaoguanli.questionType == '2'}">
                    ${fns:getDictLabel(affairTousujubaoguanli.sfType, 'affair_sfType', '')}
                </c:if>
                <c:if test="${affairTousujubaoguanli.questionType == '3'}">
                    ${fns:getDictLabel(affairTousujubaoguanli.jjType, 'affair_jjType', '')}
                </c:if>
            </td>
                <%--  <td>
                          ${fns:getDictLabel(affairTousujubaoguanli.forwardType, 'approval_unit', '')}
                      <c:if test="${affairTousujubaoguanli.forwardType == '2'}">
                          -${affairTousujubaoguanli.forwardUnit}
                      </c:if>
                  </td>--%>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.sdUnit, 'affair_xfjb_unit', '')}
            </td>
            <td>
                <fmt:formatDate value="${affairTousujubaoguanli.receiveTime}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.isrepeat, 'yes_no', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.ccUnit, 'affair_xfjb_unit', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.ischeck, 'affair_cbtype', '')}
                <c:if test="${affairTousujubaoguanli.ischeck == '2'}">
                    -${fns:getDictLabel(affairTousujubaoguanli.subOption, 'affair_bjtype_sub', '')}
                    <c:if test="${not empty affairTousujubaoguanli.result}">
                        -${affairTousujubaoguanli.result}
                    </c:if>
                </c:if>
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.isdispose, 'yes_no', '')}
                <c:if test="${affairTousujubaoguanli.isdispose == '0'}">
                    -${fns:getDictLabel(affairTousujubaoguanli.noPunish, 'affair_no_punish_type', '')}
                    <c:if test="${affairTousujubaoguanli.noPunish == '5'}">
                        -${affairTousujubaoguanli.otherMethod}
                    </c:if>
                </c:if>
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.zbStatus, 'zb_type', '')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>