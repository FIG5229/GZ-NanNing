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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairQualification.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairQualification.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">取得资格日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairQualification.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职资格名称：</span><span class="modal-custom-info2-value">${affairQualification.jobName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职资格级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairQualification.level, 'police_technical_qualification_level', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">获得资格途径：</span><span class="modal-custom-info2-value">${affairQualification.channel}</span></div>
                         </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">资格评定文件名称(文号)：</span><span class="modal-custom-info2-value">${affairQualification.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证书编号：</span><span class="modal-custom-info2-value">${affairQualification.certificateNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">专业方向：</span><span class="modal-custom-info2-value">${affairQualification.direction}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">资格考试年度：</span><span class="modal-custom-info2-value">${affairQualification.examYear}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考试名称：</span><span class="modal-custom-info2-value">${affairQualification.examName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评委会名称：</span><span class="modal-custom-info2-value">${affairQualification.juryName}</span></div>
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