<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党员花名册管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/export?partyBranchId=${treeId}&cardNum=${pmId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/export?flag=true&partyBranchId=${treeId}&cardNum=${pmId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}");
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPartyMember", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
		//添加修改详情弹窗
		function openAddEditDialog(id,flag) {
			var url = "";
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairPartyMember/form";
			}else{
				if (flag == "1"){
					url = "iframe:${ctx}/affair/affairPartyMember/form?id="+id+"&flag="+flag;
				}else{
					url = "iframe:${ctx}/affair/affairPartyMember/form?id="+id;
				}
			}
			top.$.jBox.open(url, "党员花名册",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}";
				}
			});
		};
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyMember/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairPartyMember/form";
			}
			top.$.jBox.open(url, "党员花名册",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPartyMember/list?partyBranchId=${treeId}&cardNum=${pmId}";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyMember/formDetail?id="+id;
			top.$.jBox.open(url, "党员花名册",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};	//详情弹窗
		function openDetailInfoDialog(id) {
			var url = "iframe:${ctx}/affair/affairPartyMember/list?id="+id+"&isDialog=isDialog";
			top.$.jBox.open(url, "党员花名册",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>

	<form id="searchForm"  action="${ctx}/sys/charts/partyMemberDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

		<input id="dateType" name="dateType" type="hidden" value="${type}"/>
		<input id="dateStart" name="dateStart" type="hidden" value="${startDate}"/>
		<input id="dateEnd" name="dateEnd" type="hidden" value="${endDate}"/>
		<input id="year" name="year" type="hidden" value="${year}"/>
		<input id="month" name="month" type="hidden" value="${month}"/>
<%--		借用name属性存放查看的明细类型--%>
		<input id="name" name="name" type="hidden" value="${flag}"/>
	</form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--<th>
					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>--%>
				<th>序号</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>所在党支部</th>
				<th>人员类别</th>
				<th>学历</th>
				<th>加入党组织日期</th>
				<th>转为正式党员日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPartyMember" varStatus="status">
			<tr>
			<%--	<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPartyMember.id}"/>
				</td>--%>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>

						${affairPartyMember.name}

				</td>
				<td>
					${affairPartyMember.cardNum}
				</td>
				<td>
					${affairPartyMember.partyBranch}
				</td>
				<td>
					${fns:getDictLabel(affairPartyMember.personnelCategory, 'affair_personnel_category', '')}
				</td>
				<td>
					${fns:getDictLabel(affairPartyMember.education, 'affair_party_member_xueli', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPartyMember.joinDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairPartyMember.turnDate}" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>