<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>离退信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="personnelRetreat" action="${ctx}/personnel/personnelRetreatSum/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>离退类别：</label>
            <form:select path="type" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('personnel_lttype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>离退日期：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${personnelRetreat.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            -
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${personnelRetreat.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li>
            <label class="width140">现管理单位名称：</label>
            <sys:treeselect id="nowUnitName" name="nowUnitNameId" value="${personnelRetreat.nowUnitNameId}" labelName="nowUnitName" labelValue="${personnelRetreat.nowUnitName}"
                            title="离退后现管理单位名称" url="/sys/office/treeData?type=2"  allowClear="true" notAllowSelectParent="true" />
        </li>
        <li><label>姓名：</label>
            <form:input path="name" htmlEscape="false" class="input-medium"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelRetreatSum/list'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr><c:if test="${mType==null}">
        <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
    </c:if>
        <th>序号</th>
        <th>姓名</th>
        <th>离退类别</th>
        <th>离退前级别</th>
        <th>离退后现管理单位名称</th>
        <th>离退日期</th>
        <th>离退干部现享受待遇</th>
        <th>离退干部现享受待遇类别</th>
        <th>离退备注</th>
        <th>审核状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="personnelRetreat" varStatus="status">
        <tr><c:if test="${mType==null}">
            <td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelRetreat.id}"/></td>
        </c:if>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${personnelRetreat.name}
            </td>
            <td><a href="javascript:void(0)" onclick="openDetailDialog('${personnelRetreat.id}')">
                    ${fns:getDictLabel(personnelRetreat.type, 'personnel_lttype', '')}
            </a></td>
            <td>
                    ${fns:getDictLabel(personnelRetreat.preLevel, 'personnel_ltqtype', '')}
            </td>
            <td>
                    ${personnelRetreat.nowUnitName}
            </td>
            <td>
                <fmt:formatDate value="${personnelRetreat.date}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${personnelRetreat.treatment}
            </td>
            <td>
                    ${fns:getDictLabel(personnelRetreat.treatmentType, 'personnel_dytype', '')}
            </td>
            <td>
                    ${personnelRetreat.remarks}
            </td>
            <td>
                    ${fns:getDictLabel(personnelRetreat.status, 'personnel_retreat_status', '')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>