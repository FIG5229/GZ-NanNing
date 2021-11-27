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
                                    class="modal-custom-info2-value">${affairTrainOutsource.userName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.number}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">外部培训班名称：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.externalName}</span></div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">外部培训班类型：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.externalType, 'external_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">培训完成情况：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.completion, 'train_completion', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">主办单位机构代码：</span>
                                <span class="modal-custom-info2-value">${affairTrainOutsource.institutionCode}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主办单位级别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.unitLevel, 'unit_level', '')}</span></div>
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警种：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.policeClassification, 'police_classification', '')}</span>
                            </div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔：</span>
                                <%--<span class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.policeRank, 'police_rank', '')}</span>--%>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.policeRank, 'police_rank_level', '')}</span>
                            </div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员类别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.personType, 'person_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">管理类别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.managementType, 'management_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">行政职务：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.post, 'administration_post', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务级别：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.postLevel, 'post_level', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">开始时间：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate
                                    value="${affairTrainOutsource.beganDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">结束时间：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate
                                    value="${affairTrainOutsource.resultDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">域：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.region}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训离岗状态：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairTrainOutsource.quitStatus, 'quit_status', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">承训机构名称：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.unitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训地点：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.trainSite}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证书编号：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.unitName}</span></div>
                            <div class="modal-custom-info2-row "><span class="modal-custom-info2-key">培训时所在单位及职务：</span><span
                                    class="modal-custom-info2-value">${affairTrainOutsource.unitJob}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">创建人：</span>
                                <%--<span class="modal-custom-info2-value">${affairTrainOutsource.createBy}</span>--%>
                                <span class="modal-custom-info2-value">${fns:getUserById(affairTrainOutsource.createBy).getName()}</span>
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
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>