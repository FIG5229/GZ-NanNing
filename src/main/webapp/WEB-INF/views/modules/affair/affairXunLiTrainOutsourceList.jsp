<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>委外培训管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnPrint").click(function () {
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $('#checkTh').css('display', 'none');
                $('.checkTd').css('display', 'none');
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
                    pageTitle: "打印",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: false,
                    afterPrint: function () {
                        $('#handleTh').css('display', 'table-cell');
                        $('.handleTd').css('display', 'table-cell');
                        $('#checkTh').css('display', 'table-cell');
                        $('.checkTd').css('display', 'table-cell');
                    }

                });
            });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        if ("success" == "${saveResult}") {
            parent.$.jBox.close();
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <%--<li><a href="${ctx}/affair/affairXunLi/onlineCourse?idNumber=${idNumber}">在线课程</a></li>--%>
    <li><a href="${ctx}/affair/affairXunLi/trainingCourses?idNumber=${idNumber}">培训班课程</a></li>
    <%--<li><a href="${ctx}/affair/affairXunLi/examEntering?idNumber=${idNumber}">考试</a></li>--%>
    <li class="active"><a href="${ctx}/affair/affairXunLi/record?idNumber=${idNumber}">委外培训</a></li>
    <%--<li><a href="${ctx}/affair/affairXunLi/exchangeLearning?idNumber=${idNumber}">交流学习</a></li>--%>
    <li><a href="${ctx}/affair/affairXunLi/job?idNumber=${idNumber}">岗位练兵</a></li>
</ul>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>培训班名称</th>
        <th>培训班类别</th>
        <th>培训完成情况</th>
        <th>主办单位</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>主办单位机构代码</th>
        <th>主办单位级别</th>
        <th>培训离岗状态</th>
        <th>承训机构名称</th>
        <th>培训地点（城市）</th>
        <th>培训证书编号</th>
        <th>培训时所在单位及职务</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page}" var="trainOutsource" varStatus="status">
    <tr>
        <td>
                ${trainOutsource.externalName}
        </td>
        <td>
                ${fns:getDictLabel(trainOutsource.externalType, 'external_type', '')}
        </td>
        <td>
                ${fns:getDictLabel(trainOutsource.completion, 'train_completion', '')}
        </td>
        <td>
                ${trainOutsource.unit}
        </td>
        <td>
            <fmt:formatDate value="${trainOutsource.beganDate}" pattern="yyyy-MM-dd"/>
        </td>
        <td>
            <fmt:formatDate value="${trainOutsource.resultDate}" pattern="yyyy-MM-dd"/>
        </td>
        <td>
                ${trainOutsource.institutionCode}
        </td>
        <td>
                ${fns:getDictLabel(trainOutsource.unitLevel, 'unit_level', '')}
        </td>
        <td>
                ${fns:getDictLabel(trainOutsource.quitStatus, 'quit_status', '')}
        </td>
        <td>
                ${trainOutsource.unitName}
        </td>
        <td>
                ${trainOutsource.trainSite}
        </td>
        <td>
                ${trainOutsource.certificateCode}
        </td>
        <td>
                ${trainOutsource.unitJob}
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
    <%--<div class="pagination">${page}</div>--%>
</body>
</html>