<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setCheckType(checkType) {
            /*不需要转送上一级*/
            /*if(3== checkType && (null == $("#checkId").val() || "" ==  $("#checkId").val())){
                $.jBox.tip('请选择审核单位');
                return false;
            }*/
            $("#checkType").val(checkType);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairDeclarationQiyi" action="${ctx}/affair/affairDeclarationQiyi/examine" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <div class="control-group">
        <label class="control-label">整改意见：</label>
        <div class="controls">
            <form:textarea path="shOpinion" htmlEscape="false" rows="6" class="input-xxlarge "/>
        </div>
    </div>
    <input id="checkType" name="checkType"  type="hidden"/>
    <c:choose>
        <%--之前复制的  流程走不到这里--%>
        <c:when test="${fns:getUser().id.equals('ff7f9fe2597b40429ded58f8b76a2f65')||fns:getUser().id.equals('45e43fc5465d4dbf849c6ec50609ecf4')}">
            <div style="margin-left: 500px;margin-top: 70px;">
                <input id="btnSubmit" class="btn btn-primary" type="button" value="通过并结束" onclick="setCheckType(4)"/>&nbsp;

                <input id="btnSubmit1" class="btn btn-default" type="button" value="不通过" onclick="setCheckType(0)"/>&nbsp;
            </div>
        </c:when>
        <c:otherwise>
<%--            <div class="controls">--%>
<%--                <label class="control-label">转送上一级（选填）：</label>--%>
<%--                <form:select path="checkId"  class="input-xlarge required">--%>
<%--                    <form:option value="" label=""/>--%>
<%--                    <form:options items="${fns:getDictList('job_declaration')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
<%--                </form:select>--%>
<%--                <input id="btnSubmit2" class="btn btn-primary" type="button" value="转送上一级" onclick="setCheckType(3)"/>&nbsp;--%>
<%--            </div>--%>
            <div style="margin-left: 500px;margin-top: 70px;">
                <input id="btnSubmit3" class="btn btn-primary" type="button" value="通过并结束" onclick="setCheckType(3)"/>&nbsp;

                <input id="btnSubmit4" class="btn btn-default" type="button" value="不通过" onclick="setCheckType(4)"/>&nbsp;
            </div>
        </c:otherwise>
    </c:choose>
</form:form>
</body>
</html>