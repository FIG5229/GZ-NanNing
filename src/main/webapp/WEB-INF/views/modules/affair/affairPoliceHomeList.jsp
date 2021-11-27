<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警小家建设管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		/*window.onload = function(){
			var contentTable = document.getElementById("contentTable");
			var maxCol = 6, val, count, start;
			for(var col = maxCol-1; col >= 0 ; col--){
				count = 1;
				val = "";
				for(var i=0; i<contentTable.rows.length; i++){
					if(val == contentTable.rows[i].cells[col].innerHTML){
						count++;
					}else{
						if(count > 1){ //合并
							start = i - count;
							contentTable.rows[start].cells[col].rowSpan = count;
							for(var j=start+1; j<i; j++){
								contentTable.rows[j].cells[col].style.display = "none";
							}
							count = 1;
						}
						val = contentTable.rows[i].cells[col].innerHTML;
					}
				}
				if(count > 1 ){ //合并，最后几行相同的情况下
					start = i - count;
					contentTable.rows[start].cells[col].rowSpan = count;
					for(var j=start+1; j<i; j++){
						contentTable.rows[j].cells[col].style.display = "none";
					}
				}
			}
		};*/
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceHome/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceHome/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceHome/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceHome/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPoliceHome", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPoliceHome"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairPoliceHome/formDetail?id="+id;
			top.$.jBox.open(url, "民警小家建设",1300,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "民警小家建设",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPoliceHome/list"}
			});
		}

		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairPoliceHome/form?id="+id, "民警小家建设",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPoliceHome/list"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairPoliceHome/">小家建设</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairPoliceHome" action="${ctx}/affair/affairPoliceHome/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%--<input id="fileName" name="fileName" type="hidden" value="民警小家建设管理表.xlsx"/>--%>
		<input id="fileName" name="fileName" type="hidden" value="民警小家建设表格.xlsx"/>
		<ul class="ul-form">
			<li><label style="width: 100px;">小家建设名称：</label>
<%--				<form:input path="pointUnit" htmlEscape="false" class="input-medium"/>--%>
				<sys:treeselect id="pointUnit" name="pointUnitId" value="${affairPoliceHome.pointUnitId}" labelName="pointUnit" labelValue="${affairPoliceHome.pointUnit}"
								title="单位" url="/sys/office/treeData?type=2"  allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
			</li>
			<li><label>建设项目：</label>
<%--				<form:input path="project" htmlEscape="false" class="input-medium"/>--%>
				<form:select path="project" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_jsxm')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairPoliceHome:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairPoliceHome/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairPoliceHome/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPoliceHome'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="table table-striped table-bordered table-condensed" >
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>建设时间</th>
				<th>所属单位</th>
				<th>小家建设名称</th>
				<th>建设项目</th>
				<th>修改时间</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody >
		<c:forEach items="${page.list}" var="affairPoliceHome" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPoliceHome.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairPoliceHome.pointDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairPoliceHome.unit}
				</td>
				<td>
					${affairPoliceHome.pointUnit}
				</td>
				<td>
					${fns:getDictLabel(affairPoliceHome.project, 'affair_jsxm', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairPoliceHome.updateDate}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td class="handleTd">
					<a href="javascript:;" onclick="openDetailDialog('${affairPoliceHome.id}')">查看</a>
                    <shiro:hasPermission name="affair:affairPoliceHome:edit">
					<a href="javascript:;" onclick="openEditDialog('${affairPoliceHome.id}')">修改</a>
					<a href="${ctx}/affair/affairPoliceHome/delete?id=${affairPoliceHome.id}"
					   onclick="return confirmx('确认要删除该民警小家建设吗？', this.href)">删除</a>
                    </shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

	<div class="pagination">${page}</div>
</body>
</html>