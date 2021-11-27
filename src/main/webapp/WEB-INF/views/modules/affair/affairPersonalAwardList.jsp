<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工会个人表彰管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPersonalAward/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPersonalAward/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPersonalAward/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPersonalAward/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPersonalAward", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPersonalAward"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url,title) {
			if (title == null || title ==undefined){
				title='个人工会表彰奖励';
			} else {
				title='推送到奖惩信息库';
			}
			top.$.jBox.open("iframe:"+url, title,1000,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPersonalAward"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPersonalAward/formDetail?id="+id;
			top.$.jBox.open(url, "个人工会表彰奖励",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//推送到文体人才库
		function openPushForm(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairWentiTalent/pushForm?id="+id, "推送到文体人才库",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairPersonalAward/";
					}
				});
			}else {
				$.jBox.tip('请先选择要推送的内容且只能单条推送');
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/affair/affairCollectiveAward/">集体</a></li>
		<li class="active"><a href="${ctx}/affair/affairPersonalAward/">个人</a></li>
		<%--<shiro:hasPermission name="affair:affairPersonalAward:edit"><li><a href="${ctx}/affair/affairPersonalAward/form">工会个人表彰添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPersonalAward" action="${ctx}/affair/affairPersonalAward/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="工会个人表彰表.xlsx"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairPersonalAward.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairPersonalAward.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairPersonalAward.unitId}" labelName="unit" labelValue="${affairPersonalAward.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="policeName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>奖项名称：</label>
				<form:input path="awardName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>奖项级别：</label>
				<form:select path="awardLevel" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_org_reward_punish')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPersonalAward:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairPersonalAward/form?id=${affairPersonalAward.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPersonalAward/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="" class="btn btn-primary" type="button" value="推送到文体人才库" onclick="openPushForm('myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPersonalAward'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>时间</th>
				<th>单位</th>
				<th>民警姓名</th>
				<th>职务</th>
				<th>性别</th>
				<th>奖项名称</th>
				<th>奖项级别</th>
				<th>颁发单位</th>
				<th>推送状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPersonalAward" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPersonalAward.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairPersonalAward.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairPersonalAward.unit}
				</td>
				<td>
					${affairPersonalAward.policeName}
				</td>
				<td>
					${fns:getDictLabel(affairPersonalAward.job, 'affair_njzhiwu', '')}
				</td>
				<td>
					${fns:getDictLabel(affairPersonalAward.sex, 'sex', '')}
				</td>
				<td>
					${affairPersonalAward.awardName}
				</td>
				<td>
					${fns:getDictLabel(affairPersonalAward.awardLevel, 'affair_org_reward_punish', '')}
				</td>
				<td>
					${affairPersonalAward.bzUnit}
				</td>
				<td>
						${fns:getDictLabel(affairPersonalAward.pushType, 'push_types', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairPersonalAward.id}')">查看</a>
					<shiro:hasPermission name="affair:affairPersonalAward:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairPersonalAward/form?id=${affairPersonalAward.id}')">修改</a>
						<a href="${ctx}/affair/affairPersonalAward/delete?id=${affairPersonalAward.id}" onclick="return confirmx('确认要删除该工会个人表彰吗？', this.href)">删除</a>
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/exam/examJcInfoPlus/personalAwardForm?id=${affairPersonalAward.id}','推送到奖惩信息库')">推送到奖惩信息库</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>