<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setState(state) {
            $("#state").val(state);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairSpiritualReview" action="${ctx}/affair/affairSpiritualReview/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <input id="state" name="state"  type="hidden"/>
    <shiro:hasPermission name="affair:affairSpiritualReview:manage">
    <c:choose>
        <c:when test="${fns:getUser().id.equals('ff7f9fe2597b40429ded58f8b76a2f65')||fns:getUser().id.equals('45e43fc5465d4dbf849c6ec50609ecf4')}">
            <div style="margin-left: 500px;margin-top: 70px;">
                <input id="btnSubmit" class="btn btn-primary" type="button" value="通过并结束" onclick="setState(3)"/>&nbsp;

                <input id="btnSubmit1" class="btn btn-default" type="button" value="不通过" onclick="setState(4)"/>&nbsp;
            </div>
        </c:when>
        <c:otherwise>
            <div style="margin-left: 500px;margin-top: 70px;">
                <input id="btnSubmit3" class="btn btn-primary" type="button" value="通过并结束" onclick="setState(3)"/>&nbsp;

                <input id="btnSubmit4" class="btn btn-default" type="button" value="不通过" onclick="setState(4)"/>&nbsp;
            </div>
        </c:otherwise>
    </c:choose>
    </shiro:hasPermission>
</form:form>
</body>
</html>