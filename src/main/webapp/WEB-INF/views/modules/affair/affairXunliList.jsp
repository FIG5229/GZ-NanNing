<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>训厉管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
        });
        //分页
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        //详情弹窗
        function openDetailDialog(idNumber) {
            var url = "iframe:${ctx}/affair/affairXunLi/formDetail?idNumber=" + idNumber;
            top.$.jBox.open(url, "训历管理详细信息", 800, 500, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }
            });
        };

        function exportData(idNumber,name){
            var submit = function (v) {
                if (v == 'all') {
                    $("#searchForm").attr("action","${ctx}/affair/affairXunLi/export?idNumber="+idNumber+"&name="+name+"&fileName=训历管理导出.xlsx");
                    $("#searchForm").submit();
                    $("#searchForm").attr("action","${ctx}/affair/affairXunLi/list?organizationId=${organizationId}&organization=${organization}");
                }

                if (v == 'cancel') {
                    $.jBox.tip('已取消');
                }
                return true;
            };
            $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出数据': 'all','取消':'cancel'} });
        };

        //导出训历档案
        function openExportForm(idNumber) {
            var submit = function (v, h, f) {
                if (v == 'confirm') {
                    self.location.href = "${ctx}/affair/affairXunLi/export?idNumber="+idNumber+"&fileName=训历管理导出.xlsx";
                }
                if (v == 'cancel') {
                    $.jBox.tip('已取消');
                }
                return true;
            };
            $.jBox.confirm("您是否要导出训历档案?", "数据导出确认", submit, { buttons: { '确认': 'confirm', '取消':'cancel'} });

        }

        //训厉档案弹窗
        function openRecordDetailDialog(idNumber) {
            var url = "iframe:${ctx}/affair/affairXunLi/job?idNumber=" + idNumber;
            top.$.jBox.open(url, "训历档案", 800, 500, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }
            });
        };
    </script>
</head>
<body>

<form:form id="searchForm" modelAttribute="affairXunLi" action="${ctx}/affair/affairXunLi/list" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="训历管理导出.xlsx"/>
    <ul class="ul-form">
        <li><label>身份证号：</label>
            <form:input path="idNumber" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>警号：</label>
            <form:input path="policeIdNumber" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>姓名：</label>
            <form:input path="name" htmlEscape="false" class="input-medium"/>
        </li>
       <%-- <li><label>人员类别：</label>
            <form:select path="personnelType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('person_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>--%>
        <li><label>机构：</label>
            <sys:treeselect id="organization" name="organizationId" value="${affairXunLi.organizationId}" labelName="organization" labelValue="${affairXunLi.organization}"
                            title="机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
        </li>

       <%-- <li><label>机构：</label>
            <form:input path="organization" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>域：</label>
            <sys:treeselect id="region" name="regionId" value="${affairXunLi.regionId}" labelName="region"
                            labelValue="${affairXunLi.region}"
                            title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>--%>
        </ul>
        <ul class="ul-form2">
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                    onclick="window.location.href='${ctx}/affair/affairXunLi/list?organizationId=${organizationId}&organization=${organization}'"/></li>
            <li class="clearfix">x</li>
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
        <th>性别</th>
        <th>警号</th>
        <th>身份证号</th>
        <th>机构</th>
        <%--<th>行政职务</th>
        <th>警衔</th>--%>

        <th style="width: 245px">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairXunLi" varStatus="status">
        <tr>
            <td class="checkTd">
                <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairXunLi.id}"/>
            </td>
            <td>
                ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>

            <td>
               ${affairXunLi.name}
            </td>
            <td>
                ${fns:getDictLabel(affairXunLi.sex, 'sex', '')}
            </td>
            <td>
                    ${affairXunLi.policeIdNumber}
            </td>
            <td>
                    ${affairXunLi.idNumber}
            </td>
            <td>
                ${affairXunLi.organization}
            </td>
            <%--<td>
                ${fns:getDictLabel(affairXunLi.administrativePost, 'administration_post', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairXunLi.policeRank, 'police_rank', '')}
            </td>--%>
            <td>
                <a onclick="openDetailDialog('${affairXunLi.idNumber}')" style="cursor: pointer">查看</a>
                <a onclick="openRecordDetailDialog('${affairXunLi.idNumber}')" style="cursor: pointer">查看训历档案</a>
                <a onclick="exportData('${affairXunLi.idNumber}','${affairXunLi.name}')" style="cursor: pointer;">导出训历档案</a>
               <%-- <a href="javascript:void(0)" onclick="openExportForm('${affairXunLi.idNumber}')">导出训历档案</a>--%>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>