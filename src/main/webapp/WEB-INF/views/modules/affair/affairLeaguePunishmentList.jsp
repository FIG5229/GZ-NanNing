<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团内惩罚管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairLeaguePunishment/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLeaguePunishment/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairLeaguePunishment/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLeaguePunishment/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLeaguePunishment", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLeaguePunishment"}});
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function openAddDialog(url) {
            top.$.jBox.open("iframe:"+url, "团内处分",800,520,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairLeaguePunishment"}
            });
        }

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairLeaguePunishment/formDetail?id="+id;
			top.$.jBox.open(url, "团内处分详情",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairLeaguePunishment/form?id="+id,"团内处分编辑",1000,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairLeaguePunishment"}
			});
		}
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairTwUnitAward/">单位获奖</a></li>
		<li><a href="${ctx}/affair/affairTwPersonalAward/">个人获奖</a></li>
		<li class="active"><a href="${ctx}/affair/affairLeaguePunishment/">团内处分</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairLeaguePunishment" action="${ctx}/affair/affairLeaguePunishment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="团内惩罚信息表.xlsx"/>

		<ul class="ul-form">
			<li><label>处分时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairLeaguePunishment.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairLeaguePunishment.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairLeaguePunishment.unitId}" labelName="unit" labelValue="${affairLeaguePunishment.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>处分单位：</label>
				<form:input path="punishmentUnit" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>处分名称：</label>
				<form:input path="punishment" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>处分文号：</label>
				<form:input path="fileNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>处分级别：</label>
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_punishment')}" itemLabel="label"
								  itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairLeaguePunishment:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openAddDialog('${ctx}/affair/affairLeaguePunishment/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLeaguePunishment/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<%--<li class="btns"><input class="btn btn-primary" type="button" value="审核" onclick="checkByIds('${ctx}/affair/affairLeaguePunishment/shenHeDialog','checkAll','myCheckBox')"/></li>--%>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLeaguePunishment'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>处分时间</th>
				<th>处分时所在单位</th>
				<th>姓名</th>
				<th>性别</th>
				<th>处分单位</th>
				<th>处分名称</th>
				<th>处分文号</th>
				<th>处分级别</th>
				<th>备注</th>
				<shiro:hasPermission name="affair:affairLeaguePunishment:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLeaguePunishment" varStatus="status">
			<tr>
				<td class="checkTd"><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLeaguePunishment.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairLeaguePunishment.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairLeaguePunishment.unit}
				</td>
				<td>
					${affairLeaguePunishment.name}
				</td>
				<td>
						${fns:getDictLabel(affairLeaguePunishment.sex, 'sex', '')}
				</td>
				<td>
					${affairLeaguePunishment.punishmentUnit}
				</td>
				<td>
					${affairLeaguePunishment.punishment}
				</td>
				<td>
					${affairLeaguePunishment.fileNo}
				</td>
				<td>
                        ${fns:getDictLabel(affairLeaguePunishment.type, 'affair_punishment', '')}
				</td>
				<td>
					${affairLeaguePunishment.remarks}
				</td>
                <td class="handleTd">
    				<a href="javascript:;" onclick="openDetailDialog('${affairLeaguePunishment.id}')">查看</a>
                    <shiro:hasPermission name="affair:affairLeaguePunishment:edit">
    				<a href="javascript:;" onclick="openEditDialog('${affairLeaguePunishment.id}')">修改</a>
					<a href="${ctx}/affair/affairLeaguePunishment/delete?id=${affairLeaguePunishment.id}" onclick="return confirmx('确认要删除该团内惩罚吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>