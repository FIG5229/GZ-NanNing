<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>请假信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
<br/>
<form:form id="inputForm" modelAttribute="affairQj" action="${ctx}/affair/affairQj/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">姓名：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" class="input-xlarge " readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位：</label>
        <div class="controls">
            <sys:treeselect id="unit" name="unitId" value="${affairQj.unitId}" labelName="unit" labelValue="${affairQj.unit}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true" disabled="disabled" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">部门：</label>
        <div class="controls">
            <sys:treeselect id="department" name="departmentId" value="${affairQj.departmentId}" labelName="department" labelValue="${affairQj.department}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true" disabled="disabled"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">职务：</label>
        <div class="controls">
            <form:input path="job" htmlEscape="false" class="input-xlarge " readonly="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">请假日期：</label>
        <div class="controls">
            <input name="qjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairQj.qjDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" disabled="disabled"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">假别：</label>
        <div class="controls">
            <form:select path="type" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('personnel_xjtype')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">开始时间：</label>
        <div class="controls">
            <input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairQj.startTime}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" disabled="disabled"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">结束时间：</label>
        <div class="controls">
            <input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairQj.endTime}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" disabled="disabled"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">实际休假天数：</label>
        <div class="controls">
            <form:input path="qjDay" htmlEscape="false" class="input-xlarge" disabled="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">请假备注：</label>
        <div class="controls">
            <form:textarea path="qjRemark" htmlEscape="false" class="input-xlarge " rows="4" disabled="true"/>
        </div>
    </div>
    <%--<div class="control-group">
        <label class="control-label">审核状态：</label>
        <div class="controls">
            <form:input path="status" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">部门意见：</label>
        <div class="controls">
            <form:input path="depOpinion" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">人事意见：</label>
        <div class="controls">
            <form:input path="hrOpinion" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">局、处领导意见：</label>
        <div class="controls">
            <form:input path="leaderOpinion" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>--%>
    <div class="control-group">
        <label class="control-label">销假日期：</label>
        <div class="controls">
            <input name="xjDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                value="<fmt:formatDate value="${affairQj.xjDate}" pattern="yyyy-MM-dd"/>"
                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">恢复工作时间：</label>
        <div class="controls">
            <input name="resumeTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                value="<fmt:formatDate value="${affairQj.resumeTime}" pattern="yyyy-MM-dd"/>"
                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">提前归还或延长之日数：</label>
        <div class="controls">
            <form:input path="ghycDay" htmlEscape="false" class="input-xlarge " />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">销假备注：</label>
        <div class="controls">
            <form:input path="xjRemark" htmlEscape="false" class="input-xlarge " />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">销假说明：</label>
        <div class="controls">
            <form:input path="explain" htmlEscape="false" class="input-xlarge " />
        </div>
    </div>
    <form:hidden path="status" value="10"/>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairQj:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
    </div>
</form:form>
<script>
    if("success"=="${saveResult}"){
        parent.$.jBox.close();
    }
</script>
</body>
</html>