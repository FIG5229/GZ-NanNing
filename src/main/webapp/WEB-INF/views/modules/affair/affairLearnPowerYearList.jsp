<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学习强国--年度统计管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

            //处分种类改变时触发事件，联动子选项
            $("#method").change(function(){
                showAndHide();
            });
            //控制处分种类子选项下拉框的隐藏与显示
            function showAndHide(){
                if($("#method").val() == '1' || $("#method").val() == '2' || $("#method").val() == '3' || $("#method").val() == '5'){
                    $('#otherMethod').css('display', 'none');
                }else if($("#method").val() == '4'){
                    $('#otherMethod').css('display', 'inline-block');
                }
            }
            showAndHide();
            $("#export").click(
                function(){
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action","${ctx}/affair/affairLearnPowerYear/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairLearnPowerYear/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairLearnPowerYear/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairLearnPowerYear/");
                        }
                        if (v == 'cancel') {
                            $.jBox.tip('已取消');
                        }
                        return true;
                    };
                    $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
                }
            );
            $("#print").click(function(){
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
                    pageTitle: "打印",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: false,
                    afterPrint:function(){
                        $('#handleTh').css('display', 'table-cell');
                        $('.handleTd').css('display', 'table-cell');
                    }
                });
            });
            $("#btnImport").click(function(){
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLearnPowerYear", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLearnPowerYear"}});
            });

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

<ul class="nav nav-tabs">
    <shiro:hasPermission name="affair:affairLearnDaily:view">
        <li>
            <a href="${ctx}/affair/affairLearnDaily/">单位政治理论学习</a>
        </li>
    </shiro:hasPermission>
    <shiro:hasPermission name="affair:affairLearnPower:view">
        <li class="active">
            <a href="${ctx}/affair/affairLearnPower/">学习强国</a>
        </li>
    </shiro:hasPermission>
    <shiro:hasPermission name="affair:affairNetworkCollege:view">
        <li>
            <a href="${ctx}/affair/affairNetworkCollege/">中国干部网络学院</a>
        </li>
    </shiro:hasPermission>
</ul>
<ul class="nav nav-tabs">
    <shiro:hasPermission name="affair:affairLearnPower:view">
        <li>
            <a href="${ctx}/affair/affairLearnPower/">月度学习强国</a>
        </li>
    </shiro:hasPermission>
    <shiro:hasPermission name="affair:affairLearnPowerYear:view">
        <li  class="active">
            <a  href="${ctx}/affair/affairLearnPowerYear/">年度学习强国</a>
        </li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="affairLearnPowerYear" action="${ctx}/affair/affairLearnPowerYear/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="学习强国--年度计划表.xlsx"/>
    <ul class="ul-form">
        <li>
            <label>姓名：</label>
            <form:input path="name" htmlEscape="false" class="input-medium"/>
        </li>
        <li>
            <label>年度：</label>
            <input name="year" path="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${year}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <%--<shiro:hasPermission name="affair:affairLearnPowerYear:edit">
            <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
        </shiro:hasPermission>--%>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLearnPowerYear'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>序号</th>
                <th>单位</th>
                <th>时间</th>
                <th>姓名</th>
                <th>身份证号码</th>
               <%-- <th>去年学习积分</th>
                <th>本年及去年学习积分总和</th>--%>
                <th>本年学习积分</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLearnPowerYear" varStatus="status">
			<tr>
                <td>
                        ${(page.pageNo-1)*page.pageSize+status.index+1}
                </td>
                <td>
                    ${affairLearnPowerYear.unit}
                </td>
				<td>
                    ${affairLearnPowerYear.time}
                </td>
                <td>
                    ${affairLearnPowerYear.name}
                </td>
                <td>
                        ${affairLearnPowerYear.idNumber}
                </td>
              <%--  <td>
                    ${affairLearnPowerYear.lastYearIntegral}
                </td>
                <td>
                     ${affairLearnPowerYear.thisYearStatistics}
                </td>--%>
                <td>
                     ${affairLearnPowerYear.thisYearIntegral}
                </td>

            <%--				<shiro:hasPermission name="affair:affairLearnPowerYear:edit"><td>
    				<a href="${ctx}/affair/affairLearnPowerYear/form?id=${affairLearnPowerYear.id}">修改</a>
					<a href="${ctx}/affair/affairLearnPowerYear/delete?id=${affairLearnPowerYear.id}" onclick="return confirmx('确认要删除该学习强国--年度统计吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>