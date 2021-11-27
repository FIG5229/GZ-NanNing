<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>廉政学习教育活动管理</title>
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
			var reason = "${reason}";
			if(reason != null && reason != '' && typeof reason != undefined){
				$("#reason").val(${reason}).trigger("change");
			}
			$("#reason").change(function(){
				$("#nd").val("")
				$("#sd").val("")
				$("#ed").val("")
				$("#qt").val("")
				year();
				//console.log($("#reason").val())
			});
			function year() {
				if ($("#reason").val() == '1') {
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:block")
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '2') {
					$("#rd").attr("style","display:block")
					$("#yr").attr("style","display:none")
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '3'){
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:none")
					$("#oy").attr("style","display:block")
				}else {
					$("#rd").attr("style","display:none")
					$("#yr").attr("style","display:block")
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
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "廉政教育",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLzxxjyActivities"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairLzxxjyActivities/formDetail?id="+id;
			top.$.jBox.open(url, "廉政教育详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//详情弹窗
		function openPropellingDialog(id) {
			var url = "iframe:${ctx}/affair/affairLzxxjyActivities/propelling?id="+id;
			top.$.jBox.open(url, "数据推送",500,300,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//弹出签收窗体
		function signDialog(noticeId) {
			top.$.jBox.open("iframe:${ctx}/affair/affairLzxxjyActivitiesSign?noticeId=" + noticeId, "签收状态列表", 700, 400, {
				buttons: {"确定": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		};

		/*重置有bug，废弃*/
		function resetForm() {
			var form = document.getElementById("searchForm");
			form.reset();
			/*下拉框的值单独清空*/
			document.getElementById("typeSelect").options[0].selected = true;
			$("#typeSelect").change();
			/*$("#myTitle").val('');*/

		};

		//批量签收
		function signByIds() {
			var signIds = getIds();
			if (signIds.length > 0) {
				$.ajax({
					type: "post",
					url: "${ctx}/affair/affairLzxxjyActivitiesSign/sign",
					data: {ids: signIds},
					dataType: "json",
					success: function (data) {
						$.jBox.tip(data.message);
						resetCheckBox("checkAll", "myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要签收的内容');
			}
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairLzxxjyActivities/">廉政教育</a></li>
		<shiro:hasPermission name="affair:affairLzxxjyActivities:manage">
			<li><a href="${ctx}/affair/affairLzxxjyActivities/manageList">廉政教育审核</a></li>
		</shiro:hasPermission>
		<li><a href="${ctx}/affair/affairTeamDiscipline/">纪律作风教育整顿</a></li>
		<shiro:hasPermission name="affair:affairTeamDiscipline:manage">
			<li><a href="${ctx}/affair/affairTeamDiscipline/manageList">纪律作风教育整顿审核</a></li>
		</shiro:hasPermission>
		<li ><a href="${ctx}/affair/affairEmpiricalPractice/">信息简报/经验交流</a></li>
		<%--<shiro:hasPermission name="affair:affairLzxxjyActivities:edit"><li><a href="${ctx}/affair/affairLzxxjyActivities/form">廉政学习教育活动添加</a></li></shiro:hasPermission>--%>
	</ul>
	<%--@elvariable id="affairLzxxjyActivities" type="com.thinkgem.jeesite.modules.affair.entity.AffairLzxxjyActivities"--%>
	<form:form id="searchForm" modelAttribute="affairLzxxjyActivities" action="${ctx}/affair/affairLzxxjyActivities/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
					   value="<fmt:formatDate value="${affairLzxxjyActivities.startYear}" pattern="yyyy"/>"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li id="rd"><label>发布日期：</label>
				<input id="sd" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLzxxjyActivities.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input id="ed" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLzxxjyActivities.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li id="oy"><label>其他年份：</label>
				<input id="qt" name="otherYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairLzxxjyActivities.otherYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>标题名称：</label>
				<form:input path="eventName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>发布单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairLzxxjyActivities.unitId}" labelName="unit" labelValue="${affairLzxxjyActivities.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
			<li><label>教育类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_lzjy_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
<%--			<li class="btns"><input  class="btn btn-primary" type="button" value="推送" onclick="openForm('${ctx}/affair/affairLzxxjyActivities/propelling?id=${affairGhActivityRecord.id}')"/></li>--%>
			<%--<shiro:hasPermission name="affair:affairLzxxjyActivities:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairLzxxjyActivities/form')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairLzxxjyActivities:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLzxxjyActivities/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>--%>
			<shiro:hasPermission name="affair:affairLzxxjyActivitiesSign:edit">
				<li class="btns"><input id="sign" class="btn btn-primary" type="button" value="签收" onclick="signByIds()"/>
				</li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLzxxjyActivities'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>标题名称</th>
				<th>发布单位</th>
				<th>教育类型</th>
				<th>发布日期</th>
				<th>签收状态</th>
				<th>操作</th>
<%--				<shiro:hasPermission name="affair:affairLzxxjyActivities:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLzxxjyActivities" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLzxxjyActivities.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairLzxxjyActivities.eventName}
				</td>
				<td>
						${affairLzxxjyActivities.unit}
				</td>
				<td>
						${fns:getDictLabel(affairLzxxjyActivities.type, 'affair_lzjy_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${affairLzxxjyActivities.eventDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:choose>
						<c:when test="${affairLzxxjyActivities.signStatus == '1'}">
							已签收
						</c:when>
						<c:otherwise>
							未签收
						</c:otherwise>
					</c:choose>
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairLzxxjyActivities.id}')">查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>