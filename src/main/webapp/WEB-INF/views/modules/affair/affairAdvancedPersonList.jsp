<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>读书先进--个人管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
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
								$("#searchForm").attr("action","${ctx}/affair/affairAdvancedPerson/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAdvancedPerson/list?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairAdvancedPerson/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAdvancedPerson/list");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairAdvancedPerson", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairAdvancedPerson/list"}});
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairAdvancedPerson/form?id="+id,"",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				}
			});
		}

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, " ",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}


		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairAdvancedPerson/formDetail?id="+id,"",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairAdvancedPerson"
				}
			});
		}


	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairBookCatalog/">历年书目列表</a></li>
		<li><a href="${ctx}/affair/affairAdvancedCollective/ ">读书先进--集体</a></li>
		<li class="active"><a href="${ctx}/affair/affairAdvancedPerson/ ">读书先进--个人</a></li>
		<li><a href="${ctx}/affair/affairActive/ ">活动情况</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairAdvancedPerson" action="${ctx}/affair/affairAdvancedPerson/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="读书先进个人表.xlsx"/>
		<ul class="ul-form">
			<li><label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairAdvancedPerson.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairAdvancedPerson:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairAdvancedPerson/form?id=${affairAdvancedPerson.id}')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairAdvancedPerson/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairAdvancedPerson'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>年度</th>
				<th>姓名</th>
				<th>读书心得</th>
				<shiro:hasPermission name="affair:affairAdvancedPerson:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairAdvancedPerson" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairAdvancedPerson.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairAdvancedPerson.year}
				</td>
				<td>
					${affairAdvancedPerson.name}
				</td>
				<td>
					${affairAdvancedPerson.description}
				</td>
				<td class="handleTd">
					<a href="javascript:;" onclick="openDetailDialog('${affairAdvancedPerson.id}')">查看</a>
					<shiro:hasPermission name="affair:affairAdvancedPerson:edit">
						<a href="javascript:;" onclick="openEditDialog('${affairAdvancedPerson.id}')">修改</a>
						<a href="${ctx}/affair/affairAdvancedPerson/delete?id=${affairAdvancedPerson.id}" onclick="return confirmx('确认要删除该读书先进--个人吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>