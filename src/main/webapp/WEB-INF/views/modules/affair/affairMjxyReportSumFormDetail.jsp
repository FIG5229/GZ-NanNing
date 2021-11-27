<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    function openDialog(id) {
        top.$.jBox.open("iframe:${ctx}/affair/affairMjxyReport/shenHeDialog?id="+id,"休养申报审核",800,420,{
            buttons:{"关闭":true},
            loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            },
            closed:function (){
                self.location.href="${ctx}/affair/affairMjxyReportSum/formDetail?id=${affairMjxyReportSum.id}";
            }
        });
    };
    function openForm(url) {
        top.$.jBox.open("iframe:"+url, "休养申报修改",800,520,{
            buttons:{"关闭":true},
            loaded:function(){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            },closed:function (){self.location.href="${ctx}/affair/affairMjxyReportSum/formDetail?id=${affairMjxyReportSum.id}"}
        });
    }
    $(document).ready(function() {
        $("#export").click(
            function(){
                var submit = function (v, h, f) {
                    let param = "&startDate=${affairMjxyReportSum.startDate}&endDate=${affairMjxyReportSum.endDate}&type=${affairMjxyReportSum.type}&place=${affairMjxyReportSum.place}"
                    if (v == 'all') {
                        $("#searchForm").attr("action","${ctx}/affair/affairMjxyReportSum/export?");
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/affair/affairMjxyReportSum/");
                    }
                    if (v == 'part') {
                        $("#searchForm").attr("action","${ctx}/affair/affairMjxyReportSum/export?flag=true")
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/affair/affairMjxyReportSum/");
                    }
                    if (v == 'cancel') {
                        $.jBox.tip('已取消');
                    }
                    return true;
                };
                $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
            }
        );
    });
    $(document).ready(function() {
        $("#print").click(function(){
            $('.download').css('display', 'none');
            $('#handleTh').css('display', 'none');
            $('.handleTd').css('display', 'none');
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false,
                afterPrint:function (){
                    $('.download').css('display', '');
                    $('#handleTh').css('display', 'none');
                    $('.handleTd').css('display', 'none');
                }
            });
        });
    });
</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content" id="contentTable">
                    <form:form id="searchForm" modelAttribute="affairMjxyReportSum" action="${ctx}/affair/affairMjxyReportSum/" method="post" class="breadcrumb form-search">
                        <div class="modal-custom-info2">
                            <div class="modal-custom-info2-col modal-custom-info2-col1">
                                <input id="fileName" name="fileName" type="hidden" value="休养信息.xlsx"/>
                                <input id="beginDate" name="beginDate" type="hidden" value="${affairMjxyReportSum.beginDate}"/>
                                <input id="finishDate" name="finishDate" type="hidden" value="${affairMjxyReportSum.finishDate}"/>
                                <input id="beginStartDate" name="beginStartDate" type="hidden" value="${affairMjxyReportSum.beginStartDate}"/>
                                <input id="finishStartDate" name="finishStartDate" type="hidden" value="${affairMjxyReportSum.finishStartDate}"/>
                                <div class="control-group">
                                    <label class="control-label">休养开始时间：</label>
                                    <div class="controls">
                                        <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                               value="<fmt:formatDate value="${affairMjxyReportSum.startDate}" pattern="yyyy-MM-dd"/>"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">休养结束时间：</label>
                                    <div class="controls">
                                        <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                                               value="<fmt:formatDate value="${affairMjxyReportSum.endDate}" pattern="yyyy-MM-dd"/>"
                                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">休养类型：</label>
                                    <div class="controls">
                                        <form:select path="type" class="input-xlarge required">
                                            <form:option value="" label=""/>
                                            <form:options items="${fns:getDictList('affair_xiuyang')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                        </form:select>
                                        <span class="help-inline"><font color="red">*</font> </span>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">休养地点：</label>
                                    <div class="controls">
                                        <form:input path="place" htmlEscape="false" class="input-xlarge " readonly="true"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form:form>
                    <table id="cen" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>送审单位</th>
                            <th>单位</th>
                            <th>职务</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>联系方式</th>
                            <th>免票号</th>
                            <th>审核状态</th>
                            <shiro:hasPermission name="affair:affairMjxyReportSum:edit">
                                <th id="handleTh">操作</th>
                            </shiro:hasPermission>
                        </tr>
                        <c:forEach items="${affairMjxyReportSum.childrens}" var="m" varStatus="status">
                        <tr>
                            <td>${status.index+1}</td>
                            <td>
                                <c:choose >
                                    <c:when test="${m.oneCheckMan == null}">
                                        ${m.submitMan}
                                    </c:when>
                                    <c:otherwise>
                                        ${m.oneCheckMan}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${m.unit}</td>
                            <td>${m.job}</td>
                            <td>${m.name}</td>
                            <td>${fns:getDictLabel(m.sex, 'sex', '')}</td>
                            <td>${m.contactMethod}</td>
                            <th>${m.freeTicketNo}</th>
                            <th>${fns:getDictLabel(m.checkType, 'check_type', '')}</th>
                            <shiro:hasPermission name="affair:affairMjxyReportSum:edit">
                                <td class="handleTd">
                                    <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairMjxyReportSum/form?id=${m.id}')">修改</a>
                                    <a onclick="openDialog('${m.id}')">审核</a>
                                </td>
                            </shiro:hasPermission>
                        </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="modal-custom-info1-bottom">
                <div id="rewrite" class="modal-custom-info1-btn red" onclick="window.location.href='${ctx}/affair/affairMjxyReportSum/formDetail?id=${affairMjxyReportSum.id}'">重置</div>
                <div id="print" class="modal-custom-info1-btn red">打印</div>
                <div id="export" class="modal-custom-info1-btn red">导出</div>
            </div>
        </div>
    </div>
</div>