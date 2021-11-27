<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>抚恤审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setStatus(shStatus) {
            $("#status").val(shStatus);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairCasualtyReport" action="${ctx}/affair/affairCasualtyReport/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <div class="control-group">
        <label class="control-label">审核意见：</label>
        <div class="controls">
            <form:textarea path="opinion" htmlEscape="false" rows="6" class="input-xxlarge "/>
        </div>
    </div>
    <input id="status" name="shStatus" type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">
        <shiro:hasPermission name="affair:affairCasualtyReport:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="setStatus(1)"/>&nbsp;</shiro:hasPermission>
        <shiro:hasPermission name="affair:affairCasualtyReport:edit"><input id="btnSubmit2" class="btn btn-default" type="button" value="不通过" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>
    </div>
</form:form>
</body>
</html>