<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setCheckType(checkType) {
            if(3== checkType && (null == $("#twoCheckId").val() || "" ==  $("#twoCheckId").val())){
                $.jBox.tip('请选择审核单位');
                return false;
            }
            $("#checkType").val(checkType);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairPushParty" action="${ctx}/affair/affairPushParty/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <div class="control-group">
        <label class="control-label">团委意见：</label>
        <div class="controls">
            <form:textarea path="tuanweiOpinion" htmlEscape="false" rows="6" class="input-xxlarge "/>
        </div>
    </div>
    <input id="checkType" name="checkType"  type="hidden"/>
    <c:choose>
        <c:when test="${fns:getUser().id.equals('ff7f9fe2597b40429ded58f8b76a2f65')||fns:getUser().id.equals('45e43fc5465d4dbf849c6ec50609ecf4')}">
            <div style="margin-left: 500px;margin-top: 70px;">
                <input id="btnSubmit" class="btn btn-primary" type="button" value="通过并结束" onclick="setCheckType(4)"/>&nbsp;
                <input id="btnSubmit3" class="btn btn-default" type="button" value="不通过" onclick="setCheckType(0)"/>&nbsp;
            </div>
        </c:when>
        <c:otherwise>
            <div class="controls">
                <label class="control-label">转送上一级（选填）：</label>
                <form:select path="twoCheckId"  class="input-xlarge required">
                    <c:choose>
                        <c:when test="${(fns:getUser().office.id ne '34' && fns:getUser().company.id eq '34' && fns:getUser().office.id ne '33')}">
                            <form:option value="28f59642a1e74d0588f0d515fe462775" label="南宁处团委"/>
                            <%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 10月28--%>
                        </c:when>
                        <c:when test="${(fns:getUser().office.id ne '156' && fns:getUser().company.id eq '156' && fns:getUser().office.id ne '268')}">
                            <form:option value="78d0e07ed2e14ca0b6c73e14c11f4d55" label="北海处团委"/>
                            <%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 10月28--%>
                        </c:when>
                        <c:when test="${(fns:getUser().office.id ne '95' && fns:getUser().company.id eq '95' && fns:getUser().office.id ne '148')}">
                            <form:option value="11d94fe57ede47a9bae4bffa36af487c" label="柳州处团委"/>
                            <%--<form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/> 10月28--%>
                        </c:when>
                        <c:when test="${(fns:getUser().office.id eq '1' || fns:getUser().company.id eq '1' || fns:getUser().office.id eq '148'|| fns:getUser().office.id eq '268'|| fns:getUser().office.id eq '33' || fns:getUser().office.id eq '95' || fns:getUser().office.id eq '156' || fns:getUser().office.id eq '34')}">
                            <form:option value="ff7f9fe2597b40429ded58f8b76a2f65" label="南宁局团委"/>
                        </c:when>
                        <c:otherwise>
                            <form:options items="${fns:getDictList('user_tuanwei')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </c:otherwise>
                    </c:choose>
                    <%-- <form:option value="" label=""/>
                     <form:options items="${fns:getDictList('user_tuanwei')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                </form:select>
                <input id="btnSubmit1" class="btn btn-primary" type="button" value="转送上一级" onclick="setCheckType(3)"/>&nbsp;
            </div>
            <div style="margin-left: 500px;margin-top: 70px;">
                <input id="btnSubmit4" class="btn btn-primary" type="button" value="通过并结束" onclick="setCheckType(4)"/>&nbsp;

                <input id="btnSubmit2" class="btn btn-default" type="button" value="不通过" onclick="setCheckType(0)"/>&nbsp;
            </div>
        </c:otherwise>
    </c:choose>
</form:form>
</body>
</html>