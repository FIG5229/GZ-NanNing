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
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css", "${ctxStatic}/common/modal-custom.css"],
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查单位：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.checkUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查时间：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate value="${examOtherDataChild.checkDate}"
                                                                                     pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查人：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.checkPerson}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用模板：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.useModelName}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">选择项：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.chooseOptions}</span>
                            </div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">绩效考评标准：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.testStandart}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任人：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.dutyPerson}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任单位：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.dutyUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任领导：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.dutyLeader}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">扣分情况：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.scortSituation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span
                                    class="modal-custom-info2-value">${examOtherDataChild.remark}</span></div>
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