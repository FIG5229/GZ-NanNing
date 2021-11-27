<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>教育训练培训模块管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<%--	<ul class="nav nav-tabs">--%>
<%--		<li><a href="${ctx}/affair/affairEducationTrain/">教育训练培训模块列表</a></li>--%>
<%--		<li class="active"><a href="${ctx}/affair/affairEducationTrain/form?id=${affairEducationTrain.id}">教育训练培训模块<shiro:hasPermission name="affair:affairEducationTrain:edit">${not empty affairEducationTrain.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairEducationTrain:edit">查看</shiro:lacksPermission></a></li>--%>
<%--	</ul><br/>--%>
<form:form id="inputForm" modelAttribute="affairEducationTrain" action="${ctx}/affair/affairEducationTrain/save"
           method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <br>
    <div class="control-group">
        <label class="control-label">培训年度：</label>
        <div class="controls">
            <input name="trainYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${affairEducationTrain.trainYear}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">填报机构：</label>
        <div class="controls">
                <%--            <form:input path="unit" htmlEscape="false" class="input-xlarge "/>--%>
            <sys:treeselect id="unit" name="unitId" value="${affairEducationTrain.unitId}" labelName="unit"
                            labelValue="${affairEducationTrain.unit}"
                            title="填报机构" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                            notAllowSelectParent="false" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">标题：</label>
        <div class="controls">
            <form:input path="title" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训名称：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainName" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">填报人：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="informant" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训班类型：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainType" htmlEscape="false" class="input-xlarge "/>--%>
<%--            <form:select path="trainType" class="input-xlarge required">--%>
<%--                <form:option value="" label=""/>--%>
<%--                <form:options items="${fns:getDictList('affair_benefits')}" itemLabel="label" itemValue="value"--%>
<%--                              htmlEscape="false"/>--%>
<%--            </form:select>--%>
<%--            <span class="help-inline"><font color="red">*</font> </span>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训层次：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainLevel" htmlEscape="false" class="input-xlarge "/>--%>
<%--            <form:select path="trainLevel" class="input-xlarge required">--%>
<%--                <form:option value="" label=""/>--%>
<%--                <form:options items="${fns:getDictList('affair_benefits')}" itemLabel="label" itemValue="value"--%>
<%--                              htmlEscape="false"/>--%>
<%--            </form:select>--%>
<%--            <span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训时间：</label>
        <div class="controls">
            <input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairEducationTrain.trainDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">填报开始时间：</label>
        <div class="controls">
            <input name="beganDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairEducationTrain.beganDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">填报结束时间：</label>
        <div class="controls">
            <input name="resultDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairEducationTrain.resultDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">最后一次填报日期：</label>
        <div class="controls">
            <input name="lastDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairEducationTrain.lastDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </div>
    </div>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训天数：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainDay" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训人数：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainCount" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训对象：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainObject" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训方式：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainWay" htmlEscape="false" class="input-xlarge "/>--%>
<%--&lt;%&ndash;            <form:select path="trainWay" class="input-xlarge required">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <form:option value="" label=""/>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <form:options items="${fns:getDictList('affair_benefits')}" itemLabel="label" itemValue="value"&ndash;%&gt;--%>
<%--&lt;%&ndash;                              htmlEscape="false"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </form:select>&ndash;%&gt;--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训场地：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainSite" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">实施状态：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="implementStatus" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训费：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainFees" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">师资费：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="teacherFees" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">预算给用：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="budget" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训目的：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="trainPurpose" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">列支渠道：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="listOfChannel" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训地点：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="site" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">培训内容：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">审核人：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="reviewer" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">审批时间：</label>--%>
<%--        <div class="controls">--%>
<%--            <input name="approveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "--%>
<%--                   value="<fmt:formatDate value="${affairEducationTrain.approveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
<%--                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">审批层级：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="approveLevel" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">审批结果：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="approveResult" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">审批状态：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="approveStatus" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
    <div class="control-group">
        <label class="control-label">已填报的计划培训班总数：</label>
        <div class="controls">
            <form:input path="filledClassCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">已审批通过的计划培训班数量：</label>
        <div class="controls">
            <form:input path="approvedClassCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">已审批通过的计划培训总人数：</label>
        <div class="controls">
            <form:input path="approvedCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">未完成审批的计划培训班数量：</label>
        <div class="controls">
            <form:input path="incompleteApprovalCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
<%--    <div class="control-group">--%>
<%--        <label class="control-label">单位id：</label>--%>
<%--        <div class="controls">--%>
<%--            <form:input path="unitId" htmlEscape="false" class="input-xlarge "/>--%>
<%--        </div>--%>
<%--    </div>--%>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairEducationTrain:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                            type="submit"
                                                                            value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick=" parent.$.jBox.close();"/>
    </div>
</form:form>
<script>
    if("success"=="${saveResult}"){
        parent.$.jBox.close();
    }
</script>
</body>
</html>