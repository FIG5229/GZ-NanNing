<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织建设管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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
			$("[data-toggle='popover']").popover();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairOrganizationBulid/shenHeDialog?id="+id, "审核",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairOrganizationBulid/list?&treeId=${treeId}";
					}
				});
			}else {
				$.jBox.tip('请先选择要审核的内容且只能单条审核');
			}
		}
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "组织建设管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairOrganizationBulid/list?&treeId=${treeId}"}
			});
		}
		$('#notPass').popover();
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairOrganizationBulid/formDetail?id="+id;
			top.$.jBox.open(url, "组织建设详情",1000,500,{
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
		<li class="active"><a href="${ctx}/affair/affairOrganizationBulid/list?treeId=${treeId}">组织建设列表</a></li>
		<%--<li ><a href="${ctx}/affair/affairGonghuiPersonnel/list">工会名册列表</a></li>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairOrganizationBulid" action="${ctx}/affair/affairOrganizationBulid/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairOrganizationBulid.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairOrganizationBulid.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>组织名称：</label>
				<sys:treeselect id="name" name="id" value="${affairOrganizationBulid.id}" labelName="name" labelValue="${affairOrganizationBulid.name}"
						title="所辖单位" url="/sys/office/treeData?type=2" allowClear="true" notAllowSelectParent="true" disabled="disabled" />
			</li>

			<li><label class="width140">是否建立会员评价制度：</label>
				<form:select path="isAssessSys" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label class="width120">支所队工会主席：</label>
				<form:input path="zghzNum" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairOrganizationBulid:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairOrganizationBulid/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairOrganizationBulid/deleteByIds','checkAll','myCheckBox')"/></li>
	<%--			<li class="btns"><input id="" class="btn btn-primary" type="button" value="审核" onclick="openDialog('myCheckBox')"/></li>
	--%>	</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairOrganizationBulid/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>时间</th>
				<th>工会名称</th>
				<th>单位</th>
			<%--	<th>是否建立会员评价制度</th>
				<th>会员评价测评结果</th>
				<th>所队支工会数</th>--%>
				<th>支所队工会人数</th>
			<%--	<th>满意数</th>--%>
				<%--<th>审核状态</th>--%>
				<shiro:hasPermission name="affair:affairOrganizationBulid:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairOrganizationBulid" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairOrganizationBulid.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairOrganizationBulid.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairOrganizationBulid.name}
				</td>
				<td>
					${affairOrganizationBulid.unit}
				</td>
			<%--	<td>
						${fns:getDictLabel(affairOrganizationBulid.isAssessSys, 'yes_no', '')}
				</td>
				<td>
					${affairOrganizationBulid.result}
				</td>
				<td>
					${affairOrganizationBulid.zghNum}
				</td>--%>
				<td>
					${affairOrganizationBulid.zghrNum}
				</td>
				<%--<td>
					${affairOrganizationBulid.satisfyNum}
				</td>--%>
				<%--<td>
					<c:choose>
						<c:when test="${affairOrganizationBulid.status == '2'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairOrganizationBulid.opinion}"  style="cursor: pointer;color: red">审核未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairOrganizationBulid.status, 'affair_query_shenhe', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairOrganizationBulid/form?id=${affairOrganizationBulid.id}')">修改</a>
		--%>	<td class="handleTd">
				<a href="javascript:void(0)" onclick="openDetailDialog('${affairOrganizationBulid.id}')">查看</a>
				<shiro:hasPermission name="affair:affairOrganizationBulid:edit">
				<%--<c:if test="${affairOrganizationBulid.createBy.id == fns:getUser().id}">--%>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairOrganizationBulid/form?id=${affairOrganizationBulid.id}')">修改</a>
					<a href="${ctx}/affair/affairOrganizationBulid/delete?id=${affairOrganizationBulid.id}&treeId=${treeId}" onclick="return confirmx('确认要删除该组织建设吗？', this.href)">删除</a>
				<%--</c:if>--%>

				</shiro:hasPermission>
			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>