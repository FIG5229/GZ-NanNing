<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>子女信息管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#export").click(
                function(){
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action","${ctx}/affair/affairZxInfo/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairZxInfo/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairZxInfo/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairZxInfo/");
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
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairZxInfo", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairZxInfo"}});
            });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function openDialog(url) {
            top.$.jBox.open("iframe:"+url, "助学",1150,650,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairZxInfo"}
            });
        }
        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/affair/affairZxInfo/formDetail?id="+id;
            top.$.jBox.open(url, "助学详情",900,500,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairZxInfo" action="${ctx}/affair/affairZxInfo/studyAidDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="year" type="hidden" value="${affairZxInfo.year}"/>
		<input id="month" name="month" type="hidden" value="${affairZxInfo.month}"/>
		<input id="dateStart" name="dateStart" type="hidden" value="${affairZxInfo.dateStart}"/>
		<input id="dateEnd" name="dateEnd" type="hidden" value="${affairZxInfo.dateEnd}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairZxInfo.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairZxInfo.label}"/>

	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th>序号</th>
				<th>时间</th>
				<th>单位</th>
				<th>民警姓名</th>
				<th>补助金额（元）</th>
				<th>子女姓名</th>
				<th>子女性别</th>
				<th>子女就读学校（专业）</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairZxInfo" varStatus="status">
			<tr>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairZxInfo.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairZxInfo.unit}
				</td>
				<td>
						${affairZxInfo.name}
				</td>
				<td>
						${affairZxInfo.money}
				</td>
				<td>
						${affairZxInfo.childName}
				</td>
				<td>
						${fns:getDictLabel(affairZxInfo.childSex, 'sex', '')}
				</td>
				<td>
						${affairZxInfo.childSchoolMajor}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>