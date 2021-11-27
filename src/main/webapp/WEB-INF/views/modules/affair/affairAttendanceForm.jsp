<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>考勤记录管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            var date = new Date().getDate();
        });

        function adjust(width,height)
        {
            var table = $("table");
            var tableId = table.attr('id');
            var freezeRowNum = table.attr('freezeRowNum');
            var freezeColumnNum = table.attr('freezeColumnNum');
            var width = window.screen.availWidth-width;
            var height =window.screen.availHeight-document.body.scrollTop-height;
            width = width == null ? pageWidth() : width;
            height = height == null ? pageHeight() : height;

            if (typeof(freezeRowNum) != 'undefined' || typeof(freezeColumnNum) != 'undefined') {
                freezeTable(table, freezeRowNum || 0, freezeColumnNum || 0,width , height);
                var flag = false;
                $(window).resize(function() {
                    if (flag)
                        return ;

                    setTimeout(function() {
                        adjustTableSize(tableId,width , height);
                        flag = false;
                    }, 200);

                    flag = true;
                });
            }
        }

        function addRow(list, idx, tpl, row, refresh) {
            $(list).append(Mustache.render(tpl, {
                idx: idx, delBtn: true, row: row
            }));
            $(list + idx).find("select").each(function () {
                $(this).val($(this).attr("data-value"));
            });
            $(list + idx).find("input[type='checkbox'], input[type='radio']").each(function () {
                var ss = $(this).attr("data-value").split(',');
                for (var i = 0; i < ss.length; i++) {
                    if ($(this).val() == ss[i]) {
                        $(this).attr("checked", "checked");
                    }
                }
            });
            // if(refresh){
            //     adjust(500,520);
            // }
        }

        function delRow(obj, prefix) {
            var id = $(prefix + "_id");
            var delFlag = $(prefix + "_delFlag");
            if (id.val() == "") {
                $(obj).parent().parent().remove();
            } else if (delFlag.val() == "0") {
                delFlag.val("1");
                $(obj).html("&divide;").attr("title", "撤销删除");
                $(obj).parent().parent().addClass("error");
            } else if (delFlag.val() == "1") {
                delFlag.val("0");
                $(obj).html("&times;").attr("title", "删除");
                $(obj).parent().parent().removeClass("error");
            }
        }
    </script>
</head>
<body>
<br/>
<form:form id="inputForm" modelAttribute="affairAttendance" action="${ctx}/affair/affairAttendance/save" method="post"
           class="form-search">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <ul class="ul-form">
        <li>
            <label class="control-label">所在单位：</label>
            <sys:treeselect id="unit" name="unitId" value="${affairAttendance.unitId}" labelName="unit"
                            labelValue="${affairAttendance.unit}"
                            title="所在单位" url="/affair/affairLaborOffice/treeData" cssClass="required" allowClear="true"
                            dataMsgRequired="必填信息"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </li>
        <li>
            <label>年度：</label>
            <form:input path="year" htmlEscape="false" class="input-small number required" readonly="true"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </li>
        <li>
            <label>月度：</label>
            <form:input path="mouth" htmlEscape="false" class="input-small number required" readonly="true"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </li>
    </ul>
    <div class="control-group">
        <label class="control-label">考勤记录信息：</label>
    </div>
    <div class="control-group">
        <div class="">
            <table id="contentTable" class="table table-striped table-bordered table-condensed" freezeRowNum="3"
                   freezeColumnNum="1">
                <thead>
                <tr>
                    <th class="hide"></th>
                    <th rowspan="3" style="text-align: center;vertical-align:middle">姓名</th>
                    <th rowspan="3" style="text-align: center;vertical-align:middle">身份证号</th>
                    <th rowspan="3" style="text-align: center;vertical-align:middle">执勤岗位</th>
                    <th rowspan="3" style="text-align: center;vertical-align:middle">线路岗位</th>
                    <th rowspan="3" style="text-align: center;vertical-align:middle">用工形式</th>
                    <c:forEach items="${flagList}" var="list" >
                        <%--                <th style="text-align: center;vertical-align:middle">${list.date}</th>--%>
                        <th style="text-align: center;vertical-align:middle"> ${fn:substring(list.date, 8, 10)}</th>
                    </c:forEach>
                    <th rowspan="3" style="text-align: center;vertical-align:middle">当月工时</th>
                    <th rowspan="3" style="text-align: center;vertical-align:middle">加发天数</th>
                    <th rowspan="3" style="text-align: center;vertical-align:middle">备注</th>
                </tr>
                <tr>
                    <c:forEach items="${flagList}" var="list" >
                        <th style="text-align: center"> ${fns:getDictLabel(list.week, 'attendance_week', '')}</th>
                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach items="${flagList}" var="list">
                        <th style="text-align: center;vertical-align:middle">
                                <%-- <c:choose>
                             <c:when test="${'六'.equals(list.weekday) || '日'.equals(list.weekday) || holiday.contains(list.date)}">
                                 休
                             </c:when>
                             <c:otherwise>
                                 工
                             </c:otherwise>
                         </c:choose>--%>
                                ${fns:getDictLabel(list.name, 'attendance_day_type', '')}
                        </th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody id="affairAttendanceChildList">
                </tbody>
                <%--
                <shiro:hasPermission name="affair:affairAttendance:edit">
                    <tfoot>
                    <tr>
                        <td colspan="46"><a href="javascript:"
                                            onclick="addRow('#affairAttendanceChildList', affairAttendanceChildRowIdx, affairAttendanceChildTpl,null,true);affairAttendanceChildRowIdx = affairAttendanceChildRowIdx + 1;"
                                            class="btn">新增</a></td>
                    </tr>
                    </tfoot>
                </shiro:hasPermission>
                --%>
            </table>
            <script type="text/template" id="affairAttendanceChildTpl">//<!--
						<tr id="affairAttendanceChildList{{idx}}">
							<td class="hide">
								<input id="affairAttendanceChildList{{idx}}_id" name="affairAttendanceChildList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="affairAttendanceChildList{{idx}}_delFlag" name="affairAttendanceChildList[{{idx}}].delFlag" type="hidden" value="0"/>
								<input id="affairAttendanceChildList{{idx}}_lockFlag" name="affairAttendanceChildList[{{idx}}].lockFlag" type="hidden" value="{{row.lockFlag}}"/>
							</td>
							<td>
								<input id="affairAttendanceChildList{{idx}}_name" name="affairAttendanceChildList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small " style="width: 48px;"/>
							</td>
							<td>
								<input id="affairAttendanceChildList{{idx}}_idNumber" name="affairAttendanceChildList[{{idx}}].idNumber" type="text" value="{{row.idNumber}}" class="input-small " style="width: 160px;"/>
							</td>
							<td>
								<select id="affairAttendanceChildList{{idx}}_policeType" name="affairAttendanceChildList[{{idx}}].policeType" data-value="{{row.policeType}}" class="input-small " style="width: 68px;" {{row.lock32}}>
									<option value=""></option>
									<c:forEach items="${fns:getDictList('attendence_police_types')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="affairAttendanceChildList{{idx}}_linePost" name="affairAttendanceChildList[{{idx}}].linePost" data-value="{{row.linePost}}" class="input-small " style="width: 68px;" {{row.lock33}}>
									<option value=""></option>
									<c:forEach items="${fns:getDictList('attendence_police_types')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="affairAttendanceChildList{{idx}}_workType" name="affairAttendanceChildList[{{idx}}].workType" data-value="{{row.workType}}" class="input-small " style="width: 78px;" {{row.lock34}}>
									<option value=""></option>
									<c:forEach items="${fns:getDictList('attendence_work_types')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>//-->
                <c:choose>
                    <c:when test="${flagList.size() == 30}">
                        //<!--<td>
                        <select id="affairAttendanceChildList{{idx}}_day1" name="affairAttendanceChildList[{{idx}}].day1" data-value="{{row.day1}}" class="input-small " style="width: 48px;" {{row.lock1}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day2" name="affairAttendanceChildList[{{idx}}].day2" data-value="{{row.day2}}" class="input-small " style="width: 48px" {{row.lock2}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day3" name="affairAttendanceChildList[{{idx}}].day3" data-value="{{row.day3}}" class="input-small " style="width: 48px" {{row.lock3}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day4" name="affairAttendanceChildList[{{idx}}].day4" data-value="{{row.day4}}" class="input-small" style="width: 48px" {{row.lock4}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day5" name="affairAttendanceChildList[{{idx}}].day5" data-value="{{row.day5}}" class="input-small" style="width: 48px"  {{row.lock5}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day6" name="affairAttendanceChildList[{{idx}}].day6" data-value="{{row.day6}}" class="input-small" style="width: 48px"  {{row.lock6}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day7" name="affairAttendanceChildList[{{idx}}].day7" data-value="{{row.day7}}" class="input-small" style="width: 48px" {{row.lock7}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day8" name="affairAttendanceChildList[{{idx}}].day8" data-value="{{row.day8}}" class="input-small" style="width: 48px" {{row.lock8}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day9" name="affairAttendanceChildList[{{idx}}].day9" data-value="{{row.day9}}" class="input-small" style="width: 48px" {{row.lock9}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day10" name="affairAttendanceChildList[{{idx}}].day10" data-value="{{row.day10}}" class="input-small" style="width: 48px" {{row.lock10}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day11" name="affairAttendanceChildList[{{idx}}].day11" data-value="{{row.day11}}" class="input-small" style="width: 48px" {{row.lock11}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day12" name="affairAttendanceChildList[{{idx}}].day12" data-value="{{row.day12}}" class="input-small" style="width: 48px" {{row.lock12}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day13" name="affairAttendanceChildList[{{idx}}].day13" data-value="{{row.day13}}" class="input-small" style="width: 48px"  {{row.lock13}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day14" name="affairAttendanceChildList[{{idx}}].day14" data-value="{{row.day14}}" class="input-small" style="width: 48px" {{row.lock14}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day15" name="affairAttendanceChildList[{{idx}}].day15" data-value="{{row.day15}}" class="input-small" style="width: 48px" {{row.lock15}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day16" name="affairAttendanceChildList[{{idx}}].day16" data-value="{{row.day16}}" class="input-small" style="width: 48px" {{row.lock16}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day17" name="affairAttendanceChildList[{{idx}}].day17" data-value="{{row.day17}}" class="input-small" style="width: 48px" {{row.lock17}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day18" name="affairAttendanceChildList[{{idx}}].day18" data-value="{{row.day18}}" class="input-small" style="width: 48px" {{row.lock18}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day19" name="affairAttendanceChildList[{{idx}}].day19" data-value="{{row.day19}}" class="input-small" style="width: 48px" {{row.lock19}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day20" name="affairAttendanceChildList[{{idx}}].day20" data-value="{{row.day20}}" class="input-small" style="width: 48px" {{row.lock20}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day21" name="affairAttendanceChildList[{{idx}}].day21" data-value="{{row.day21}}" class="input-small" style="width: 48px" {{row.lock21}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day22" name="affairAttendanceChildList[{{idx}}].day22" data-value="{{row.day22}}" class="input-small" style="width: 48px" {{row.lock22}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day23" name="affairAttendanceChildList[{{idx}}].day23" data-value="{{row.day23}}" class="input-small" style="width: 48px" {{row.lock23}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day24" name="affairAttendanceChildList[{{idx}}].day24" data-value="{{row.day24}}" class="input-small" style="width: 48px" {{row.lock24}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day25" name="affairAttendanceChildList[{{idx}}].day25" data-value="{{row.day25}}" class="input-small" style="width: 48px" {{row.lock25}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day26" name="affairAttendanceChildList[{{idx}}].day26" data-value="{{row.day26}}" class="input-small" style="width: 48px" {{row.lock26}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day27" name="affairAttendanceChildList[{{idx}}].day27" data-value="{{row.day27}}" class="input-small" style="width: 48px" {{row.lock27}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day28" name="affairAttendanceChildList[{{idx}}].day28" data-value="{{row.day28}}" class="input-small" style="width: 48px" {{row.lock28}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day29" name="affairAttendanceChildList[{{idx}}].day29" data-value="{{row.day29}}" class="input-small" style="width: 48px"  {{row.lock29}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day30" name="affairAttendanceChildList[{{idx}}].day30" data-value="{{row.day30}}" class="input-small" style="width: 48px" {{row.lock30}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_monthHours" name="affairAttendanceChildList[{{idx}}].monthHours" type="text" value="{{row.monthHours}}" class="input-small " style="width: 48px;" {{row.lock35}}/>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_jfDays" name="affairAttendanceChildList[{{idx}}].jfDays" type="text" value="{{row.jfDays}}" class="input-small " style="width: 48px;" {{row.lock36}}/>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_beizhu" name="affairAttendanceChildList[{{idx}}].beizhu" type="text" value="{{row.beizhu}}" class="input-small "/>
                        </td>
                        <%--
                        <shiro:hasPermission name="affair:affairAttendance:edit"><td class="text-center" width="10">
                            {{#delBtn}}<span class="close" onclick="delRow(this, '#affairAttendanceChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
                            </td></shiro:hasPermission>
                            --%>
                        </tr>//-->
                    </c:when>
                    <c:when test="${flagList.size() == 31 }">
                        //<!--<td>
                        <select id="affairAttendanceChildList{{idx}}_day1" name="affairAttendanceChildList[{{idx}}].day1" data-value="{{row.day1}}" class="input-small "  style="width: 48px" {{row.lock1}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day2" name="affairAttendanceChildList[{{idx}}].day2" data-value="{{row.day2}}" class="input-small " style="width: 48px" {{row.lock2}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day3" name="affairAttendanceChildList[{{idx}}].day3" data-value="{{row.day3}}" class="input-small "  style="width: 48px" {{row.lock3}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day4" name="affairAttendanceChildList[{{idx}}].day4" data-value="{{row.day4}}" class="input-small"  style="width: 48px" {{row.lock4}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day5" name="affairAttendanceChildList[{{idx}}].day5" data-value="{{row.day5}}" class="input-small"  style="width: 48px" {{row.lock5}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day6" name="affairAttendanceChildList[{{idx}}].day6" data-value="{{row.day6}}" class="input-small" style="width: 48px"  {{row.lock6}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day7" name="affairAttendanceChildList[{{idx}}].day7" data-value="{{row.day7}}" class="input-small" style="width: 48px"  {{row.lock7}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day8" name="affairAttendanceChildList[{{idx}}].day8" data-value="{{row.day8}}" class="input-small"  style="width: 48px" {{row.lock8}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day9" name="affairAttendanceChildList[{{idx}}].day9" data-value="{{row.day9}}" class="input-small"  style="width: 48px" {{row.lock9}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day10" name="affairAttendanceChildList[{{idx}}].day10" data-value="{{row.day10}}" class="input-small" style="width: 48px"  {{row.lock10}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day11" name="affairAttendanceChildList[{{idx}}].day11" data-value="{{row.day11}}" class="input-small"  style="width: 48px" {{row.lock11}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day12" name="affairAttendanceChildList[{{idx}}].day12" data-value="{{row.day12}}" class="input-small" style="width: 48px"  {{row.lock12}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day13" name="affairAttendanceChildList[{{idx}}].day13" data-value="{{row.day13}}" class="input-small"  style="width: 48px" {{row.lock13}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day14" name="affairAttendanceChildList[{{idx}}].day14" data-value="{{row.day14}}" class="input-small" style="width: 48px"  {{row.lock14}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day15" name="affairAttendanceChildList[{{idx}}].day15" data-value="{{row.day15}}" class="input-small" style="width: 48px"  {{row.lock15}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day16" name="affairAttendanceChildList[{{idx}}].day16" data-value="{{row.day16}}" class="input-small" style="width: 48px"  {{row.lock16}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day17" name="affairAttendanceChildList[{{idx}}].day17" data-value="{{row.day17}}" class="input-small"  style="width: 48px" {{row.lock17}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day18" name="affairAttendanceChildList[{{idx}}].day18" data-value="{{row.day18}}" class="input-small"  style="width: 48px" {{row.lock18}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day19" name="affairAttendanceChildList[{{idx}}].day19" data-value="{{row.day19}}" class="input-small"  style="width: 48px" {{row.lock19}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day20" name="affairAttendanceChildList[{{idx}}].day20" data-value="{{row.day20}}" class="input-small"  style="width: 48px" {{row.lock20}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day21" name="affairAttendanceChildList[{{idx}}].day21" data-value="{{row.day21}}" class="input-small"  style="width: 48px" {{row.lock21}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day22" name="affairAttendanceChildList[{{idx}}].day22" data-value="{{row.day22}}" class="input-small"  style="width: 48px" {{row.lock22}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day23" name="affairAttendanceChildList[{{idx}}].day23" data-value="{{row.day23}}" class="input-small" style="width: 48px"  {{row.lock23}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day24" name="affairAttendanceChildList[{{idx}}].day24" data-value="{{row.day24}}" class="input-small" style="width: 48px"  {{row.lock24}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day25" name="affairAttendanceChildList[{{idx}}].day25" data-value="{{row.day25}}" class="input-small" style="width: 48px"  {{row.lock25}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day26" name="affairAttendanceChildList[{{idx}}].day26" data-value="{{row.day26}}" class="input-small"  style="width: 48px" {{row.lock26}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day27" name="affairAttendanceChildList[{{idx}}].day27" data-value="{{row.day27}}" class="input-small" style="width: 48px"  {{row.lock27}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day28" name="affairAttendanceChildList[{{idx}}].day28" data-value="{{row.day28}}" class="input-small" style="width: 48px"  {{row.lock28}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day29" name="affairAttendanceChildList[{{idx}}].day29" data-value="{{row.day29}}" class="input-small"  style="width: 48px" {{row.lock29}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day30" name="affairAttendanceChildList[{{idx}}].day30" data-value="{{row.day30}}" class="input-small"  style="width: 48px" {{row.lock30}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day31" name="affairAttendanceChildList[{{idx}}].day31" data-value="{{row.day31}}" class="input-small" style="width: 48px"  {{row.lock31}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_monthHours" name="affairAttendanceChildList[{{idx}}].monthHours" type="text" value="{{row.monthHours}}" class="input-small " style="width: 48px" {{row.lock35}}/>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_jfDays" name="affairAttendanceChildList[{{idx}}].jfDays" type="text" value="{{row.jfDays}}" class="input-small " style="width: 48px" {{row.lock36}}/>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_beizhu" name="affairAttendanceChildList[{{idx}}].beizhu" type="text" value="{{row.beizhu}}" class="input-small " style="width: 48px" />
                        </td>
                        <%--
                        <shiro:hasPermission name="affair:affairAttendance:edit"><td class="text-center" width="10">
                            {{#delBtn}}<span class="close" onclick="delRow(this, '#affairAttendanceChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
                            </td></shiro:hasPermission>
                            --%>
                        </tr>//-->
                    </c:when>
                    <c:when test="${flagList.size() == 29 }">
                        //<!--<td>
                        <select id="affairAttendanceChildList{{idx}}_day1" name="affairAttendanceChildList[{{idx}}].day1" data-value="{{row.day1}}" class="input-small "  style="width: 48px" {{row.lock1}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day2" name="affairAttendanceChildList[{{idx}}].day2" data-value="{{row.day2}}" class="input-small " style="width: 48px" {{row.lock2}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day3" name="affairAttendanceChildList[{{idx}}].day3" data-value="{{row.day3}}" class="input-small "  style="width: 48px" {{row.lock3}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day4" name="affairAttendanceChildList[{{idx}}].day4" data-value="{{row.day4}}" class="input-small"  style="width: 48px" {{row.lock4}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day5" name="affairAttendanceChildList[{{idx}}].day5" data-value="{{row.day5}}" class="input-small"  style="width: 48px" {{row.lock5}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day6" name="affairAttendanceChildList[{{idx}}].day6" data-value="{{row.day6}}" class="input-small"  style="width: 48px" {{row.lock6}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day7" name="affairAttendanceChildList[{{idx}}].day7" data-value="{{row.day7}}" class="input-small"  style="width: 48px" {{row.lock7}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day8" name="affairAttendanceChildList[{{idx}}].day8" data-value="{{row.day8}}" class="input-small"  style="width: 48px" {{row.lock8}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day9" name="affairAttendanceChildList[{{idx}}].day9" data-value="{{row.day9}}" class="input-small"  style="width: 48px" {{row.lock9}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day10" name="affairAttendanceChildList[{{idx}}].day10" data-value="{{row.day10}}" class="input-small"  style="width: 48px" {{row.lock10}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day11" name="affairAttendanceChildList[{{idx}}].day11" data-value="{{row.day11}}" class="input-small"  style="width: 48px" {{row.lock11}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day12" name="affairAttendanceChildList[{{idx}}].day12" data-value="{{row.day12}}" class="input-small"  style="width: 48px" {{row.lock12}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day13" name="affairAttendanceChildList[{{idx}}].day13" data-value="{{row.day13}}" class="input-small"  style="width: 48px" {{row.lock13}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day14" name="affairAttendanceChildList[{{idx}}].day14" data-value="{{row.day14}}" class="input-small"  style="width: 48px" {{row.lock14}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day15" name="affairAttendanceChildList[{{idx}}].day15" data-value="{{row.day15}}" class="input-small"  style="width: 48px" {{row.lock15}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day16" name="affairAttendanceChildList[{{idx}}].day16" data-value="{{row.day16}}" class="input-small" style="width: 48px"  {{row.lock16}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day17" name="affairAttendanceChildList[{{idx}}].day17" data-value="{{row.day17}}" class="input-small"  style="width: 48px" {{row.lock17}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day18" name="affairAttendanceChildList[{{idx}}].day18" data-value="{{row.day18}}" class="input-small"  style="width: 48px" {{row.lock18}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day19" name="affairAttendanceChildList[{{idx}}].day19" data-value="{{row.day19}}" class="input-small"  style="width: 48px" {{row.lock19}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day20" name="affairAttendanceChildList[{{idx}}].day20" data-value="{{row.day20}}" class="input-small"  style="width: 48px" {{row.lock20}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day21" name="affairAttendanceChildList[{{idx}}].day21" data-value="{{row.day21}}" class="input-small"  style="width: 48px" {{row.lock21}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day22" name="affairAttendanceChildList[{{idx}}].day22" data-value="{{row.day22}}" class="input-small"  style="width: 48px" {{row.lock22}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day23" name="affairAttendanceChildList[{{idx}}].day23" data-value="{{row.day23}}" class="input-small"  style="width: 48px" {{row.lock23}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day24" name="affairAttendanceChildList[{{idx}}].day24" data-value="{{row.day24}}" class="input-small"  style="width: 48px" {{row.lock24}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day25" name="affairAttendanceChildList[{{idx}}].day25" data-value="{{row.day25}}" class="input-small"  style="width: 48px" {{row.lock25}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day26" name="affairAttendanceChildList[{{idx}}].day26" data-value="{{row.day26}}" class="input-small"  style="width: 48px" {{row.lock26}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day27" name="affairAttendanceChildList[{{idx}}].day27" data-value="{{row.day27}}" class="input-small"  style="width: 48px" {{row.lock27}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day28" name="affairAttendanceChildList[{{idx}}].day28" data-value="{{row.day28}}" class="input-small"  style="width: 48px" {{row.lock28}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day29" name="affairAttendanceChildList[{{idx}}].day29" data-value="{{row.day29}}" class="input-small"  style="width: 48px" {{row.lock29}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_monthHours" name="affairAttendanceChildList[{{idx}}].monthHours" type="text" value="{{row.monthHours}}" class="input-small " style="width: 48px" {{row.lock35}}/>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_jfDays" name="affairAttendanceChildList[{{idx}}].jfDays" type="text" value="{{row.jfDays}}" class="input-small " style="width: 48px" {{row.lock36}}/>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_beizhu" name="affairAttendanceChildList[{{idx}}].beizhu" type="text" value="{{row.beizhu}}" class="input-small " style="width: 48px" />
                        </td>
                        <%--
                        <shiro:hasPermission name="affair:affairAttendance:edit"><td class="text-center" width="10">
                            {{#delBtn}}<span class="close" onclick="delRow(this, '#affairAttendanceChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
                            </td></shiro:hasPermission>
                            --%>
                        </tr>//-->
                    </c:when>
                    <c:when test="${flagList.size() == 28 }">
                        //<!--<td>
                        <select id="affairAttendanceChildList{{idx}}_day1" name="affairAttendanceChildList[{{idx}}].day1" data-value="{{row.day1}}" class="input-small " style="width: 48px"  {{row.lock1}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day2" name="affairAttendanceChildList[{{idx}}].day2" data-value="{{row.day2}}" class="input-small " style="width: 48px" {{row.lock2}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day3" name="affairAttendanceChildList[{{idx}}].day3" data-value="{{row.day3}}" class="input-small "  style="width: 48px" {{row.lock3}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day4" name="affairAttendanceChildList[{{idx}}].day4" data-value="{{row.day4}}" class="input-small"  style="width: 48px" {{row.lock4}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day5" name="affairAttendanceChildList[{{idx}}].day5" data-value="{{row.day5}}" class="input-small"  style="width: 48px" {{row.lock5}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day6" name="affairAttendanceChildList[{{idx}}].day6" data-value="{{row.day6}}" class="input-small"  style="width: 48px" {{row.lock6}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day7" name="affairAttendanceChildList[{{idx}}].day7" data-value="{{row.day7}}" class="input-small"  style="width: 48px" {{row.lock7}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day8" name="affairAttendanceChildList[{{idx}}].day8" data-value="{{row.day8}}" class="input-small"  style="width: 48px" {{row.lock8}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day9" name="affairAttendanceChildList[{{idx}}].day9" data-value="{{row.day9}}" class="input-small"  style="width: 48px" {{row.lock9}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day10" name="affairAttendanceChildList[{{idx}}].day10" data-value="{{row.day10}}" class="input-small"  style="width: 48px" {{row.lock10}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day11" name="affairAttendanceChildList[{{idx}}].day11" data-value="{{row.day11}}" class="input-small"  style="width: 48px" {{row.lock11}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day12" name="affairAttendanceChildList[{{idx}}].day12" data-value="{{row.day12}}" class="input-small"  style="width: 48px" {{row.lock12}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day13" name="affairAttendanceChildList[{{idx}}].day13" data-value="{{row.day13}}" class="input-small"  style="width: 48px" {{row.lock13}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day14" name="affairAttendanceChildList[{{idx}}].day14" data-value="{{row.day14}}" class="input-small"  style="width: 48px" {{row.lock14}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day15" name="affairAttendanceChildList[{{idx}}].day15" data-value="{{row.day15}}" class="input-small"  style="width: 48px" {{row.lock15}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day16" name="affairAttendanceChildList[{{idx}}].day16" data-value="{{row.day16}}" class="input-small"  style="width: 48px" {{row.lock16}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day17" name="affairAttendanceChildList[{{idx}}].day17" data-value="{{row.day17}}" class="input-small"  style="width: 48px" {{row.lock17}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day18" name="affairAttendanceChildList[{{idx}}].day18" data-value="{{row.day18}}" class="input-small"  style="width: 48px" {{row.lock18}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day19" name="affairAttendanceChildList[{{idx}}].day19" data-value="{{row.day19}}" class="input-small"  style="width: 48px" {{row.lock19}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day20" name="affairAttendanceChildList[{{idx}}].day20" data-value="{{row.day20}}" class="input-small"  style="width: 48px" {{row.lock20}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day21" name="affairAttendanceChildList[{{idx}}].day21" data-value="{{row.day21}}" class="input-small"  style="width: 48px" {{row.lock21}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day22" name="affairAttendanceChildList[{{idx}}].day22" data-value="{{row.day22}}" class="input-small"  style="width: 48px" {{row.lock22}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day23" name="affairAttendanceChildList[{{idx}}].day23" data-value="{{row.day23}}" class="input-small"  style="width: 48px" {{row.lock23}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day24" name="affairAttendanceChildList[{{idx}}].day24" data-value="{{row.day24}}" class="input-small"  style="width: 48px" {{row.lock24}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day25" name="affairAttendanceChildList[{{idx}}].day25" data-value="{{row.day25}}" class="input-small"  style="width: 48px" {{row.lock25}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day26" name="affairAttendanceChildList[{{idx}}].day26" data-value="{{row.day26}}" class="input-small"  style="width: 48px" {{row.lock26}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day27" name="affairAttendanceChildList[{{idx}}].day27" data-value="{{row.day27}}" class="input-small"  style="width: 48px" {{row.lock27}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <select id="affairAttendanceChildList{{idx}}_day28" name="affairAttendanceChildList[{{idx}}].day28" data-value="{{row.day28}}" class="input-small"  style="width: 48px" {{row.lock28}}>
                        <option value=""></option>
                        <c:forEach items="${fns:getDictList('affair_attendence_types')}" var="dict">
                            <option value="${dict.value}">${dict.label}</option>
                        </c:forEach>
                        </select>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_monthHours" name="affairAttendanceChildList[{{idx}}].monthHours" type="text" value="{{row.monthHours}}" class="input-small " style="width: 48px" {{row.lock35}}/>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_jfDays" name="affairAttendanceChildList[{{idx}}].jfDays" type="text" value="{{row.jfDays}}" class="input-small " style="width: 48px" {{row.lock36}}/>
                        </td>
                        <td>
                        <input id="affairAttendanceChildList{{idx}}_beizhu" name="affairAttendanceChildList[{{idx}}].beizhu" type="text" value="{{row.beizhu}}" class="input-small " style="width: 48px" />
                        </td>
                        <%--
                        <shiro:hasPermission name="affair:affairAttendance:edit"><td class="text-center" width="10">
                            {{#delBtn}}<span class="close" onclick="delRow(this, '#affairAttendanceChildList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
                            </td></shiro:hasPermission>
                            --%>
                        </tr>//-->
                    </c:when>
                </c:choose>

            </script>
            <script type="text/javascript">
                var affairAttendanceChildRowIdx = 0,
                    affairAttendanceChildTpl = $("#affairAttendanceChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
                $(document).ready(function () {
                    var data = ${fns:toJson(affairAttendance.affairAttendanceChildList)};
                    for (var i = 0; i < data.length; i++) {
                        addRow('#affairAttendanceChildList', affairAttendanceChildRowIdx, affairAttendanceChildTpl, data[i],false);
                        affairAttendanceChildRowIdx = affairAttendanceChildRowIdx + 1;
                    }
                });
            </script>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="affair:affairAttendance:edit">
            <c:if test="${'0' eq showFlag}">
            <input id="btnSubmit" class="btn btn-primary"
                                                                        type="submit"
                                                                        value="保 存"/>&nbsp;
            </c:if>
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
    </div>
</form:form>
<script>
    if ("sucess" == "${saveResult}") {
        parent.$.jBox.close();
    }
</script>
<link rel="stylesheet" href="${ctxStatic}/freezeTable/css/table.css" type="text/css"/>
<script src="${ctxStatic}/freezeTable/js/table.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function() {
        adjust(0,530);
    });
</script>
</body>
</html>