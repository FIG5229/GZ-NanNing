<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>文化人才管理</title>
    <meta name="decorator" content="default"/>
    <%--<script src="./../../../../static/js/jquery-3.2.1.min.js"/>--%>
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

    </script>
</head>
<body>
<br/>
<form:form id="inputForm" modelAttribute="affairWenhua" action="${ctx}/affair/affairWenhua/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">姓名：</label>
        <div class="controls">
            <form:input id="username" path="name" htmlEscape="false" class="input-xlarge required" onblur="leave();manuscriptStatistics()"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
    <c:choose>
        <c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
            <div class="control-group">
                <label class="control-label">单位：</label>
                <div class="controls">
                    <sys:treeselect id="unit" name="unitId" value="${affairWenhua.unitId}" labelName="unit"
                                    labelValue="${affairWenhua.unit}"
                                    title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                                    notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="control-group">
                <label class="control-label">单位：</label>
                <div class="controls">
                    <sys:treeselect id="unit" name="unitId" value="${affairWenhua.unitId}" labelName="unit"
                                    labelValue="${affairWenhua.unit}"
                                    title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                                    notAllowSelectParent="false" dataMsgRequired="必填信息"/>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <%--<div class="control-group">
        <label class="control-label">单位：</label>
        <div class="controls">
            <sys:treeselect id="unit" name="unitId" value="${affairWenhua.unitId}" labelName="unit"
                            labelValue="${affairWenhua.unit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                            notAllowSelectParent="false" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>--%>
    <div class="control-group">
        <label class="control-label">特长：</label>
        <div class="controls">
            <form:select path="specialty" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_wenyi_specialty')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">入会情况：</label>
        <div class="controls">
            <form:select path="joinType" class="input-xlarge ">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_ruhui')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">作品情况：</label>
        <div class="col-sm-5" id="inputAppend">
            <form:select path="workSituation" class="input-xlarge" style="left: 20px" id="production" onchange="awards()" >
                <from:option value="" label=""/>
                <c:forEach items="${affairWenhua.leave}" var="affairWenhua" varStatus="status">
                    <from:option value="${affairWenhua}" label="${affairWenhua}"/>
                </c:forEach>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">奖项情况：</label>
        <div class="controls">
            <form:select path="reward" class="input-xlarge" id="awardsCondition">
                <c:forEach items="${affairWenhua.awards}" var="workSituation" varStatus="status">
                    <from:option value="${workSituation}" label="${workSituation}"/>
                </c:forEach>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">宣传情况：</label>
        <div class="controls" >
            <form:input path="publicity" type="text" htmlEscape="false" class="input-xlarge" id="cardId" value="${manuscriptList}"/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairWenhua:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                                    value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
    </div>
</form:form>
<script language="javascript">

    if ("success" == "${saveResult}") {
        parent.$.jBox.tip("保存成功");
        parent.$.jBox.close();
    }

    //绑定作品情况的数据
    function leave() {
        var name = window.document.getElementById('username').value;
        /*作品情况内容清空*/
        $("#production").val('');
        $("#production").text('');
        $("#s2id_production>a>span").eq(0).text('');
        /*奖项名称下拉框清空*/
        $("#awardsCondition").val('');
        $("#awardsCondition").text('');
        $("#s2id_awardsCondition>a>span").eq(0).text('');
        $.ajax({
            url: "${ctx}/affair/affairWenhua/findUserNameList",
            type: "post",
            data: {name: name},
            success: function (data) {
                $('#production').append("<option value=''></option>");
                if (data != null && data != "" && data != undefined) {
                    for (var i = 0; i < data.length; i++) {
                        //html += "<option value= '"+data['data'][i].id+"'>"+data['data'][i].lsName+"</option>";
                        //html += "<option value= '"+data[i].proName+"'>"+data[i].proName+"</option>";
                        $('#production').append("<option value='" + data[i] + "'>" + data[i] + "</option>");

                    }

                }else{
                   alert("您好，你没有作品内容！")
                }
            }
        });
    }

    function awards() {
        var options = $("#production option:selected");
        var workSituation = options.text();
        $("#awardsCondition").val('');
        $("#awardsCondition").text('');
        $("#s2id_awardsCondition>a>span").eq(0).text('');
        $.ajax({
            url: "${ctx}/affair/affairWenhua/fingproductionList",
            type: "post",
            data: {workSituation: workSituation},
            success: function (data) {
                //console.log(data)
                if (data != null && data != "" && data != undefined) {
                    for (var i = 0; i < data.length; i++) {
                        $("#awardsCondition").append("<option value='" + data[i] + "'>" + data[i] + "</option>");
                    }
                } else {
                    document.getElementById('awardsCondition').innerHTML="";
                    $("#awardsCondition span").html()
                    //$("#awardsCondition").append("<option value='" +   + "'>" + + "</option>");
                    alert("您好，此作品没有获奖记录哦！")
                }
            }
        });
    }

    //统计
    function manuscriptStatistics() {
        var name = window.document.getElementById('username').value;
        $.ajax({
            url: "${ctx}/affair/affairWenhua/findManuscriptList", // 请求路径
            type: "post",
            data: {name: name},
            success: function (data) {
             $("#cardId").val(data)
            }
        });
    }



</script>
</body>
</html>