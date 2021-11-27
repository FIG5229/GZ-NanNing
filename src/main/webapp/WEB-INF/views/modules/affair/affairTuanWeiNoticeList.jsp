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
					formValues: false
				});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		//弹出签收窗体
		function signDialog(noticeId){
			top.$.jBox.open("iframe:${ctx}/affair/affairFileNoticeSign?noticeId="+noticeId, "签收状态列表",700,400,{
				buttons:{"确定":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		//批量签收
		function signByIds() {
			var signIds = getIds();
			if (signIds.length > 0) {
				$.ajax({
					type:"post",
					url:"${ctx}/affair/affairFileNoticeSign/sign",
					data:{ids:signIds},
					dataType:"json",
					success:function(data){
						$.jBox.tip(data.message);
						resetCheckBox("checkAll","myCheckBox");
						location.reload();
					}
				})
			} else {
				$.jBox.tip('请先选择要签收的内容');
			}
		};
		//详情弹窗
		function openDetailDialog(id,signStatus) {
			var url = "iframe:${ctx}/affair/affairFileNotice/detail?id="+id+"&signStatus="+signStatus;
			top.$.jBox.open(url, "团委通知通报",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/affair/affairFileNotice?flag=3";
				}
			});
		};
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/affair/affairFileNotice?flag=3">查询</a></li>
	<shiro:hasPermission name="affair:affairFileNotice:manage3"><li><a href="${ctx}/affair/affairFileNotice/manageList?flag=3">管理</a></li></shiro:hasPermission>
</ul>
<%--@elvariable id="affairFileNotice" type="com.thinkgem.jeesite.modules.affair.entity.AffairFileNotice"--%>
<form:form id="searchForm" modelAttribute="affairFileNotice" action="${ctx}/affair/affairFileNotice/" method="post" class="breadcrumb form-search">
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
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairFileNoticeSign:edit">
			<li class="btns"><input id="sign" class="btn btn-primary" type="button" value="签收" onclick="signByIds()"/></li>
		</shiro:hasPermission>
		<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
		<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairFileNotice?flag=3'"/></li>
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
		<th>签收状态</th>
		<th>签收日期</th>
		<th>是否推送到主页</th>
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
				<a href="javascript:void(0)" onclick="openDetailDialog('${affairFileNotice.id}','${affairFileNotice.signStatus}')">${affairFileNotice.title}</a>
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
				<c:choose>
					<c:when test="${affairFileNotice.signStatus == '1'}">
						已签收
					</c:when>
					<c:otherwise>
						未签收
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<fmt:formatDate value="${affairFileNotice.signDate}" pattern="yyyy-MM-dd"/>
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
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>