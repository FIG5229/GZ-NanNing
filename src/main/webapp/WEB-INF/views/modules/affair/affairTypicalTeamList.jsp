<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>典型集体管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});

			$("#btnExport").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTypicalTeam/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTypicalTeam/list?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTypicalTeam/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTypicalTeam/list");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTypicalTeam", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTypicalTeam/list"}});
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openAddForm() {
			top.$.jBox.open("iframe:${ctx}/affair/affairTypicalTeam/form", "典型集体",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}


		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTypicalTeam/form?id="+id,"典型集体编辑",800,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				}
			});
		}
		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTypicalTeam/formDetail?id="+id,"典型集体详情",800,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairTypicalTeam"

				}
			});
		}
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairTypicalPerson/">典型人物</a></li>
		<li class="active"><a href="${ctx}/affair/affairTypicalTeam/">典型集体</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTypicalTeam" action="${ctx}/affair/affairTypicalTeam/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="4.6典型集体.xlsx"/>
		<ul class="ul-form">
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>单位：</label>
							<sys:treeselect id="unit" name="unitId" value="${affairTypicalTeam.unitId}" labelName="unit" labelValue="${affairTypicalTeam.unit}"
											title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairTypicalTeam.unitId}" labelName="unit" labelValue="${affairTypicalTeam.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
						</li>
				</c:otherwise>
			</c:choose>
						<%--<form:input path="unit" htmlEscape="false" class="input-medium"/>--%>
			<li><label>培树时间：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTypicalTeam.beginTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTypicalTeam.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>培树级别：</label>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>培树目标：</label>
				<form:input path="target" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>培树部门：</label>
<%--				<form:input path="psDepartment" htmlEscape="false" class="input-medium"/>--%>
				<sys:treeselect id="psDepartment" name="psDepartmentId" value="${affairTypicalTeam.psDepartmentId}" labelName="psDepartment" labelValue="${affairTypicalTeam.psDepartment}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li><label>联系人：</label>
				<form:input path="contactPerson" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTypicalTeam:edit">
			<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加"
									onclick="openAddForm()"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="btnDel" class="btn btn-primary" type="button" value="删除"
									onclick="deleteByIds('${ctx}/affair/affairTypicalTeam/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairTypicalTeam'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>培树时间</th>
				<th>培树级别</th>
				<th>培树目标</th>
				<th>培树部门</th>
				<th>联系人</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTypicalTeam" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTypicalTeam.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTypicalTeam.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairTypicalTeam.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairTypicalTeam.level, 'affair_approval_unitLevel', '')}
				</td>
				<td>
					${affairTypicalTeam.target}
				</td>
				<td>
					${affairTypicalTeam.psDepartment}
				</td>
				<td>
					${affairTypicalTeam.contactPerson}
				</td>
				<td class="handleTd">
    				<a href="javascript:" onclick="openDetailDialog('${affairTypicalTeam.id}')">查看</a>
					<c:if test="${affairTypicalTeam.changeType == '1'}">
						<a href="javascript:" onclick="openEditDialog('${affairTypicalTeam.id}')">修改</a>
					</c:if>

					<shiro:hasPermission name="affair:affairTypicalTeam:edit">
						<c:if test="${fns:getUser().id eq affairTypicalTeam.createBy.id}">
							<a href="${ctx}/affair/affairTypicalTeam/delete?id=${affairTypicalTeam.id}" onclick="return confirmx('确认要删除该典型集体吗？', this.href)">删除</a>
						</c:if>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>