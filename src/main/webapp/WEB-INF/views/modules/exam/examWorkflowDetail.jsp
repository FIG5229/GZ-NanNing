<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<div id="modalSysFirst">
    <div class="">
        <div class="modal-custom-head">
            <h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
        </div>
        <div class="modal-custom-main">
            <%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
            <input id="status" name="status" type="hidden" value="${status}"/>
            <div class="modal-custom-content">
                <table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>单位名称</th>
                        <th>自评状态</th>
                        <th>系统初核</th>
                        <th>系统公示</th>
                        <th>部门负责人签字</th>
                        <th>分管领导签字</th>
                        <th>绩效考评领导小组复核及调整</th>
                        <th>局主管领导最终签字</th>
                        <th>最终结果公示</th>
                    </tr>
                    </thead>
                    <tbody>
                   <tr>
                       <td>
                           南宁公安处
                       </td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                   </tr>
                   <tr>
                       <td>
                           柳州公安处
                       </td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                   </tr>
                   <tr>
                       <td>
                           北海公安处
                       </td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                       <td></td>
                   </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
