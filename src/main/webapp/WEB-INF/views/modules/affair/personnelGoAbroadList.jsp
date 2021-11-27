<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>领导干部出国管理表管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/personnelGoAbroad/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/personnelGoAbroad/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/personnelGoAbroad/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/personnelGoAbroad/");
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
		/*function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelGoAbroad", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}*/


		function excelImp(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelGoAbroad&isCover=1","领导干部出国管理表导入",800,520,{title:"领导干部出国管理表导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelGoAbroad&isCover=0","领导干部出国管理表导入",800,520,{title:"领导干部出国管理表导入", buttons:{"关闭":true},
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
			top.$.jBox.open("iframe:"+url, "出国(境)信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/personnelGoAbroad/formDetail?id="+id;
			top.$.jBox.open(url, "出国(境)信息详情",1200,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

    </script>
</head>
<body>

	<c:choose>
		<c:when test="${mType eq '3'}">
			<ul class="nav nav-tabs">
				<li class="active"><a href="${ctx}/affair/personnelGoAbroad?mType=3">出国境管理</a></li>
			</ul>
				<form:form id="searchForm" modelAttribute="personnelGoAbroad" action="${ctx}/affair/personnelGoAbroad/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="fileName" name="fileName" type="hidden" value="出国（境）信息集.xlsx"/>
					<input id="mType" name="mType" type="hidden" value="${mType}"/>
					<input id="idNumber" name="idNumber" type="hidden" value="${personnelGoAbroad.idNumber}"/>
					<ul class="ul-form">
						<li><label>姓名：</label>
							<form:input path="name" htmlEscape="false" class="input-medium"/>
						</li>
						<li><label class="width120">出国（境）日期：</label>
							<input name="beginGoAbroadDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelGoAbroad.beginGoAbroadDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
							<input name="endGoAbroadDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelGoAbroad.endGoAbroadDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</li>
						<li><label>回国日期：</label>
							<input name="beginReturnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelGoAbroad.beginReturnDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
							<input name="endReturnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelGoAbroad.endReturnDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</li>
					</ul>
					<ul class="ul-form2">
						<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
						<shiro:hasPermission name="affair:personnelGoAbroad:edit">
							<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/personnelGoAbroad/form?mType=3','${ctx}/affair/personnelGoAbroad?mType=3')"/></li>
							<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/personnelGoAbroad/deleteByIds','checkAll','myCheckBox')"/></li>
							<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/affair/personnelGoAbroad?mType=3')"/></li>
						</shiro:hasPermission>
						<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
						<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/personnelGoAbroad?mType=3&idNumber=${personnelGoAbroad.idNumber}'"/></li>
						<li class="clearfix"></li>
					</ul>
				</form:form>
		</c:when>
		<c:when test="${mType eq '2'}">

		</c:when>
		<c:when test="${mType eq '1'}">
			<form:form id="searchForm" modelAttribute="personnelGoAbroad" action="${ctx}/affair/personnelGoAbroad/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="fileName" name="fileName" type="hidden" value="出国（境）信息集.xlsx"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelGoAbroad.idNumber}"/>
				<ul class="ul-form">
					<li><label>姓名：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width120">出国（境）日期：</label>
						<input name="beginGoAbroadDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelGoAbroad.beginGoAbroadDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endGoAbroadDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelGoAbroad.endGoAbroadDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>回国日期：</label>
						<input name="beginReturnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelGoAbroad.beginReturnDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endReturnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelGoAbroad.endReturnDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label class="width140">出国（境）经费来源：</label>
						<form:input path="fundsSource" htmlEscape="false" class="input-medium"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="affair:personnelGoAbroad:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/personnelGoAbroad/form?mType=1','${ctx}/affair/personnelGoAbroad?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/personnelGoAbroad/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/affair/personnelGoAbroad?mType=1')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/personnelGoAbroad?mType=1&idNumber=${personnelGoAbroad.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelGoAbroad" action="${ctx}/affair/personnelGoAbroad/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="fileName" name="fileName" type="hidden" value="出国（境）信息集.xlsx"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelGoAbroad.idNumber}"/>
				<ul class="ul-form">
					<li><label>姓名：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width120">出国（境）日期：</label>
						<input name="beginGoAbroadDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelGoAbroad.beginGoAbroadDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endGoAbroadDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelGoAbroad.endGoAbroadDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>回国日期：</label>
						<input name="beginReturnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelGoAbroad.beginReturnDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
						<input name="endReturnDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelGoAbroad.endReturnDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label class="width140">出国（境）经费来源：</label>
						<form:input path="fundsSource" htmlEscape="false" class="input-medium"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="affair:personnelGoAbroad:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/personnelGoAbroad/form?idNumber=${personnelGoAbroad.idNumber}','${ctx}/affair/personnelGoAbroad?idNumber=${personnelGoAbroad.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/personnelGoAbroad/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/affair/personnelGoAbroad?idNumber=${personnelGoAbroad.idNumber}')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/personnelGoAbroad?idNumber=${personnelGoAbroad.idNumber}'"/></li>
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
				<c:if test="${mType eq '3' || mType eq '1'}">
				<th>姓名</th>
				</c:if>
				<th>出国事由</th>
				<th>持有护照（通行证）上交日期</th>
				<th>持有护照（通行证）领用日期</th>
				<th>出国（境）日期</th>
				<th>回国日期</th>
				<th>派出单位</th>
				<th>所至单位</th>
				<c:if test="${mType==null || mType eq '3' || mType eq '1'}">
					<shiro:hasPermission name="affair:personnelGoAbroad:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelGoAbroad" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '3' || mType eq '1'}">
					<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelGoAbroad.id}"/></td>
				</c:if>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>

				<c:if test="${mType eq '3' || mType eq '1'}">
				<td>
						${personnelGoAbroad.name}
				</td>
				</c:if>
				<td>
					${personnelGoAbroad.reason}
				</td>
				<td>
					<fmt:formatDate value="${personnelGoAbroad.handinDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnelGoAbroad.receiveDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnelGoAbroad.goAbroadDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnelGoAbroad.returnDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelGoAbroad.assignUnit}
				</td>
				<td>
					${personnelGoAbroad.toUnit}
				</td>
				<c:choose>
					<c:when test="${mType eq '3'}">
						<td class="handleTd">
							<a href="javascript:void(0)" onclick="openDetailDialog('${personnelGoAbroad.id}')">查看</a>
						<shiro:hasPermission name="affair:personnelGoAbroad:edit">

                            <c:if test="${personnelGoAbroad.createBy.id == fns:getUser().id}">
                                <a href="javascript:void(0)"
                                   onclick="openForm('${ctx}/affair/personnelGoAbroad/form?id=${personnelGoAbroad.id}','${ctx}/affair/personnelGoAbroad?mType=3')">修改</a>
                                <a href="${ctx}/affair/personnelGoAbroad/delete?id=${personnelGoAbroad.id}&mType=3"
                                   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
                            </c:if>

						</shiro:hasPermission>
						</td>
					</c:when>
					<c:when test="${mType eq '2'}">
					</c:when>
					<c:when test="${mType eq '1'}">
						<td class="handleTd">
							<a href="javascript:void(0)" onclick="openDetailDialog('${personnelGoAbroad.id}')">查看</a>
						<shiro:hasPermission name="affair:personnelGoAbroad:edit">

<%--                            <c:if test="${personnelGoAbroad.createBy.id == fns:getUser().id}">--%>
                                <a href="javascript:void(0)"
                                   onclick="openForm('${ctx}/affair/personnelGoAbroad/form?id=${personnelGoAbroad.id}','${ctx}/affair/personnelGoAbroad?mType=1')">修改</a>
                                <a href="${ctx}/affair/personnelGoAbroad/delete?id=${personnelGoAbroad.id}&mType=1"
                                   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                            </c:if>--%>

						</shiro:hasPermission>
						</td>
					</c:when>
					<c:otherwise>
						<td class="handleTd">
							<a href="javascript:void(0)" onclick="openDetailDialog('${personnelGoAbroad.id}')">查看</a>
						<shiro:hasPermission name="affair:personnelGoAbroad:edit">

<%--
                            <c:if test="${personnelGoAbroad.createBy.id == fns:getUser().id}">
--%>
                                <a href="javascript:void(0)"
                                   onclick="openForm('${ctx}/affair/personnelGoAbroad/form?id=${personnelGoAbroad.id}','${ctx}/affair/personnelGoAbroad?idNumber=${personnelGoAbroad.idNumber}')">修改</a>
                                <a href="${ctx}/affair/personnelGoAbroad/delete?id=${personnelGoAbroad.id}&idNumber=${personnelGoAbroad.idNumber}"
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
