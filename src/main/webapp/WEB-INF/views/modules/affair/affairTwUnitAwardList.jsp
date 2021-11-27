<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团委集体表彰管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairTwUnitAward/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTwUnitAward/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTwUnitAward/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTwUnitAward/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTwUnitAward", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTwUnitAward"}});
			});

			$("[data-toggle='popover']").popover();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,title) {
			top.$.jBox.open("iframe:"+url, title,800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					//self.location.href="${ctx}/affair/affairTwUnitAward"
					$("#searchForm").submit();
				}

			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTwUnitAward/formDetail?id="+id;
			top.$.jBox.open(url, "单位获奖详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function checkByIds() {
			if(null == $("#oneCheckManName").val() || "" ==  $("#oneCheckManName").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val());
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
		//添加审核弹窗
		function openDialog(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairTwUnitAward/shenHeDialog?id="+id, "审核",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairTwUnitAward/";
					}
				});
			}else {
				$.jBox.tip('请先选择要审核的内容且只能单条审核');
			}
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
						self.location.href = '${ctx}/affair/affairTwUnitAward/list';
					}
				});
			} else {
				$.jBox.tip('请先选择审核内容');
			}
		}
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairTwUnitAward/">单位获奖</a></li>
		<li><a href="${ctx}/affair/affairTwPersonalAward/">个人获奖</a></li>
		<li><a href="${ctx}/affair/affairLeaguePunishment/">团内处分</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTwUnitAward" action="${ctx}/affair/affairTwUnitAward/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="团委单位表彰奖励表.xlsx"/>
		<input id="userOffice" name="userOffice" type="hidden" value="${fns:getUser().office.id}">
		<ul class="ul-form">
			<li><label>获奖时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTwUnitAward.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTwUnitAward.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>获奖单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairTwUnitAward.unitId}" labelName="unit" labelValue="${affairTwUnitAward.unit}"
								title="单位" url="/sys/office/treeData?type=2" checked="true" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>奖项名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>审批单位：</label>
				<form:input path="approvalUnit" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>获奖文号：</label>
				<form:input path="fileNo" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTwUnitAward:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTwUnitAward/form','单位获奖添加')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTwUnitAward/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input class="btn btn-primary" type="button" value="审核" onclick="checkByIds('${ctx}/affair/affairTwUnitAward/shenHeDialog','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTwUnitAward'"/></li>
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
				<th>获奖单位</th>
				<th>获奖组织</th>
				<th>奖项名称</th>
				<th>审批单位</th>
				<th>获奖文号</th>
				<th>奖励级别</th>
				<th>备注</th>
				<th>审核状态</th>
				<th>推送状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTwUnitAward" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTwUnitAward.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairTwUnitAward.date}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
						${affairTwUnitAward.unit}
				</td>
				<td>
						${affairTwUnitAward.getAwardParty}
				</td>
				<td>
						${affairTwUnitAward.name}
				</td>
				<td>
					${affairTwUnitAward.approvalUnit}
				</td>
				<td>
					${affairTwUnitAward.fileNo}
				</td>
				<td>
						${fns:getDictLabel(affairTwUnitAward.type, "affair_tw_reward_punish", "")}
				</td>
				<td>
						${affairTwUnitAward.remark}
				</td>
				<td>
					<c:choose>
						<c:when test="${affairTwUnitAward.status == '2'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairTwUnitAward.opinion}"  style="cursor: pointer;color: red">审核未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairTwUnitAward.status, 'affair_query_shenhe', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
						${fns:getDictLabel(affairTwUnitAward.pushType, 'push_types', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTwUnitAward.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTwUnitAward:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTwUnitAward/form?id=${affairTwUnitAward.id}','单位获奖修改')">修改</a>
						<a href="${ctx}/affair/affairTwUnitAward/delete?id=${affairTwUnitAward.id}" onclick="return confirmx('确认要删除该团委集体表彰吗？', this.href)">删除</a>
						<%--<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/twUnitAwardForm?id=${affairTwUnitAward.id}')">推送到奖惩信息库</a>--%>
						<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examAutoEvaluation/pushUnitRewardsLibrary?id=${affairTwUnitAward.id}&pushFrom=团内奖惩单位','推送到绩效考评')">推送到绩效考评</a>
					</shiro:hasPermission>
				</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>