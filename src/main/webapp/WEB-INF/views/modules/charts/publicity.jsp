<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>宣传教育情况</title>
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
		.charts-divk {
			display: inline-block;
			width: 450px;
			height: 550px;
			margin-top: 20px;
		}
		#sevenKnowledge{
			width: 900px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
<%--		<li class="active"><a href="${ctx}/sys/charts/publicity?id=${id}">宣传教育情况</a></li>--%>
		<li class="active"><a href="${ctx}/sys/charts/publicityIndex?id=${id}">宣传教育情况</a></li>
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
	
	<div class="content-div">
		<div>立功嘉奖</div>
		<div class="inner-div">
			<div id="first" class="charts-div"></div>
			<div id="second" class="charts-div"></div>
		</div>
	</div>
<%--新需求不存在舆情
	<div class="content-div">
		<div>
			<label for="reason">舆情管控情况</label>
			<select id="reason" style="width: 100px;">
				<option value="1" class="active">来源</option>
				<option value="2">处置情况</option>
			</select>
		</div>
		<div class="inner-div" style="margin-top:5px;" id="shows">
			<div id="third" class="charts-div"></div>
			<div id="fourth" class="charts-div"></div>
		</div>
	</div>--%>

	<div class="content-div">
		<div>理论学习情况</div>
		<div class="inner-div">
			<div id="fifth" class="charts-div"></div>
			<div id="sixth" class="charts-div"></div>
		</div>
	</div>

	<div class="content-div">
		<div>典型培树情况</div>
		<div class="inner-div">
			<div id="typicalPerson" class="charts-div"></div>
			<div id="typicalTeam" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>读书活动开展情况</div>
		<div class="inner-div">
			<div id="readBook" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>文艺</div>
		<div class="inner-div">
			<div id="literaryWorks" class="charts-div"></div>
			<div id="literaryTalents" class="charts-div"></div>
			<div id="talentsJoin" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>
			<select id="selectType" style="width: 100px">
				<c:if test="${officeId != '34' && officeId != '95' && officeId != '156' && companyId == '1' || companyId == '0' }">
					<option value="2">南宁处</option>
					<option value="3">柳州处</option>
					<option value="4">北海处</option>
				</c:if>
				<c:if test="${companyId == '34' || officeId == '34'}">
					<option value="2">南宁处</option>
				</c:if>
				<c:if test="${companyId == '95' || officeId == '95'}">
					<option value="3">柳州处</option>
				</c:if>
				<c:if test="${companyId == '156' || officeId == '156'}">
					<option value="4">北海处</option>
				</c:if>
			</select>
		</div>
		<br/>
		<%--<div>刊稿稿件</div>--%>
		<div class="inner-div">
			<%--<div id="news" class="charts-divk"></div>--%>
			<div id="nncnews" class="charts-divk"></div>
			<div id="lzcnews" class="charts-divk"></div>
			<div id="bhcnews" class="charts-divk"></div>
		</div>
	</div>

	<div class="content-div">
		<div>七知档案</div>
		<div class="inner-div">
			<div id="sevenKnowledge" class="charts-divk"></div>
		</div>
	</div>
	<script>

	</script>
	<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		//一进页面默认按年度查询
		$("#dateType").val(2);

		function getDateParam() {
			var dateType="&dateType="+$("#dateType").val();
			var year = "&year="+$("#year").val();
			var dateStart="&dateStart="+$("#dateStart").val();
			var dateEnd="&dateEnd="+$("#dateEnd").val();
			var month="&month="+$("#month").val();
			/* switch ($("#dateType").val()) {
                 case '1':   //月度
                     month="&month="+$("#month").val();
                     dateStart="&dateStart=null";
                     dateEnd="&dateEnd=null";
                     year="&year=null";
                     break;
                 case '3':   //时间段
                     year = "&year=null";
                     dateStart="&dateStart="+$("#dateStart").val();
                     dateEnd="&dateEnd="+$("#dateEnd").val();
                     month="&month=null";
                     break;
                 default:    //年度
                     year = "&year="+$("#year").val();
                     dateStart="&dateStart=null";
                     dateEnd="&dateEnd=null";
                     month="&month=null";
                     break;
             }*/
			var dateParam = dateType+year+dateStart+dateEnd+month;
			return dateParam;
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
		unitReward();
		function unitReward() {
			$.get('${ctx}/sys/charts/unitReward?'+getDateParam(), function(data){
				var labelData = data.labelData;
				var totalData = data.totalData;
				// 基于准备好的dom，初始化echarts实例
				var firstChart = echarts.init(document.getElementById('first'));
				firstChart.off('click');
				// 指定图表的配置项和数据
				var firstOption = {
					title: {text: '集体奖励', x: 'center'},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data:'数量',
						icon: 'circle'
					},
					toolbox: {

					},
					calculable : true,
					xAxis : [
						{
							type : 'category',
							data :labelData,
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
						bottom:'13%'
					},
					series : [
						{
							name: '数量',
							type: 'bar',
							data: totalData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				firstChart.setOption(firstOption, true);
				firstChart.on('click', function (params) {
					var nameCodeType = "";
					if ("集体一等功" == params.name) {
						nameCodeType = "1";
						var url = "iframe:${ctx}/affair/affairXcUnitReward/findUnitRewardInfoDetail?nameCodeType="+nameCodeType+getDateParam();
						top.$.jBox.open(url, params.name,1500,550,{
							buttons:{"关闭":true},
							loaded:function(){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					}else if ("集体二等功" == params.name){
						nameCodeType = "2";
						var url = "iframe:${ctx}/affair/affairXcUnitReward/findUnitRewardInfoDetail?nameCodeType="+nameCodeType+getDateParam();
						top.$.jBox.open(url, params.name,1500,550,{
							buttons:{"关闭":true},
							loaded:function(){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					} else if ("集体三等功" == params.name){
						nameCodeType = "3";
						var url = "iframe:${ctx}/affair/affairXcUnitReward/findUnitRewardInfoDetail?nameCodeType="+nameCodeType+getDateParam();
						top.$.jBox.open(url, params.name,1500,550,{
							buttons:{"关闭":true},
							loaded:function(){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					}else if ("嘉奖" == params.name){
						nameCodeType ="4','5','6','7','8','9','10','11','12";
						var url = "iframe:${ctx}/affair/affairXcUnitReward/findOtherUnitRewardInfoDetail?nameCodeType="+nameCodeType+getDateParam();
						top.$.jBox.open(url, params.name,1500,550,{
							buttons:{"关闭":true},
							loaded:function(){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					}

				})
			});
		}
		personnelReward();
		function personnelReward() {
			$.get('${ctx}/sys/charts/personnelReward?'+getDateParam(), function(data){
				var labelData = data.labelData;
				var totalData = data.totalData;
				// 基于准备好的dom，初始化echarts实例
				var secondChart = echarts.init(document.getElementById('second'));
				secondChart.off('click');
				// 指定图表的配置项和数据
				var secondOption = {
					title: {text: '个人奖励', x: 'center'},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data:'数量',
						icon: 'circle'
					},
					toolbox: {

					},
					calculable : true,
					xAxis : [
						{
							type : 'category',
							data :labelData,
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
						bottom:'13%'
					},
					series : [
						{
							name: '数量',
							type: 'bar',
							data: totalData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				secondChart.setOption(secondOption, true);
				secondChart.on('click', function (params) {
					var nameCodeType = "";
					if ("个人一等功" == params.name) {
						nameCodeType = "1";
						var url = "iframe:${ctx}/affair/affairPersonalReward/findPersonnelRewardInfoDetail?nameCodeType="+nameCodeType+getDateParam();
						top.$.jBox.open(url, params.name,1500,550,{
							buttons:{"关闭":true},
							loaded:function(){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					}else if ("个人二等功" == params.name){
						nameCodeType = "2";
						var url = "iframe:${ctx}/affair/affairPersonalReward/findPersonnelRewardInfoDetail?nameCodeType="+nameCodeType+getDateParam();
						top.$.jBox.open(url, params.name,1500,550,{
							buttons:{"关闭":true},
							loaded:function(){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					} else if ("个人三等功" == params.name){
						nameCodeType = "3";
						var url = "iframe:${ctx}/affair/affairPersonalReward/findPersonnelRewardInfoDetail?nameCodeType="+nameCodeType+getDateParam();
						top.$.jBox.open(url, params.name,1500,550,{
							buttons:{"关闭":true},
							loaded:function(){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					}else if ("嘉奖" == params.name){
						nameCodeType ="4','5','6','7','8','9','10','11','12";
						var url = "iframe:${ctx}/affair/affairPersonalReward/findOtherPersonnelRewardInfoDetail?nameCodeType="+nameCodeType+getDateParam();
						top.$.jBox.open(url, params.name,1500,550,{
							buttons:{"关闭":true},
							loaded:function(){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					}

				})
			});
		}
		
		//隐藏之前实现的新闻宣传统计分析
	/*	news();
		function news(){
			$.get('${ctx}/sys/charts/news?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
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
							name:'新闻宣传情况',
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
				// if (labelData.length > 0 && countData.length > 0) {
				// 	firstChart.setOption(firstOption, true);
				// }
				firstChart.setOption(firstOption, true);


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
							name: '新闻宣传情况',
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
				/!*if (labelData.length > 0 && pieData.length > 0) {
					secondChart.setOption(secondOption, true);
				}*!/
				secondChart.setOption(secondOption, true);
			});
		}*/
	//调用
	// yuQing($("#reason").val());
	//舆情图表
	function yuQing(flag){
		$.get('${ctx}/sys/charts/yuqing?id=${id}&flag='+flag+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
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
					bottom:'13%'
				},
				series : [
					{
						name:'舆情管控情况',
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

			/*if (labelData.length > 0 && countData.length > 0) {
				thirdChart.setOption(thirdOption);
			}*/
			thirdChart.setOption(thirdOption, true);

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
						name: '舆情管控情况',
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
			/*if (labelData.length > 0 && pieData.length > 0) {
				fourthChart.setOption(fourthOption);
			}*/
			fourthChart.setOption(fourthOption, true);
		});
    }
        // xuexi();
	function xuexi(){
		$.get('${ctx}/sys/charts/xuexi?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
			var sumData = data.sumData;
			var pieCountData = [];
			pieCountData.push({name: '党委中心组学习', value: sumData[0]});
			pieCountData.push({name: '党支部集中学习', value: sumData[1]});

			// 基于准备好的dom，初始化echarts实例
			var fifthChart = echarts.init(document.getElementById('fifth'));
            fifthChart.off('click');
			// 指定图表的配置项和数据
			var fifthOption = {
				title: {},
				tooltip: {
					trigger: 'axis'
				},
				legend: {
					bottom: 10,
					left: 'center',
					data: ['数量'],
					icon: 'circle'
				},
				toolbox: {},
				calculable: true,
				xAxis: [
					{
						type: 'category',
						data: ['党委中心组学习', '党支部集中学习'],
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
					left: 50,
					bottom:'13%'
				},
				series: [
					{
						name: '数量',
						type: 'bar',
						data: sumData,
						color: '#5B9BD5',
						label: {
							show: true,
							position: "top"
						}
					}
				]
			};
			// 使用刚指定的配置项和数据显示图表。
			/* if (sumData) {
                 fifthChart.setOption(fifthOption, true);
             }*/
			fifthChart.setOption(fifthOption, true);

			var sixthChart = echarts.init(document.getElementById('sixth'));
			var sixthOption = {
				// title: {text: '数量', x: 'center'},
				tooltip: {
					trigger: 'item',					//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
					formatter: '{a} <br/>{b} : {c} ({d}%)'
				},
				legend: {
					// orient: 'vertical',
					// top: 'middle',
					bottom: 10,
					left: 'center',
					data: ['党委中心组学习', '党支部集中学习'],
					icon: 'circle'
				},
				series: [
					{
						name: '理论学习情况',
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
			/*if (pieCountData) {
                sixthChart.setOption(sixthOption, true);
            }*/
			sixthChart.setOption(sixthOption, true);
		});
    }
        typical();
	function typical(){
		$.get('${ctx}/sys/charts/typicalPerson?'+getDateParam(), function(data) {
			var labelData = [];
			var numData = [];
			for (var i = 0; i < data.length; i++){
				labelData.push(data[i].label);
				numData.push(data[i].num);
			}
			var personChart = echarts.init(document.getElementById('typicalPerson'));
            personChart.off('click');
			var personOption = {
				title: {text: '典型个人', x: 'center'},
				tooltip: {
					trigger: 'axis'
				},
				legend: {
					bottom: 10,
					left: 'center',
					data: ['人数'],
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
					left: 50,
					bottom:'15%'
				},
				series: [
					{
						name: '人数',
						type: 'bar',
						data: numData,
						color: '#5B9BD5',
						label: {
							show: true,
							position: "top"
						}
					}
				]
			};
			personChart.setOption(personOption, true);
			personChart.on("click",function (params) {
				openTypicalPersonDetail(params.name);
			});
		});

		$.get('${ctx}/sys/charts/typicalTeam?'+getDateParam(), function(data) {
			var labelData = [];
			var numData = [];
			for (var i = 0; i < data.length; i++){
				labelData.push(data[i].label);
				numData.push(data[i].num);
			}
			var teamChart = echarts.init(document.getElementById('typicalTeam'));
            teamChart.off('click');
			var teamOption = {
				title: {text: '典型集体', x: 'center'},
				tooltip: {
					trigger: 'axis'
				},
				legend: {
					bottom: 10,
					left: 'center',
					data: ['数量'],
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
					left: 50,
					bottom:'15%'
				},
				series: [
					{
						name: '数量',
						type: 'bar',
						data: numData,
						color: '#5B9BD5',
						label: {
							show: true,
							position: "top"
						}
					}
				]
			};

			teamChart.setOption(teamOption, true);
			teamChart.on("click",function (params) {
				openTypicalTeamDetail(params.name);
			});
		});
	}

		function openTypicalPersonDetail(label){
			var url =  "iframe:${ctx}/affair/affairTypicalPerson/typicalDetail?label="+label+getDateParam();
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		readBookActivity();
		function readBookActivity() {
			$.get('${ctx}/sys/charts/readBookActivity?'+getDateParam(), function(data) {
				var labelData = [];
				var numData = [];
				for (var i = 0; i < data.length; i++){
					labelData.push(data[i].label);
					numData.push(data[i].num);
				}
				var readBookChart = echarts.init(document.getElementById('readBook'));
                readBookChart.off('click');
				var readBookOption = {
					title: {text: '读书活动', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: ['次数'],
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
						left: 50,
						bottom:'13%'
					},
					series: [
						{
							name: '次数',
							type: 'bar',
							data: numData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				readBookChart.setOption(readBookOption, true);
				readBookChart.on("click",function (params) {
					openReadBookDetail(params.name);
				});
			});
		}

		function openReadBookDetail(label) {
			var url =  "iframe:${ctx}/affair/affairActive/readBookDetail?label="+label+getDateParam();
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

	function openTypicalTeamDetail(label){
		var url =  "iframe:${ctx}/affair/affairTypicalTeam/typicalDetail?label="+label+getDateParam();
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}


		literary();
		/*文艺*/
		function literary(){
			$.get('${ctx}/sys/charts/literaryWorks?'+getDateParam(), function(data) {
				var labelData = [];
				var countryData = [];
				var provinceData = [];
				var officeData = [];
				var countyData = [];
				var otherData = [];
				for (var i = 0; i < data.length; i++){
					labelData.push(data[i].label);
					countryData.push(data[i].country);
					provinceData.push(data[i].province);
					officeData.push(data[i].office);
					countyData.push(data[i].county);
					otherData.push(data[i].other);
				}
				var personChart = echarts.init(document.getElementById('literaryWorks'));
                personChart.off('click');
				var personOption = {
					title: {text: '文艺作品', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: ['国家级','省部级','厅局级','县处级','其他'],
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
						left: 50,
						bottom:'13%'
					},
					series: [
						{
							name: '国家级',
							type: 'bar',
							data: countryData,
							color: '#d53940',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '省部级',
							type: 'bar',
							data: provinceData,
							color: '#d5bb38',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '厅局级',
							type: 'bar',
							data: officeData,
							color: '#26d561',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '县处级',
							type: 'bar',
							data: countyData,
							color: '#3d9ad5',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '其他',
							type: 'bar',
							data: otherData,
							color: '#cb24d5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				personChart.setOption(personOption, true);
				personChart.on("click",function (params) {
					openLiteraryWorksDetail(labelData[params.dataIndex],params.seriesName);
				});
			});

			$.get('${ctx}/sys/charts/literaryTalents?'+getDateParam(), function(data) {
				var labelData = [];
				var literatureData = [];
				var photographyData = [];
				var artsData = [];
				var calligraphyData = [];
				var cuttingData = [];
				var moviesData = [];
				var otherData = [];
				for (var i = 0; i < data.length; i++){
					labelData.push(data[i].label);
					literatureData.push(data[i].literature);
					photographyData.push(data[i].photography);
					artsData.push(data[i].arts);
					calligraphyData.push(data[i].calligraphy);
					cuttingData.push(data[i].cutting);
					moviesData.push(data[i].movies);
					otherData.push(data[i].other);
				}
				var personChart = echarts.init(document.getElementById('literaryTalents'));
                personChart.off('click');
				var personOption = {
					title: {text: '文艺人才类型', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: ['文学','摄影','美术','书法','篆刻','影视','其他'],
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
						left: 50,
						bottom:'13%'
					},
					series: [
						{
							name: '文学',
							type: 'bar',
							data: literatureData,
							color: '#d53940',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '摄影',
							type: 'bar',
							data: photographyData,
							color: '#d5bb38',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '美术',
							type: 'bar',
							data: artsData,
							color: '#26d561',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '书法',
							type: 'bar',
							data: calligraphyData,
							color: '#3d9ad5',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '篆刻',
							type: 'bar',
							data: cuttingData,
							color: '#cb24d5',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '影视',
							type: 'bar',
							data: moviesData,
							color: '#cb24d5',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '其他',
							type: 'bar',
							data: otherData,
							color: '#cb24d5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				personChart.setOption(personOption, true);
				personChart.on("click",function (params) {
					openLiteraryTalentsDetail(labelData[params.dataIndex],params.seriesName);
				});
			});

			$.get('${ctx}/sys/charts/talentJoin?'+getDateParam(), function(data) {
				var labelData = [];
				var zgData = [];
				var qggnData = [];
				var zgtlData = [];
				var gxData = [];
				var gxgaData = [];
				var jtgsData = [];
				for (var i = 0; i < data.length; i++){
					labelData.push(data[i].label);
					zgData.push(data[i].zg);
					qggnData.push(data[i].qggn);
					zgtlData.push(data[i].zgtl);
					gxData.push(data[i].gx);
					gxgaData.push(data[i].gxga);
					jtgsData.push(data[i].jtgs);
				}
				var label =['中国文联','全国公安文联','中国铁路文联','广西文联','广西公安文联','集团公司文联'];
				var param =[zgData,qggnData,zgtlData,gxData,gxgaData,jtgsData];
				var seriesData=[];
				var color = ['#2a3fed','#26d561','#3d9ad5','#cb24d5','#d53732','#d53732',];
				for(let index in label) {
					seriesData.push({name:label[index],type: 'bar',data: param[index],color:color[index],label: {show: true,position: "top"}})
				}
				var personChart = echarts.init(document.getElementById('talentsJoin'));
                personChart.off('click');
				var personOption = {
					title: {text: '文艺人才协会', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: -5,
						left: 'center',
						data: label,
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
						left: 50,
						bottom:'13%'
					},
					series: seriesData
				};
				personChart.setOption(personOption, true);
				personChart.on("click",function (params) {
					openTalentJoinDetail(labelData[params.dataIndex],params.seriesName);
				});
			});

		}
		<%--<c:if test="${officeId eq '1' || officeId eq '0'}">
			news();
		</c:if>--%>
    <%--<c:if test="${officeId eq '34'}">
			nncnews();
		</c:if>
		<c:if test="${officeId eq '95'}">
			lzcnews();
		</c:if>
		<c:if test="${officeId eq '156'}">
			bhcnews();
		</c:if>--%>

		if($("#selectType").val() == '2'){
			nncnews();
		}else if($("#selectType").val() == '3'){
			lzcnews();
		}else if($("#selectType").val() == '4'){
			bhcnews();
		}
		function news() {
			$.get('${ctx}/sys/charts/news?'+getDateParam(), function(data) {
				$('#news').show();
				$('#nncnews').hide();
				$("#lzcnews").hide();
				$("#bhcnews").hide();
				var labelData = [];
				var countDataOne = [];
				var countDataTwo = [];
				var countDataThree = [];
				var countDataFour = [];
				var countDataFive = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].unit);

					countDataOne.push(data[i].sumOne);
					countDataTwo.push(data[i].sumTwo);
					countDataThree.push(data[i].sumThree);
					countDataFour.push(data[i].sumFour);
					countDataFive.push(data[i].sumFive);

					// console.log(data[i].unit);
				}
				var label =['中央级','省部级','地市级','各大网络','新媒体平台'];
				var param =[countDataOne,countDataTwo,countDataThree,countDataFour,countDataFive];
				var seriesData=[];
				var color = ['#2a3fed','#26d561','#3d9ad5','#cb24d5','#d53732','#d53732',];
				for(let index in label) {
					seriesData.push({name:label[index],type: 'bar',data: param[index],color:color[index],label: {show: true,position: "top"}})
				}

				// 基于准备好的dom，初始化echarts实例
				var newsChart = echarts.init(document.getElementById('news'));
				newsChart.off('click');
				// 指定图表的配置项和数据
				var newsOption = {
					title: {text: '刊稿稿件', x: 'center'},
					tooltip: {
						trigger: 'axis'
						/*axisPointer: {            // 坐标轴指示器，坐标轴触发有效
							type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
						}*/
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					legend: {
						bottom: -5,
						left: 'center',
						data: label,
						icon: 'circle'
					},
					xAxis: [
						{
							type: 'category',
							data: labelData,
							/*axisTick: {
								alignWithLabel: true
							},
							boundaryGap: true,*/
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
						left: 50,
						bottom:'13%'
					},
					series: seriesData
				};
				newsChart.setOption(newsOption, true);
				newsChart.on('click', function (params) {
					var typr;
					if(params.seriesName == "中央级"){
						typr = "中央级（中央电视台、新华社/中新社、中央人民广播电台、中央级报纸）"
					}else if(params.seriesName == "省部级"){
						typr = "省部级（省卫视、专题、其他）"
					}else if(params.seriesName == "地市级"){
						typr = "地市级";
					}else if(params.seriesName == "各大网络"){
						typr = "各大网络";
					}else if(params.seriesName == "新媒体平台"){
						typr = "新媒体平台";
					}

					console.log(params.seriesName);
					var url = "iframe:${ctx}/affair/affairNews/news?unit="+params.name+getDateParam()+"&typr="+typr;
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});

				})
			});
		}

		function nncnews() {
			$.get('${ctx}/sys/charts/nncNews?'+getDateParam(), function(data) {
				$('#news').hide();
				$('#nncnews').show();
				$("#lzcnews").hide();
				$("#bhcnews").hide();
				var labelData = [];
				var countDataOne = [];
				var countDataTwo = [];
				var countDataThree = [];
				var countDataFour = [];
				var countDataFive = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].unit);

					countDataOne.push(data[i].sumOne);
					countDataTwo.push(data[i].sumTwo);
					countDataThree.push(data[i].sumThree);
					countDataFour.push(data[i].sumFour);
					countDataFive.push(data[i].sumFive);

					// console.log(data[i].unit);
				}
				var label =['中央级','省部级','地市级','各大网络','新媒体平台'];
				var param =[countDataOne,countDataTwo,countDataThree,countDataFour,countDataFive];
				var seriesData=[];
				var color = ['#2a3fed','#26d561','#3d9ad5','#cb24d5','#d53732','#d53732',];
				for(let index in label) {
					seriesData.push({name:label[index],type: 'bar',data: param[index],color:color[index],label: {show: true,position: "top"}})
				}

				// 基于准备好的dom，初始化echarts实例
				var nncnewsChart = echarts.init(document.getElementById('nncnews'));
				nncnewsChart.off('click');
				// 指定图表的配置项和数据
				var nncnewsOption = {
					title: {text: '刊稿稿件', x: 'center'},
					tooltip: {
						trigger: 'axis'
						/*axisPointer: {            // 坐标轴指示器，坐标轴触发有效
							type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
						}*/
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					legend: {
						bottom: -5,
						left: 'center',
						data: label,
						icon: 'circle'
					},
					xAxis: [
						{
							type: 'category',
							data: labelData,
							/*axisTick: {
								alignWithLabel: true
							},
							boundaryGap: true,*/
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
						left: 50,
						bottom:'13%'
					},
					series: seriesData
				};
				nncnewsChart.setOption(nncnewsOption, true);
				nncnewsChart.on('click', function (params) {
					var typr;
					if(params.seriesName == "中央级"){
						typr = "中央级（中央电视台、新华社/中新社、中央人民广播电台、中央级报纸）"
					}else if(params.seriesName == "省部级"){
						typr = "省部级（省卫视、专题、其他）"
					}else if(params.seriesName == "地市级"){
						typr = "地市级";
					}else if(params.seriesName == "各大网络"){
						typr = "各大网络";
					}else if(params.seriesName == "新媒体平台"){
						typr = "新媒体平台";
					}

					console.log(params.seriesName);
					var url = "iframe:${ctx}/affair/affairNews/news?unit="+params.name+getDateParam()+"&typr="+typr;
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});

				})
			});
		}

		function lzcnews() {
			$.get('${ctx}/sys/charts/lzcNews?'+getDateParam(), function(data) {
				$('#news').hide();
				$('#nncnews').hide();
				$("#lzcnews").show();
				$("#bhcnews").hide();
				var labelData = [];
				var countDataOne = [];
				var countDataTwo = [];
				var countDataThree = [];
				var countDataFour = [];
				var countDataFive = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].unit);

					countDataOne.push(data[i].sumOne);
					countDataTwo.push(data[i].sumTwo);
					countDataThree.push(data[i].sumThree);
					countDataFour.push(data[i].sumFour);
					countDataFive.push(data[i].sumFive);

					console.log(data[i]);
				}
				var label =['中央级','省部级','地市级','各大网络','新媒体平台'];
				var param =[countDataOne,countDataTwo,countDataThree,countDataFour,countDataFive];
				var seriesData=[];
				var color = ['#2a3fed','#26d561','#3d9ad5','#cb24d5','#d53732','#d53732',];
				for(let index in label) {
					seriesData.push({name:label[index],type: 'bar',data: param[index],color:color[index],label: {show: true,position: "top"}})
				}

				// 基于准备好的dom，初始化echarts实例
				var lzcnewsChart = echarts.init(document.getElementById('lzcnews'));
				lzcnewsChart.off('click');
				// 指定图表的配置项和数据
				var lzcnewsOption = {
					title: {text: '刊稿稿件', x: 'center'},
					tooltip: {
						trigger: 'axis'
						/*axisPointer: {            // 坐标轴指示器，坐标轴触发有效
							type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
						}*/
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					legend: {
						bottom: -5,
						left: 'center',
						data: label,
						icon: 'circle'
					},
					xAxis: [
						{
							type: 'category',
							data: labelData,
							/*axisTick: {
								alignWithLabel: true
							},
							boundaryGap: true,*/
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
						left: 50,
						bottom:'13%'
					},
					series: seriesData
				};
				lzcnewsChart.setOption(lzcnewsOption, true);
				lzcnewsChart.on('click', function (params) {
					var typr;
					if(params.seriesName == "中央级"){
						typr = "中央级（中央电视台、新华社/中新社、中央人民广播电台、中央级报纸）"
					}else if(params.seriesName == "省部级"){
						typr = "省部级（省卫视、专题、其他）"
					}else if(params.seriesName == "地市级"){
						typr = "地市级";
					}else if(params.seriesName == "各大网络"){
						typr = "各大网络";
					}else if(params.seriesName == "新媒体平台"){
						typr = "新媒体平台";
					}

					console.log(params.seriesName);
					var url = "iframe:${ctx}/affair/affairNews/news?unit="+params.name+getDateParam()+"&typr="+typr;
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});

				})
			});
		}

		function bhcnews() {
			$.get('${ctx}/sys/charts/bhcNews?'+getDateParam(), function(data) {
				$('#news').hide();
				$('#nncnews').hide();
				$("#lzcnews").hide();
				$("#bhcnews").show();
				var labelData = [];
				var countDataOne = [];
				var countDataTwo = [];
				var countDataThree = [];
				var countDataFour = [];
				var countDataFive = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].unit);

					countDataOne.push(data[i].sumOne);
					countDataTwo.push(data[i].sumTwo);
					countDataThree.push(data[i].sumThree);
					countDataFour.push(data[i].sumFour);
					countDataFive.push(data[i].sumFive);


				}
				var label =['中央级','省部级','地市级','各大网络','新媒体平台'];
				var param =[countDataOne,countDataTwo,countDataThree,countDataFour,countDataFive];
				var seriesData=[];
				var color = ['#2a3fed','#26d561','#3d9ad5','#cb24d5','#d53732','#d53732',];
				for(let index in label) {
					seriesData.push({name:label[index],type: 'bar',data: param[index],color:color[index],label: {show: true,position: "top"}})
				}

				// 基于准备好的dom，初始化echarts实例
				var bhcnewsChart = echarts.init(document.getElementById('bhcnews'));
				bhcnewsChart.off('click');
				// 指定图表的配置项和数据
				var bhcnewsOption = {
					title: {text: '刊稿稿件', x: 'center'},
					tooltip: {
						trigger: 'axis'
						/*axisPointer: {            // 坐标轴指示器，坐标轴触发有效
							type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
						}*/
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					legend: {
						bottom: -5,
						left: 'center',
						data: label,
						icon: 'circle'
					},
					xAxis: [
						{
							type: 'category',
							data: labelData,
							/*axisTick: {
								alignWithLabel: true
							},
							boundaryGap: true,*/
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
						left: 50,
						bottom:'13%'
					},
					series: seriesData
				};
				bhcnewsChart.setOption(bhcnewsOption, true);
				bhcnewsChart.on('click', function (params) {
					var typr;
					if(params.seriesName == "中央级"){
						typr = "中央级（中央电视台、新华社/中新社、中央人民广播电台、中央级报纸）"
					}else if(params.seriesName == "省部级"){
						typr = "省部级（省卫视、专题、其他）"
					}else if(params.seriesName == "地市级"){
						typr = "地市级";
					}else if(params.seriesName == "各大网络"){
						typr = "各大网络";
					}else if(params.seriesName == "新媒体平台"){
						typr = "新媒体平台";
					}

					console.log(params.seriesName);
					var url = "iframe:${ctx}/affair/affairNews/news?unit="+params.name+getDateParam()+"&typr="+typr;
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});

				})
			});
		}
		/*七知档案*/
		sevenKnowledge();
		function sevenKnowledge() {
			$.get('${ctx}/sys/charts/sevenKnowledge?'+getDateParam(), function(data) {
				var labelData = [];
				var wdData = [];
				var gzData = [];
				var zdgzData = [];
				for (var i = 0; i < data.length; i++){
					labelData.push(data[i].label);
					wdData.push(data[i].wd);
					gzData.push(data[i].gz);
					zdgzData.push(data[i].zdgz);
				}
				var label =['稳定人群','关注人群','重点关注人群'];
				var param =[wdData,gzData,zdgzData];
				var seriesData=[];
				// var color = ['#3ced13','#FF9900','#FF0000'];
				var color = ['#4e8bff','#d5bb38','#d53732'];
				for(let index in label) {
					seriesData.push({name:label[index],type: 'bar',data: param[index],color:color[index],label: {show: true,position: "top"}})
				}
				var sevenKnowledgeChart = echarts.init(document.getElementById('sevenKnowledge'));
				sevenKnowledgeChart.off('click');
				var personOption = {
					title: {text: '七知档案', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: -5,
						left: 'center',
						data: label,
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
						left: 50,
						bottom:'20%'
					},
					series: seriesData
				};
				sevenKnowledgeChart.setOption(personOption, true);
				sevenKnowledgeChart.on("click",function (params) {
					openSevenKnowledgeDetail(labelData[params.dataIndex],params.seriesName);
				});
			});

		}

		function openLiteraryWorksDetail(company,label){
			var url =  "iframe:${ctx}/affair/affairWenyi/literaryWorks?label="+label+'&ratifyUnit='+company+getDateParam();
			top.$.jBox.open(url, company+label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function openLiteraryTalentsDetail(company,label){
			var url =  "iframe:${ctx}/affair/affairWenhua/literaryTalent?label="+label+'&unit='+company+getDateParam();
			top.$.jBox.open(url, company+label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function openTalentJoinDetail(company,label){
			var url =  "iframe:${ctx}/affair/affairWenhua/talentJoin?label="+label+'&unit='+company+getDateParam();
			top.$.jBox.open(url, company+label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		function openSevenKnowledgeDetail(company,label){
			var url =  "iframe:${ctx}/affair/affairSevenKnowledge/openSevenKnowledgeDetail?label="+label+'&unit='+company+getDateParam();
			top.$.jBox.open(url, company+label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		partyStudy();
		function partyStudy(){
			$.get('${ctx}/sys/charts/partyStudy?'+getDateParam(), function(data) {
				var labelData = [];
				var numData = [];
				for (var i = 0; i < data.length; i++){
					labelData.push(data[i].label);
					numData.push(data[i].num);
				}
				var partyStudyChart = echarts.init(document.getElementById('fifth'));
                partyStudyChart.off('click');
				var partyStudyOption = {
					title: {text: '党委中心组学习', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: ['次数'],
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
								rotate:40,
							}
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
					grid: {
						left: 50,
						bottom:'13%'
					},
					series: [
						{
							name: '次数',
							type: 'bar',
							data: numData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				partyStudyChart.setOption(partyStudyOption, true);
				partyStudyChart.on("click",function (params) {
					openPartyStudyDetail(params.name);
				});
			});
		}

		function openPartyStudyDetail(label){
			var url =  "iframe:${ctx}/affair/affairGroupStudy/partyStudyDetail?label="+label+getDateParam();
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

	$("#reason").change(function(){
		// yuQing($("#reason").val());
	});
		//年度、月份、时间段
		$("#dateType").change(function(){
			if($("#dateType").val() == '2'){//年度
				$('#year').css('display', 'inline-block');
				$('#month').css('display', 'none');
				$('#dateStart').css('display', 'none');
				$('#spanTo').css('display', 'none');
				$('#dateEnd').css('display', 'none');
			}else if($("#dateType").val() == '1'){//月度
				$('#year').css('display', 'none');
				$('#month').css('display', 'inline-block');
				$('#dateStart').css('display', 'none');
				$('#spanTo').css('display', 'none');
				$('#dateEnd').css('display', 'none');
			}else{//时间段
				$('#year').css('display', 'none');
				$('#month').css('display', 'none');
				$('#dateStart').css('display', 'inline-block');
				$('#spanTo').css('display', 'inline-block');
				$('#dateEnd').css('display', 'inline-block');
			}
		});
		//查询按钮
		function selectEcharts(){

			/*if($("#selectType").val() == '1'){
				news();
			}else if($("#selectType").val() == '2'){
				nncnews();
			}else if($("#selectType").val() == '3'){
				lzcnews();
			}else if($("#selectType").val() == '4'){
				bhcnews();
			}*/
			if($("#selectType").val() == '2'){
				nncnews();
			}else if($("#selectType").val() == '3'){
				lzcnews();
			}else if($("#selectType").val() == '4'){
				bhcnews();
			}
			unitReward();
            personnelReward();
			// xuexi();	需求变动
			typical();
			readBookActivity();
			literary();
			partyStudy();
			sevenKnowledge();//统计分析
			// yuQing($("#reason").val());
		}

		/*$("#selectType").change(function(){
			if($("#selectType").val() == '1'){
				news();
			}else if($("#selectType").val() == '2'){
				nncnews();
			}else if($("#selectType").val() == '3'){
				lzcnews();
			}else if($("#selectType").val() == '4'){
				bhcnews();
			}
		});*/
		$("#selectType").change(function(){
			if($("#selectType").val() == '2'){
				nncnews();
			}else if($("#selectType").val() == '3'){
				lzcnews();
			}else if($("#selectType").val() == '4'){
				bhcnews();
			}
		});
	</script>

</body>
</html>