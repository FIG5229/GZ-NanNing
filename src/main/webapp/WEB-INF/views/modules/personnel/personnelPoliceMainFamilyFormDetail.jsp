<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function () {
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
                    <form:form id="searchForm" modelAttribute="personnelPoliceMainFamily" action="${ctx}/personnel/personnelPoliceMainFamily/" method="post" class="breadcrumb form-search">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span>
                                <span class="modal-custom-info2-value">${personnelPoliceMainFamily.name}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span>
                                <span class="modal-custom-info2-value">${personnelPoliceMainFamily.unit}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证：</span>
                                <span class="modal-custom-info2-value">${personnelPoliceMainFamily.idNumber}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span>
                                <span class="modal-custom-info2-value">${personnelPoliceMainFamily.policeNum}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否已婚：</span>
                                <span class="modal-custom-info2-value">
                                     <c:choose>
                                         <c:when test="${personnelPoliceMainFamily.hasMarried == '1'}">
                                             未婚
                                         </c:when>
                                         <c:when test="${personnelPoliceMainFamily.hasMarried == '2'}">
                                             初婚
                                         </c:when>
                                         <c:when test="${personnelPoliceMainFamily.hasMarried == '3'}">
                                             再婚
                                         </c:when>
                                         <c:when test="${personnelPoliceMainFamily.hasMarried == '4'}">
                                             复婚
                                         </c:when>
                                         <c:when test="${personnelPoliceMainFamily.hasMarried == '5'}">
                                             丧偶
                                         </c:when>
                                         <c:when test="${personnelPoliceMainFamily.hasMarried == '6'}">
                                             离婚
                                         </c:when>
                                         <c:otherwise>
                                             已婚
                                         </c:otherwise>
                                     </c:choose>
                                </span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否有兄弟姐妹：</span>
                                <span class="modal-custom-info2-value">
                                        <c:choose>
                                            <c:when test="${personnelPoliceMainFamily.hasBrother == '1'}">
                                              是
                                            </c:when>
                                            <c:otherwise>
                                                否
                                            </c:otherwise>
                                        </c:choose>
                                </span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否有子女：</span>
                                <span class="modal-custom-info2-value">
                                        <c:choose>
                                            <c:when test="${personnelPoliceMainFamily.hasChild == '1'}">
                                                是
                                            </c:when>
                                            <c:otherwise>
                                                否
                                            </c:otherwise>
                                        </c:choose>
                                </span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子女是否有配偶：</span>
                                <span class="modal-custom-info2-value">
                                        <c:choose>
                                            <c:when test="${personnelPoliceMainFamily.hasChildInLow == '1'}">
                                                是
                                            </c:when>
                                            <c:otherwise>
                                                否
                                            </c:otherwise>
                                        </c:choose>
                                </span>
                            </div>
                        </div>
                    </form:form>
                    <table id="cen" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>身份证号</th>
                            <th>与本人关系</th>
                            <th>性别</th>
                            <th>出生年月</th>
                            <th>政治面貌</th>
                            <th>现状</th>
                            <th>工作单位名称及职务</th>
                            <th>工作单位所在政区</th>
                            <th>国籍</th>
                            <th>民族</th>
                            <th>学历</th>
                            <th>身份</th>
                            <th>身份或职位</th>
                            <th>职务层次</th>
                            <th>联系方式</th>
                            <th>住址</th>
                            <th>备注</th>
                        </tr>
                        <c:forEach items="${personnelPoliceMainFamily.personnelPoliceFamilyInfoList}" var="m" >
                        <tr>
                            <td>${m.name}</td>
                            <td>${m.idNumber}</td>
                            <td>${m.relationship}</td>
                            <td>${fns:getDictLabel(m.sex, 'sex', '')}</td>
                            <td><fmt:formatDate value="${m.birthday}" pattern="yyyy-MM-dd"/></td>
                            <td>${fns:getDictLabel(m.politicalStatus, 'political_status', '')}</td>
                            <td>${m.statusQuo}</td>
                            <td>${m.job}</td>
                            <td>${m.steer}</td>
                            <td>${m.nationality}</td>
                            <td>${fns:getDictLabel(m.nation, 'nation', '')}</td>
                            <td>${m.education}</td>
                            <td>${m.identity}</td>
                            <td>${m.position}</td>
                            <td>${m.jobLevel}</td>
                            <td>${m.contact}</td>
                            <td>${m.address}</td>
                            <td>${m.remark}</td>
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