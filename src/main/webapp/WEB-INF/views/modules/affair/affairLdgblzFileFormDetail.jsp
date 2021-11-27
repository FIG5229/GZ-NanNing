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
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
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
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 231px;">姓名：</span><span class="modal-custom-info2-value" style="width: 134px;">${affairLdgblzFile.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 231px;">出生年月：</span><span class="modal-custom-info2-value" style="width: 134px;"><fmt:formatDate value="${affairLdgblzFile.birthday}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairLdgblzFile.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入党时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairLdgblzFile.rdDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 112px;">工作单位：</span><span class="modal-custom-info2-value" style="width: 253px;">${affairLdgblzFile.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 112px;">职级：</span><span class="modal-custom-info2-value" style="width: 253px;">${affairLdgblzFile.workUnitJob}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 112px;">学历：</span><span class="modal-custom-info2-value" style="width: 253px;">${affairLdgblzFile.education}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 50px;">级别：</span><span class="modal-custom-info2-value">${affairLdgblzFile.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 50px;">籍贯：</span><span class="modal-custom-info2-value">${affairLdgblzFile.nativePlace}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col4">
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">惩处情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.chchqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">拒礼拒贿、上交礼金礼品情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.jlqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">操办婚丧喜庆事宜情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.cbqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">个人事项报告情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.grqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">学廉考廉情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.xlqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">落实党风廉政建设责任制考核情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.lshqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">述职述廉情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.shzhqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">民主评议和民主测评情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.mzhqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">审计结论：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.shjjl}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">问题线索查核情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.wtqk}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 225px;">其他情况：</span><span class="modal-custom-info2-value" style="width: 800px;">${affairLdgblzFile.qtqk}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-file">
                    <div class="modal-custom-info1-file-1">附件：</div>
                    <div class="modal-custom-info1-file-r">
                        <c:forEach items="${filePathList}" var="m" varStatus="status">
                            <div class="modal-custom-info1-file-item">
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