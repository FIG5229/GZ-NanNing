<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党建工作情况</title>
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
		.charts-div2 {
			display: inline-block;
			width: 450px;
			height: 550px;
		}

	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li class="active"><a href="${ctx}/sys/charts/party?id=${id}">党建工作情况</a></li>--%>
		<li class="active"><a href="${ctx}/sys/charts/partyIndex">党建工作情况</a></li>
	</ul>

	<div class="content-div">
        <div >
            <div >
				类别：
				<form:select id="dateType" path="dateType" class="input-medium" cssStyle="margin-bottom: 10px;width: 100px;">
					<form:options items="${fns:getDictList('statistics_dateType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<input id="month" name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${month}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width: 120px;display: none;"/>

				<input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" style="width: 60px;"/>

				<%-- 时间段查询--%>
				<input id="dateStart" name="dateStart" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${dateStart}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" style="width: 120px;display: none;"/>
				<span id="spanTo" style="display: none">-</span>
				<input id="dateEnd" name="dateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="${dateEnd}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" style="width: 120px;display: none;"/>

				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" style="margin-bottom: 10px;" onclick="selectEcharts()"/>
            </div>
        </div>
	</div>

<%--	<div class="content-div">
		<div>党组织概况</div>
		<div class="inner-div">
			<div id="first" class="charts-div"></div>
			<div id="second" class="charts-div"></div>
		</div>
	</div>--%>

	<div class="content-div">
		<div>
			<select id="selectType2" style="width:165px;">
				<option value="1">民主（组织）生活会</option>
				<option value="2">党书记述职测评</option>
				<option value="3">党员人数</option>
				<option value="4">民主评议党员</option>
				<option value="5">党组织换届</option>
			</select>
		</div>
		<div class="inner-div" style="margin-top: 5px;">
			<div id="fifth" class="charts-div2"></div>
			<%--党员人数--%>
			<div id="eighth" class="charts-div2"  style="display: none;"></div>
			<div id="sixth" class="charts-div2"></div>
			<div id="seventh" class="charts-div2" style="display: none;"></div>
		</div>
	</div>
	
	<%--<div class="content-div">
		<div>
			<select id="selectType" style="width:165px;">
				<option value="1">组织奖励信息</option>
				<option value="2">"两学一做"学习教育</option>
				&lt;%&ndash;<option value="3">党组织综合</option>&ndash;%&gt;
			</select>
		</div>
		<div class="inner-div" style="margin-top: 5px;">
			<div id="third" class="charts-div"></div>
			<div id="fourth" class="charts-div"></div>
		</div>
	</div>--%>
	
	<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		//查询按钮
		function selectEcharts(){
			// jiceng();
			// renshu();
			/*if($("#selectType").val() == '1'){
                orgRewardPunish();
            }else if($("#selectType").val() == '2'){
                liangxueyizuo();
            }*/
			if($("#selectType2").val() == '1'){
				lifeMeet();
			}else if($("#selectType2").val() == '2'){
				assess();
			}else if($("#selectType2").val() == '3'){
				// partyMember();
				//更改查询信息
				partyMemberInfo();
			}else if($("#selectType2").val() == '4'){
				comment();
			}else if ($("#selectType2").val() == '5') {
				election();
			}
		}
		//民主组织生活会
		function lifeMeet(){
			$.get('${ctx}/sys/charts/lifeMeet?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				$('#seventh').hide();
				$("#sixth").hide();
				$("#eighth").hide();
				$("#fifth").show();
				var labelData = ['完成','未完成'];
				var countData = [];
				countData.push(data.done);
				countData.push(data.unDone);
				// 基于准备好的dom，初始化echarts实例
				var fifthChart = echarts.init(document.getElementById('fifth'));
				fifthChart.off('click'); // 移除点击事件
				// 指定图表的配置项和数据
				var fifthOption = {
					title: {text: '完成情况', x: 'center'},
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
					series : [
						{
							name:'党支部数量',
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
				fifthChart.setOption(fifthOption, true);
				fifthChart.on('click', function (params) {
					openLifeMeetDetailDialog(params.name);
				});

			});
		}

		function openLifeMeetDetailDialog(label) {

			var year = $("#year").val();
			var dateStart = $("#dateStart").val();
			var dateEnd =  $("#dateEnd").val();
			var month = $("#month").val();
			var url="iframe:${ctx}/sys/charts/lifeMeet/detail?"+"label="+label+"&year="+year+"&dateStart="+dateStart+"&dateEnd="+dateEnd+"&month="+month+"&dateType="+$("#dateType").val();
			top.$.jBox.open(url, "民主生活会完成情况",1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});

		}

		function partyMemberInfo(){
			clearChart("eighth");
			$.get('${ctx}/sys/charts/partyNum?dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				$("#eighth").show();
				var labelData =[];// ['全局党员数','正式党员','预备党员','入党积极分子','入党发展对象','党代表','在职','离退','女','少数民族'];
				var countData = [];
				for(var i=0;i<data.length;i++){
					labelData.push(data[i].label);
					countData.push({name:data[i].label,value:data[i].num});
				}
				// 基于准备好的dom，初始化echarts实例
				var eighthChart = echarts.init(document.getElementById('eighth'));
				eighthChart.off('click'); // 移除点击事件
				// 指定图表的配置项和数据
				var eighthOption = {
					//title: {text: '类别', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: labelData,
						icon: 'circle'
					},
					toolbox: {},
					calculable: true,
					xAxis: [
						{
							type: 'category',
							data: labelData,
							axisLabel: {
								interval:0,
								rotate:40
							}
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
					grid: {
						left:50
					},
					series: [
						{
							name: '党员信息',
							type: 'bar',
							data: countData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				eighthChart.setOption(eighthOption, true);
				eighthChart.on('click', function (params) {
					var baseUrl = "iframe:${ctx}/sys/charts/partyMemberDetail?name=";
					if (params.name == "正式党员" || params.name == "预备党员"  ){
						baseUrl = "iframe:${ctx}/sys/charts/partyMemberDetail?name=";
					}
					if (params.name == "入党发展对象") {
						baseUrl = "iframe:${ctx}/affair/affairDevelopObject/partyDevelopDetail?flag=";
					}
					if (params.name == "入党积极分子") {
						baseUrl = "iframe:${ctx}/affair/affairActivist/partyActivistDetail?flag=";
					}
					if (params.name == "党代表") {
						baseUrl = "iframe:${ctx}/affair/affairOtherPartyRepresentative/partyRepresentativeDetail?flag=";
					}
					var url = baseUrl+params.name+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});
		}



		//党书记述职测评
		function assess(){
			$.get('${ctx}/sys/charts/assessPie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				$('#seventh').hide();
				$("#eighth").hide();
				var pieLabelData = ['完成','未完成'];
				var pieData = [];
				pieData.push({name : '完成', value: data.num});
				pieData.push({name : '未完成', value: data.unDone});
				// 基于准备好的dom，初始化echarts实例
				var fifthChart = echarts.init(document.getElementById('fifth'));
				// 指定图表的配置项和数据
				var fifthOption = {
					title: {text: '完成情况', x: 'center'},
					tooltip: {
						trigger: 'item',					//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
						formatter: '{a} <br/>{b} : {c} ({d}%)'
					},
					legend: {
						// orient: 'vertical',
						// top: 'middle',
						bottom: 10,
						left: 'center',
						data: pieLabelData,
						icon: 'circle'
					},
					series: [
						{
							name: '党支部数量',
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
				//清空哦echart
				fifthChart.clear();
				fifthChart.off('click');
				// 使用刚指定的配置项和数据显示图表。
				fifthChart.setOption(fifthOption, true);
				fifthChart.on('click', function (params) {
					<%--self.location.href = "${ctx}/affair/affairAssess/list";--%>
					openAssessDetail(params.name);
				});
			});
			$.get('${ctx}/sys/charts/assessColumn?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				$('#seventh').hide();
				$("#eighth").hide();
				var labelData = [];
				var countData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					countData.push(data[i].num);
				}
				// 基于准备好的dom，初始化echarts实例
				var sixthChart = echarts.init(document.getElementById('sixth'));
				var sixthOption = {
					title: {text: '测评等级', x: 'center'},
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
					series : [
						{
							name:'测评等次',
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
				sixthChart.clear();
				sixthChart.off('click');
				sixthChart.setOption(sixthOption, true);
				$("#sixth").show();
				sixthChart.on('click', function (params) {
					<%--self.location.href = "${ctx}/affair/affairAssess/list";--%>
					openAssessLevelDetail(params.name);
					var url = "ifamre:${ctx}/sys/charts"
				});
			});
		}

		function openAssessDetail(label) {
			var url="iframe:${ctx}/affair/affairGeneralSituation/assessDoneDetail?"+"label="+label+getDateParam();
			top.$.jBox.open(url, label,1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function getDateParam() {
			var dateType="&dateType="+$("#dateType").val();
			var year = "&year="+$("#year").val();
			var dateStart="&dateStart="+$("#dateStart").val();
			var dateEnd="&dateEnd="+$("#dateEnd").val();
			var month="&month="+$("#month").val();
			var dateParam = dateType+year+dateStart+dateEnd+month;
			return dateParam;
		}
		function openAssessLevelDetail(label) {
			var url="iframe:${ctx}/affair/affairAssess/assessLevelDetail?"+"label="+label+'&dateType='+$("#dateType").val()+"&years="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
			top.$.jBox.open(url, label,1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		/**
		 * 清除其他的echart
		 * @param type
		 */
		function clearChart(type){
			var chart=[];
			var sixthChart = echarts.init(document.getElementById('sixth'));
			chart.push({type:"sixth",value:sixthChart});
			var fifthChart = echarts.init(document.getElementById('fifth'));
			chart.push({type:"fifth",value:fifthChart});
			for (var i = 0;i<chart.length;i++){
				if (type != chart[i].type) {
					chart[i].value.clear();
				}
			}
		}


		//民主评议党员
		function comment(){
			$.get('${ctx}/sys/charts/commentGrade?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				$('#seventh').hide();
				$("#sixth").hide();
				$("#eighth").hide();
				var labelData = [];
				var countData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					countData.push(data[i].num);
				}
				// 基于准备好的dom，初始化echarts实例
				var fifthChart = echarts.init(document.getElementById('fifth'));
				fifthChart.off('click'); // 移除点击事件
				// 指定图表的配置项和数据
				var fifthOption = {
					title: {text: '类别', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: labelData,
						icon: 'circle'
					},
					toolbox: {},
					calculable: true,
					xAxis: [
						{
							type: 'category',
							data: labelData,
							axisLabel: {
								interval:0,
								rotate:40
							}
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
					grid: {
						left:50
					},
					series: [
						{
							name: '人数',
							type: 'bar',
							data: countData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				fifthChart.setOption(fifthOption, true);
				fifthChart.on('click', function (params) {
					var url = "iframe:${ctx}/affair/affairComment/echartGradeFindPage?flag="+params.name+"&pbId=${id}&dateType="+$("#dateType").val()+"&echartYear="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});

		}
		/*党组织换届*/
		function election(){
			$.get('${ctx}/sys/charts/election?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				$('#seventh').hide();
				$("#sixth").hide();
				$("#eighth").hide();
				var labelData = [];
				var countData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					countData.push(data[i].num);
				}
				// 基于准备好的dom，初始化echarts实例
				var fifthChart = echarts.init(document.getElementById('fifth'));
				fifthChart.clear();
				fifthChart.off('click'); // 移除点击事件
				// 指定图表的配置项和数据
				var fifthOption = {
					title: {text: '党组织换届', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: labelData,
						icon: 'circle'
					},
					toolbox: {},
					calculable: true,
					xAxis: [
						{
							type: 'category',
							data: labelData,
							axisLabel: {
								interval:0,
								rotate:40
							}
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
					grid: {
						left:50
					},
					series: [
						{
							name: '数量',
							type: 'bar',
							data: countData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				fifthChart.setOption(fifthOption, true);
				fifthChart.on('click', function (params) {
					var url = "iframe:${ctx}/affair/affairElection/electionDetail?label="+params.name+getDateParam();
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});

		}

		//调用民主组织生活会
		lifeMeet();
/*-----------------------------------------------------------------------------------------------------------------------------------------------*/

		//一进页面默认按年度查询
		$("#dateType").val(2);



		//调用基层党组织柱状图
		/*最新需求无此项*/
		// jiceng();
		//基层党组织柱状图
		/*function jiceng(){
			$.get('${ctx}/sys/charts/jiceng?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				var labelData = [];
				var countData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					countData.push(data[i].count);
				}

				// 基于准备好的dom，初始化echarts实例
				var firstChart = echarts.init(document.getElementById('first'));
				// 指定图表的配置项和数据
				var firstOption = {
					title: {text: '基层党组织', x: 'center'},
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
						bottom: '8%',
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
								rotate: 36,
								formatter: (params)=>{
								return '{marginLeft|'+params+'}';
			},
				{
					{
						[0, -20, 32, 0],
								color;: '#000',
							fontSize;: 14,
							fontFamily;: 'PingFangSC',
							fontWeight;: 300

					}
				}
			},
			}
			],
				[
					{
						type : 'value'
					}
				],
						//滚动条
						dataZoom;: [
					{
						show: true,
						start: 15,
						height: 20,
						end: 100,
						bottom: '2%',
					},
					{
						type: 'inside',
						start: 50,
						end: 100
					},
					{
						show: true,
						yAxisIndex: 0,
						filterMode: 'empty',
						width: 20,
						height: '70%',
						showDataShadow: false,
						left: '93%'
					}
				],
						series; : [
					{
						name:'党组织概况',
						type:'bar',
						barWidth: '60%',
						data: countData,
						label:{
							show:true,
							position:"top"
						}
					}
				]
			}
				// 使用刚指定的配置项和数据显示图表。
				/!*if (labelData.length) {
                    firstChart.setOption(firstOption, true);
                }*!/
				firstChart.setOption(firstOption, true);
			});
		}
		//调用党员人数柱状图
		/!*已经更换到下方下拉框*!/
		/!*renshu();*!/
		//党员人数柱状图
		function renshu(){
			$.get('${ctx}/sys/charts/renshu?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				var labelData = [];
				var countData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					countData.push(data[i].count);
				}

				// 基于准备好的dom，初始化echarts实例
				var secondChart = echarts.init(document.getElementById('second'));
				// 指定图表的配置项和数据
				var secondOption = {
					title: {text: '党员人数', x: 'center'},
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
						bottom: '8%',
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
								rotate: 36,
								formatter: (params)=>{
								return '{marginLeft|'+params+'}';
			},
				{
					{
						[0, -20, 32, 0],
								color;: '#000',
							fontSize;: 14,
							fontFamily;: 'PingFangSC',
							fontWeight;: 300

					}
				}
			},
			}
			],
				[
					{
						type : 'value'
					}
				],
						/!*滚动条*!/
						dataZoom;: [
					{
						show: true,
						start: 15,
						height: 20,
						end: 100,
						bottom: '2%',
					},
					{
						type: 'inside',
						start: 50,
						end: 100
					},
					{
						show: true,
						yAxisIndex: 0,
						filterMode: 'empty',
						width: 20,
						height: '70%',
						showDataShadow: false,
						left: '93%'
					}
				],
						series; : [
					{
						name:'党组织概况',
						type:'bar',
						barWidth: '60%',
						data: countData,
						label:{
							show:true,
							position:"top"
						}
					}
				]
			}
				// 使用刚指定的配置项和数据显示图表。
				/!*if (labelData.length) {
                    secondChart.setOption(secondOption, true);
                }*!/
				secondChart.setOption(secondOption, true);
			})
		}*/
		//两学一做
		/*function liangxueyizuo(){
            $.get('${ctx}/sys/charts/liangxueyizuo?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&month="+$("#month").val(), function(data) {
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
				title: {text: '先进党支部', x: 'center'},
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
					bottom: '8%',
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
                            rotate: 36,
                            formatter: (params)=>{
                                return '{marginLeft|'+params+'}';
                            },
                            rich: {
                                marginLeft: {
                                    padding: [0, -20, 32, 0],
                                    color: '#000',
                                    fontSize: 14,
                                    fontFamily: 'PingFangSC',
                                    fontWeight: 300

                                }
                            }
                        },
					}
				],
				yAxis : [
					{
						type : 'value'
					}
				],
                //滚动条
                dataZoom: [
                    {
                        show: true,
                        start: 15,
                        height: 20,
                        end: 100,
                        bottom: '2%',
                    },
                    {
                        type: 'inside',
                        start: 50,
                        end: 100
                    },
                    {
                        show: true,
                        yAxisIndex: 0,
                        filterMode: 'empty',
                        width: 20,
                        height: '70%',
                        showDataShadow: false,
                        left: '93%'
                    }
                ],
				series : [
					{
						name:'“两学一做”学习教育',
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
			/!*if (labelData.length > 0 && countData.length > 0) {
				thirdChart.setOption(thirdOption, true);
			}*!/
			thirdChart.setOption(thirdOption, true);

			var fourthChart = echarts.init(document.getElementById('fourth'));

			var fourthOption = {
				title: {text: '先进党支部', x: 'center'},
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
						name: '“两学一做”学习教育',
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
			/!*if (labelData) {
				fourthChart.setOption(fourthOption, true);
			}*!/
			fourthChart.setOption(fourthOption, true);

		});
	};*/

		//组织奖惩
		/*function orgRewardPunish(){
            $.get('${ctx}/sys/charts/orgRewardPunish?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&month="+$("#month").val(), function(data) {
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
                title: {text: '先进党支部', x: 'center'},
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
                    bottom: '8%',
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
                            rotate: 36,
                            formatter: (params)=>{
                                return '{marginLeft|'+params+'}';
                            },
                            rich: {
                                marginLeft: {
                                    padding: [0, -20, 32, 0],
                                    color: '#000',
                                    fontSize: 14,
                                    fontFamily: 'PingFangSC',
                                    fontWeight: 300

                                }
                            }
                        },
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                //滚动条
                dataZoom: [
                    {
                        show: true,
                        start: 15,
                        height: 20,
                        end: 100,
                        bottom: '2%',
                    },
                    {
                        type: 'inside',
                        start: 50,
                        end: 100
                    },
                    {
                        show: true,
                        yAxisIndex: 0,
                        filterMode: 'empty',
                        width: 20,
                        height: '70%',
                        showDataShadow: false,
                        left: '93%'
                    }
                ],
                series : [
                    {
                        name:'组织奖惩',
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
           /!* if (labelData.length > 0 && countData.length > 0) {
                thirdChart.setOption(thirdOption, true);
            }*!/
			thirdChart.setOption(thirdOption, true);


            var fourthChart = echarts.init(document.getElementById('fourth'));

            var fourthOption = {
                title: {text: '先进党支部', x: 'center'},
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
                        name: '组织奖惩',
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
            /!*if (labelData) {
                fourthChart.setOption(fourthOption, true);
            }*!/
			fourthChart.setOption(fourthOption, true);

        });
    };*/
		//调用组织奖惩方法
		//orgRewardPunish();

		/*$("#selectType").change(function(){
            if($("#selectType").val() == '1'){
                orgRewardPunish();
            }else if($("#selectType").val() == '2'){
                liangxueyizuo();
            }
        });*/


		function openAssessDetail(label) {
			var url="iframe:${ctx}/affair/affairGeneralSituation/assessDoneDetail?"+"label="+label+getDateParam();
			top.$.jBox.open(url, label,1000,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		//党员类别统计
		function partyMember(){
			$.get('${ctx}/sys/charts/partyMemberCategory?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				var labelData = [];
				var countData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					countData.push(data[i].count);
				}
				// 基于准备好的dom，初始化echarts实例
				var fifthChart = echarts.init(document.getElementById('fifth'));
				fifthChart.off('click'); // 移除点击事件
				// 指定图表的配置项和数据
				var fifthOption = {
					//title: {text: '类别', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: labelData,
						icon: 'circle'
					},
					toolbox: {},
					calculable: true,
					xAxis: [
						{
							type: 'category',
							data: labelData,
							axisLabel: {
								interval:0,
								rotate:40
							}
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
					grid: {
						left:50
					},
					series: [
						{
							name: '人数',
							type: 'bar',
							data: countData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				fifthChart.setOption(fifthOption, true);
				fifthChart.on('click', function (params) {
					var url = "iframe:${ctx}/affair/affairPartyMember/echartCategoryFindPage?flag="+params.name+"&pbId=${id}&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});
			//党员性别统计
			$.get('${ctx}/sys/charts/partyMemberSex?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				var labelData = [];
				var countData = [];
				var pieCountData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					countData.push(data[i].count);
				}

				if(labelData != null && labelData != undefined){
					for (var i = 0; i < labelData.length; i++) {
						pieCountData.push({name : labelData[i], value: countData[i]});
					}

				}
				// 基于准备好的dom，初始化echarts实例
				var sixthChart = echarts.init(document.getElementById('sixth'));
				sixthChart.off('click'); // 移除点击事件
				var sixthOption = {
					title: {text: '性别', x: 'center'},
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
							name: '人数',
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
							data: pieCountData
						}
					]
				};
				sixthChart.setOption(sixthOption, true);
				$("#sixth").show();
				sixthChart.on('click', function (params) {
					var url = "iframe:${ctx}/affair/affairPartyMember/echartSexFindPage?flag="+params.name+"&pbId=${id}&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});
			//民族统计
			$.get('${ctx}/sys/charts/partyMemberNation?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				var labelData = [];
				var countData = [];
				var pieCountData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					countData.push(data[i].count);
				}

				if(labelData != null && labelData != undefined){
					for (var i = 0; i < labelData.length; i++) {
						pieCountData.push({name : labelData[i], value: countData[i]});
					}

				}
				// 基于准备好的dom，初始化echarts实例
				var seventhChart = echarts.init(document.getElementById('seventh'));
				seventhChart.off('click'); // 移除点击事件
				var seventhOption = {
					title: {text: '民族', x: 'center'},
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
							name: '人数',
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
							data: pieCountData
						}
					]
				};
				seventhChart.setOption(seventhOption, true);
				$('#seventh').show();
				seventhChart.on('click', function (params) {
					var url = "iframe:${ctx}/affair/affairPartyMember/echartNationFindPage?flag="+params.name+"&pbId=${id}&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});
			//在职、离退人员统计
			$.get('${ctx}/sys/charts/partyMemberWorkPlace?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				$("#eighth").show();
				var labelData = [];
				var countData = [];
				labelData.push("在职党员数");
				labelData.push("离退党员数");
				countData.push(data.zzhCount);
				countData.push(data.ltxCount);
				// 基于准备好的dom，初始化echarts实例
				var eighthChart = echarts.init(document.getElementById('eighth'));
				eighthChart.off('click'); // 移除点击事件
				// 指定图表的配置项和数据
				var eighthOption = {
					//title: {text: '类别', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: labelData,
						icon: 'circle'
					},
					toolbox: {},
					calculable: true,
					xAxis: [
						{
							type: 'category',
							data: labelData,
							axisLabel: {
								interval:0,
								rotate:40
							}
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
					grid: {
						left:50
					},
					series: [
						{
							name: '人数',
							type: 'bar',
							data: countData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				eighthChart.setOption(eighthOption, true);
				eighthChart.on('click', function (params) {
					var flag = "";
					if("在职党员数"== params.name){
						flag = "1";
					}else{
						flag = "2";
					}
					var url = "iframe:${ctx}/affair/affairPartyMember/echartWorkPlaceFindPage?flag="+flag+"&pbId=${id}&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(h){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				});
			});
		}






		//年度、月份、时间段
		$("#dateType").change(function(){
			if($("#dateType").val() == '2'){//年度
				$('#year').css('display', 'inline-block');
				$('#month').css('display', 'none');
				$('#dateStart').css('display', 'none');
				$('#spanTo').css('display', 'none');
				$('#dateEnd').css('display', 'none');
				$('#month').val('');
				$('#dateStart').val('');
				$('#dateEnd').val('');
			}else if($("#dateType").val() == '1'){//月度
				$('#year').css('display', 'none');
				$('#month').css('display', 'inline-block');
				$('#dateStart').css('display', 'none');
				$('#spanTo').css('display', 'none');
				$('#dateEnd').css('display', 'none');
				$('#year').val('');
				$('#dateStart').val('');
				$('#dateEnd').val('');
			}else{//时间段
				$('#year').css('display', 'none');
				$('#month').css('display', 'none');
				$('#dateStart').css('display', 'inline-block');
				$('#spanTo').css('display', 'inline-block');
				$('#dateEnd').css('display', 'inline-block');
				$('#month').val('');
				$('#year').val('');
			}
		});


		$("#selectType2").change(function(){
			if($("#selectType2").val() == '1'){
				lifeMeet();
			}else if($("#selectType2").val() == '2'){
				assess();
			}else if($("#selectType2").val() == '3'){
				//更改查询信息
				// partyMember();
				partyMemberInfo();
			}else if($("#selectType2").val() == '4'){
				comment();
			}else if ($("#selectType2").val() == '5') {
				election();
			}
		});
	</script>

</body>
</html>