<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团委通知通报管理</title>
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
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
        //弹出新的窗体
		function signDialog(noticeId){
			top.$.jBox.open("iframe:${ctx}/affair/affairFileNoticeSign?noticeId="+noticeId, "签收状态列表",700,400,{
				buttons:{"确定":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//批量发布
		function publishByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairFileNotice/publishByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要发布的内容');
			}
		};
		//批量取消发布
		function cancelByIds() {
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairFileNotice/cancelByIds",
					data:{ids:ids},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要取消发布的内容');
			}
		};
		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/affair/affairFileNotice/form?id="+id+"&flag=3";
			if (id == null || id == undefined){
				url = "iframe:${ctx}/affair/affairFileNotice/form?flag=3";
			}
			top.$.jBox.open(url, "团委通知通报",1000,800,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairFileNotice/manageList?flag=3";
				}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairFileNotice/formDetail?id="+id+"&flag=3";
			top.$.jBox.open(url, "团委通知通报",1000,700,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairFileNotice?flag=3">查询</a></li>
		<shiro:hasPermission name="affair:affairFileNotice:manage3"><li class="active"><a href="${ctx}/affair/affairFileNotice/manageList?flag=3">管理</a></li></shiro:hasPermission>
	</ul>
	<%--@elvariable id="affairFileNotice" type="com.thinkgem.jeesite.modules.affair.entity.AffairFileNotice"--%>
	<form:form id="searchForm" modelAttribute="affairFileNotice" action="${ctx}/affair/affairFileNotice/manageList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="flag" name="flag" type="hidden" value="3"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
            <li><label>发文类型：</label>
				<form:select id="typeSelect" path="type" class="input-medium">
					<form:option value="" label=""/>
                    <form:options items="${fns:getDictList('affair_tw_file_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
				</form:select>
            </li>
			<li><label>发布日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairFileNotice.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				 --
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairFileNotice.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>发布状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_publish_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairFileNotice:manage3">
				<li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()" value="添加"/></li>
				<li class="btns"><input id="sign" class="btn btn-primary" type="button" value="发布" onclick="publishByIds()"/></li>
				<li class="btns"><input id="cancel" class="btn btn-primary" type="button" value="取消发布" onclick="cancelByIds()"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairFileNotice/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairFileNotice/manageList?flag=3'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>
                    <input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
				</th>
				<th>序号</th>
				<th>标题</th>
                <th>发文类型</th>
				<th>发布人</th>
				<th>发布日期</th>
				<th>发布状态</th>
				<th>签收状态</th>
				<th>是否推送到主页</th>
				<shiro:hasPermission name="affair:affairFileNotice:manage"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairFileNotice" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairFileNotice.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<a onclick="openDetailDialog('${affairFileNotice.id}')">${affairFileNotice.title}</a>
				</td>
				<td>
                        ${fns:getDictLabel(affairFileNotice.type, 'affair_tw_file_type', '')}
				</td>
				<td>
					${affairFileNotice.publisher}
				</td>
				<td>
					<%--<fmt:formatDate value="${affairFileNotice.publishDate}" pattern="yyyy-MM-dd"/>--%>
					<fmt:formatDate value="${affairFileNotice.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairFileNotice.status, 'affair_publish_status', '')}
				</td>
				<td>
					<c:if test="${not empty affairFileNotice.sumNum && affairFileNotice.sumNum != 0}">
						<a onclick="signDialog('${affairFileNotice.id}')">${affairFileNotice.signNum}/${affairFileNotice.sumNum}</a>
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${affairFileNotice.isPush == 1}">
							是
						</c:when>
						<c:otherwise>
							否
						</c:otherwise>
					</c:choose>
				</td>
				<shiro:hasPermission name="affair:affairFileNotice:manage"><td class="handleTd">
					<c:if test="${affairFileNotice.createBy.id == fns:getUser().id}">
						<%-- 一旦发布不可再修改--%>
						<c:if test="${affairFileNotice.status != '2'}">
							<a onclick="openAddEditDialog('${affairFileNotice.id}')">修改</a>
						</c:if>
						<a href="${ctx}/affair/affairFileNotice/delete?id=${affairFileNotice.id}&flag=3"
						   onclick="return confirmx('确认要删除该通知通报吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>