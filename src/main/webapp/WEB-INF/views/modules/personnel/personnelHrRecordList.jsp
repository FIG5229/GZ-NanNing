<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人事档案管理信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelHrRecord/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelHrRecord/list");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelHrRecord/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelHrRecord/list");
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
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelHrRecord", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "人事档案管理信息",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelHrRecord/formDetail?id="+id;
			top.$.jBox.open(url, "人事档案管理信息",800,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }

		//转入档案登记表
		function openZhuanRu(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "转入档案登记表",1200,800,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//转出档案登记表
		function openZhuanChu(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "转出档案登记表",1200,800,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//台账
		function openTaiZhang(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "台账",1200,800,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}

		//导入,  增加判断 覆盖/插入
		// 打开导入页面请求路径由 ${ctx}/file/template/download/view?id= xxxx
		// 调整为 ${ctx}/file/template/personnelBasesdownload/view?id= xxxx &isCover=1
		function openImportForm(url,title) {
			//日常联系情况信息导入
			if(title == null || title == ''){
				title = '人事档案管理信息导入';
			}
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelHrRecord&isCover=1",title,800,520,{title:title, buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelHrRecord&isCover=0",title,800,520,{title:title, buttons:{"关闭":true},
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
			<form:form id="searchForm" modelAttribute="personnelHrRecord" action="${ctx}/personnel/personnelHrRecord/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelHrRecord.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="人事档案管理信息集.xlsx"/>
				<ul class="ul-form">
					<li><label class="width120">档案转出日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHrRecord.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHrRecord.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label class="width120">档案转入日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHrRecord.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHrRecord.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>档案编号：</label>
						<form:input path="recordNo" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width120">档案管理单位：</label>
						<form:select path="unit" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_daunit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li><label class="width120">档案版本类别：</label>
						<form:select path="type" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_banben')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelHrRecord:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelHrRecord/form?mType=1','${ctx}/personnel/personnelHrRecord?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelHrRecord/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelHrRecord?mType=1')"/></li>
						<li class="btns"><input id="taizhang" class="btn btn-primary" type="button" value="台账" onclick="openTaiZhang('${ctx}/affair/affairArchiveRegister/taizhang','${ctx}/personnel/personnelHrRecord?mType=1')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="zhuanchu" class="btn btn-primary" type="button" value="转出档案登记表" onclick="openZhuanChu('${ctx}/affair/affairLedgerOut/list','${ctx}/personnel/personnelHrRecord?mType=1')"/></li>
					<li class="btns"><input id="zhuanru" class="btn btn-primary" type="button" value="转入档案登记表" onclick="openZhuanRu('${ctx}/affair/affairLedgerInto/list','${ctx}/personnel/personnelHrRecord?mType=1')"/></li>
<%--					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>--%>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelHrRecord?mType=1&?idNumber=${personnelHrRecord.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelHrRecord" action="${ctx}/personnel/personnelHrRecord/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelHrRecord.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="人事档案管理信息集.xlsx"/>
				<ul class="ul-form">
					<li><label class="width120">档案转出日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHrRecord.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHrRecord.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label class="width120">档案转入日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							value="<fmt:formatDate value="${personnelHrRecord.startDate}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelHrRecord.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>档案编号：</label>
						<form:input path="recordNo" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label class="width120">档案管理单位：</label>
						<form:select path="unit" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_daunit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li><label class="width120">档案版本类别：</label>
						<form:select path="type" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_banben')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelHrRecord:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelHrRecord/form?idNumber=${personnelHrRecord.idNumber}','${ctx}/personnel/personnelHrRecord?idNumber=${personnelHrRecord.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelHrRecord/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelHrRecord?idNumber=${personnelHrRecord.idNumber}')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelHrRecord?idNumber=${personnelHrRecord.idNumber}'"/></li>
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
				<th>身份证号</th>
				<th>档案管理单位</th>
				<th>部门</th>
				<th>姓名</th>
				<th>职务</th>
				<th>级别</th>
				</c:if>
				<th>档案转入日期</th>
				<th>档案来处</th>
				<c:if test="${mType==null || mType eq '1'}">
					<shiro:hasPermission name="personnel:personnelHrRecord:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelHrRecord" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelHrRecord.id}"/>
				</td>
				</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>

				<c:if test="${mType eq '1'}">
				<td>
						${personnelHrRecord.idNumber}
				</td>
				<td>
						${fns:getDictLabel(personnelHrRecord.unit, 'personnel_daunit', '')}
				</td>
				<td>
						${personnelHrRecord.department}
				</td>
				<td>
						${personnelHrRecord.name}
				</td>
				<td>
						${personnelHrRecord.duty}
				</td>
				<td>
						${personnelHrRecord.level}
				</td>
				</c:if>
				<td><a href="javascript:void(0)" onclick="openDetailDialog('${personnelHrRecord.id}')">
					<fmt:formatDate value="${personnelHrRecord.intoDate}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
					${personnelHrRecord.source}
				</td>
				<%--<td>
					${personnelHrRecord.recordNo}
				</td>
				<td>
					${fns:getDictLabel(personnelHrRecord.type, 'personnel_banben', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnelHrRecord.outDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelHrRecord.remark}
				</td>--%>
				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<shiro:hasPermission name="personnel:personnelHrRecord:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelHrRecord.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelHrRecord/form?id=${personnelHrRecord.id}','${ctx}/personnel/personnelHrRecord?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelHrRecord/delete?id=${personnelHrRecord.id}&mType=1" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td>
						</shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="personnel:personnelHrRecord:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelHrRecord.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelHrRecord/form?id=${personnelHrRecord.id}','${ctx}/personnel/personnelHrRecord?idNumber=${personnelHrRecord.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelHrRecord/delete?id=${personnelHrRecord.id}&idNumber=${personnelHrRecord.idNumber}" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
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
