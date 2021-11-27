<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>青年团建</title>
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
    <li class="active"><a href="${ctx}/sys/charts/group?id=${id}">青年团建情况</a></li>
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
    <div>青年团建情况</div>
    <div class="inner-div">
        <div id="first" class="charts-div"></div>
        <div id="second" class="charts-div"></div>
        <div id="third" class="charts-div"></div>
    </div>
</div>


<script src="${ctx}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>

</body>
</html>