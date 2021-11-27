<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<!-- 绩效考评-全局公示 -->
<div id="modalAllPublic" class="">
	<div class="">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<%@include file="/WEB-INF/views/modules/exam/step.jsp"%>
			<!--
			<div class="modal-custom-bar" style="padding-left: 20px;">
				${name}情况
				<select>
					<option value ="">总成绩</option>
				</select>
			</div>
			-->
			<div class="modal-custom-content">
				<div class="modal-custom-tb-l">
					<div id="modalAllPublicEchart"></div>
				</div>
				<div class="modal-custom-tb-r">
					<table class="table table-striped table-bordered table-condensed" style="width: 100%;">
						<thead>
							<tr>
								<th>单位名称</th>
								<th>总得分</th>
								<th>基础分</th>
								<th>扣分情况</th>
								<th>实际得分</th>
								<th>换算后得分</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${objList}" var="obj">
								<tr>
									<td>${obj.name}</td>
									<td style="color: #2429FF;"><a href="${ctx}/exam/examWorkflow/appraise/public?id=${workflowId}&objId=${obj.id}&status=${status}">${obj.value}</a></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	modalAllPublicEchart()
	function modalAllPublicEchart(){
		var myChart1 = echarts.init(document.getElementById('modalAllPublicEchart'));
		option = {
			title : {
			    text: '${name}情况',
			    x:'center'
			},
			legend: {
					orient: 'horizontal',
					bottom: 0,
					data: [
						<c:forEach items="${objList}" var="obj" varStatus="status">
							<c:choose>
							   <c:when test="${status.index==0}">
						          '${obj.name}'
								</c:when>
								<c:otherwise>,'${obj.name}'
								</c:otherwise>
							</c:choose>
						</c:forEach>
					]
			},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data: [
						<c:forEach items="${objList}" var="obj" varStatus="status">
							<c:choose>
								<c:when test="${status.index==0}">
								'${obj.name}'
								</c:when>
								<c:otherwise>,'${obj.name}'
								</c:otherwise>
							</c:choose>
						</c:forEach>],
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'考评',
		            type:'bar',
		            barWidth: '60%',
		            data:[
						<c:forEach items="${objList}" var="obj" varStatus="status">
							<c:choose>
								<c:when test="${status.index==0}">
								${obj.value}
								</c:when>
								<c:otherwise>,'${obj.value}'
								</c:otherwise>
							</c:choose>
						</c:forEach>]
		        }
		    ]
		};
		myChart1.setOption(option);
	}
</script>
