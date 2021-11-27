<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工会活动报名管理</title>
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
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityEnroll/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityEnroll/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityEnroll/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGhActivityEnroll/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairGhActivityEnroll", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairGhActivityEnroll"}});
			});

			$("[data-toggle='popover']").popover();
		});
		function submitByIds() {
			if(null == $("#oneCheckId").val() || "" ==  $("#oneCheckId").val()){
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
        /*//提交人员选择弹框
        function personnelChoose(url) {
            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                ids.push($(this).val());
            });
            var idsStr = ids.join(",");
            top.$.jBox.open("iframe:"+url+"?ids="+idsStr,"单位选择",800,520,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairGhActivityEnroll/list"}
			});
		};*/
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			<%--top.$.jBox.open("iframe:"+url, "活动报名",1200,550,{--%>
			<%--	buttons:{"关闭":true},--%>
			<%--	loaded:function(){--%>
			<%--		$(".jbox-content", top.document).css("overflow-y","hidden");--%>
			<%--	},closed:function (){self.location.href="${ctx}/affair/affairGhActivityEnroll"}--%>
			<%--});--%>
			window.open(url,'','',false);
		}
		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairGhActivityEnroll/shenHeDialog?id="+id,"活动报名审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairGhActivityEnroll/list";
				}
			});
        }
        //详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairGhActivityEnroll/formDetail?id=" + id;
			top.$.jBox.open(url, "活动报名", 800, 600, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
        }
        $('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairGhActivityEnroll/">活动报名</a></li>
<%--		<li ><a href="${ctx}/affair/affairGhActivityManage/">活动管理</a></li>--%>
		<shiro:hasPermission name="affair:affairGhActivityRecord:view"><li ><a href="${ctx}/affair/affairGhActivityRecord/">活动记录</a></li></shiro:hasPermission>
		<%--<shiro:hasPermission name="affair:affairGhActivityEnroll:edit"><li><a href="${ctx}/affair/affairGhActivityEnroll/form">工会活动报名添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairGhActivityEnroll" action="${ctx}/affair/affairGhActivityEnroll/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="工会活动报名表.xlsx"/>
		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairGhActivityEnroll.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairGhActivityEnroll.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairGhActivityEnroll.unitId}" labelName="unit" labelValue="${affairGhActivityEnroll.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>职务：</label>
				<form:input path="job" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>出生年月：</label>
				<input name="startBirthdayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairGhActivityEnroll.startBirthdayDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endBirthdayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairGhActivityEnroll.endBirthdayDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label class="width120">活动项目：</label>
				<form:input path="project" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairGhActivityEnroll:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairGhActivityEnroll/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairGhActivityEnroll/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairGhActivityEnroll'"/></li>
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
				<th>单位</th>
				<th>民警姓名</th>
				<th>职务</th>
				<th>性别</th>
				<th>出生年月</th>
				<th>活动项目</th>
				<th>审核状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairGhActivityEnroll" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairGhActivityEnroll.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairGhActivityEnroll.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairGhActivityEnroll.unit}
				</td>
				<td>
				      ${affairGhActivityEnroll.name}
				</td>
				<td>
						${affairGhActivityEnroll.job}
				</td>
				<td>
						${fns:getDictLabel(affairGhActivityEnroll.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairGhActivityEnroll.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairGhActivityEnroll.project}
				</td>
				<td>
					<c:choose>
						<c:when test="${affairGhActivityEnroll.checkType == '0'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairGhActivityEnroll.opinion}"  style="cursor: pointer;color: red">审核未通过</a>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairGhActivityEnroll.checkType, 'check_type', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairGhActivityEnroll.id}')">查看 </a>
					<shiro:hasPermission name="affair:affairGhActivityEnroll:edit">
					<c:if test="${affairGhActivityEnroll.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairGhActivityEnroll/form?id=${affairGhActivityEnroll.id}')">修改</a>
					</c:if>
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairGhActivityEnroll/addPeople?id=${affairGhActivityEnroll.id}')">添加人员</a>
						<shiro:hasPermission name="affair:affairGhActivityEnroll:manage">
						<a href="${ctx}/affair/affairGhActivityEnroll/delete?id=${affairGhActivityEnroll.id}" onclick="return confirmx('确认要删除该工会活动报名吗？', this.href)">删除</a>
						<a href="javascript:void(0)" onclick="openDialog('${affairGhActivityEnroll.id}')">审核</a>
					</shiro:hasPermission>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="affairGhActivityEnroll" action="${ctx}/affair/affairGhActivityEnroll/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="oneCheckId" path="oneCheckId"  class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('user_gonghui')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
</body>
</html>