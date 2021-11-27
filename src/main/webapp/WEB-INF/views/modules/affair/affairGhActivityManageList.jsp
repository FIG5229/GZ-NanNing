<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工会活动管理管理</title>
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityManage/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityManage/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityManage/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityManage/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairGhActivityManage", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairGhActivityManage"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "活动管理",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairGhActivityManage"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairGhActivityManage/formDetail?id=" + id;
			top.$.jBox.open(url, "活动管理", 800, 600, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		};
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairGhActivityManage/shenHeDialog?id="+id,"活动记录审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairGhActivityManage/list";
				}
			});
		};
		function submitByIds() {
			if(null == $("#oneCheckId").val() || "" ==  $("#oneCheckId").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		};
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairGhActivityManage/shenHeDialog?id="+id,"活动报名审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairGhActivityManage/list";
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/affair/affairGhActivityEnroll/">活动报名</a></li>
		<li class="active"><a href="${ctx}/affair/affairGhActivityManage/">活动管理</a></li>
		<li ><a href="${ctx}/affair/affairGhActivityRecord/">活动记录</a></li>	</ul>
	<form:form id="searchForm" modelAttribute="affairGhActivityManage" action="${ctx}/affair/affairGhActivityManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="活动管理表.xlsx"/>
		<ul class="ul-form">
			<li><label>活动时间：</label>
				<input name="beginActivityDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairGhActivityManage.beginActivityDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endActivityDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairGhActivityManage.endActivityDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairGhActivityManage.unitId}" labelName="unit" labelValue="${affairGhActivityManage.unit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairGhActivityManage:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairGhActivityManage/form')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairGhActivityManage/deleteByIds','checkAll','myCheckBox')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairGhActivityManage'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>活动时间</th>
				<th>单位</th>
				<th>姓名</th>
				<th>开支科目</th>
				<th>用途</th>
				<th>经办人</th>
				<th>更新时间</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairGhActivityManage:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairGhActivityManage" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairGhActivityManage.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairGhActivityManage.activityDate}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
					${affairGhActivityManage.unit}
				</td>
				<td>
					${affairGhActivityManage.name}
				</td>
				<td>
					${affairGhActivityManage.kzKemu}
				</td>
				<td>
					${affairGhActivityManage.useWay}
				</td>
				<td>
					${affairGhActivityManage.jbMan}
				</td>
				<td>
					<fmt:formatDate value="${affairGhActivityManage.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${affairGhActivityManage.checkType == '0'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairGhActivityManage.opinion}"  style="cursor: pointer;color: red">审核未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairGhActivityManage.checkType, 'check_type', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairGhActivityManage.id}')">查看 </a>
					<shiro:hasPermission name="affair:affairGhActivityManage:edit">
					<c:if test="${affairGhActivityManage.createBy.id == fns:getUser().id&&('1'.equals(affairGhActivityManage.checkType)||'0'.equals(affairGhActivityManage.checkType))}">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairGhActivityManage/form?id=${affairGhActivityManage.id}')">修改</a>
					</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairGhActivityManage:manage">
						<a href="${ctx}/affair/affairGhActivityManage/delete?id=${affairGhActivityManage.id}" onclick="return confirmx('确认要删除该工会活动报名吗？', this.href)">删除</a>
						<c:if test="${affairGhActivityManage.checkType != '1'}">
							<a onclick="openDialog('${affairGhActivityManage.id}')">审核</a>
						</c:if>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="affairGhActivityManage" action="${ctx}/affair/affairGhActivityManage/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="oneCheckId" path="oneCheckId"  class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('user_gonghui')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
</body>
</html>