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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">体检日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelBuild.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">血型：</span><span class="modal-custom-info2-value">${personnelBuild.bloodType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身高(cm)：</span><span class="modal-custom-info2-value">${personnelBuild.height}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">左眼视力：</span><span class="modal-custom-info2-value">${personnelBuild.leftVision}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">右眼视力：</span><span class="modal-custom-info2-value">${personnelBuild.rightVision}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">脉搏(次/分)：</span><span class="modal-custom-info2-value">${personnelBuild.pulse}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">体重(kg)：</span><span class="modal-custom-info2-value">${personnelBuild.weight}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">血压(mmHg)：</span><span class="modal-custom-info2-value">${personnelBuild.bloodPressure}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主要既往病史：</span><span class="modal-custom-info2-value">${personnelBuild.medicalHistory}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">享受医疗待遇：</span><span class="modal-custom-info2-value">${personnelBuild.treatment}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">体检医院名称：</span><span class="modal-custom-info2-value">${personnelBuild.hospitalName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">外科：</span><span class="modal-custom-info2-value">${personnelBuild.surgery}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">内科：</span><span class="modal-custom-info2-value">${personnelBuild.internalMedicine}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">五官科：</span><span class="modal-custom-info2-value">${personnelBuild.otorhinolaryngology}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">妇科：</span><span class="modal-custom-info2-value">${personnelBuild.gynaecology}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">生化检查：</span><span class="modal-custom-info2-value">${personnelBuild.biochemistry}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">胸透、B超：</span><span class="modal-custom-info2-value">${personnelBuild.xRayB}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">特殊检查：</span><span class="modal-custom-info2-value">${personnelBuild.specialCheck}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">体检结论：</span><span class="modal-custom-info2-value">${personnelBuild.conclusion}</span></div>
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