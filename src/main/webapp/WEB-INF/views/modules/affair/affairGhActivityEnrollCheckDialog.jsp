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
<form:form id="inputForm" modelAttribute="affairGhActivityEnroll" action="${ctx}/affair/affairGhActivityEnroll/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
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
    <input id="checkType" name="checkType"  type="hidden"/>
    <c:choose>
        <c:when test="${fns:getUser().id.equals('21d95a5f60a741249921c82b0a69d6f3')||fns:getUser().id.equals('b91d9ac0c32847c4ab6f21e910959198')}">
            <div style="margin-left: 500px;margin-top: 70px;">
                <input id="btnSubmit1" class="btn btn-primary" type="button" value="通过并结束" onclick="setCheckType(4)"/>&nbsp;
                <input id="btnSubmit3" class="btn btn-default" type="button" value="不通过" onclick="setCheckType(0)"/>&nbsp;
            </div>

        </c:when>
        <c:otherwise>
            <div class="controls">
                <label class="control-label">转送上一级（选填）：</label>
                <form:select path="twoCheckId"  class="input-xlarge required">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('user_gonghui')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
                <input id="btnSubmit5" class="btn btn-primary" type="button" value="转送上一级" onclick="setCheckType(3)"/>&nbsp;
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