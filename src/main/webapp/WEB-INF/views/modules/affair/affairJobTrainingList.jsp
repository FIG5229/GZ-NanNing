<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>岗位练兵功能管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#export").click(
                function () {
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairJobTraining/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairJobTraining/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairJobTraining/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairJobTraining/");
                        }
                        if (v == 'cancel') {
                            $.jBox.tip('已取消');
                        }
                        return true;
                    };
                    $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, {
                        buttons: {
                            '导出全部数据': 'all',
                            '导出当前页面数据': 'part',
                            '取消': 'cancel'
                        }
                    });
                }
            );
            $("#print").click(function () {
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $('#headTitle').addClass("table table-striped table-bordered table-condensed");
                $('#headTitle').removeAttr('style');
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
                    pageTitle: "",
                    removeInline: false,
                    printDelay: 333,
                    header: "",
                    formValues: false,
                    afterPrint: function () {
                        $('#handleTh').css('display', 'table-cell');
                        $('.handleTd').css('display', 'table-cell');
                        $('#headTitle').css('display', 'none');
                    }
                });
            });
            $("#btnImport").click(function () {
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairJobTraining", "导入", 800, 520, {
                    itle: "导入数据", buttons: {"关闭": true},
                    bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
                        self.location.href = "${ctx}/affair/affairJobTraining/list?organizationId=${organizationId}&organization=${organization}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
                    }
                });
            });

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        //添加，修改弹窗
        function openDialog(url) {
            top.$.jBox.open("iframe:" + url, "岗位练兵", 800, 500, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairJobTraining/list?organizationId=${organizationId}&organization=${organization}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
                }
            });
        }
        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/affair/affairJobTraining/formDetail?id=" + id;
            top.$.jBox.open(url, "岗位练兵详情", 800, 500, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }
            });
        }
    </script>
</head>
<body>

<%--<ul class="nav nav-tabs">
    <shiro:hasPermission name="affair:affairTraining:view"><li><a href="${ctx}/affair/affairTraining/list">练兵比武</a></li></shiro:hasPermission>
&lt;%&ndash;<shiro:hasPermission name="affair:affairTrainingManage:view"><li><a href="${ctx}/affair/affairTrainingManage/list">成绩管理员练兵比武</a></li></shiro:hasPermission>&ndash;%&gt;
    <shiro:hasPermission name="affair:affairJobTraining:view"><li class="active"><a href="${ctx}/affair/affairJobTraining/list">岗位练兵</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairTrainCombat:view"><li><a href="${ctx}/affair/affairTrainCombat/list">实弹训练</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairSwapExercise:view"><li><a href="${ctx}/affair/affairSwapExercise/">交流锻炼</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairTrainOutsource:view"><li><a href="${ctx}/affair/affairTrainOutsource/">委外培训</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairSendTeacher:view"><li><a href="${ctx}/affair/affairSendTeacher/">送教上门</a></li></shiro:hasPermission>
</ul>--%>
<form:form id="searchForm" modelAttribute="affairJobTraining" action="${ctx}/affair/affairJobTraining/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="岗位练兵.xlsx"/>
    <ul class="ul-form">
        <li><label>用户名：</label>
            <form:input path="username" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>警号：</label>
            <form:input path="policeNumber" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>警衔：</label>
            <form:select path="policeRank" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('police_rank_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
               <%-- <form:options items="${fns:getDictList('police_rank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
            </form:select>
        </li>
      <%--  <li><label>警种：</label>
            <form:select path="policeClassification" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('police_classification')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>--%>

        <li><label>训练开始时间：</label>
            <input name="drillDateBegin" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairJobTraining.drillDateBegin}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li><label>训练结束时间：</label>
            <input name="drillDateOver" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairJobTraining.drillDateOver}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li><label>姓名：</label>
            <form:input path="name" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>证件号：</label>
            <form:input path="idNumber" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>项目类别：</label>
            <form:select path="itemClassification" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('project_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>人员类别：</label>
            <form:select path="personnelCategory" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('person_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>管理类别：</label>
            <form:select path="managementClass" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('management_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>行政职务：</label>
            <form:select path="administrativePost" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('administration_post')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>职务类别：</label>
            <form:select path="jobLevel" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('post_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <%--<li><label>机构：</label>
            <form:input path="organization" htmlEscape="false" class="input-medium"/>
        </li>--%>
        <li><label>机构：</label>
            <sys:treeselect id="organizationId" name="organizationId" value="${affairJobTraining.organizationId}" labelName="organization" labelValue="${affairJobTraining.organization}"
                            title="所属机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
        </li>
        <li><label>创建日期：</label>
            <input name="creationTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairJobTraining.creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li><label>域：</label>
            <sys:treeselect id="region" name="regionId" value="${affairJobTraining.regionId}" labelName="region"
                            labelValue="${affairJobTraining.region}"
                            title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>
        <li class="clearfix"></li>

        <ul class="ul-form2">

            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>

            <shiro:hasPermission name="affair:affairJobTraining:edit">
                <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                        onclick="openDialog('${ctx}/affair/affairJobTraining/form')"/></li>

                <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                        onclick="deleteByIds('${ctx}/affair/affairJobTraining/deleteByIds','checkAll','myCheckBox')"/>
                </li>

                <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
            </shiro:hasPermission>


            <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
            <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
            <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairJobTraining/list?organizationId=${organizationId}&organization=${organization}'"/></li>

            <li class="clearfix">x</li>
        </ul>

    </ul>
</form:form>
<sys:message content="${message}"/>
<div id="contentTable">
    <table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
        <tr>
            <td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处岗位练兵报表</td>
        </tr>
    </table>
    <table  class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
                                onclick='chooseAll(this,"myCheckBox")'/></th>
        <th>序号</th>
        <th>用户名</th>
        <th>训练开始时间</th>
        <th>训练结束时间</th>
        <th>姓名</th>
        <th>项目类别</th>
        <th>创建人</th>
        <th>创建日期</th>
        <th>训练概况</th>
       <%-- <th>备注信息</th>--%>
        <shiro:hasPermission name="affair:affairJobTraining:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairJobTraininginfo" varStatus="status">
        <tr>
            <td class="checkTd">
                <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairJobTraininginfo.id}"/>
            </td>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairJobTraininginfo.username}
            </td>
            <td>
                <fmt:formatDate value="${affairJobTraininginfo.drillDateBegin}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${affairJobTraininginfo.drillDateOver}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
               ${affairJobTraininginfo.name}
            </td>
            <td>
                ${fns:getDictLabel(affairJobTraininginfo.itemClassification, 'project_type', '')}
            </td>
            <td>
                ${affairJobTraininginfo.creator}
            </td>
            <td>
                <fmt:formatDate value="${affairJobTraininginfo.creationTime}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
               ${affairJobTraininginfo.drillGeneralSituation}
            </td>
           <%--<td>
                ${affairJobTraining.remarks}
            </td>--%>
            <td>
                <a onclick="openDetailDialog('${affairJobTraininginfo.id}')" style="cursor: pointer">查看</a>
                <shiro:hasPermission name="affair:affairJobTraining:edit">
                    <a href="javascript:void(0)"
                       onclick="openDialog('${ctx}/affair/affairJobTraining/form?id=${affairJobTraininginfo.id}')">修改</a>
                    <a href="${ctx}/affair/affairJobTraining/delete?id=${affairJobTraininginfo.id}&organizationId=${affairJobTraining.organizationId}" onclick="return confirmx('确认要删除该条记录吗？', this.href)">删除</a>
                </shiro:hasPermission>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
<div class="pagination">${page}</div>
</body>
</html>