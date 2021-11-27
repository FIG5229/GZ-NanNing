<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>住址通信信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelAddress/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelAddress/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelAddress/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelAddress/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
		});
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelAddress", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "住址通信信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelAddress/formDetail?id="+id;
			top.$.jBox.open(url, "住址通信信息",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //导入 新
		function openImportForm(url,title) {
			if(title == null || title == ''){
				title = '住址通信信息集导入';
			}
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelAddress&isCover=1",title,800,520,{title:title, buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelAddress&isCover=0",title,800,520,{title:title, buttons:{"关闭":true},
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
	<c:when test="${mType eq '2'}"></c:when>
	<c:when test="${mType eq '1'}">
		<form:form id="searchForm" modelAttribute="personnelAddress" action="${ctx}/personnel/personnelAddress/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${personnelAddress.idNumber}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="fileName" name="fileName" type="hidden" value="住址通信信息集.xlsx"/>
			<ul class="ul-form">
				<li><label class="width120">工作单位通信地址：</label>
					<form:input path="unitAdress" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label class="width120">工作单位邮政编码：</label>
					<form:input path="unitPostCode" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>工作电话：</label>
					<form:input path="workTel" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>家庭住址：</label>
					<form:input path="familyAddress" htmlEscape="false" class="input-medium"/>
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="personnel:personnelAddress:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelAddress/form?mType=1','${ctx}/personnel/personnelAddress?mType=1')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelAddress/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelAddress?mType=1')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
		     	<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelAddress?mType=1&idNumber=${personnelAddress.idNumber}'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
	</c:when>
	<c:otherwise>
		<form:form id="searchForm" modelAttribute="personnelAddress" action="${ctx}/personnel/personnelAddress/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${personnelAddress.idNumber}"/>
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="fileName" name="fileName" type="hidden" value="住址通信信息集.xlsx"/>
			<ul class="ul-form">
				<li><label class="width120">工作单位通信地址：</label>
					<form:input path="unitAdress" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label class="width120">工作单位邮政编码：</label>
					<form:input path="unitPostCode" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>工作电话：</label>
					<form:input path="workTel" htmlEscape="false" class="input-medium"/>
				</li>
				<li><label>家庭住址：</label>
					<form:input path="familyAddress" htmlEscape="false" class="input-medium"/>
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="personnel:personnelAddress:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelAddress/form?idNumber=${personnelAddress.idNumber}','${ctx}/personnel/personnelAddress?idNumber=${personnelAddress.idNumber}')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelAddress/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelAddress?idNumber=${personnelAddress.idNumber}')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelAddress?idNumber=${personnelAddress.idNumber}'"/></li>
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
				<th>工作单位通信地址</th>
				<th>工作单位邮政编码</th>
				<th>工作电话</th>
				<th>家庭住址</th>
				<th>个人电子信箱</th>
				<th>移动电话号码</th>
				<th>家庭电话</th>
				<c:if test="${mType==null || mType eq '1'}">
				<shiro:hasPermission name="personnel:personnelAddress:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelAddress" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelAddress.id}"/></td>
				</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${mType eq '1'}">
				<td>
						${personnelAddress.personnelName}
				</td>
				<td>
						${personnelAddress.idNumber}
				</td>
				</c:if>
				<td><a onclick="openDetailDialog('${personnelAddress.id}')">
					${personnelAddress.unitAdress}
				</a></td>
				<td>
					${personnelAddress.unitPostCode}
				</td>
				<td>
					${personnelAddress.workTel}
				</td>
				<td>
					${personnelAddress.familyAddress}
				</td>
				<td>
					${personnelAddress.email}
				</td>
				<td>
					${personnelAddress.mobilePhone}
				</td>
				<td>
					${personnelAddress.homeTel}
				</td>
				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<shiro:hasPermission name="personnel:personnelAddress:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelAddress.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelAddress/form?id=${personnelAddress.id}','${ctx}/personnel/personnelAddress?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelAddress/delete?id=${personnelAddress.id}&mType=1" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td>
						</shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="personnel:personnelAddress:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelAddress.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelAddress/form?id=${personnelAddress.id}','${ctx}/personnel/personnelAddress?idNumber=${personnelAddress.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelAddress/delete?id=${personnelAddress.id}&idNumber=${personnelAddress.idNumber}" onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
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
