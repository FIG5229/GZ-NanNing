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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairBaseSkill.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年龄：</span><span class="modal-custom-info2-value">${affairBaseSkill.age}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairBaseSkill.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目一：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemOne}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目三：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemThree}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目五：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemFive}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目七：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemSeven}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目九：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemNine}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">综合评定：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBaseSkill.assessment, 'pass_status', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value">${affairBaseSkill.yearMonth}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件号：</span><span class="modal-custom-info2-value">${affairBaseSkill.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBaseSkill.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairBaseSkill.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目二：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemTwo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目四：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemFour}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目六：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemSix}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目八：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemEight}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目十：</span><span class="modal-custom-info2-value">${affairBaseSkill.itemTen}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairBaseSkill.remark}</span></div>
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