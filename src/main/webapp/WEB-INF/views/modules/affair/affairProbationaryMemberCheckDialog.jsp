<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>预备党员审核</title>
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
            $("#status").val(status);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(result){
                window.parent.window.jBox.close();
            });
            /*	var form = document.getElementById('inputForm');
                form.submit();*/
            //window.parent.window.jBox.close();
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairProbationaryMember" action="${ctx}/affair/affairProbationaryMember/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <input id="status" name="status" type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">
        <shiro:hasPermission name="affair:affairProbationaryMember:manage"><input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
        <shiro:hasPermission name="affair:affairProbationaryMember:manage"><input id="btnSubmit2" class="btn btn-default" type="button" value="不通过" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
    </div>
</form:form>
</body>
</html>