<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>医疗保险管理</title>
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
<form:form id="inputForm" modelAttribute="affairMedicalInsurance" action="${ctx}/affair/affairMedicalInsurance/generate" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <br>
    <div class="control-group">
        <label class="control-label">时间：</label>
        <div class="controls">
            <input name="timeYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                   value=  ${affairMedicalInsurance.timeYear}""
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
            <span class="help-inline"><font color="red">*</font></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">个人缴费比例：</label>
        <div class="controls">
            <form:input path="individualProportion" htmlEscape="false" class="input-xlarge required"/>%
            <span class="help-inline"><font color="red">*</font> </span>

        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位缴费比例：</label>
        <div class="controls">
            <form:input path="unitProportion" htmlEscape="false" class="input-xlarge  required"/>%
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">月个账划入比例：</label>
        <div class="controls">
            <form:input path="monthAccount" htmlEscape="false" class="input-xlarge  required"/>%
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">补充资金月个账划入比例：</label>
        <div class="controls">
            <form:input path="addition" htmlEscape="false" class="input-xlarge required "/>%
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">年度补助比例(小于等于45岁)：</label>
        <div class="controls">
            <form:input path="annualProportion" htmlEscape="false" class="input-xlarge  required"/>%
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">年度补助比例(大于45岁)：</label>
        <div class="controls">
            <form:input path="annualProportionOver" htmlEscape="false" class="input-xlarge  required"/>%
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">全区公务员平均月工资：</label>
        <div class="controls">
            <form:input path="averageSalary" htmlEscape="false" class="input-xlarge  required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">缴费基数上限：</label>
        <div class="controls">
            <form:input path="maxNumber" htmlEscape="false" class="input-xlarge   required"/>
            <span class="help-inline"><font color="red">*</font></span>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairMedicalInsurance:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
    </div>
</form:form>
</body>
</html>