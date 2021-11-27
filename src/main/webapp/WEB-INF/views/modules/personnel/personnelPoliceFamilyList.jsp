<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警家庭管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceFamily/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceFamily/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceFamily/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceFamily/");
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

		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelPoliceFamily", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}

		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPoliceFamily/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/personnel/personnelPoliceFamily/form?add=add";
			}
			top.$.jBox.open(url, "民警家庭数据",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/personnel/personnelPoliceFamily/list";
				}
			});
		};

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPoliceFamily/formDetail?id="+id;
			top.$.jBox.open(url, "民警家庭",1000,600,{
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
		<li class="active"><a href="${ctx}/personnel/personnelPoliceFamily/">民警家庭</a></li>
<%--		<shiro:hasPermission name="personnel:personnelPoliceFamily:edit"><li><a href="${ctx}/personnel/personnelPoliceFamily/form">民警家庭添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="personnelPoliceFamily" action="${ctx}/personnel/personnelPoliceFamily/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="民警家庭.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>出生日期：</label>
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${personnelPoliceFamily.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>政治面貌：</label>
				<form:select path="politicsFace" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('political_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="personnel:personnelPoliceFamily:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openAddEditDialog()"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/personnel/personnelPoliceFamily/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入"
										onclick="excelImp('${ctx}/personnel/personnelPoliceFamily')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置"
									 onclick="window.location.href='${ctx}/personnel/personnelPoliceFamily'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>身份证号</th>
				<th>姓名</th>
				<th>称谓</th>
				<th>性别</th>
				<th>出生日期</th>
				<th>政治面貌</th>
				<th>现状</th>
				<th>工作单位名称及职务</th>
				<th>工作单位所在政区</th>
				<shiro:hasPermission name="personnel:personnelPoliceFamily:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPoliceFamily" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelPoliceFamily.id}"/></td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${personnelPoliceFamily.idNumber}
				</td>
				<td>
					${personnelPoliceFamily.name}
				</td>
				<td>
					${fns:getDictLabel(personnelPoliceFamily.relationship, 'family_appellation', '')}
				</td>
				<td>
					${fns:getDictLabel(personnelPoliceFamily.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnelPoliceFamily.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(personnelPoliceFamily.politicsFace, 'political_status', '')}
				</td>
				<td>
					${personnelPoliceFamily.status}
				</td>
				<td>
					${personnelPoliceFamily.unitNameJob}
				</td>
				<td>
					${personnelPoliceFamily.unitArea}
				</td>
				<td class="handleTd">
						<a href="javascript:;" onclick="openDetailDialog('${personnelPoliceFamily.id}')">查看</a>
					<shiro:hasPermission name="personnel:personnelPoliceFamily:edit">
						<a href="javascript:;" onclick="openAddEditDialog('${personnelPoliceFamily.id}')">修改</a>
						<a href="${ctx}/personnel/personnelPoliceFamily/delete?id=${personnelPoliceFamily.id}" onclick="return confirmx('确认要删除该民警家庭吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<%--<form:form id="searchForm2" modelAttribute="affairActivityMien" action="${ctx}/affair/affairActivityMien/submitByIds" method="post" class="breadcrumb form-search">
		&lt;%&ndash;<ul class="ul-form" style="text-align: right">
			<font color="red">请选择申报单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<sys:treeselect id="chuCheckMan" name="chuCheckId" value="${affairActivityMien.chuCheckId}" labelName="chuCheckMan" labelValue="${affairActivityMien.chuCheckMan}"
							title="单位" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息" isAll="true"/>
			<input  class="btn btn-primary" type="button" value="申报" onclick="submitByIds('${ctx}/affair/affairActivityMien/submitByIds')"/>
		</ul>&ndash;%&gt;

		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="chuCheckMan" path="chuCheckId"   class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('affair_mein_shenhe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>--%>
</body>
</html>