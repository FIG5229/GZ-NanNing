<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>考评管理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function openForm(url) {
            top.$.jBox.open("iframe:" + url, "考评管理", 1000, 620, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    /*保存后页面在本页面刷新 不跳转到待办页面*/
                    self.location.href = "${ctx}/exam/examWorkflow?examType=${examType}"
                }
            });
        }

        function startWorkflow(mess,url) {
            top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
                if(v=='ok'){
                    if (typeof href == 'function') {
                        href();
                    }else{
                        resetTip(); //loading();
                        location = url;
                    }
                        jBox.tip("正在启动，请稍等，大约5分钟启动完成.","loading");
                }else{

                }
            });
        }

        function openGradesListDialog(workflowId) {
            top.$.jBox.open("iframe:${ctx}/exam/examLdScore/list?workflowId="+workflowId, "考评管理", 1000, 620, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    /*保存后页面在本页面刷新 不跳转到待办页面*/
                    self.location.href = "${ctx}/exam/examWorkflow?examType=${examType}"
                }
            });
        }

        function examDetail(workflowId) {
            top.$.jBox.open("iframe:${ctx}/exam/examWorkflowDatas/unExam?workflowId="+workflowId, "未初核人员", 1000, 620, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    /*保存后页面在本页面刷新 不跳转到待办页面*/
                    self.location.href = "${ctx}/exam/examWorkflow?examType=${examType}"
                }
            });
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/exam/examWorkflow/flowList?examType=${examType}">待办</a></li>
    <li><a href="${ctx}/exam/examWorkflow/flowList?examType=${examType}&history=true">已办</a></li>
    <c:if test="${'1'.equals(examType)}">
        <shiro:hasPermission name="exam:examWorkflow:edit1">
            <li class="active"><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li>
        </shiro:hasPermission>
    </c:if>
    <c:if test="${'2'.equals(examType)}">
        <shiro:hasPermission name="exam:examWorkflow:edit2">
            <li class="active"><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li>
        </shiro:hasPermission>
    </c:if>
    <c:if test="${'3'.equals(examType)}">
        <shiro:hasPermission name="exam:examWorkflow:edit3">
            <li class="active"><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li>
        </shiro:hasPermission>
    </c:if>
    <c:if test="${'4'.equals(examType)}">
        <shiro:hasPermission name="exam:examWorkflow:edit4">
            <li class="active"><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li>
        </shiro:hasPermission>
    </c:if>
    <c:if test="${'5'.equals(examType)}">
        <shiro:hasPermission name="exam:examWorkflow:edit5">
            <li class="active"><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li>
        </shiro:hasPermission>
    </c:if>
    <c:if test="${'6'.equals(examType)}">
        <shiro:hasPermission name="exam:examWorkflow:edit6">
            <li class="active"><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li>
        </shiro:hasPermission>
    </c:if>
    <c:if test="${'7'.equals(examType)}">
        <shiro:hasPermission name="exam:examWorkflow:edit7">
            <li class="active"><a href="${ctx}/exam/examWorkflow?examType=${examType}">管理</a></li>
        </shiro:hasPermission>
    </c:if>
</ul>
<form:form id="searchForm" modelAttribute="examWorkflow" action="${ctx}/exam/examWorkflow?examType=${examType}"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>名称：</label>
            <form:input path="name" htmlEscape="false" class="input-medium"/>
        </li>
            <%--
            <li><label>流程启动时间：</label>
                <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                    value="<fmt:formatDate value="${examWorkflow.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </li>
            <li><label>流程结束时间：</label>
                <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                    value="<fmt:formatDate value="${examWorkflow.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            </li>
            --%>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <%--处领导 局领导无添加权限--%>
        <shiro:lacksRole name="nncld">
            <shiro:lacksRole name="leader">
                <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                        onclick="openForm('${ctx}/exam/examWorkflow/form?examType=${examType}')"/></li>
            </shiro:lacksRole>
        </shiro:lacksRole>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选
        </th>
        <th>名称</th>
        <th>考评类别</th>
        <th>考评周期</th>
        <th>考评对象</th>
        <th>状态</th>
        <th>更新时间</th>
        <%--				<shiro:hasPermission name="exam:examWorkflow:edit"><th>操作</th></shiro:hasPermission>--%>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="examWorkflow">
        <tr>
            <td>
                <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examWorkflow.id}"/>
            </td>
            <td>
                    ${examWorkflow.name}
            </td>
            <td>
                    ${fns:getDictLabel(examWorkflow.exam_type, "kp_type", "")}
            </td>
            <td>
                    ${fns:getDictLabel(examWorkflow.exam_cycle, "cycle", "")}
            </td>
            <td>
                    ${fns:getDictLabel(examWorkflow.exam_object_type, "exam_object_type", "")}
            </td>
            <td>
                    ${fns:getDictLabel(examWorkflow.status, "workflow_status", "")}
                    <%--
                        <c:if test="${null != examWorkflow.operationStatus}">
                            (${fns:getDictLabel(examWorkflow.operationStatus, "workflow_operation_status", "")})
                        </c:if>
                        --%>
            </td>
            <td>
                <fmt:formatDate value="${examWorkflow.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
                <%--				<shiro:hasPermission name="exam:examWorkflow:edit"><td>--%>
            <td>

<%--                <c:if test="${examWorkflow.status == 1 || examWorkflow.status == 3 || examWorkflow.status == 8}">--%>
                    <c:choose>
                        <c:when test="${examWorkflow.status == -1}">

                        </c:when>
                        <c:when test="${examWorkflow.status == 8}">
                            <a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${examWorkflow.id}&examType=${examType}&history=true&noTree=true" >查看环节详情</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/exam/examWorkflowDatas/examChuIndexBeta?examWorkflowId=${examWorkflow.id}&examType=${examType}&isAll=true" >查看环节详情</a>
                        </c:otherwise>
                    </c:choose>
<%--                </c:if>--%>
            </td>
                <%--				</td></shiro:hasPermission>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>