<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setStatus(shType) {
            $("#shType").val(shType);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairGz" action="${ctx}/affair/affairGz/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <div class="control-group">
        <label class="control-label">审核意见：</label>
        <div class="controls">
            <form:textarea path="shOpinion" htmlEscape="false" rows="6" class="input-xxlarge "/>
        </div>
    </div>
    <input id="shType" name="shType"  type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">
       <input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="setStatus(1)"/>&nbsp;
       <input id="btnSubmit2" class="btn btn-default" type="button" value="不通过" onclick="setStatus(2)"/>&nbsp;
    </div>
</form:form>
</body>
</html>