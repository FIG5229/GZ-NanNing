<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<title>统计分析</title>
		<meta name="decorator" content="default" />
		<style type="text/css">
			.main-wrap{
				padding: 0 5px 5px 5px;
			}
			.top-bar{
				padding: 5px 20px;
				background:rgba(247,246,246,1);
			}
			.echarts-wrap{
				width: 1000px;
			}
			.echarts-row{
				overflow: hidden;
				    margin-top: 20px;
			}
			.echarts-title{
				padding-left: 10px;
				font-size:14px;
				font-weight:bold;
				color:rgba(0,0,0,0.65);
				line-height:22px;
			}
			.echarts-item{
				margin-right: 10px;
				float: left;
				width: 480px;
				height: 300px;
			}
			
		</style>
	</head>
	<body>
		<ul class="nav nav-tabs">
			<li><a href="">人员信息情况</a></li>
			<li><a href="">机构信息情况</a></li>
			<li class="active"><a href="">宣传教育情况</a></li>
			<li><a href="">纪检监察情况</a></li>
			<li><a href="">工团工作情况</a></li>
			<li><a href="">组织干部情况</a></li>
			<li><a href="">党建工作情况</a></li>
			<li><a href="">绩效考核情况</a></li>
		</ul><br />
		<div class="main-wrap">
			<div class="top-bar">
				类别: 
				<select>
				  <option>每月</option>
				  <option>2</option>
				  <option>3</option>
				</select>
				<input style="margin-bottom: 0px;" id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
			<div class="echarts-wrap">
				<div class="echarts-row">
					<div class="echarts-item" id="echarts1"></div>
					<div class="echarts-item" id="echarts2"></div>
				</div>
				<div class="echarts-row">
					
					<div class="echarts-title">
						舆情管控情况
						<select>
						  <option>来源</option>
						</select>
					</div>
					<div class="echarts-item" id="echarts3"></div>
					<div class="echarts-item" id="echarts4"></div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			$('.search-toggle').click(function(){
				if($('.search-tip').hasClass('active')){
					$('.search-tip').removeClass('active');
					$('.search-select').addClass('active');
				}else{
					$('.search-tip').addClass('active');
					$('.search-select').removeClass('active');
				}
			});
			
			
				var myChart1 = echarts.init(document.getElementById('echarts1'));
				var myChart2 = echarts.init(document.getElementById('echarts2'));
				var myChart3 = echarts.init(document.getElementById('echarts3'));
				var myChart4 = echarts.init(document.getElementById('echarts4'));
				
				barFn(myChart1);
				pieFn(myChart2);
				barFn(myChart3);
				pieFn(myChart4);
			function barFn(obj){
				option = {
					title : {
					    text: '新闻宣传情况',
					    x:'center'
					},
					legend: {
							orient: 'horizontal',
							bottom: 0,
							data: ['中央级媒体刊发','省级媒体刊发','其他媒体刊发']
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
				            data: ['中央级媒体刊发','省级媒体刊发','其他媒体刊发'],
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
				obj.setOption(option);
			}
			function pieFn(obj) {
				option = {
				    title : {
				        text: '新闻宣传情况',
				        x:'center'
				    },
				    tooltip : {
							trigger: 'item',
							formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient: 'horizontal',
				        bottom: 0,
				        data: ['中央级媒体刊发','省级媒体刊发','其他媒体刊发']
				    },
				    series : [
				        {
				            name: '访问来源',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:[
				                {value:335, name:'中央级媒体刊发'},
				                {value:310, name:'省级媒体刊发'},
				                {value:234, name:'其他媒体刊发'}
				            ],
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
				};
				obj.setOption(option);
			}
		</script>
	</body>
</html>
