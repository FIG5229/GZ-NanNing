<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>工作纪实审核</title>
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
        function setStatus(status) {
            $("#checkType").val(status);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(result){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairWorkRecord" action="${ctx}/affair/affairWorkRecord/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <div class="control-group">
        <label class="control-label">单位负责人意见：</label>
        <div class="controls">
            <form:textarea path="unitPrincipalOpinion" htmlEscape="false" rows="6" class="input-xxlarge"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">评价：</label>
        <div class="controls">
            <form:select path="evaluate" class="input-medium">
                <form:options items="${fns:getDictList('work_record_evaluate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font></span>
        </div>
    </div>
    <input id="checkType" name="checkType" type="hidden"/>
    <div style="margin-left: 500px;margin-top: 30px;">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="setStatus(4)"/>&nbsp;
        <input id="btnSubmit2" class="btn btn-default" type="button" value="退回整改" onclick="setStatus(0)"/>&nbsp;
    </div>
</form:form>
</body>
</html>