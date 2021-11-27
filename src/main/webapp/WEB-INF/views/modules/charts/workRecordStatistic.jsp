<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作记实统计</title>
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
<ul class="nav nav-tabs" id="nav">
	<li  class="active"><a href="${ctx}/sys/charts/personnel?id=${id}">工作记实</a></li>
</ul>

<div class="content-div">
	<label>类别：绩效考评</label>
</div>
<div class="content-div">
	<%--未完成--%>
	<div>工作记实情况</div>
	<div class="inner-div">
		<div id="first" class="charts-div"></div>
		<div id="second" class="charts-div"></div>
	</div>
</div>

<%--<div class="content-div">
    <div>人员岗位情况</div>
    <div class="inner-div">
        <div id="third" class="charts-div"></div>
        <div id="fourth" class="charts-div"></div>
    </div>
</div>--%>

<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		<c:if test="${'绩效考核情况'.equals(target)}">
		$("#nav li a").each(function(){
			if('绩效考核情况'==this.innerText){this.click();}
		});
		</c:if>
	});
	$(function() {
		$.get('${ctx}/sys/charts/personnel-politics?id=${id}', function(data) {
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
						},
						axisLabel: {
							interval:0,
							rotate:40
						}
					}
				],
				yAxis : [
					{
						type : 'value'
					}
				],
				grid: {
					left: 50,
					bottom:'10%'
				},
				series : [
					{
						name:'政治面貌情况',
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
			if (labelData) {
				firstChart.setOption(firstOption);
			}
			firstChart.on("click",function (params) {
				openPoliticsDetailDialog(params.name);
			});

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
						name: '政治面貌情况',
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
			if (labelData) {
				secondChart.setOption(secondOption);
			}

			secondChart.on("click",function (params) {
				openPoliticsDetailDialog(params.name);
			});
		});
	});
	function getDateParam() {
		var dateType="&dateType="+$("#dateType").val();
		var year = "&year="+$("#year").val();
		var dateStart="&dateStart="+$("#dateStart").val();
		var dateEnd="&dateEnd="+$("#dateEnd").val();
		var month="&month="+$("#month").val();
		var dateParam = dateType+year+dateStart+dateEnd+month;
		return dateParam;
	}
	function openPoliticsDetailDialog(label){
		var url = "iframe:${ctx}/personnel/personnelBase/politicsFaceDetail?label="+label
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}
</script>
</body>
</html>