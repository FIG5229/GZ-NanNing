<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<html>
<head>
    <title>考勤记录管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            /* $("#export").click(
                 function(){
                     var submit = function (v, h, f) {
                         if (v == 'all') {
                             $("#searchForm").attr("action","
            ${ctx}/affair/affairAttendance/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairAttendance/list");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairAttendance/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairAttendance/list");
                        }
                        if (v == 'cancel') {
                            $.jBox.tip('已取消');
                        }
                        return true;
                    };
                    $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
                }
            );*/

            $("#print").click(function(){
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
                    pageTitle: "打印",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: false,
                    afterPrint:function (){
                    }
                });
            });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<%--<div class="modal-custom-main-title"></div>--%>
<%--<form:form id="searchForm" modelAttribute="affairAttendance" action="${ctx}/affair/affairAttendance/detail" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="idNumber" name="idNumber" type="hidden" value="${affairAttendance.idNumber}"/>
    <input id="name" name="name" type="hidden" value="${affairAttendance.name}"/>
    <input id="fileName" name="fileName" type="hidden" value="考勤记录.xlsx"/>
    <ul class="ul-form">
        <li><label>年度：</label>
            <form:input path="year" htmlEscape="false" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>--%>
<sys:message content="${message}"/>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在单位：</span><span
                                    class="modal-custom-info2-value">${affairAttendanceChild.unit}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年度：</span><span
                                    class="modal-custom-info2-value">${affairAttendanceChild.year}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col3">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">月度：</span><span
                                    class="modal-custom-info2-value">${affairAttendanceChild.month}</span></div>
                        </div>
                    </div>
                    <table id="contentTable2" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>身份证号</th>
                            <th>执勤岗位</th>
                            <th>线路岗位</th>
                            <th>工时制</th>
                            <th>1</th>
                            <th>2</th>
                            <th>3</th>
                            <th>4</th>
                            <th>5</th>
                            <th>6</th>
                            <th>7</th>
                            <th>8</th>
                            <th>9</th>
                            <th>10</th>
                            <th>11</th>
                            <th>12</th>
                            <th>13</th>
                            <th>14</th>
                            <th>15</th>
                            <th>16</th>
                            <th>17</th>
                            <th>18</th>
                            <th>19</th>
                            <th>20</th>
                            <th>21</th>
                            <th>22</th>
                            <th>23</th>
                            <th>24</th>
                            <th>25</th>
                            <th>26</th>
                            <th>27</th>
                            <th>28</th>
                            <th>29</th>
                            <th>30</th>
                            <th>31</th>
                            <th>当月工时</th>
                            <th>缺勤</th>
                            <th>工伤</th>
                            <th>年休</th>
                            <th>出差</th>
                            <th>执勤</th>
                            <th>加班</th>
                            <th>零星加班</th>
                            <th>备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                ${affairAttendanceChild.name}
                            </td>
                            <td>
                                ${affairAttendanceChild.idNumber}
                            </td>
                            <td>
                                ${fns:getDictLabel(affairAttendanceChild.policeType, 'attendence_police_types', '')}
                            </td>
                            <td>
                                ${fns:getDictLabel(affairAttendanceChild.linePost, 'attendence_police_types', '')}
                            </td>
                            <td>
                                ${fns:getDictLabel(affairAttendanceChild.workType, 'attendence_work_types', '')}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day1 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day1 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day1, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day2 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day2 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day2, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day3 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day3 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day3, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day4 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day4 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day4, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day5 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day5 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day5, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day6 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day6 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day6, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day7 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day7 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day7, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day8 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day8 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day8, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day9 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day9 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day9, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day10 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day10 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day10, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day11 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day11 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day11, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day12 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day12 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day12, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day13 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day13 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day13, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day14 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day14 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day14, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day15 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day15 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day15, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day16 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day16 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day16, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day17 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day17 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day17, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day18 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day18 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day18, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day19 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day19 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day19, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day20 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day20 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day20, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day21 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day21 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day21, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day22 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day22 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day22, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day23 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day23 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day23, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day24 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day24 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day24, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day25 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day25 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day25, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day26 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day26 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day26, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day27 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day27 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day27, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day28 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day28 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day28, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day29 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day29 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day29, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day30 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day30 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day30, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${affairAttendanceChild.day31 == '0'}">
                                        <font>√</font>
                                    </c:when>
                                    <c:when test="${affairAttendanceChild.day31 == '1'}">
                                        <font color="red">○</font>
                                    </c:when>
                                    <c:otherwise>
                                        ${fns:getDictLabel(affairAttendanceChild.day31, 'affair_attendence_types', '')}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                ${affairAttendanceChild.monthHours}
                            </td>
                            <td>
                                ${affairAttendanceChild.lackWork}
                            </td>
                            <td>
                                ${affairAttendanceChild.workInjury}
                            </td>
                            <td>
                                ${affairAttendanceChild.yearWeak}
                            </td>
                            <td>
                                ${affairAttendanceChild.goOut}
                            </td>
                            <td>
                                ${affairAttendanceChild.inWork}
                            </td>
                            <td>
                                ${affairAttendanceChild.overtime}
                            </td>
                            <td>
                                ${affairAttendanceChild.overtimeChip}
                            </td>
                            <td>
                                ${affairAttendanceChild.remarks}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div>
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>