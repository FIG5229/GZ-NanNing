<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>转办</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setStatus(zbStatus) {
            $("#zbStatus").val(zbStatus);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairTousujubaoguanli" action="${ctx}/affair/affairTousujubaoguanli/zhuanban" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">转办单位：</label>
        <div class="controls">
            <sys:treeselect id="zbUnit" name="zbUnitId" value="${affairTousujubaoguanli.zbUnitId}" labelName="zbUnit" labelValue="${affairTousujubaoguanli.zbUnit}"
                            title="转办单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="false"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <input id="zbStatus" name="zbStatus" type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="通 过" onclick="setStatus(1)"/>
<%--        <shiro:hasPermission name="affair:affairTousujubaoguanli:edit"><input id="btnSubmit2" class="btn btn-default" type="button" value="不通过" onclick="setStatus(2)"/>&nbsp;</shiro:hasPermission>--%>
    </div>
</form:form>
</body>
