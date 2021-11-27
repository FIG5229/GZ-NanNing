<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>绩效考评-统计分析-普通民警考核</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .content-div {
            margin: 20px;
            padding: 40px;
            border: 1px solid #000;
            border-radius: 4px;
            width: auto;
        }

        .inner-div {
            padding: 20px;
            border: 1px solid #000;
            border-radius: 4px;
        }

        .charts-div {
            display: inline-block;
            width: 450px;
            height: 550px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s, v) {
            self.location.href = "${ctx}/exam/examLdPerformanceAppraisal/policemanList?reasonType=${reasonType}&dateType=${dateType}&year=${year}&month=${month}&unitId=${selUnitId}&unitId=${unitId}&pageNo=" + n + "&pageSize=" + s;
        }

        //打开考评档案
        function openExamRecord(personId, idNumber, name) {
            var url = '${ctx}/exam/examRecord/list?type=person&personId=' + personId + '&idNumber=' + idNumber + '&name=' + name;
            top.$.jBox.open("iframe:" + url, "考评档案", 1000, 600, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }
            });
        }
    </script>
</head>
<body>
<c:if test="${reasonType eq '2'}">
    <%--中基层领导考核情况--%>
    <ul class="nav nav-tabs" id="chuZLDexamUl">
            <%--通过c:forEach 遍历生成--%>
        <c:forEach items="${unitList}" var="unit" varStatus="status">
            <c:choose>
                <c:when test="${unit.unitId eq selUnitId}">
                    <li class="active">
                        <a href="${ctx}/exam/examLdPerformanceAppraisal/policemanList?reasonType=${reasonType}&jz=${jz}&zw=${zw}&ageStart=${ageStart}&ageEnd=${ageEnd}&dateType=${dateType}&year=${year}&month=${month}&pageNo=1&pageSize=10&selUnitId=${unit.unitId}&unitId=${unitId}">${unit.unitName}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="${ctx}/exam/examLdPerformanceAppraisal/policemanList?reasonType=${reasonType}&jz=${jz}&zw=${zw}&ageStart=${ageStart}&ageEnd=${ageEnd}&dateType=${dateType}&year=${year}&month=${month}&pageNo=1&pageSize=10&selUnitId=${unit.unitId}&unitId=${unitId}">${unit.unitName}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${reasonType eq '1'}">
    <%--处领导考核情况--%>
    <ul class="nav nav-tabs" id="chuLDexamUl">
            <%--通过c:forEach 遍历生成--%>
        <c:forEach items="${unitList}" var="unit" varStatus="status">
            <c:choose>
                <c:when test="${unit.unitId eq selUnitId}">
                    <li class="active">
                        <a href="${ctx}/exam/examLdPerformanceAppraisal/policemanList?reasonType=${reasonType}&jz=${jz}&zw=${zw}&ageStart=${ageStart}&ageEnd=${ageEnd}&dateType=${dateType}&year=${year}&month=${month}&unitId=${unitId}&pageNo=1&pageSize=10&selUnitId=${unit.unitId}">${unit.unitName}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="${ctx}/exam/examLdPerformanceAppraisal/policemanList?reasonType=${reasonType}&jz=${jz}&zw=${zw}&ageStart=${ageStart}&ageEnd=${ageEnd}&dateType=${dateType}&year=${year}&month=${month}&unitId=${unitId}&pageNo=1&pageSize=10&selUnitId=${unit.unitId}">${unit.unitName}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</c:if>
<sys:message content="${message}"/>
<%--普通民警考核情况--%>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>分数</th>
        <c:if test="${dateType eq '2'}">
            <th>等次</th>
        </c:if>
        <th>考评档案</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="examLdScore" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${examLdScore.name}
            </td>
            <td>
            <c:if test="${dateType eq '1'}">
                ${examLdScore.score}
            </c:if>
            <c:if test="${dateType eq '2'}">
                ${examLdScore.sumScore}
            </c:if>
            </td>
            <c:if test="${dateType eq '2'}">
                <td>
                    <%--<a href="">--%>${fns:getDictLabel(examLdScore.grades,'performance_grade', '')}<%--</a>--%><%--评定--%>
                </td>
            </c:if>
            <td>
                    <%--${examLdScoreMonth.dangan}--%>
                <a href="javaScript:void(0);"
                   onclick="openExamRecord('${examLdScore.personnelBaseId}','${examLdScore.idNumber}','${examLdScore.name}')">档案详情</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${reasonType ne '2' and reasonType ne '1'}">
<div class="pagination">${page}</div>
</c:if>
</body>
</html>