<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function(){
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
                formValues: false
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairHealthCheckup.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairHealthCheckup.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairHealthCheckup.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">体检时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairHealthCheckup.tjDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairHealthCheckup.sex, 'sex', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年龄：</span><span class="modal-custom-info2-value">${affairHealthCheckup.age}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">患病史：</span><span class="modal-custom-info2-value">
                                <c:forEach items="${affairHealthCheckup.type.split(',')}" var="arr" varStatus="status">
                                    <c:choose>
                                        <c:when test="${status.index==0}">${fns:getDictLabel(arr, 'affair_health_checkup_type', '')}</c:when>
                                        <c:otherwise>
                                            ,${fns:getDictLabel(arr, 'affair_health_checkup_type', '')}
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">体检情况：</span><span class="modal-custom-info2-value">${affairHealthCheckup.tjQingkuang}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">复检原因：</span><span class="modal-custom-info2-value">${affairHealthCheckup.fjReason}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>