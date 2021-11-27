<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>团委通知通报管理</title>
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

        function setStatus(status) {
            $("#status").val(status);
        };
        if ("sucess" == "${saveResult}") {
            parent.$.jBox.close();
        }
    </script>
</head>
<body>

<br/>
<%--@elvariable id="affairFileNotice" type="com.thinkgem.jeesite.modules.affair.entity.AffairFileNotice"--%>
<form:form id="inputForm" modelAttribute="affairFileNotice" action="${ctx}/affair/affairFileNotice/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <input id="flag" name="flag" type="hidden" value="${affairFileNotice.flag}"/>
    <div class="control-group">
        <label class="control-label">标题：</label>
        <div class="controls">
            <form:input path="title" htmlEscape="false" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <%-- <div class="control-group">
         <label class="control-label">发布部门：</label>
         <div class="controls">
             <sys:treeselect id="publishDep" name="publishOrgId" value="${affairFileNotice.publishOrgId}" labelName="publishDep" labelValue="${affairFileNotice.publishDep}"
                 title="发布部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
             <span class="help-inline"><font color="red">*</font> </span>
         </div>
     </div>--%>
    <div class="control-group">
        <label class="control-label">发文类型：</label>
        <div class="controls">
            <form:select path="type" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_tw_file_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">接收部门：</label>
        <div class="controls">
            <sys:treeselect id="receiveDep" name="receiveDepId" value="${affairFileNotice.receiveDepId}"
                            labelName="receiveDep" labelValue="${affairFileNotice.receiveDep}"
                            title="接收部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                            notAllowSelectParent="true" checked="true" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否推送到首页：</label>
        <div class="controls">
            <form:checkboxes id="isPush" path="isPush" items="${fns:getDictList('warn_yes')}" itemLabel="label"
                             itemValue="value" htmlEscape="false"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主要内容：</label>
        <div class="controls">
            <form:textarea id="content" htmlEscape="true" path="content" rows="4" class="input-xxlarge"/>
            <sys:ckeditor replace="content"/>
        </div>
    </div>
    <%--发布日期暂时用更新日期，不让手填--%>
    <%--<div class="control-group">
        <label class="control-label">发布日期：</label>
        <div class="controls">
            <input name="publishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                   value="<fmt:formatDate value="${affairFileNotice.publishDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>--%>
    <div class="control-group">
        <label class="control-label">附件：</label>
        <div class="controls">
            <form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:webuploader input="filePath" type="files" uploadPath="affair/affairFileNotice" selectMultiple="true"/>
        </div>
    </div>
    <input id="status" name="status" type="hidden"/>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairFileNotice:manage3">
            <%-- 一旦发布不可再操作--%>
            <c:if test="${affairFileNotice.status != '2'}">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="setStatus(1)"/>
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="发 布" onclick="setStatus(2)"/>
            </c:if>
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
    </div>
</form:form>
</body>
</html>