<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>文档分类管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#officeName").css('display', 'none');
            $("#officeButton").css('display', 'none');
            $("#peopleName").css('display', 'none');
            $("#peopleButton").css('display', 'none');
            /*if(sessionStorage.getItem('useRange')=='useRange'){
                $("#usableRangeSet").children('a').eq(0).click();
                sessionStorage.removeItem('useRange')
            }*/
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

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            var tempId = $('#tempId').val();
            $.ajax({
                type: 'post',
                url: "${ctx}/affair/affairDocClassify/changePage",
                data: {
                    pageNo: n,
                    pageSize: s,
                    tempId: tempId},
                dataType: "json",
                success: function (data) {
                    if (data.success == true) {
                        $('#contentTbody').html('');
                        var resultList =  data.result.list;
                        for ( var i = 0;i<resultList.length;i++ ) {
                            var $tr =
                                '            <tr>\n' +
                                '                <td class="checkTd">\n' +
                                '                    <input style=\'margin-left:12px\' type=\'checkbox\' name="myCheckBox"\n' +
                                '                           value="'+resultList[i].id+'"/></td>\n' +
                                '                <td>\n' +resultList[i].audiencesName +'</td>\n' +
                                '                <td>\n'+ resultList[i].audiencesType +'\n' +
                                '                </td>\n' +
                                '            </tr>\n';
                            $('#contentTbody').append($tr);
                        }
                        $('.pagination').html(data.result.html)
                    } else {
                        $.jBox.tip('添加失败');
                    }
                }, error: function (d) {
                    $.jBox.tip('发生错误，添加失败');
                }
            });

            //$("#searchForm").submit();
            return false;
        }

        //根据选择的父分类，为父级id及父级ids字段赋值
        function setParenId() {
            var parentId = $("#parentClassify").val();
            $("#parentId").val('');
            $("#parentIds").val('');
            $.ajax({
                type: "post",
                url: "${ctx}/affair/affairDocClassify/getParentIdsById",
                data: {id: parentId},
                dataType: "json",
                success: function (data) {
                    if (data.success == true) {
                        $("#parentId").val(parentId);
                        $("#parentIds").val(data.result.parentIds + "," + parentId);
                    } else {
                        $.jBox.tip('没有查询到该父类相关信息');
                    }
                }
            })
        }

        /*
        * 展示使用范围设置的列表
        * */
        function showUsableRangeDiv() {
            $("#usableRangeDiv").css('display', 'block');
            $("#usableRangeSet").addClass('active');
            $("#inputForm").css('display', 'none');
            $("#baseInfo").removeClass('active');
        }

        /*
        * 展示基本信息表单
        * */
        function showBaseInfoForm() {
            $("#usableRangeDiv").css('display', 'none');
            $("#usableRangeSet").removeClass('active');
            $("#inputForm").css('display', 'block');
            $("#baseInfo").addClass('active');
        }

        /*
        * 显示部门选择页面
        * */
        function openOfficeSelect() {
            $("#officeName").click();
        }

        /*
        * 显示人员选择页面
        * */
        function openPeopleSelect() {
            $("#peopleName").click();
        }

        /*
        * 部门选择点击确定，执行添加方法
        * */
        function addOffice() {
            var officeName = $("#officeName").val();
            var officeId = $("#officeId").val();
            var type = '部门';
            var tempId = $("#tempId").val();
            sendAddAjax(officeName, type, officeId, tempId);

        }

        /*
        * 人员选择点击确定，执行添加方法
        * */
        function addPeople() {
            var peopleName = $("#peopleName").val();
            var peopleId = $("#peopleId").val();
            var type = '人员';
            var tempId = $("#tempId").val();
            sendAddAjax(peopleName, type, peopleId, tempId);
        }

        /*
        * 人员/部门添加具体方法
        * */
        function sendAddAjax(audiencesName, audiencesType, audiencesId, tempId) {
            $.ajax({
                type: 'post',
                url: "${ctx}/affair/affairDocClassify/saveUsableRangeList",
                data: {
                    audiencesName: audiencesName,
                    audiencesType: audiencesType,
                    audiencesId: audiencesId,
                    tempId: tempId
                },
                dataType: "json",
                success: function (data) {
                    if (data.success == true) {
                        $.jBox.tip('添加成功');
                        /*sessionStorage.setItem('useRange', 'useRange')*/
                        //location.reload();
                        $('#contentTbody').html('');
                        console.log(data);
                        var resultList =  data.result.list;
                        for ( var i = 0;i<resultList.length;i++ ) {
                            var $tr =
                            '            <tr>\n' +
                            '                <td class="checkTd">\n' +
                            '                    <input style=\'margin-left:12px\' type=\'checkbox\' name="myCheckBox"\n' +
                            '                           value="'+resultList[i].id+'"/></td>\n' +
                            '                <td>\n' +resultList[i].audiencesName +'</td>\n' +
                            '                <td>\n'+ resultList[i].audiencesType +'\n' +
                            '                </td>\n' +
                            '            </tr>\n';
                            $('#contentTbody').append($tr);
                        }
                        $('.pagination').html(data.result.html)
                    } else {
                        $.jBox.tip('添加失败');
                    }
                }, error: function (d) {
                    $.jBox.tip('发生错误，添加失败');
                }
            })
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li id="baseInfo" class="active" onclick="showBaseInfoForm()">
        <a><%--<a href="${ctx}/affair/affairDocClassify/form?id=${affairDocClassify.id}">--%>基本信息<shiro:hasPermission
                name="affair:affairDocClassify:edit">${not empty affairDocClassify.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
                name="affair:affairDocClassify:edit">查看</shiro:lacksPermission></a></li>
    <li id="usableRangeSet"><a onclick="showUsableRangeDiv()" style="cursor: pointer">使用范围设置</a><%--<a href="${ctx}/affair/affairDocClassify/usableRangeList?id=${affairDocClassify.id}">使用范围设置</a>--%>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="affairDocClassify" action="${ctx}/affair/affairDocClassify/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <form:input id="formtempId" name="tempId" value="${tempId}" path="tempId" style="display:none"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">分类编码：</label>
        <div class="controls">
            <form:input path="classifyCode" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">分类名称：</label>
        <div class="controls">
            <form:input path="classifyName" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">父分类：</label>
        <div class="controls">
            <form:select id="parentClassify" path="parentClassify" class="input-xlarge" onchange="setParenId()">
                <form:option value="" label=""/>
                <c:forEach items="${affairDocClassifyList}" var="docClassify">
                    <form:option value="${docClassify.id}" label="${docClassify.classifyName}"/>
                </c:forEach>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">排序号：</label>
        <div class="controls">
            <form:input path="sortNumber" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">使用范围：</label>
        <div class="controls">
            <form:radiobuttons id="usableRange" path="usableRange" items="${fns:getDictList('all_appoint')}"
                               itemLabel="label"
                               itemValue="value" htmlEscape="false"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remark" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">上传图片：</label>
        <div class="controls">
            <form:hidden id="photo" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:webuploader input="photo" type="images" uploadPath="affair/affairDocClassify" selectMultiple="true"/>
        </div>
    </div>
    <div class="control-group" hidden>
        <label class="control-label">父级编号：</label>
        <div class="controls">
            <form:input path="parentId" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group" hidden>
        <label class="control-label">所有父级编号：</label>
        <div class="controls">
            <form:input path="parentIds" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairDocClassify:edit"><input id="btnSubmit" class="btn btn-primary"
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
<div id="usableRangeDiv" style="display: none">
    <div class="form-actions">
        <input id="classifyId" name="classifyId" type="hidden" value="${affairDocClassify.id}"/>
        <input id="tempId" name="tempId" value="${tempId}" style="display: none"/>
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input class="btn btn-primary" type="button" onclick="openOfficeSelect()" value="添加部门"/>
        <sys:treeselect id="office" name="office.id" value="" labelName="office.name"
                        labelValue=""
                        title="部门" url="/sys/office/treeData?type=2"
                        cssClass="input-small" allowClear="true" notAllowSelectParent="true" onchange="addOffice()"/>
        <input class="btn btn-primary" type="button" onclick="openPeopleSelect()" value="添加人员"/>
        <sys:treeselect id="people" name="people.id" value="" labelName="people.name"
                        labelValue=""
                        title="人员" url="/sys/office/treeData?type=3"
                        cssClass="input-small" allowClear="true" notAllowSelectParent="true" onchange="addPeople()"/>
        <input class="btn btn-primary" type="button"
               onclick="deleteByIds('${ctx}/affair/affairDocClassify/deleteUsableRangeByIds','checkAll','myCheckBox')"
               value="删除"/>
        <sys:message content="${message}"/>
    </div>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
                                    onclick='chooseAll(this,"myCheckBox")'/></th>
            <th>受众名称</th>
            <th>受众类别</th>
        </tr>
        </thead>
        <tbody id="contentTbody">
        <c:forEach items="${page.list}" var="affairDocClassifyRange">
            <tr>
                <td class="checkTd">
                    <input style='margin-left:12px' type='checkbox' name="myCheckBox"
                           value="${affairDocClassifyRange.id}"/>
                </td>
                <td>
                        ${affairDocClassifyRange.audiencesName}
                </td>
                <td>
                        ${affairDocClassifyRange.audiencesType}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <div class="pagination">${page}</div>
   <%-- <div class="button" style="margin-left: 2%"><input class="btn btn-primary" type="button" value="立即刷新"/></div>
    <br>
    <div>
		<span class="help-inline">
			<font color="red">注:
			<br>
			1.完成添加与删除操作后，如果您希望立刻起作用,请点击"立即刷新"按钮;
			<br>
			2.系统每天晚上会自动刷新，从而完成动态增加人员
			</font>
		</span>

    </div>--%>
</div>
</body>
</html>