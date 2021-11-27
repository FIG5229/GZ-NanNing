<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>语言能力信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelLanguage/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelLanguage/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelLanguage/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelLanguage/");
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
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelLanguage", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}

		function openImportForm(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelLanguage&isCover=1","语言能力信息导入",800,520,{title:"语言能力信息导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelLanguage&isCover=0","语言能力信息导入",800,520,{title:"语言能力信息导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'cancel') {
					$.jBox.tip('已取消');
				}
				return true;
			};
			$.jBox.confirm("请选择导入模式?", "数据导入确认", submit, { buttons: { '覆盖': 'cover', '插入': 'insert','取消':'cancel'} });
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "语言能力信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelLanguage/formDetail?id="+id;
			top.$.jBox.open(url, "语言能力信息",800,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

    </script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/personnel/personnelLanguage/">语言能力信息列表</a></li>
		<shiro:hasPermission name="personnel:personnelLanguage:edit"><li><a href="${ctx}/personnel/personnelLanguage/form">语言能力信息添加</a></li></shiro:hasPermission>
	</ul>--%>
	<c:choose>
		<c:when test="${mType eq '2'}">

		</c:when>
		<c:when test="${mType eq '1'}">
			<form:form id="searchForm" modelAttribute="personnelLanguage" action="${ctx}/personnel/personnelLanguage/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelLanguage.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="语言能力信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>语种：</label>
						<form:input path="type" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>熟练程度：</label>
						<form:select path="degree" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_language')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelLanguage:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelLanguage/form?mType=1','${ctx}/personnel/personnelLanguage?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelLanguage/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelLanguage?mType=1')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelLanguage?mType=1&idNumber=${personnelLanguage.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelLanguage" action="${ctx}/personnel/personnelLanguage/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelLanguage.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="语言能力信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>语种：</label>
						<form:input path="type" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>熟练程度：</label>
						<form:select path="degree" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_language')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
							</ul>
				<ul class="ul-form2"><li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelLanguage:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelLanguage/form?idNumber=${personnelLanguage.idNumber}','${ctx}/personnel/personnelLanguage?idNumber=${personnelLanguage.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelLanguage/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelLanguage?idNumber=${personnelLanguage.idNumber}')"/></li>
					</shiro:hasPermission>
						<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
						<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelLanguage?idNumber=${personnelLanguage.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:otherwise>
	</c:choose>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				</c:if>
				<th>序号</th>
				<c:if test="${Type eq '1'}">
				<th>姓名</th>
				<th>身份证号</th>
				</c:if>
				<th>语种</th>
				<th>熟练程度</th>
				<th>补充说明</th>
				<c:if test="${mType==null || mType eq '1'}">
					<shiro:hasPermission name="personnel:personnelLanguage:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelLanguage" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelLanguage.id}"/></td>
				</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${Type eq '1'}">
				<td>
						${personnelLanguage.personName}
				</td>
				<td>
						${personnelLanguage.idNumber}
				</td>
				</c:if>
				<td><a onclick="openDetailDialog('${personnelLanguage.id}')">
					${personnelLanguage.type}
				</a></td>
				<td>
					${fns:getDictLabel(personnelLanguage.degree, 'personnel_language', '')}
				</td>
				<td>
					${personnelLanguage.explain}
				</td>

				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<shiro:hasPermission name="personnel:personnelLanguage:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelLanguage.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelLanguage/form?id=${personnelLanguage.id}','${ctx}/personnel/personnelLanguage?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelLanguage/delete?id=${personnelLanguage.id}&mType=1" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td>
						</shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="personnel:personnelLanguage:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelLanguage.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelLanguage/form?id=${personnelLanguage.id}','${ctx}/personnel/personnelLanguage?idNumber=${personnelLanguage.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelLanguage/delete?id=${personnelLanguage.id}&idNumber=${personnelLanguage.idNumber}" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td></shiro:hasPermission>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
