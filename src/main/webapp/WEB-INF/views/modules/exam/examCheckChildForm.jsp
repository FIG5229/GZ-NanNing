<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>问题整改</title>
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
<br/>
<form:form id="inputForm" modelAttribute="examCheckChild" action="${ctx}/exam/examCheckChild/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">整改情况：</label>
        <div class="controls">
            <textarea name="reorganizeInfo" class="required">${examCheckChild.reorganizeInfo}</textarea>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">附件：</label>
        <div class="controls">
            <form:hidden id="appendfile" path="appendfile" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:webuploader input="appendfile" type="files" uploadPath="exam/examCheckChild"
                             selectMultiple="true"/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="exam:examCheck:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
    </div>
    <script>
        if("success"=="${saveResult}"){
            parent.$.jBox.close();
        }
    </script>
</form:form>
</body>
</html>