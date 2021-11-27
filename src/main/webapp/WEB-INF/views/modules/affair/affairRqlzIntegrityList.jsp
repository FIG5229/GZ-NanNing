<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任前廉政鉴定管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairRqlzIntegrity/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairRqlzIntegrity/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairRqlzIntegrity/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairRqlzIntegrity/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairRqlzIntegrity", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairRqlzIntegrity"}});
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
			top.$.jBox.open("iframe:"+url, "廉政鉴定",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairRqlzIntegrity"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairRqlzIntegrity/formDetail?id="+id;
			top.$.jBox.open(url, "廉政鉴定",1200,600,{
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
		<li class="active"><a href="${ctx}/affair/affairRqlzIntegrity/">廉政鉴定</a></li>
		<li ><a href="${ctx}/affair/affairLianzhengSupervise/">廉政监督</a></li>
		<li ><a href="${ctx}/affair/affairLdgblzFile/">领导干部廉政档案</a></li>
<%--		<li ><a href="http://10.3.240.8/" target="_blank">领导干部考廉系统</a></li>--%>

	<%--<shiro:hasPermission name="affair:affairRqlzIntegrity:edit"><li><a href="${ctx}/affair/affairRqlzIntegrity/form">任前廉政鉴定添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairRqlzIntegrity" action="${ctx}/affair/affairRqlzIntegrity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="廉政鉴定.xlsx"/>
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
					   value="<fmt:formatDate value="${affairRqlzIntegrity.startYear}" pattern="yyyy"/>"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li id="rd"><label>鉴定时间：</label>
				<input id="sd" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairRqlzIntegrity.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input id="ed" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairRqlzIntegrity.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li id="oy"><label>其他年份：</label>
				<input id="qt" name="otherYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairRqlzIntegrity.otherYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>鉴定类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_lzjd')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="lzUnit" name="lzUnitId" value="${affairRqlzIntegrity.lzUnitId}" labelName="lzUnit" labelValue="${affairRqlzIntegrity.lzUnit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>

			<li><label>鉴定单位：</label>
				<form:select path="jdUnit" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xfjb_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairRqlzIntegrity:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairRqlzIntegrity/form')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>

			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairRqlzIntegrity:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairRqlzIntegrity/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairRqlzIntegrity'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr> <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>单位</th>
				<th>职务</th>
				<th>鉴定类型</th>
				<th>鉴定时间</th>
				<th>鉴定单位</th>
				<th>是否同意</th>
<%--				<th>审核状态</th>--%>
				<shiro:hasPermission name="affair:affairRqlzIntegrity:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairRqlzIntegrity" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairRqlzIntegrity.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
				${affairRqlzIntegrity.name}
				</td>
				<td>
					${affairRqlzIntegrity.lzUnit}
				</td>
				<td>
						${affairRqlzIntegrity.job}
				</td>
				<td>
						${fns:getDictLabel(affairRqlzIntegrity.type, 'affair_lzjd', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairRqlzIntegrity.foundDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(affairRqlzIntegrity.jdUnit, 'affair_xfjb_unit', '')}
				</td>
				<td>
						${fns:getDictLabel(affairRqlzIntegrity.isAgree, 'yes_no', '')}
				</td>
				<%--<td>
					${affairRqlzIntegrity.approvalStatus}
				</td>--%>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairRqlzIntegrity.id}')">查看</a>
				<shiro:hasPermission name="affair:affairRqlzIntegrity:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairRqlzIntegrity/form?id=${affairRqlzIntegrity.id}')">修改</a>
			</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairRqlzIntegrity:delete">
						<a href="${ctx}/affair/affairRqlzIntegrity/delete?id=${affairRqlzIntegrity.id}" onclick="return confirmx('确认要删除该任前廉政鉴定吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>