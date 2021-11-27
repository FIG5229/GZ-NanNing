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
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content">
                    <div class="modal-custom-info2" id="contentTable">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairItemReport.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairItemReport.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位名称：</span><span class="modal-custom-info2-value">${affairItemReport.unit}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${affairItemReport.policeNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">婚姻状况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairItemReport.marriageStatus, 'affair_marital_status', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">户口性质：</span><span class="modal-custom-info2-value">${affairItemReport.hk}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">户籍所在地详址：</span><span class="modal-custom-info2-value">${affairItemReport.address}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairItemReport.status, 'affair_query_shenhe', '')}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>
