<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script>
    if("success"=="${saveResult}"){
        parent.$.jBox.close();
    }
</script>
<script type="text/javascript">
    $(document).ready(function() {

        $("#print").click(function(){
            $("#zhuceBtn").css('display', 'none');
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
                afterPrint:function(){
                    $('#zhuceBtn').css('display', 'table-cell');
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">组织名称：</span><span class="modal-custom-info2-value">${affairTwBase.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所辖单位：</span><span class="modal-custom-info2-value">${affairTwBase.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所属上级团组织：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTwBase.orgName, 'affair_org_name', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">上一次换届改选时间：</span><span class="modal-custom-info2-value">${affairTwBase.hjDate}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">团（支）委人数：</span><span class="modal-custom-info2-value">${affairTwBase.num}</span></div>

                        </div>
                    </div>
                    <table id="contentTables" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>角色</th>
                            <th>姓名</th>
                            <th>职责</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${affairTwBase.affairTwBaseSignList}" var="affairTwBaseSign">
                            <tr>
                                <td>
                                        ${fns:getDictLabel(affairTwBaseSign.role, 'affair_twrole', '')}
                                </td>
                                <td>
                                    ${affairTwBaseSign.name}
                                </td>
                                <td>
                                        ${affairTwBaseSign.job}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <form:form id="searchForm" action="${ctx}/affair/affairTwBase/zhuCe" method="post" class="breadcrumb form-search">
                    <input id="groupId" name="groupId" type="hidden" value="${affairTwBase.id}"/>
                    <input id="groupName" name="groupName" type="hidden" value="${affairTwBase.name}"/>
                    <li class="btns"><input  class="btn btn-primary"  type="submit" value="注册" id="zhuceBtn" /></li>
                    <table id="contentTabless" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>身份证号</th>
                            <th>出生日期</th>
                            <th>年龄</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list}" var="affairTjRegisterFromP">
                            <tr>

                                <td>
                                    <input id="personId" name="personId" type="hidden" value="${affairTjRegisterFromP.id}"/>
                                        ${affairTjRegisterFromP.name}
                                </td>
                                <td>
                                        ${affairTjRegisterFromP.idNumber}
                                </td>
                                <td>
                                        ${affairTjRegisterFromP.birthday}
                                </td>
                                <td>
                                        ${affairTjRegisterFromP.age}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </form:form>
                <form:form id="searchForm" method="post" class="breadcrumb form-search">
                <input id="id" name="id" type="hidden" value="${affairTwBase.id}"/>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                        <%--                    <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>--%>
                        <%-- <shiro:hasPermission name="affair:affairOneHelpOne:edit">
                             <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
                         </shiro:hasPermission>--%>
                </div>
            </div>
            </form:form>
        </div>
    </div>
</div>