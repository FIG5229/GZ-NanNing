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
			<div class="modal-custom-main-step">
				<div class="step-start active">开始</div>
				<div class="modal-step-col active">系统自评</div>
				<div class="modal-step-col active">系统初步考核</div>
				<div class="modal-step-col active">系统公示</div>
				<div class="modal-step-col active">部门负责人签字</div>
				<div class="modal-step-col active">分管局领导签字</div>
				<div class="modal-step-col active">绩效考评领导小组复核及调整</div>
				<div class="modal-step-col active">局主管领导最终审签</div>
				<div class="modal-step-col active">最终结果全局公示</div>
				<div class="step-start step-end active">结束</div>
			</div>
			<div class="modal-custom-bar" style="padding-left: 20px;">
				2019年9月份南宁铁路公安局各处考评情况  
				<select>
					<option value ="">总成绩</option>
				</select>
			</div>
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
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
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
			    text: '2019年9月份南宁铁路公安局各处考评情况',
			    x:'center'
			},
			legend: {
					orient: 'horizontal',
					bottom: 0,
					data: ['新闻宣传情况区','新闻宣传情况区','新闻宣传情况区']
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
		            data: ['新闻宣传情况区','新闻宣传情况区','新闻宣传情况区'],
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
		            data:[15, 8, 4, 7]
		        }
		    ]
		};
		myChart1.setOption(option);
	}
</script>
