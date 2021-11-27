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
                    <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelYoufu.pensionType, 'personnel_fuxu', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤标识：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelYoufu.pensionMark, 'personnel_fxbs', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤证书编号：</span><span class="modal-custom-info2-value">${personnelYoufu.pensionNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">亲属性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelYoufu.relativesSex, 'sex', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelYoufu.pensionDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤单位名称：</span><span class="modal-custom-info2-value">${personnelYoufu.pensionUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤事由：</span><span class="modal-custom-info2-value">${personnelYoufu.pensionCause}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">与民警关系：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelYoufu.relationship, 'personnel_guanxi', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系方式：</span><span class="modal-custom-info2-value">${personnelYoufu.contact}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col3">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤金额：</span><span class="modal-custom-info2-value">${personnelYoufu.money}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤单位代码：</span><span class="modal-custom-info2-value">${personnelYoufu.pensionCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${personnelYoufu.remark}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">亲属姓名：</span><span class="modal-custom-info2-value">${personnelYoufu.relativesName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位及住址：</span><span class="modal-custom-info2-value">${personnelYoufu.address}</span></div>
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
