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
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
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
                <div class="modal-custom-content">
                    <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairCasualtyReport.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairCasualtyReport.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">种类与性质：</span><span class="modal-custom-info2-value">${affairCasualtyReport.type}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部门与警种：</span><span class="modal-custom-info2-value">${affairCasualtyReport.depPolice}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤亡时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairCasualtyReport.casualtyDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定部门：</span><span class="modal-custom-info2-value">${affairCasualtyReport.affirmDep}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部门名称：</span><span class="modal-custom-info2-value">${affairCasualtyReport.depName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairCasualtyReport.affirmDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">复核部门：</span><span class="modal-custom-info2-value">${affairCasualtyReport.checkDep}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">复核时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairCasualtyReport.checkDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">牺牲/病故证明书编号：</span><span class="modal-custom-info2-value">${affairCasualtyReport.certificateNo1}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">负伤程度：</span><span class="modal-custom-info2-value">${affairCasualtyReport.injuryDegree}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">残疾等级：</span><span class="modal-custom-info2-value">${affairCasualtyReport.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤残评定机构：</span><span class="modal-custom-info2-value">${affairCasualtyReport.evaluateOrg}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">记录状态：</span><span class="modal-custom-info2-value">${affairCasualtyReport.jlStatus}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否自杀：</span><span class="modal-custom-info2-value">${affairCasualtyReport.isSelfKill}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤亡原因：</span><span class="modal-custom-info2-value">${affairCasualtyReport.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">被伤害方式：</span><span class="modal-custom-info2-value">${affairCasualtyReport.method}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">执行勤务情况：</span><span class="modal-custom-info2-value">${affairCasualtyReport.zxqwSituation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤亡事件实力对比：</span><span class="modal-custom-info2-value">${affairCasualtyReport.comparison}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用武器警械：</span><span class="modal-custom-info2-value">${affairCasualtyReport.useArm}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                               <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤亡情节：</span><span class="modal-custom-info2-value">${affairCasualtyReport.plot}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairCasualtyReport.happenDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事故性质：</span><span class="modal-custom-info2-value">${affairCasualtyReport.character}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任认定：</span><span class="modal-custom-info2-value">${affairCasualtyReport.dutyAffirm}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">违章情况：</span><span class="modal-custom-info2-value">${affairCasualtyReport.wzSituation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">烈士标识：</span><span class="modal-custom-info2-value">${affairCasualtyReport.martyrLogo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证书编号：</span><span class="modal-custom-info2-value">${affairCasualtyReport.certificateNo2}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairCasualtyReport.approvalDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准部门：</span><span class="modal-custom-info2-value">${affairCasualtyReport.approvalDep}</span></div>

                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定因公牺牲文件：</span><span class="modal-custom-info2-value">
                                <c:forEach items="${filePathList}" var="m" >
                                    <div>
                                         <span>${m.fileName}</span>
                                    </div>
                                </c:forEach></span>
                            </div>

                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民政（人社）部门复核认定文件：</span><span class="modal-custom-info2-value">
                             <c:forEach items="${filePathList1}" var="m" >
                                <div>
                                    <span>${m.fileName}</span>
                                </div>
                             </c:forEach></span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">烈士批复文件：</span><span class="modal-custom-info2-value">
                                <c:forEach items="${filePathList2}" var="m" >
                                <div>
                                    <span>${m.fileName}</span>
                                </div>
                                </c:forEach>
                            </span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人民警察牺牲/病故证明书：</span><span class="modal-custom-info2-value">
                                 <c:forEach items="${filePathList3}" var="m" >
                                <div>
                                    <span>${m.fileName}</span>
                                </div>
                                 </c:forEach>
                            </span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核意见：</span><span class="modal-custom-info2-value">${affairCasualtyReport.opinion}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairCasualtyReport.shStatus, 'affair_query_shenhe', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairCasualtyReport.remark}</span></div>
                        </div>
                    </div>
                        <div>
                            <span>附件：</span>
                            <c:forEach items="${filePathList}" var="m" >
                                <div>
                                    <span>${m.fileName}</span>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                        <div>
                            <c:forEach items="${filePathList1}" var="m" >
                                <div>
                                    <span>${m.fileName}</span>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                        <div>
                            <c:forEach items="${filePathList2}" var="m" >
                                <div>
                                    <span>${m.fileName}</span>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                        <div>
                            <c:forEach items="${filePathList3}" var="m" >
                                <div>
                                    <span>${m.fileName}</span>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div  class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>