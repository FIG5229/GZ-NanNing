<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        //加一个function用来实现审核的弹窗
        function openDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairTousujubaoguanli/shenHeDialog?id="+id, "个人事项报告审核",800,420,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){
                    self.location.href="${ctx}/affair/affairTousujubaoguanli/manageList";
                }
            });
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li ><a href="${ctx}/affair/affairTousujubaoguanli/">投诉举报列表</a></li>
    <li class="active"><a href="${ctx}/affair/affairTousujubaoguanli/manageList">投诉审核举报列表</a></li>
    <%--<shiro:hasPermission name="affair:affairTousujubaoguanli:edit"><li><a href="${ctx}/affair/affairTousujubaoguanli/form">投诉举报添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairTousujubaoguanli" action="${ctx}/affair/affairTousujubaoguanli/manageList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>投诉方式：</label>
            <form:select path="complaintWay" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_tousu')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>举报人姓名：</label>
            <form:input path="informer" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>被举报人姓名：</label>
            <form:input path="repoter" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>被举报人单位：</label>
            <sys:treeselect id="repoterUnit" name="repoterUnitId" value="${affairTousujubaoguanli.repoterUnitId}" labelName="repoterUnit" labelValue="${affairTousujubaoguanli.repoterUnit}"
                            title="被举报人单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
        </li>
        <li><label>问题类型：</label>
            <form:select path="questionType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_wenti')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>录入时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTousujubaoguanli.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTousujubaoguanli.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>收到时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTousujubaoguanli.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTousujubaoguanli.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>批转单位：</label>
            <sys:treeselect id="forwardUnit" name="forwardUnitId" value="${affairTousujubaoguanli.forwardUnitId}" labelName="forwardUnit" labelValue="${affairTousujubaoguanli.forwardUnit}"
                            title="批转单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
        </li>
        <li><label>是否查实：</label>
            <form:select path="ischeck" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>是否纪律处分：</label>
            <form:select path="isdispose" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>是否重复：</label>
            <form:select path="isrepeat" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>举报人</th>
        <th>举报人单位</th>
        <th>被举报人</th>
        <th>被举报人单位</th>
        <th>问题类型</th>
        <th>录入时间</th>
        <th>是否查实</th>
        <th>是否纪律处分</th>
        <th>审核状态</th>
        <shiro:hasPermission name="affair:affairTousujubaoguanli:manage"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairTousujubaoguanli" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td><a href="${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}">
                    ${affairTousujubaoguanli.informer}
            </a></td>
            <td>
                    ${affairTousujubaoguanli.informerUnit}
            </td>
            <td>
                    ${affairTousujubaoguanli.repoter}
            </td>
            <td>
                    ${affairTousujubaoguanli.repoterUnit}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.questionType, 'affair_wenti', '')}
            </td>
            <td>
                <fmt:formatDate value="${affairTousujubaoguanli.entryTime}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.ischeck, 'yes_no', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.isdispose, 'yes_no', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.approvalStatus, 'affair_query_shenhe', '')}
            </td>
            <shiro:hasPermission name="affair:affairTousujubaoguanli:manage"><td>
                <a onclick="openDialog('${affairTousujubaoguanli.id}')">审核</a>
            </td></shiro:hasPermission>
            <td></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>