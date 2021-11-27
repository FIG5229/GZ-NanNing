<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>实弹训练管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btnPrint").click(function () {
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $('#checkTh').css('display', 'none');
                $('.checkTd').css('display', 'none');
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
                        $('#checkTh').css('display', 'table-cell');
                        $('.checkTd').css('display', 'table-cell');
                        $('#headTitle').css('display', 'none');

                    }
                });
            });

            $("#btnExport").click(
                function () {
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairTrainCombat/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairTrainCombat/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairTrainCombat/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairTrainCombat/");
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

            $("#btnImport").click(function () {
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTrainCombat", "导入", 800, 520, {
                    itle: "导入数据", buttons: {"关闭": true},
                    bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
                        self.location.href = "${ctx}/affair/affairTrainCombat/list?organizationId=${organizationId}&organization=${organization}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
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

        function openAddForm(url) {
            top.$.jBox.open("iframe:" + url, "实弹训练", 1200, 600, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairTrainCombat/list?organizationId=${organizationId}&organization=${organization}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
                }
            });
        }

        function openEditDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairTrainCombat/form?id=" + id, "实弹训练编辑", 1200, 600, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairTrainCombat/list?organizationId=${organizationId}&organization=${organization}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
                }
            });
        }

        function openDetailDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairTrainCombat/formDetail?id=" + id, "实弹训练详情", 1200, 600, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairTrainCombat/list?organizationId=${organizationId}&organization=${organization}&pageNo=${page.pageNo}&pageSize=${page.pageSize}";
                }
            });
        }

        if ("success" == "${saveResult}") {
            parent.$.jBox.close();
        }
    </script>
</head>
<body>
<%--<ul class="nav nav-tabs">
    <shiro:hasPermission name="affair:affairTraining:view"><li><a href="${ctx}/affair/affairTraining/list">练兵比武</a></li></shiro:hasPermission>
&lt;%&ndash;
    <shiro:hasPermission name="affair:affairTrainingManage:view"><li><a href="${ctx}/affair/affairTrainingManage/list">成绩管理员练兵比武</a></li></shiro:hasPermission>
&ndash;%&gt;
    <shiro:hasPermission name="affair:affairJobTraining:view"><li><a href="${ctx}/affair/affairJobTraining/">岗位练兵</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairTrainCombat:view"><li class="active"><a href="${ctx}/affair/affairTrainCombat/list">实弹训练</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairSwapExercise:view"><li><a href="${ctx}/affair/affairSwapExercise/">交流锻炼</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairTrainOutsource:view"><li><a href="${ctx}/affair/affairTrainOutsource/">委外培训</a></li></shiro:hasPermission>
    <shiro:hasPermission name="affair:affairSendTeacher:view"><li><a href="${ctx}/affair/affairSendTeacher/">送教上门</a></li></shiro:hasPermission>
</ul>--%>
<form:form id="searchForm" modelAttribute="affairTrainCombat" action="${ctx}/affair/affairTrainCombat/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="实弹训练表.xlsx"/>
    <ul class="ul-form">
       <%-- <li><label>用户名：</label>
            <form:input path="userName" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>警号：</label>
            <form:input path="number" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>训练日期：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTrainCombat.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTrainCombat.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>--%>
        <li><label>姓名：</label>
            <form:input path="name" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>身份证号：</label>
            <form:input path="idNumber" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>单发环数：</label>
            <form:input path="minBulletNum" htmlEscape="false" class="input-medium"/>
            至
            <form:input path="maxBulletNum" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>平均单发环数排序：</label>
           <form:select path="orderBy" class="input-medium">
               <form:option value="DESC" label="降序"/>
               <form:option value="ASC" label="升序"/>
           </form:select>
        </li>
           <li><label>机构：</label>
               <sys:treeselect id="organizationId" name="organizationId" value="${affairTrainCombat.organizationId}" labelName="organization" labelValue="${affairTrainCombat.organization}"
                               title="所属机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
           </li>

        <%--<li><label>项目类别：</label>
            <form:select path="projectType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('project_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>警种：</label>
            <form:select path="policeClassification" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('police_classification')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>警衔：</label>
            <form:select path="policeRank" class="input-medium">
                <form:option value="" label=""/>
                &lt;%&ndash;<form:options items="${fns:getDictList('police_rank')}" itemLabel="label" itemValue="value" htmlEscape="false"/>&ndash;%&gt;
                <form:options items="${fns:getDictList('police_rank_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>人员类别：</label>
            <form:select path="personType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('person_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>管理类别：</label>
            <form:select path="managementType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('management_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>行政职务：</label>
            <form:select path="post" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('administration_post')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>职务级别：</label>
            <form:select path="postLevel" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('post_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>单位：</label>
            <sys:treeselect id="unit" name="unitId" value="${affairTrainCombat.unitId}" labelName="unit"
                            labelValue="${affairTrainCombat.unit}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                            notAllowSelectParent="false" dataMsgRequired="必填信息"/>
        </li>
        <li><label>域：</label>
            <sys:treeselect id="region" name="regionId" value="${affairTrainCombat.regionId}" labelName="region"
                            labelValue="${affairTrainCombat.region}"
                            title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>--%>
        <ul class="ul-form2">
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <shiro:hasPermission name="affair:affairTrainCombat:edit">
                <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                        onclick="openAddForm('${ctx}/affair/affairTrainCombat/form')"/></li>
                <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
                <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                        onclick="deleteByIds('${ctx}/affair/affairTrainCombat/deleteByIds','checkAll','myCheckBox')"/>
                </li>
            </shiro:hasPermission>
            <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
            <li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
            <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                    onclick="window.location.href='${ctx}/affair/affairTrainCombat/list?organizationId=${organizationId}&organization=${organization}'"/></li>
            <li class="clearfix"></li>
        </ul>
    </ul>
</form:form>
<sys:message content="${message}"/>
<div id="contentTable">
    <table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
        <tr>
            <td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处实弹训练报表</td>
        </tr>
    </table>
    <table  class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
                                onclick='chooseAll(this,"myCheckBox")'/>全选
        </th>

        <th>序号</th>
        <%--<th>用户名</th>--%>
        <th>姓名</th>
        <%--<th>训练日期</th>--%>
        <th>子弹数量</th>
        <th>平均单发环数</th>
        <th>备注</th>
        <th>创建人</th>
        <th>创建时间</th>
        <th>更新时间</th>
        <shiro:hasPermission name="affair:affairTrainCombat:edit">
            <th class="handleTd">操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairTrainCombatinfo" varStatus="status">
        <tr>
            <td class="checkTd">
                <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTrainCombatinfo.id}"/>
            </td>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <%--<td>
                    ${affairTrainCombat.userName}
            </td>--%>
            <td>
                    ${affairTrainCombatinfo.name}
            </td>
            <%--<td>
                <fmt:formatDate value="${affairTrainCombat.date}" pattern="yyyy-MM-dd"/>
            </td>--%>
            <td>
                    ${affairTrainCombatinfo.count}
            </td>
            <td>
                    ${affairTrainCombatinfo.average}
            </td>
            <td>
                    ${affairTrainCombatinfo.remarks}
            </td>
            <td>
                	${fns:getUserById(affairTrainCombatinfo.createBy).getName()}
            </td>
            <td>
                <fmt:formatDate value="${affairTrainCombatinfo.createDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                <fmt:formatDate value="${affairTrainCombatinfo.updateDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td class="handleTd">
                <a href="javascript:" onclick="openDetailDialog('${affairTrainCombatinfo.id}')">查看</a>
                <shiro:hasPermission name="affair:affairTrainCombat:edit">
                    <a href="javascript:" onclick="openEditDialog('${affairTrainCombatinfo.id}')">修改</a>
                    <a href="${ctx}/affair/affairTrainCombat/delete?id=${affairTrainCombatinfo.id}&organizationId=${affairTrainCombat.organizationId}"
                       onclick="return confirmx('确认要删除该实弹训练吗？', this.href)">删除</a>
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