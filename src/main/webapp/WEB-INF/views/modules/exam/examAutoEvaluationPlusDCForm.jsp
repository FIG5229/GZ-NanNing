<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>奖励信息库推送管理</title>
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

            $("#type").change(function () {
                jcObject();
            });
            function jcObject() {
                if($("#type").val() == '0'){
                    $('#personNameDiv').css('display', 'inline-block');
                    $('#personIdNumDiv').css('display', 'inline-block');
                }else{
                    $('#personNameDiv').css('display', 'none');
                    $('#personIdNumDiv').css('display', 'none');
                }
            }
            //调用
            jcObject();
        });

        function getChooseStandardList() {
            var list = [];
            var content = "";
            $.ajax({
                url: "${ctx}/exam/examStandardBaseInfo/getStandardListBeta",
                dataType: "json",
                async: true,//请求是否异步，默认为异步
                data: {kpType: $("#evalType").val()},
                type: "POST",
                success: function (res) {
                    if (res.result != null) {
                        list = res.result;
                        content = '';
                        list.forEach((item, index) => {
                            content += '<option value=' + item.id + '>' + item.name + '</option>';
                        })
                        $("#model").empty();
                        $("#model").append(content);
                        $("#model").val('');
                        $("#model").siblings('.select2-container').find('.select2-chosen').text($("#model").find("option:selected").text());
                        getChooseOptionsList();
                    }
                }
            });
        };
        //根据使用模板的值渲染选择项所对应的内容
        function getChooseOptionsList() {
            var list = [];
            var content = "";
            $.ajax({
                <%--url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel",--%>
                url: "${ctx}/exam/examCheck/findChooseOptionsByUseModel2",
                dataType: "json",
                async: true,//请求是否异步，默认为异步
                data: {useModel: $("#model").val()},
                type: "POST",
                success: function (res) {
                    if (res.result != null) {
                        list = res.result;
                        content = '';
                        list.forEach((item, index) => {
                            content += '<option value=' + item.optionId + '>' + item.itemValue + '</option>';
                        })
                        $("#option").empty();
                        $("#option").append(content);
                        $("#option").val('');
                        $("#option").siblings('.select2-container').find('.select2-chosen').text($("#option").find("option:selected").text());
                    }
                }
            });
        };

        function CheckInputIntFloat(oInput)
        {
            if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
            {
                oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
            }
        }

    </script>
</head>
<body>
<br/>
<form:form id="inputForm" modelAttribute="examAutoEvaluation" action="${ctx}/exam/examAutoEvaluation/savePushInfo" method="post" class="form-horizontal">
    <input name="awardId" value="${awardId}" type="hidden"/><%--奖励id--%>
    <form:hidden path="pushFrom"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">开始时间：</label>
        <div class="controls">
            <input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                   value="<fmt:formatDate value="${examAutoEvaluation.happenTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">结束时间：</label>
        <div class="controls">
            <input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
                   value="<fmt:formatDate value="${examAutoEvaluation.time}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">问题概述：</label>
        <div class="controls">
            <form:textarea path="details" htmlEscape="false" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">扣分情况：</label>
        <div class="controls">
            <form:input path="score" htmlEscape="false" class="input-xlarge required"  onkeyup="javascript:CheckInputIntFloat(this);"/>&nbsp;分
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">奖励对象：</label>
        <div class="controls">
            <form:select  id="type" path="type" class="input-xlarge">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('jc_objects')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group" id="personNameDiv" style="display: none;">
        <label class="control-label">姓名：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="true" class="input-medium required"  cssStyle=""  placeholder="姓名"/>
        </div>
    </div>
    <div class="control-group" id="personIdNumDiv" style="display: none;">
        <label class="control-label">身份证号：</label>
        <div class="controls">
            <form:input path="idNumber" htmlEscape="false" class="input-medium required"  cssStyle="" placeholder="身份证号"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">责任单位：</label>
        <div class="controls">
            <sys:treeselect id="unit" name="unitId" value="${examAutoEvaluation.unitId}" labelName="unit" labelValue="${examAutoEvaluation.unit}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" checked="false" dataMsgRequired="必填信息"/>
            <font color="red">*</font>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">层次：</label>
        <div class="controls">
            <select id="evalType" name="evalType" dclass="input-medium required" onchange="getChooseStandardList()" style="width: 150px;">
                <option value=""></option>
                <c:forEach items="${fns:getDictList('kp_type')}" var="dict">
                    <option value="${dict.value}">${dict.label}</option>
                </c:forEach>
            </select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">使用模板：</label>
        <div class="controls">
            <form:select  id="model" path="modelId" class="input-xlarge required" cssStyle="width: 400px;" onchange="getChooseOptionsList()">
                <form:option value="" label=""/>
<%--                <form:options items="${templateFile}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">选择项：</label>
        <div class="controls">
            <select id="option" style="width: 400px;" name="optionId">
                <option value=""></option>
            </select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remark" htmlEscape="false" rows="4" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="save()" value="保 存"/>&nbsp
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
    </div>
</form:form>
<script>
    if("success"=="${saveResult}"){
        parent.$.jBox.close();
    }
</script>
</body>
</html>