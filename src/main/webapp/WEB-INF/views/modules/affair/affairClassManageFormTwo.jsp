<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>培训班管理管理</title>
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
    </script>
</head>
<body>

<br/>
<form:form id="inputForm" modelAttribute="affairClassManage" action="${ctx}/affair/affairClassManage/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">培训名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" class="input-xlarge "/>
                <%--				<span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训年度：</label>
        <div class="controls">
            <input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${affairClassManage.year}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位：</label>
        <div class="controls">
                <%--<form:input path="unit" htmlEscape="false" class="input-xlarge required"/>--%>
            <sys:treeselect id="unit" name="unitId" value="${affairClassManage.unitId}" labelName="unit"
                            labelValue="${affairClassManage.unit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                            notAllowSelectParent="false" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
                <%--				<span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训开始日期：</label>
        <div class="controls">
            <input name="beganDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairClassManage.beganDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训结束日期：</label>
        <div class="controls">
            <input name="resultDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairClassManage.resultDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">标题：</label>
        <div class="controls">
            <form:input path="title" htmlEscape="false" class="input-xlarge "/>
                <%--            <span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训班类型：</label>
        <div class="controls">
            <form:select path="type" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_train_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
                <%--            <span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训层次：</label>
        <div class="controls">
            <form:select path="level" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_train_level')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
                <%--				<span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训方式：</label>
        <div class="controls">
            <form:select path="trainWay" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_train_way')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
                <%--				<span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训天数：</label>
        <div class="controls">
            <form:input path="trainDay" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训人数：</label>
        <div class="controls">
            <form:input path="count" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训目的：</label>
        <div class="controls">
            <form:input path="purpose" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训内容：</label>
        <div class="controls">
            <form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训场地：</label>
        <div class="controls">
            <form:input path="site" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训对象：</label>
        <div class="controls">
            <form:input path="trainObject" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训预算：</label>
        <div class="controls">
            <form:input path="budget" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <%--		<div class="control-group">--%>
    <%--			<label class="control-label">渠道：</label>--%>
    <%--			<div class="controls">--%>
    <%--				<form:input path="channel" htmlEscape="false" class="input-xlarge "/>--%>
    <%--			</div>--%>
    <%--		</div>--%>
    <div class="control-group">
        <label class="control-label">渠道：</label>
        <div class="controls">
            <form:select path="channel" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_train_channel')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">培训费：</label>
        <div class="controls">
            <form:input path="fees" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    </div>
    <div class="control-group">
        <label class="control-label">师资费：</label>
        <div class="controls">
            <form:input path="teacherFees" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">住宿费：</label>
        <div class="controls">
            <form:input path="accommodationFees" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">伙食费：</label>
        <div class="controls">
            <form:input path="boardWages" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">场地资料交通费：</label>
        <div class="controls">
            <form:input path="siteFees" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">其他费用：</label>
        <div class="controls">
            <form:input path="otherFees" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">费用总额：</label>
        <div class="controls">
            <form:input path="feesCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">实施状态：</label>
        <div class="controls">
            <form:select path="status" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_train_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <%--		<div class="control-group">--%>
    <%--			<label class="control-label">开班状态：</label>--%>
    <%--			<div class="controls">--%>
    <%--				<form:input path="openStatus" htmlEscape="false" class="input-xlarge "/>--%>
    <%--			</div>--%>
    <%--		</div>--%>
    <%--    <div class="control-group">--%>
    <%--        <label class="control-label">实施状态：</label>--%>
    <%--        <div class="controls">--%>
    <%--            <form:select path="status" class="input-xlarge required">--%>
    <%--                <form:option value="" label=""/>--%>
    <%--                <form:options items="${fns:getDictList('affair_train_status')}" itemLabel="label" itemValue="value"--%>
    <%--                              htmlEscape="false"/>--%>
    <%--            </form:select>--%>
    <%--                &lt;%&ndash;				<span class="help-inline"><font color="red">*</font> </span>&ndash;%&gt;--%>
    <%--        </div>--%>
    <%--    </div>--%>
    <%--    <div class="control-group">--%>
    <%--        <label class="control-label">建班状态：</label>--%>
    <%--        <div class="controls">--%>
    <%--            <form:select path="classStatus" class="input-xlarge required">--%>
    <%--                <form:option value="" label=""/>--%>
    <%--                <form:options items="${fns:getDictList('affair_train_classStatus')}" itemLabel="label" itemValue="value"--%>
    <%--                              htmlEscape="false"/>--%>
    <%--            </form:select>--%>
    <%--                &lt;%&ndash;				<span class="help-inline"><font color="red">*</font> </span>&ndash;%&gt;--%>
    <%--        </div>--%>
    <%--    </div>--%>
    <%--    <div class="control-group">--%>
    <%--        <label class="control-label">结项状态：</label>--%>
    <%--        <div class="controls">--%>
    <%--            <form:select path="pospStatus" class="input-xlarge required">--%>
    <%--                <form:option value="" label=""/>--%>
    <%--                <form:options items="${fns:getDictList('affair_train_pospStatus')}" itemLabel="label" itemValue="value"--%>
    <%--                              htmlEscape="false"/>--%>
    <%--            </form:select>--%>
    <%--                &lt;%&ndash;				<span class="help-inline"><font color="red">*</font> </span>&ndash;%&gt;--%>
    <%--        </div>--%>
    <%--    </div>--%>
    <div class="control-group">
        <label class="control-label">创建人：</label>
        <div class="controls">
            <form:input path="creator" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">填报人：</label>
        <div class="controls">
            <form:input path="informant" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">创建时间：</label>
        <div class="controls">
            <input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${affairClassManage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">创建人单位：</label>
        <div class="controls">
                <%--<form:input path="unit" htmlEscape="false" class="input-xlarge required"/>--%>
            <sys:treeselect id="creatorUnit" name="creatorUnitId" value="${affairClassManage.creatorUnitId}"
                            labelName="creatorUnit" labelValue="${affairClassManage.creatorUnit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                            notAllowSelectParent="false" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
                <%--				<span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">主办单位：</label>
        <div class="controls">
                <%--<form:input path="unit" htmlEscape="false" class="input-xlarge required"/>--%>
            <sys:treeselect id="sponsorUnit" name="sponsorUnitId" value="${affairClassManage.sponsorUnitId}"
                            labelName="sponsorUnit" labelValue="${affairClassManage.sponsorUnit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                            notAllowSelectParent="false" dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
                <%--				<span class="help-inline"><font color="red">*</font> </span>--%>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">班主任：</label>
        <div class="controls">
            <form:input path="teacher" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">助教：</label>
        <div class="controls">
            <form:input path="assistant" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">班主任电话：</label>
        <div class="controls">
            <form:input path="teacherPhone" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">联系电话：</label>
        <div class="controls">
            <form:input path="phone" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">课程总分：</label>
        <div class="controls">
            <form:input path="classCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">评估总分：</label>
        <div class="controls">
            <form:input path="score" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">应参训人数：</label>
        <div class="controls">
            <form:input path="participateTrain" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">实际参训人数：</label>
        <div class="controls">
            <form:input path="realParticipate" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">参训率：</label>
        <div class="controls">
            <form:input path="participateRate" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">已通过人数：</label>
        <div class="controls">
            <form:input path="passedCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">未通过人数：</label>
        <div class="controls">
            <form:input path="failCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">通过率：</label>
        <div class="controls">
            <form:input path="passRate" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">学习总时长：</label>
        <div class="controls">
            <form:input path="studyTime" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">平均学习时长：</label>
        <div class="controls">
            <form:input path="averageTime" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">学习总次数：</label>
        <div class="controls">
            <form:input path="studyCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">平均学习总次数：</label>
        <div class="controls">
            <form:input path="averageCount" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">审批状态：</label>
        <div class="controls">
            <form:input path="approvalStatus" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="affair:affairClassManage:edit"><input id="btnSubmit" class="btn btn-primary"
                                                                         type="submit"
                                                                         value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
    </div>
</form:form>
<script>
    if ("success" == "${saveResult}") {
        parent.$.jBox.close();
    }
</script>
</body>
</html>