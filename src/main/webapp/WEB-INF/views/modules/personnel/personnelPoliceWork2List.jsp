<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警务技术(专业技术)任职资格信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceWork2/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceWork2/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceWork2/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceWork2/");
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
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelPoliceWork2", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "警务技术(专业技术)任职资格信息添加",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPoliceWork2/formDetail?id="+id;
			top.$.jBox.open(url, "警务技术(专业技术)任职资格信息",800,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

		function openImportForm(url,title) {
			if(title == null || title == ''){
				title = '警务技术(专业技术)任职资格信息集导入';
			}
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPoliceWork2&isCover=1",title,800,520,{title:title, buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPoliceWork2&isCover=0",title,800,520,{title:title, buttons:{"关闭":true},
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
				<li class="active"><a href="${ctx}/personnel/personnelPoliceWork2?mType=3">警务技术任职资格管理</a></li>
			</ul>
			<form:form id="searchForm" modelAttribute="personnelPoliceWork2" action="${ctx}/personnel/personnelPoliceWork2/" method="post" class="breadcrumb form-search">
		     	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceWork2.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="警务技术(专业技术)任职资格信息集.xlsx"/>
				<ul class="ul-form">
					<li><label class="width120">任职资格名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width120">取得资格日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceWork2.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						-
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceWork2.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelPoliceWork2:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceWork2/form?mType=3','${ctx}/personnel/personnelPoliceWork2?mType=3')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceWork2/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPoliceWork2?mType=3')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPoliceWork2?mType=3&idNumber=${personnelPoliceWork2.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:when test="${mType eq '2'}">

		</c:when>
		<c:when test="${mType eq '1'}">
			<form:form id="searchForm" modelAttribute="personnelPoliceWork2" action="${ctx}/personnel/personnelPoliceWork2/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceWork2.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="警务技术(专业技术)任职资格信息集.xlsx"/>
				<ul class="ul-form">
					<li><label class="width120">任职资格名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width120">取得资格日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceWork2.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						-
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceWork2.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelPoliceWork2:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceWork2/form?mType=1','${ctx}/personnel/personnelPoliceWork2?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceWork2/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPoliceWork2?mType=1')"/></li>
					</shiro:hasPermission>
						<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
						<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPoliceWork2?mType=1&idNumber=${personnelPoliceWork2.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelPoliceWork2" action="${ctx}/personnel/personnelPoliceWork2/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceWork2.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="警务技术(专业技术)任职资格信息集.xlsx"/>
				<ul class="ul-form">
					<li><label class="width120">任职资格名称：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width120">取得资格日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							value="<fmt:formatDate value="${personnelPoliceWork2.startDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						-
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceWork2.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelPoliceWork2:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceWork2/form?idNumber=${personnelPoliceWork2.idNumber}','${ctx}/personnel/personnelPoliceWork2?idNumber=${personnelPoliceWork2.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceWork2/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPoliceWork2?idNumber=${personnelPoliceWork2.idNumber}')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPoliceWork2?idNumber=${personnelPoliceWork2.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:otherwise>
	</c:choose>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:if test="${mType==null || mType eq '1' ||mType eq '3'}">
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				</c:if>
				<th>序号</th>
				<c:if test="${mType eq '1' ||mType eq '3'}">
				<th>姓名</th>
				<th>身份证号</th>
				</c:if>
				<th>任职资格名称</th>
				<th>取得资格日期</th>
				<th>任职资格级别</th>
				<th>证书编号</th>
				<th>考试名称</th>
				<c:if test="${mType==null || mType eq '1' ||mType eq '3'}">
					<shiro:hasPermission name="personnel:personnelPoliceWork2:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPoliceWork2" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1' ||mType eq '3'}">
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelPoliceWork2.id}"/>
				</td></c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${mType eq '1' ||mType eq '3'}">
				<td>
						${personnelPoliceWork2.personName}
				</td>
				<td>
						${personnelPoliceWork2.idNumber}
				</td>
				</c:if>
				<td>
					${personnelPoliceWork2.name}
				</td>
				<td>
					<fmt:formatDate value="${personnelPoliceWork2.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelPoliceWork2.level}
				</td>
				<td>
					${personnelPoliceWork2.certificateNo}
				</td>
				<td>
					${personnelPoliceWork2.examName}
				</td>

				<c:choose>
					<c:when test="${mType eq '3'}">
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelPoliceWork2.id}')">查看</a>
						<shiro:hasPermission name="personnel:personnelPoliceWork2:edit">

<%--                                <c:if test="${personnelPoliceWork2.createBy.id == fns:getUser().id}">--%>
                                    <a href="javascript:void(0)"
                                       onclick="openForm('${ctx}/personnel/personnelPoliceWork2/form?id=${personnelPoliceWork2.id}','${ctx}/personnel/personnelPoliceWork2?mType=3')">修改</a>
                                    <a href="${ctx}/personnel/personnelPoliceWork2/delete?id=${personnelPoliceWork2.id}&mType=3"
                                       onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                                </c:if>--%>

						</shiro:hasPermission>
						</td>
					</c:when>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelPoliceWork2.id}')">查看</a>
						<shiro:hasPermission name="personnel:personnelPoliceWork2:edit">

<%--                                <c:if test="${personnelPoliceWork2.createBy.id == fns:getUser().id}">--%>
                                    <a href="javascript:void(0)"
                                       onclick="openForm('${ctx}/personnel/personnelPoliceWork2/form?id=${personnelPoliceWork2.id}','${ctx}/personnel/personnelPoliceWork2?mType=1')">修改</a>
                                    <a href="${ctx}/personnel/personnelPoliceWork2/delete?id=${personnelPoliceWork2.id}&mType=1"
                                       onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                                </c:if>--%>

						</shiro:hasPermission>
						</td>
					</c:when>
					<c:otherwise>
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelPoliceWork2.id}')">查看</a>
						<shiro:hasPermission name="personnel:personnelPoliceWork2:edit">

<%--                                <c:if test="${personnelPoliceWork2.createBy.id == fns:getUser().id}">--%>
                                    <a href="javascript:void(0)"
                                       onclick="openForm('${ctx}/personnel/personnelPoliceWork2/form?id=${personnelPoliceWork2.id}','${ctx}/personnel/personnelPoliceWork2?idNumber=${personnelPoliceWork2.idNumber}')">修改</a>
                                    <a href="${ctx}/personnel/personnelPoliceWork2/delete?id=${personnelPoliceWork2.id}&idNumber=${personnelPoliceWork2.idNumber}"
                                       onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                                </c:if>--%>

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
