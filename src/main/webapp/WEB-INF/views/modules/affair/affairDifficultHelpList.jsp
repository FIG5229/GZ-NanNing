<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>困难帮扶申报管理</title>
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
            top.$.jBox.open("iframe:${ctx}/affair/affairDifficultHelp/form", "困难帮扶申请",800,600,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairDifficultHelp"}
            });
        }

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDifficultHelp/formDetail?id="+id;
			top.$.jBox.open(url, "困难帮扶申报详情",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//编辑弹窗
		function openEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairDifficultHelp/form?id="+id;
			top.$.jBox.open(url, "困难帮扶申报修改",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDifficultHelp/list"}
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
			top.$.jBox.open("iframe:${ctx}/affair/affairDifficultHelp/examineView?id="+id,"工作申报审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairDifficultHelp/list/?repage";
				}
			});
		}


	</script>
</head>
<body>
	<ul class="nav nav-tabs">
        <li><a href="${ctx}/affair/affairDeclarationQiyi/?topType=1">创先争优申报</a></li>
		<li class="active"><a href="${ctx}/affair/affairDifficultHelp/">困难帮扶申报</a></li>
        <li><a href="${ctx}/affair/affairDeclarationQiyi/?topType=2">其他申报</a></li>
<%--		<shiro:hasPermission name="affair:affairDifficultHelp:edit"><li><a href="${ctx}/affair/affairDifficultHelp/form">困难帮扶申报添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairDifficultHelp" action="${ctx}/affair/affairDifficultHelp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>慰问对象类别：</label>
				<form:select path="wwType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('comfort_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>慰问金额：</label>
				<form:input path="money" htmlEscape="false" class="input-medium"/>
			</li>

		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairDifficultHelp:edit">

			<li class="btns"><input id="btnAdd" class="btn btn-primary" type="button" value="添加"
                                    onclick="openAddDialog()"/></li>
			<li class="btns"><input id="btnDel" class="btn btn-primary" type="button" value="删除"
                                    onclick="deleteByIds('${ctx}/affair/affairDifficultHelp/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
<%--			<li class="btns"><input id="btnExamine" class="btn btn-primary" type="button" value="审核"/></li>--%>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input id="btnReset" class="btn btn-primary" type="button" value="重置"
                                    onclick="window.location.href='${ctx}/affair/affairDifficultHelp/'"/></li>
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
				<th>姓名</th>
				<th>性别</th>
				<th>出生年月</th>
				<th>入党时间</th>
				<th>所在单位</th>
				<th>职务</th>
				<th>慰问对象类别</th>
				<th>慰问原因</th>
				<th>慰问金额</th>
				<th>慰问款源</th>
				<th>慰问时间</th>
				<th>联系电话</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairDifficultHelp:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDifficultHelp" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDifficultHelp.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairDifficultHelp.name}
				</td>
				<td>
					${affairDifficultHelp.sex}
				</td>
				<td>
					<fmt:formatDate value="${affairDifficultHelp.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairDifficultHelp.joinPartyTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairDifficultHelp.unit}
				</td>
				<td>
					${affairDifficultHelp.job}
				</td>
				<td>
						${fns:getDictLabel(affairDifficultHelp.wwType, 'comfort_category', '')}
				</td>
				<td>
					${affairDifficultHelp.reason}
				</td>
				<td>
					${affairDifficultHelp.money}
				</td>
				<td>
					${fns:getDictLabel(affairDifficultHelp.moneySource, 'source_condolence_payment', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairDifficultHelp.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairDifficultHelp.phoneNumber}
				</td>
				<td>
					${fns:getDictLabel(affairDifficultHelp.checkType, 'declare_status', '')}
				</td>
				<td class="handleTd">
    				<a href="javascript:" onclick="openDetailDialog('${affairDifficultHelp.id}')">查看</a>
				<shiro:hasPermission name="affair:affairDifficultHelp:edit">
					<c:if test="${affairDifficultHelp.createBy.id == fns:getUser().id&&('1'.equals(affairDifficultHelp.checkType))}">
    					<a href="javascript:" onclick="openEditDialog('${affairDifficultHelp.id}')">修改</a>
					</c:if>
						<a href="${ctx}/affair/affairDifficultHelp/delete?id=${affairDifficultHelp.id}" onclick="return confirmx('确认要删除该困难帮扶申报吗？', this.href)">删除</a>
				</shiro:hasPermission>
				<%--	<shiro:hasPermission name="affair:affairDifficultHelp:manage">
						<c:if test="${affairDifficultHelp.checkId == fns:getUser().id}">
							<a href="javascript:;" onclick="openExamineDialog('${affairDifficultHelp.id}')">审核</a>
						</c:if>
					</shiro:hasPermission>--%>

						<c:if test="${fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb'|| fns:getUser().id eq '04f9b5355e054b40b7dc7e2a202dc0c3' ||
								fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d'}">
							<a href="javascript:" onclick="openExamineDialog('${affairDifficultHelp.id}')">审核</a>
						</c:if>


				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<shiro:hasPermission name="affair:affairDifficultHelp:edit">
		<form:form id="searchForm2" modelAttribute="affairDifficultHelp" action="${ctx}/affair/affairDifficultHelp/submitByIds" method="post" class="breadcrumb form-search">
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