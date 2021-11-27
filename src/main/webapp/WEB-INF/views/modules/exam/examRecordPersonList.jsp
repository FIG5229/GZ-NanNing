<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>考评档案(个人)</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            self.location.href = '${ctx}/exam/examRecord/list?type=person&personId=${personId}&idNumber=${idNumber}&name=${name}&pageNo=' + $("#pageNo").val() + '&pageSize=' + $("#pageSize").val();
            //$("#searchForm").submit();
            return false;
        }

    </script>
    <style>
        .condition_div {
            float: left;
            width: 48%;
            margin-right: 1%;
            margin-left: 1%;
            margin-top: 2%;
        }

        .condition_div_label {
            width: 100%;
            height: auto;
            background-color: #f2f2f2;
            font-size: 14px;
            padding-top: 1%;
            padding-bottom: 1%;
        }
    </style>
</head>
<body>
<h3 style="text-align: center">${name}考评档案</h3>
<sys:message content="${message}"/>
<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<input id="personId" name="personId" type="hidden" value="${personId}"/>
<input id="idNumber" name="idNumber" type="hidden" value="${idNumber}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <%-- <th>人员</th>--%>
        <th>年份</th>
        <th>1月</th>
        <th>2月</th>
        <th>3月</th>
        <th>4月</th>
        <th>5月</th>
        <th>6月</th>
        <th>7月</th>
        <th>8月</th>
        <th>9月</th>
        <th>10月</th>
        <th>11月</th>
        <th>12月</th>
        <th>年度</th>
        <th>年度等次</th>
        <th>年度最终分数</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="examRecord">
        <tr>
                <%--	<td>
                            ${examRecord.unit}
                    </td>--%>
            <td>
                    ${examRecord.year}
            </td>
            <c:forEach items="${examRecord.monthList}" var="month">
                <td>
                        ${examRecord.monthSumMap[month]}
                </td>
            </c:forEach>
            <td>
                    ${examRecord.yearEndSum}
            </td>
            <td>
                    ${fns:getDictLabel(examRecord.grades,'performance_grade', '')}
            </td>        <%--年度等次--%>
            <td>
                    ${examRecord.yearSum}
            </td>
        </tr>
    </c:forEach>
    </tbody>
    </tbody>
</table>
<div class="pagination">${page}</div>
<div>
    <form:form id="searchForm" modelAttribute="examRecord" action="" method="post" class="breadcrumb form-search">
        <ul class="ul-form">
            <li><label class="width120">年份：</label>
                <input id="year" name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                       value="${year}"
                       onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});" style="width: 60px;"/>
            </li>
            <li>
                <label>开始月份：</label>
                <input id="startMonth" name="year" type="text" readonly="readonly" maxlength="20"
                       class="input-medium Wdate "
                       value="${startMonth}"
                       onclick="WdatePicker({dateFmt:'MM',isShowClear:false});" style="width: 60px;"/>
                    <%--<form:input path="startMonth" name="startmonth" htmlEscape="false" class="input-medium"/>--%>
                <span style="margin-left: 4px">————</span>
                <label style="margin-left:4px;width: auto">结束月份：</label>
                <input id="endMonth" name="year" type="text" readonly="readonly" maxlength="20"
                       class="input-medium Wdate "
                       value="${endMonth}"
                       onclick="WdatePicker({dateFmt:'MM',isShowClear:false});" style="width: 60px;"/>
            </li>
            <li class="btns"><input id="btn" class="btn btn-primary" type="button" value="查询" onclick="setValue()"/>
            </li>
                <%--  <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                          onclick="openForm('${ctx}/exam/examStandardBaseInfo/form')"/></li>--%>
            <li class="clearfix"></li>
        </ul>
    </form:form>
    <div class="modal-custom-content" style="text-align: center;">
        <div class="condition_div bonus_point_div">
            <div class="condition_div_label">公共加分情况</div>
            <textarea id="bonus_point_text" style="resize:none;width: 95%;margin-top: 2%;min-height: 200px" readonly="readonly"></textarea>
        </div>

        <div class="condition_div deduction_div">
            <div class="condition_div_label">公共减分情况</div>
            <textarea id="deduction_text" style="resize:none;width: 95% ;margin-top: 2%;min-height: 200px" readonly="readonly"></textarea>
        </div>
    </div>
    <div class="modal-custom-content" style="text-align: center;">
        <div class="condition_div bonus_point_div">
            <div class="condition_div_label">其他加扣分情况</div>
            <textarea id="other_point_text" style="resize:none;width: 95%;margin-top: 2%;min-height: 200px" readonly="readonly"></textarea>
        </div>
    </div>
</div>
<script>
    function setValue() {
        var year = $("#year").val();//年份
        var startMonth = $("#startMonth").val();//开始月份
        var endMonth = $("#endMonth").val();//结束月份
        var idNumber = $("#idNumber").val();//人员身份证号
        if(year ==''){
            $.jBox.tip("年份不能为空")
            return;
        }
        $.ajax({
            url: "${ctx}/exam/examRecord/condition",
            type: "post",
            data: {year: year, startMonth: startMonth, endMonth: endMonth, idNumber: idNumber,type:'person'},
            dataType: "json",
            success: function (data) {
                $.jBox.tip("查询成功");
                if (typeof data.result['minusPoints'] != 'undefined' && data.result['minusPoints'].length > 0) {//扣分
                    var message = '';
                    for (var i = 0; i < data.result['minusPoints'].length > 0; i++) {
                        message += i + 1 + ". " + data.result['minusPoints'][i] + "\n"
                    }
                    $("#deduction_text").val(message);
                }
                if (typeof data.result['bonusPoints']!='undefined' && data.result['bonusPoints'].length > 0) {//加分
                    var message = '';
                    for (var i = 0; i < data.result['bonusPoints'].length > 0; i++) {
                        message += i + 1 + ". " + data.result['bonusPoints'][i] + "\n"
                    }
                    //document.getElementById("bonus_point_text").innerText = message
                    $("#bonus_point_text").val(message);
                }
                if (typeof data.result['otherPoints']!= 'undefined' && data.result['otherPoints'].length > 0) {//加分
                    var message = '';
                    for (var i = 0; i < data.result['otherPoints'].length > 0; i++) {
                        console.log(data.result['otherPoints'][i])
                        message += i + 1 + ". " + data.result['otherPoints'][i] + "\n"
                    }
                    //document.getElementById("bonus_point_text").innerText = message
                    $("#other_point_text").val(message);
                }

            },
            error: function (data) {
                $.jBox.tip("发生错误，请重试")
            }
        });
    }
</script>
</body>
</html>