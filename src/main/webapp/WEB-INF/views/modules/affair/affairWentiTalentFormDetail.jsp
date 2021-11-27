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
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairWentiTalent.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairWentiTalent.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value"></span>${affairWentiTalent.unit}</div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">毕业院校：</span><span class="modal-custom-info2-value">${affairWentiTalent.school}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">体育技术等级：</span><span class="modal-custom-info2-value">${affairWentiTalent.tiyuLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">取得成绩：</span><span class="modal-custom-info2-value">${affairWentiTalent.achievement}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairWentiTalent.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${affairWentiTalent.policeNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生年月：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairWentiTalent.birthday}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">何种体育特长：</span><span class="modal-custom-info2-value">${affairWentiTalent.skill}</span></div>
                        </div>
                    </div>
                    <%--<div>
                        <span>附件：</span>
                        <span>XXXXXXXXXXXXXXXXXXXXXXXXX.pdf</span>
                        <a href="#">在线预览</a>
                        <a href="#">下载</a>
                    </div>--%>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>