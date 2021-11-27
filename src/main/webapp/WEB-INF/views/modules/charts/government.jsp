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
		<li class="active"><a href="${ctx}/sys/charts/government?id=${id}">政办工作情况</a></li>
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
		<div>政工信息统计</div>
		<div class="inner-div">
			<div id="first" class="charts-div"></div>
			<div id="secrecy" class="charts-div"></div>
			<div id="third" class="charts-div"></div>
		</div>
	</div>
	
	<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">
        //一进页面默认按年度查询
        $("#dateType").val(2);
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
	work();
	secrecy();
	function work(){
		$.get('${ctx}/sys/charts/workCount?'+getDateParam(), function(data) {
			var reportData = [];
			var educationData = [];
			var labelData = [];
			var surveyData = [];


            for (var i = 0; i < data.length; i++) {
                reportData.push({name : '政工简报', value: data[i].report});
                educationData.push({name : '宣传教育', value: data[i].xuanjiao});
                surveyData.push({name : '调研交流', value: data[i].survey});
                labelData.push(data[i].label);
            }
			// 基于准备好的dom，初始化echarts实例
			var firstChart = echarts.init(document.getElementById('first'));
			firstChart.clear();
			// 指定图表的配置项和数据
			var firstOption = {
				title: {text: '政工信息统计', x: 'center'},
				tooltip : {
					trigger: 'axis'
				},
				legend: {
					bottom: 10,
					left: 'center',
					data:['调研交流','政工简报','宣传教育'],
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
						name:'调研交流',
						type:'bar',
						data: surveyData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
					{
						name:'政工简报',
						type:'bar',
						data: reportData,
						color:'#ED7D31',
						label:{
							show:true,
							position:"top"
						}
					},
                    {
                        name:'宣传教育',
                        type:'bar',
                        data: educationData,
                        color:'#2a3fed',
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
				openWorkDetail(labelData[params.dataIndex],params.name);
			});

			/*var secondChart = echarts.init(document.getElementById('second'));
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
			secondChart.setOption(secondOption, true);
			secondChart.off("click");
			secondChart.on("click",function(params){
				openMedicalAidDetail(params.name);
			});*/

		});
	}

	function secrecy() {
		$.get('${ctx}/sys/charts/secrecyCount?'+getDateParam(), function(data) {
			var propagandaData = [];
			var alarmData = [];
			var labelData = [];
			for (var i = 0; i < data.length; i++) {
				propagandaData.push({name : '宣传教育', value: data[i].propaganda});
				alarmData.push({name : '警钟长鸣', value: data[i].alarm});
				labelData.push(data[i].label);
			}
			// 基于准备好的dom，初始化echarts实例
			var secrecyChart = echarts.init(document.getElementById('secrecy'));
			secrecyChart.clear();
			// 指定图表的配置项和数据
			var secrecyOption = {
				title: {text: '保密统计', x: 'center'},
				tooltip : {
					trigger: 'axis'
				},
				legend: {
					bottom: 10,
					left: 'center',
					data:['宣传教育','警钟长鸣'],
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
						name:'宣传教育',
						type:'bar',
						data: propagandaData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
					{
						name:'警钟长鸣',
						type:'bar',
						data: alarmData,
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
		secrecyChart.setOption(secrecyOption, true);
		secrecyChart.off("click");
		secrecyChart.on("click",function(params){
			openSecrecyDetail(labelData[params.dataIndex],params.name);
			});
		});
	}

	function openSecrecyDetail(company,label) {
		var url = "iframe:${ctx}/cms/article/secrecyDetail?label="+label+"&company="+company+getDateParam()
		top.$.jBox.open(url, company+label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}

	function openWorkDetail(company,label){
        var url = "iframe:${ctx}/cms/article/governmentDetail?label="+label+"&company="+company+getDateParam()
        top.$.jBox.open(url, company+label,1100,550,{
            buttons:{"关闭":true},
            loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            }
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
		work();
		secrecy();
	}



	</script>
</body>
</html>