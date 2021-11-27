<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>团青情况统计</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <style type="text/css">
        .content-div {
            margin: 20px;
            padding: 40px;
            border: 1px solid #000;
            border-radius: 4px;
            width: 1000px;
        }

        .inner-div {
            padding: 20px;
            border: 1px solid #000;
            border-radius: 4px;
        }

        .charts-div {
            display: inline-block;
            width: 300px;
            height: 550px;
        }

        #activity {
            width: 500px;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/charts/tuanWeiIndex">青年团建情况</a></li>
</ul>
<div class="content-div">
    <div>团员基本情况</div>
    <div class="inner-div">
        <div id="first" class="charts-div"></div>
        <div id="education" class="charts-div"></div>
        <div id="politicalFace" class="charts-div"></div>
        <div id="leagueCadres" class="charts-div"></div>
    </div>
    <br>
    <div class="inner-div">
        <div>
            类别：
            <form:select id="dateType" path="dateType" class="input-medium"
                         cssStyle="margin-bottom: 10px;width: 100px;">
                <form:options items="${fns:getDictList('statistics_dateType')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <input id="month" name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="${month}"
                   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width: 120px;display: none;"/>

            <input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="${year}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" style="width: 60px;"/>

            <%--时间段查询--%>
            <input id="dateStart" name="dateStart" type="text" readonly="readonly" maxlength="20"
                   class="input-medium Wdate "
                   value="${dateStart}"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                   style="width: 120px;display: none;"/>
            <span id="spanTo" style="display: none">-</span>
            <input id="dateEnd" name="dateEnd" type="text" readonly="readonly" maxlength="20"
                   class="input-medium Wdate "
                   value="${dateEnd}"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
                   style="width: 120px;display: none;"/>

            <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" style="margin-bottom: 10px;"
                   onclick="selectEcharts()"/>
        </div>
    </div>
    <br>
    <div>团费情况</div>
    <div class="inner-div">
        <div id="tourFee" class="charts-div"></div>
    </div>

    <br>
    <div>团员教育评议情况</div>
    <div class="inner-div">
        <div id="educationComment" class="charts-div"></div>
    </div>
    <br>
    <div>团内活动情况</div>
    <div class="inner-div">
        <div id="activity" class="charts-div"></div>
        <%-- <div id="topActivity" class="charts-div"></div>--%>
    </div>
</div>


<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<%--人员年龄--%>
<script type="text/javascript">
    //一进页面默认按年度查询
    $("#dateType").val(2);
    //年度、月份、时间段
    $("#dateType").change(function () {
        if ($("#dateType").val() == '2') {//年度
            $('#year').css('display', 'inline-block');
            $('#month').css('display', 'none');
            $('#dateStart').css('display', 'none');
            $('#spanTo').css('display', 'none');
            $('#dateEnd').css('display', 'none');
        } else if ($("#dateType").val() == '1') {//月度
            $('#year').css('display', 'none');
            $('#month').css('display', 'inline-block');
            $('#dateStart').css('display', 'none');
            $('#spanTo').css('display', 'none');
            $('#dateEnd').css('display', 'none');
        } else {//时间段
            $('#year').css('display', 'none');
            $('#month').css('display', 'none');
            $('#dateStart').css('display', 'inline-block');
            $('#spanTo').css('display', 'inline-block');
            $('#dateEnd').css('display', 'inline-block');
        }
    });

    function getDateParam() {
        var dateType = "&dateType=" + $("#dateType").val();
        var year = "&year=" + $("#year").val();
        var dateStart = "&dateStart=" + $("#dateStart").val();
        var dateEnd = "&dateEnd=" + $("#dateEnd").val();
        var month = "&month=" + $("#month").val();
        /* switch ($("#dateType").val()) {
             case '1':   //月度
                 month="&month="+$("#month").val();
                 dateStart="&dateStart=null";
                 dateEnd="&dateEnd=null";
                 year="&year=null";
                 break;
             case '3':   //时间段
                 year = "&year=null";
                 dateStart="&dateStart="+$("#dateStart").val();
                 dateEnd="&dateEnd="+$("#dateEnd").val();
                 month="&month=null";
                 break;
             default:    //年度
                 year = "&year="+$("#year").val();
                 dateStart="&dateStart=null";
                 dateEnd="&dateEnd=null";
                 month="&month=null";
                 break;
         }*/
        var dateParam = dateType + year + dateStart + dateEnd + month;
        return dateParam;
    }


    personnelByAge();
    findLeagueFee();
    education();
    political();
    findLeagueFee();
    comment();
    leagueCadres();
    leagueActivity();

    function personnelByAge() {
        $.get('${ctx}/sys/charts/youthLeague?label=' + getDateParam(), function (data) {
            // data=data.result;
            var ageCountData = [];
            ageCountData.push({name: "小于28岁", value: data.smail});
            ageCountData.push({name: "小于35岁", value: data.big});
            // 基于准备好的dom，初始化echarts实例
            var firstChart = echarts.init(document.getElementById('first'));
            firstChart.clear();

            // 指定图表的配置项和数据
            var option = {
                //标题　　
                title: {
                    text: '年龄比例',
                    subtext: '',
                    x: 'center'
                },
                //提示框，鼠标悬浮交互时的信息提示
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                //图例，每个图表最多仅有一个图例
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['小于28岁', "小于35岁"]
                },
                // 系列列表,每个系列通过 type 决定自己的图表类型
                series: [
                    {
                        name: '访问',
                        type: 'pie',
                        radius: '62%',
                        center: ['50%', '65%'],
                        minAngle: '15',
                        data: ageCountData,
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    formatter: "{b} :\n  {c} \n ({d}%)",
                                    position: "inner"
                                }
                            }
                        }
                    }
                ],
            };
            firstChart.setOption(option, true);
            firstChart.off("click");
            firstChart.on("click", function (params) {
                openAgeDetail(params.name);
            });

        });
    }

    function openAgeDetail(label) {
        var url = "iframe:${ctx}/affair/affairTjRegister/personnelAge?label=" + label + getDateParam();
        top.$.jBox.open(url, "团员年龄信息", 1000, 600, {
            buttons: {"关闭": true},
            loaded: function (h) {
                $(".jbox-content", top.document).css("overflow-y", "hidden");
            }
        });
    }

    //查询按钮
    function selectEcharts() {
        personnelByAge();
        findLeagueFee();
        education();
        political();
        findLeagueFee();
        comment();
        leagueCadres();
        leagueActivity();
    }

    <%--人员学历--%>

    function education() {
        $.get('${ctx}/sys/charts/youthLeagueEducation?' + getDateParam(), function (data) {
            // data=data.result;
            var educationData = [];
            var educationLable = [];
            for (var i = 0; i < data.length; i++) {
                educationData.push({name: data[i].label, value: data[i].num});
                educationLable.push(data[i].label)

            }
            // 基于准备好的dom，初始化echarts实例
            var firstChart = echarts.init(document.getElementById('education'));
            firstChart.clear();


            if (data.length == 0) {
                firstChart.showLoading({
                        text: '暂无数据',
                        color: '#ffffff',
                        textColor: '#8a8e91',
                        maskColor: 'rgba(255, 255, 255, 0.8)',
                    }
                );
            } else {
                firstChart.hideLoading();
            }

            // 指定图表的配置项和数据
            var option = {
                //标题　　
                title: {
                    text: '学历情况',
                    subtext: '',
                    x: 'center'
                },
                //提示框，鼠标悬浮交互时的信息提示
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                noDataLoadingOption: {
                    effect: "bubble",
                    text: "暂无数据",
                    effectOption: {
                        effect: {
                            n: 0
                        }
                    },
                    textStyle: {
                        fontSize: 32,
                        fontWeight: 'bold'
                    }
                },
                //图例，每个图表最多仅有一个图例
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    /*data必须与series中的data的name一致*/
                    data: educationLable
                },
                // 系列列表,每个系列通过 type 决定自己的图表类型
                series: [
                    {
                        name: '查看',
                        type: 'pie',
                        radius: '62%',
                        center: ['50%', '65%'],
                        minAngle: '15',
                        data: educationData,
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    formatter: "{b} :\n  {c} \n ({d}%)",
                                    position: "inner"
                                }
                            }
                        }
                    }
                ],
            };
            firstChart.clear();
            firstChart.setOption(option, true);
            firstChart.off("click");
            firstChart.on('click', function (params) {
                openDetailDialog(params.data.name);
                <%--self.location.href = "${ctx}/affair/affairLifeMeet/list";--%>
            });

        });


    }

    //详情弹窗
    function openDetailDialog(label) {

        var url = "iframe:${ctx}/affair/affairTjRegister/echart?education=" + label;
        top.$.jBox.open(url, "团员学历信息", 1000, 600, {
            buttons: {"关闭": true},
            loaded: function (h) {
                $(".jbox-content", top.document).css("overflow-y", "hidden");
            }
        });
    }

    <%--人员政治面貌--%>

    function political() {
        $.get('${ctx}/sys/charts/youthLeaguePolitical?' + getDateParam(), function (data) {
            // data=data.result;
            var politicalData = [];
            var politicalLable = [];
            for (var i = 0; i < data.length; i++) {
                politicalData.push({name: data[i].label, value: data[i].num});
                politicalLable.push(data[i].label)

            }
            ////debugger;er;
            // 基于准备好的dom，初始化echarts实例
            var firstChart = echarts.init(document.getElementById('politicalFace'));
            firstChart.clear();
            if (data.length == 0) {
                firstChart.showLoading({
                        text: '暂无数据',
                        color: '#ffffff',
                        textColor: '#8a8e91',
                        maskColor: 'rgba(255, 255, 255, 0.8)',
                    }
                );
            } else {
                firstChart.hideLoading();
            }
            // 指定图表的配置项和数据
            var option = {
                //标题　　
                title: {
                    text: '政治面貌情况',
                    subtext: '',
                    x: 'center'
                },
                //提示框，鼠标悬浮交互时的信息提示
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                //图例，每个图表最多仅有一个图例
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    /*data必须与series中的data的name一致*/
                    data: politicalLable
                },

                // 设置值域的标签

                label: {

                    normal: {

                        position: 'outer',  // 设置标签位置，默认在饼状图外 可选值：'outer' ¦ 'inner（饼状图上）'

                        // formatter: '{a} {b} : {c}个 ({d}%)'   设置标签显示内容 ，默认显示{b}

                        // {a}指series.name  {b}指series.data的name

                        // {c}指series.data的value  {d}%指这一部分占总数的百分比

                        formatter: '{c}'

                    }

                },
                // 系列列表,每个系列通过 type 决定自己的图表类型
                series: [
                    {
                        name: '查看',
                        type: 'pie',
                        radius: '62%',
                        center: ['50%', '65%'],
                        minAngle: '15',
                        data: politicalData,
                        itemStyle: {
                            avoidLabelOverlap: true,
                            normal: {
                                label: {
                                    show: true,
                                    formatter: "{b} :\n  {c} \n ({d}%)",
                                    position: "outside"
                                },
                                labelLine: {    //引导线设置
                                    normal: {
                                        show: true,   //引导线显示
                                    }
                                }
                            }
                        }
                    }
                ],
            };
            firstChart.setOption(option, true);
            firstChart.off('click');
            firstChart.on('click', function (params) {
                openPoliticalDetailDialog(params.data.name);
                <%--self.location.href = "${ctx}/affair/affairLifeMeet/list";--%>
            });

        });


    }

    //详情弹窗
    function openPoliticalDetailDialog(label) {
        var url = "iframe:${ctx}/affair/affairTjRegister/echart/political?label=" + label + getDateParam();
        top.$.jBox.open(url, "政治面貌信息", 1000, 600, {
            buttons: {"关闭": true},
            loaded: function (h) {
                $(".jbox-content", top.document).css("overflow-y", "hidden");
            }
        });
    }

    /*专兼职团干部*/
    function leagueCadres() {
        $.get('${ctx}/sys/charts/leagueCadres?' + getDateParam(), function (data) {
            var labelData = [];
            var countData = [];


            for (var i = 0; i < data.length; i++) {
                countData.push({name: data[i].label, value: data[i].num});
                labelData.push(data[i].label)
            }

            // 基于准备好的dom，初始化echarts实例
            var cadresChart = echarts.init(document.getElementById('leagueCadres'));
            // 指定图表的配置项和数据
            var cadresOption = {
                title: {
                    text: '专兼职团干部情况',
                    subtext: '',
                    x: 'center'
                },
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
                            rotate: 40
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
                    bottom: '15%'
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
            cadresChart.setOption(cadresOption, true);
            if (data.length == 0) {
                cadresChart.showLoading({
                        text: '暂无数据',
                        color: '#ffffff',
                        textColor: '#8a8e91',
                        maskColor: 'rgba(255, 255, 255, 0.8)',
                    }
                );
            } else {
                cadresChart.hideLoading();
            }
            cadresChart.off("click");
            cadresChart.on("click", function (params) {
                openLeagueCadresDetail(params.name);
            });


        });
    }

    function openLeagueCadresDetail(label) {
        //var url = "iframe:${ctx}/affair/affairTjRegister/cadresDetail?label="+label+getDateParam();
        var url = "iframe:${ctx}/affair/affairTwBaseSign/cadresDetail?label=" + label + getDateParam();
        top.$.jBox.open(url, "专兼职团干部人数信息", 1000, 600, {
            buttons: {"关闭": true},
            loaded: function (h) {
                $(".jbox-content", top.document).css("overflow-y", "hidden");
            }
        });
    }

    <%--团费收缴--%>

    function findLeagueFee() {
        $.get('${ctx}/sys/charts/youthLeagueFee?' + getDateParam(), function (data) {
            // data=data.result;
            var countData = [];
            var moneyData = [];
            for (var i = 0; i < data.length; i++) {
                countData.push(data[i].count);
                moneyData.push(data[i].sum)

            }
            // 基于准备好的dom，初始化echarts实例
            var feeChart = echarts.init(document.getElementById('tourFee'));
            feeChart.clear();
            // 指定图表的配置项和数据
            var option = {
                //标题　　
                title: {
                    text: '团费收缴',
                    subtext: '',
                    x: 'center'
                },
                //提示框，鼠标悬浮交互时的信息提示
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} <!--({d}%)-->"
                },
                //图例，每个图表最多仅有一个图例
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    /*data必须与series中的data的name一致*/
                    data: ['人数', '金额'],
                },
                xAxis: [
                    {
                        type: 'category',
                        // data : ['人数','金额'],
                        axisLabel: {
                            interval: 0,
                            rotate: 40
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
                    bottom: '13%'
                },
                // 系列列表,每个系列通过 type 决定自己的图表类型
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
                    },
                    {
                        name: '金额',
                        type: 'bar',
                        data: moneyData,
                        color: '#ED7D31',
                        label: {
                            show: true,
                            position: "top"
                        }
                    }
                ],
            };
            feeChart.setOption(option, true);
            feeChart.off("click");
            feeChart.on('click', function (params) {
                openFeeDetailDialog(params.name);
                <%--self.location.href = "${ctx}/affair/affairLifeMeet/list";--%>
            });

        });
    }

    //团费详情弹窗
    function openFeeDetailDialog(label) {
        var url = "iframe:${ctx}/affair/affairGroupManagement/feeDetail?label=" + label + getDateParam();
        top.$.jBox.open(url, "团费收缴信息", 1000, 600, {
            buttons: {"关闭": true},
            loaded: function (h) {
                $(".jbox-content", top.document).css("overflow-y", "hidden");
            }
        });
    }

    <%--团员教育评议情况--%>

    function comment() {
        $.get('${ctx}/sys/charts/youthLeagueComment?' + getDateParam(), function (data) {
            // data=data.result;
            var politicalData = [];
            var politicalLable = [];
            for (var i = 0; i < data.length; i++) {
                politicalData.push({name: data[i].label, value: data[i].num});
                politicalLable.push(data[i].label)
            }
            ////debugger;er
            // 基于准备好的dom，初始化echarts实例
            var educationCommentChart = echarts.init(document.getElementById('educationComment'));
            educationCommentChart.clear();
            if (data.length == 0) {
                educationCommentChart.showLoading({
                        text: '暂无数据',
                        color: '#ffffff',
                        textColor: '#8a8e91',
                        maskColor: 'rgba(255, 255, 255, 0.8)',
                    }
                );
            } else {
                educationCommentChart.hideLoading();
            }
            // 指定图表的配置项和数据
            var option = {
                //标题　　
                title: {
                    text: '团员教育评议情况',
                    subtext: '',
                    x: 'center'
                },
                //提示框，鼠标悬浮交互时的信息提示
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                //图例，每个图表最多仅有一个图例
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    /*data必须与series中的data的name一致*/
                    data: politicalLable
                },
                // 系列列表,每个系列通过 type 决定自己的图表类型
                series: [
                    {
                        name: '查看',
                        type: 'pie',
                        radius: '62%',
                        center: ['50%', '65%'],
                        minAngle: '15',
                        data: politicalData,
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    formatter: "{b} :\n  {c} \n ({d}%)",
                                    position: "inner"
                                }
                            }
                        }
                    }
                ],
            };
            educationCommentChart.setOption(option, true);
            educationCommentChart.off('click');
            educationCommentChart.on('click', function (params) {
                openCommentDetailDialog(params.data.name);
                <%--self.location.href = "${ctx}/affair/affairLifeMeet/list";--%>
            });

        });


    }

    function openCommentDetailDialog(label) {
        var url = "iframe:${ctx}/affair/affairEducationComment/echart/educationComment?label=" + label + getDateParam();
        top.$.jBox.open(url, "团员评议信息", 1000, 600, {
            buttons: {"关闭": true},
            loaded: function (h) {
                $(".jbox-content", top.document).css("overflow-y", "hidden");
            }
        });
    }

    function leagueActivity() {
        $.get('${ctx}/sys/charts/leagueActivity?' + getDateParam(), function (data) {
            var labelData = [];
            var countData = [];


            for (var i = 0; i < data.length; i++) {
                countData.push({name: data[i].label, value: data[i].num});
                labelData.push(data[i].label)
            }

            // 基于准备好的dom，初始化echarts实例
            var activityChart = echarts.init(document.getElementById('activity'));
            // 指定图表的配置项和数据
            var activityOption = {
                title: {
                    text: '基层团支部上报情况',
                    subtext: '',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    bottom: 10,
                    left: 'center',
                    data: ['条数'],
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
                            rotate: 40
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
                    bottom: '25%'
                },
                series: [
                    {
                        name: '条数',
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
            activityChart.setOption(activityOption, true);
            if (data.length == 0) {
                activityChart.showLoading({
                        text: '暂无数据',
                        color: '#ffffff',
                        textColor: '#8a8e91',
                        maskColor: 'rgba(255, 255, 255, 0.8)',
                    }
                );
            } else {
                activityChart.hideLoading();
            }
            activityChart.off("click");
            activityChart.on("click", function (params) {
                openLeagueActivityDetail(params.name);
            });


        });

        $.get('${ctx}/sys/charts/leagueTopActivity?' + getDateParam(), function (data) {
            var labelData = [];
            var countData = [];


            for (var i = 0; i < data.length; i++) {
                countData.push({name: data[i].label, value: data[i].num});
                labelData.push(data[i].label)
            }

            // 基于准备好的dom，初始化echarts实例
            var topActivityChart = echarts.init(document.getElementById('topActivity'));
            // 指定图表的配置项和数据
            var topActivityOption = {
                title: {
                    text: '团内活动信息',
                    subtext: '',
                    x: 'center'
                },
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
                        data: labelData
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                grid: {
                    left: 50
                },
                series: [
                    {
                        name: '条数',
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
            topActivityChart.setOption(topActivityOption, true);
            if (data.length == 0) {
                topActivityChart.showLoading({
                        text: '暂无数据',
                        color: '#ffffff',
                        textColor: '#8a8e91',
                        maskColor: 'rgba(255, 255, 255, 0.8)',
                    }
                );
            } else {
                topActivityChart.hideLoading();
            }
            topActivityChart.off("click");
            topActivityChart.on("click", function (params) {
                openLeagueTopActivityDetail(params.name);
            });


        });


    }

    function openLeagueActivityDetail(label) {
        var url = "iframe:${ctx}/affair/affairTnActivityRecord/activityDetail?label=" + label + getDateParam();
        top.$.jBox.open(url, label, 1000, 600, {
            buttons: {"关闭": true},
            loaded: function (h) {
                $(".jbox-content", top.document).css("overflow-y", "hidden");
            }
        });
    }

    function openLeagueTopActivityDetail(label) {
        var url = "iframe:${ctx}/affair/affairTnActivityRecord/topActivityDetail?label=" + label + getDateParam();
        top.$.jBox.open(url, label, 1000, 600, {
            buttons: {"关闭": true},
            loaded: function (h) {
                $(".jbox-content", top.document).css("overflow-y", "hidden");
            }
        });
    }

</script>
</body>
</html>