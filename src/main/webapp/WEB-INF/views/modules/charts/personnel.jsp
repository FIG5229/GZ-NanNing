<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人员信息情况</title>
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
		<li  class="active"><a href="${ctx}/sys/charts/personnel?id=${id}">人员信息情况</a></li>
	</ul>
	
	
	<%--<div class="content-div">
		<label for="select">类别：</label>
		<select id="select">
			<option value="1">人员基本信息</option>
			<!--

			<option value="2">警衔信息</option>
			<option value="3">教育培训（进修）信息</option>
			<option value="4">奖励信息集</option>
			<option value="5">出国（境）信息集</option>
			<option value="6">离退信息集</option>
			<option value="7">履历信息集</option>
			<option value="8">家庭成员及社会关系信息集</option>
			<option value="9">疗（休）养信息集</option>
			<option value="10">优抚信息集</option>
			<option value="11">伤亡信息集</option>
			<option value="12">护照（通行证）信息集</option>
			<option value="13">惩戒信息集</option>
			<option value="14">休假信息集</option>
			<option value="15">教官信息集</option>
			<option value="16">工资信息集</option>
			<option value="17">工资发放信息集</option>
			<option value="18">社会团体任职信息集</option>
			<option value="19">人民警察证信息集</option>
			<option value="20">评审专家经历信息集</option>
			<option value="21">聘用信息集</option>
			<option value="22">组织考核考察（审查）信息集</option>
			<option value="23">专长信息集</option>
			<option value="24">专业技术工作及成果信息集</option>
			<option value="25">专业技术工作获奖信息集</option>
			<option value="26">住址通信信息集</option>
			<option value="27">职务层次信息集</option>
			<option value="28">执法资格等级考试情况信息集</option>
			<option value="29">增员信息集</option>
			<option value="30">语言能力信息集</option>
			<option value="31">业绩信息集</option>
			<option value="32">学位信息集</option>
			<option value="33">学历信息集</option>
			<option value="34">协管干部信息</option>
			<option value="35">退出现役军人（武警）信息集</option>
			<option value="36">体格检查信息集</option>
			<option value="37">涉密信息集</option>
			<option value="38">社会保险信息集</option>
			<option value="39">日常联系情况信息</option>
			<option value="40">人员编制信息集</option>
			<option value="41">人事档案管理信息</option>
			<option value="42">年度考核信息</option>
			<option value="43">论著信息集</option>
			<option value="44">录（入）警信息集</option>
			<option value="45">	警务技术(专业技术)职务信息集</option>
			<option value="46">警务技术(专业技术)任职资格信息集</option>
			<option value="47">交流信息集</option>
			<option value="48">减员信息集</option>
			<option value="49">获取专利信息集</option>
			<option value="50">后备干部信息集</option>
			<option value="51">行政职务信息集</option>
			<option value="52">公务员登记信息集</option>
			<option value="53">公务用枪持枪证信息集</option>
			<option value="54">工人技术等级信息</option>
			<option value="55">高层次人才信息集</option>
			<option value="56">岗位变动信息</option>
			<option value="57">抚恤信息</option>
			<option value="58">抚恤金发放记录信息</option>
			<option value="59">党员信息集</option>
			<option value="60">参与重要活动信息</option>
			<option value="61">案件技术支持信息</option>
			<option value="62">补充信息集</option>
			-->
		</select>
	</div>--%>

	<div class="content-div">
		<label>类别：人员基本信息</label>
	</div>
	<div class="content-div">
		<div>政治面貌情况</div>
		<div class="inner-div">
			<div id="first" class="charts-div"></div>
			<div id="second" class="charts-div"></div>
		</div>
	</div>
	<div class="content-div">
		<div>民警因私外出</div>
		<div class="inner-div">
			<div id="mjyswc" class="charts-div"></div>
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

		$.get('${ctx}/sys/charts/policeYswc', function(data) {
			var labelData = [];
			var countData = [];
			for (var i = 0; i < data.length; i++) {

				labelData.push("0-5天");
				labelData.push("6-10天");
				labelData.push("11-15天");
				labelData.push("16-30天");
				labelData.push("30天以上");

				countData.push(data[i].one);
				countData.push(data[i].two);
				countData.push(data[i].three);
				countData.push(data[i].four);
				countData.push(data[i].five);

			}
			// 基于准备好的dom，初始化echarts实例
			var mjyswcChart = echarts.init(document.getElementById('mjyswc'));
			// 指定图表的配置项和数据
			var mjyswcOption = {
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
						name:'民警因私外出',
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
				mjyswcChart.setOption(mjyswcOption);
			}
			mjyswcChart.on("click",function (params) {
				openMjyswcDetailDialog(params.name);
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
	function openMjyswcDetailDialog(label){
		var url = "iframe:${ctx}/affair/affairYjGoOutReport/tjfxFormDetail?size="+label
		top.$.jBox.open(url, label,1100,550,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}
	
	/*$.get('${ctx}/sys/charts/personnel-job?id=${id}', function(data) {
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
					name:'人员岗位情况',
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
	    if (labelData.length > 0 && countData.length > 0) {
	    	thirdChart.setOption(thirdOption);
		}  
		
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
					name: '人员岗位情况',
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
		if (labelData.length > 0 && pieData.length > 0) {
			fourthChart.setOption(fourthOption);
		}	
			
	});*/



	
	</script>
</body>
</html>