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
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content">
                    <div id="contentTable" class="modal-custom-content">
                        <div class="modal-custom-info2">
                            <div class="modal-custom-info2-col modal-custom-info2-col1">
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.name}</span></div>
                                <div class="modal-custom-info2-row"><span
                                        class="modal-custom-info2-key">称谓：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceFamily.relationship, 'family_appellation', '')}</span></div>
                                <div class="modal-custom-info2-row"><span
                                        class="modal-custom-info2-key">出生日期：</span><span
                                        class="modal-custom-info2-value"><fmt:formatDate
                                        value="${personnelPoliceFamily.birthday}" pattern="yyyy-MM-dd"/></span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceFamily.sex, 'sex', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span
                                        class="modal-custom-info2-key">政治面貌：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceFamily.politicsFace, 'political_status', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">现状：</span><span
                                        class="modal-custom-info2-value"></span>${personnelPoliceFamily.status}</div>
                                <div class="modal-custom-info2-row"><span
                                        class="modal-custom-info2-key">工作单位名称及职务：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.unitNameJob}</span></div>
                                <div class="modal-custom-info2-row"><span
                                        class="modal-custom-info2-key">工作单位所在政区：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.unitArea}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">国籍：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.nationality}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceFamily.nation, 'nation', '')}</span>
                                </div>
                            </div>
                            <div class="modal-custom-info2-col modal-custom-info2-col2">
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.education}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.identity}</span></div>
                                <div class="modal-custom-info2-row"><span
                                        class="modal-custom-info2-key">身份或职位：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.identityJob}</span></div>
                                <div class="modal-custom-info2-row"><span
                                        class="modal-custom-info2-key">职务层次：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.jobLevel}</span></div>
                                <div class="modal-custom-info2-row"><span
                                        class="modal-custom-info2-key">联系方式：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.contactMethod}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.identity}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">住址：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.address}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span
                                        class="modal-custom-info2-value">${personnelPoliceFamily.remark}</span></div>
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