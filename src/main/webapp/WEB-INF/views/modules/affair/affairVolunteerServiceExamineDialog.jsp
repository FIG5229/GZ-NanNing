<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setCheckType(checkType) {
            $("#checkType").val(checkType);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairVolunteerService" action="${ctx}/affair/affairVolunteerService/examine" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <div class="control-group">
        <label class="control-label">整改意见：</label>
        <div class="controls">
            <form:textarea path="opinion" htmlEscape="false" rows="6" class="input-xxlarge "/>
        </div>
    </div>
    <input id="checkType" name="examineStatus"  type="hidden"/>
            <div style="margin-left: 500px;margin-top: 70px;">
                <input id="btnSubmit3" class="btn btn-primary" type="button" value="通过并结束" onclick="setCheckType(3)"/>&nbsp;

                <input id="btnSubmit4" class="btn btn-default" type="button" value="不通过" onclick="setCheckType(4)"/>&nbsp;
            </div>

</form:form>
</body>
</html>