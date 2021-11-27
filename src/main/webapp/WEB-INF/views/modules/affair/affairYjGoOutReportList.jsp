<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>民警因私外出报备管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
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
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        //添加，修改弹窗
        function openDialog(url, type) {
            top.$.jBox.open("iframe:" + url, "因私外出报备" + type, 1000, 600, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairYjGoOutReport"
                }
            });
        }

        //查看弹窗
        function openDetailDialog(url, type) {
            top.$.jBox.open("iframe:" + url, "因私外出报备" + type, 1000, 600, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairYjGoOutReport"
                }
            });
        }

        //提交送审
        function submitByIds() {
            if (null == $("#chuCheckManSel").val() || "" == $("#chuCheckManSel").val()) {
                $.jBox.tip('请选择审核单位');
                return false;
            }
            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                ids.push($(this).val());
            });
            if (ids.length > 0) {
                var idsStr = ids.join(",");
                $("#idsValue").val(idsStr);
                $("#searchForm2").submit();
            } else {
                $.jBox.tip('请先选择要提交的内容');
            }
        }


        //提交送审
        function plshByIds() {

            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                ids.push($(this).val());
            });
            if (ids.length > 0) {
                var idsStr = ids.join(",");
                $("#idsValue").val(idsStr);
                $("#searchForm2").submit();
            } else {
                $.jBox.tip('请先选择要提交的内容');
            }
        }

        //导出当前记录信息
        function exportInfo(id, name) {
            var submit = function (v, h, f) {
                if (v == 'confirm') {
                    self.location.href = "${ctx}/affair/affairYjGoOutReport/export?id=" + id;
                }
                if (v == 'cancel') {
                    $.jBox.tip('已取消');
                }
                return true;
            };
            $.jBox.confirm("您是否要导出" + name + "的因私外出报备单?", "数据导出确认", submit, {buttons: {'确认': 'confirm', '取消': 'cancel'}});

        }

        //打开审核弹窗
        function openShDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairYjGoOutReport/shenHeDialog?id="+id,"审核",800,420,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },
                closed:function (){
                    self.location.href="${ctx}/affair/affairYjGoOutReport/list";
                }
            });
        }


        //得到被选中的checkbox的数据id
        function getIds(checkBoxsName) {
            var ids = [];
            $("input:checkbox[type=checkbox]:checked").each(function () {
                if ($(this).val() != "on"){
                    ids.push($(this).val());
                }
            });
            return ids;
        };


        function plshByIds(url,checkAllId,checkBoxsName) {
            var plshIds = getIds(checkBoxsName);
            if (plshIds.length > 0) {
                $.ajax({
                    type:"post",
                    url:url,
                    data:{ids:plshIds},
                    dataType:"json",
                    success:function(data){
                        $.jBox.tip(data.message);
                        resetCheckBox(checkAllId,checkBoxsName);
                        location.reload();
                    }
                })
            } else {
                $.jBox.tip('请先选择要审核的内容');
            }
        };


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/affair/affairYjGoOutReport/">民警因私外出报备列表</a></li>
    <%--	<shiro:hasPermission name="affair:affairYjGoOutReport:edit"><li><a href="${ctx}/affair/affairYjGoOutReport/form">民警因私外出报备添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairYjGoOutReport" action="${ctx}/affair/affairYjGoOutReport/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>部门：</label>
            <sys:treeselect id="unitId" name="unitId" value="${affairYjGoOutReport.unitId}" labelName="unit"
                            labelValue="${affairYjGoOutReport.unit}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>
        <li><label>前往地区：</label>
            <form:input path="goPlace" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>离开时间：</label>
            <input name="leaveTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairYjGoOutReport.leaveTime}" pattern="yyyy-MM-dd HH:mm"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
        </li>
        <li><label>返回时间：</label>
            <input name="backTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairYjGoOutReport.backTime}" pattern="yyyy-MM-dd HH:mm"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>
        </li>
        <li><label>姓名：</label>
            <form:input path="name" htmlEscape="false" class="input-medium"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <c:if test="${fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e'}">
            <li class="btns"><input id="shtg" class="btn btn-primary" type="button" value="审核通过"
                                    onclick="plshByIds('${ctx}/affair/affairYjGoOutReport/plshByIds','checkAll','myCheckBox')"/>
            </li>
            <li class="btns"><input id="thzg" class="btn btn-primary" type="button" value="退回整改"
                                    onclick="plshByIds('${ctx}/affair/affairYjGoOutReport/plshthByIds','checkAll','myCheckBox')"/>
            </li>
        </c:if>
        <shiro:hasPermission name="affair:affairYjGoOutReport:edit">
            <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                    onclick="openDialog('${ctx}/affair/affairYjGoOutReport/form','添加')"/></li>
            <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                    onclick="deleteByIds('${ctx}/affair/affairYjGoOutReport/deleteByIds','checkAll','myCheckBox')"/>
            </li>

        </shiro:hasPermission>
        <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairYjGoOutReport'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
                                onclick='chooseAll(this,"myCheckBox")'/></th>
        <th>序号</th>
        <th>姓名</th>
        <th>事由</th>
        <th>部门名称</th>
        <th>前往地区</th>
        <th>离开时间</th>
        <th>返回时间</th>
        <th>审核状态</th>
        <th id="handleTh">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairYjGoOutReport" varStatus="status">
        <tr>
            <td class="checkTd">
                <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairYjGoOutReport.id}"/>
            </td>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairYjGoOutReport.name}
            </td>
            <td>
                    ${affairYjGoOutReport.thing}
            </td>
            <td>
                    ${affairYjGoOutReport.unit}
            </td>
            <td>
                    ${affairYjGoOutReport.goPlace}
            </td>
            <td>
                <fmt:formatDate value="${affairYjGoOutReport.leaveTime}" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td>
                <fmt:formatDate value="${affairYjGoOutReport.backTime}" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td>
                    ${fns:getDictLabel(affairYjGoOutReport.checkType, 'check_type', '')}
            </td>
            <td class="handleTd">
                <a href="javaScript:void(0)"
                   onclick="openDetailDialog('${ctx}/affair/affairYjGoOutReport/formDetail?id=${affairYjGoOutReport.id}','查看')">查看</a>
                <a href="javaScript:void(0)"
                   onclick="exportInfo('${affairYjGoOutReport.id}','${affairYjGoOutReport.name}')">导出</a>
                <c:choose>
                    <%--bfdf74f010c9466dba12c1589ecab7f3 南宁局政治部组织干部处  34e8d855cf6b4b1ab5e7e23e7aaba658  南宁处政治处组织干部室 ec3ba2efdd404f2faa520f6e8a71ec4c 柳州处政治处组织干部室  c90918faf2614baa8fa85230482bd43e 北海处政治处组织干部室--%>
                  <c:when test="${(affairYjGoOutReport.checkType eq '2' or affairYjGoOutReport.checkType eq '3') && (fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e')}">
                        <a href="javaScript:void(0)" onclick="openShDialog('${affairYjGoOutReport.id}')" style="cursor: pointer">审核</a>
                    </c:when>
                </c:choose>
                <%--0223问题修改增加撤销功能--%>
                <c:choose>
                    <c:when test="${(affairYjGoOutReport.checkType eq '3' or affairYjGoOutReport.checkType eq '4'  or affairYjGoOutReport.checkType eq '0') && (fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e')}">
                        <a href="${ctx}/affair/affairYjGoOutReport/revocation?id=${affairYjGoOutReport.id}" onclick="return confirmx('确认要撤销该民警因私外出报备吗？', this.href)">撤销审核</a>
                    </c:when>
                </c:choose>
                <shiro:hasPermission name="affair:affairYjGoOutReport:edit">
                    <a href="javaScript:void(0)"
                       onclick="openDialog('${ctx}/affair/affairYjGoOutReport/form?id=${affairYjGoOutReport.id}','修改')">修改</a>
                    <a href="${ctx}/affair/affairYjGoOutReport/delete?id=${affairYjGoOutReport.id}"
                       onclick="return confirmx('确认要删除该民警因私外出报备吗？', this.href)">删除</a>
                </shiro:hasPermission>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<form:form id="searchForm2" modelAttribute="affairYjGoOutReport" action="${ctx}/affair/affairYjGoOutReport/submitByIds"
           method="post" class="breadcrumb form-search">
    <ul class="ul-form" style="text-align: right">
        <font color="red">请选择申报单位：</font>
        <input type="hidden" name="ids" id="idsValue"/>
        <input type="hidden" name="chuCheckMan" id="chuCheckMan" value="${affairYjGoOutReport.chuCheckMan}"/>
        <input type="hidden" name="chuCheckId" id="chuCheckId" value="${affairYjGoOutReport.chuCheckId}"/>
        <form:select id="chuCheckManSel" path="chuCheckId"   class="input-xlarge required">
            <c:choose>
                <c:when test="${(fns:getUser().office.id eq '34' || fns:getUser().company.id eq '34') && fns:getUser().office.id ne '27'}">
                    <form:option value="34e8d855cf6b4b1ab5e7e23e7aaba658" label="南宁处政治处组织干部室"/>
                </c:when>
                <c:when test="${(fns:getUser().office.id eq '156' || fns:getUser().company.id eq '156') && fns:getUser().office.id ne '264'}">
                    <form:option value="c90918faf2614baa8fa85230482bd43e" label="北海处政治处组织干部室"/>
                </c:when>
                <c:when test="${(fns:getUser().office.id eq '95' || fns:getUser().company.id eq '95') && fns:getUser().office.id ne '142'}">
                    <form:option value="ec3ba2efdd404f2faa520f6e8a71ec4c" label="柳州处政治处组织干部室"/>
                </c:when>
                <c:when test="${(fns:getUser().office.id eq '1' || fns:getUser().company.id eq '1' || fns:getUser().office.id eq '27'||
                 fns:getUser().office.id eq '142'|| fns:getUser().office.id eq '264') && fns:getUser().office.id ne '95' && fns:getUser().office.id ne '156' && fns:getUser().office.id ne '34'}">
                    <form:option value="bfdf74f010c9466dba12c1589ecab7f3" label="南宁局政治部组织干部处"/>
                </c:when>
                <c:otherwise>
                    <form:option value="bfdf74f010c9466dba12c1589ecab7f3" label="南宁局政治部组织干部处"/>
                    <form:option value="34e8d855cf6b4b1ab5e7e23e7aaba658" label="南宁处政治处组织干部室"/>
                    <form:option value="ec3ba2efdd404f2faa520f6e8a71ec4c" label="柳州处政治处组织干部室"/>
                    <form:option value="c90918faf2614baa8fa85230482bd43e" label="北海处政治处组织干部室"/>
                </c:otherwise>
            </c:choose>
        </form:select>
        <input class="btn btn-primary" type="button" value="申报"
               onclick="submitByIds('${ctx}/affair/affairYjGoOutReport/submitByIds')"/>
    </ul>
</form:form>
</body>
</html>