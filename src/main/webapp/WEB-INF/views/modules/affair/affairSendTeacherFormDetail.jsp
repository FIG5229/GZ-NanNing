<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#print").click(function () {
            $('.download').css('display', 'none');
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
                formValues: false,
                afterPrint: function () {
                    $('.download').css('display', '');
                }
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">用户名：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.userName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.number}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受训民警：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.idNumber}</span></div>
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警种：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairSendTeacher.policeClassification, 'police_classification', '')}</span>
                            </div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔：</span>
                               <%--<span class="modal-custom-info2-value">${fns:getDictLabel(affairSendTeacher.policeRank, 'police_rank', '')}</span>--%>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSendTeacher.policeRank, 'police_rank_level', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">组织单位：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">域：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.region}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训开始时间：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate
                                    value="${affairSendTeacher.beganDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训结束时间：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate
                                    value="${affairSendTeacher.resultDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员类别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairSendTeacher.personType, 'person_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">管理类别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairSendTeacher.managementType, 'management_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">行政职务：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairSendTeacher.post, 'administration_post', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务级别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairSendTeacher.postLevel, 'post_level', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">送教时长(天)：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.sendDay}</span></div>
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">送教市场：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.sendMarket}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">送教场次：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.sendPeriod}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受教单位：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.sendUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">送教内容：</span><span
                                    class="modal-custom-info2-value">${affairSendTeacher.sendContent}</span></div>
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
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>