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
                <div class="modal-custom-content" id="contentTable">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职资格名称：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职资格级别：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准资格评定文件名称(文号)：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.file}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">专业方向：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.direction}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">资格评审年度：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.reviewYear}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评委会名称：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.juryName}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">取得资格日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceWork2.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">取得资格途径：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.channel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证书编号：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.certificateNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">资格考试年度：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.examYear}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考试名称：</span><span class="modal-custom-info2-value">${personnelPoliceWork2.examName}</span></div>
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