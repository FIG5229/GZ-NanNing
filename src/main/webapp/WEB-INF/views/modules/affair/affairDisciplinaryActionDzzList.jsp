<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党组织纪律处分管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryActionDzz/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryActionDzz/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryActionDzz/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryActionDzz/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairDisciplinaryActionDzz", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairDisciplinaryActionDzz"}});
			});

			//打印
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
				//console.log($("#reason").val())
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
			top.$.jBox.open("iframe:"+url, "党组织纪律处分",900,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDisciplinaryActionDzz"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDisciplinaryActionDzz/formDetail?id="+id;
			top.$.jBox.open(url, "\n" +
					"党组织纪律处分详情",900,600,{
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
		<li><a href="${ctx}/affair/affairDisciplinaryAction/">纪律处分</a></li>
		<li class="active"><a href="${ctx}/affair/affairDisciplinaryActionDzz/">党组织处理</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairDisciplinaryActionDzz" action="${ctx}/affair/affairDisciplinaryActionDzz/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="党组织纪律处分表.xlsx"/>
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
					   value="<fmt:formatDate value="${affairDisciplinaryActionDzz.startYear}" pattern="yyyy"/>"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li id="rd"><label>时间：</label>
				<input id="sd" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairDisciplinaryActionDzz.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input id="ed" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairDisciplinaryActionDzz.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li id="oy"><label>其他年份：</label>
				<input id="qt" name="otherYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairDisciplinaryActionDzz.otherYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>党组织：</label>
				<sys:treeselect id="org" name="orgId" value="${affairDisciplinaryActionDzz.orgId}" labelName="org" labelValue="${affairDisciplinaryActionDzz.org}"
					title="党组织名称" url="/affair/affairGeneralSituation/jLChFtreeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li><label>问题性质：</label>
				<form:select path="nature" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_wenti')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>方式：</label>
				<form:select path="disciplinaryType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_dj_sub')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>主办单位：</label>
				<form:select path="unit" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_cf_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairDisciplinaryActionDzz/form')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairDisciplinaryActionDzz/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairDisciplinaryActionDzz'"/></li>
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
				<th>党组织</th>
				<th>问题性质</th>
				<th>方式</th>
				<th>主办单位</th>
				<th>文号</th>
				<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDisciplinaryActionDzz" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDisciplinaryActionDzz.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairDisciplinaryActionDzz.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairDisciplinaryActionDzz.org}
				</td>
				<td>
					${fns:getDictLabel(affairDisciplinaryActionDzz.nature, 'affair_wenti', '')}
				</td>
				<td>
					${fns:getDictLabel(affairDisciplinaryActionDzz.disciplinaryType, 'affair_dj_sub', '')}
				</td>
				<td>
					${fns:getDictLabel(affairDisciplinaryActionDzz.unit, 'affair_cf_unit', '')}
				</td>
				<td>
					${affairDisciplinaryActionDzz.fileNum}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairDisciplinaryActionDzz.id}')">查看</a>
					<c:choose>
						<c:when test="${ 'd5ec905f77714c6f8a216e5cbd14ff67' == fns:getUser().id}">
							<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:edit">
								<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryActionDzz/form?id=${affairDisciplinaryActionDzz.id}')">修改</a>
								<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionformDzz?id=${affairDisciplinaryActionDzz.id}')">推送到奖惩信息库</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:delete">
								<a href="${ctx}/affair/affairDisciplinaryActionDzz/delete?id=${affairDisciplinaryActionDzz.id}" onclick="return confirmx('确认要删除该纪律处分吗？', this.href)">删除</a>
							</shiro:hasPermission>
						</c:when>
						<c:otherwise>
							<c:if test="${'276d8cdc184748c8a5ff014221fb135a' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryActionDzz/form?id=${affairDisciplinaryActionDzz.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionformDzz?id=${affairDisciplinaryActionDzz.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:delete">
									<a href="${ctx}/affair/affairDisciplinaryActionDzz/delete?id=${affairDisciplinaryActionDzz.id}" onclick="return confirmx('确认要删除该纪律处分吗？', this.href)">删除</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'35737e5582804ef08502c7283db5c5cf' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryActionDzz/form?id=${affairDisciplinaryActionDzz.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionformDzz?id=${affairDisciplinaryActionDzz.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'5a0766c9a3df41a88f5759a29886f1ae' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryActionDzz/form?id=${affairDisciplinaryActionDzz.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionformDzz?id=${affairDisciplinaryActionDzz.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'b25351b897104a698accd3583ceba19f' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryActionDzz/form?id=${affairDisciplinaryActionDzz.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionformDzz?id=${affairDisciplinaryActionDzz.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${affairTousujubaoguanli.createBy.id == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryActionDzz/form?id=${affairDisciplinaryActionDzz.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionformDzz?id=${affairDisciplinaryActionDzz.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
							</c:if>
						</c:otherwise>
					</c:choose>
					<%--<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryActionDzz/form?id=${affairDisciplinaryActionDzz.id}')">修改</a>
						<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionformDzz?id=${affairDisciplinaryActionDzz.id}')">推送到奖惩信息库</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairDisciplinaryActionDzz:delete">
						<a href="${ctx}/affair/affairDisciplinaryActionDzz/delete?id=${affairDisciplinaryActionDzz.id}" onclick="return confirmx('确认要删除该纪律处分吗？', this.href)">删除</a>
					</shiro:hasPermission>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>