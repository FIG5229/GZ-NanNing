<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团员教育评议管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairEducationComment/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairEducationComment/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairEducationComment/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairEducationComment/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairEducationComment", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairEducationComment"}});
			});

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
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "团员教育评议",900,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairEducationComment"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairEducationComment/formDetail?id="+id;
			top.$.jBox.open(url, "团员教育评议",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
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
			top.$.jBox.open("iframe:${ctx}/affair/affairEducationComment/shenHeDialog?id="+id,"团员教育评议",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairEducationComment/list/?repage";
				}
			});
		};
	</script>
</head>
<body>
<%--	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairEducationComment/">团员教育评议</a></li>
&lt;%&ndash;		<shiro:hasPermission name="affair:affairEducationComment:edit"><li><a href="${ctx}/affair/affairEducationComment/form">团员教育评议添加</a></li></shiro:hasPermission>&ndash;%&gt;
	</ul>--%>
<%--下页调用form表单 不可全部隐藏--%>
	<form:form id="searchForm" modelAttribute="affairEducationComment" action="${ctx}/affair/affairEducationComment/echart/educationComment" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="year" name="year" type="hidden" value="${affairEducationComment.year}"/>
		<input id="month" name="month" type="hidden" value="${affairEducationComment.month}"/>
		<input id="dateType" name="dateType" type="hidden" value="${affairEducationComment.dateType}"/>
		<input id="label" name="label" type="hidden" value="${affairEducationComment.label}"/>
		<input name="startDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairEducationComment.startDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<input name="endDate" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
			   value="<fmt:formatDate value="${affairEducationComment.endDate}" pattern="yyyy-MM-dd"/>"
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		<%--<ul class="ul-form">
			<li><label>警号：</label>
				<form:input path="policeNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>职务：</label>
				<form:input path="job" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>评议时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairEducationComment.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairEducationComment.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>自评等级：</label>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_comment_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>团支部意见：</label>
				<form:select path="opinion" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_comment_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属团组织：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairEducationComment.unitId}"
								labelName="unit" labelValue="${affairEducationComment.unit}"
								title="所属团组织" url="/affair/affairTwBase/treeData" cssClass="input-small"
								allowClear="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairEducationComment:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairEducationComment/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairEducationComment/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairEducationComment'"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
<%--				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>--%>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>籍贯</th>
				<th>出生年月</th>
				<th>入团时间</th>
				<th>职务</th>
				<th>所属团组织</th>
				<th>团支部意见</th>
				<th>处团委意见</th>
				<th>自评等级</th>
				<th>状态</th>
<%--				<shiro:hasPermission name="affair:affairEducationComment:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairEducationComment" varStatus="status">
			<tr>
<%--				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairEducationComment.id}"/></td>--%>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairEducationComment.name}
				</td>
				<td>
					${fns:getDictLabel(affairEducationComment.sex, 'sex', '')}
				</td>
				<td>
						${affairEducationComment.nativePlace}
				</td>
				<td>
					<fmt:formatDate value="${affairEducationComment.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairEducationComment.rtDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairEducationComment.job}
				</td>
				<td>
						${affairEducationComment.unit}
				</td>
				<td>
						${fns:getDictLabel(affairEducationComment.opinion, 'affair_comment_grade', '')}
				</td>
				<td>
						${fns:getDictLabel(affairEducationComment.ctwOpinion, 'affair_comment_grade', '')}
				</td>
				<td>
					${fns:getDictLabel(affairEducationComment.level, 'affair_comment_grade', '')}
				</td>
				<td>
						${fns:getDictLabel(affairEducationComment.checkType, 'education_comment', '')}
			<%--	<td class="handleTd">
					<a onclick="openDetailDialog('${affairEducationComment.id}')">查看</a>
					<shiro:hasPermission name="affair:affairEducationComment:edit">
						<c:if test="${affairEducationComment.createBy.id == fns:getUser().id &&('0'.equals(affairEducationComment.checkType)||'1'.equals(affairEducationComment.checkType))}">
							<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairEducationComment/form?id=${affairEducationComment.id}')">修改</a>
						</c:if>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairEducationComment:manage">
						<a href="${ctx}/affair/affairEducationComment/delete?id=${affairEducationComment.id}" onclick="return confirmx('确认要删除该团员教育评议吗？', this.href)">删除</a>
						<a onclick="openDialog('${affairEducationComment.id}')">审核</a>
					</shiro:hasPermission>
				</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<div class="pagination">${page}</div>
<%--<form:form id="searchForm2" modelAttribute="affairEducationComment" action="${ctx}/affair/affairEducationComment/submitByIds" method="post" class="breadcrumb form-search">
    <ul class="ul-form" style="text-align: right">
        <font color="red">请选择评定单位：</font>
        <input type="hidden" name="ids" id="idsValue"/>
        <form:select id="oneCheckId" path="oneCheckId"  class="input-xlarge required">
            <form:option value="" label=""/>
            <form:options items="${fns:getDictList('user_tuanwei')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
        </form:select>
        <input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
    </ul>
</form:form>--%>
</body>
</html>
</td>
