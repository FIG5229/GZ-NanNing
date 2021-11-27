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
<form:form id="inputForm" modelAttribute="affairTemporaryPolice" action="${ctx}/affair/affairTemporaryPolice/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input type="hidden" name="ct" value="${temporaryPolice.checkType}">
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
        <c:if test="${temporaryPolice.checkType == '1'}">
            <div class="control-group">
                <label class="control-label">理由：</label>
                <form:textarea path="chuOpinion" htmlEscape="false" class="input-xlarge " rows="4"/>
            </div>
        </c:if>
       <c:if test="${temporaryPolice.checkType == '2'}">
           <div class="control-group">
               <label class="control-label">理由：</label>
               <form:textarea path="juOpinion" htmlEscape="false" class="input-xlarge " rows="4"/>
           </div>
       </c:if>
        <c:if test="${temporaryPolice.checkType == '3'|| temporaryPolice.checkType == '8' || temporaryPolice.checkType == '9'}">
            <div class="control-group">
                <label class="control-label">理由：</label>
                <form:textarea path="juLdOpinion" htmlEscape="false" class="input-xlarge " rows="4"/>
            </div>
        </c:if>
    <input id="checkType" name="checkType"  type="hidden"/>
    <c:if test="${temporaryPolice.checkType == '1'}">
        <div style="margin-left: 500px;margin-top: 70px;">
            <input id="btnSubmit3" class="btn btn-primary" type="button" value="通过" onclick="setCheckType(5)"/>&nbsp;
            <input id="btnSubmit4" class="btn btn-primary" type="button" value="不通过" onclick="setCheckType(4)"/>&nbsp;
        </div>
    </c:if>
    <c:if test="${temporaryPolice.checkType == '2'}">
        <div style="margin-left: 500px;margin-top: 70px;">
            <input id="btnSubmit5" class="btn btn-primary" type="button" value="通过" onclick="setCheckType(6)"/>&nbsp;
            <input id="btnSubmit6" class="btn btn-primary" type="button" value="不通过" onclick="setCheckType(7)"/>&nbsp;
        </div>
    </c:if>
    <c:if test="${temporaryPolice.checkType == '3' }">
        <div style="margin-left: 500px;margin-top: 70px;">
            <input id="btnSubmit7" class="btn btn-primary" type="button" value="通过" onclick="setCheckType(11)"/>&nbsp;
            <input id="btnSubmit8" class="btn btn-primary" type="button" value="不通过" onclick="setCheckType(9)"/>&nbsp;
        </div>
    </c:if>


</form:form>
</body>
</html>