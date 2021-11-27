<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>教育训练培训模块管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            $("#btnPrint").click(function () {
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

            $("#btnExport").click(
                function () {
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairEducationTrain/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairEducationTrain/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairEducationTrain/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairEducationTrain/");
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
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairEducationTrain", "导入", 800, 520, {
                    itle: "导入数据", buttons: {"关闭": true},
                    bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
                        self.location.href = "${ctx}/affair/affairEducationTrain/list"
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
            top.$.jBox.open("iframe:" + url, "培训计划", 1200, 600, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairEducationTrain"
                }
            });
        }


        function openEditDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairEducationTrain/form?id=" + id, "培训计划修改", 1200, 600, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairEducationTrain"
                }
            });
        }

        function openDetailDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairEducationTrain/formDetail?id=" + id, "培训计划详情", 1200, 600, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairEducationTrain"
                }
            });
        }
        //添加审核弹窗
        function openDialog(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/affair/affairEducationTrain/shenHeDialog?id="+id, "审核",800,420,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
                        self.location.href="${ctx}/affair/affairEducationTrain/";
                    }
                });
            }else {
                $.jBox.tip('请先选择要审核的内容且只能单条审核');
            }
        }
        //批量审核
        function checkByIds(url, myCheckBox, checkBoxsName) {
            var editIds = getIds(checkBoxsName);
            if (editIds.length > 0) {
                var isStr = editIds.join(',');
                top.$.jBox.open("iframe:" + url + "?ids=" + isStr, "批量审核", 700, 520, {
                    buttons: {"关闭": true},
                    loaded: function () {
                        $(".jbox-content", top.document).css("overflow-y", "hidden");
                    }, closed: function () {
                        self.location.href = '${ctx}/affair/affairEducationTrain/list';
                    }
                });
            } else {
                $.jBox.tip('请先选择审核内容');
            }
        };

        if ("success" == "${saveResult}") {
            parent.$.jBox.close();
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/affair/affairEducationTrain/">培训计划</a></li>
    <%--		<shiro:hasPermission name="affair:affairEducationTrain:edit"><li><a href="${ctx}/affair/affairEducationTrain/form">教育训练培训模块添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairEducationTrain" action="${ctx}/affair/affairEducationTrain/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="教育训练培训计划表.xlsx"/>
    <ul class="ul-form">
            <%--			<li><label>培训年度：</label>--%>
            <%--				<form:input path="trainYear" htmlEscape="false" class="input-medium"/>--%>
            <%--			</li>--%>
            <li>
                <label>培训年度：</label>
                <input name="trainYear" path="trainYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                       value="${affairEducationTrain.trainYear}"
                       onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
            </li>
            <%--			<li><label>填报机构：</label>--%>
            <%--				<form:input path="unit" htmlEscape="false" class="input-medium"/>--%>
            <%--			</li>--%>
        <li><label>填报机构：</label>
            <sys:treeselect id="unit" name="unitId" value="${affairEducationTrain.unitId}" labelName="unit"
                            labelValue="${affairEducationTrain.unit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="false"/>
                <%--<form:input path="unit" htmlEscape="false" class="input-medium"/>--%>
        </li>
        <li><label>标题：</label>
            <form:input path="title" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label class="width110">填报结束时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairEducationTrain.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至 <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                     value="<fmt:formatDate value="${affairEducationTrain.endDate}" pattern="yyyy-MM-dd"/>"
                     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
<%--        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairEducationTrain:edit">
            <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                    onclick="openAddForm('${ctx}/affair/affairEducationTrain/form')"/></li>
            <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
            <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                    onclick="deleteByIds('${ctx}/affair/affairEducationTrain/deleteByIds','checkAll','myCheckBox')"/>
            </li>
            <li class="btns"><input class="btn btn-primary" type="button" value="审核" onclick="checkByIds('${ctx}/affair/affairEducationTrain/shenHeDialog','checkAll','myCheckBox')"/></li>
        </shiro:hasPermission>
        <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
        <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairEducationTrain'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
        <th>序号</th>
        <th>单位</th>
        <th>培训名称</th>
        <th>培训目的</th>
        <th>培训对象</th>
        <th>培训内容</th>
        <th>培训地点</th>
        <th>参训人数</th>
        <th>培训费</th>
        <th>师资费</th>
        <th>列支渠道</th>
        <th>实施状态</th>
        <shiro:hasPermission name="affair:affairEducationTrain:edit">
            <th class="handleTd">操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairEducationTrain" varStatus="status">
        <tr>

			<td class="checkTd">
				<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairEducationTrain.id}"/>
			</td>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairEducationTrain.unit}
             </td>
            <td>
                    ${affairEducationTrain.title}
            </td>
            <td>
                    ${affairEducationTrain.trainPurpose}
            </td>
            <td>
                    ${affairEducationTrain.trainObject}
            </td>
            <td>
                    ${affairEducationTrain.content}
            </td>
            <td>
                    ${affairEducationTrain.trainSite}
            </td>
            <td>
                    ${affairEducationTrain.trainCount}
            </td>
            <td>
                    ${affairEducationTrain.trainFees}
            </td>
            <td>
                    ${affairEducationTrain.teacherFees}
            </td>
            <td>
                    ${affairEducationTrain.listOfChannel}
            </td>
            <td>
                    ${affairEducationTrain.implementStatus}
            </td>
            <td class="handleTd">
                <a onclick="openDetailDialog('${affairEducationTrain.id}')">查看</a>
            <shiro:hasPermission name="affair:affairEducationTrain:edit">
                    <a href="javascript:;" onclick="openEditDialog('${affairEducationTrain.id}')">修改</a>
                    <a href="${ctx}/affair/affairEducationTrain/delete?id=${affairEducationTrain.id}"
                       onclick="return confirmx('确认要删除该教育训练培训模块吗？', this.href)">删除</a>
            </shiro:hasPermission>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>