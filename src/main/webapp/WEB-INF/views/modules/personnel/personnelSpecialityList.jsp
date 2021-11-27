<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专长信息集管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelSpeciality/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelSpeciality/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelSpeciality/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelSpeciality/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

//这是打印功能的JS
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
		});
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelSpeciality/formDetail?id="+id;
			top.$.jBox.open(url, "专长信息",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelSpeciality", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}

		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "专长信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href=rUrl
				}
			});
		}

		//添加修改弹窗
		function openAddEditDialog(url,rUrl) {
			var url = "iframe:"+url;
			top.$.jBox.open(url, "专长信息",900,600,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href=rUrl;
				}
			});
		};

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		//导入履历信息-新，增加判断 覆盖/插入
		// 打开导入页面请求路径由 ${ctx}/file/template/download/view?id= xxxx
		// 调整为 ${ctx}/file/template/personnelBasesdownload/view?id= xxxx &isCover=1
		function openImportForm(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelSpeciality&isCover=1","专长信息导入",800,520,{title:"专长信息导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelSpeciality&isCover=0","专长信息导入",800,520,{title:"专长信息导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'cancel') {
					$.jBox.tip('已取消');
				}
				return true;
			};
			$.jBox.confirm("请选择导入模式?", "数据导入确认", submit, { buttons: { '覆盖': 'cover', '插入': 'insert','取消':'cancel'} });
		}

	</script>
</head>
<body>
<c:choose>
<c:when test="${mType eq '2'}"></c:when>
<c:when test="${mType eq '1'}">
<ul class="nav nav-tabs">
	<li><a href="${ctx}/personnel/personnelTalents/">人才库</a></li>
	<li class="active"><a href="${ctx}/personnel/personnelSpeciality/?mType=1">专长信息</a></li>
	<li><a href="${ctx}/personnel/personnelSkill?mType=1">高层次人才</a></li>
</ul>
	<form:form id="searchForm" modelAttribute="personnelSpeciality" action="${ctx}/personnel/personnelSpeciality/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="mType" name="mType" type="hidden" value="${mType}"/>
		<input id="fileName" name="fileName" type="hidden" value="专长信息集.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="personnelName" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="personnel:personnelSpeciality:edit">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog('${ctx}/personnel/personnelSpeciality/jumpForm?mType=1','${ctx}/personnel/personnelSpeciality?mType=1')" value="添加"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelSpeciality/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelSpeciality/?mType=1')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelSpeciality/?mType=1'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
</c:when>
<c:otherwise>
<form:form id="searchForm" modelAttribute="personnelSpeciality" action="${ctx}/personnel/personnelSpeciality/" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<input id="mType" name="mType" type="hidden" value="${mType}"/>
	<input id="fileName" name="fileName" type="hidden" value="专长信息集.xlsx"/>
	<ul class="ul-form">
		<li><label>姓名：</label>
			<form:input path="personnelName" htmlEscape="false" class="input-medium"/>
		</li>
	</ul>
	<ul class="ul-form2">
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
		<shiro:hasPermission name="personnel:personnelSpeciality:edit">
			<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog('${ctx}/personnel/personnelSpeciality/jumpForm?idNumber=${personnelSpeciality.idNumber}','${ctx}/personnel/personnelSpeciality?idNumber=${personnelSpeciality.idNumber}')" value="添加"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelSpeciality/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelSpeciality/?idNumber=${personnelSpeciality.idNumber}')"/></li>
		</shiro:hasPermission>
		<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
		<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelSpeciality/?idNumber=${personnelSpeciality.idNumber}'"/></li>
		<li class="clearfix"></li>
	</ul>
</form:form>
</c:otherwise>
</c:choose>
<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>身份证号</th>
				<th>出生年月</th>
				<th>特长</th>
				<shiro:hasPermission name="personnel:personnelSpeciality:edit"><th class="handleTd">操作</th></shiro:hasPermission>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelSpeciality" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelSpeciality.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${personnelSpeciality.personnelName}
				</td>
				<td>
						${fns:getDictLabel(personnelSpeciality.sex, 'sex', '')}
				</td>
				<td>
						${personnelSpeciality.idNumber}
				</td>
				<td>
						<fmt:formatDate value="${personnelSpeciality.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${personnelSpeciality.speciality}
				</td>
				<td class="handleTd">
					<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
				<shiro:hasPermission name="personnel:personnelSpeciality:edit">
					<a href="javascript:void(0)" onclick="openDetailDialog('${personnelSpeciality.id}')">查看</a>
					<a href="javascript:void(0)" onclick=openForm('${ctx}/personnel/personnelSpeciality/form?id=${personnelSpeciality.id}','${ctx}/personnel/personnelSpeciality?mType=1')>修改</a>
					<a href="${ctx}/personnel/personnelSpeciality/delete?id=${personnelSpeciality.id}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
    			　</shiro:hasPermission>
					</c:when>
					<c:otherwise>
					<shiro:hasPermission name="personnel:personnelSpeciality:edit">
						<a href="javascript:void(0)" onclick="openDetailDialog('${personnelSpeciality.id}')">查看</a>
						<a href="javascript:void(0)" onclick=openForm('${ctx}/personnel/personnelSpeciality/form?id=${personnelSpeciality.id}','${ctx}/personnel/personnelSpeciality?idNumber=${personnelSpeciality.idNumber}')>修改</a>
						<a href="${ctx}/personnel/personnelSpeciality/delete?id=${personnelSpeciality.id}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
						　</shiro:hasPermission>
					</c:otherwise>
					</c:choose>
				</td>

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
