<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>纪检监察情况</title>
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
		<li class="active"><a href="${ctx}/sys/charts/monitor?id=${id}">纪检监察情况</a></li>
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
		<div>
			<select id="reason">
				<option value="1">信访举报</option>
				<option value="3">谈话函询</option>
				<option value="4">廉政鉴定</option>
				<option value="5">廉政监督</option>
			</select>
		</div>
		<div class="inner-div" style="margin-top: 5px;">
			<div id="first" class="charts-div"></div>
			<div id="second" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>纪律处分</div>
		<div class="inner-div" style="margin-top: 5px;">
			<div id="third" class="charts-div"></div>
			<div id="forth" class="charts-div"></div>
			<div id="fifth" class="charts-div"></div>
			<div id="sixth" class="charts-div"></div>
			<div id="seventh" class="charts-div"></div>
			<div id="eighth" class="charts-div"></div>
		</div>
	</div>
	<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">

        $("#dateType").val(2);
        firstTouSu();
        function firstTouSu(){
            $.get('${ctx}/sys/charts/firstTouSuColumn?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
                var labelData = data.labelData;
                var completeData = data.completeData;
                var totalData = data.totalData;
                // 基于准备好的dom，初始化echarts实例
                var firstChart = echarts.init(document.getElementById('first'));
                firstChart.off('click');
                // 指定图表的配置项和数据
                var firstOption = {
                    title : {

                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        bottom: 10,
                        left: 'center',
                        data:['南宁铁路公安局纪委','南宁公安处纪委','柳州公安处纪委','北海公安处纪委'],
                        icon: 'circle'
                    },
                    toolbox: {

                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'category',
                            data : ['南宁铁路公安局纪委','南宁公安处纪委','柳州公安处纪委','北海公安处纪委'],
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
						bottom:'15%'
					},
                    series : [
                        {
                            name:'本级自受理',
                            type:'bar',
                            data: totalData,
                            color:'#5B9BD5',
                            label:{
                                show:true,
                                position:"top"
                            }
                        },
                        {
                            name:'其他',
                            type:'bar',
                            data: completeData,
                            color:'#5B9BD5',
                            label:{
                                show:true,
                                position:"top"
                            }
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                /*if (labelData) {
                    firstChart.setOption(firstOption, true);
                }*/
                firstChart.setOption(firstOption, true);
				$("#second").show();
                firstChart.on('click', function (params) {
					var sdUnit = "";
					if ("南宁铁路公安局纪委" == params.name) {
						sdUnit = "1";
					}else if ("南宁公安处纪委" == params.name){
						sdUnit = "2";
					} else if ("柳州公安处纪委" == params.name){
						sdUnit = "3";
					}else if ("北海公安处纪委" == params.name){
						sdUnit = "4";
					}
					var url = "iframe:${ctx}/sys/charts/echartsIsCheckTypeFindPage?sdUnit="+sdUnit+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val()
					top.$.jBox.open(url, params.name,900,850,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
            });
        }
		secondTouSu();
		function secondTouSu(){
			$.get('${ctx}/sys/charts/secondTouSuColumn?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				$("#second").show();
				var labelData = data.labelData;
				var completeData = data.completeData;
				var totalData = data.totalData;
				// 基于准备好的dom，初始化echarts实例
				var secondChart = echarts.init(document.getElementById('second'));
				secondChart.off('click');
				// 指定图表的配置项和数据
				var secondOption = {
					title : {

					},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data:['南宁铁路公安局纪委','南宁公安处纪委','柳州公安处纪委','北海公安处纪委'],
						icon: 'circle'
					},
					toolbox: {

					},
					calculable : true,
					xAxis : [
						{
							type : 'category',
							data : ['南宁铁路公安局纪委','南宁公安处纪委','柳州公安处纪委','北海公安处纪委'],
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
						bottom:'15%'
					},
					series : [
						{
							name:'违反党政纪行为',
							type:'bar',
							data: labelData,
							color:'#5B9BD5',
							label:{
								show:true,
								position:"top"
							}
						},
						{
							name:'涉法行为',
							type:'bar',
							data: totalData,
							color:'#5B9BD5',
							label:{
								show:true,
								position:"top"
							}
						},
						{
							name:'违反警纪行为',
							type:'bar',
							data: completeData,
							color:'#5B9BD5',
							label:{
								show:true,
								position:"top"
							}
						}
					]
				};
				// 使用刚指定的配置项和数据显示图表。
				/*if (labelData) {
                    firstChart.setOption(firstOption, true);
                }*/
				secondChart.setOption(secondOption, true);
				secondChart.on('click', function (params) {
					var sdUnit = "";
					if ("南宁铁路公安局纪委" == params.name) {
						sdUnit = "1";
					}else if ("南宁公安处纪委" == params.name){
						sdUnit = "2";
					} else if ("柳州公安处纪委" == params.name){
						sdUnit = "3";
					}else if ("北海公安处纪委" == params.name){
						sdUnit = "4";
					}
					var url = "iframe:${ctx}/sys/charts/echartsQuestionTypeFindPage?sdUnit="+sdUnit+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val()
					top.$.jBox.open(url, params.name,900,850,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		firstJiLv();
		function firstJiLv(){
			$.get('${ctx}/sys/charts/firstJiLvColumn?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				var labelData = data.labelData;
				var totalData = data.totalData;
				// 基于准备好的dom，初始化echarts实例
				var thirdChart = echarts.init(document.getElementById('third'));
				thirdChart.off('click');
				// 指定图表的配置项和数据
				var thirdOption = {
					title : {

					},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data:'人数',
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
							name: '人数',
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
				thirdChart.setOption(thirdOption, true);
				thirdChart.on('click', function (params) {
					var flag = "";
					if ("南宁铁路公安局" == params.name) {
						flag = "1";
					}else if ("南宁公安处" == params.name){
						flag = "2";
					} else if ("柳州公安处" == params.name){
						flag = "3";
					}else if ("北海公安处" == params.name){
						flag = "4";
					}
					var url = "iframe:${ctx}/affair/affairDisciplinaryAction/findFirstJiLvDetail?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val()
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		secondJiLv();
		function secondJiLv(){
			$.get('${ctx}/sys/charts/secondJiLvPie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				var labelData = [];
				var pieData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					var pie = {};
					pie.value = data[i].count;
					pie.name = data[i].label;
					pieData.push(pie);
				}
				var forthChart = echarts.init(document.getElementById('forth'));
				forthChart.off('click');
				var forthOption = {
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
							name: '问题性质',
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
				forthChart.setOption(forthOption, true);
				forthChart.on('click', function (params) {
					var natureType = "";
					if ("政治纪律" == params.name) {
						natureType = "1";
					}else if ("组织纪律" == params.name){
						natureType = "2";
					} else if ("廉洁纪律" == params.name){
						natureType = "3";
					}else if ("群众纪律" == params.name){
						natureType = "4";
					}else if("工作纪律" == params.name){
						natureType = "5";
					}else if("生活纪律" == params.name){
						natureType = "6";
					}else if("其他" == params.name){
						natureType = "7";
					}
					var url = "iframe:${ctx}/affair/affairDisciplinaryAction/findInfoByNatureDetail?natureType="+natureType+"&sdUnit=${sdUnit}&id=${id}"+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		thirdJiLv()
		function thirdJiLv(){
			$.get('${ctx}/sys/charts/thirdJiLvColumn?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				var labelData = data.labelData;
				var totalData = data.totalData;
				// 基于准备好的dom，初始化echarts实例
				var fifthChart = echarts.init(document.getElementById('fifth'));
				fifthChart.off('click');
				// 指定图表的配置项和数据
				var fifthOption = {
					title : {

					},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data:'人数',
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
							name: '人数',
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
				fifthChart.setOption(fifthOption, true);
				fifthChart.on('click', function (params) {
					var unitType = "";
					if ("南宁铁路公安局" == params.name) {
						unitType = "1";
					}else if ("南宁公安处" == params.name){
						unitType = "2";
					} else if ("柳州公安处" == params.name){
						unitType = "3";
					}else if ("北海公安处" == params.name){
						unitType = "4";
					}
					var url = "iframe:${ctx}/affair/affairDisciplinaryAction/findDetailByCfUnit?unitType="+unitType+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		forththJiLv();
		function forththJiLv(){
			$.get('${ctx}/sys/charts/forthJiLvPie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				var labelData = [];
				var pieData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					var pie = {};
					pie.value = data[i].count;
					pie.name = data[i].label;
					pieData.push(pie);
				}
				var sixthChart = echarts.init(document.getElementById('sixth'));
				sixthChart.off('click');
				var sixthOption = {
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
							name: '处分',
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
				sixthChart.setOption(sixthOption, true);
				sixthChart.on('click', function (params) {
					var chuFenType = "";
					if ("行政处分" == params.name) {
						chuFenType = "1";
					}else if ("党纪处分" == params.name){
						chuFenType = "2";
					}
					var url = "iframe:${ctx}/affair/affairDisciplinaryAction/findDetailInfoByChuFen?chuFenType="+chuFenType+"&sdUnit=${sdUnit}&id=${id}&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		fifthJiLv();
		function fifthJiLv(){
			$.get('${ctx}/sys/charts/fifthJiLvPie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				var labelData = [];
				var pieData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					var pie = {};
					pie.value = data[i].count;
					pie.name = data[i].label;
					pieData.push(pie);
				}
				var sevenChart = echarts.init(document.getElementById('seventh'));
				sevenChart.off('click');
				var sevenOption = {
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
							name: '党纪处分',
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
				sevenChart.setOption(sevenOption, true);
				sevenChart.on('click', function (params) {
					var djChuFen = "";
					if ("警告" == params.name) {
						djChuFen = "1";
					}else if ("严重警告" == params.name){
						djChuFen = "2";
					}else if ("撤销党内职务" == params.name){
						djChuFen = "3";
					}else if ("留党察看" == params.name){
						djChuFen = "4";
					}else if ("开除党籍" == params.name){
						djChuFen = "5";
					}
					var url = "iframe:${ctx}/affair/affairDisciplinaryAction/findDetailInfoByDjChuFen?djChuFen="+djChuFen+"&id=${id}&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}

		sixthJiLv();
		function sixthJiLv(){
			$.get('${ctx}/sys/charts/sixthJiLvPie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				var labelData = [];
				var pieData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					var pie = {};
					pie.value = data[i].count;
					pie.name = data[i].label;
					pieData.push(pie);
				}
				var eighthChart = echarts.init(document.getElementById('eighth'));
				eighthChart.off('click');
				var eighthOption = {
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
							name: '行政处分',
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
				eighthChart.setOption(eighthOption, true);
				eighthChart.on('click', function (params) {
					var xzChuFen = "";
					if ("警告" == params.name) {
						xzChuFen = "1";
					}else if ("记过" == params.name){
						xzChuFen = "2";
					}else if ("记大过" == params.name){
						xzChuFen = "3";
					}else if ("降级" == params.name){
						xzChuFen = "4";
					}else if ("撤职" == params.name){
						xzChuFen = "5";
					}else if ("开除" == params.name){
						xzChuFen = "6";
					}
					var url = "iframe:${ctx}/affair/affairDisciplinaryAction/findDetailInfoByXzChuFen?xzChuFen="+xzChuFen+"&id=${id}&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		function firstTh(){
			$.get('${ctx}/sys/charts/firstThCount?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				$("#second").show();
				var labelData = data.labelData;
				var totalData = data.totalData;
				// 基于准备好的dom，初始化echarts实例
				var firstsChart = echarts.init(document.getElementById('first'));
				firstsChart.off('click');
				// 指定图表的配置项和数据
				var firstsOption = {
					title : {

					},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data:'人数',
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
						bottom:'18%'
					},
					series : [
						{
							name: '谈话次数',
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
				firstsChart.setOption(firstsOption, true);
				firstsChart.on('click', function (params) {
					var zbUnitType = "";
					if ("南宁铁路公安局监察处" == params.name) {
						zbUnitType = "1";
					}else if ("南宁公安处监察室" == params.name){
						zbUnitType = "2";
					} else if ("柳州公安处监察室" == params.name){
						zbUnitType = "3";
					}else if ("北海公安处监察室" == params.name){
						zbUnitType = "4";
					}
					var url = "iframe:${ctx}/affair/affairTalkManagement/findDetailInfoByZbUnit?zbUnitType="+zbUnitType+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val()
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		function secondTh(){
			$.get('${ctx}/sys/charts/secondThPie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				$("#second").show();
				var labelData = [];
				var pieData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					var pie = {};
					pie.value = data[i].count;
					pie.name = data[i].label;
					pieData.push(pie);
				}
				var secondsChart = echarts.init(document.getElementById('second'));
				secondsChart.off('click');
				var secondsOption = {
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
							name: '类型',
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
				secondsChart.setOption(secondsOption, true);
				secondsChart.on('click', function (params) {
					var thType = "";
					if ("谈话" == params.name) {
						thType = "1";
					}else if ("函询" == params.name){
						thType = "2";
					}else if ("谈话提醒" == params.name){
						thType = "3";
					}else if ("批评教育" == params.name){
						thType = "4";
					}else if ("责任检查" == params.name){
						thType = "5";
					}else if ("诫勉谈话" == params.name){
						thType = "6";
					}
					var url = "iframe:${ctx}/affair/affairTalkManagement/findDetailInfoByLx?thType="+thType+"&id=${id}&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		function firstLz(){
			$.get('${ctx}/sys/charts/firstLzCount?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				$("#second").show();
				var labelData = data.labelData;
				var totalData = data.totalData;
				// 基于准备好的dom，初始化echarts实例
				var firstsChart = echarts.init(document.getElementById('first'));
				firstsChart.off('click');
				// 指定图表的配置项和数据
				var firstsOption = {
					title : {

					},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data:'人数',
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
						bottom:'16%'
					},
					series : [
						{
							name: '廉政鉴定',
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
				firstsChart.setOption(firstsOption, true);
				firstsChart.on('click', function (params) {
					var jdType = "";
					if ("任前廉政鉴定" == params.name) {
						jdType = "1";
					}else if ("评先评优廉政鉴定" == params.name){
						jdType = "2";
					} else if ("出国（境）廉政鉴定" == params.name){
						jdType = "3";
					}else if ("其他廉政鉴定" == params.name){
						jdType = "4";
					}
					var url = "iframe:${ctx}/affair/affairRqlzIntegrity/findDetailInfoByType?jdType="+jdType+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val()
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		function secondLz(){
			$.get('${ctx}/sys/charts/secondLzPie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				$("#second").show();
				var labelData = [];
				var pieData = [];
				for (var i = 0; i < data.length; i++) {
					labelData.push(data[i].label);
					var pie = {};
					pie.value = data[i].count;
					pie.name = data[i].label;
					pieData.push(pie);
				}
				var secondsChart = echarts.init(document.getElementById('second'));
				secondsChart.off('click');
				var secondsOption = {
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
							name: '廉政鉴定',
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
				secondsChart.setOption(secondsOption, true);
				secondsChart.on('click', function (params) {
					var jdType = "";
					if ("任前廉政鉴定" == params.name) {
						jdType = "1";
					}else if ("评先评优廉政鉴定" == params.name){
						jdType = "2";
					} else if ("出国（境）廉政鉴定" == params.name){
						jdType = "3";
					}else if ("其他廉政鉴定" == params.name){
						jdType = "4";
					}
					var url = "iframe:${ctx}/affair/affairRqlzIntegrity/findDetailInfoByType?jdType="+jdType+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val()
					top.$.jBox.open(url, params.name,1100,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}
		function firstJd(){
			$.get('${ctx}/sys/charts/firstJdCount?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
				$("#second").hide();
				var labelData = data.labelData;
				var totalData = data.totalData;
				// 基于准备好的dom，初始化echarts实例
				var firstsChart = echarts.init(document.getElementById('first'));
				firstsChart.off('click');
				// 指定图表的配置项和数据
				var firstsOption = {
					title : {

					},
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						bottom: 10,
						left: 'center',
						data:'次数',
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
						bottom:'15%'
					},
					series : [
						{
							name: '廉政鉴定',
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
				firstsChart.setOption(firstsOption, true);
				firstsChart.on('click', function (params) {
					var type = "";
					if ("南宁铁路公安局纪委" == params.name) {
						type = "1";
					}else if ("南宁公安处纪委" == params.name){
						type = "2";
					} else if ("柳州公安处纪委" == params.name){
						type = "3";
					}else if ("北海公安处纪委" == params.name){
						type = "4";
					}
					var url = "iframe:${ctx}/affair/affairLianzhengSupervise/findDetailInfo?type="+type+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val()
					top.$.jBox.open(url, params.name,1500,550,{
						buttons:{"关闭":true},
						loaded:function(){
							$(".jbox-content", top.document).css("overflow-y","hidden");
						}
					});
				})
			});
		}

	/*$(function() {
		touSu($("#reason").val());
	});*/

		$("#reason").change(function() {
			if ($("#reason").val() == '1') {
				firstTouSu();
				secondTouSu();
			} else if ($("#reason").val() == '3') {
				firstTh();
				secondTh();
			} else if ($("#reason").val() == '4') {
				firstLz();
				secondLz();
			}else {
				firstJd();
			}
		firstJiLv();
		secondJiLv();
		thirdJiLv();
		forththJiLv();
		fifthJiLv();
		sixthJiLv();
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
			if ($("#reason").val() == '1') {
				firstTouSu();
				secondTouSu();
			} else if ($("#reason").val() == '3') {
				firstTh();
				secondTh();
			} else if ($("#reason").val() == '4') {
				firstLz();
				secondLz();
			}else {
				firstJd();
			}
			firstJiLv();
			secondJiLv();
			thirdJiLv();
			forththJiLv();
			fifthJiLv();
			sixthJiLv();
		}
	// $("#reason").change(function() {
	// 	if ($("#reason").val() == '1') {
	// 		firstTouSu();
	// 		secondTouSu();
	// 	} else if ($("#reason").val() == '3') {
	// 		firstTh();
	// 		secondTh();
	// 	} else if ($("#reason").val() == '4') {
	// 		firstLz();
	// 		secondLz();
	// 	}else {
	// 		firstJd();
	// 	}
	// 	firstJiLv();
	// 	secondJiLv();
	// 	thirdJiLv();
	// 	forththJiLv();
	// 	fifthJiLv();
	// 	sixthJiLv();
	// });

	</script>
</body>
</html>