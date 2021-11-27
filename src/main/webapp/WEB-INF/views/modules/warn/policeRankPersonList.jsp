<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警衔测算</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript"  src="${ctxStatic}/js/export.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				submitHandler: function(form){
					loading('正在加载，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});


			$("#print").click(function(){
				$("#contentTable").printThis({
					debug: false,
					importCSS: true,
					importStyle: true,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false
				});
			});
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceRank/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceRank/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceRank/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceRank/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "警衔信息管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/personnel/personnelPoliceRank/formDetail?id="+id;
            top.$.jBox.open(url, "警衔信息",900,500,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }

        function exportData() {
			dataExport("contentTable");
		}
		function queryData() {
			$("#searchForm").attr("action", "${ctx}/personnel/personnelPoliceRank/calculateBeta?removeCache=is");
			$("#searchForm").submit();
			$("#searchForm").attr("action", "${ctx}/personnel/personnelPoliceRank/calculateBeta");
		}
    </script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="personnelPoliceRank" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value=""/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="mType" name="mType" type="hidden" value="${mType}"/>
		<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceRank.idNumber}"/>
		<input id="fileName" name="fileName" type="hidden" value="衔称信息集.xlsx"/>
		<ul class="ul-form">
			<li><label>截止日期：<span><fmt:formatDate value="${endFinishDate}" pattern="yyyy-MM-dd"/></span> </label></li>
				<%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
			<li class="btns"><input id="exportCeSuan" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>出生日期</th>
				<th>单位</th>
				<th>参加工作日期</th>
				<th>学制年限</th>
				<th>职务</th>
				<th>职务层次</th>
				<th>任职时间</th>
				<th>变动原因</th>
				<th>现任警衔</th>
				<th>现任警衔起算日期</th>
				<th>拟授警衔</th>
				<th>新警衔起算日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${mingdanList}" var="personnnel" varStatus="status">
			<tr>
				<td>
						${status.index+1}
				</td>
				<td>
						${personnnel.peopleName}
				</td>
				<td>
						${fns:getDictLabel(personnnel.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.birthdayTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnnel.unit}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.publicSecurityDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${personnnel.xuezhiYear}
				</td>
				<td>
						${personnnel.jobAbbreviation}
				</td>
				<td>
						${fns:getDictLabel(personnnel.jobLevel,"personnel_zwcc","")}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${personnnel.changeReason}
				</td>
				<td>
						${fns:getDictLabel(personnnel.policeRankLevel, "police_rank_level", "")}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(personnnel.policeRankLevel+1, "police_rank_level", "")}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.endDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>