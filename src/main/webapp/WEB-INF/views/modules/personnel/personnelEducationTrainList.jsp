<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训（进修）信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelEducationTrain/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelEducationTrain/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelEducationTrain/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelEducationTrain/");
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
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelEducationTrain", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "培训（进修）信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelEducationTrain/formDetail?id="+id;
			top.$.jBox.open(url, "培训（进修）信息",800,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

		//导入履历信息-新，增加判断 覆盖/插入
		// 打开导入页面请求路径由 ${ctx}/file/template/download/view?id= xxxx
		// 调整为 ${ctx}/file/template/personnelBasesdownload/view?id= xxxx &isCover=1
		function openImportForm(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelEducationTrain&isCover=1","教育培训导入",800,520,{title:"教育培训导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelEducationTrain&isCover=0","教育培训导入",800,520,{title:"教育培训导入", buttons:{"关闭":true},
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
			<form:form id="searchForm" modelAttribute="personnelEducationTrain" action="${ctx}/personnel/personnelEducationTrain/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelEducationTrain.idNumber}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="培训进修信息集.xlsx"/>
				<ul class="ul-form">
				<li><label class="width120">培训班名称：</label>
					<form:input path="className" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>起始日期：</label>
					<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelEducationTrain.beginDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					至
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelEducationTrain.finishDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelEducationTrain:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelEducationTrain/form?mType=1','${ctx}/personnel/personnelEducationTrain?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelEducationTrain/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelEducationTrain?mType=1')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			    <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelEducationTrain?mType=1&idNumber=${personnelEducationTrain.idNumber}'"/></li>
			  <li class="clearfix"></li>
			   </ul>
		   </form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelEducationTrain" action="${ctx}/personnel/personnelEducationTrain/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelEducationTrain.idNumber}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="培训进修信息集.xlsx"/>
				<ul class="ul-form">
					<li><label class="width120">培训班名称：</label>
						<form:input path="className" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>起始日期：</label>
						<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelEducationTrain.beginDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelEducationTrain.finishDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelEducationTrain:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelEducationTrain/form?idNumber=${personnelEducationTrain.idNumber}','${ctx}/personnel/personnelEducationTrain?idNumber=${personnelEducationTrain.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelEducationTrain/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelEducationTrain?idNumber=${personnelEducationTrain.idNumber}')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelEducationTrain?idNumber=${personnelEducationTrain.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
	    </c:otherwise>
	</c:choose>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><c:if test="${mType==null || mType eq '1'}">
					<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
			    </c:if>
				<th>序号</th>
				<c:if test="${mType eq '1'}">
				<th>姓名</th>
				<th>身份证号</th>
				</c:if>
				<th>培训班名称</th>
				<th>培训类别</th>
				<th>培训完成情况</th>
				<th>培训时所在单位及职务</th>
				<th>培训机构名称</th>
				<c:if test="${mType==null || mType eq '1'}">
				<shiro:hasPermission name="personnel:personnelEducationTrain:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelEducationTrain" varStatus="status">
			<tr><c:if test="${mType==null || mType eq '1'}">
					<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelEducationTrain.id}"/></td>
			    </c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${mType eq '1'}">
				<td>
						${personnelEducationTrain.personName}
				</td>
				<td>
						${personnelEducationTrain.idNumber}
				</td>
				</c:if>
				<td>
					${personnelEducationTrain.className}
				</td>
				<td>
					${fns:getDictLabel(personnelEducationTrain.type, 'personnel_pxtype', '')}
				</td>
				<td>
					${personnelEducationTrain.situation}
				</td>
				<td>
					${personnelEducationTrain.unitJob}
				</td>
				<td>
					${personnelEducationTrain.organizationName}
				</td>
				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelEducationTrain.id}')">查看</a>
						<shiro:hasPermission name="personnel:personnelEducationTrain:edit">

								<%--<c:if test="${personnelEducationTrain.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelEducationTrain/form?id=${personnelEducationTrain.id}','${ctx}/personnel/personnelEducationTrain?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelEducationTrain/delete?id=${personnelEducationTrain.id}&mType=1"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
								<%--</c:if>--%>

						</shiro:hasPermission>
						</td>
					</c:when>
					<c:otherwise>
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelEducationTrain.id}')">查看</a>
						<shiro:hasPermission name="personnel:personnelEducationTrain:edit">

<%--
								<c:if test="${personnelEducationTrain.createBy.id == fns:getUser().id}">
--%>
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelEducationTrain/form?id=${personnelEducationTrain.id}','${ctx}/personnel/personnelEducationTrain?idNumber=${personnelEducationTrain.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelEducationTrain/delete?id=${personnelEducationTrain.id}&idNumber=${personnelEducationTrain.idNumber}"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--
								</c:if>
--%>
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
