<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>铁路公安机关课程建设情况统计管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#print").click(function () {
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $('#checkTh').css('display', 'none');
                $('.checkTd').css('display', 'none');
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
                    pageTitle: "打印",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: false,
                    afterPrint: function () {
                        $('#handleTh').css('display', 'table-cell');
                        $('.handleTd').css('display', 'table-cell');
                        $('#checkTh').css('display', 'table-cell');
                        $('.checkTd').css('display', 'table-cell');
                    }
                });
            });
            $("#export").click(function(){
                var submit = function (v, h, f) {
                    if (v == 'all') {
                        $("#searchForm").attr("action","${ctx}/affair/affairCourseBuildStatistical/export");
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/affair/affairCourseBuildStatistical/");
                    }
                    if (v == 'part') {
                        $("#searchForm").attr("action","${ctx}/affair/affairCourseBuildStatistical/export?flag=true")
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/affair/affairCourseBuildStatistical/");
                    }
                    if (v == 'cancel') {
                        $.jBox.tip('已取消');
                    }
                    return true;
                };
                $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
            });
            $("#btnImport").click(function(){
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_courseBuildStatistical", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairCourseBuildStatistical"}});
            });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        //添加，修改弹窗
        function openDialog(url, type) {
            top.$.jBox.open("iframe:" + url, "课程建设情况统计" + type, 800, 600, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairCourseBuildStatistical"
                }
            });
        }
    </script>
</head>
<body>
<%--<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/affair/affairCourseBuildStatistical/">铁路公安机关课程建设情况统计列表</a></li>
    <shiro:hasPermission name="affair:affairCourseBuildStatistical:edit"><li><a href="${ctx}/affair/affairCourseBuildStatistical/form">铁路公安机关课程建设情况统计添加</a></li></shiro:hasPermission>
</ul>--%>
<form:form id="searchForm" modelAttribute="affairCourseBuildStatistical"
           action="${ctx}/affair/affairCourseBuildStatistical/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="铁路公安机关课程建设情况统计表.xlsx"/>
    <ul class="ul-form">
        <li><label>单位：</label>
            <sys:treeselect id="unitId" name="unitId" value="${affairCourseBuildStatistical.unitId}"
                            labelName="unitName" labelValue="${affairCourseBuildStatistical.unitName}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairCourseBuildStatistical:edit">
            <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                    onclick="openDialog('${ctx}/affair/affairCourseBuildStatistical/form','添加')"/></li>
            <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                    onclick="deleteByIds('${ctx}/affair/affairCourseBuildStatistical/deleteByIds','checkAll','myCheckBox')"/>
            </li>
            <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
        </shiro:hasPermission>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairCourseBuildStatistical'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th id="handleTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
                                 onclick='chooseAll(this,"myCheckBox")'/>全选
        </th>
        <th>序号</th>
        <th>单位名称</th>
        <th>面授课程</th>
        <th>在线课程</th>
        <th>合计</th>
        <th>备注</th>
        <shiro:hasPermission name="affair:affairCourseBuildStatistical:edit">
            <th id="checkTh">操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairCourseBuildStatistical" varStatus="status">
        <tr>
            <td class="handleTd">
                <input style='margin-left:12px' type='checkbox' name="myCheckBox"
                       value="${affairCourseBuildStatistical.id}"/>
            </td>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairCourseBuildStatistical.unitName}
            </td>
            <td>
                    ${affairCourseBuildStatistical.msCourse}
            </td>
            <td>
                    ${affairCourseBuildStatistical.zxCourse}
            </td>
            <td>
                    ${affairCourseBuildStatistical.total}
            </td>
            <td>
                    ${affairCourseBuildStatistical.remarks}
            </td>
            <shiro:hasPermission name="affair:affairCourseBuildStatistical:edit">
                <td class="checkTd">
                    <a href="javascript:void(0);"
                       onclick="openDialog('${ctx}/affair/affairCourseBuildStatistical/form?id=${affairCourseBuildStatistical.id}','修改')">修改</a>
                    <a href="${ctx}/affair/affairCourseBuildStatistical/delete?id=${affairCourseBuildStatistical.id}"
                       onclick="return confirmx('确认要删除该铁路公安机关课程建设情况统计吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>