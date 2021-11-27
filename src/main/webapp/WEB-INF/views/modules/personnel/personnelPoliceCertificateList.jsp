<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人民警察证信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/");
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
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelPoliceCertificate", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}

		function openImportForm(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPoliceCertificate&isCover=1","人民警察证信息导入",800,520,{title:"人民警察证信息导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPoliceCertificate&isCover=0","人民警察证信息导入",800,520,{title:"人民警察证信息导入", buttons:{"关闭":true},
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
			top.$.jBox.open("iframe:"+url, "人民警察证信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPoliceCertificate/formDetail?id="+id;
			top.$.jBox.open(url, "人民警察证信息",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
		var height = $(window).height();
		height = height-10;
		var width = $(window).width();
		width = width;
        function openScreenDialog() {
			var startEndDate=$("#startEndDate").val();
			var endEndDate=$("#endEndDate").val();
			var status=$("#status").val();
			top.$.jBox.open("iframe:${ctx}/personnel/personnelPoliceCertificate/screen?startEndDate="+startEndDate
					+"&endEndDate="+endEndDate+"&status="+status, "人民警察证信息",width,height,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
			<%--$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/screen");--%>
			// $("#searchForm").submit();

			//添加，修改弹窗
			function openDialog(url) {
				var url = "iframe:${ctx}/personnel/personnelPoliceCertificate/historyform?idNumber="+idNumber;
				top.$.jBox.open("iframe:"+url, " ",1200,600,{
					buttons:{"关闭":true},
					loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){self.location.href="${ctx}/personnel/personnelPoliceCertificate"}
				});
			}
		}
		//历史记录
		function openHistory(idNumber) {
			var url = "iframe:${ctx}/personnel/personnelPoliceCertificate/historyform?idNumber="+idNumber;
			top.$.jBox.open(url, "",900,500,{
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
		<c:when test="${mType eq '2'}">

		</c:when>
		<c:when test="${mType eq '1'}">
			<form:form id="searchForm" modelAttribute="personnelPoliceCertificate" action="${ctx}/personnel/personnelPoliceCertificate/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceCertificate.idNumber}"/>
				<input id="type" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="警察证信息.xlsx"/>
				<ul class="ul-form">
					<li><label>警察证号：</label>
						<form:input path="certificateNo" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>警察证状态：</label>
						<form:select path="status" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_jcztype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li><label>发证日期：</label>
						<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceCertificate.beginDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceCertificate.finishDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>有效期截止日期：</label>
						<input id="startEndDate" name="startEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceCertificate.startEndDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input id="endEndDate" name="endEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceCertificate.endEndDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>姓名：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelPoliceCertificate:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceCertificate/form?mType=1','${ctx}/personnel/personnelPoliceCertificate?mType=1')"/></li>
<%--						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceCertificate/deleteByIds','checkAll','myCheckBox')"/></li>--%>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPoliceCertificate?mType=1')"/></li>
					</shiro:hasPermission>
<%--					<li class="btns"><input id="zz" class="btn btn-primary" type="button" value="制证"/></li>--%>
					<li class="btns"><input id="screen" class="btn btn-primary" type="button" value="人民警察证信息筛选"
											onclick="openScreenDialog()"/></li>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPoliceCertificate?idNumber=${personnelPoliceCertificate.idNumber}&mType=${mType}'"/>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelPoliceCertificate" action="${ctx}/personnel/personnelPoliceCertificate/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceCertificate.idNumber}"/>
				<input id="type" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="警察证信息.xlsx"/>
				<ul class="ul-form">
					<li><label>警察证号：</label>
						<form:input path="certificateNo" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>警察证状态：</label>
						<form:select path="status" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_jcztype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li><label>发证日期：</label>
						<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceCertificate.beginDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceCertificate.finishDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>姓名：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelPoliceCertificate:edit">
<%--						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceCertificate/form?idNumber=${personnelPoliceCertificate.idNumber}','${ctx}/personnel/personnelPoliceCertificate?idNumber=${personnelPoliceCertificate.idNumber}')"/></li>--%>
<%--						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceCertificate/deleteByIds','checkAll','myCheckBox')"/></li>--%>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPoliceCertificate?idNumber=${personnelPoliceCertificate.idNumber}')"/></li>
					</shiro:hasPermission>
<%--					<li class="btns"><input id="zz" class="btn btn-primary" type="button" value="制证"/></li>--%>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPoliceCertificate?idNumber=${personnelPoliceCertificate.idNumber}&mType=${mType}'"/></li>
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
				<th>单位</th>
				<th>部门</th>
				<th>姓名</th>
				<th>警察证号</th>
				<th>身份证号</th>
				</c:if>
				<th>制发原因</th>
				<th>警察证状态</th>
				<th>发证日期</th>
				<c:if test="${mType==null || mType eq '1'}">
				<shiro:hasPermission name="personnel:personnelPoliceCertificate:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPoliceCertificate" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelPoliceCertificate.id}"/></td>
				</c:if>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${mType eq '1'}">
				<td>
<%--						${fns:getDictLabel(personnelPoliceCertificate.unitId,"company","")}--%>
                         ${personnelPoliceCertificate.unit}
				</td>
				<td>
						${personnelPoliceCertificate.department}
				</td>
				<td>
					<a href="javascript:" onclick="openHistory('${personnelPoliceCertificate.idNumber}')">
							${personnelPoliceCertificate.name}
					</a>
				</td>
				<td>
					${personnelPoliceCertificate.certificateNo}
				</td>
				<td>
						${personnelPoliceCertificate.idNumber}
				</td>
				</c:if>
				<td>
					${fns:getDictLabel(personnelPoliceCertificate.createReason, 'certification_type', '')}
				</td>
				<td>
					${fns:getDictLabel(personnelPoliceCertificate.status, 'personnel_jcztype', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnelPoliceCertificate.sendDate}" pattern="yyyy-MM-dd"/>
				</td>
				<%--<td>
					${personnelPoliceCertificate.orgName}
				</td>
				<td>
					${personnelPoliceCertificate.backReason}
				</td>--%>

				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<td class="handleTd">
							<a href="javascript:" onclick="openDetailDialog('${personnelPoliceCertificate.id}')">查看</a>
							<shiro:hasPermission name="personnel:personnelPoliceCertificate:edit">
<%--                                <c:if test="${personnelPoliceCertificate.createBy.id == fns:getUser().id}">--%>
                                    <a href="javascript:void(0)"
                                       onclick="openForm('${ctx}/personnel/personnelPoliceCertificate/form?id=${personnelPoliceCertificate.id}','${ctx}/personnel/personnelPoliceCertificate?mType=1')">修改</a>
                                    <a href="${ctx}/personnel/personnelPoliceCertificate/delete?id=${personnelPoliceCertificate.id}&mType=1"
                                       onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                                </c:if>--%>
							</shiro:hasPermission>
						</td>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="personnel:personnelPoliceCertificate:edit">
							<td class="handleTd">
<%--                                <c:if test="${personnelPoliceCertificate.createBy.id == fns:getUser().id}">--%>
                                    <a href="javascript:void(0)"
                                       onclick="openForm('${ctx}/personnel/personnelPoliceCertificate/form?id=${personnelPoliceCertificate.id}','${ctx}/personnel/personnelPoliceCertificate?idNumber=${personnelPoliceCertificate.idNumber}')">修改</a>
                                    <a href="${ctx}/personnel/personnelPoliceCertificate/delete?id=${personnelPoliceCertificate.id}&idNumber=${personnelPoliceCertificate.idNumber}"
                                       onclick="return confirmx('确认要删除                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  该离退信息吗？', this.href)">删除</a>
<%--                                </c:if>--%>
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
