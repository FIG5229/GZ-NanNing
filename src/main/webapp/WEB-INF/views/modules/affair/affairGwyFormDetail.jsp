<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function(){
            $('.download').css('display', 'none');
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false,
                afterPrint:function (){
                    $('.download').css('display', '');
                }
            });
        });
    });
</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairGwy.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">死亡时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairGwy.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">基本工资：</span><span class="modal-custom-info2-value">${affairGwy.baseWage}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">上一次全国城镇居民可支配收入：</span><span class="modal-custom-info2-value">${affairGwy.lastYear}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">上一次全国城镇居民可支配收入的2倍：</span><span class="modal-custom-info2-value">${affairGwy.lastDoule}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">增发金额：</span><span class="modal-custom-info2-value">${affairGwy.additionalAmount}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">按【2014】101号遗属生活困难补助费（月）：</span><span class="modal-custom-info2-value">${affairGwy.hardFee}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公务员丧葬补助按桂人社发【2011】186号：</span><span class="modal-custom-info2-value">${affairGwy.gongWuYuan}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">按民发【2014】101号计发合计：</span><span class="modal-custom-info2-value">${affairGwy.people}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairGwy.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否有遗属：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairGwy.isWill, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">40个月基本离退费：</span><span class="modal-custom-info2-value">${affairGwy.frotyMonth}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">增发比例：</span><span class="modal-custom-info2-value">${affairGwy.issuanceRatio}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">社保遗嘱救济费（月）：</span><span class="modal-custom-info2-value">${affairGwy.relief}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">两项合计：</span><span class="modal-custom-info2-value">${affairGwy.sum}</span></div>

                        </div>
                    </div>
                    <div>
                        <span>附件：</span>
                        <c:forEach items="${filePathList}" var="m" varStatus="status">
                            <div>
                                <span>${m.fileName}</span>
                                    <%--<a href="#">在线预览</a>--%>
                                <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>