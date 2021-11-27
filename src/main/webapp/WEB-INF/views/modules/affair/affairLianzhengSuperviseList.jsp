<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>廉政监督管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairLianzhengSupervise/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLianzhengSupervise/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairLianzhengSupervise/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLianzhengSupervise/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLianzhengSupervise", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLianzhengSupervise"}});
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
			//默认显示某个输入框
			var reason = "${reason}";
			if(reason != null && reason != '' && typeof reason != undefined){
				$("#reason").val(${reason}).trigger("change");
			}
			$("#reason").change(function(){
				$("#nd").val("")
				$("#sd").val("")
				$("#ed").val("")
				$("#qt").val("")
				year();
				console.log($("#reason").val())
			});
			function year() {
				if ($("#reason").val() == '1') {
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:block")
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '2') {
					$("#rd").attr("style","display:block")
					$("#yr").attr("style","display:none")
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '3'){
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:none")
					$("#oy").attr("style","display:block")
				}else {
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:block")
					$("#oy").attr("style","display:none")
				}
			}
			year();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "廉政监督",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLianzhengSupervise"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairLianzhengSupervise/formDetail?id="+id;
			top.$.jBox.open(url, "廉政监督档案",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/affair/affairRqlzIntegrity/">廉政鉴定</a></li>
		<li class="active"><a href="${ctx}/affair/affairLianzhengSupervise/">廉政监督</a></li>
		<li ><a href="${ctx}/affair/affairLdgblzFile/">领导干部廉政档案</a></li>
<%--		<li ><a href="http://10.3.240.8/" target="_blank">领导干部考廉系统</a></li>--%>
	<%--<shiro:hasPermission name="affair:affairLianzhengSupervise:edit"><li><a href="${ctx}/affair/affairLianzhengSupervise/form">廉政监督添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairLianzhengSupervise" action="${ctx}/affair/affairLianzhengSupervise/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="廉政监督.xlsx"/>
		<ul class="ul-form">
			<li>
				<label>数据范围：</label>
				<select id="reason" style="width: 140px;" name="reason">
					<option value="2">全部</option>
					<option value="3">其他年份</option>
				</select>
			</li>
			<li id="yr"><label>年度：</label>
				<input id="nd" name="startYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLianzhengSupervise.startYear}" pattern="yyyy"/>"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li id="rd"><label>时间：</label>
				<input id="sd" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLianzhengSupervise.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input id="ed" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLianzhengSupervise.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li id="oy"><label>其他年份：</label>
				<input id="qt" name="otherYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairLianzhengSupervise.otherYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>主办单位：</label>
				<sys:treeselect id="zbUnit" name="zbUnitId" value="${affairLianzhengSupervise.zbUnitId}" labelName="zbUnit" labelValue="${affairLianzhengSupervise.zbUnit}"
								title="主办单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>

			<li><label>监督单位：</label>
				<form:select path="jdUnit" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xfjb_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairLianzhengSupervise:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairLianzhengSupervise/form')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairLianzhengSupervise:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLianzhengSupervise/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLianzhengSupervise'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr> <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>时间</th>
				<th>参加人员</th>
				<th>主办单位</th>
				<th>项目名称</th>
				<th>金额</th>
				<th>监督单位</th>
<%--				<th>审核状态</th>--%>
				<shiro:hasPermission name="affair:affairLianzhengSupervise:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLianzhengSupervise" varStatus="status">
			<tr><td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLianzhengSupervise.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairLianzhengSupervise.foundDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairLianzhengSupervise.joinPeople}
				</td>
				<td>
						${affairLianzhengSupervise.zbUnit}
				</td>
				<td>
					${affairLianzhengSupervise.title}
				</td>
				<td>
						${affairLianzhengSupervise.money}
				</td>
				<td>
								${fns:getDictLabel(affairLianzhengSupervise.jdUnit, 'affair_xfjb_unit', '')}
				</td>
				<%--<td>
					${affairLianzhengSupervise.approvalStatus}
				</td>--%>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairLianzhengSupervise.id}')">查看</a>
				<shiro:hasPermission name="affair:affairLianzhengSupervise:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairLianzhengSupervise/form?id=${affairLianzhengSupervise.id}')">修改</a>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairLianzhengSupervise:delete">
						<a href="${ctx}/affair/affairLianzhengSupervise/delete?id=${affairLianzhengSupervise.id}" onclick="return confirmx('确认要删除该廉政监督吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>