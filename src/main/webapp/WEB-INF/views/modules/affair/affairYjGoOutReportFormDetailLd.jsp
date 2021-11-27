<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>领导干部外出报备单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="decorator" content="default"/>

</head>

<body>
<table id="contentTable" class="table table-striped table-bordered table-condensed" style="text-align: center">
    <tr>
        <td colspan="6" style="text-align: center">
            <h4> 南宁铁路公安局领导干部外出报备单</h4>
        </td>
    </tr>
    <tr style="text-align: right" colspan="4">
        <span>${year}年${month}月${date}日</span>
    </tr>
    <tr id='r2'>
        <td class='x63' style="width: 10%">单位或部门</td>
        <td colspan='2' class='x75' style="width: 40%">${affairYjGoOutReport.unit}</td>
        <td class='x63' style="width: 10%">姓名</td>
        <td colspan='2' class='x75' style="width: 40%">${affairYjGoOutReport.name}</td>
    </tr>
    <tr>
        <td class='x63' style="width: 10%">起止时间</td>
        <td colspan="5">
            <fmt:formatDate value="${affairYjGoOutReport.leaveTime}" pattern="yyyy-MM-dd HH:mm"/>---
            <fmt:formatDate value="${affairYjGoOutReport.backTime}" pattern="yyyy-MM-dd HH:mm"/>
        </td>
    </tr>
    <tr id='r3'>
        <td class='x63'>前往地区</td>
        <td class='x65' colspan="5">${affairYjGoOutReport.goPlace}</td>
    </tr>
    <tr id='r4' style="height: 70px">
        <td class='x63'>事由</td>
        <td colspan='5' class='x69'>${affairYjGoOutReport.thing}</td>
    </tr>
    <tr id='r5' style="height: 70px">
        <td class='x63'>
            分管领导
            <br/>审批意见
        </td>
        <td colspan='5' class='x69'>${affairYjGoOutReport.fgldOpinion}</td>
    </tr>
    <tr id='r6' style="height: 70px">
        <td class='x63'>
            主要领导
            <br/>
            审批意见
        </td>
        <td colspan='5' class='x69'>${affairYjGoOutReport.mainldOpinion}</td>
    </tr>
</table>
填表说明：
<br>1.公安局机关正职领导外出报备，先报分管局领导同意后，再由办公室呈报主要领导审批。
<br>2.公安局机关副职领导外出报备，报分管局领导同意后，抄送办公室备案。
</body>
</html>
