<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导干部护照(通行证)管理表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//这是打印功能的JS
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
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/personnelPassport/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/personnelPassport/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/personnelPassport/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/personnelPassport/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
		});
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelPassport", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "护照(通行证)信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/personnelPassport/formDetail?id="+id;
			top.$.jBox.open(url, "",800,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		function openImportForm(url,title) {
			if(title == null || title == ''){
				title = '护照（通行证）信息集导入';
			}
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPassport&isCover=1",title,800,520,{title:title, buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPassport&isCover=0",title,800,520,{title:title, buttons:{"关闭":true},
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
	</script>
</head>
<body>

	<c:choose>
		<c:when test="${mType eq '3'}">
			<ul class="nav nav-tabs">
				<li class="active"><a href="${ctx}/affair/personnelPassport?mType=3">护照(通行证)管理</a></li>
			</ul>
			<form:form id="searchForm" modelAttribute="personnelPassport" action="${ctx}/affair/personnelPassport/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPassport.idNumber}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="护照（通行证）信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>证照名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>证照姓名：</label>
						<form:input path="personName" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>签发日期：</label>
						<input name="beginIssueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.beginIssueDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endIssueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.endIssueDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label class="width120">证件领用日期：</label>
						<input name="beginReceiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.beginReceiveDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endReceiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.endReceiveDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="affair:personnelPassport:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/personnelPassport/form?mType=3','${ctx}/affair/personnelPassport?mType=3')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/affair/personnelPassport?mType=3')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/personnelPassport/deleteByIds','checkAll','myCheckBox')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/personnelPassport?mType=3&idNumber=${personnelPassport.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:when test="${mType eq '2'}"></c:when>
		<c:when test="${mType eq '1'}">
			<form:form id="searchForm" modelAttribute="personnelPassport" action="${ctx}/affair/personnelPassport/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPassport.idNumber}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="护照（通行证）信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>证照名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>证照姓名：</label>
						<form:input path="personName" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>签发日期：</label>
						<input name="beginIssueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.beginIssueDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endIssueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.endIssueDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label class="width120">证件领用日期：</label>
						<input name="beginReceiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.beginReceiveDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endReceiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.endReceiveDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="affair:personnelPassport:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/personnelPassport/form?mType=1','${ctx}/affair/personnelPassport?mType=1')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/affair/personnelPassport?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/personnelPassport/deleteByIds','checkAll','myCheckBox')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/personnelPassport?mType=1&idNumber=${personnelPassport.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelPassport" action="${ctx}/affair/personnelPassport/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPassport.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="护照（通行证）信息集.xlsx"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<ul class="ul-form">
					<li><label>证照名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>证照姓名：</label>
						<form:input path="personName" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>签发日期：</label>
						<input name="beginIssueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.beginIssueDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endIssueDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.endIssueDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label class="width120">证件领用日期：</label>
						<input name="beginReceiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.beginReceiveDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endReceiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPassport.endReceiveDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="affair:personnelPassport:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/personnelPassport/form?idNumber=${personnelPassport.idNumber}','${ctx}/affair/personnelPassport?idNumber=${personnelPassport.idNumber}')"/></li>
						<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/affair/personnelPassport?idNumber=${personnelPassport.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/personnelPassport/deleteByIds','checkAll','myCheckBox')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/personnelPassport?idNumber=${personnelPassport.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:otherwise>
	</c:choose>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><c:if test="${mType==null || mType eq '3' || mType eq '1'}">
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
			</c:if>
				<th>序号</th>
				<th>证照名称</th>
				<th>证照姓名</th>
				<th>签发地</th>
				<th>签发日期</th>
				<th>证照有效截止日期</th>
				<th>证件编号</th>
				<th>证件状态</th>
				<th>证件领用日期</th>
				<th>证件交还日期</th>
				<c:if test="${mType==null || mType eq '3' || mType eq '1'}">
				<shiro:hasPermission name="affair:personnelPassport:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPassport" varStatus="status">
			<tr><c:if test="${mType==null || mType eq '3' || mType eq '1'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelPassport.id}"/></td>
			</c:if>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${personnelPassport.name}
				</td>
				<td>
					${personnelPassport.personName}
				</td>
				<td>
					${personnelPassport.place}
				</td>
				<td>
					<fmt:formatDate value="${personnelPassport.issueDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnelPassport.toDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelPassport.certificateNo}
				</td>
				<td>
					${fns:getDictLabel(personnelPassport.status, 'personnel_zjzt', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnelPassport.receiveDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnelPassport.returnDate}" pattern="yyyy-MM-dd"/>
				</td>
				<c:choose>
					<c:when test="${mType eq '3'}">
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelPassport.id}')">查看</a>
						<shiro:hasPermission name="affair:personnelPassport:edit">

<%--                            <c:if test="${personnelPassport.createBy.id == fns:getUser().id}">--%>
                                <a href="javascript:void(0)"
                                   onclick="openForm('${ctx}/affair/personnelPassport/form?id=${personnelPassport.id}','${ctx}/affair/personnelPassport?mType=3')">修改</a>
                                <a href="${ctx}/affair/personnelPassport/delete?id=${personnelPassport.id}&mType=3"
                                   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                            </c:if>--%>

						</shiro:hasPermission>
						</td>
					</c:when>
					<c:when test="${mType eq '2'}">
					</c:when>
					<c:when test="${mType eq '1'}">
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelPassport.id}')">查看</a>
						<shiro:hasPermission name="affair:personnelPassport:edit">

<%--                            <c:if test="${personnelPassport.createBy.id == fns:getUser().id}">--%>
                                <a href="javascript:void(0)"
                                   onclick="openForm('${ctx}/affair/personnelPassport/form?id=${personnelPassport.id}','${ctx}/affair/personnelPassport?mType=1')">修改</a>
                                <a href="${ctx}/affair/personnelPassport/delete?id=${personnelPassport.id}&mType=1"
                                   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                            </c:if>--%>

						</shiro:hasPermission>
						</td>
					</c:when>
					<c:otherwise>
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelPassport.id}')">查看</a>
						<shiro:hasPermission name="affair:personnelPassport:edit">

<%--                            <c:if test="${personnelPassport.createBy.id == fns:getUser().id}">--%>
                                <a href="javascript:void(0)"
                                   onclick="openForm('${ctx}/affair/personnelPassport/form?id=${personnelPassport.id}','${ctx}/affair/personnelPassport?idNumber=${personnelPassport.idNumber}')">修改</a>
                                <a href="${ctx}/affair/personnelPassport/delete?id=${personnelPassport.id}&idNumber=${personnelPassport.idNumber}"
                                   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                            </c:if>--%>

						</shiro:hasPermission>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
