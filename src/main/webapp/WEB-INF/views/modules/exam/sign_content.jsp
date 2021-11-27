<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>评价内容</title>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
    <meta name="decorator" content="default"/>
    <script>
        if ("success" == "${result}") {
            parent.window.location.href = "${ctx}/exam/examWorkflow/exam?id=${workflowId}";
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate();
        });
        function formSubmit(processType) {
            $("#processType").val(processType);
            $("#inputForm").submit();
        }
    </script>
</head>
<body>
<form:form id="inputForm" action="${ctx}/exam/examWorkflow/appaise/data/save" method="post" class="form-horizontal">
    <input id="id" name="id" value="${workflowId}" type="hidden"/>
    <input id="status" name="status" value="${status}" type="hidden"/>
    <input id="standardId" name="standardId" value="${standardId}" type="hidden"/>
    <input id="selectedNumber" name="selectedNumber" value="${selectedNumber}" type="hidden"/>
    <input id="processType" name="processType" value="${processType}" type="hidden"/>
    <div id="modalSysSelf">
        <div class="modal-custom-content">
            <div class="modal-custom-tb-l" style="display: none">
                <table class="table table-striped table-bordered table-condensed" style="width: 100%;">
                    <thead>
                    <tr>
                        <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                            <th>${examStandardTemplateItemDefine.columnName}</th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${rowlist}" var="row"  varStatus="status">
                        <tr id="s_${row.row_num}">
                            <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                <td>
                                        ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                </td>
                            </c:forEach>
                            <td><input class="i-checks" type="checkbox" name="standardbox" value="${row.row_num}"
                            <c:if test="${row.isSelected==1}">
                                       checked
                            </c:if>
                            ></td>
                            <input type='hidden' name='standardDatas[${status.index}].standardId'
                                   value='${standardId}'/>
                            <input type='hidden' name='standardDatas[${status.index}].workflowId'
                                   value='${workflowId}'/>
                            <input type='hidden' name='standardDatas[${status.index}].rowId'
                                   value='${row.row_num}'/>
                            <c:if test="${null != row.id}">
                                <input type='hidden' name='standardDatas[${status.index}].id'
                                       value='${row.id}'/>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-custom-tb-r" style="width: 100%">
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
                        <c:if test="${'partBureausSign'.equals(processType)||'mainBureausSign'.equals(processType)}">
                            <th>部门负责人</th>
                        </c:if>
                        <c:if test="${'mainBureausSign'.equals(processType)}">
                            <th>分管局领导</th>
                            <th>绩效考评领导</th>
                        </c:if>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody id="r_body">
                    <c:set var="num" value="0"></c:set>
                    <c:set var="step" value="1"></c:set>
                    <c:forEach items="${rowlist}" var="row"   varStatus="status">
                        <c:if test="${'1'.equals(row.isSelected)}">
                            <tr id="r_${row.row_num}">
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
                                <c:if test="${'partBureausSign'.equals(processType)||'mainBureausSign'.equals(processType)}">
                                    <td>${row.departSign}</td>
                                </c:if>
                                <c:if test="${'mainBureausSign'.equals(processType)}">
                                    <td>${row.partBureausSign}</td>
                                    <td>${row.adjustPerson}</td>
                                </c:if>
                                <td>
                                    <c:choose>
                                        <c:when test="${row.operationStatus>-1}">
                                            已签字
                                        </c:when>
                                        <c:otherwise>
                                            未签字
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <input type='hidden' name='datas[${num}].standardId'
                                       value='${standardId}'/>
                                <input type='hidden' name='datas[${num}].workflowId'
                                       value='${workflowId}'/>
                                <input type='hidden' name='datas[${num}].rowId'
                                       value='${row.row_num}'/>
                                <input type='hidden' name='datas[${num}].id'
                                       value='${row.id}'/>
                            </tr>
                            <c:set var="num" value="${num+step}"></c:set>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <div style="text-align: right;">
                    <c:choose>
                        <c:when test="${'5'.equals(nextStep)}">
                            <input id="complete" class="btn btn-primary" type="button" value="签字并推送到" onclick="formSubmit('partBureausSign')"/>
                            <select name="personId">
                                <c:forEach items="${userList}" var="user">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </c:when>
                        <c:when test="${'6'.equals(nextStep)||'8'.equals(nextStep)}">
                            <input id="btnSubmit" class="btn btn-primary" type="submit" value="确认签字" onclick="formSubmit('departSign')"/>
                        </c:when>
                        <c:when test="${'7'.equals(nextStep)}">
                            <input id="complete" class="btn btn-primary" type="button" value="签字并推送到" onclick="formSubmit('mainBureausSign')"/>
                            <select name="personId">
                                <c:forEach items="${userList}" var="user">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <input id="complete" class="btn btn-primary" type="button" value="签字并推送到" onclick="formSubmit('departSign')"/>
                            <select name="personId">
                                <c:forEach items="${userList}" var="user">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</form:form>
</body>
</html>
