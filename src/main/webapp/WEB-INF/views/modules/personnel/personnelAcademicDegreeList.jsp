<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学位信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelAcademicDegree/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelAcademicDegree/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelAcademicDegree/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelAcademicDegree/");
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
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelAcademicDegree", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "学位信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelAcademicDegree/formDetail?id="+id;
			top.$.jBox.open(url, "学位信息",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
		function openImportForm(url,title) {
			if(title == null || title == ''){
				title = '学位信息集导入';
			}
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelAcademicDegree&isCover=1",title,800,520,{title:title, buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelAcademicDegree&isCover=0",title,800,520,{title:title, buttons:{"关闭":true},
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
			<form:form id="searchForm" modelAttribute="personnelAcademicDegree" action="${ctx}/personnel/personnelAcademicDegree/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelAcademicDegree.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="学位信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>学位名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>授予日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelAcademicDegree.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						-
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelAcademicDegree.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>授予国家：</label>
						<form:select path="country" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_guojia')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelAcademicDegree:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelAcademicDegree/form?mType=1','${ctx}/personnel/personnelAcademicDegree?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelAcademicDegree/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelAcademicDegree?mType=1')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelAcademicDegree?mType=1&idNumber=${personnelAcademicDegree.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelAcademicDegree" action="${ctx}/personnel/personnelAcademicDegree/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelAcademicDegree.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="学位信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>学位名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>授予日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							value="<fmt:formatDate value="${personnelAcademicDegree.startDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelAcademicDegree.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>授予国家：</label>
						<form:select path="country" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_guojia')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
						<shiro:hasPermission name="personnel:personnelAcademicDegree:edit">
							<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelAcademicDegree/form?idNumber=${personnelAcademicDegree.idNumber}','${ctx}/personnel/personnelAcademicDegree?idNumber=${personnelAcademicDegree.idNumber}')"/></li>
							<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelAcademicDegree/deleteByIds','checkAll','myCheckBox')"/></li>
							<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelAcademicDegree?idNumber=${personnelAcademicDegree.idNumber}')"/></li>
							<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
							<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
						</shiro:hasPermission>
						<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelAcademicDegree?idNumber=${personnelAcademicDegree.idNumber}'"/></li>
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
				<c:if test="${mType eq '1'}">
				<th>姓名</th>
				<th>身份证号</th>
				</c:if>
				<th>学位名称</th>
				<th>授予日期</th>
				<th>授予单位</th>
				<th>授予国家</th>
				<th>最高学位说明</th>
				<th>记录状态</th>
				<c:if test="${mType==null || mType eq '1'}">
					<shiro:hasPermission name="personnel:personnelAcademicDegree:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelAcademicDegree" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
					<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelAcademicDegree.id}"/></td></c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${mType eq '1'}">
				<td>
						${personnelAcademicDegree.personName}
				</td>
				<td>
						${personnelAcademicDegree.idNumber}
				</td>
				</c:if>
				<td><a onclick="openDetailDialog('${personnelAcademicDegree.id}')">
					${personnelAcademicDegree.name}
				</a></td>
				<td>
					<fmt:formatDate value="${personnelAcademicDegree.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelAcademicDegree.unit}
				</td>
				<td>
					${fns:getDictLabel(personnelAcademicDegree.country, 'personnel_guojia', '')}
				</td>
				<td>
					${personnelAcademicDegree.explain}
				</td>
				<td>
					${fns:getDictLabel(personnelAcademicDegree.status, 'personnel_jltype', '')}
				</td>
				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<shiro:hasPermission name="personnel:personnelAcademicDegree:edit">
						<td class="handleTd">
<%--							<c:if test="${personnelAcademicDegree.createBy.id == fns:getUser().id}">--%>
								<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelAcademicDegree/form?id=${personnelAcademicDegree.id}','${ctx}/personnel/personnelAcademicDegree?mType=1')">修改</a>
								<a href="${ctx}/personnel/personnelAcademicDegree/delete?id=${personnelAcademicDegree.id}&mType=1" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--							</c:if>--%>
						</td>
						</shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="personnel:personnelAcademicDegree:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelAcademicDegree.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelAcademicDegree/form?id=${personnelAcademicDegree.id}','${ctx}/personnel/personnelAcademicDegree?idNumber=${personnelAcademicDegree.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelAcademicDegree/delete?id=${personnelAcademicDegree.id}&idNumber=${personnelAcademicDegree.idNumber}" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td>
						</shiro:hasPermission>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
