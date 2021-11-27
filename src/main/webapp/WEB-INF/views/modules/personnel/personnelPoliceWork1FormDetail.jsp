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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">专业技术职务名称：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.jobName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职方式：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.method}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceWork1.status, 'personnel_rztype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">专业技术岗位等级：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.grade}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职变动类别：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.changeType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceWork1.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">多职务主次序号：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.sequenceNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">集体内排列顺序：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.sort}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主管工作：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.majorWork}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件名称或文号：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.file}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">聘任证书编号：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.certificateNo}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职预定截止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceWork1.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">多职务名称描述：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.describe}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">名称：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">代码：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceWork1.relationship, 'personnel_guanxi', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在政区：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.area}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">隶属关系：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.relationship}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">级别：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性质类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceWork1.type, 'personnel_leibie', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所属行业：</span><span class="modal-custom-info2-value">${personnelPoliceWork1.industry}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">免职日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceWork1.cancelDate}" pattern="yyyy-MM-dd"/></span></div>
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