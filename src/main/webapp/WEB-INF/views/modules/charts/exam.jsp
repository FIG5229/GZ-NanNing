<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>绩效考评情况</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <style type="text/css">
        .content-div {
            margin: 20px;
            padding: 40px;
            border:1px solid #000;
            border-radius: 4px;
            width: 900px;
        }
        .inner-div {
            padding: 20px;
            border:1px solid #000;
            border-radius: 4px;
        }

        .charts-div {
            display: inline-block;
            width: 350px;
            height: 500px;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li  class="active"><a href="${ctx}/sys/charts/performance?id=${id}&unitType=${unitType}">绩效考核（单位）</a></li>
<%--    <li class=""><a href="${ctx}/exam/examPerformanceAppraisal/">部门绩效考核情况</a></li>--%>
    <li class=""><a href="${ctx}/exam/examLdPerformanceAppraisal?unitId=${id}&unitType=${unitType}">绩效考核（人员）</a></li>
</ul>

<div class="content-div">
    <div >
        <div >
            考评周期：
            <form:select id="dateType" path="dateType" class="input-medium" cssStyle="margin-bottom: 10px;width: 100px;">
                <form:options items="${fns:getDictList('statistics_dateType2')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="${year}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" style="width: 60px;display: none"/>

            <input id="month" name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="${month}"
                   onclick="WdatePicker({dateFmt:'yyyyMM',isShowClear:false});" style="width: 120px;"/>
           <%-- <label>分数：</label>
            <input id="score_start" name="score_start" style="width: 30px"/>-<input id="score_end" name="score_end" style="width: 30px"/>分--%>
            <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" style="margin-bottom: 10px;" onclick="selectEcharts()"/>
        </div>
    </div>
</div>


<div class="content-div">
<%--    <div>局考处考评情况</div>--%>
    <div >
        <div id="qz">
            <c:choose>
                <c:when test="${unitType eq 'isJu'}">
                    <select id="reason" style="width: 140px;">
                        <option value="1">局考处</option>
                        <option value="2">局考局机关</option>
                        <option value="3">处考队所</option>
                        <option value="4">处考处机关和独立单位</option>
                    </select>
                </c:when>
                <c:when test="${unitType eq 'isNNC'}">
                    <select id="reason" style="width: 140px;">
                        <option value="1">局考处</option>
                        <option value="3">处考队所</option>
                        <option value="4">处考处机关和独立单位</option>
                    </select>
                </c:when>
                <c:when test="${unitType eq 'isLZC'}">
                    <select id="reason" style="width: 140px;">
                        <option value="1">局考处</option>
                        <option value="3">处考队所</option>
                        <option value="4">处考处机关</option>
                    </select>
                </c:when>
                <c:when test="${unitType eq 'isBHC'}">
                    <select id="reason" style="width: 140px;">
                        <option value="1">局考处</option>
                        <option value="3">处考队所</option>
                        <option value="4">处考处机关</option>
                    </select>
                </c:when>
                <c:when test="${unitType eq 'isJJG'}">
                    <select id="reason" style="width: 140px;">
                        <option value="2">局考局机关</option>
                    </select>
                </c:when>
                <c:when test="${unitType eq 'isNNCpcs' or unitType eq 'isLZCpcs' or unitType eq 'isBHCpcs'}">
                    <select id="reason" style="width: 140px;">
                        <option value="3">处考队所</option>
                    </select>
                </c:when>
                <c:when test="${unitType eq 'isNNCjg' or unitType eq 'isLZCjg' or unitType eq 'isBHCjg'}">
                    <select id="reason" style="width: 140px;">
                        <option value="4">处考处机关和独立单位</option>
                    </select>
                </c:when>
                <c:otherwise>
                    <select id="reason" style="width: 140px;">
                        <option value="1">局考处</option>
                        <option value="2">局考局机关</option>
                        <option value="3">处考队所</option>
                        <option value="4">处考处机关和独立单位</option>
                    </select>
                </c:otherwise>
            </c:choose>
            <c:if test="${unitType eq 'isJu' or unitType eq 'isNNC' or unitType eq 'isLZC' or unitType eq 'isBHC' or unitType eq 'isNNCpcs' or unitType eq 'isLZCpcs' or unitType eq 'isBHCpcs'}">
                <%--工作项--%>
                <div id="workNameDiv" style="display:inline-block;">
                    <select id="workName" style="width: 140px">
                        <option value="0">全部</option>
                        <c:forEach items="${examWeigths}" var="examWeight">
                            <option value="${examWeight.value}">${examWeight.label}</option>
                        </c:forEach>
                    </select>
                    <%--<select id="workName" style="width: 140px">
                        <option value="0">全部</option>
                        <option value="1">治安工作</option>
                        <option value="2">高铁安保</option>
                        <option value="3">保安管理</option>
                        <option value="4">刑侦工作</option>
                        <option value="5">禁毒工作</option>
                        <option value="6">刑事技术工作</option>
                        <option value="7">反恐防爆</option>
                        <option value="8">安检工作</option>
                        <option value="9">特警工作</option>
                        <option value="10">国保工作</option>
                        <option value="11">内保工作</option>
                        <option value="12">法制监管</option>
                        <option value="13">警卫工作</option>
                        <option value="14">消防监督</option>
                        <option value="15">交通管理</option>
                        <option value="16">指挥中心工作</option>
                        <option value="17">网络安全</option>
                        <option value="18">信息化建设</option>
                        <option value="19">办公室工作</option>
                        <option value="20">装备财务</option>
                        <option value="21">纪检监察工作</option>
                        <option value="22">警务督察</option>
                        <option value="23">训练工作</option>
                        <option value="24">宣传工作</option>
                        <option value="25">共青团工作</option>
                        <option value="26">工会工作</option>
                        <option value="27">离退工作</option>
                        <option value="28">组织人事工作</option>
                        <option value="29">党建工作</option>
                        <option value="30">技术侦察</option>
                        <option value="31">警犬工作</option>
                        <option value="32">公共加扣分</option>
                            &lt;%&ndash;<option value="33">其他公共加扣分情况</option>&ndash;%&gt;
                    </select>--%>
                </div>
            </c:if>
        </div>
    </div>

    <div class="inner-div">
        <div id="first" class="charts-div" style="width: 100%;"></div>
        <%--<div id="second" class="charts-div" style="width: 100%;"></div>--%>
    </div>


</div>
<c:choose>
    <c:when test="${fn:contains(unitName,'领导')}">

    </c:when>
    <c:when test="${id eq '1'}">

    </c:when>
    <c:otherwise>
        <div class="content-div">
            <div id="third" class="charts-div" style="width: 100%;">
                <iframe id="mainContent" src="${ctx}/exam/examRecord/list?type=unit&unitId=${id}&name=${unitName}&unitType=${unitType}" width="100%" height="100%" frameborder="0"></iframe>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<script>

</script>
<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">

    function getDateParam() {
        var startScore="&startScore="+$("#score_start").val();
        var endScore="&endScore="+$("#score_end").val();
        var dateParam = startScore+endScore;
        return dateParam;
    }

    function exam(dateFlag,flag){
        if (dateFlag == 1) {
            if (flag == 1) {
                //局考处 10.28增加 workName 参数  kevin.jia
                $.get('${ctx}/sys/charts/exam?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&month="+$("#month").val()+"&workName="+$("#workName").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].unit);
                        countData.push(data[i].sum);
                        var pie = {};
                        pie.value = data[i].unit;
                        pie.name = data[i].sum;
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
                                name:'局考处',
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
                                name: '局考处',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }else if (flag == 2) {
                $.get('${ctx}/sys/charts/exam?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&month="+$("#month").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].unit);
                        countData.push(data[i].sum);
                        var pie = {};
                        pie.value = data[i].unit;
                        pie.name = data[i].sum;
                        pieData.push(pie);
                    }
                    console.log(labelData)
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
                                axisLabel:{
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
                                name:'局考局',
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
                                name: '局考局',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }else if (flag == 3){
                //处考队所  10.29增加 workName 参数  kevin.jia
                $.get('${ctx}/sys/charts/exam?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&month="+$("#month").val()+"&workName="+$("#workName").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].unit);
                        countData.push(data[i].sum);
                        var pie = {};
                        pie.value = data[i].unit;
                        pie.name = data[i].sum;
                        pieData.push(pie);
                    }
                    console.log(labelData)
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
                                name:'处考队所',
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
                                name: '处考队所',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }else if (flag == 4) {
                $.get('${ctx}/sys/charts/exam?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&month="+$("#month").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].unit);
                        countData.push(data[i].sum);
                        var pie = {};
                        pie.value = data[i].unit;
                        pie.name = data[i].sum;
                        pieData.push(pie);
                    }
                    console.log(labelData)
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
                                name:'处考处机关',
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
                                name: '处考处机关',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }else if (flag == 7) {
                $.get('${ctx}/sys/charts/exam?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&month="+$("#month").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].name);
                        countData.push(data[i].score);
                        var pie = {};
                        pie.value = data[i].name;
                        pie.name = data[i].score;
                        pieData.push(pie);
                    }
                    console.log(labelData)
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
                                name:'普通民警',
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
                                name: '普通民警',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }
            }else if (dateFlag == 2) {
            if (flag == 1) {
                //局考处 增加workName参数 10.29 kevin.jia
                $.get('${ctx}/sys/charts/examYear?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&workName="+$("#workName").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].unit);
                        countData.push(data[i].sum);
                        var pie = {};
                        pie.value = data[i].unit;
                        pie.name = data[i].sum;
                        pieData.push(pie);
                    }
                    console.log(labelData)
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
                                name:'局考处',
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
                                name: '局考处',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }else if (flag == 2) {
                $.get('${ctx}/sys/charts/examYear?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].unit);
                        countData.push(data[i].sum);
                        var pie = {};
                        pie.value = data[i].unit;
                        pie.name = data[i].sum;
                        pieData.push(pie);
                    }
                    console.log(labelData)
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
                                name:'局考局',
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
                                name: '局考局',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }else if (flag == 3){
                //处考队所增加workName参数 10.29 kevin.jia
                $.get('${ctx}/sys/charts/examYear?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&workName="+$("#workName").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].unit);
                        countData.push(data[i].sum);
                        var pie = {};
                        pie.value = data[i].unit;
                        pie.name = data[i].sum;
                        pieData.push(pie);
                    }
                    console.log(labelData)
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
                                name:'处考队所',
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
                                name: '处考队所',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }else{
                $.get('${ctx}/sys/charts/examYear?id=${id}&flag='+flag+'&dateFlag='+dateFlag+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+getDateParam(), function(data) {
                    var labelData = [];
                    var countData = [];
                    var pieData = [];
                    for (var i = 0; i < data.length; i++) {
                        labelData.push(data[i].unit);
                        countData.push(data[i].sum);
                        var pie = {};
                        pie.value = data[i].unit;
                        pie.name = data[i].sum;
                        pieData.push(pie);
                    }
                    console.log(labelData)
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
                                name:'处考处机关',
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
                                name: '处考处机关',
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
                    /*if (labelData.length > 0 && pieData.length > 0) {
                        secondChart.setOption(secondOption, true);
                    }*/
                    secondChart.setOption(secondOption, true);
                });
            }
        }

    }

    //调用方法
    exam($("#dateType").val(),$("#reason").val());

    $("#dateType").change(function(){
        exam($("#dateType").val(),$("#reason").val());
    });
    $("#reason").change(function(){
        $("#workName").val('0');//将下拉框值设置为全部
        $("#s2id_workName").find('span[class="select2-chosen"]').text("全部");//设置下拉框显示框值
        var reasonSelVal =  $(this).val();//获取当前切换选择的值,局考处 1，处考队所 3 显示工作项下拉框; 2 局考局机关 4 处考处机关 隐藏
        if(reasonSelVal == 2 || reasonSelVal == 4){
            $('#workNameDiv').hide();
        }else{
            $('#workNameDiv').show();
        }
        exam($("#dateType").val(),$("#reason").val());
    });
    /*工作项*/
    $("#workName").change(function(){
        exam($("#dateType").val(),$("#reason").val());
    });
    //年度、月份
    $("#dateType").change(function(){
        if($("#dateType").val() == '2'){//年度
            $('#year').css('display', 'inline-block');
            $('#month').css('display', 'none');
        }else if($("#dateType").val() == '1'){//月度
            $('#year').css('display', 'none');
            $('#month').css('display', 'inline-block');
        }
    });
    //查询按钮
    function selectEcharts(){
        exam($("#dateType").val(),$("#reason").val());
    }
</script>
</body>
</html>