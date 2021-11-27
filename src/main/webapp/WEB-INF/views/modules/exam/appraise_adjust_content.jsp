<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>评价内容</title>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
    <meta name="decorator" content="default"/>
    <script>
        if ("success" == "${result}") {
            //parent.window.location.href = "${ctx}/exam/examWorkflow/exam?id=${workflowId}";
        }
    </script>
    <script type="text/javascript">
        function add() {
            var selectedBoxes = [];
            $('input[name="selectedBox"]').each(function () {
                selectedBoxes.push($(this).val());
            });
            var selectedRowNum = 0;
            if (null != $("#selectedNumber").val() && '' != $("#selectedNumber").val()) {
                selectedRowNum = parseInt($("#selectedNumber").val());
            }

            var i = 0;
            var rowNum = 0;
            $('input[name="standardbox"]:checked').each(function () {
                if ($.inArray($(this).val(), selectedBoxes) < 0) {
                    rowNum = selectedRowNum + i;
                    var html = "<tr id='r_" + $(this).val() + "'><td><input class='i-checks' type='checkbox' name='selectedBox' value='" + $(this).val() + "'/></td>"
                    html += $("#s_" + $(this).val()).html();
                    html = html.substr(0, html.lastIndexOf("<td>"));
                    html += "<td></td>";
                    html += "<td><input type='text' name='datas[" + rowNum + "].value' style='width:30px;' class='input-xlarge  number required'/></td>";
                    html += "<td><p>xxx.pdf</p><p style='cursor: pointer;color: #2429FF;'><i class='icon-paperclip'></i>附件</p></td>";
                    html += "<td>"+$("#objName").val()+"<input type='hidden' name='datas[" + rowNum + "].fillPerson' value='" + $("#objId").val() + "'/></td>";
                    html += "<td><input type='hidden' name='datas[" + rowNum + "].examPerson' value=''/></td>";
                    html += "<td><input type='hidden' name='datas[" + rowNum + "].departSign' value=''/></td>";
                    html += "<td><input type='hidden' name='datas[" + rowNum + "].partBureausSign' value=''/></td>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].standardId' value='" + $("#standardId").val() + "'/>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].workflowId' value='" + $("#id").val() + "'/>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].rowId' value='" + $(this).val() + "'/>";
                    html += "</tr>";
                    $("#r_body").append(html);
                    i++;
                    //console.log($("#r_body").html())
                }
            });

        }

        function reduce() {
            $('input[name="selectedBox"]:checked').each(function () {
                $("#r_" + $(this).val()).replaceWith("");
            });
        }

        function formSubmit(processType) {
            $("#processType").val(processType);
            $("#inputForm").submit();
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate();
        });
    </script>
</head>
<body>
<form:form id="inputForm" action="${ctx}/exam/examWorkflow/appaise/data/save" method="post" class="form-horizontal">
    <input id="id" name="id" value="${workflowId}" type="hidden"/>
    <input id="objId" name="objId" value="${objId}" type="hidden"/>
    <input id="objName" name="objName" value="${objName}" type="hidden"/>
    <input id="status" name="status" value="${status}" type="hidden"/>
    <input id="standardId" name="standardId" value="${standardId}" type="hidden"/>
    <input id="selectedNumber" name="selectedNumber" value="${selectedNumber}" type="hidden"/>
    <input id="processType" name="processType" value="${processType}" type="hidden"/>
    <div id="modalSysSelf">
        <div class="modal-custom-content">
            <div class="modal-custom-tb-l">
                <table class="table table-striped table-bordered table-condensed" style="width: 100%;">
                    <thead>
                    <tr>
                        <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                            <th>${examStandardTemplateItemDefine.columnName}</th>
                        </c:forEach>
                        <th>选择</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${rowlist}" var="row"  varStatus="status">
                        <tr id="s_${row.row_num}">
                            <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                <td>
                                        ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                </td>
                                <c:if test="${'2'.equals(examStandardTemplateItemDefine.columnType)||'3'.equals(examStandardTemplateItemDefine.columnType)}">
                                    <input type='hidden' name='standardDatas[${status.index}].value'
                                           value='${row.get(String.valueOf(examStandardTemplateItemDefine.id))}'/>
                                </c:if>
                            </c:forEach>
                            <td><input class="i-checks" type="checkbox" name="standardbox" value="${row.row_num}"
                            <c:if test="${row.isSelected==1}">
                                       checked
                            </c:if>
                            ></td>
                            <input type='hidden' name='standardDatas[${status.index}].standardId'
                                   value='${standardId}'/>
                            <input type='hidden' name='standardDatas[${status.index}].workflowId'
                                   value='${workflowId}'/>
                            <input type='hidden' name='standardDatas[${status.index}].rowId'
                                   value='${row.row_num}'/>
                            <c:if test="${null != row.id}">
                                <input type='hidden' name='standardDatas[${status.index}].id'
                                       value='${row.id}'/>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-custom-tb-c">
                <div class="tb-btn tb-jia"><i class="icon-long-arrow-right" onclick="add()"></i></div>
                <div class="tb-btn tb-jian"><i class="icon-long-arrow-left" onclick="reduce()"></i></div>
            </div>
            <div class="modal-custom-tb-r">
                <table class="table table-striped table-bordered table-condensed" style="width: 100%;">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                            <th>${examStandardTemplateItemDefine.columnName}</th>
                        </c:forEach>
                        <th>上级检查评分情况</th>
                        <th>得分</th>
                        <th>附件</th>
                        <th>自评人</th>
                        <th>考核人</th>
                        <th>部门负责人</th>
                        <th>分管局领导</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody id="r_body">
                    <c:set var="num" value="0"></c:set>
                    <c:set var="step" value="1"></c:set>
                    <c:forEach items="${rowlist}" var="row"   varStatus="status">
                            <c:if test="${'1'.equals(row.isSelected)}">
                                <tr id="r_${row.row_num}">
                                    <td>
                                        <input class='i-checks' type='checkbox' name='selectedBox'
                                               value='${row.row_num}'/>
                                    </td>
                                    <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                        <td>
                                                ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                        </td>
                                    </c:forEach>
                                    <td></td>
                                    <td><input type='text' name='datas[${num}].value'
                                               style='width:30px;' class='input-xlarge  number required'
                                               value='${row.value}'/></td>
                                    <td><p>xxx.pdf</p>
                                        <p style='cursor: pointer;color: #2429FF;'><i class='icon-paperclip'></i>附件</p>
                                        \n
                                    </td>
                                    <td>${row.fillPerson}</td>
                                    <td>${row.examPerson}</td>
                                    <td>${row.departSign}</td>
                                    <td>${row.partBureausSign}</td>
                                    <td>
                                    <c:choose>
                                        <c:when test="${row.operationStatus >-1}">已调整</c:when>
                                        <c:otherwise>未调整</c:otherwise>
                                    </c:choose>
                                    </td>
                                    <input type='hidden' name='datas[${num}].standardId'
                                           value='${standardId}'/>
                                    <input type='hidden' name='datas[${num}].workflowId'
                                           value='${workflowId}'/>
                                    <input type='hidden' name='datas[${num}].rowId'
                                           value='${row.row_num}'/>
                                    <input type='hidden' name='datas[${num}].id'
                                           value='${row.id}'/>
                                </tr>
                                <c:set var="num" value="${num+step}"></c:set>
                            </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <div style="text-align: right;">
                    调整人：<input type="text" name="fill_person" value="${fns:getUser().name}" disabled></br></br><input
                        id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"/>&nbsp;&nbsp;&nbsp;&nbsp;<input
                        id="complete" class="btn btn-primary" type="button" value="结束考核调整"
                        onclick="formSubmit('groupAdjust')"/>
                </div>
        </div>
    </div>
    </div>
</form:form>
</body>
</html>
