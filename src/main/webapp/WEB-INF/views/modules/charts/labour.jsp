<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工团工作情况</title>
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
			width: 300px;
			height: 550px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/charts/labourIndex">工会保障情况</a></li>
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
		<div>帮扶救助-助医
		<%--<select id="zk" style="width: 120px;">
			<option value=""></option>
			<option value="0">集团公司</option>
			<option value="1">公安局</option>
			<option value="2">公安处</option>
		</select>--%>
		</div>
		<div class="inner-div">
			<div id="first" class="charts-div"></div>
			<div id="second" class="charts-div"></div>
			<div id="third" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>帮扶救助-助学</div>
		<div class="inner-div">
			<div id="studyAid" class="charts-div"></div>
			<div id="studyPie" class="charts-div"></div>
		</div>
	</div>

	
	<div class="content-div">
		<div>
			<select id="reason" style="width: 120px;">
				<option value="1">慰问工作</option>
				<%--<option value="2">基础建设</option>
				<option value="3">固资管理</option>
				<option value="4">组织建设</option>
				<option value="5">民警休养</option>
				<option value="6">女警风采</option>
				<option value="7">幼儿补助</option>
				<option value="8">工会活动</option>
				<option value="9">工会表彰奖励</option>
				<option value="10">团委基础台账</option>
				<option value="11">团委表彰奖励</option>
				<option value="12">品牌创建</option>
				<option value="13">团内活动</option>
				<option value="14">媒体宣传</option>
				<option value="15">调研简报</option>
				<option value="16">关工委工作</option>
				<option value="17">文艺活动</option>--%>
				<option value="18">健康体检</option>
				<option value="8">工会活动</option>

			</select>
		</div>
		<div class="inner-div" style="margin-top:5px;">
			<div id="fourth" class="charts-div"></div>
			<div id="fifth" class="charts-div"></div>
			<div id="sixth" class="charts-div"></div>
		</div>
	</div>
	
	<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">

		function getDateParam() {
			var dateType = "&dateType=" + $("#dateType").val();
			var year = "&year=" + $("#year").val();
			var dateStart = "&dateStart=" + $("#dateStart").val();
			var dateEnd = "&dateEnd=" + $("#dateEnd").val();
			var month = "&month=" + $("#month").val();
			var dataParam = dateType + year + dateStart + dateEnd + month;
			return dataParam;
		}
		//一进页面默认按年度查询
		$("#dateType").val(2);

	bangfu();
	function bangfu(){
		$.get('${ctx}/sys/charts/bangfu?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
			var moneyData = data.moneyData;
			var countData = data.countData;
			var labelData = data.labelData;
			var pieCountData = [];
			//pieCountData.push({name : '助医', value: countData[0]});
			//pieCountData.push({name : '助困', value: countData[1]});
			//pieCountData.push({name : '助学', value: countData[2]});

			var pieMoneyData = [];
			//pieMoneyData.push({name : '助医', value: moneyData[0]});
			//pieMoneyData.push({name : '助困', value: moneyData[1]});
			//pieMoneyData.push({name : '助学', value: moneyData[2]});
            if(labelData != null && labelData != undefined){
                for (var i = 0; i < labelData.length; i++) {
                    pieCountData.push({name : labelData[i], value: countData[i]});
                    pieMoneyData.push({name : labelData[i], value: moneyData[i]});
                }

            }
			// 基于准备好的dom，初始化echarts实例
			var firstChart = echarts.init(document.getElementById('first'));
			// 指定图表的配置项和数据
			var firstOption = {
				title: {text: '助医情况', x: 'center'},
				tooltip : {
					trigger: 'axis'
				},
				legend: {
					/*y:'top',
					x:'center',*/
					padding:[0,0,500,0],
					bottom: 10,
					left: 'center',
					data:['人数','金额'],
					icon: 'circle'
				},
				toolbox: {

				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						data : labelData,
						axisLabel: {
							//  让x轴文字方向为竖向
							interval: 0,
							/*formatter: function(labelData) {
								return labelData.split('').join('\n')
							}*/
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
					left:50
				},
				series : [
					{
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
					{
						name:'金额',
						type:'bar',
						data: moneyData,
						color:'#ED7D31',
						label:{
							show:true,
							position:"top"
						}
					}
				]
			};
			// 使用刚指定的配置项和数据显示图表。
			/*if (moneyData) {
				firstChart.setOption(firstOption, true);
			}*/
			firstChart.setOption(firstOption, true);
			firstChart.off("click");
			firstChart.on("click",function(params){
				openMedicalAidDetail(params.name);
			});

			var secondChart = echarts.init(document.getElementById('second'));
			var secondOption = {
				title: {text: '人数', x: 'center'},
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
						name: '帮扶救助人数情况',
						type: 'pie',
						selectedMode: 'single',
						radius: [0, '65%'],
						center: ["53%", "53%"],
						label: {
							normal: {
								position: 'outside',
								formatter: '{d}%',
								show: true,     //展示
							},
							emphasis: {    //文本样式
								show: true,    //展示
								textStyle: {    //文本样式
									fontSize: '14',
									fontWeight: '600',
								}
							}
						},
						labelLine: {
							normal: {
								show: true,
								length:5
							}
						},
						data: pieCountData
					}
				]
			};
			secondChart.setOption(secondOption, true);
			secondChart.off("click");
			secondChart.on("click",function(params){
				openMedicalAidDetail(params.name);
			});

			var thirdChart = echarts.init(document.getElementById('third'));
			var thirdOption = {
				title: {text: '金额', x: 'center'},
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
						name: '帮扶救助金额情况',
						type: 'pie',
						selectedMode: 'single',
						radius: [0, '65%'],
						center: ["51%", "53%"],
						label: {
							normal: {
								show: true,
								position: 'outside',
								formatter: '{d}%'
							},
							emphasis: {    //文本样式
								show: true,    //展示
								textStyle: {    //文本样式
									fontSize: '14',
									fontWeight: '600',
								}
							}
						},
						labelLine: {
							normal: {
								show: true,
								length:5
							},


						},
						data: pieMoneyData
					}
				]
			};
			/*if (pieMoneyData) {
				thirdChart.setOption(thirdOption, true);
			}*/
			thirdChart.setOption(thirdOption, true);
			thirdChart.off("click");
			thirdChart.on("click",function(params){
				openMedicalAidDetail(params.name);
			});
		});
	}
	weiWen($("#reason").val());
	function weiWen(flag) {
		if (flag == 1) {
			$.get('${ctx}/sys/charts/weiwen-column?id=${id}'+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				var labelData = data.labelData;
				//var sumData = data.sumData;
				var countData = data.countData;
				var pieCountData = [];
				//var pieSumData = [];

                if(labelData != null && labelData != undefined){
                    for (var i = 0; i < labelData.length; i++) {
                        pieCountData.push({name: labelData[i], value: countData[i]});
                        //pieSumData.push({name: labelData[i], value: sumData[i]})
                    }
                }
				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
					title: {text: '慰问工作', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						padding:[0,0,500,0],
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
								//  让x轴文字方向为竖向
								interval: 0,
								/*formatter: function(labelData) {
                                    return labelData.split('').join('\n')
                                }*/
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
						}/*,
						{
							name: '慰问金',
							type: 'bar',
							data: sumData,
							color: '#ED7D31',
							label: {
								show: true,
								position: "top"
							}
						}*/
					]
				};
				fourthChart.setOption(fourthOption, true);

				fourthChart.off("click");
				fourthChart.on("click",function(params){
					openSympathyDetail(params.name);
				});

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
					//title: {text: '人数', x: 'center'},
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
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								}
							},
							data: pieCountData
						}
					]
				};
				fifthChart.setOption(fifthOption, true);
				fifthChart.off("click");
				fifthChart.on("click",function(params){
					openSympathyDetail(params.name);
				});


				/*var sixthChart = echarts.init(document.getElementById('sixth'));
				var sixthOption = {
					title: {text: '慰问金', x: 'center'},
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
							name: '慰问金',
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
							data: pieSumData
						}
					]
				};
				sixthChart.setOption(sixthOption, true);
				$("#sixth").show();*/
			});
		}else if (flag == 2) {
			$.get('${ctx}/sys/charts/xzy?id=${id}'+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&monthStart="+$("#monthStart").val()+"&monthEnd="+$("#monthEnd").val(), function (data) {
				var sumData = data.sumData;
				var pieCountData = [];
				pieCountData.push({name: '民警小家建设', value: sumData[0]});
				pieCountData.push({name: '小种养', value: sumData[1]});

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
					title: {},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: ['基础建设次数'],
						icon: 'circle'
					},
					toolbox: {},
					calculable: true,
					xAxis: [
						{
							type: 'category',
							data: ['民警小家建设', '小种养']
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
					series: [
						{
							name: '基础建设次数',
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
				/*if (sumData) {
					fourthChart.clear();
					fourthChart.setOption(fourthOption, true);
				}*/
				fourthChart.clear();
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
					// title: {text: '基础建设次数', x: 'center'},
					tooltip: {
						trigger: 'item',					//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
						formatter: '{a} <br/>{b} : {c} ({d}%)'
					},
					legend: {
						// orient: 'vertical',
						// top: 'middle',
						bottom: 10,
						left: 'center',
						data: ['民警小家建设', '小种养'],
						icon: 'circle'
					},
					series: [
						{
							name: '基础建设情况',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (pieCountData) {
					fifthChart.clear();
					fifthChart.setOption(fifthOption, true);
				}*/
				fifthChart.clear();
				fifthChart.setOption(fifthOption, true);
				/*if (sumData) {
					// $("#sixth").css("display","none");
					$("#sixth").hide();
				}*/
				$("#sixth").hide();
			});
		}else if(flag == 3){
			$.get('${ctx}/sys/charts/gz?id=${id}&flag='+flag+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&monthStart="+$("#monthStart").val()+"&monthEnd="+$("#monthEnd").val(), function(data) {
				var labelData = data.labelData;
				var sumData = data.sumData;
				var countData = data.countData;
				var pieCountData = [];
				var pieSumData = [];
				if(labelData != null && labelData != undefined){
					for (var i = 0; i < labelData.length; i++) {
						pieCountData.push({name: labelData[i], value: countData[i]});
						pieSumData.push({name: labelData[i], value: sumData[i]})
					}
				}
				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
					title: {},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: ['次数', '金额'],
						icon: 'circle'
					},
					toolbox: {},
					calculable: true,
					xAxis: [
						{
							type: 'category',
							data: labelData
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
							name: '次数',
							type: 'bar',
							data: countData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '金额',
							type: 'bar',
							data: sumData,
							color: '#ED7D31',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				// 使用刚指定的配置项和数据显示图表。
				/*if (sumData) {
					fourthChart.setOption(fourthOption, true);
				}*/
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
					title: {text: '次数', x: 'center'},
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
							name: '次数',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (countData) {
					fifthChart.setOption(fifthOption, true);
				}*/
				fifthChart.setOption(fifthOption, true);

				var sixthChart = echarts.init(document.getElementById('sixth'));
				var sixthOption = {
					title: {text: '金额', x: 'center'},
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
							name: '金额',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieSumData
						}
					]
				};
				/*if (sumData) {
					sixthChart.setOption(sixthOption, true);
					$("#sixth").show();
				}*/
				sixthChart.setOption(sixthOption, true);
				$("#sixth").show();
			});
		}else if (flag == 4){
			$.get('${ctx}/sys/charts/guzi?id=${id}&flag='+flag+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&monthStart="+$("#monthStart").val()+"&monthEnd="+$("#monthEnd").val(), function(data) {
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
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							name:'组织建设情况',
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
				fourthChart.setOption(fourthOption, true);


				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
							name: '组织建设情况',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data:pieData
						}
					]
				};
				/*if (labelData.length > 0 && pieData.length > 0) {
					secondChart.setOption(secondOption, true);
				}*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
			}else if(flag == 5){
			$.get('${ctx}/sys/charts/guzi?id=${id}&flag='+flag, function(data) {
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
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							name:'民警休养情况',
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
					fourthChart.clear();
					fourthChart.setOption(fourthOption, true);
				}*/
				fourthChart.clear();
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));

				var fifthOption = {
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
							name: '民警休养情况',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieData
						}
					]
				};
				/*if (labelData.length > 0 && pieData.length > 0) {
					fifthChart.setOption(fifthOption, true);
					$("#sixth").hide();
				}*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}else if(flag == 6){
			$.get('${ctx}/sys/charts/guzi?id=${id}&flag='+flag, function(data) {
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
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							name:'女警风采情况',
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
					fourthChart.clear();
					fourthChart.setOption(fourthOption, true);
				}*/
				fourthChart.clear();
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));

				var fifthOption = {
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
							name: '女警风采情况',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieData
						}
					]
				};
				/*if (labelData.length > 0 && pieData.length > 0) {
					fifthChart.setOption(fifthOption, true);
					$("#sixth").hide();
				}*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}else if(flag == 7){
			$.get('${ctx}/sys/charts/gz?id=${id}&flag='+flag+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&monthStart="+$("#monthStart").val()+"&monthEnd="+$("#monthEnd").val(), function(data) {
				var labelData = data.labelData;
				var sumData = data.sumData;
				var countData = data.countData;
				var pieCountData = [];
				var pieSumData = [];
				if(labelData != null && labelData != undefined){
					for (var i = 0; i < labelData.length; i++) {
						pieCountData.push({name: labelData[i], value: countData[i]});
						pieSumData.push({name: labelData[i], value: sumData[i]})
					}
				}

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
					title: {},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data: ['次数', '金额'],
						icon: 'circle'
					},
					toolbox: {},
					calculable: true,
					xAxis: [
						{
							type: 'category',
							data: labelData
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
							name: '次数',
							type: 'bar',
							data: countData,
							color: '#5B9BD5',
							label: {
								show: true,
								position: "top"
							}
						},
						{
							name: '金额',
							type: 'bar',
							data: sumData,
							color: '#ED7D31',
							label: {
								show: true,
								position: "top"
							}
						}
					]
				};
				// 使用刚指定的配置项和数据显示图表。
				/*if (sumData) {
					fourthChart.setOption(fourthOption, true);
				}*/
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
					title: {text: '次数', x: 'center'},
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
							name: '次数',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (countData) {
					fifthChart.setOption(fifthOption, true);
				}*/
				fifthChart.setOption(fifthOption, true);

				var sixthChart = echarts.init(document.getElementById('sixth'));
				var sixthOption = {
					title: {text: '金额', x: 'center'},
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
							name: '金额',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '65%'],
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieSumData
						}
					]
				};
				/*if (sumData) {
					sixthChart.setOption(sixthOption, true);
					$("#sixth").show();
				}*/
				sixthChart.setOption(sixthOption, true);
				$("#sixth").show();
			});
		}else if(flag == 8){
			$.get('${ctx}/sys/charts/huodong?id=${id}&flag='+flag, function(data) {
				var sumData = data.sumData;
				var pieCountData = [];
				pieCountData.push({name: '活动报名', value: sumData[0]});
				pieCountData.push({name: '活动记录', value: sumData[1]});

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							data: ['活动报名', '活动记录']
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
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
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
						data: ['活动报名', '活动记录'],
						icon: 'circle'
					},
					series: [
						{
							name: '工会活动情况',
							type: 'pie',
							selectedMode: 'single',
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (pieCountData) {
                    sixthChart.setOption(sixthOption, true);
                }*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}else if(flag == 9){
			$.get('${ctx}/sys/charts/biaozhang?id=${id}&flag='+flag, function(data) {
				var sumData = data.sumData;
				var pieCountData = [];
				pieCountData.push({name: '集体表彰', value: sumData[0]});
				pieCountData.push({name: '个人表彰', value: sumData[1]});

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							data: ['集体表彰', '个人表彰']
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
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
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
						data: ['集体表彰', '个人表彰'],
						icon: 'circle'
					},
					series: [
						{
							name: '工会表彰情况',
							type: 'pie',
							selectedMode: 'single',
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (pieCountData) {
                    sixthChart.setOption(sixthOption, true);
                }*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}else if(flag == 11){
			$.get('${ctx}/sys/charts/twbiaozhang?id=${id}&flag='+flag, function(data) {
				var sumData = data.sumData;
				var pieCountData = [];
				pieCountData.push({name: '集体表彰', value: sumData[0]});
				pieCountData.push({name: '个人表彰', value: sumData[1]});

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							data: ['集体表彰', '个人表彰']
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
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
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
						data: ['集体表彰', '个人表彰'],
						icon: 'circle'
					},
					series: [
						{
							name: '团委表彰情况',
							type: 'pie',
							selectedMode: 'single',
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (pieCountData) {
                    sixthChart.setOption(sixthOption, true);
                }*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}else if(flag == 12){
			$.get('${ctx}/sys/charts/guzi?id=${id}&flag='+flag, function(data) {
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
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							name:'品牌创建情况',
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
				fourthChart.setOption(fourthOption, true);


				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
							name: '品牌创建情况',
							type: 'pie',
							selectedMode: 'single',
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data:pieData
						}
					]
				};
				/*if (labelData.length > 0 && pieData.length > 0) {
					secondChart.setOption(secondOption, true);
				}*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();

			});
		}else if(flag == 13){
			$.get('${ctx}/sys/charts/tnhuodong?id=${id}&flag='+flag, function(data) {
				var sumData = data.sumData;
				var pieCountData = [];
				pieCountData.push({name: '活动报名', value: sumData[0]});
				pieCountData.push({name: '活动记录', value: sumData[1]});

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							data: ['活动报名', '活动记录']
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
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
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
						data: ['活动报名', '活动记录'],
						icon: 'circle'
					},
					series: [
						{
							name: '团内活动情况',
							type: 'pie',
							selectedMode: 'single',
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (pieCountData) {
                    sixthChart.setOption(sixthOption, true);
                }*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}else if(flag == 15){
			$.get('${ctx}/sys/charts/jianbao?id=${id}&flag='+flag, function(data) {
				var sumData = data.sumData;
				var pieCountData = [];
				pieCountData.push({name: '思想状况分析', value: sumData[0]});
				pieCountData.push({name: '事迹简报', value: sumData[1]});
				pieCountData.push({name: '调研文章', value: sumData[2]});

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							data: ['思想状况分析', '事迹简报', '调研文章']
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
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
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
						data: ['思想状况分析', '事迹简报', '调研文章'],
						icon: 'circle'
					},
					series: [
						{
							name: '调研简报',
							type: 'pie',
							selectedMode: 'single',
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (pieCountData) {
                    sixthChart.setOption(sixthOption, true);
                }*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}else if(flag == 16){
			$.get('${ctx}/sys/charts/gongwei?id=${id}&flag='+flag, function(data) {
				var sumData = data.sumData;
				var pieCountData = [];
				pieCountData.push({name: '工作信息', value: sumData[0]});
				pieCountData.push({name: '工作总结', value: sumData[1]});

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							data: ['工作信息', '工作总结']
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
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
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
						data: ['工作信息', '工作总结'],
						icon: 'circle'
					},
					series: [
						{
							name: '工委工作',
							type: 'pie',
							selectedMode: 'single',
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (pieCountData) {
                    sixthChart.setOption(sixthOption, true);
                }*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}else if(flag == 17){
			$.get('${ctx}/sys/charts/wenyi?id=${id}&flag='+flag, function(data) {
				var sumData = data.sumData;
				var pieCountData = [];
				pieCountData.push({name: '警官艺术团', value: sumData[0]});
				pieCountData.push({name: '体协', value: sumData[1]});

				// 基于准备好的dom，初始化echarts实例
				var fourthChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var fourthOption = {
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
							data: ['警官艺术团', '体协']
						}
					],
					yAxis: [
						{
							type: 'value'
						}
					],
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
				fourthChart.setOption(fourthOption, true);

				var fifthChart = echarts.init(document.getElementById('fifth'));
				var fifthOption = {
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
						data: ['警官艺术团', '体协'],
						icon: 'circle'
					},
					series: [
						{
							name: '文艺活动',
							type: 'pie',
							selectedMode: 'single',
							center: ["51%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '14',
										fontWeight: '600',
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								},


							},
							data: pieCountData
						}
					]
				};
				/*if (pieCountData) {
                    sixthChart.setOption(sixthOption, true);
                }*/
				fifthChart.setOption(fifthOption, true);
				$("#sixth").hide();
			});
		}
    }
        function tiJian() {
		$.get('${ctx}/sys/charts/tiJianType?id=${id}'+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
			var countData = [];
			var labelData = [];
			for (var i = 0;i<data.length;i++){
				countData.push(data[i].num);
				labelData.push(data[i].label);
			}
			// countData = data.countData;
			// labelData = data.labelData;

			// 基于准备好的dom，初始化echarts实例
			var fourthChart = echarts.init(document.getElementById('fourth'));
			// 指定图表的配置项和数据
			var fourthOption = {
				title: {text: '健康体检', x: 'center'},
				tooltip: {
					trigger: 'axis'
				},
				legend: {
					padding:[0,0,500,0],
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
							interval: 0,
							/*formatter:function(value)
							{
								return value.split("").join("\n");
							}*/
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
			fourthChart.setOption(fourthOption, true);
			fourthChart.off("click");
			fourthChart.on("click",function(params){
				openHealthCheckup(params.name);
			});

		});
		$.get('${ctx}/sys/charts/tiJian?id=${id}'+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
			var labelData = ['参检','未参检'];
			var countData = [];
			var pieCountData = [];
			countData.push(data.tjNum);
			countData.push(data.unTjNum);
			pieCountData.push({name: labelData[0], value: countData[0]});
			pieCountData.push({name: labelData[1], value: countData[1]});

			var fifthChart = echarts.init(document.getElementById('fifth'));
			var fifthOption = {
				title: {text: '参检率', x: 'center'},
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
						radius: [0, '65%'],
						center: ["51%", "53%"],
						label: {
							normal: {
								show: true,
								position: 'outside',
								formatter: '{d}%'
							},
							emphasis: {    //文本样式
								show: true,    //展示
								textStyle: {    //文本样式
									fontSize: '14',
									fontWeight: '600',
								}
							}
						},
						labelLine: {
							normal: {
								show: true,
								length:5

							}
						},
						data: pieCountData
					}
				]
			};
			fifthChart.setOption(fifthOption, true);
			fifthChart.off("click");
			fifthChart.on("click",function (params) {
				openHealthReferenceDetail(params.name);
			});
		});
    }
        $("#reason").change(function(){
		if($("#reason").val() == '1'){
			weiWen($("#reason").val());
		}else if ($("#reason").val() == '8') {
			labourActivity();
		}else {
			tiJian();
		}
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
		studyAid();
		bangfu();
		if($("#reason").val() == '1'){
			weiWen($("#reason").val());
		}else if ($("#reason").val() == '8') {
			labourActivity();
		}else {
			tiJian();
		}
	}

	studyAid();
	function studyAid() {
		/**/
			$.get('${ctx}/sys/charts/studyAid?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				var countData = [];
				var labelData = [];
				for(var i =0 ;i < data.length; i++){
					labelData.push(data[i].label);
					countData.push({name:data[i].label,value:data[i].num});
				}
				var studyAidChart = echarts.init(document.getElementById('studyAid'));

				// 指定图表的配置项和数据
				var studyAidOption = {
					// ----  标题 -----
					title: {text: '助学情况', x: 'center'},
					// ---- legend ----
					legend: {
						padding:[0,0,500,0],
						type: 'plain',  // 图列类型，默认为 'plain'
						top: 'bottom',  // 图列相对容器的位置 top\bottom\left\right
						selected: {
							// '销量': true  // 图列选择，图形加载出来会显示选择的图列，默认为true
						},
						textStyle: {  // 图列内容样式
							color: '#fff',  // 字体颜色
							backgroundColor: 'black'  // 字体背景色
						},
						tooltip: {  // 图列提示框，默认不显示
							show: true,
							color: 'red'
						},
						data: [   // 图列内容
							{
								name: '人数',
								icon: 'circle',
								textStyle: {
									color: 'blue',  // 单独设置某一个图列的颜色
									backgroundColor: '#fff' // 单独设置某一个图列的字体背景色
								}
							}
						]
					},
					// ---  提示框 ----
					tooltip: {
						show: true,   // 是否显示提示框，默认为true
						trigger: 'item', // 数据项图形触发
						axisPointer: {   // 指示样式
							type: 'shadow',
							axis: 'auto'
						},
						padding: 5,
						textStyle: {   // 提示框内容的样式
							color: '#fff'
						}
					},
					//  ------  X轴 ------
					xAxis: {
						show: true,  // 是否显示
						position: 'bottom',  // x轴的位置
						offset: 0, // x轴相对于默认位置的偏移
						type: 'category',   // 轴类型， 默认为 'category'
						name: '',    // 轴名称
						nameLocation: 'end',  // 轴名称相对位置
						nameTextStyle: {   // 坐标轴名称样式
							color: 'black',
							padding: [5, 0, 0, -5]
						},
						nameGap: 15, // 坐标轴名称与轴线之间的距离
						nameRotate: 0,  // 坐标轴名字旋转

						axisLabel: {    // 坐标轴标签
							show: true,  // 是否显示
							inside: false, // 是否朝内
							rotate: 0, // 旋转角度
							margin: 5, // 刻度标签与轴线之间的距离
							color: 'black',  // 默认取轴线的颜色
							interval: 0,
							/*formatter: function(labelData) {
								return labelData.split('').join('\n')
							}*/
							rotate:40
						},
						splitLine: {    // gird区域中的分割线
							show: false,  // 是否显示
							lineStyle: {
								// color: 'red',
								// width: 1,
								// type: 'solid'
							}
						},
						splitArea: {    // 网格区域
							show: false  // 是否显示，默认为false
						},
						data: labelData
					},
					//   ------   y轴  ----------
					yAxis: {
						show: true,  // 是否显示
						position: 'left', // y轴位置
						offset: 0, // y轴相对于默认位置的偏移
						type: 'value',  // 轴类型，默认为 ‘category’
						name: '人数',   // 轴名称
						nameLocation: 'end', // 轴名称相对位置value
						nameTextStyle: {    // 坐标轴名称样式
							color: '#fff',
							padding: [5, 0, 0, 5]  // 坐标轴名称相对位置
						},
						nameGap: 15, // 坐标轴名称与轴线之间的距离
						nameRotate: 270,  // 坐标轴名字旋转


						splitLine: {    // gird 区域中的分割线
							show: true,   // 是否显示
							lineStyle: {
								color: '#666',
								width: 1,
								type: 'dashed'
							}
						},
						splitArea: {     // 网格区域
							show: false   // 是否显示，默认为false
						}
					},
					//  -------   内容数据 -------
					series: [
						{
							name: '人数',      // 序列名称
							type: 'bar',      // 类型
							legendHoverLink: true,  // 是否启用图列 hover 时的联动高亮
							label: {   // 图形上的文本标签
								show: false,
								position: 'insideTop', // 相对位置
								rotate: 0,  // 旋转角度
								color: '#eee'
							},

							barWidth: 20,  // 柱形的宽度
							barCategoryGap: '20%',  // 柱形的间距
							data: countData
						}
					]
				};
				studyAidChart.setOption(studyAidOption, true);
				studyAidChart.on('click', function (params) {
					// 控制台打印数据的名称
					openStudyAidDetail(params.name);
				});

				var studyPieChart = echarts.init(document.getElementById('studyPie'));
				var studyPieOption = {
					title: {text: '人数', x: 'center'},
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
							name: '助学',
							type: 'pie',
							selectedMode: 'single',
							radius: [0, '60%'],
							center: ["50%", "53%"],
							label: {
								normal: {
									show: true,
									position: 'outside',
									formatter: '{d}%'
								},
								emphasis: {    //文本样式
									show: true,    //展示
									textStyle: {    //文本样式
										fontSize: '13',
										fontWeight: '600'
									}
								}
							},
							labelLine: {
								normal: {
									show: true,
									length:5
								}
							},
							data: countData
						}
					]
				};
				studyPieChart.setOption(studyPieOption, true);
				studyPieChart.off("click");
				studyPieChart.on("click",function(params){
					// openZhuXuePieDetail(params.name);
					openStudyAidDetail(params.name);
				});
			});
		}

		/*助学明细*/
		function openStudyAidDetail(label) {
			var url = "iframe:${ctx}/affair/affairZxInfo/studyAidDetail?label="+label+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		/*工会活动*/
		function labourActivity() {
			$.get('${ctx}/sys/charts/labourActivity?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
				var labelData = ['南宁处','北海处','柳州处','公安局'];
				var countData = [];
				for (var i = 0; i < data.length; i++){
					countData.push(data[i].num);
				}

				// 基于准备好的dom，初始化echarts实例
				var activityChart = echarts.init(document.getElementById('fourth'));
				// 指定图表的配置项和数据
				var activityOption = {
					title: {text: '工会活动', x: 'center'},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						padding:[0,0,500,0],
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
				// 使用刚指定的配置项和数据显示图表。
				/* if (sumData) {
                     fifthChart.setOption(fifthOption, true);
                 }*/
				activityChart.setOption(activityOption, true);
				$("#sixth").hide();
				var fifthChart = echarts.init(document.getElementById('fifth'));
				fifthChart.clear();
				activityChart.off('click');
				activityChart.on('click', function (params) {
					// 控制台打印数据的名称
					openActivityDetail(params.name);
				});
			});
		}
		/*工会活动明细*/
		function openActivityDetail(label) {
			var url = "iframe:${ctx}/affair/affairGhActivityRecord/activityDetail?label="+label+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		/*健康体检病史种类明细*/
		function openHealthCheckup(label){
			var url = "iframe:${ctx}/affair/affairHealthCheckup/healthCheckupDetail?label="+label+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		/*健康体检参见率明细*/
		function openHealthReferenceDetail(label) {
			if (label == '参检') {
				var url = "iframe:${ctx}/affair/affairHealthCheckup/healthReferenceDetail?label="+label+getDateParam()
			}else {
				var url = "iframe:${ctx}/personnel/personnelBase/healthReferenceDetail?label="+label+getDateParam()
			}
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		function openMedicalAidDetail(label) {
			var url = "iframe:${ctx}/affair/affairZyInfo/medicalAidDetail?label="+label+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		function openSympathyDetail(label) {
			var url = "iframe:${ctx}/affair/affairConsolationWorkInfo/sympathyDetail?label="+label+getDateParam();
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

	</script>
	<script type="text/javascript">


	</script>
</body>
</html>