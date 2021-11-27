<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构信息情况</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<style type="text/css">
		.content-div {
			margin: 20px;
			padding: 40px;
			border:1px solid #000;
			border-radius: 4px;
			width: 1000px;
		}
		.inner-div {
			padding: 20px;
			border:1px solid #000;
			border-radius: 4px;
		}
		
		.charts-div {
			display: inline-block;
			width: 450px;
			height: 550px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li  class="active"><a href="${ctx}/sys/charts/organization?id=${id}">机构信息情况</a></li>
	</ul>
	
	<div class="content-div">
		<label for="year">年度：</label>
		<input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
			   value="${year}"
			   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" style="width: 60px;"/>
		<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" style="margin-bottom: 10px;" onclick="selectEcharts()"/>
	</div>
	
	<div class="content-div">
		<div>人员编制情况</div>
		<div class="inner-div">
			<div id="first" class="charts-div"></div>
			<div id="second" class="charts-div"></div>
		</div>
	</div>
	
	<div class="content-div">
		<div>职数情况</div>
		<div class="inner-div">
			<div id="third" class="charts-div"></div>
			<div id="fourth" class="charts-div"></div>
		</div>
	</div>
	
	<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">

	$(function() {
		bainzhi();
		zhishu();
	});
	function bainzhi(){
		$.get('${ctx}/sys/charts/bianzhi?id=${id}'+'&year='+$("#year").val(), function(data) {
			var labelData = [];
			var countData = [];
			var pieData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
				var pie = {};
				pie.value = data[i].count;
				pie.name = data[i].label;
				pieData.push(pie);
			}
			// 基于准备好的dom，初始化echarts实例
			var firstChart = echarts.init(document.getElementById('first'));
			// 指定图表的配置项和数据
			var firstOption = {
				color: ['#3398DB'],
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
						data : labelData,
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
						name:'人员编制情况',
						type:'bar',
						barWidth: '60%',
						data: countData,
						label:{
							show:true,
							position:"top"
						}
					}
				]
			};
			// 使用刚指定的配置项和数据显示图表。
			firstChart.setOption(firstOption,true);


			var secondChart = echarts.init(document.getElementById('second'));
			var secondOption = {
				tooltip: {
					trigger: 'item',					//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
					formatter: '{a} <br/>{b} : {c} ({d}%)'
				},
				legend: {
					// orient: 'vertical',
					// top: 'middle',
					bottom: 10,
					left: 'center',
					data: labelData,
					icon: 'circle'
				},
				series: [
					{
						name: '职数情况',
						type: 'pie',
						selectedMode: 'single',
						radius: [0, '70%'],
						label: {
							normal: {
								position: 'inner',
								formatter: '{d}%'
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data:pieData
					}
				]
			};
			secondChart.setOption(secondOption,true);
		});
	};

	function zhishu(){
		$.get('${ctx}/sys/charts/zhishu?id=${id}'+'&year='+$("#year").val(), function(data) {
			var labelData = [];
			var countData = [];
			var pieData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
				var pie = {};
				pie.value = data[i].count;
				pie.name = data[i].label;
				pieData.push(pie);
			}

			// 基于准备好的dom，初始化echarts实例
			var thirdChart = echarts.init(document.getElementById('third'));
			// 指定图表的配置项和数据
			var thirdOption = {
				color: ['#3398DB'],
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
						data : labelData,
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
						name:'职数情况',
						type:'bar',
						barWidth: '60%',
						data: countData,
						label:{
							show:true,
							position:"top"
						}
					}
				]
			};

			// 使用刚指定的配置项和数据显示图表。
			thirdChart.setOption(thirdOption,true);

			var fourthChart = echarts.init(document.getElementById('fourth'));

			var fourthOption = {
				tooltip: {
					trigger: 'item',					//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
					formatter: '{a} <br/>{b} : {c} ({d}%)'
				},
				legend: {
					// orient: 'vertical',
					// top: 'middle',
					bottom: 10,
					left: 'center',
					data: labelData,
					icon: 'circle'
				},
				series: [
					{
						name: '职数情况',
						type: 'pie',
						selectedMode: 'single',
						radius: [0, '70%'],
						label: {
							normal: {
								position: 'inner',
								formatter: '{d}%'
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data: pieData
					}
				]
			};
			fourthChart.setOption(fourthOption,true);

		});
	};
	//查询按钮
	function selectEcharts(){
		bainzhi();
		zhishu();
	};
	</script>
</body>
</html>