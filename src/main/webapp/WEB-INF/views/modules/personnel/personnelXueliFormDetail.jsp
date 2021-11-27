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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历名称：</span><span class="modal-custom-info2-value">${personnelXueli.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学校(单位)名称：</span><span class="modal-custom-info2-value">${personnelXueli.schoolName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安专业技术分类：</span><span class="modal-custom-info2-value">${personnelXueli.classify}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入学日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelXueli.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">毕(肄)业日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelXueli.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学制(年)：</span><span class="modal-custom-info2-value">${personnelXueli.year}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所学专业代码：</span><span class="modal-custom-info2-value">${personnelXueli.majorName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所学专业名称：</span><span class="modal-custom-info2-value">${personnelXueli.majorCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">从学类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelXueli.type1, 'personnel_cxtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">最高学历说明：</span><span class="modal-custom-info2-value">${personnelXueli.explain}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">记录状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelXueli.status, 'personnel_jltype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">多学历序号：</span><span class="modal-custom-info2-value">${personnelXueli.sequenceNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学校(单位)所在政区：</span><span class="modal-custom-info2-value">${personnelXueli.schoolArea}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">毕业院校类别：</span><span class="modal-custom-info2-value">${personnelXueli.type2}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">毕(肄)业证书编号：</span><span class="modal-custom-info2-value">${personnelXueli.certificateNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习形式：</span><span class="modal-custom-info2-value">${personnelXueli.modality}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习完成情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelXueli.completeSituation, 'personnel_xuexi', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历描述：</span><span class="modal-custom-info2-value">${personnelXueli.describe}</span></div>
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