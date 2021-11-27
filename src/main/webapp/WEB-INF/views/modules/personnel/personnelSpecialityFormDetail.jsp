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
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">姓名：</span>
                                <span class="modal-custom-info2-value">${personnelSpeciality.personnelName}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">性别：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(personnelSpeciality.sex, 'sex', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">出生日期：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSpeciality.birthday}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">特长：</span>
                                <span class="modal-custom-info2-value">${personnelSpeciality.speciality}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">单位：</span>
                                <span class="modal-custom-info2-value">${personnelSpeciality.unit}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">专长类别：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(personnelSpeciality.type, 'personnel_zclb', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">计算机使用程度：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(personnelSpeciality.computerDegree, 'personnel_jsjlevel', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">等级程度：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(personnelSpeciality.gradeDegree, 'personnel_djcd', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">专长认定单位名称：</span>
                                <span class="modal-custom-info2-value">${personnelSpeciality.unitName}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">专长类别补充：</span>
                                <span class="modal-custom-info2-value">${personnelSpeciality.supplement}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">专长描述：</span>
                                <span class="modal-custom-info2-value">${personnelSpeciality.describe}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div  class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>