<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setSbType(sbType) {
            if(6== sbType && (null == $("#juCheckManName").val() || "" ==  $("#juCheckManName").val())){
                $.jBox.tip('请选择审核单位');
                return false;
            }
            $("#sbType").val(sbType);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="affairXcRewardDeclaration" action="${ctx}/affair/affairXcRewardDeclaration/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
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
    <div class="controls">
        <label class="control-label">推送（选填）：</label>
        <sys:treeselect id="juCheckMan" name="juCheckId" value="${affairXcRewardDeclaration.juCheckId}" labelName="juCheckMan" labelValue="${affairXcRewardDeclaration.juCheckMan}"
                        title="送审单位" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
        <input id="btnSubmit1" class="btn btn-primary" type="button" value="转送上一级" onclick="setSbType(6)"/>&nbsp;
    </div>
    <input id="sbType" name="sbType"  type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">
        <input id="btnSubmit" class="btn btn-primary" type="button" value="申报通过" onclick="setSbType(2)"/>&nbsp;
        <input id="btnSubmit3" class="btn btn-primary" type="button" value="退回整改" onclick="setSbType(3)"/>&nbsp;
        <input id="btnSubmit2" class="btn btn-default" type="button" value="不通过" onclick="setSbType(5)"/>&nbsp;
    </div>
</form:form>
</body>
</html>