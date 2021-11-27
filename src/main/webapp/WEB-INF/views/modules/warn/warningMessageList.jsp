<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预警信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<style>
		.redText{
			color: red;
		}
		.yellowText{
			color: #ffb118;
		}
		.blueText{
			color: #2aabd2;
		}
		.demo {
			display: inline-block;
			*display: inline;
			*zoom: 1;
			width: 200px;
			height: 20px;
			line-height: 20px;
			font-size: 12px;
			overflow: hidden;
			-ms-text-overflow: ellipsis;
			text-overflow: ellipsis;
			white-space: nowrap;}
		.demo:hover {
			height: auto;
			min-height: 20px;
			white-space: normal;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function handelById(warnId,mess,repeatCycle) {
			var myRepeatCycleStr = '';
			if(repeatCycle == '0'){
				myRepeatCycleStr = '今天'
			}else if(repeatCycle == '1'){
				myRepeatCycleStr = '本周'
			}else if(repeatCycle == '2'){
				myRepeatCycleStr = '本月'
			}else if(repeatCycle == '3'){
				myRepeatCycleStr = '今年'
			}else{
				myRepeatCycleStr = '永不'
			}
			top.$.jBox.confirm(mess+'确认后'+myRepeatCycleStr+'将不再提醒！','系统提示',function(v,h,f){
				if(v=='ok'){
					sendAjax("${ctx}/warn/warning/handelByWarnId",warnId)
				}
			});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		}
		function noRemind(warnId,mess){
			top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
				if(v=='ok'){
					sendAjax("${ctx}/warn/warning/noRemindById",warnId)
				}
			});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		}
		//已完成/收到   不再提醒 ajax请求
		function sendAjax(url,warnId) {
			var idStr = $("#idStr").val();
			$.ajax({
				url:url,
				type:"post",
				data:{warnId:warnId,idStr:idStr},
				dataType:"json",
				success:function(data){
					if(data.success == true){
						$.jBox.tip(data.message);
						if(data.result!=null && data.result!='undefined'){
							$("#idStr").val(data.result);
							location = "${ctx}/warn/warning/handel?idStr="+data.result;
						}

					}else{
						$.jBox.tip(data.message);
					}
				},
				error:function(d){
					$.jBox.tip('发生错误，提交失败');
				}
			});
		}

		//生成警衔晋升名单
		function createPersonList() {
			top.$.jBox.open("iframe:${ctx}/personnel/personnelPoliceRank/warnCreatePersonList", "生成名单",900,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="warning" action="${ctx}/warn/warning/handel?idStr=${idStr}" method="post" class="breadcrumb form-search">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

</form:form>
	<input id="idStr" value="${idStr}" hidden>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>预警名称</th>
				<th>重复周期</th>
				<th>开始时间</th>
				<%--<th>气泡内容</th>
				<th>气泡紧急程度</th>--%>
				<th>弹窗内容</th>
				<th>弹窗紧急程度</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="warning" varStatus="status">
			<c:choose>
				<c:when test="${warning.alertDegree eq '1'}">
					<tr class = "redText">
				</c:when>
				<c:when test="${warning.alertDegree eq '2'}">
					<tr class = "yellowText">
				</c:when>
				<c:when test="${warning.alertDegree eq '3'}">
					<tr class = "blueText">
				</c:when>
				<c:otherwise>
					<tr>
				</c:otherwise>
			</c:choose>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${warning.name}
				</td>
				<td>
					${fns:getDictLabel(warning.repeatCycle, 'warn_repeat_cycle', '')}
				</td>
				<td>
					<c:if test="${warning.repeatCycle == '0'}">
						${warning.hour}:${warning.minute}
					</c:if>
					<c:if test="${warning.repeatCycle == '1'}">
						${fns:getDictLabel(warning.week,'warn_week','')}&nbsp;&nbsp;${warning.hour}:${warning.minute}
					</c:if>
					<c:if test="${warning.repeatCycle == '2'}">
						${warning.day}日&nbsp; ${warning.hour}:${warning.minute}
					</c:if>
					<c:if test="${warning.repeatCycle == '3'}">
						${warning.month}月${warning.day}日${warning.hour}:${warning.minute}
					</c:if>
					<c:if test="${warning.repeatCycle == '4'}">
						<fmt:formatDate value="${warning.date}" pattern="yyyy-MM-dd HH:mm"/>
					</c:if>
				</td>
				<%--<td>
					${warning.bubbleContent}
				</td>
				<td>
					${fns:getDictLabel(warning.bubbleDegree, 'warn_degree', '')}
				</td>--%>
				<td>
					<c:choose>
						<c:when test="${fn:contains(warning.alertContent,'createPersonList')}">
							<c:set var="alertContent" value="${fn:replace(warning.alertContent,'createPersonList', '')}" />
							${alertContent}
							<input type="button" onclick="createPersonList()" value="生成名单">
						</c:when>
						<c:otherwise>
							<span class="demo"> ${warning.alertContent}</span>
						</c:otherwise>
					</c:choose>
					<%--${warning.alertContent}--%>
				</td>
				<td>
					${fns:getDictLabel(warning.alertDegree, 'warn_degree', '')}
				</td>
				<td>
					<a onclick="handelById('${warning.id}','确定已完成/收到？','${warning.repeatCycle}')" style="cursor: pointer">已完成/收到</a>
					<%--<a onclick="handelById('${warning.id}','确定收到？')" style="cursor: pointer">收到</a>--%>
					<a onclick="noRemind('${warning.id}','确定今日不再提醒？')" style="cursor: pointer">今日不再提醒</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>