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
                        <div class="modal-custom-info2-col modal-custom-info2-col1" style="width: 45%">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairOrganizationBulid.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所辖单位：</span><span class="modal-custom-info2-value">${affairOrganizationBulid.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">支队所工会主席：</span><span class="modal-custom-info2-value">${affairOrganizationBulid.zghzNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否建立会员评价制度：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairOrganizationBulid.isAssessSys,'yes_no','')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所队支工会数：</span><span class="modal-custom-info2-value">${affairOrganizationBulid.zghNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主要做法：</span><span class="modal-custom-info2-value">${affairOrganizationBulid.method}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2" style="width: 45%">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">换届时间：</span><span class="modal-custom-info2-value"></span>${affairOrganizationBulid.hjDate}<%--<fmt:formatDate value="${affairOrganizationBulid.hjDate}" pattern="yyyy-MM-dd"/>--%></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工会名称：</span><span class="modal-custom-info2-value"></span>${affairOrganizationBulid.name}</div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所属上级工会：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairOrganizationBulid.orgName, 'affair_gh_org', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">会员评价测试结果：</span><span class="modal-custom-info2-value">${affairOrganizationBulid.result}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">支工会人数：</span><span class="modal-custom-info2-value">${affairOrganizationBulid.zghrNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">满意数：</span><span class="modal-custom-info2-value">${affairOrganizationBulid.satisfyNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairOrganizationBulid.remark}</span></div>

                        </div>
                    </div>
                    <span>委员会分工：</span>
                    <table id="cen" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>委员会情况</th>
                            <th>委员会角色名字</th>
                        </tr>
                        <c:forEach items="${affairOrganizationBulid.affairOrganziationBuildSignList}" var="m" >
                        <tr>
                            <td>${fns:getDictLabel(m.committee,'affair_wyh','')}</td>
                            <td>${m.committeeName}</td>
                        </tr>
                        </c:forEach>
                    </table>
                    <span>经费检查委员会分工：</span>
                    <table id="cen2" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>经费审查委员会情况</th>
                            <th>经费审查委员会名字</th>
                        </tr>
                        <c:forEach items="${affairOrganizationBulid.affairOrganizationBuildSingList2}" var="m" >
                        <tr>
                            <td>${fns:getDictLabel(m.review,'affair_jfsc','')}</td>
                            <td>${m.reviewName}</td>
                        </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>
