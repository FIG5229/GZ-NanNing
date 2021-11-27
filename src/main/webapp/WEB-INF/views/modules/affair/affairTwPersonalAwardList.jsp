<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团委个人表彰管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairTwPersonalAward/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTwPersonalAward/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTwPersonalAward/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTwPersonalAward/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTwPersonalAward", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTwPersonalAward"}});
			});

			$("[data-toggle='popover']").popover();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url,title) {
			top.$.jBox.open("iframe:"+url, title,900,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					//self.location.href="${ctx}/affair/affairTwPersonalAward"
					$("#searchForm").submit();
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTwPersonalAward/formDetail?id="+id;
			top.$.jBox.open(url, "个人获奖明细",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//批量审核
		function checkByIds(url, myCheckBox, checkBoxsName) {
			var editIds = getIds(checkBoxsName);
			if (editIds.length > 0) {
				var isStr = editIds.join(',');
				top.$.jBox.open("iframe:" + url + "?ids=" + isStr, "批量审核", 700, 520, {
					buttons: {"关闭": true},
					loaded: function () {
						$(".jbox-content", top.document).css("overflow-y", "hidden");
					}, closed: function () {
						self.location.href = '${ctx}/affair/affairTwPersonalAward/list';
					}
				});
			} else {
				$.jBox.tip('请先选择审核内容');
			}
		}
		//推送到人才青年库
		function openPushForm(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairYouthTalent/pushForm?id="+id, "推送到青年人才库",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairTwPersonalAward/";
					}
				});
			}else {
				$.jBox.tip('请先选择要推送的内容且只能单条推送');
			}
		}
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/affair/affairTwUnitAward/">单位获奖</a></li>
		<li class="active"><a href="${ctx}/affair/affairTwPersonalAward/">个人获奖</a></li>
		<li><a href="${ctx}/affair/affairLeaguePunishment/">团内处分</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="affairTwPersonalAward" action="${ctx}/affair/affairTwPersonalAward/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="团委个人表彰奖励表.xlsx"/>
		<ul class="ul-form">
			<li><label>获奖时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTwPersonalAward.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTwPersonalAward.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairTwPersonalAward.unitId}" labelName="unit" labelValue="${affairTwPersonalAward.unit}"
								title="获奖单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>奖项名称：</label>
				<form:input path="rewardName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>奖项文号：</label>
				<form:input path="fileNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>审核单位：</label>
				<form:input path="approvalUnit" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTwPersonalAward:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTwPersonalAward/form','个人获奖添加')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTwPersonalAward/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="审核" onclick="checkByIds('${ctx}/affair/affairTwPersonalAward/shenHeDialog','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input class="btn btn-primary"  type="button" value="推送到人才青年库" onclick="openPushForm('myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTwPersonalAward'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>获奖时间</th>
				<th>获奖时所在单位</th>
				<th>姓名</th>
				<th>性别</th>
				<th>奖项名称</th>
				<th>奖项文号</th>
				<th>奖励级别</th>
				<th>审核单位</th>
				<th>审核状态</th>
				<th>推送状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTwPersonalAward" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTwPersonalAward.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
				<fmt:formatDate value="${affairTwPersonalAward.date}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
						${affairTwPersonalAward.unit}
				</td>
				<td>
					${affairTwPersonalAward.name}
				</td>
				<td>
					${fns:getDictLabel(affairTwPersonalAward.sex, 'sex', '')}
				</td>
				<td>
						${affairTwPersonalAward.rewardName}
				</td>
				<td>
						${affairTwPersonalAward.fileNo}
				</td>
				<td>
						${fns:getDictLabel(affairTwPersonalAward.type, 'affair_tw_reward_punish', '')}
				</td>
				<td>
					${affairTwPersonalAward.approvalUnit}
				</td>
				<td>
					<c:choose>
						<c:when test="${affairTwPersonalAward.status == '2'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairTwPersonalAward.opinion}"  style="cursor: pointer;color: red">审核未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairTwPersonalAward.status, 'affair_query_shenhe', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
						${fns:getDictLabel(affairTwPersonalAward.pushType, 'push_types', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTwPersonalAward.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTwPersonalAward:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTwPersonalAward/form?id=${affairTwPersonalAward.id}','个人获奖修改')">修改</a>
						<a href="${ctx}/affair/affairTwPersonalAward/delete?id=${affairTwPersonalAward.id}" onclick="return confirmx('确认要删除该团委个人表彰吗？', this.href)">删除</a>
<%--						<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/affairTwPersonalAwardForm?id=${affairTwPersonalAward.id}')">推送到奖惩信息库</a>--%>
						<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examAutoEvaluation/pushUnitRewardsLibrary?id=${affairTwPersonalAward.id}&pushFrom=团内奖惩个人','推送到绩效考评')">推送到绩效考评</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>