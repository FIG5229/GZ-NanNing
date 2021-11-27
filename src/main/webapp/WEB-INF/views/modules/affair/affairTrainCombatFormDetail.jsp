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
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">用户名：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.userName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.number}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.name}</span></div>
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">训练日期：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate
                                    value="${affairTrainCombat.date}" pattern="yyyy-MM-dd"/></span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.idNumber}</span></div>
                           <%-- <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">项目类别：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTrainCombat.projectType, 'project_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警种：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainCombat.policeClassification, 'police_classification', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔：</span>
                               &lt;%&ndash; <span class="modal-custom-info2-value">${fns:getDictLabel(affairTrainCombat.policeRank, 'police_rank', '')}</span>&ndash;%&gt;
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTrainCombat.policeRank, 'police_rank_level', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">创建人：</span><span
                                    &lt;%&ndash;class="modal-custom-info2-value">${affairTrainCombat.createBy}</span></div>&ndash;%&gt;
                                    class="modal-custom-info2-value">${fns:getUserById(affairTrainCombat.createBy).getName()}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员类别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainCombat.personType, 'person_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">管理类别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainCombat.managementType, 'management_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">行政职务：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainCombat.post, 'administration_post', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务级别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainCombat.postLevel, 'post_level', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">域：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.region}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子弹数量：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.count}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">平均单发环数：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.average}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span
                                    class="modal-custom-info2-value">${affairTrainCombat.remarks}</span></div>
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">创建时间：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate
                                    value="${affairTrainCombat.createDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>--%>
                    </div>
                    <%--<div class="modal-custom-info1-file">
                        <div class="modal-custom-info1-file-1">附件：</div>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${filePathList}" var="m" varStatus="status">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        &lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>--%>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>