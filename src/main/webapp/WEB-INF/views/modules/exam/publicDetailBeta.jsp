<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        var children= $("#tabs").children()[0].click();
    });
    function changeStandard(standardId) {
        $("#tabs").children().each(function () {
            if($(this).attr('id')==standardId){
                $(this).addClass("red");
            }
            else{
                $(this).removeClass("red");
            }
        });
        var url="${ctx}/exam/examWorkflow/appaise/content?objId="+$("#objId").val()+"&objName="+$("#objName").val()+"&standardId="+standardId+"&workflowId="+$("#workflowId").val()+"&status="+$("#status").val()+"&processType=${processType}&personType=${personType}&history=${history}";
        $("#iframe").attr("src",url);
    }
    function back(){
        //window.location.href = "${ctx}/exam/examWorkflow/flowList?examType=${examType}"
        history.back(-1);
    }
</script>
<%--全局公示查看考评详情页面--%>
<input id="workflowId" name="workflowId" value="${workflowId}" type="hidden"/>
<input id="status" name="status" value="${status}" type="hidden"/>
<input id="objId" name="objId" value="${objId}" type="hidden"/>
<input id="objName" name="objName" value="${objName}" type="hidden"/>
<input id="noTree" name="noTree" value="${noTree}" type="hidden"/>
<div id="modalSysDepartment" class="">
    <div class="">
        <div class="modal-custom-main">
            <div style="color: red">(说明：红色字体为推送的考评项,蓝色字体为自动考评,绿色为审核人修改，紫色为绩效办修改)</div>
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>选择</th>
                    <%--<c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                        <th>${examStandardTemplateItemDefine.columnName}</th>
                    </c:forEach>--%>
                    <th>

                        <c:choose>
                            <c:when test="${examType == '5' || examType == '6' || examType == '7'}">
                                姓名
                            </c:when>
                            <c:otherwise>
                                填报单位
                            </c:otherwise>
                        </c:choose>
                    </th>
                    <th>工作名称</th>
                    <th>项目</th>
                    <th>类别</th>
                    <th>评分标准</th>
                    <th>标准分</th>
                    <c:if test="${benRen eq 'show'}">
                        <th>本人</th>
                    </c:if>
                    <c:if test="${zhuRen eq 'show'}">
                        <th>主任</th>
                    </c:if>
                    <c:if test="${shuJi eq 'show'}">
                        <th>书记</th>
                    </c:if>
                    <c:if test="${zhuGuanLingDao eq 'show'}">
                        <th>主管领导</th>
                    </c:if>
                    <c:if test="${chuZhang eq 'show'}">
                        <th>处长</th>
                    </c:if>
                    <c:if test="${fuChuZhang eq 'show'}">
                        <th>副处长</th>
                    </c:if>
                    <c:if test="${fuZhi eq 'show'}">
                        <th>副职</th>
                    </c:if>
                    <c:if test="${fuZhiDuiZhang eq 'show'}">
                        <th>副支队长</th>
                    </c:if>
                    <c:if test="${suoZhang eq 'show'}">
                        <th>所长</th>
                    </c:if>
                    <c:if test="${jiaoDaoYuan eq 'show'}">
                        <th>教导员</th>
                    </c:if>
                    <c:if test="${zhengWei eq 'show'}">
                        <th>政委</th>
                    </c:if>
                    <c:if test="${zhiDuiZhang eq 'show'}">
                        <th>支队长</th>
                    </c:if>
                    <c:if test="${daDuiZhang eq 'show'}">
                        <th>大队长</th>
                    </c:if>
                    <c:if test="${zhuXi eq 'show'}">
                        <th>主席</th>
                    </c:if>
                    <c:if test="${fuSuoZhang eq 'show'}">
                        <th>副所长</th>
                    </c:if>
                    <c:if test="${fenGuanLingDao eq 'show'}">
                        <th>分管领导</th>
                    </c:if>
                    <c:if test="${gongAnChu eq 'show'}">
                        <th>公安处</th>
                    </c:if>
                    <c:if test="${zeRenDanWei eq 'show'}">
                        <th>责任单位</th>
                    </c:if>
                    <c:if test="${fuZhuRen eq 'show'}">
                        <th>副主任</th>
                    </c:if>
                    <c:if test="${zhuYaoLingDao eq 'show'}">
                        <th>主要领导</th>
                    </c:if>
                    <c:if test="${qiTaFuZhi eq 'show'}">
                        <th>其他副职</th>
                    </c:if>
                    <c:if test="${fuDaDuiZhang eq 'show'}">
                        <th>副大队长</th>
                    </c:if>
                    <c:if test="${baoBaoZhiDui eq 'show'}">
                        <th>包保支队领导</th>
                    </c:if>
                    <c:if test="${qiTaLingDao eq 'show'}">
                        <th>其他领导</th>
                    </c:if>
                    <th>备注</th>
                    <!--
                    <th>上级检查评分情况</th>
                    -->
                    <%--<c:choose>
                        <c:when test="${'2'.equals(modleType)}">
                            <th>加分(实际)</th>
                        </c:when>
                        <c:otherwise>
                            <th>扣分(实际)</th>
                        </c:otherwise>
                    </c:choose>--%>
                    <th>加扣分(实际)</th>
                    <th>具体事项</th>
                    <th>附件</th>
                    <th>理由</th>
                    <th>状态</th>
                    <c:if test="${processType == 'appraiseExam'}">
                        <th>操作</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${workflowDatasList}" varStatus="status"  var="workflowData">
                    <tr
                            <c:choose>
                                <c:when test="${workflowData.isAlter == '2'}">
                                    style="color:#be44ff;"
                                </c:when>
                                <c:when test="${workflowData.isAlter == '1'}">
                                    style="color:#71ff3c;"
                                </c:when>
                                <c:when test="${workflowData.isAlreadySelected == '9' || workflowData.isAlreadySelected == '66'|| workflowData.isAlreadySelected == '99'}">
                                    style="color:red;"
                                </c:when>
                                <c:when test="${workflowData.isAlreadySelected == '77'}">
                                    style="color:#2b2cff;"
                                </c:when>
                                <c:otherwise>

                                </c:otherwise>
                            </c:choose>
                    >
                        <td>
                            <c:choose>
                                <c:when test="${workflowData.isSelected == '3'}">
                                    已删除
                                </c:when>
                                <c:otherwise>
                                    <input class='i-checks' type='checkbox' name='selectedBox'
                                           value='${workflowData.id}'/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${workflowData.fillPerson}</td>
                        <td>
                             ${fns:getDictLabel(workflowData.workName, 'exam_weigths', '')}
                        </td>
                        <td>${workflowData.s1}</td>
                        <td>${workflowData.s2}</td>
                        <td>${workflowData.s3}</td>
                        <td>
                            <c:choose>
                                <c:when test="${workflowData.special == 'no'}"></c:when><c:when test="${workflowData.valueType == 1}">
                                    ${workflowData.s4}
                                </c:when>
                                <c:when test="${workflowData.valueType == -1}">
                                    ${workflowData.s5}
                                </c:when>
                            </c:choose>
                        </td>
                        <c:if test="${benRen eq 'show'}">
                            <th>${workflowData.benRen}</th>
                        </c:if>
                        <c:if test="${zhuRen eq 'show'}">
                            <th>${workflowData.zhuRen}</th>
                        </c:if>
                        <c:if test="${shuJi eq 'show'}">
                            <th>${workflowData.shuJi}</th>
                        </c:if>
                        <c:if test="${zhuGuanLingDao eq 'show'}">
                            <th>${workflowData.zhuGuanLingDao}</th>
                        </c:if>
                        <c:if test="${chuZhang eq 'show'}">
                            <th>${workflowData.chuZhang}</th>
                        </c:if>
                        <c:if test="${fuChuZhang eq 'show'}">
                            <th>${workflowData.fuChuZhang}</th>
                        </c:if>
                        <c:if test="${fuZhi eq 'show'}">
                            <th>${workflowData.fuZhi}</th>
                        </c:if>
                        <c:if test="${fuZhiDuiZhang eq 'show'}">
                            <th>${workflowData.fuZhiDuiZhang}</th>
                        </c:if>
                        <c:if test="${suoZhang eq 'show'}">
                            <th>${workflowData.suoZhang}</th>
                        </c:if>
                        <c:if test="${jiaoDaoYuan eq 'show'}">
                            <th>${workflowData.jiaoDaoYuan}</th>
                        </c:if>
                        <c:if test="${zhengWei eq 'show'}">
                            <th>${workflowData.zhengWei}</th>
                        </c:if>
                        <c:if test="${zhiDuiZhang eq 'show'}">
                            <th>${workflowData.zhiDuiZhang}</th>
                        </c:if>
                        <c:if test="${daDuiZhang eq 'show'}">
                            <th>${workflowData.daDuiZhang}</th>
                        </c:if>
                        <c:if test="${zhuXi eq 'show'}">
                            <th>${workflowData.zhuXi}</th>
                        </c:if>
                        <c:if test="${fuSuoZhang eq 'show'}">
                            <th>${workflowData.fuSuoZhang}</th>
                        </c:if>
                        <c:if test="${fenGuanLingDao eq 'show'}">
                            <th>${workflowData.fenGuanLingDao}</th>
                        </c:if>
                        <c:if test="${gongAnChu eq 'show'}">
                            <th>${workflowData.gongAnChu}</th>
                        </c:if>
                        <c:if test="${zeRenDanWei eq 'show'}">
                            <th>${workflowData.zeRenDanWei}</th>
                        </c:if>
                        <c:if test="${fuZhuRen eq 'show'}">
                            <th>${workflowData.fuZhuRen}</th>
                        </c:if>
                        <c:if test="${zhuYaoLingDao eq 'show'}">
                            <th>${workflowData.zhuYaoLingDao}</th>
                        </c:if>
                        <c:if test="${qiTaFuZhi eq 'show'}">
                            <th>${workflowData.qiTaFuZhi}</th>
                        </c:if>
                        <c:if test="${fuDaDuiZhang eq 'show'}">
                            <th>${workflowData.fuDaDuiZhang}</th>
                        </c:if>
                        <c:if test="${baoBaoZhiDui eq 'show'}">
                            <th>${workflowData.baoBaoZhiDui}</th>
                        </c:if>
                        <c:if test="${qiTaLingDao eq 'show'}">
                            <th>${workflowData.qiTaLingDao}</th>
                        </c:if>
                        <td>${workflowData.s6}</td>
                        <td><input type='text' name='datas[${status.index}].value' style='width:30px;' class='input-xlarge  number required'
                                   value='${workflowData.value}'<c:if test="${'3'.equals(row.operationStatus)}">readonly="readonly"</c:if>
                                   <c:if test="${workflowData.isSelected == '3'}">disabled="disabled"</c:if>
                        /></td>
                        <td><textarea rows='2' name='datas[${status.index}].items' cols='10'
                                      <c:if test="${workflowData.isSelected == '3'}">disabled="disabled"</c:if>
                            >${workflowData.items}</textarea></td>
                        <td>
                            <input id="datas${status.index}-nameFile" name="datas[${status.index}].path" type="hidden"
                                   value="${workflowData.path}" class="fileinput"/>
                            <ol id="datas${status.index}-nameFilePreview"></ol>
                            <div id="datas${status.index}-nameFileUploaderDiv" class="nameFileUploaderDiv">
                                <div class="queueList">
                                    <div id="nameFileDndArea" class='placeholder'>
                                        <div class="nameFileFilePicker" onclick="setNumber('${status.index}')"></div>
                                    </div>
                                </div>
                                <div class="statusBar" style="display:none;">
                                    <div class="progress">
                                        <span class="text">0%</span>
                                        <span class="percentage"></span>
                                    </div><div class="info"></div>
                                    <div class="btns">
                                        <div id="datas${status.index}-nameFileFilePicker2"></div><div class="uploadBtn">开始上传</div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>${workflowData.detail}</td>
                        <td>${ fns:getDictLabel (workflowData.operationStatus, "workflow_operation_status", "")}</td>
                        <c:if test="${processType == 'appraiseExam'}">
                            <td>
<%--                                <a href="javascript:;">删除</a>--%>
                                <a href="${ctx}/exam/examWorkflowDatas/id=${workflowData.id}}">推送</a>
    <c:choose>
        <c:when test="${nextStep == '4'}">
            <%--选择每一条考评项的下一环节评审人--%>
            <sys:treeselect id="datas${status.index}person" name="datas[${status.index}].departSignId" value="${workflowData.departSignId}" labelName="datas[${status.index}].departSign" labelValue="${workflowData.departSign}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true"/>
        </c:when>
        <c:when test="${nextStep == '5'}">
            <%--选择每一条考评项的下一环节评审人--%>
            <sys:treeselect id="datas${status.index}person" name="datas[${status.index}].partBureausSignId" value="${workflowData.partBureausSignId}" labelName="datas[${status.index}].partBureausSign" labelValue="${workflowData.partBureausSign}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true"/>
        </c:when>
        <c:when test="${nextStep == '6'}">
            <%--选择每一条考评项的下一环节评审人--%>
            <sys:treeselect id="datas${status.index}person" name="datas[${status.index}].adjustPersonId" value="${workflowData.adjustPersonId}" labelName="datas[${status.index}].adjustPerson" labelValue="${workflowData.adjustPerson}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true"/>
        </c:when>
        <c:when test="${nextStep == '7'}">
            <%--选择每一条考评项的下一环节评审人--%>
            <sys:treeselect id="datas${status.index}person" name="datas[${status.index}].mainBureausSignId" value="${workflowData.mainBureausSignId}" labelName="datas[${status.index}].mainBureausSign" labelValue="${workflowData.mainBureausSign}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true"/>
        </c:when>

    </c:choose>
                            </td>
                        </c:if>
                        <input type='hidden' name='datas[${status.index}].standardId'
                               value='${workflowData.standardId}'/>
                        <input type='hidden' name='datas[${status.index}].workflowId'
                               value='${workflowData.workflowId}'/>
                        <input type='hidden' name='datas[${status.index}].rowId'
                               value='${workflowData.rowId}'/>
                        <input type='hidden' name='datas[${status.index}].id'
                               value='${workflowData.id}'/>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
            <input class="btn btn-primary"  type="button" value="返回" onclick="back()" >
        </div>
    </div>
</div>
