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
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false,
                afterPrint:function (){
                    $('.download').css('display', 'table-cell');
                }
            });
        });
    });

</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div id="contentTable" class="modal-custom-content">
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">考核比武名称：</span>
                            <span class="modal-custom-info2-value">${affairTraining.title}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">类型：</span>
                            <span class="modal-custom-info2-value">${affairTraining.type}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">层次：</span>
                            <span class="modal-custom-info2-value">${affairTraining.level}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">主办部门：</span>
                            <span class="modal-custom-info2-value">${affairTraining.unit}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">开始时间：</span>
                            <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairTraining.beginTime}"/></span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">结束时间：</span>
                            <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairTraining.endTime}"/></span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">描述：</span>
                            <span class="modal-custom-info2-value">${affairTraining.description}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">状态：</span>
                            <span class="modal-custom-info2-value">${affairTraining.state}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-custom-info1-bottom">
                <div id="print" class="modal-custom-info1-btn red">打印</div>
            </div>
        </div>
    </div>
</div>
