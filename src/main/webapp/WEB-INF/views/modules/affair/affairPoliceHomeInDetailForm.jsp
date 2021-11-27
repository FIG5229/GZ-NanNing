<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>民警小家建设管理</title>
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
    </script>
</head>
<body>
<br>
<form:form id="inputForm" modelAttribute="affairPoliceHome" action="${ctx}/affair/affairPoliceHome/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">建设时间：</label>
        <div class="controls">
            <input name="pointDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" aria-readonly="true"
                   value="<fmt:formatDate value="${affairPoliceHome.pointDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">所属单位：</label>
        <div class="controls">
            <sys:treeselect id="unit" name="unitId" value="${affairPoliceHome.unitId}" labelName="unit" labelValue="${affairPoliceHome.unit}" disabled="disabled"
                            title="单位" url="/sys/office/treeData?type=1" cssClass="required" isAll="true" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">小家建设名称：</label>
        <div class="controls">
            <sys:treeselect id="pointUnit" name="pointUnitId" value="${affairPoliceHome.pointUnitId}" labelName="pointUnit" labelValue="${affairPoliceHome.pointUnit}"
                            disabled="disabled" title="单位" url="/sys/office/treeData?type=2" cssClass="required"  allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">建设项目：</label>
        <div class="controls">
            <form:select path="project" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_jsxm')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">所需设备：</label>
        <div class="controls">
            <form:input path="device" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">建设数量：</label>
        <div class="controls">
            <form:input path="nums" id="num" htmlEscape="false" class="input-xlarge  digits"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单价（元）：</label>
        <div class="controls">
            <form:input path="price" id="price" htmlEscape="false" class="input-xlarge  number "/>元
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">小计（元）：</label>
        <div class="controls">
            <form:input path="sum" id="sum" htmlEscape="false" class="input-xlarge  number" />元
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">内容：</label>
        <div class="controls">
            <form:input path="content" id="content" htmlEscape="false" class="input-xlarge " />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">经办人：</label>
        <div class="controls">
            <form:input path="jingBan" id="content" htmlEscape="false" class="input-xlarge " />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申报单位审核人：</label>
        <div class="controls">
            <form:input path="unitShRen" id="content" htmlEscape="false" class="input-xlarge " />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">处工会审核意见：</label>
        <div class="controls">
            <form:input path="chuShOpinion" id="content" htmlEscape="false" class="input-xlarge " />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">局工会审核意见：</label>
        <div class="controls">
            <form:input path="juShOpinion" id="content" htmlEscape="false" class="input-xlarge " />
        </div>
    </div>
<%--    <div class="control-group">
        <label class="control-label">附件：</label>
        <div class="controls">
            <form:hidden id="filePath" path="filePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:webuploader input="filePath" type="files" uploadPath="affair/affairPoliceHome" selectMultiple="true"/>
        </div>
    </div>--%>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairPoliceHome:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
    </div>

</form:form>
<script>
    if("success"=="${saveResult}"){
        parent.$.jBox.close();
    }
</script>
</body>
</html>