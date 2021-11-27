<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>铁路公安机关课程建设情况统计管理</title>
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

        if ("success" == "${saveResult}") {
            parent.$.jBox.close();
        }

        function setTotalVal() {
            var msCourse = $("#msCourse").val();
            var zxCourse = $("#zxCourse").val();
            if (typeof msCourse == "undefined" || msCourse == null || msCourse == "") {
                msCourse = 0;
            }
            if (typeof zxCourse == "undefined" || zxCourse == null || zxCourse == "") {
                zxCourse = 0
            }
			if (checkNumber(msCourse) && checkNumber(zxCourse)) {
				var total = parseFloat(msCourse) + parseFloat(zxCourse);
                $("#total").val(total)
            } else {
				showTipDialog()
            }

        }

        //验证字符串是否是数字
        function checkNumber(theObj) {
            var reg = /^[0-9]+.?[0-9]*$/;
            if (reg.test(theObj)) {
                return true;
            }
            return false;
        }

        function showTipDialog() {
            top.$.jBox.error('请输入数字', '系统提示', {
                buttonsFocus: 1, closed: function () {
                    if (typeof closed == 'function') {
                        closed();
                    }
                }
            });
            //top.$('.jbox-body .jbox-icon').css('top','55px');
        }
    </script>
</head>
<body>
<%--<ul class="nav nav-tabs">
    <li><a href="${ctx}/affair/affairCourseBuildStatistical/">铁路公安机关课程建设情况统计列表</a></li>
    <li class="active"><a href="${ctx}/affair/affairCourseBuildStatistical/form?id=${affairCourseBuildStatistical.id}">铁路公安机关课程建设情况统计<shiro:hasPermission name="affair:affairCourseBuildStatistical:edit">${not empty affairCourseBuildStatistical.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairCourseBuildStatistical:edit">查看</shiro:lacksPermission></a></li>
</ul>--%>
<br/>
<form:form id="inputForm" modelAttribute="affairCourseBuildStatistical"
           action="${ctx}/affair/affairCourseBuildStatistical/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">单位：</label>
        <div class="controls">
            <sys:treeselect id="unitId" name="unitId" value="${affairCourseBuildStatistical.unitId}"
                            labelName="unitName" labelValue="${affairCourseBuildStatistical.unitName}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true"
                            notAllowSelectParent="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">面授课程数：</label>
        <div class="controls">
            <form:input path="msCourse" htmlEscape="false" class="input-xlarge " onkeyup ="setTotalVal()"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">在线课程数：</label>
        <div class="controls">
            <form:input path="zxCourse" htmlEscape="false" class="input-xlarge " onkeyup ="setTotalVal()"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">合计：</label>
        <div class="controls">
            <form:input path="total" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairCourseBuildStatistical:edit"><input id="btnSubmit"
                                                                                    class="btn btn-primary"
                                                                                    type="submit"
                                                                                    value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
    </div>
</form:form>
</body>
</html>