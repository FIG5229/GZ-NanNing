<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>硬件建设管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairYjBuild/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairYjBuild/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairYjBuild/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairYjBuild/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_yjBuild", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairYjBuild"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加，修改弹窗
		function openDialog(url,type) {
			top.$.jBox.open("iframe:"+url, "硬件建设"+type,1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairYjBuild/formDetail?id="+id;
			top.$.jBox.open(url, "硬件建设详情",1000,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//提交送审
		function submitByIds() {
			if(null == $("#oneCheckMan").val() || "" ==  $("#oneCheckMan").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				//console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		}
		//打开审核弹窗
		function openShDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairYjBuild/shenHeDialog?id="+id,"硬件建设审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairYjBuild/list";
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairYjBuild/">硬件建设</a></li>
		<%--<shiro:hasPermission name="affair:affairYjBuild:edit"><li><a href="${ctx}/affair/affairYjBuild/form">硬件建设添加</a></li></shiro:hasPermission>
	--%></ul>
	<form:form id="searchForm" modelAttribute="affairYjBuild" action="${ctx}/affair/affairYjBuild/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="5.1硬件建设 .xlsx"/>
		<ul class="ul-form">
			<li><label>建设时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairYjBuild.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairYjBuild.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="proName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>建设单位：</label>
				<form:input path="buildUnit" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairYjBuild:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairYjBuild/form','添加')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairYjBuild/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<shiro:hasPermission name="affair:affairYjBuild:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairYjBuild'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/></th>
				<th>序号</th>
				<th>项目名称</th>
				<th>建设时间</th>
				<th>建设单位</th>
				<th>审核状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairYjBuild" varStatus="status">
			<tr>
				<td  class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairYjBuild.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairYjBuild.proName}
				</td>
				<td>
					<fmt:formatDate value="${affairYjBuild.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairYjBuild.buildUnit}
				</td>
				<td>
					${fns:getDictLabel(affairYjBuild.checkType, 'check_type', '')}
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairYjBuild.id}')" style="cursor: pointer">查看</a>
					<shiro:hasPermission name="affair:affairYjBuild:edit">
    				<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairYjBuild/form?id=${affairYjBuild.id}','修改')">修改</a>
					<a href="${ctx}/affair/affairYjBuild/delete?id=${affairYjBuild.id}" onclick="return confirmx('确认要删除该硬件建设吗？', this.href)">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairYjBuild:shenhe">
						<c:if test="${'5'== fns:getUser().office.id ||  '29'== fns:getUser().office.id || '144'== fns:getUser().office.id || '265'== fns:getUser().office.id }">
							<c:if test="${affairYjBuild.oneCheckId == fns:getUser().id || affairYjBuild.twoCheckId == fns:getUser().id }">
								<a onclick="openShDialog('${affairYjBuild.id}')" style="cursor: pointer">审核</a>
							</c:if>
						</c:if>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<shiro:hasPermission name="affair:affairYjBuild:edit">
	<form:form id="searchForm2" modelAttribute="affairYjBuild" action="${ctx}/affair/affairYjBuild/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="oneCheckMan" path="oneCheckId"   class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('affair_mein_shenhe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
	</shiro:hasPermission>
</body>
</html>