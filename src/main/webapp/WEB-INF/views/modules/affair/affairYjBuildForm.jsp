<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>硬件建设管理</title>
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
<%--<ul class="nav nav-tabs">
    <li><a href="${ctx}/affair/affairYjBuild/">硬件建设列表</a></li>
    <li class="active"><a href="${ctx}/affair/affairYjBuild/form?id=${affairYjBuild.id}">硬件建设<shiro:hasPermission
            name="affair:affairYjBuild:edit">${not empty affairYjBuild.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="affair:affairYjBuild:edit">查看</shiro:lacksPermission></a></li>
</ul>--%>
<br/>
<form:form id="inputForm" modelAttribute="affairYjBuild" action="${ctx}/affair/affairYjBuild/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">项目名称：</label>
        <div class="controls">
            <form:input path="proName" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">建设时间：</label>
        <div class="controls">
            <input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairYjBuild.date}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">建设内容：</label>
        <div class="controls">
            <form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="150" class="input-xxlarge"/>
            <sys:ckeditor replace="content" uploadPath="affair/affairYjBuild" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">建设单位：</label>
        <div class="controls">
            <form:input path="buildUnit" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">附件：</label>
        <div class="controls">
            <form:hidden id="appendfile" path="appendfile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:webuploader input="appendfile" type="files" uploadPath="affair/affairBuild" selectMultiple="true"/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairYjBuild:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                     type="submit"
                                                                     value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
    </div>
</form:form>
<script>
    if ("success" == "${saveResult}") {
        parent.$.jBox.tip("保存成功");
        parent.$.jBox.close();
    }
</script>
</body>
</html>