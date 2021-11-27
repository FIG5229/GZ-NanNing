<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>离退信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("[data-toggle='popover']").popover();
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelRetreat/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelRetreat/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelRetreat/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelRetreat/");
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
		//导入功能
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelRetreat", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function openImportForm(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelRetreat&isCover=1","离退信息导入",800,520,{title:"离退信息导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelRetreat&isCover=0","离退信息导入",800,520,{title:"离退信息导入", buttons:{"关闭":true},
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
        //添加功能
		function openForm(url,pUrl) {
			top.$.jBox.open("iframe:"+url, "离退信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=pUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelRetreat/formDetail?id="+id;
			top.$.jBox.open(url, "离退信息",800,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //批量提交
		function submitByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val());
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/personnel/personnelRetreat/submitByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
        }
        $('#notPass').popover();
	</script>
</head>
<body>
	<c:choose>
		<c:when test="${mType eq '3'}">
			<ul class="nav nav-tabs">
				<li class="active"><a href="${ctx}/personnel/personnelRetreat?mType=3">离退休信息</a></li>
				<li ><a href="${ctx}/personnel/personnelRetreat/manage">离退休手续办理</a></li>
				<li ><a href="${ctx}/personnel/personnelRetreatSum/statistic">离退休情况统计</a></li>
			</ul>
			<form:form id="searchForm" modelAttribute="personnelRetreat" action="${ctx}/personnel/personnelRetreat" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="离退信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>离退类别：</label>
						<form:select path="type" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_lttype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li><label>离退日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelRetreat.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						-
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelRetreat.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>姓名：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelRetreat:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelRetreat/form','${ctx}/personnel/personnelRetreat?mType=3')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelRetreat/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input class="btn btn-primary" type="button" value="提交" onclick="submitByIds()" /></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelRetreat?mType=3')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelRetreat?mType=3'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:when test="${mType eq '2'}">

		</c:when>
		<c:when test="${mType eq '1'}"><form:form id="searchForm" modelAttribute="personnelRetreat" action="${ctx}/personnel/personnelRetreat/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="idNumber" name="idNumber" type="hidden" value="${personnelRetreat.idNumber}" />
			<input id="mType" name="mType" type="hidden" value="${mType}"/>
			<input id="fileName" name="fileName" type="hidden" value="离退信息集.xlsx"/>
			<ul class="ul-form">
				<li><label>离退类别：</label>
					<form:select path="type" class="input-medium">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('personnel_lttype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</li>
				<li><label>离退日期：</label>
					<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelRetreat.startDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					-
					<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						   value="<fmt:formatDate value="${personnelRetreat.endDate}" pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</li>
				<li><label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="personnel:personnelRetreat:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelRetreat/form','${ctx}/personnel/personnelRetreat?mType=1')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelRetreat/deleteByIds','checkAll','myCheckBox')"/></li>
					<li class="btns"><input class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelRetreat?mType=1')"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelRetreat?mType=1'"/></li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelRetreat" action="${ctx}/personnel/personnelRetreat/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelRetreat.idNumber}" />
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="离退信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>离退类别：</label>
						<form:select path="type" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_lttype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li><label>离退日期：</label>
						<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelRetreat.startDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						-
						<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelRetreat.endDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
					<li><label>姓名：</label>
						<form:input path="name" htmlEscape="false" class="input-medium"/>
					</li>
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelRetreat:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelRetreat/form?idNumber=${personnelRetreat.idNumber}','${ctx}/personnel/personnelRetreat?idNumber=${personnelRetreat.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelRetreat/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelRetreat?idNumber=${personnelRetreat.idNumber}')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelRetreat?idNumber=${personnelRetreat.idNumber}'"/></li>
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
				<th>身份证号</th>
				</c:if>
				<th>离退类别</th>
				<th>离退前级别</th>
				<th>离退后现管理单位名称</th>
				<th>离退日期</th>
				<th>离退干部现享受待遇</th>
				<th>离退干部现享受待遇类别</th>
				<th>离退备注</th>
				<th>离退休材料</th>
				<c:if test="${mType==null || mType eq '3' || mType eq '1'}">
				<shiro:hasPermission name="personnel:personnelRetreat:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelRetreat" varStatus="status">
			<tr><c:if test="${mType==null || mType eq '3' || mType eq '1'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelRetreat.id}"/></td>
			</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${mType eq '3' || mType eq '1'}">
				<td>
						${personnelRetreat.name}
				</td>
				<td>
						${personnelRetreat.idNumber}
				</td>
				</c:if>
				<td>
					${fns:getDictLabel(personnelRetreat.type, 'personnel_lttype', '')}
				</td>
				<td>
						${fns:getDictLabel(personnelRetreat.preLevel, 'personnel_ltqtype', '')}
				</td>
				<td>
						${personnelRetreat.nowUnitName}
				</td>
				<td>
						<fmt:formatDate value="${personnelRetreat.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnelRetreat.treatment}
				</td>
				<td>
					${fns:getDictLabel(personnelRetreat.treatmentType, 'personnel_dytype', '')}
				</td>
				<td>
					${personnelRetreat.remarks}
				</td>
				<td>
					${personnelRetreat.fileName}
				</td>
						<c:choose>
							<c:when test="${mType eq '3'}">
								<td class="handleTd">
									<a onclick="openDetailDialog('${personnelRetreat.id}')">查看</a>
								<shiro:hasPermission name="personnel:personnelRetreat:edit">

<%--
                                    <c:if test="${personnelRetreat.createBy.id == fns:getUser().id}">
--%>
                                        <a href="javascript:void(0)"
                                           onclick="openForm('${ctx}/personnel/personnelRetreat/form?id=${personnelRetreat.id}','${ctx}/personnel/personnelRetreat?mType=3')">修改</a>
                                        <a href="${ctx}/personnel/personnelRetreat/delete?id=${personnelRetreat.id}&mType=3"
                                           onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                                    </c:if>--%>

								</shiro:hasPermission>
								</td>
							</c:when>
							<c:when test="${mType eq '2'}">
							</c:when>
							<c:when test="${mType eq '1'}">
								<td class="handleTd">
									<a onclick="openDetailDialog('${personnelRetreat.id}')">查看</a>
								<shiro:hasPermission name="personnel:personnelRetreat:edit">

<%--                                    <c:if test="${personnelRetreat.createBy.id == fns:getUser().id}">--%>
                                        <a href="javascript:void(0)"
                                           onclick="openForm('${ctx}/personnel/personnelRetreat/form?id=${personnelRetreat.id}','${ctx}/personnel/personnelRetreat?mType=1')">修改</a>
                                        <a href="${ctx}/personnel/personnelRetreat/delete?id=${personnelRetreat.id}&mType=1"
                                           onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                                    </c:if>--%>

								</shiro:hasPermission>
								</td>
							</c:when>
							<c:otherwise>
								<td class="handleTd">
									<a onclick="openDetailDialog('${personnelRetreat.id}')">查看</a>
								<shiro:hasPermission name="personnel:personnelRetreat:edit">

<%--                                    <c:if test="${personnelRetreat.createBy.id == fns:getUser().id}">--%>
                                        <a href="javascript:void(0)"
                                           onclick="openForm('${ctx}/personnel/personnelRetreat/form?id=${personnelRetreat.id}','${ctx}/personnel/personnelRetreat?idNumber=${personnelRetreat.idNumber}')">修改</a>
                                        <a href="${ctx}/personnel/personnelRetreat/delete?id=${personnelRetreat.id}&idNumber=${personnelRetreat.idNumber}"
                                           onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--                                    </c:if>--%>

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
