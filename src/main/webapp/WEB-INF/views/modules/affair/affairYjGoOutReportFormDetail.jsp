<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
 <title>民警因私外出报备单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="decorator" content="default"/>

</head>

<body>
<table id="contentTable" class="table table-striped table-bordered table-condensed" style="text-align: center">
 <tr >
     <td colspan="6" style="text-align: center">
        <h4> 南宁铁路公安局民警因私外出报备单</h4>
     </td>
 </tr>
 <tr id='r2'>
       <td class='x63' style="width: 10%">部门</td>
       <td colspan='2' class='x75' style="width: 40%">${affairYjGoOutReport.unit}</td>
       <td class='x63' style="width: 10%">姓名</td>
       <td colspan='2' class='x75' style="width: 40%">${affairYjGoOutReport.name}</td>
 </tr>
 <tr  id='r3'>
   <td class='x63' style="width: 10%">前往地区</td>
   <td class='x65' style="width: 20%">${affairYjGoOutReport.goPlace}</td>
   <td class='x63' style="width: 10%">离开时间</td>
   <td class='x66' style="width: 20%"><fmt:formatDate value="${affairYjGoOutReport.leaveTime}" pattern="yyyy-MM-dd HH:mm"/></td>
   <td class='x64' style="width: 10%">返回时间</td>
   <td class='x66' style="width: 20%"><fmt:formatDate value="${affairYjGoOutReport.backTime}" pattern="yyyy-MM-dd HH:mm"/></td>
 </tr>
 <tr id='r4' style="height: 70px">
    <td class='x63' >事由</td>
    <td colspan='5' class='x69' >${affairYjGoOutReport.thing}</td>
 </tr>
 <tr  id='r5'>
 <td colspan='6' class='x72' >
     部门正职领导意见
     <br><br>
     <span>
        ${affairYjGoOutReport.bmzzldOpinion}
     </span>
     <span style='mso-spacerun:yes;'>
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     </span>
     <br><br><br>
     <div style="width: 40%;float: right">
          <span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
     <br>
     <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>签名（并盖部门公章）：
     <br>
     <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>年<span style='mso-spacerun:yes;'>&nbsp;&nbsp; </span>月<span style='mso-spacerun:yes;'>&nbsp;&nbsp;&nbsp; </span>日
     </div>
    </td>
 </tr>
</table>
填表说明：
<br>1.此申请单留存本部门。此表仅用于填写民警因私离开本单位辖区（离开南宁）请假报备（如工作轮休期间回家等），因出差、培训或工作离开的不用填写；
<br>2.公安局机关民警要严格遵守公安部“五条禁令”、铁路公安局“十二个严禁”以及公安机关各项纪律要求。
</body>
</html>
