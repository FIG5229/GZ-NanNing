<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>女警工作管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		/*// 浏览器当前窗口可视区域宽度iframe*/
		let height =  $(window).height()-50;
		let width =  $(window).width()-100;
		//批量发布
		function publishByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairPolicewomanWork/publishByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要发布的内容');
			}
		};
		//批量取消发布
		function cancelByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairPolicewomanWork/cancelByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要取消发布的内容');
			}
		};
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairPolicewomanWork/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairPolicewomanWork/form";
			}
			top.$.jBox.open(url, "女警工作",width,height,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairPolicewomanWork/manageList";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPolicewomanWork/formDetail?id="+id;
			// let height =  $(window).height();
			// let width =  $(window).width();
			top.$.jBox.open(url, "女警工作",width,height,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		//推送详情弹窗
		function openPropellingDialog(id) {
			var url = "iframe:${ctx}/affair/affairPolicewomanWork/propelling?id="+id;
			top.$.jBox.open(url, "\n" + "数据推送",500,300,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function(){
					self.location.href="${ctx}/affair/affairPolicewomanWork/manageList";
				}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
<%--		<li><a href="${ctx}/affair/affairPolicewomanWork/">女警工作查询</a></li>--%>
		<shiro:lacksPermission name="affair:affairPolicewomanWork:manage">
			<li><a href="${ctx}/affair/affairPolicewomanWork/">女警工作管理</a></li>
			<input type="hidden" value="=lacksPermission" id="permission">
		</shiro:lacksPermission>

		<shiro:hasPermission name="affair:affairPolicewomanWork:manage">
			<li class="active"><a href="${ctx}/affair/affairPolicewomanWork/manageList">女警工作管理</a></li>
		</shiro:hasPermission>

		<li><a href="${ctx}/affair/affairPolicewomanTalent/">女警风采信息</a></li>
		<li ><a href="${ctx}/affair/affairChildSubsidy/">幼儿补助信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPolicewomanWork" action="${ctx}/affair/affairPolicewomanWork/manageList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_publish_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发布日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPolicewomanWork.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				--
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPolicewomanWork.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPolicewomanWork:manage">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="sign" class="btn btn-primary" type="button" value="发布" onclick="publishByIds()"/></li>
				<li class="btns"><input id="cancel" class="btn btn-primary" type="button" value="取消发布" onclick="cancelByIds()"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPolicewomanWork/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPolicewomanWork/manageList'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>标题</th>
				<th>发布部门</th>
				<th>发布人</th>
				<th>状态</th>
				<th>发布日期</th>
				<shiro:hasPermission name="affair:affairPolicewomanWork:manage"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPolicewomanWork" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPolicewomanWork.id}"/>
				</td>
				<td>
					${status.index+1}
				</td>
				<td>
					<a onclick="openDetailDialog('${affairPolicewomanWork.id}')">${affairPolicewomanWork.title}</a>
				</td>
				<td>
					${affairPolicewomanWork.publishDep}
				</td>
				<td>
					${affairPolicewomanWork.publisher}
				</td>
				<td>
					${fns:getDictLabel(affairPolicewomanWork.status, 'affair_publish_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPolicewomanWork.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="affair:affairPolicewomanWork:manage"><td class="handleTd">
					<c:if test="${affairPolicewomanWork.createBy.id == fns:getUser().id}">
						<%-- 一旦发布不可再修改--%>
						<c:if test="${affairPolicewomanWork.status != '2'}">
							<a href="javascript:void(0)" onclick="openAddEditDialog('${affairPolicewomanWork.id}')">修改</a>
						</c:if>
						<a href="${ctx}/affair/affairPolicewomanWork/delete?id=${affairPolicewomanWork.id}&flag=1"
						   onclick="return confirmx('确认要删除该女警工作吗？', this.href)">删除</a>
					</c:if>
					<a href="javascript:void(0)" onclick="openPropellingDialog('${affairPolicewomanWork.id}')">推送</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>