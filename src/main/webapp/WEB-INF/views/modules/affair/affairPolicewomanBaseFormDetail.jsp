<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/><script type="text/javascript">
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
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairPolicewomanBase.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生年月：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPolicewomanBase.birthday}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPolicewomanBase.xl, 'affair_xueli', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">毕业院校：</span><span class="modal-custom-info2-value">${affairPolicewomanBase.school}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">获得荣誉情况：</span><span class="modal-custom-info2-value">${affairPolicewomanBase.situation}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairPolicewomanBase.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairPolicewomanBase.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否党员：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPolicewomanBase.isPartyMember, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">技术职称：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPolicewomanBase.jszc, 'affair_jishu', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所学专业：</span><span class="modal-custom-info2-value">${affairPolicewomanBase.major}</span></div>
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