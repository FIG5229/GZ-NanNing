<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>评价内容</title>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
    <meta name="decorator" content="default"/>
</head>
<body>
<div id="modalSysSelf">
    <div class="modal-custom-content">
        <div class="modal-custom-tb-r"style="width: 100%">
                <table class="table table-striped table-bordered table-condensed" style="width: 100%;">
                    <thead>
                    <tr>
                        <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                            <th>${examStandardTemplateItemDefine.columnName}</th>
                        </c:forEach>
                        <th>上级检查评分情况</th>
                        <c:choose>
                            <c:when test="${'2'.equals(modleType)}">
                                <th>加分(实际)</th>
                            </c:when>
                            <c:otherwise>
                                <th>扣分(实际)</th>
                            </c:otherwise>
                        </c:choose>
                        <th>附件</th>
                        <th>自评人</th>
                        <th>考核人</th>
                    </tr>
                    </thead>
                    <tbody id="r_body">
                    <c:forEach items="${rowlist}" var="row"  varStatus="status">
                        <c:if test="${row.isSelected==1}">
                        <tr id="s_${row.row_num}">
                            <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                <td>
                                        ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                </td>
                            </c:forEach>
                            <td></td>
                            <td>${row.value}</td>
                            <td><a href="${pageContext.request.contextPath}${row.path}" target="_blank">${row.path.substring(row.path.lastIndexOf("/")+1)}</a></td>
                            <td>${row.fillPerson}</td>
                            <td>${row.examPerson}</td>
                        </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
        </div>
    </div>
</div>
</body>
</html>
