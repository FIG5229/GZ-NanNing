<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>党员民主评议管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        if("sucess"=="${saveResult}"){
            parent.$.jBox.close();
        }
    </script>
</head>
<body>
<br/>
<form:form id="inputForm" modelAttribute="affairComment" action="${ctx}/affair/affairComment/saveComment?treeId=${treeId}" method="post" class="form-horizontal">
    <input id="treeId" name="treeId" type="hidden" value="${treeId}"/>
    <div class="control-group">
        <label class="control-label">评议年度：</label>
        <div class="controls">
            <input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                   value=""
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:true});"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">评议时间：</label>
        <div class="controls">
            <input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                   value="<fmt:formatDate value="${affairComment.date}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">参加评议人数：</label>
        <div class="controls">
            <form:input path="personNum" htmlEscape="false" class="input-xlarge  digits"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">评议结果：</label>
        <div class="controls">
            <form:select path="grade" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_comment_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="result" htmlEscape="false" rows="4" class="input-xxlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairComment:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
    </div>
</form:form>
</body>
</html>