<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>警务督察</title>
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
            width: 900px;
            height: 550px;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/charts/inspector?id=${id}">警务督察情况</a></li>
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
        <label for="reason">督察问题</label>
        <select id="reason" style="width: 120px;">
            <c:choose>
                <c:when test="${fns:getUser().id eq '26449823050b49c786f7baff26b6a7a2'}">
                    <option value="1">全局</option>
                    <option value="2">督察处</option>
                    <option value="3">南宁处督察支队</option>
                    <option value="4">柳州处督察支队</option>
                    <option value="5">北海处督察支队</option>
                </c:when>
                <c:when test="${fns:getUser().id eq 'ca32723864644fa8979693dc9a539b91'}">
                    <option value="3">南宁处督察支队</option>
                </c:when>
                <c:when test="${fns:getUser().id eq 'ad04613867cc41f081c78ac19f159263'}">
                    <option value="4">柳州处督察支队</option>
                </c:when>
                <c:when test="${fns:getUser().id eq '1fdedc31fd6944eb8cbb9a279f4cb3c4'}">
                    <option value="5">北海处督察支队</option>
                </c:when>
                <c:otherwise></c:otherwise>
            </c:choose>

        </select>
    </div>
    <div class="inner-div">
        <div id="first" class="charts-div"></div>
        <div id="second" class="charts-div"></div>
    </div>
</div>
<c:choose>
<c:when test="${fns:getUser().id eq '26449823050b49c786f7baff26b6a7a2'}">
<div class="content-div">
    <label for="reason2">督察记录</label>
    <select id="reason2" style="width: 120px;">
        <option value="1">全局</option>
        <option value="2">督察支队</option>
    </select>
    <div class="inner-div">
        <div id="third" class="charts-div"></div>
    </div>
</div>
</c:when>
</c:choose>


<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    //一进页面默认按年度查询
    $("#dateType").val(2);
    <c:choose>
        <c:when test="${fns:getUser().id eq '26449823050b49c786f7baff26b6a7a2'}">
            allDuCha();
        </c:when>
        <c:when test="${fns:getUser().id eq 'ca32723864644fa8979693dc9a539b91'}">
             nncDuCha();
        </c:when>
        <c:when test="${fns:getUser().id eq 'ad04613867cc41f081c78ac19f159263'}">
            lzcDuCha();
        </c:when>
        <c:when test="${fns:getUser().id eq '1fdedc31fd6944eb8cbb9a279f4cb3c4'}">
            bhcDuCha();
        </c:when>
        <c:otherwise></c:otherwise>
    </c:choose>
    function allDuCha(){
        $.get('${ctx}/sys/charts/all-ducha-column?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var yzgTotalData = [];
            var totalData = [];
            var wzgTotalData = [];
            var xqTotalData = [];
            var wuTotalData = [];
            for (var i = 0;i<data.length;i++){
                totalData.push(data[i].total);
                labelData.push(data[i].label);
                yzgTotalData.push(data[i].yzgTotal);
                wzgTotalData.push(data[i].wzgTotal);
                xqTotalData.push(data[i].xqTotal);
               wuTotalData.push(data[i].wuTotal);
            }
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
                    data:['数量'],
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
                    bottom:'15%'
                },
                series : [
                    {
                        name:'总数量',
                        type:'bar',
                        data: totalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'已整改数量',
                        type:'bar',
                        data: yzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'未整改数量',
                        type:'bar',
                        data: wzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'限期整改数量',
                        type:'bar',
                        data: xqTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'无',
                        type:'bar',
                        data: wuTotalData,
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
            firstChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/sys/charts/inspectorAllPiePage?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });

        $.get('${ctx}/sys/charts/all-ducha-pie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
			var labelData = [];
			var pieData = [];
			for (var i = 0; i < data.length; i++) {
				// labelData.push(data[i].label);
				// var pie = {};
				// pie.value = data[i].count;
				// pie.name = data[i].label;
				// pieData.push(pie);
                // pieData.push(data[i].total);
                var pie = {};
                pie.value = data[i].total;
                pie.name = data[i].label;
                pieData.push(pie);
                labelData.push(data[i].label);
			}
			console.log(pieData);
			console.log(labelData);
			var secondChart = echarts.init(document.getElementById('second'));
            secondChart.off('click');
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
						name: '监察问题库情况',
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
			secondChart.setOption(secondOption, true);
            secondChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/affair/affairDcwtLibrary/findAllPieDetailInfoList?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
		});
           }
    function juDuCha(){
        $.get('${ctx}/sys/charts/ju-ducha-column?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var yzgTotalData = [];
            var totalData = [];
            var wzgTotalData = [];
            var xqTotalData = [];
            var wuTotalData = [];
            for (var i = 0;i<data.length;i++){
                totalData.push(data[i].total);
                labelData.push(data[i].label);
                yzgTotalData.push(data[i].yzgTotal);
                wzgTotalData.push(data[i].wzgTotal);
                xqTotalData.push(data[i].xqTotal);
                wuTotalData.push(data[i].wuTotal);
            }
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
                    data:['数量'],
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
                        name:'总数量',
                        type:'bar',
                        data: totalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'已整改数量',
                        type:'bar',
                        data: yzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'未整改数量',
                        type:'bar',
                        data: wzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'限期整改数量',
                        type:'bar',
                        data: xqTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'无',
                        type:'bar',
                        data: wuTotalData,
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
            firstChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/sys/charts/inspectorJuPiePage?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });

        $.get('${ctx}/sys/charts/ju-ducha-pie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var pieData = [];
            for (var i = 0; i < data.length; i++) {
                // labelData.push(data[i].label);
                // var pie = {};
                // pie.value = data[i].count;
                // pie.name = data[i].label;
                // pieData.push(pie);
                // pieData.push(data[i].total);
                var pie = {};
                pie.value = data[i].total;
                pie.name = data[i].label;
                pieData.push(pie);
                labelData.push(data[i].label);
            }
            console.log(pieData);
            console.log(labelData);
            var secondChart = echarts.init(document.getElementById('second'));
            secondChart.off('click');
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
                        name: '监察问题库情况',
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
            secondChart.setOption(secondOption, true);
            secondChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/affair/affairDcwtLibrary/findJuPieDetailInfoList?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });
    }
    function nncDuCha(){
            $.get('${ctx}/sys/charts/nnc-ducha-column?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var yzgTotalData = [];
            var totalData = [];
            var wzgTotalData = [];
            var xqTotalData = [];
            var wuTotalData = [];
            for (var i = 0;i<data.length;i++){
                totalData.push(data[i].total);
                labelData.push(data[i].label);
                yzgTotalData.push(data[i].yzgTotal);
                wzgTotalData.push(data[i].wzgTotal);
                xqTotalData.push(data[i].xqTotal);
                wuTotalData.push(data[i].wuTotal);
            }
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
                    data:['数量'],
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
                        name:'总数量',
                        type:'bar',
                        data: totalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'已整改数量',
                        type:'bar',
                        data: yzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'未整改数量',
                        type:'bar',
                        data: wzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'限期整改数量',
                        type:'bar',
                        data: xqTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'无',
                        type:'bar',
                        data: wuTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    }
                ]
            };
            /*if (labelData) {
                firstChart.setOption(firstOption, true);
            }*/
            firstChart.setOption(firstOption, true);
            firstChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/sys/charts/inspectorNncPiePage?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });

        $.get('${ctx}/sys/charts/nnc-ducha-pie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var pieData = [];
            for (var i = 0; i < data.length; i++) {
                var pie = {};
                pie.value = data[i].total;
                pie.name = data[i].label;
                pieData.push(pie);
                labelData.push(data[i].label);
            }
            console.log(pieData);
            console.log(labelData);
            var secondChart = echarts.init(document.getElementById('second'));
            secondChart.off('click');
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
                        name: '监察问题库情况',
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
            secondChart.setOption(secondOption, true);
            secondChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/affair/affairDcwtLibrary/findNncPieDetailInfoList?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });
    }
    function lzcDuCha(){
        $.get('${ctx}/sys/charts/lzc-ducha-column?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var yzgTotalData = [];
            var totalData = [];
            var wzgTotalData = [];
            var xqTotalData = [];
            var wuTotalData = [];
            for (var i = 0;i<data.length;i++){
                totalData.push(data[i].total);
                labelData.push(data[i].label);
                yzgTotalData.push(data[i].yzgTotal);
                wzgTotalData.push(data[i].wzgTotal);
                xqTotalData.push(data[i].xqTotal);
                wuTotalData.push(data[i].wuTotal);
            }
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
                    data:['数量'],
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
                        name:'总数量',
                        type:'bar',
                        data: totalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'已整改数量',
                        type:'bar',
                        data: yzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'未整改数量',
                        type:'bar',
                        data: wzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'限期整改数量',
                        type:'bar',
                        data: xqTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'无',
                        type:'bar',
                        data: wuTotalData,
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
            firstChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/sys/charts/inspectorLzcPiePage?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });

        $.get('${ctx}/sys/charts/lzc-ducha-pie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var pieData = [];
            for (var i = 0; i < data.length; i++) {
                var pie = {};
                pie.value = data[i].total;
                pie.name = data[i].label;
                pieData.push(pie);
                labelData.push(data[i].label);
            }
            console.log(pieData);
            console.log(labelData);
            var secondChart = echarts.init(document.getElementById('second'));
            secondChart.off('click');
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
                        name: '监察问题库情况',
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
            secondChart.setOption(secondOption, true);
            secondChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/affair/affairDcwtLibrary/findLzcPieDetailInfoList?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });
    }
    function bhcDuCha(){
        $.get('${ctx}/sys/charts/bhc-ducha-column?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var yzgTotalData = [];
            var totalData = [];
            var wzgTotalData = [];
            var xqTotalData = [];
            var wuTotalData = [];
            for (var i = 0;i<data.length;i++){
                totalData.push(data[i].total);
                labelData.push(data[i].label);
                yzgTotalData.push(data[i].yzgTotal);
                wzgTotalData.push(data[i].wzgTotal);
                xqTotalData.push(data[i].xqTotal);
                wuTotalData.push(data[i].wuTotal);
            }
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
                    data:['数量'],
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
                        name:'总数量',
                        type:'bar',
                        data: totalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'已整改数量',
                        type:'bar',
                        data: yzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'未整改数量',
                        type:'bar',
                        data: wzgTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'限期整改数量',
                        type:'bar',
                        data: xqTotalData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    },
                    {
                        name:'无',
                        type:'bar',
                        data: wuTotalData,
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
            firstChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/sys/charts/inspectorBhcPiePage?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });

        $.get('${ctx}/sys/charts/bhc-ducha-pie?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var pieData = [];
            for (var i = 0; i < data.length; i++) {
                var pie = {};
                pie.value = data[i].total;
                pie.name = data[i].label;
                pieData.push(pie);
                labelData.push(data[i].label);
            }
            console.log(pieData);
            console.log(labelData);
            var secondChart = echarts.init(document.getElementById('second'));
            secondChart.off('click');
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
                        name: '监察问题库情况',
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
            secondChart.setOption(secondOption, true);
            secondChart.on('click', function (params) {
                var flag = "";
                if ("执行力和落实力" == params.name){
                    flag = "1";
                }else if ("执法监督" == params.name){
                    flag = "2";
                }else if ("干部作风" == params.name){
                    flag = "3";
                }else if ("安检查危" == params.name){
                    flag = "4";
                }else if ("站车治安秩序" == params.name){
                    flag = "5";
                }else if ("内务管理" == params.name){
                    flag = "6";
                }else if ("勤务状态" == params.name){
                    flag = "7";
                }else if ("警纪警规" == params.name){
                    flag = "8";
                }else if ("警车管理" == params.name){
                    flag = "9";
                }else if ("枪支管理" == params.name){
                    flag = "10";
                }else if ("警容风纪" == params.name){
                    flag = "11";
                }else if ("队伍管理" == params.name){
                    flag = "12";
                }else if ("其他" == params.name){
                    flag = "13";
                }else if ("无" == params.name){
                    flag = "14";
                }else if("安保措施落实不力" == params.name){
                    flag = "15";
                }
                var url = "iframe:${ctx}/affair/affairDcwtLibrary/findBhcPieDetailInfoList?flag="+flag+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });
    }
    allJiLu();
    function allJiLu(){
        //统计监督单位
        $.get('${ctx}/sys/charts/allDuchaSupervisoryUnit?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var countData = [];
            for (var i = 0; i < data.length; i++) {
                labelData.push("南宁铁路公安局");
                countData.push(data[i].count);
            }
            // 基于准备好的dom，初始化echarts实例
            var thirdChart = echarts.init(document.getElementById('third'));
            thirdChart.off('click');
            // 指定图表的配置项和数据
            var thirdOption = {
                title: {text: '督察单位', x: 'center'},
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    bottom: 10,
                    left: 'center',
                    data:labelData,
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
                        name:'数量',
                        type:'bar',
                        data: countData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    }
                ]
            };
            thirdChart.setOption(thirdOption, true);
            thirdChart.on('click', function (params) {
                var url = "iframe:${ctx}/affair/affairDcwtLibrary/findAllDcLibrary?dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });
    }
    function jilu(){
        //统计监督单位
        $.get('${ctx}/sys/charts/duchaSupervisoryUnit?id=${id}&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data){
            var labelData = [];
            var countData = [];
            for (var i = 0; i < data.length; i++) {
                labelData.push(data[i].label);
                countData.push(data[i].count);
            }
            // 基于准备好的dom，初始化echarts实例
            var thirdChart = echarts.init(document.getElementById('third'));
            thirdChart.off('click');
            // 指定图表的配置项和数据
            var thirdOption = {
                title: {text: '督察单位', x: 'center'},
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    bottom: 10,
                    left: 'center',
                    data:labelData,
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
                        name:'数量',
                        type:'bar',
                        data: countData,
                        color:'#5B9BD5',
                        label:{
                            show:true,
                            position:"top"
                        }
                    }
                ]
            };
            thirdChart.setOption(thirdOption, true);
            thirdChart.on('click', function (params) {
                var jdUnit = "";
                if ("警务督察处" == params.name){
                    jdUnit = "18"
                }else if ("南宁督察支队" == params.name){
                    jdUnit = "68"
                }else if ("柳州督察支队" == params.name){
                    jdUnit = "183"
                }else if ("北海督察支队" == params.name){
                    jdUnit = "284"
                }
                var url = "iframe:${ctx}/affair/affairDcwtLibrary/findAllDcLibraryByJdUnit?jdUnit="+jdUnit+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
                top.$.jBox.open(url, params.name,900,850,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            });
        });
    }
    /*$(function() {
        allDuCha();
    });*/
    $("#reason").change(function(){
        if($("#reason").val() == '1'){
            allDuCha();
        }else if($("#reason").val() == '2'){
            juDuCha();
        }else if($("#reason").val() == '3'){
            nncDuCha();
        }else if($("#reason").val() == '4'){
            lzcDuCha();
        }else if ($("#reason").val() == '5'){
            bhcDuCha();
        }

    });
    $("#reason2").change(function(){
        if($("#reason2").val() == '1'){
            allJiLu();
        }else if($("#reason2").val() == '2'){
            jilu();
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
        if($("#reason").val() == '1'){
            allDuCha();
        }else if($("#reason").val() == '2'){
            juDuCha();
        }else if($("#reason").val() == '3'){
            nncDuCha();
        }else if($("#reason").val() == '4'){
            lzcDuCha();
        }else if ($("#reason").val() == '5'){
            bhcDuCha();
        }
        if($("#reason2").val() == '1'){
            allJiLu();
        }else if($("#reason2").val() == '2'){
            jilu();
        }
    }
</script>
</body>
</html>