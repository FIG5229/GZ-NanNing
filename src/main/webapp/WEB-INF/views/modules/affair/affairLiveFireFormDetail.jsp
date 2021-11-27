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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairLiveFire.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年龄：</span><span class="modal-custom-info2-value">${affairLiveFire.age}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairLiveFire.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">枪型：</span><span class="modal-custom-info2-value">${affairLiveFire.gunType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">九环：</span><span class="modal-custom-info2-value">${affairLiveFire.nineRings}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">七环：</span><span class="modal-custom-info2-value">${affairLiveFire.sevenRings}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">五环：</span><span class="modal-custom-info2-value">${affairLiveFire.fiveRings}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否合格：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairLiveFire.isQualified, 'yes_no', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value">${affairLiveFire.yearMonth}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件号：</span><span class="modal-custom-info2-value">${affairLiveFire.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairLiveFire.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairLiveFire.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">十环：</span><span class="modal-custom-info2-value">${affairLiveFire.tenRings}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">八环：</span><span class="modal-custom-info2-value">${affairLiveFire.eightRings}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">六环：</span><span class="modal-custom-info2-value">${affairLiveFire.sixRings}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">总环数：</span><span class="modal-custom-info2-value">${affairLiveFire.sumRings}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairLiveFire.remark}</span></div>
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