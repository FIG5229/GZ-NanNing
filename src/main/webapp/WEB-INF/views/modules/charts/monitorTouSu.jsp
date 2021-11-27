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
            width: 780px;
        }
        .inner-div {
            padding: 20px;
            border:1px solid #000;
            border-radius: 4px;
        }

        .charts-div {
            display: inline-block;
            width: 700px;
            height: 550px;
        }
    </style>
</head>
<body>
<%--<div class="content-div">
    <div >
        <div >
           &lt;%&ndash; 类别：
            <select id="dateType" name="dateType" class="input-medium" cssStyle="margin-bottom: 10px;width: 100px;">
                <options items="${fns:getDictList('statistics_dateType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </select>&ndash;%&gt;
            <input id="month" name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="${month}"
                   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width: 120px;display: none;"/>

            <input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="${year}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" style="width: 60px;"/>

            &lt;%&ndash; 时间段查询&ndash;%&gt;
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
</div>--%>
<div class="content-div">
    <div>信访举报投诉</div>
    <div class="inner-div" style="margin-top: 5px;">
        <div id="first" class="charts-div"></div>
    </div>
</div>
<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    touSuPie();
    function touSuPie(){
        $.get('${ctx}/sys/charts/echartsIsCheckTypeFindPageInfo?sdUnit=${sdUnit}&id=${id}&dateType=${dateType}&year=${year}&dateStart=${startDate}&dateEnd=${endDate}&month=${month}', function(data){
            var labelData = data.labelData;
            console.log(labelData)
            var totalData = data.totalData;
            console.log(totalData)
            var pieCountData = [];
            if(labelData != null && labelData != undefined){
                for (var i = 0; i < labelData.length; i++) {
                    pieCountData.push({name : labelData[i], value: totalData[i]});
                }
            }
            var firstChart = echarts.init(document.getElementById('first'));
            firstChart.off('clcik');
            var firstOption = {
                title: {text: '信访举报投诉', x: 'center'},
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
                        name: '信访举报投诉数量',
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
            firstChart.setOption(firstOption, true);
            firstChart.on('click', function (params) {
                var checkType = "";
                if ("查实" == params.name) {
                    checkType = "1";
                }else if ("查否" == params.name){
                    checkType = "2";
                } else if ("部分属实" == params.name){
                    checkType = "3";
                }else if ("暂存待查" == params.name){
                    checkType = "4";
                }
                console.log(checkType)
                var url = "iframe:${ctx}/affair/affairTousujubaoguanli/findIsCheckInfoDetail?checkType="+checkType+"&sdUnit=${sdUnit}&id=${id}&dateType=${dateType}&year=${year}&dateStart=${startDate}&dateEnd=${endDate}&month=${month}";
                top.$.jBox.open(url, params.name,1100,550,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    }
                });
            })
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
</script>
</body>
</html>