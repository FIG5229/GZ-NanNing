<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#export").click(
            function(){
                var submit = function (v, h, f) {
                    if (v == 'all') {
                        $("#searchForm").attr("action","${ctx}/affair/affairZxInfo/export");
                        $("#searchForm").submit();
                    }
                    if (v == 'part') {
                        $("#searchForm").attr("action","${ctx}/affair/affairZxInfo/export?flag=true")
                        $("#searchForm").submit();
                    }
                    if (v == 'cancel') {
                        $.jBox.tip('已取消');
                    }
                    return true;
                };
                $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
            }
        );
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
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairZxInfo.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民警性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairZxInfo.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairZxInfo.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">补助类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairZxInfo.type, 'affair_zxtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">顺号：</span><span class="modal-custom-info2-value">${affairZxInfo.shun}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民警姓名：</span><span class="modal-custom-info2-value">${affairZxInfo.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">医保号：</span><span class="modal-custom-info2-value">${affairZxInfo.medicaNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民警身份证号：</span><span class="modal-custom-info2-value">${affairZxInfo.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子女姓名：</span><span class="modal-custom-info2-value">${affairZxInfo.childName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子女性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairZxInfo.childSex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子女出生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairZxInfo.childBirthday}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">就读院校（专业）：</span><span class="modal-custom">${affairZxInfo.childSchoolMajor}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所学专业：</span><span class="modal-custom">${affairZxInfo.childMajor}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学校类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairZxInfo.childSchoolType, 'affair_zxctype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学年制：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairZxInfo.childYearSystem, 'affair_zx_year_system', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">就读年级：</span><span class="modal-custom-info2-value">${affairZxInfo.childGrade}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">补助金额：</span><span class="modal-custom-info2-value">${affairZxInfo.money}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairZxInfo.remarks}</span></div>

                        </div>
                        <%--<table id="contentTables" class="table table-striped table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>子女姓名</th>
                                <th>子女性别</th>
                                <th>子女出生年月</th>
                                <th>补助类型</th>
                                <th>就读学校</th>
                                <th>所学专业</th>
                                <th>学校类别</th>
                                <th>学年制</th>
                                <th>就读年级</th>
                                <th> 资助金额</th>
                                <th>备注</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${affairZxInfo.affairZxInfoChildList}" var="affairZxInfoChild">
                                <tr>
                                    <td>
                                            ${affairZxInfoChild.name}
                                    </td>
                                    <td>
                                            ${fns:getDictLabel(affairZxInfoChild.sex, 'sex', '')}
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${affairZxInfoChild.birthday}" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <td>
                                            ${fns:getDictLabel(affairZxInfoChild.type, 'affair_zxctype', '')}
                                    </td>
                                    <td>
                                            ${affairZxInfoChild.schoolMajor}
                                    </td>
                                    <td>
                                            ${affairZxInfoChild.major}
                                    </td>
                                    <td>
                                            ${affairZxInfoChild.schoolType}
                                    </td>
                                    <td>
                                            ${fns:getDictLabel(affairZxInfoChild.yearSystem, 'affair_zx_year_system', '')}
                                    </td>
                                    <td>
                                            ${affairZxInfoChild.grade}
                                    </td>
                                    <td>
                                            ${affairZxInfoChild.money}
                                    </td>
                                    <td>
                                            ${affairZxInfoChild.remarks}
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>--%>
                        </div>
                            <div>附件：</div>
                            <div>
                                <c:forEach items="${filePathList}" var="m" varStatus="status">
                                    <div class="modal-custom-info1-file-item">
                                        <span>${m.fileName}</span>
                                        <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                    </div>
                                </c:forEach>
                            </div>

                </div>
                <form:form id="searchForm" method="post" class="breadcrumb form-search">
                <input id="fileName" name="fileName" type="hidden" value="金秋助学.xlsx"/>
                <input id="id" name="id" type="hidden" value="${affairZxInfo.id}"/>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
            </form:form>
        </div>
    </div>
</div>