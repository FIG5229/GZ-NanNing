<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#print").click(function () {
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css", "${ctxStatic}/common/jeesite.css", "${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false
            });
        });
    });
</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span
                                    class="modal-custom-info2-value">${affairApplicant.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span
                                    class="modal-custom-info2-value"
                                    style="width: 105px;">${fns:getDictLabel(affairApplicant.sex, 'sex', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span
                                    class="modal-custom-info2-value"
                                    style="width: 105px;">${fns:getDictLabel(affairApplicant.nation, 'nation', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">申请入党时间：</span><span
                                    class="modal-custom-info2-value" style="width: 105px;"><fmt:formatDate value="${affairApplicant.enterDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span
                                    class="modal-custom-info2-value">${affairApplicant.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在党组织：</span><span
                                    class="modal-custom-info2-value">${affairApplicant.partyBranch}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">文化程度：</span><span
                                    class="modal-custom-info2-value">${affairApplicant.educationDegree}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col3" style="width: 220px;">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span
                                    class="modal-custom-info2-value">${affairApplicant.policeNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span
                                    class="modal-custom-info2-value">${affairApplicant.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生年月：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate value="${affairApplicant.birthday}" pattern="yyyy-MM"/></span></div>
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