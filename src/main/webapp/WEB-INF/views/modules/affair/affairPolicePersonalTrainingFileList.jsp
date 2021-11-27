<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警个人训历档案报表管理</title>
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
        //导入训历档案
        function openImportForm(idNumber,name) {
            var submit = function (v, h, f) {
                if (v == 'confirm') {
                    top.$.jBox.open("iframe:${ctx}/file/template/trainingFiledownload/view?id=affair_policePersonalTrainingFile&idNumber="+idNumber, name+"个人训历档案导入",800,520,{title:name+"个人训历档案导入", buttons:{"关闭":true},
                        bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
                        closed:function (){
                            self.location.href="${ctx}/affair/affairPolicePersonalTrainingFile/list?workunitId=${unitId}&workunitName=${unitName}&pageNo=${page.pageNo}&pageSize=${page.pageSize}"
                        }});
                }
                if (v == 'cancel') {
                    $.jBox.tip('已取消');
                }
                return true;
            };
            $.jBox.confirm("您是否要更新"+name+"的训历档案?", "数据导入确认", submit, { buttons: { '确认': 'confirm', '取消':'cancel'} });
        }
        //导出训历档案
        function openExportForm(idNumber,name) {
            var submit = function (v, h, f) {
                if (v == 'confirm') {
                    self.location.href = "${ctx}/affair/affairPolicePersonalTrainingFile/export?idNumber="+idNumber+"&fileName=民警个人训历档案导出.xlsx";
                }
                if (v == 'cancel') {
                    $.jBox.tip('已取消');
                }
                return true;
            };
            $.jBox.confirm("您是否要导出"+name+"的训历档案?", "数据导出确认", submit, { buttons: { '确认': 'confirm', '取消':'cancel'} });

        }
        function openDetailDialog(idNumber,name) {
            var url = "iframe:${ctx}/affair/affairPolicePersonalTrainingFile/formDetail?idNumber="+idNumber;
            top.$.jBox.open(url, name+"个人训历档案",1000,600,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairPolicePersonalTrainingFile/list?workunitId=${unitId}&workunitName=${unitName}">民警个人训历档案报表</a></li>
		<%--<shiro:hasPermission name="affair:affairPolicePersonalTrainingFile:edit"><li><a href="${ctx}/affair/affairPolicePersonalTrainingFile/form">民警个人训历档案报表添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="personnelBase" action="${ctx}/affair/affairPolicePersonalTrainingFile/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
            <li><label>姓名：</label>
                <form:input path="name" htmlEscape="false" class="input-medium"/>
            </li>
            <li><label>警号：</label>
                <form:input path="policeIdNumber" htmlEscape="false" class="input-medium"/>
            </li>
            <li><label>身份证号：</label>
                <form:input path="idNumber" htmlEscape="false" class="input-medium"/>
            </li>
            <li><label>单位：</label>
                <sys:treeselect id="workunitName" name="workunitId" value="${personnelBase.workunitId}" labelName="workunitName" labelValue="${personnelBase.workunitName}"
                                title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" />
                <%--disabled="disabled" 11.14--%>
            </li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPolicePersonalTrainingFile/list?workunitId=${unitId}&workunitName=${unitName}'"/></li>
            <li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <%--<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>--%>
            <th>序号</th>
            <th>单位</th>
            <th>姓名</th>
            <th>身份证号</th>
            <th>警号</th>
            <th>职务简称</th>
            <th>人员状态</th>
            <shiro:hasPermission name="affair:affairPolicePersonalTrainingFile:edit"><th id="handleTh">操作</th></shiro:hasPermission>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="personnelBase" varStatus="status">
            <tr>

                <%--<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelBase.id}"/></td>--%>
                <td>
                        ${(page.pageNo-1)*page.pageSize+status.index+1}
                </td>
                <td>
                        ${personnelBase.workunitName}
                </td>
                <td>
                        ${personnelBase.name}
                </td>
                <td>${personnelBase.idNumber}</td>
                <td>
                        ${personnelBase.policeIdNumber}
                </td>
                <td>
                        ${personnelBase.jobAbbreviation}
                </td>
                <td>
                        ${fns:getDictLabel(personnelBase.status, 'personnel_status', '')}
                </td>
                <td class="handleTd">
                    <a href="javascript:void(0)" onclick="openDetailDialog('${personnelBase.idNumber}','${personnelBase.name}')">查看</a>
                <shiro:hasPermission name="affair:affairPolicePersonalTrainingFile:edit">
                    <a href="javascript:void(0)" onclick="openImportForm('${personnelBase.idNumber}','${personnelBase.name}')">导入训历档案</a>
                </shiro:hasPermission>
                    <a href="javascript:void(0)" onclick="openExportForm('${personnelBase.idNumber}','${personnelBase.name}')">导出训历档案</a>
                </td>
            </tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>