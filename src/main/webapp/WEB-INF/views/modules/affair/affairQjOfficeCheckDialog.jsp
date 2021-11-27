<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setStatus(status) {
            $("#status").val(status);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairQj" action="${ctx}/affair/affairQj/leaderShenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <div class="control-group">
        <label class="control-label">审核意见：</label>
        <div class="controls">
            <form:textarea path="leaderOpinion" htmlEscape="false" rows="6" class="input-xxlarge "/>
        </div>
    </div>
    <input id="status" name="status" type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">
        <shiro:hasPermission name="affair:affairQj:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="setStatus(8)"/>&nbsp;</shiro:hasPermission>
        <shiro:hasPermission name="affair:affairQj:edit"><input id="btnSubmit2" class="btn btn-default" type="button" value="不通过" onclick="setStatus(9)"/>&nbsp;</shiro:hasPermission>
    </div>
</form:form>
</body>
</html>