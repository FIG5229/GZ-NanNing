<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作申报管理</title>
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

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openAddDialog() {
			top.$.jBox.open("iframe:${ctx}/affair/affairDeclarationQiyi/form?type=${type}&typeTop=${typeTop}", "工作申报",800,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDeclarationQiyi/?type=${type}&typeTop=${typeTop}"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDeclarationQiyi/formDetail?id="+id;
			top.$.jBox.open(url, "工作申报详情",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//编辑弹窗
		function openEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairDeclarationQiyi/form?id="+id;
			top.$.jBox.open(url, "工作申报修改",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDeclarationQiyi/?type=${type}&typeTop=${typeTop}"}
			});
		}
		function submitByIds() {
			if(null == $("#checkId").val() || "" ==  $("#checkId").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val());
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		}
		function openExamineDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairDeclarationQiyi/examineView?id="+id,"工作申报审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairDeclarationQiyi/list/?type=${type}&typeTop=${typeTop}";
				}
			});
		}


	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:choose>
			<c:when test="${topType == 1}">
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=1&topType=1">创先争优申报</a></li>
				<li><a href="${ctx}/affair/affairDifficultHelp/">困难帮扶申报</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=1&topType=2">其他申报</a></li>
			</c:when>
			<c:when test="${topType == 2}">
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=1&topType=1">创先争优申报</a></li>
				<li><a href="${ctx}/affair/affairDifficultHelp/">困难帮扶申报</a></li>
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=1&topType=2">其他申报</a></li>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	</ul>
	<%--优化9.8 li 修改，不显示ul的黑线--%>
	<c:if test="${'2' != topType}">
	<ul class="nav nav-tabs">
		<c:choose>
			<c:when test="${type == '1' && !'2'.equals(topType)}">
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=1&topType=${topType}">先进党组织</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=2&topType=${topType}">优秀党员</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=3&topType=${topType}">优秀党务工作者</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=4&topType=${topType}">党内优质品牌</a></li>
			</c:when>
			<c:when test="${type == '2'&& !'2'.equals(topType)}">
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=1&topType=${topType}">先进党组织</a></li>
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=2&topType=${topType}">优秀党员</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=3&topType=${topType}">优秀党务工作者</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=4&topType=${topType}">党内优质品牌</a></li>
			</c:when>
			<c:when test="${type == '3'&& !'2'.equals(topType)}">
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=1&topType=${topType}">先进党组织</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=2&topType=${topType}">优秀党员</a></li>
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=3&topType=${topType}">优秀党务工作者</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=4&topType=${topType}">党内优质品牌</a></li>
			</c:when>
			<c:when test="${type == '4'&& !'2'.equals(topType)}">
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=1&topType=${topType}">先进党组织</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=2&topType=${topType}">优秀党员</a></li>
				<li><a href="${ctx}/affair/affairDeclarationQiyi/?type=3&topType=${topType}">优秀党务工作者</a></li>
				<li class="active"><a href="${ctx}/affair/affairDeclarationQiyi/?type=4&topType=${topType}">党内优质品牌</a></li>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</ul>
	</c:if>

	<form:form id="searchForm" modelAttribute="affairDeclarationQiyi" action="${ctx}/affair/affairDeclarationQiyi/?type=${type}&topType=${topType}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<c:if test="${type != 1}">
				<li><label>
					<c:if test="${type==2 || type ==3}">姓名</c:if>
					<c:if test="${type==4}">品牌名</c:if>：
					</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
				</li>
			</c:if>
			<li><label>
				<c:if test="${type == 1}">上报单位</c:if>
				<c:if test="${type == 2 || type == 3 || type == 4 }">所在单位</c:if>：
			</label>
				<sys:treeselect id="unit" name="unitId" value="${affairDeclarationQiyi.unitId}" labelName="unit" labelValue="${affairDeclarationQiyi.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<c:if test="${type ==2 || type == 3}">
				<li><label>职务：</label>
					<form:input path="job" htmlEscape="false" class="input-medium"/>
				</li>
			</c:if>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairDeclarationQiyi:edit">
				<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加"
										onclick="openAddDialog()"/></li>
				<li class="btns"><input id="btnDel" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairDeclarationQiyi/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
				<%--			<li class="btns"><input id="btnExamine" class="btn btn-primary" type="button" value="审核"/></li>--%>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置"
									onclick="window.location.href='${ctx}/affair/affairDeclarationQiyi/?type=${type}&topType=${topType}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh">
					<input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<c:if test="${type != 1}">
					<th>
						<c:if test="${type==2 || type ==3}">姓名</c:if>
						<c:if test="${type==4}">品牌名</c:if>
					</th>
				</c:if>
				<th>
					<c:if test="${type == 1}">上报单位</c:if>
					<c:if test="${type == 2 || type == 3 || type == 4 }">所在单位</c:if>
				</th>
				<c:if test="${type ==2 || type == 3}">
					<th>职务</th>
				</c:if>
				<th>主要事迹</th>
				<th>组织意见</th>
				<th>备注</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairDeclarationQiyi:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDeclarationQiyi" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDeclarationQiyi.id}"/>
				</td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${type != 1}">
					<td>
						${affairDeclarationQiyi.name}
					</td>
				</c:if>
				<td>
					${affairDeclarationQiyi.unit}
				</td>
				<c:if test="${type ==2 || type == 3}">
					<td>
						${affairDeclarationQiyi.job}
					</td>
				</c:if>
				<td>
					${affairDeclarationQiyi.mainStory}
				</td>
				<td>
					${affairDeclarationQiyi.orgOpinion}
				</td>
				<td>
					${affairDeclarationQiyi.remark}
				</td>
				<td>
						${fns:getDictLabel(affairDeclarationQiyi.checkType, 'declare_status', '')}
				</td>
					<td>
						<shiro:hasPermission name="affair:affairDeclarationQiyi:edit">
							<a href="javascript:" onclick="openDetailDialog('${affairDeclarationQiyi.id}')">查看</a>
							<%--只有创建者并且未提交或者审核不通过才能修改--%>
							<c:if test="${affairDeclarationQiyi.createBy.id == fns:getUser().id && ('1'.equals(affairDeclarationQiyi.checkType) ||
							'4'.equals(affairDeclarationQiyi.checkType))}">
								<a href="javascript:" onclick="openEditDialog('${affairDeclarationQiyi.id}')">修改</a>
							</c:if>
							<a href="${ctx}/affair/affairDeclarationQiyi/delete?id=${affairDeclarationQiyi.id}" onclick="return confirmx('确认要删除该工作申报吗？', this.href)">删除</a>
						</shiro:hasPermission>
						<%--<shiro:hasPermission name="affair:affairDeclarationQiyi:manage">
							&lt;%&ndash;审核人为用户Id时可进行审核&ndash;%&gt;
							<c:if test="${affairDeclarationQiyi.checkId == fns:getUser().id}">
								<a href="javascript:" onclick="openExamineDialog('${affairDeclarationQiyi.id}')">审核</a>
							</c:if>
						</shiro:hasPermission>--%>

							<c:if test="${fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'|| fns:getUser().id eq '04f9b5355e054b40b7dc7e2a202dc0c3' ||
						 fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d'}">
								<c:if test="${affairDeclarationQiyi.checkType != '1'}">
									<a href="javascript:" onclick="openExamineDialog('${affairDeclarationQiyi.id}')">审核</a>
								</c:if>
							</c:if>

				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<shiro:hasPermission name="affair:affairDeclarationQiyi:edit">
		<form:form id="searchForm2" modelAttribute="affairDeclarationQiyi" action="${ctx}/affair/affairDeclarationQiyi/submitByIds?type=${type}&topType=${topType}" method="post" class="breadcrumb form-search">
			<ul class="ul-form" style="text-align: right">
				<font color="red">请选择审核单位：</font>
				<input type="hidden" name="ids" id="idsValue"/>
				<form:select id="checkId" path="checkId"  class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('job_declaration')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
			</ul>
		</form:form>
	</shiro:hasPermission>
</body>
</html>