<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>典型人物管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});

			$("#btnExport").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTypicalPerson/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTypicalPerson/list?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTypicalPerson/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTypicalPerson/list");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTypicalPerson", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTypicalPerson/list"}});
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openAddForm(url) {
			top.$.jBox.open("iframe:"+url, "典型人物",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTypicalPerson"}
			});
		}


		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTypicalPerson/form?id="+id,"典型人物编辑",800,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairTypicalPerson"}
			});
		}
		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTypicalPerson/formDetail?id="+id,"典型人物详情",1000,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairTypicalPerson"

				}
			});
		}

        
	</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="affairTypicalPerson" action="${ctx}/affair/affairTypicalPerson/typicalDetail" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="type" name="type" type="hidden" value="${type}"/>
		<input id="year" name="year" type="hidden" value="${affairTypicalPerson.year}"/>
		<input id="month" name="month" type="hidden" value="${affairTypicalPerson.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairTypicalPerson.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairTypicalPerson.label}"/>
		<input name="beginPsTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate required"
			   value="<fmt:formatDate value="${affairTypicalPerson.beginPsTime}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<input name="endPsTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate required"
			   value="<fmt:formatDate value="${affairTypicalPerson.endPsTime}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>政治面貌</th>
				<th>单位</th>
				<th>培树时间</th>
				<th>培树级别</th>
				<th>培树目标</th>
				<th>培树部门</th>
				<th>联系人</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTypicalPerson" varStatus="status">
			<tr>

				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTypicalPerson.name}
				</td>
				<td>
					${fns:getDictLabel(affairTypicalPerson.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabel(affairTypicalPerson.politicsFace, 'political_status', '')}
				</td>
				<td>
					${affairTypicalPerson.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairTypicalPerson.psTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairTypicalPerson.psLevel, 'affair_approval_unitLevel', '')}
				</td>
				<td>
					${affairTypicalPerson.psTarget}
				</td>
				<td>
					${affairTypicalPerson.psDepartment}
				</td>
				<td>
					${affairTypicalPerson.contactPerson}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>