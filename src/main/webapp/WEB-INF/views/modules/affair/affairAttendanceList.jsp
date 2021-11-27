<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤记录管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#unlock").click(function () {
                $("#searchForm").attr("action","${ctx}/affair/affairAttendanceChild/unlock?unitId="+$("#uid").val()+"&year="+$("#year").val()+"&month="+$("#mouth").val());
                $("#searchForm").submit();
            });
            $("#lock").click(function () {
                $("#searchForm").attr("action","${ctx}/affair/affairAttendanceChild/locked?unitId="+$("#uid").val()+"&year="+$("#year").val()+"&month="+$("#mouth").val());
                $("#searchForm").submit();
            });
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairAttendance/export?flag=true&unitId="+$("#uid").val());
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAttendance/list");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: {  '导出数据': 'part','取消':'cancel'} });
					}
			);

			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
                $('#idNum').css('display', 'none');
                $('#quanxuan').css('display', 'none');
                $('.quanxuan').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('.bzh').css('display', 'none');
                $('#bzt').css('display', 'none');
                $('#headTitle').addClass("table table-striped table-bordered table-condensed");
                $('#headTitle').removeAttr('style');
                $('#endTitle').removeAttr('style');
                // $('#headTitle').css('display', 'table-cell');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
                        $('#idNum').css('display', 'table-cell');
                        $('#quanxuan').css('display', 'table-cell');
                        $('.quanxuan').css('display','table-cell');
                        $('#headTitle').css('display', 'none');
                        $('#endTitle').css('display', 'none');
                        $('.bzh').css('display', 'table-cell');
                        $('#bzt').css('display', 'table-cell');
					}
				});
			});
		});
        let height =  $(window).height()-50;
        let width =  $(window).width()-30;
        //添加修改弹窗
        function openAddEditDialog(id) {
            var unitId = $("#uid").val();
            console.log(unitId);
            var unit = $("#unit").val();
            var year = $("#year").val();
            var month = $("#mouth").val();
            var url = "iframe:${ctx}/affair/affairAttendance/form?id="+id+"&unitId="+unitId+"&unit="+unit+"&year="+year+"&month="+month;
            if (id == null || id == undefined){
                url = "iframe:${ctx}/affair/affairAttendance/form?unitId="+unitId+"&unit="+unit+"&year="+year+"&month="+month;
            }
            top.$.jBox.open(url, "考勤记录管理",window.screen.availWidth,window.screen.availHeight-200,{
                persistent: true,
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){
                    self.location.href="${ctx}/affair/affairAttendance/list?unitId="+unitId+"&unit="+unit
                        +"&year="+year+"&mouth="+month+"&name="+$("#name").val()+"&idNumber="+$("#idNumber").val();
                }
            });
        }

        function openAddOneEditDialog(id,idNumber) {
            var unitId = $("#uid").val();
            console.log(unitId);
            var year = $("#year").val();
            var month = $("#mouth").val();
            var unit = $("#unit").val();
            var url = "iframe:${ctx}/affair/affairAttendance/updateOne?id="+id+"&unitId="+unitId+"&unit="+unit+"&year="+year+"&month="+month+"&idNumber="+idNumber;
            if (id == null || id == undefined){
                url = "iframe:${ctx}/affair/affairAttendance/addOne?unitId="+unitId+"&unit="+unit+"&year="+year+"&month="+month;
            }
            top.$.jBox.open(url, "考勤记录管理",width,height,{
                persistent: true,
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){
                    self.location.href="${ctx}/affair/affairAttendance/list?unitId="+unitId+"&unit="+unit
                        +"&year="+year+"&mouth="+month+"&name="+$("#name").val()+"&idNumber="+$("#idNumber").val();
                }
            });
        }

        function excelImp(url){
            top.$.jBox.open("iframe:${ctx}/file/template/download/affairAttendanceDownloadView?id=affair_affairAttendance", "导入", 800, 520, {
                itle: "导入数据", buttons: {"关闭": true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function openlDialog(id) {
            var url = "iframe:${ctx}/affair/affairAttendanceChild/detail?id="+ id;
            top.$.jBox.open(url, "考勤记录", 1000, 450, {
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }
        function importChild(id,month,year){
            var unitId = $("#uid").val();
            console.log(unitId);
            var year = $("#year").val();
            var month = $("#mouth").val();
            var unit = $("#unit").val();
            let url = "iframe:${ctx}/affair/affairAttendance/child/import?unitId="+$("#uid").val()+"&month="+month+"&year="+year+"&fileName=考勤记录.xlsx";
            top.$.jBox.open(url,
                "导入",800,520,{title:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairAttendance/list?unitId="+unitId+"&unit="+unit;}});
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairAttendance" action="${ctx}/affair/affairAttendance/list?unitId=${uid}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input id="uid" name="uid" type="hidden" value="${uid}"/>
        <input id="mo" name="mo" type="hidden" value="${mo}"/>
        <input id="ye" name="ye" type="hidden" value="${ye}"/>
    <input id="fileName" name="fileName" type="hidden" value="考勤记录.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idNumber" htmlEscape="false" class="input-medium"/>
			</li>
            <li><label>年度：</label>
                <form:input id="year" path="year" htmlEscape="false" class="input-medium"/>
            </li>
            <li><label>月度：</label>
                <form:input id="mouth" path="mouth" htmlEscape="false" class="input-medium"/>
            </li>
        </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairAttendance:manage">
            <li class="btns"><input id="unlock" class="btn btn-primary" type="button" value="解锁" /></li>
            <li class="btns"><input id="lock" class="btn btn-primary" type="button" value="加锁"/></li>
<%--            <a href="${ctx}/affair/affairAttendanceChild/unlock?id=${affairAttendanceChild.id}">解锁</a>--%>
<%--            <a href="${ctx}/affair/affairAttendanceChild/locked?id=${affairAttendanceChild.id}">加锁</a>--%>
            <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                    onclick="deleteByIds('${ctx}/affair/affairAttendanceChild/deleteByIds','checkAll','myCheckBox')"/>
            </li>
        </shiro:hasPermission>
        <shiro:hasPermission name="affair:affairAttendance:edit">
            <li class="btns"><input id="btn" class="btn btn-primary" type="button" onclick="openAddEditDialog()"
                                    value="批量添加"/></li>
          <%--  <li class="btns"><input id="btno" class="btn btn-primary" type="button" onclick="openAddOneEditDialog()"
                                    value="添加"/></li>--%>


        </shiro:hasPermission>
        <c:if test="${'8478b98cb7e249a2afe133bed5b5e5d8' eq fns:getUser().id || 'a58a91c7d4db4cd4b639c863c0e48832' eq fns:getUser().id || '76722f985c9e4ee7968fbd2a5d2feb2b' eq fns:getUser().id || 'e25def61e95f4864b15d203d17fcce79' eq fns:getUser().id}">
            <li class="btns">
                <input  class="btn btn-primary"  type="button" value="导入" onclick="importChild('${uid}','${mo}','${ye}')"/>
            </li>
        </c:if>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairAttendance/list?unitId=${affairAttendance.unitId}'"/>
        </li>
        <li class="clearfix"></li>
    </ul>
	</form:form>
	<sys:message content="${message}"/>
    <div id="contentTable">
        <table id="headTitle"  class="table table-striped table-bordered table-condensed"  style="display: none;">
            <tr>
                <td style="text-align: center; font-size: 36px;padding-top: 10px;" >${affairAttendance.year}年${affairAttendance.mouth}月${ui}考勤记录表</td>
            </tr>
        </table>
        <table id="endTitle"  class="table table-striped table-bordered table-condensed"  style="display: none;">
            <tr>
                <td>考勤员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审核人：</td>
            </tr>
        </table>
	<table  class="table table-striped table-bordered table-condensed">
		<thead>
        <tr>
            <th rowspan="3" id="quanxuan">
                <input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>
                全选
            </th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">序号</th>
<%--            <th rowspan="3">单位</th>--%>
            <th rowspan="3" style="width: 60px;text-align: center;vertical-align:middle">&nbsp;&nbsp;姓&nbsp;&nbsp;名&nbsp;&nbsp;</th>
<%--            <th rowspan="3" id="idNum">身份证号</th>--%>
            <th id="zqh" rowspan="3" style="text-align: center;vertical-align:middle">值勤岗位</th>
            <th id="xlh" rowspan="3" style="text-align: center;vertical-align:middle">线路岗位</th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">用工形式</th>
            <c:forEach items="${flagList}" var="list" >
<%--                <th style="text-align: center;vertical-align:middle">${list.date}</th>--%>
                <th style="text-align: center;vertical-align:middle"> ${fn:substring(list.date, 8, 10)}</th>
            </c:forEach>
            <th id="gsh" rowspan="3" style="text-align: center;vertical-align:middle">当月工时</th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">缺勤</th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">公伤</th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">年休</th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">出差</th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">值勤</th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">加班</th>
            <th rowspan="3" style="text-align: center;vertical-align:middle">零星加班</th>
            <th id="bzt" rowspan="3" style="text-align: center;vertical-align:middle">备注</th>
            <shiro:hasPermission name="affair:affairAttendance:edit">
                <th id="handleTh" rowspan="3" style="text-align: center">操作</th>
            </shiro:hasPermission>
        </tr>
        <tr>
            <c:forEach items="${flagList}" var="list" >
                <th style="text-align: center"> ${fns:getDictLabel(list.week, 'attendance_week', '')}</th>
            </c:forEach>
        </tr>
        <tr>
            <c:forEach items="${flagList}" var="list" >
                <th style="text-align: center">
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
		<tbody>
        <c:forEach items="${page.list}" var="affairAttendanceChild" varStatus="status">
			<tr>
                <td class="quanxuan">
                    <input style='margin-left:12px' type='checkbox' name="myCheckBox"
                           value="${affairAttendanceChild.id}"/>
                </td>
                <td style="text-align: center">
                        ${(page.pageNo-1)*page.pageSize+status.index+1}
                </td>
             <%--   <td>
                        ${affairAttendanceChild.unit}
                </td>--%>
				<td style="text-align: center">
                            ${affairAttendanceChild.name}
				</td>
               <%-- <td class="idNum">
                        ${affairAttendanceChild.idNumber}
                </td>--%>
				<td class="zqt" style="text-align: center">
                        ${fns:getDictLabel(affairAttendanceChild.policeType, 'attendence_police_types', '')}
				</td>
                <td class="xlt" style="text-align: center">
                        ${fns:getDictLabel(affairAttendanceChild.linePost, 'attendence_police_types', '')}
                </td>
				<td style="text-align: center">
                        ${fns:getDictLabel(affairAttendanceChild.workType, 'attendence_work_types', '')}
				</td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day1 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day1, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day2 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day2, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day3 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day3, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day4 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day4, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day5 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day5, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day6 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day6, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day7 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day7, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day8 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day8, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day9 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day9, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day10 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day10, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="text-align: center">
                    <c:choose>
                        <c:when test="${affairAttendanceChild.day11 == '1'}">
                            <font color="red" style="font-size:  24px">○</font>
                        </c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairAttendanceChild.day11, 'affair_attendence_types', '')}
                        </c:otherwise>
                    </c:choose>
                </td>
                <c:choose>
                    <c:when test="${flagList.size() == 31}">
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day12 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day12, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day13 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day13, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day14 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day14, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day15 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day15, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day16 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day16, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day17 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day17, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day18 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day18, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day19 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day19, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day20 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day20, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day21 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day21, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day22 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day22, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day23 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day23, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day24 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day24, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day25 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day25, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day26 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day26, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day27 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day27, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day28 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day28, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day29 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day29, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day30 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day30, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day31 == '1'}">
                                    <font color="red" style="font-size: 24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day31, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:when>
                    <c:when test="${flagList.size() == 30}">
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day12 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day12, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day13 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day13, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day14 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day14, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day15 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day15, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day16 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day16, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day17 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day17, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day18 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day18, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day19 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day19, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day20 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day20, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day21 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day21, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day22 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day22, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day23 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day23, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day24 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day24, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day25 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day25, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day26 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day26, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day27 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day27, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day28 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day28, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day29 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day29, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day30 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day30, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:when>
                    <c:when test="${flagList.size() == 29}">
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day12 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day12, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day13 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day13, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day14 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day14, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day15 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day15, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day16 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day16, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day17 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day17, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day18 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day18, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day19 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day19, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day20 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day20, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day21 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day21, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day22 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day22, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day23 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day23, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day24 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day24, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day25 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day25, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day26 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day26, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day27 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day27, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day28 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day28, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day29 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day29, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day12 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day12, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day13 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day13, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day14 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day14, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day15 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day15, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day16 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day16, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day17 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day17, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day18 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day18, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day19 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day19, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day20 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day20, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day21 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day21, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day22 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day22, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day23 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day23, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day24 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day24, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day25 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day25, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day26 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day26, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day27 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day27, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="text-align: center">
                            <c:choose>
                                <c:when test="${affairAttendanceChild.day28 == '1'}">
                                    <font color="red" style="font-size:  24px">○</font>
                                </c:when>
                                <c:otherwise>
                                    ${fns:getDictLabel(affairAttendanceChild.day28, 'affair_attendence_types', '')}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:otherwise>
                </c:choose>
				<td class="gst" style="text-align: center">
                        ${affairAttendanceChild.monthHours}
				</td>
				<td style="text-align: center">
                        ${affairAttendanceChild.lackWork}
				</td>
				<td style="text-align: center">
                        ${affairAttendanceChild.workInjury}
				</td>
				<td style="text-align: center">
                        ${affairAttendanceChild.yearWeak}
				</td>
				<td style="text-align: center">
                        ${affairAttendanceChild.goOut}
				</td>
				<td style="text-align: center">
                        ${affairAttendanceChild.inWork}
				</td>
				<td style="text-align: center">
                        ${affairAttendanceChild.overtime}
				</td>
				<td style="text-align: center">
                        ${affairAttendanceChild.overtimeChip}
				</td>
				<td class="bzh" style="text-align: center">
                        ${affairAttendanceChild.beizhu}
				</td>
                <td class="handleTd">
                <shiro:hasPermission name="affair:affairAttendance:edit">
                            <a href="javascript:void(0)" onclick="openAddOneEditDialog('${affairAttendanceChild.attId}','${affairAttendanceChild.idNumber}')">修改</a>
                            <a href="${ctx}/affair/affairAttendanceChild/delete?id=${affairAttendanceChild.id}&unitId=${uid}"
                           onclick="return confirmx('确认要删除该考勤记录吗？', this.href)">删除</a>
				</shiro:hasPermission>
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
    </div>
	<%--<div class="pagination">${page}</div>--%>
</body>
</html>