<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>检查报送</title>
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
<form:form id="inputForm" modelAttribute="examCheck1" action="${ctx}/exam/examCheck1/save" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">整改情况：</label>
        <div class="controls">
            <form:select path="reviewType" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('exam_rectify')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">整改情况说明：</label>
        <div class="controls">
            <form:textarea path="explan" htmlEscape="false" rows="6" class="input-xxlarge "/>
        </div>
    </div>
    <input id="status" name="status" type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
    </div>
</form:form>

<script>
    if("success"=="${saveResult}"){
        parent.$.jBox.close();
    }
</script>
</body>
</html>