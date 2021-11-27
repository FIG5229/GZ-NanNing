<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>教育训练</title>
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
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/charts/train?id=${id}">教育训练</a></li>
</ul>

<div class="content-div">
    <div >
        <div >
            类别：
            <form:select id="dateType" path="dateType" class="input-medium" cssStyle="margin-bottom: 10px;width: 100px;">
                <form:options items="${fns:getDictList('statistics_dateType2')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <input id="month" name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="${month}"
                   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width: 120px;display: none;"/>

            <input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="${year}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" style="width: 60px;"/>

            <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" style="margin-bottom: 10px;" onclick="selectEcharts()"/>
        </div>
    </div>
</div>

<div class="content-div">
    <div>基本知识</div>
    <div class="inner-div">
        <div id="second" class="charts-div"></div>
    </div>
</div>

<div class="content-div">
    <div>教官库</div>
    <div class="inner-div">
        <div id="first" class="charts-div"></div>
    </div>
</div>

<script>

</script>
<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    jiaoguan()
    function jiaoguan(){
        $.get('${ctx}/sys/charts/countInstructor', function(data) {
            var labelData = [];
            var numData = [];
            for (var i = 0; i < data.length; i++){
                labelData.push(data[i].label);
                numData.push(data[i].num);
            }
            var firstChart = echarts.init(document.getElementById('first'));
            firstChart.off('click');
            var firstOption = {
                title: {text: '教官库', x: 'center'},
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
                    bottom:'13%'
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
            firstChart.setOption(firstOption, true);
            firstChart.off("click");
            firstChart.on("click",function (params) {
                openJiaoGuankDetail(params.name);
            });
        });
    }

    baseKnoldedge()
    function baseKnoldedge() {
        $.get('${ctx}/sys/charts/baseKonoledge?id=${id}'+'&dateType='+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val(), function(data) {
            var countData = [];
            var labelData = [];
            for (var i = 0;i<data.length;i++){
                countData.push(data[i].num);
                labelData.push(data[i].label);
            }

            var fourthChart = echarts.init(document.getElementById('second'));
            // 指定图表的配置项和数据
            var fourthOption = {
                title: {text: '基本知识', x: 'center'},
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
                            interval: 0,
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
                openBasicKnoledge(params.name);
            });
        });
    }

    function selectEcharts(){
        baseKnoldedge();
    }

    function openBasicKnoledge(label){
        var url = "iframe:${ctx}/affair/affairBasicKnowledge/basicKnoledgeDetail?status="+label+"&dateType="+$("#dateType").val()+"&year="+$("#year").val()+"&dateStart="+$("#dateStart").val()+"&dateEnd="+$("#dateEnd").val()+"&month="+$("#month").val();
        top.$.jBox.open(url, label,1100,550,{
            buttons:{"关闭":true},
            loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            }
        });
    }

    function openJiaoGuankDetail(label) {
        var url =  "iframe:${ctx}/affair/affairInteriorInstructorLibrary/jiaoGuanDetail?label="+label;
        top.$.jBox.open(url, label,1100,550,{
            buttons:{"关闭":true},
            loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            }
        });
    }
</script>


<script>
    $("#dateType").val(2);
    function getDateParam() {
        var dateType="&dateType="+$("#dateType").val();
        var year = "&year="+$("#year").val();
        var month="&month="+$("#month").val();
        var dateParam = dateType+year+month;
        return dateParam;
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
        }
    });
</script>
</body>
</html>