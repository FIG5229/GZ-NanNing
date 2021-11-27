<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function(){
            $('.download').css('display', 'none');
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2./exam/examAutoEvaluation/3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false,
                afterPrint:function(){
                    $('.download').css('display', '');
                }
            });
        });
    });
</script>
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <c:if test="${examAutoEvaluation.evalType == '5' || examAutoEvaluation.evalType == '6' || examAutoEvaluation.evalType == '7'}">
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${examAutoEvaluation.name}</span></div>
                            </c:if>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${examAutoEvaluation.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年份：</span><span class="modal-custom-info2-value">${examAutoEvaluation.year}</span></div>
                            <c:if test="${examAutoEvaluation.period == '1'}">
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">月份：</span><span class="modal-custom-info2-value">${examAutoEvaluation.month}</span></div>
                            </c:if>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${examAutoEvaluation.time}" pattern="yyyy-MM-dd"/></span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考评对象类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(examAutoEvaluation.type,'exam_object_type','')}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考评类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(examAutoEvaluation.evalType,'kp_type','')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考评周期：</span><span class="modal-custom-info2-value">${fns:getDictLabel(examAutoEvaluation.period,'exam_cycle','')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">分值：</span><span class="modal-custom-info2-value">${examAutoEvaluation.score}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${examAutoEvaluation.remark}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用模板：</span><span class="modal-custom-info2-value">${examAutoEvaluation.model}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">选择项：</span><span class="modal-custom-info2-value">${examAutoEvaluation.option}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${examAutoEvaluation.idNumber}</span></div>--%>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核部门：</span><span class="modal-custom-info2-value">${examAutoEvaluation.assess}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">来源说明：</span><span class="modal-custom-info2-value">${examAutoEvaluation.fromSys}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">问题发生时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${examAutoEvaluation.happenTime}" pattern="yyyy-MM-dd"/></span></div>--%>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${examAutoEvaluation.checkTime}" pattern="yyyy-MM-dd"/></span></div>--%>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">行号：</span><span class="modal-custom-info2-value">${examAutoEvaluation.rowNum}</span></div>--%>
                        </div>
                    </div>
                    <div class="modal-custom-info2"><span class="modal-custom-info2-key">详情：</span><span class="modal-custom-info2-value">${examAutoEvaluation.details}</span></div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>