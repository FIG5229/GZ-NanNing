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
                        <div class="modal-custom-info2-row" style="margin-top: 20px;">
                            <span class="modal-custom-info2-key" >文化人才：</span>
                            <table id="wenhua" class="table table-striped table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>特长信息</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${wenHuaList}" var="affairWenHua" varStatus="status">
                                    <tr>
                                        <td>${status.index+1}</td>
                                        <td>${affairWenHua.specialty}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-custom-info2-row" style="margin-top: 20px;">
                            <span class="modal-custom-info2-key" >体育特长：</span>
                            <table id="tiyu" class="table table-striped table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>特长信息</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tiyuList}" var="affairWentiTalent" varStatus="status">
                                    <tr>
                                        <td>${status.index+1}</td>
                                        <td>${affairWentiTalent.skill}</td>
                                     </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-custom-info2-row" style="margin-top: 20px;">
                            <span class="modal-custom-info2-key" >新警特长：</span>
                            <table id="xinjin" class="table table-striped table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>特长信息</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${xinjinList}" var="affairYouthTalent" varStatus="status">
                                    <tr>
                                        <td>${status.index+1}</td>
                                        <td>${affairYouthTalent.skill}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-custom-info2-row" style="margin-top: 20px;">
                            <span class="modal-custom-info2-key" >专长信息：</span>
                            <table id="zhuanchang" class="table table-striped table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>特长信息</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page}" var="personnelSkill" varStatus="status">
                                    <tr>
                                        <td>${status.index+1}</td>
                                        <td>${personnelSkill.describe}</td>
                                      </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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