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
                <shiro:lacksRole name="nncld">
                    <shiro:lacksRole name="leader">
                    <a onclick="openForm('${ctx}/exam/examWorkflow/qingkuang?id=${examWorkflow.id}&examType=${examType}')"
                       href="javascript:void(0)">查看</a>
                    <%--
                <c:if test="${examWorkflow.createBy.id == fns:getUser().id}"></c:if>--%>
                    <a href="javascritp:void(0)"
                       onclick="openForm('${ctx}/exam/examWorkflow/form?id=${examWorkflow.id}&examType=${examType}')">修改</a>
                    <a href="${ctx}/exam/examWorkflow/delete?id=${examWorkflow.id}"
                       onclick="return confirmx('确认要删除该考评管理吗？', this.href)">删除</a>
                    <c:if test="${-1 eq examWorkflow.status}">
                        <a href="javascript:;" onclick="startWorkflow('确认要启动该工作吗?(该工作启动稍慢，请等候大约5分钟)','${ctx}/exam/examWorkflow/start?id=${examWorkflow.id}&examType=${examType}')">启动</a>
                    </c:if>
                    <c:if test="${3 eq examWorkflow.nextStatus}">
                        <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}"
                           onclick="return confirmx('确认要系统公示吗？', this.href)">系统公示</a>
                    </c:if>
                    <c:if test="${3 eq examWorkflow.status && 8 != examWorkflow.nextStatus}">
                        <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}"
                           onclick="return confirmx('确认要结束系统公示吗？', this.href)">结束系统公示</a>
                    </c:if>
                    <c:if test="${8 eq examWorkflow.nextStatus}">
                        <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}&examCycle=${examWorkflow.exam_cycle}"
                           onclick="return confirmx('确认要公示最终结果吗？', this.href)">最终结果公示</a>
                    </c:if>
                    <%--<c:if test="${8 eq examWorkflow.nextStatus && '2'.equals(examWorkflow.exam_cycle)}">
                        <a href="${ctx}/exam/examLdScore/list?workflowId=${examWorkflow.id}">等次</a>
                    </c:if>--%>
                    <c:if test="${8 eq examWorkflow.status && '2'.equals(examWorkflow.exam_cycle)}">
                        <a href="javascript:;" onclick="openGradesListDialog('${examWorkflow.id}')">等次</a>
                    </c:if>
                    <c:if test="${8 eq examWorkflow.status}">
                        <%-- <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}"
                            onclick="return confirmx('最终结果公示吗？', this.href)">结束最终结果公示</a>--%>
                    </c:if>
                    <c:if test="${1 eq examWorkflow.status}">
                        <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}"
                           onclick="return confirmx('确认要结束自评吗？', this.href)">结束自评</a>
                    </c:if>
                    <c:if test="${4 eq examWorkflow.status && examWorkflow.nextStatus != 8}">
                        <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}"
                           onclick="return confirmx('确认要结束部门负责人签字吗？', this.href)">结束部门负责人签字</a>
                    </c:if>
                    <c:if test="${5 eq examWorkflow.status && examWorkflow.nextStatus != 8}">
                        <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}"
                           onclick="return confirmx('确认要结束分管局领导吗？', this.href)">结束分管局领导</a>
                    </c:if>
                    <c:if test="${6 eq examWorkflow.status && examWorkflow.nextStatus != 8}">
                        <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}"
                           onclick="return confirmx('确认要结束绩效办调整吗？', this.href)">结束绩效办调整</a>
                    </c:if>
                    <c:if test="${7 eq examWorkflow.status && examWorkflow.nextStatus != 8}">
                        <a href="${ctx}/exam/examWorkflow/updateStatus?id=${examWorkflow.id}&examType=${examType}"
                           onclick="return confirmx('确认要结束局领导审核吗？', this.href)">结束局领导审核</a>
                    </c:if>

                    <shiro:hasAnyPermissions name="exam:examWorkflow:edit1,exam:examWorkflow:edit2,exam:examWorkflow:edit3,exam:examWorkflow:edit4,exam:examWorkflow:edit5,exam:examWorkflow:edit6,exam:examWorkflow:edit7">
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
                    </shiro:hasAnyPermissions>
                        <a href="javaScript:;" onclick="examDetail('${examWorkflow.id}')">查看待初核人员</a>
                    <%--<c:if test="${fns:getUser().id eq '46c521bf67e24db28772b3eac52dc454' || fns:getUser().id eq '6af0e615e9b0477da82eff06ff532c8b' ||
                    fns:getUser().id eq '978958003ea44a4bba3eed8ee6ceff3c' || fns:getUser().id eq 'cca66e1339f14799b01f6db43ed16e16'}">
                        <a href="${ctx}/exam/examWorkflowDatas/examChuIndexBeta?examWorkflowId=${examWorkflow.id}&examType=${examType}" >查看环节详情</a>
                    </c:if>--%>

                </shiro:lacksRole>
                </shiro:lacksRole>

                <%--处领导 局领导只有查看详情权限--%>
            <shiro:hasAnyRoles name="nncld,leader">
                <c:choose>
                    <c:when test="${examWorkflow.status == -1}">

                    </c:when>
                    <c:when test="${examWorkflow.status == 8}">
                        <a href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${examWorkflow.id}&examType=${examType}&history=true&noTree=true&isAll=true" >查看环节详情</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${ctx}/exam/examWorkflowDatas/examChuIndexBeta?examWorkflowId=${examWorkflow.id}&examType=${examType}&isAll=true" >查看环节详情</a>
                    </c:otherwise>
                </c:choose>
            </shiro:hasAnyRoles>
            </td>
                <%--				</td></shiro:hasPermission>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>