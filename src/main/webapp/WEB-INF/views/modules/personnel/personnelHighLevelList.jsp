<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>高层次人才信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelHighLevel/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelHighLevel/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelHighLevel/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelHighLevel/");
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
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelHighLevel", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "高层次人才信息添加",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/personnel/personnelHighLevel?idNumber=${personnelHighLevel.idNumber}"}
			});
		}

		//导入履历信息-新，增加判断 覆盖/插入
		// 打开导入页面请求路径由 ${ctx}/file/template/download/view?id= xxxx
		// 调整为 ${ctx}/file/template/personnelBasesdownload/view?id= xxxx &isCover=1
		function openImportForm(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelHighLevel&isCover=1","高层次人才导入",800,520,{title:"高层次人才导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelHighLevel&isCover=0","高层次人才导入",800,520,{title:"高层次人才导入", buttons:{"关闭":true},
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
		<c:when test="${mType eq '2'}">

		</c:when>
		<c:when test="${mType eq '1'}">
			<form:form id="searchForm" modelAttribute="personnelHighLevel" action="${ctx}/personnel/personnelHighLevel/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelHighLevel.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="高层次人才信息集.xlsx"/>
				<ul class="ul-form">
					<li><label class="width120">高层次人才名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width160">高层次人才称号批准日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHighLevel.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHighLevel.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelHighLevel:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelHighLevel/form?mType=1','${ctx}/personnel/personnelHighLevel?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelHighLevel/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelHighLevel?mType=1')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelHighLevel?mType=1&idNumber=${personnelHighLevel.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelHighLevel" action="${ctx}/personnel/personnelHighLevel/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelHighLevel.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="高层次人才信息集.xlsx"/>
				<ul class="ul-form">
					<li><label class="width120">高层次人才名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width160">高层次人才称号批准日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							value="<fmt:formatDate value="${personnelHighLevel.startDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHighLevel.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelHighLevel:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelHighLevel/form?idNumber=${personnelHighLevel.idNumber}','${ctx}/personnel/personnelHighLevel?idNumber=${personnelHighLevel.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelHighLevel/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelHighLevel?idNumber=${personnelHighLevel.idNumber}')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelHighLevel?idNumber=${personnelHighLevel.idNumber}'"/></li>
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
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				</c:if>
				<th>序号</th>
				<c:if test="${mType eq '1'}">
				<th>姓名</th>
				<th>身份证号</th>
				</c:if>
				<th>高层次人才类别</th>
				<th>高层次人才名称</th>
				<th>高层次人才享受待遇</th>
				<th>高层次人才永久居留权地名称</th>
				<th>高层次人才永久居留权地代码</th>
				<th>专业类别描述</th>
				<th>高层次人才称号批准日期</th>
				<th>高层次人才称号批准单位名称</th>
				<th>高层次人才称号批准单位隶属关系</th>
				<th>高层次人才称号批准单位级别</th>
				<c:if test="${mType==null || mType eq '1'}">
					<shiro:hasPermission name="personnel:personnelHighLevel:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelHighLevel" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelHighLevel.id}"/>
				</td></c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${mType eq '1'}">
				<td>
						${personnelHighLevel.personName}
				</td>
				<td>
						${personnelHighLevel.idNumber}
				</td>
				</c:if>
				<td>
					${fns:getDictLabel(personnelHighLevel.type, 'personnel_gccrclb', '')}
				</td>
				<td>
					${personnelHighLevel.name}
				</td>
				<td>
					${personnelHighLevel.treatment}
				</td>
				<td>
					${personnelHighLevel.placeName}
				</td>
				<td>
					${personnelHighLevel.placeCode}
				</td>
				<td>
					${personnelHighLevel.describe}
				</td>
				<td>
					<fmt:formatDate value="${personnelHighLevel.approvalDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelHighLevel.approvalUnit}
				</td>
				<td>
					${personnelHighLevel.approvalUnitRelationship}
				</td>
				<td>
					${fns:getDictLabel(personnelHighLevel.approvalUnitLevel, 'personnel_gccdwjb', '')}
				</td>
				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<shiro:hasPermission name="personnel:personnelHighLevel:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelHighLevel.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelHighLevel/form?id=${personnelHighLevel.id}','${ctx}/personnel/personnelHighLevel?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelHighLevel/delete?id=${personnelHighLevel.id}&mType=1" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td>
						</shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="personnel:personnelHighLevel:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelHighLevel.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelHighLevel/form?id=${personnelHighLevel.id}','${ctx}/personnel/personnelHighLevel?idNumber=${personnelHighLevel.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelHighLevel/delete?id=${personnelHighLevel.id}&idNumber=${personnelHighLevel.idNumber}" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
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
