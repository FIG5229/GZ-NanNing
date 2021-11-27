<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>入党申请人管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        if ("sucess" == "${saveResult}") {
            parent.$.jBox.close();
        }
        function setDefaults() {
            //清空
            $("#idNumber").val('');
            $("#policeNum").val('');
            $("#sex").val('');
            $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
            $("#job").val('');
            $("#nation").val('');
            $('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
            $.ajax({
                type:"post",
                url:"${ctx}/personnel/personnelBase/getPersonByName",
                data:{name:$("#name").val()},
                dataType:"json",
                success:function(data){
                    if(data.success==true && data.result.length==1){
                        $("#idNumber").val(data.result[0].idNumber);
                        $("#policeNum").val(data.result[0].policeIdNumber);
                        $("#sex").val(data.result[0].sex);
                        $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
                        $("#job").val(data.result[0].jobAbbreviation);
                        $("#nation").val(data.result[0].nation);
                        $('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
                    }else if(data.success==true && data.result.length>1){
                        var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
                        html += '<thead><tr><th></th><th>姓名</th><th>单位</th><th>身份证号</th><th>警号</th></tr></thead>';
                        html += '<tbody>';
                        for(var i=0; i< data.result.length; i++) {
                            html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
                            html += '<td>'+data.result[i].name+'</td>';
                            html += '<td>'+data.result[i].workunitName+'</td>';
                            html += '<td>'+data.result[i].idNumber+'</td>';
                            html += '<td>'+data.result[i].policeIdNumber+'</td>';
                            html += '</tr>';
                        }

                        html +=	'</tbody>';
                        html +=	'</table>';
                        var submit = function (v, h, f) {
                            $("#idNumber").val(data.result[f.selected].idNumber);
                            $("#policeNum").val(data.result[f.selected].policeIdNumber);
                            $("#sex").val(data.result[f.selected].sex);
                            $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
                            $("#job").val(data.result[f.selected].jobAbbreviation);
                            $("#nation").val(data.result[f.selected].nation);
                            $('#nation').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
                            return true;
                        };
                        top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
                    }else {
                        $.jBox.tip('没有查询到该人名相关信息');
                    }
                }
            })
        }
    </script>
</head>
<body>
<br/>
<form:form id="inputForm" modelAttribute="affairApplicant" action="${ctx}/affair/affairApplicant/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">姓名：</label>
        <div class="controls">
            <form:input id="name" path="name" htmlEscape="false" class="input-xlarge required" onkeydown="if(event.keyCode==13){setDefaults();return false;}"/>
            <span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">身份证号：</label>
        <div class="controls">
            <form:input id="idNumber" path="idNumber" htmlEscape="false" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">警号：</label>
        <div class="controls">
            <form:input id="policeNum" path="policeNum" htmlEscape="false" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">性别：</label>
        <div class="controls">
            <form:select id="sex" path="sex" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">所在党组织：</label>
        <div class="controls">
            <sys:treeselect id="partyBranch" name="partyBranchId" value="${affairApplicant.partyBranchId}"
                            labelName="partyBranch" labelValue="${affairApplicant.partyBranch}"
                            title="所在党组织" url="/affair/affairGeneralSituation/treeData" cssClass="required"
                            allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">职务：</label>
        <div class="controls">
            <form:input id="job" path="job" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">民族：</label>
        <div class="controls">
            <form:select id="nation" path="nation" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">文化程度：</label>
        <div class="controls">
            <form:input path="educationDegree" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">出生年月：</label>
        <div class="controls">
            <input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairApplicant.birthday}" pattern="yyyy-MM"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">申请入党时间：</label>
        <div class="controls">
            <input name="enterDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairApplicant.enterDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairApplicant:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                       type="submit"
                                                                       value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>