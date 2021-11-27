<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>组织干部情况</title>
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
			width: 850px;
			height: 550px;
		}
		#fifth{
			width: 85%;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/charts/leader?id=${id}">组织干部情况</a></li>
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
		<div>专长情况</div>
		<%--<div>
			<select id="specialitySelect">
				<option value="1">专长</option>
			</select>
		</div>--%>
		<div class="inner-div" style="margin-top: 5px;">
			<div id="fifth" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>人员学历信息：</div>
		<div class="inner-div">
			<div id="fullTimeEducation" class="charts-div"></div>
		</div>
		<br>
		<div class="inner-div">
			<div id="maxEducation" class="charts-div"></div>
		</div>
	</div>

	<div class="content-div">
		<div >
			<div>
				年龄：
				<select id="ageType" style="margin-bottom: 10px;width: 100px;">
					<option value="1">年龄(岁)</option>
                    <option value="2">年龄段(岁)</option>
				</select>
				<span id="ageSolt">
					<select id="age" name="age" type="text" readonly="readonly" maxlength="20" class="input-medium "
						   value="${age}" style="margin-bottom: 10px;width: 100px;display: inline-block;"></select>
				</span>

				<%-- 时间段查询--%>
				<span id="ageGroup" class="hide">
					<select id="ageStart" style="margin-bottom: 10px;width: 100px;"></select>
					<span id="line" style="margin-bottom: 10px;width: 100px;">-</span>
					<select id="ageEnd"style="margin-bottom: 10px;width: 100px;"></select>
				</span>

				<input class="btn btn-primary" type="button" value="查询" style="margin-bottom: 10px;" onclick="personnelAge()"/>
			</div>
		</div>
		<div class="inner-div">
			<div id="personnelAge" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>警衔：</div>
		<div class="inner-div">
			<div id="policeRank" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>职级：</div>
		<div class="inner-div">
			<div id="personnelJob" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>民族：</div>
		<div class="inner-div">
			<div id="nation" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>人员类别：</div>
		<div class="inner-div">
			<div id="personnelCategory" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>工作年限：</div>
		<div class="inner-div">
			<div id="workYear" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>档案管理情况</div>
		<div class="inner-div">
			<div id="first" class="charts-div"></div>
			<div id="second" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>
			<select id="conditionSelect">
				<option value="1">警衔变动情况</option>
				<%--<option value="2">警号管理</option>
                <option value="3">干部工作管理</option>
                <option value="4">离退休管理</option>
                <option value="5">劳资管理</option>
                <option value="6">公积金管理</option>
                <option value="7">抚恤管理</option>
                <option value="8">社保管理</option>--%>
			</select>
		</div>
		<div class="inner-div" style="margin-top: 5px;">
			<div id="third" class="charts-div"></div>
			<%--			<div id="fourth" class="charts-div"></div>--%>
		</div>
	</div>

	<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">

		window.onload=function(){
			//设置年份的选择
			var startYear=18;//起始年份
			var endYear=65;//结束年份
			var obj=document.getElementById('age')
			var ageStart=document.getElementById('ageStart')
			var ageEnd=document.getElementById('ageEnd')
			for (var i=startYear;i<=endYear;i++){
				obj.options.add(new Option(i,i));
				ageStart.options.add(new Option(i,i));
				ageEnd.options.add(new Option(i,i));
			}
			// obj.options[0].selected=1;
			// $("#age").val(1)
			$("#age").siblings('.select2-container').find('.select2-chosen').text($("#age").find("option:selected").text());
			$("#age").selectedIndex=0;//index为索引值
			// ageStart.options[obj.options.length-51].selected=1;
			// ageEnd.options[obj.options.length-51].selected=1;
			personnelAge();
		}



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
		$("#ageType").val(1);
	$(function() {
		dangan();
		jingxian();
		specialityInfo();
		fullTimeEducation();
		maxEducation();
		personnelAge();
		policeRank();
		personnelJob();
		nation();
		personnelCategory();
		workYear();

	});
	function dangan(){
		$.get('${ctx}/sys/charts/dangan?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
			var labelData = data.labelData;
			var zhuanruData = data.zhuanruData;
			var zhuanchuData = data.zhuanchuData;
			// 基于准备好的dom，初始化echarts实例
			var firstChart = echarts.init(document.getElementById('first'));
			// 指定图表的配置项和数据
			var firstOption = {
				title : {

				},
				tooltip : {
					trigger: 'axis'
				},
				legend: {
					bottom: 10,
					left: 'center',       //控制data位置
					data:['转入','转出'], //data从数据库拿的数据
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
					bottom:'20%'
				},
				series : [
					{
						name:'转入',
						type:'bar',
						data: zhuanruData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
					{
						name:'转出',
						type:'bar',
						data: zhuanchuData,
						color:'#ED7D31',
						label:{
							show:true,
							position:"top"
						}
					}
				]
			};
			// 使用刚指定的配置项和数据显示图表。
			/*if (labelData) {
				firstChart.setOption(firstOption);
			}*/
			firstChart.setOption(firstOption, true);
			firstChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (firstChart.containPixel('grid', pointInPixel)) {
					let xIndex = firstChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					<%--openArchivesDetail(labelData[xIndex],${id});--%>
				}
			})
		});
	}

	function openArchivesDetail(label,unitId) {
		var url = "iframe:${ctx}/personnel/personnelHrRecord/archivesListDetail?label="+name+"&unitId="+unitId;
		top.$.jBox.open(url, name,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}

	//没用到
	function jieyue(){
		$.get('${ctx}/sys/charts/jieyue?id=${id}', function(data){
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
						name:'档案借阅情况',
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
			/*if (labelData) {
				secondChart.setOption(secondOption);
			}*/
			secondChart.setOption(secondOption,true);
		});
	}

	function jingxian(){
		$.get('${ctx}/sys/charts/jingxian?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
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
						name:'警衔变动情况',
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
			/*if (labelData) {
				thirdChart.setOption(thirdOption);
			}*/
			thirdChart.setOption(thirdOption,true);

			/*var fourthChart = echarts.init(document.getElementById('fourth'));
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
						name: '警衔变动情况',
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
			/!*if (labelData) {
				fourthChart.setOption(fourthOption);
			}*!/
			fourthChart.setOption(fourthOption,true);*/
		});
	}

	//专长情况
	function specialityInfo(){
		$.get('${ctx}/sys/charts/specialityInfo?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
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
			var fifthChart = echarts.init(document.getElementById('fifth'));
			// 指定图表的配置项和数据
			var fifthOption = {
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
				toolbox: {

				},
				calculable : true,
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			fifthChart.setOption(fifthOption, true);
			fifthChart.off("click");
			fifthChart.on("click",function(params){
				openSpecialityListDetail(params.name,${id});
			});
		});

	}

	$("#conditionSelect").change(function(){

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

		//周岁、年龄段
		$("#ageType").change(function(){
			if($("#ageType").val() == '1'){				//周岁
				$('#ageSolt').show();
				$('#ageGroup').hide();
			}else if($("#ageType").val() == '2') {		//年龄段
				$('#ageSolt').hide();
				$('#ageGroup').show();
			}
		});
	//查询按钮
	function selectEcharts(){
		dangan();
		jingxian();
		specialityInfo();//专长情况
	}
	function openSpecialityListDetail(name,unitId) {
		var url = "iframe:${ctx}/personnel/personnelTalents/specialityListDetail?specialityName="+name+"&unitId="+unitId;
		top.$.jBox.open(url, name,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}

	/*全日制  存在双学位*/
	function fullTimeEducation() {
		$.get('${ctx}/sys/charts/countFullTimeEducation?id=${id}'+getDateParam(), function(data){
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
			}
			// 基于准备好的dom，初始化echarts实例
			var fullTimeEducationChart = echarts.init(document.getElementById('fullTimeEducation'));
			// 指定图表的配置项和数据
			var fullTimeEducationOption = {
				color: ['#3398DB'],
				title: {
					text: '全日制',
					subtext: '',
					x: 'center'
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
				toolbox: {

				},
				calculable : true,
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			fullTimeEducationChart.setOption(fullTimeEducationOption, true);
			fullTimeEducationChart.off("click");
			// fullTimeEducationChart.getZr().off();
			fullTimeEducationChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (fullTimeEducationChart.containPixel('grid', pointInPixel)) {
					let xIndex = fullTimeEducationChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					openFullTimeEducationDetail(labelData[xIndex],${id});
				}
			})
		});

	}

	/*全日制明细*/
	function openFullTimeEducationDetail(label,unitId) {
		var url = "iframe:${ctx}/personnel/personnelXueli/fullTimeEducationList?label="+label+"&unitId="+unitId;
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}
	/*最高学历*/
	function maxEducation() {
		$.get('${ctx}/sys/charts/countMaxEducation?id=${id}'+getDateParam(), function(data){
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
			}
			// 基于准备好的dom，初始化echarts实例
			var maxEducationChart = echarts.init(document.getElementById('maxEducation'));
			// 指定图表的配置项和数据
			var maxEducationOption = {
				color: ['#3398DB'],
				title: {
					text: '最高学历',
					subtext: '',
					x: 'center'
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
				toolbox: {

				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						data : labelData,
						axisTick: {
							alignWithLabel: true
						},
						axisLabel: {
							interval:0,
							rotate:30
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			maxEducationChart.setOption(maxEducationOption, true);
			// maxEducationChart.off("click");
			// maxEducationChart.getZr().off();
			// maxEducationChart.getZr().on();
			maxEducationChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (maxEducationChart.containPixel('grid', pointInPixel)) {
					let xIndex = maxEducationChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					openMaxEducationDetail(labelData[xIndex],${id});
				}
			})
		});
	}
	/*最高学历明细*/
	function openMaxEducationDetail(label,unitId) {
			var url = "iframe:${ctx}/personnel/personnelXueli/maxEducationList?label="+label+"&unitId="+unitId;
			top.$.jBox.open(url, label,1100,550,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
	}
	/*年龄*/
	function personnelAge() {
		$.get('${ctx}/sys/charts/countPersonnelAge?id=${id}'+"&ageType="+$("#ageType").val()+"&age="+$("#age").val()+"&startAge="+$("#ageStart").val()+"&endAge="+$("#ageEnd").val(), function(data){
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
			}
			// 基于准备好的dom，初始化echarts实例
			var personnelAgeChart = echarts.init(document.getElementById('personnelAge'));
			// 指定图表的配置项和数据
			var personnelAgeOption = {
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
				toolbox: {

				},
				calculable : true,
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			personnelAgeChart.setOption(personnelAgeOption, true);
			personnelAgeChart.off("click");
			personnelAgeChart.getZr().off();
			personnelAgeChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (personnelAgeChart.containPixel('grid', pointInPixel)) {
					let xIndex = personnelAgeChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					openPersonnelAgeDetail(labelData[xIndex],${id});
				}
			})
		});
	}
	/*年龄明细*/
	function openPersonnelAgeDetail(label,unitId) {
		let url = "iframe:${ctx}/personnel/personnelBase/personnelAgeList?label="+label+"&unitId="+unitId+"&ageType="+$("#ageType").val()+"&age="+$("#age").val()+"&startAge="+$("#ageStart").val()+"&endAge="+$("#ageEnd").val();
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}
	/*警衔*/
	function policeRank() {
		$.get('${ctx}/sys/charts/countPoliceRank?id=${id}'+getDateParam(), function(data){
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
			}
			// 基于准备好的dom，初始化echarts实例
			var policeRankChart = echarts.init(document.getElementById('policeRank'));
			// 指定图表的配置项和数据
			var policeRankOption = {
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
				toolbox: {

				},
				calculable : true,
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			policeRankChart.setOption(policeRankOption, true);
			policeRankChart.off("click");
			policeRankChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (policeRankChart.containPixel('grid', pointInPixel)) {
					let xIndex = policeRankChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					openPoliceRankDetail(labelData[xIndex],${id});
				}
			})
		});
	}
	/*警衔明细*/
	function openPoliceRankDetail(label,unitId) {
		let url = "iframe:${ctx}/personnel/personnelPoliceRank/chartsPoliceRankList?label="+label+"&unitId="+unitId+"&ageType="+$("#ageType").val()+"&age="+$("#age").val()+"&startAge="+$("#ageStart").val()+"&endAge="+$("#ageEnd").val();
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}
	
	function personnelJob() {
		$.get('${ctx}/sys/charts/countPersonnelJob?id=${id}'+getDateParam(), function(data){
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
			}
			// 基于准备好的dom，初始化echarts实例
			var personnelJobChart = echarts.init(document.getElementById('personnelJob'));
			// 指定图表的配置项和数据
			var personnelJobOption = {
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
				toolbox: {

				},
				calculable : true,
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			personnelJobChart.setOption(personnelJobOption, true);
			personnelJobChart.off("click");
			personnelJobChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (personnelJobChart.containPixel('grid', pointInPixel)) {
					let xIndex = personnelJobChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					openPersonnelJobDetail(labelData[xIndex],${id});
				}
			})
		});
	}

	/*职级明细*/
	function openPersonnelJobDetail(label,unitId) {
		let url = "iframe:${ctx}/personnel/personnelJob/chartsPersonnelJobList?label="+label+"&unitId="+unitId;
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}

	function nation() {
		$.get('${ctx}/sys/charts/countNation?id=${id}'+getDateParam(), function(data){
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
			}
			// 基于准备好的dom，初始化echarts实例
			var nationChart = echarts.init(document.getElementById('nation'));
			// 指定图表的配置项和数据
			var nationOption = {
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
				toolbox: {

				},
				calculable : true,
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			nationChart.setOption(nationOption, true);
			nationChart.off("click");
			nationChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (nationChart.containPixel('grid', pointInPixel)) {
					let xIndex = nationChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					openNationDetail(labelData[xIndex],${id});
				}
			})
		});
	}

	function openNationDetail(label, unitId) {
		let url = "iframe:${ctx}/personnel/personnelBase/chartsNationList?label="+label+"&unitId="+unitId;
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}

	function personnelCategory() {
		$.get('${ctx}/sys/charts/countPersonnelCategory?id=${id}'+getDateParam(), function(data){
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
			}
			// 基于准备好的dom，初始化echarts实例
			var personnelCategoryChart = echarts.init(document.getElementById('personnelCategory'));
			// 指定图表的配置项和数据
			var personnelCategoryOption = {
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
				toolbox: {

				},
				calculable : true,
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			personnelCategoryChart.setOption(personnelCategoryOption, true);
			personnelCategoryChart.off("click");
			personnelCategoryChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (personnelCategoryChart.containPixel('grid', pointInPixel)) {
					let xIndex = personnelCategoryChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					openCategoryDetail(labelData[xIndex],${id});
				}
			})
		});
	}

	function openCategoryDetail(label, unitId) {
		let url = "iframe:${ctx}/personnel/personnelBase/chartsCategoryList?label="+label+"&unitId="+unitId;
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}

	function workYear() {
		$.get('${ctx}/sys/charts/countWorkYear?id=${id}'+getDateParam(), function(data){
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {
				labelData.push(data[i].label);
				countData.push(data[i].count);
			}
			// 基于准备好的dom，初始化echarts实例
			var workYearChart = echarts.init(document.getElementById('workYear'));
			// 指定图表的配置项和数据
			var workYearOption = {
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
				toolbox: {

				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						name : '(单位：年)',
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
						name:'人数',
						type:'bar',
						data: countData,
						color:'#5B9BD5',
						label:{
							show:true,
							position:"top"
						}
					},
				]
			};
			workYearChart.setOption(workYearOption, true);
			workYearChart.off("click");
			workYearChart.getZr().on('click', params => {
				let pointInPixel = [params.offsetX, params.offsetY]
				if (workYearChart.containPixel('grid', pointInPixel)) {
					let xIndex = workYearChart.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[0]
					openWorkYearDetail(labelData[xIndex],${id});
				}
			})
		});
	}

	function openWorkYearDetail(label, unitId) {
		let url = "iframe:${ctx}/personnel/personnelBase/chartsWorkYearList?label="+label+"&unitId="+unitId;
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