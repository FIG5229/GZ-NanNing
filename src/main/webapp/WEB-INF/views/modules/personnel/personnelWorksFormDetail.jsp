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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著名称：</span><span class="modal-custom-info2-value">${personnelWorks.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著学科领域：</span><span class="modal-custom-info2-value">${personnelWorks.field}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著完成日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelWorks.completeDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著出版日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelWorks.publishDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著出版机构：</span><span class="modal-custom-info2-value">${personnelWorks.workPublishOffice}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著出版机构隶属层次：</span><span class="modal-custom-info2-value">${personnelWorks.publishOfficeLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著出版形式类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelWorks.publishOfficeType, 'personnel_chubantype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">作者身份：</span><span class="modal-custom-info2-value">${personnelWorks.authorIdentity}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">作者顺序：</span><span class="modal-custom-info2-value">${personnelWorks.authorSort}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发表日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelWorks.pubDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">期刊报纸名称：</span><span class="modal-custom-info2-value">${personnelWorks.newspaperName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">期刊发行号码：</span><span class="modal-custom-info2-value">${personnelWorks.periodicalNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著密级：</span><span class="modal-custom-info2-value">${personnelWorks.secretGrade}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所著论著的图片资料：</span><span class="modal-custom-info2-value">${personnelWorks.material}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">何种索引收录：</span><span class="modal-custom-info2-value">${personnelWorks.index}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">论著语种：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelWorks.language, 'personnel_yuzhong', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主要作者姓名：</span><span class="modal-custom-info2-value">${personnelWorks.authorName}</span></div>

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