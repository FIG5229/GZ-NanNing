<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setCheckType(checkType) {
            if(3== checkType && (null == $("#juCheckId").val() || "" ==  $("#juCheckId").val())){
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
<form:form id="inputForm" modelAttribute="affairYjGoOutReport" action="${ctx}/affair/affairYjGoOutReport/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
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
    <br>
    <div class="controls" hidden>
        <label class="control-label">推送（选填）：</label>
        <form:select id="juCheckId" path="juCheckId"   class="input-xlarge required">
            <c:choose>
                <c:when test="${(fns:getUser().office.id eq '34' || fns:getUser().company.id eq '34') && fns:getUser().office.id ne '27'}">
                    <form:option value="34e8d855cf6b4b1ab5e7e23e7aaba658" label="南宁处政治处组织干部室"/>
                </c:when>
                <c:when test="${(fns:getUser().office.id eq '156' || fns:getUser().company.id eq '156') && fns:getUser().office.id ne '264'}">
                    <form:option value="c90918faf2614baa8fa85230482bd43e" label="北海处政治处组织干部室"/>
                </c:when>
                <c:when test="${(fns:getUser().office.id eq '95' || fns:getUser().company.id eq '95') && fns:getUser().office.id ne '142'}">
                    <form:option value="ec3ba2efdd404f2faa520f6e8a71ec4c" label="柳州处政治处组织干部室"/>
                </c:when>
                <c:when test="${(fns:getUser().office.id eq '1' || fns:getUser().company.id eq '1' || fns:getUser().office.id eq '27'|| fns:getUser().office.id eq '142'|| fns:getUser().office.id eq '264') && fns:getUser().office.id ne '95' && fns:getUser().office.id ne '156' && fns:getUser().office.id ne '34'}">
                    <form:option value="bfdf74f010c9466dba12c1589ecab7f3" label="南宁局政治部组织干部处"/>
                </c:when>
                <c:otherwise>
                    <form:option value="bfdf74f010c9466dba12c1589ecab7f3" label="南宁局政治部组织干部处"/>
                    <form:option value="34e8d855cf6b4b1ab5e7e23e7aaba658" label="南宁处政治处组织干部室"/>
                    <form:option value="ec3ba2efdd404f2faa520f6e8a71ec4c" label="柳州处政治处组织干部室"/>
                    <form:option value="c90918faf2614baa8fa85230482bd43e" label="北海处政治处组织干部室"/>
                </c:otherwise>
            </c:choose>
        </form:select>
        <input id="btnSubmit1" class="btn btn-primary" type="button" value="转送上一级" onclick="setCheckType(3)"/>&nbsp;
    </div>
    <input id="checkType" name="checkType"  type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="审核通过" onclick="setCheckType(4)"/>&nbsp;
        <input id="btnSubmit2" class="btn btn-default" type="button" value="退回整改" onclick="setCheckType(0)"/>&nbsp;
    </div>
</form:form>
</body>
</html>