<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>谈话函询管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTalkManagement/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTalkManagement/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTalkManagement/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTalkManagement/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTalkManagement", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTalkManagement"}});
			});
			var reason = "${reason}";
			if(reason != null && reason != '' && typeof reason != undefined){
				$("#reason").val(${reason}).trigger("change");
			}
			$("#reason").change(function(){
				$("#nd").val("");
				$("#sd").val("");
				$("#ed").val("");
				$("#qt").val("");
				year();
			});
			function year() {
				if ($("#reason").val() == '1') {
					$("#rd").attr("style","display:none");
					$("#yr").attr("style","display:block");
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '2') {
					$("#rd").attr("style","display:block");
					$("#yr").attr("style","display:none");
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '3'){
					$("#rd").attr("style","display:none");
					$("#yr").attr("style","display:none");
					$("#oy").attr("style","display:block")
				}else {
					$("#rd").attr("style","display:none");
					$("#yr").attr("style","display:block");
					$("#oy").attr("style","display:none")
				}
			}
			year();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "谈话函询",950,650,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTalkManagement"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTalkManagement/formDetail?id="+id;
			top.$.jBox.open(url, "\n" +
					"谈话函询详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairTalkManagement/">谈话函询</a></li>
<%--		<li><a href="${ctx}/affair/affairTalkManagement/affairTalkManagementListSum">统计汇总</a></li>--%>
		<%--<shiro:hasPermission name="affair:affairTalkManagement:edit"><li><a href="${ctx}/affair/affairTalkManagement/form">谈话函询添加</a></li></shiro:hasPermission>--%>

	</ul>
	<form:form id="searchForm" modelAttribute="affairTalkManagement" action="${ctx}/affair/affairTalkManagement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="谈话函询管理表.xlsx"/>
		<ul class="ul-form">
			<li>
				<label>数据范围：</label>
				<select id="reason" style="width: 140px;" name="reason">
					<option value="2">全部</option>
					<option value="3">其他年份</option>
				</select>
			</li>
			<li id="yr"><label>年度：</label>
				<input id="nd" name="startYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTalkManagement.startYear}" pattern="yyyy"/>"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li id="rd"><label>谈话时间：</label>
				<input id="sd" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairTalkManagement.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input id="ed" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairTalkManagement.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li id="oy"><label>其他年份：</label>
				<input id="qt" name="otherYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairTalkManagement.otherYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairTalkManagement.unitId}" labelName="unit" labelValue="${affairTalkManagement.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li><label>主办部门：</label>
				<form:select path="zbUnit" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zb_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>类型：</label>
				<form:select path="letterCategory" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_tanhua')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTalkManagement:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTalkManagement/form')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairTalkManagement:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTalkManagement/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<%--<shiro:hasPermission name="affair:affairBorrow:edit">
                    <li class="btns"><input  class="btn btn-primary" type="button" value="审核"/></li>
                </shiro:hasPermission>--%>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTalkManagement'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>单位</th>
				<th>职务</th>
				<th>职级</th>
				<th>政治面貌</th>
				<th>主办部门</th>
				<th>类型</th>
				<th>谈话人</th>
				<th>谈话时间</th>
<%--				<th>审核状态</th>--%>
				<shiro:hasPermission name="affair:affairTalkManagement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTalkManagement" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTalkManagement.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
				${affairTalkManagement.name}
				</td>
				<td>
						${affairTalkManagement.unit}
				</td>
				<td>
						${affairTalkManagement.job}
				</td>
				<td>
						${affairTalkManagement.jobLevel}
				</td>
				<td>
					${fns:getDictLabel(affairTalkManagement.mianmao, 'political_status', '')}
				</td>
				<td>
						${fns:getDictLabel(affairTalkManagement.zbUnit, 'affair_zb_unit', '')}
				</td>
				<td>
					${fns:getDictLabel(affairTalkManagement.letterCategory, 'affair_tanhua', '')}
				</td>
				<td>
						${affairTalkManagement.talker}
				</td>
				<td>
					<fmt:formatDate value="${affairTalkManagement.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTalkManagement.id}')">查看</a>
					<c:choose>
						<c:when test="${ 'd5ec905f77714c6f8a216e5cbd14ff67' == fns:getUser().id}">
							<shiro:hasPermission name="affair:affairTalkManagement:edit">
								<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTalkManagement/form?id=${affairTalkManagement.id}')">修改</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="affair:affairTalkManagement:delete">
								<a href="${ctx}/affair/affairTalkManagement/delete?id=${affairTalkManagement.id}" onclick="return confirmx('确认要删除该谈话函询吗？', this.href)">删除</a>
							</shiro:hasPermission>
						</c:when>
						<c:otherwise>
							<c:if test="${'276d8cdc184748c8a5ff014221fb135a' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairTalkManagement:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTalkManagement/form?id=${affairTalkManagement.id}')">修改</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="affair:affairTalkManagement:delete">
									<a href="${ctx}/affair/affairTalkManagement/delete?id=${affairTalkManagement.id}" onclick="return confirmx('确认要删除该谈话函询吗？', this.href)">删除</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'35737e5582804ef08502c7283db5c5cf' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairTalkManagement:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTalkManagement/form?id=${affairTalkManagement.id}')">修改</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'5a0766c9a3df41a88f5759a29886f1ae' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairTalkManagement:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTalkManagement/form?id=${affairTalkManagement.id}')">修改</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'b25351b897104a698accd3583ceba19f' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairTalkManagement:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTalkManagement/form?id=${affairTalkManagement.id}')">修改</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${affairTousujubaoguanli.createBy.id == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairTalkManagement:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTalkManagement/form?id=${affairTalkManagement.id}')">修改</a>
								</shiro:hasPermission>
							</c:if>
						</c:otherwise>
					</c:choose>
				<%--<shiro:hasPermission name="affair:affairTalkManagement:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTalkManagement/form?id=${affairTalkManagement.id}')">修改</a>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairTalkManagement:delete">
						<a href="${ctx}/affair/affairTalkManagement/delete?id=${affairTalkManagement.id}" onclick="return confirmx('确认要删除该谈话函询吗？', this.href)">删除</a>
					</shiro:hasPermission>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>