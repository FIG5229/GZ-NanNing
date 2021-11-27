<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抚恤金发放</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPensionRecord/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPensionRecord/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPensionRecord/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPensionRecord/");
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
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelPensionRecord", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //添加弹窗
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "抚恤发放",900,700,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPensionRecord/formDetail?id="+id;
			top.$.jBox.open(url, "",850,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		function openImportForm(url,title) {
			if(title == null || title == ''){
				title = '抚恤金发放记录信息导入';
			}
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPensionRecord&isCover=1",title,800,520,{title:title, buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPensionRecord&isCover=0",title,800,520,{title:title, buttons:{"关闭":true},
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
			<li ><a href="${ctx}/affair/affairCasualtyReport/">抚恤申报</a></li>
			<shiro:hasPermission name="affair:affairCasualtyReport:manage"><li ><a href="${ctx}/affair/affairCasualtyReport/manageList">抚恤审核</a></li></shiro:hasPermission>
			<li class="active"><a href="${ctx}/personnel/personnelPensionRecord?mType=3">抚恤发放</a></li>
			<li><a href="${ctx}/affair/affairCasualtyReport/statistic">伤亡信息查询</a></li>
		</ul>
		<form:form id="searchForm" modelAttribute="personnelPensionRecord" action="${ctx}/personnel/personnelPensionRecord/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${personnelPensionRecord.idNumber}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="fileName" name="fileName" type="hidden" value="抚恤金发放记录信息集.xlsx"/>
			<ul class="ul-form">
                <li><label>单位：</label>
                    <sys:treeselect id="unit" name="unitId" value="${personnelPensionRecord.unitId}" labelName="unit" labelValue="${personnelPensionRecord.unit}"
                                    title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"/>
                </li>
				<li><label class="width120">抚恤金发放日期：</label>
					<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.beginDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					-
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.finishDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label class="width120">抚恤金审批日期：</label>
					<input name="beginDate1" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.beginDate1}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					-
					<input name="finishDate1" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.finishDate1}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label>抚恤金性质：</label>
					<form:select path="character" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('pension_nature')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="personnel:personnelPensionRecord:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPensionRecord/form?mType=3','${ctx}/personnel/personnelPensionRecord?mType=3')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPensionRecord/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPensionRecord?mType=3')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPensionRecord?mType=3&idNumber=${personnelPensionRecord.idNumber}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>
	<c:when test="${mType eq '2'}"></c:when>
	<c:when test="${mType eq '1'}">
		<form:form id="searchForm" modelAttribute="personnelPensionRecord" action="${ctx}/personnel/personnelPensionRecord/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${personnelPensionRecord.idNumber}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="fileName" name="fileName" type="hidden" value="抚恤金发放记录信息集.xlsx"/>
			<ul class="ul-form">
				<li><label class="width120">抚恤金发放日期：</label>
					<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.beginDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					-
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.finishDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label class="width120">抚恤金审批日期：</label>
					<input name="beginDate1" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.beginDate1}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					-
					<input name="finishDate1" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.finishDate1}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label>抚恤金性质：</label>
					<form:select path="character" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('pension_nature')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="personnel:personnelPensionRecord:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPensionRecord/form?mType=1','${ctx}/personnel/personnelPensionRecord?mType=1')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPensionRecord/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPensionRecord?mType=1')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPensionRecord?mType=1&idNumber=${personnelPensionRecord.idNumber}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>
	<c:otherwise>
		<form:form id="searchForm" modelAttribute="personnelPensionRecord" action="${ctx}/personnel/personnelPensionRecord/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${personnelPensionRecord.idNumber}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="fileName" name="fileName" type="hidden" value="抚恤金发放记录信息集.xlsx"/>
			<ul class="ul-form">
				<li><label class="width120">抚恤金发放日期：</label>
					<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.beginDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					-
					<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.finishDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label class="width120">抚恤金审批日期：</label>
					<input name="beginDate1" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.beginDate1}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					-
					<input name="finishDate1" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelPensionRecord.finishDate1}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label>抚恤金性质：</label>
					<form:select path="character" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('pension_nature')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
			</ul>
			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="personnel:personnelPensionRecord:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPensionRecord/form?idNumber=${personnelPensionRecord.idNumber}','${ctx}/personnel/personnelPensionRecord?idNumber=${personnelPensionRecord.idNumber}')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPensionRecord/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPensionRecord?idNumber=${personnelPensionRecord.idNumber}')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPensionRecord?idNumber=${personnelPensionRecord.idNumber}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:otherwise>
</c:choose>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:if test="${mType==null || mType eq '1' || mType eq '3'}">
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				</c:if>
				<th>序号</th>
				<th>单位</th>
				<th>抚恤金发放日期</th>
				<th>抚恤金审批日期</th>
				<th>抚恤金发放对象</th>
				<th>终止发放原因</th>
				<th>终止日期</th>
				<th>抚恤金性质</th>
				<th>抚恤金说明</th>
				<c:if test="${mType==null || mType eq '1' || mType eq '3'}">
					<shiro:hasPermission name="personnel:personnelPensionRecord:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPensionRecord" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1' || mType eq '3'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelPensionRecord.id}"/></td>
				</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${personnelPensionRecord.unit}
				</td>
				<td><a onclick="openDetailDialog('${personnelPensionRecord.id}')">
					<fmt:formatDate value="${personnelPensionRecord.provideDate}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
					<fmt:formatDate value="${personnelPensionRecord.approvalDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelPensionRecord.providePerson}
				</td>
				<td>
					${personnelPensionRecord.endReason}
				</td>
				<td>
					<fmt:formatDate value="${personnelPensionRecord.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(personnelPensionRecord.character, 'pension_nature', '')}
				</td>
				<td>
					${personnelPensionRecord.explain}
				</td>


				<c:choose>
					<c:when test="${mType eq '3'}">
						<shiro:hasPermission name="personnel:personnelPensionRecord:edit">
						<td class="handleTd">
<%--							<c:if test="${personnelPensionRecord.createBy.id == fns:getUser().id}">--%>
								<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelPensionRecord/form?id=${personnelPensionRecord.id}','${ctx}/personnel/personnelPensionRecord?mType=3')">修改</a>
								<a href="${ctx}/personnel/personnelPensionRecord/delete?id=${personnelPensionRecord.id}&mType=3" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--							</c:if>--%>
						</td>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<shiro:hasPermission name="personnel:personnelPensionRecord:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelPensionRecord.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelPensionRecord/form?id=${personnelPensionRecord.id}','${ctx}/personnel/personnelPensionRecord?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelPensionRecord/delete?id=${personnelPensionRecord.id}&mType=1" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td>
						</shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="personnel:personnelPensionRecord:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelPensionRecord.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelPensionRecord/form?id=${personnelPensionRecord.id}','${ctx}/personnel/personnelPensionRecord?idNumber=${personnelPensionRecord.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelPensionRecord/delete?id=${personnelPensionRecord.id}&idNumber=${personnelPensionRecord.idNumber}" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
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
