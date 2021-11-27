<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript"  src="${ctxStatic}/js/common.js?t='20210413'"></script>
<meta name="decorator" content="default"/>
<script>
    //生成表格
    function createTable() {
        var url = "iframe:${ctx}/exam/examWorkflow/createTable?workflowId="+$("#workflowId").val();
        top.$.jBox.open(url, "目前各项得分情况",1000,600,{
            buttons:{"关闭":true},
            loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            }
        });
    }

    /*推送*/
    function push(id,standardContent) {
        this.$.jBox.open("iframe:${ctx}/exam/examWorkflowDatas/pushView?id="+id+"&s3="+standardContent, "推送考评",400,height,{
            id:"termIFrame",
            target:top,
            buttons:{"关闭":true},
            loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            },closed:function (){
                self.location.href="${ctx}/exam/examWorkflowDatas/examBetaById?examWorkflowId=${workflowId}&examType=${examType}&fillPersonId=${fillPersonId}";
            }
        });
    }

    $(document).ready(function () {
        $("#inputForm").validate();
    });
</script>
<!-- 绩效考评-系统公示 -->
<div class="">
    <div class="">
        <div class="modal-custom-main">
            <%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
            &nbsp;&nbsp;${headItemScoreMap.cgInfo}&nbsp;&nbsp;${headItemScoreMap.zdInfo}&nbsp;&nbsp;${headItemScoreMap.decInfo}&nbsp;&nbsp;${headItemScoreMap.addInfo}&nbsp;&nbsp;
            <input id="check" class="btn btn-primary" type="button" value="生成表格" onclick="createTable()"/>
            <input id="workflowId" name="workflowId" value="${workflowId}" type="hidden"/>
            <div class="modal-custom-content">
                <table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>单位名称</th>
                        <th>总得分</th>
                        <th>基础分</th>
                        <th>常规业务扣分</th>
                        <c:if test="${examTypePublic ne '5' and examTypePublic ne '6' and examTypePublic ne '7'}">
                            <th>重点业务扣分</th>
                        </c:if>
                        <c:if test="${examTypePublic eq '3' or (!empty isAddWorkItemExam and isAddWorkItemExam eq 'isAddWorkItemExam')}">
                            <th>得分制工作项得分</th>
                        </c:if>
                        <th>公共扣分</th>
                        <th>公共加分</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${objScoreList}" var="obj">
                        <tr>
                            <td>${obj.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${examTypePublic eq '3'}">
                                        ${obj.sumWorkScore}
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${!empty obj.sumWorkScore}">
                                                ${obj.sumWorkScore}
                                            </c:when>
                                            <c:when test="${empty obj.baseSum}">
                                                ${100-obj.conventionWorkScore-obj.importWorkScore-obj.commonSubstractScore+obj.commonAddScore}
                                            </c:when>
                                            <c:otherwise>
                                                ${obj.baseSum-obj.conventionWorkScore-obj.importWorkScore-obj.commonSubstractScore+obj.commonAddScore}
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                <c:when test="${examTypePublic eq '3'}">
                                    ${obj.baseSum}
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${empty obj.baseSum}">
                                            100
                                        </c:when>
                                        <c:otherwise>
                                            ${obj.baseSum}
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                            </td>
                            <td>${obj.conventionWorkScore}</td>
                            <c:if test="${examTypePublic ne '5' and examTypePublic ne '6' and examTypePublic ne '7'}">
                            <td>${obj.importWorkScore}</td>
                            </c:if>
                            <c:if test="${examTypePublic eq '3' or (!empty isAddWorkItemExam and isAddWorkItemExam eq 'isAddWorkItemExam')}">
                                <td>${obj.addWorkItemScore}</td>
                            </c:if>
                            <td>${obj.commonSubstractScore}</td>
                            <td>${obj.commonAddScore}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>


            <script	type="text/javascript">

                let height =  $(window).height()-200;
                /*子iframe获取不到let类型 可以获取到var类型*/
                var test =  "咋获取不到呢";
                let width =  $(window).width()-200;

                /*	function sendSubmit(processType, personType) {
                        //debugger;er;
                        if(null == $("#personId").val()||'' == $("#personId").val()){
                            jBox.alert("请选择推送人");
                            return false;
                        }
                        $("#status").attr("value",status);
                        $("#operationStatus").attr("value",operationStatus);
                        var workflowId = '${examWorkflow.id}';
		$("#workflowId").attr("value",workflowId);
        document.getElementById("workflowId").value=workflowId;
        alert($("#workflowId").val());
		$("#processType").val(processType);
		$("#personType").val(personType);
		$("#inputForm").submit();
	}*/

                function selectTerm() {
                    <c:choose>
                    <c:when test="${standardInfoList.size() != 0 }">
                    var url = "iframe:${ctx}/exam/examWorkflow/selectTerm?standardId=${standardInfoList.get(0).id}&workflowId=${workflowId}&objId=${objId}&fillPersonId=${fillPersonId}";
                    </c:when>
                    <c:otherwise>
                    var url = "iframe:${ctx}/exam/examWorkflow/selectTerm?&workflowId=${workflowId}&objId=${objId}&fillPersonId=${fillPersonId}";
                    </c:otherwise>
                    </c:choose>
                    <%--var url = "iframe:${ctx}/exam/examWorkflow/selectTerm?standardId=${standardInfoList.get(0).id}&workflowId=${workflowId}&objId=${objId}&fillPersonId=${fillPersonId}";--%>
                    this.$.jBox.open(url, "选择考评项",width,height,{
                        id:"termIFrame",
                        target:top,
                        buttons:{"关闭":true},
                        loaded:function(h){
                            $(".jbox-content", top.document).css("overflow-y","hidden");
                        },closed:function (){
                            self.location.href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&examType=${examType}&fillPersonId=${fillPersonId}";
                        }
                    });
                }

                //得到被选中的checkbox的数据id
                function getIds(checkBoxsName) {
                    var ids = [];
                    $("input:checkbox[type=checkbox]:checked").each(function () {
                        if ($(this).val() != "on"){
                            ids.push($(this).val());
                        }
                    });
                    return ids;
                };
                function deleteByIdsBeta(url,checkAllId,checkBoxsName) {
                    var delIds = getIds(checkBoxsName);
                    if (delIds.length > 0) {
                        $.ajax({
                            type:"post",
                            url:url,
                            data:{ids:delIds},
                            dataType:"json",
                            success:function(data){
                                $.jBox.tip(data.message);
                                // resetCheckBox(checkAllId,checkBoxsName);
                                location.reload();
                            }
                        })
                    } else {
                        $.jBox.tip('请先选择要删除的内容');
                    }
                };
                function removeByIds() {
                    deleteByIdsBeta('${ctx}/exam/examWorkflowDatas/removeByIds','checkAll','selectedBox');

                }



                function formSubmit(processType) {
                    $("#processType").val(processType);
                    $("#inputForm").submit();
                }
                function sendSubmit(processType, personType) {
                    // if(null == $("#personId").val()||'' == $("#personId").val()){
                    //     jBox.alert("请选择推送人");
                    //     return false;
                    // }
                    var workflowId = '${examWorkflow.id}';
                    $("#workflowId").attr("value",workflowId);
                    $("#processType").val(processType);
                    $("#personType").val(personType);
                    $("#inputForm").submit();
                }
            </script>
            <div>
                <form:form id="inputForm" action="${ctx}/exam/examWorkflowDatas/saveBetaHistory" method="post" class="form-horizontal">
                    <input id="num" name="num" value="0" type="hidden"/>
                    <input id="status" name="status" value="${status}" type="hidden"/>
                    <input id="workflowId" name="workflowId" value="${workflowId}" type="hidden"/>
                    <input id="operationStatus" name="operationStatus" value="" type="hidden"/>
                    <input id="processType" name="processType" value="${processType}" type="hidden"/>
                    <input id="personType" name="personType" value="${personType}" type="hidden"/>
                    <input id="nextStatus" name="nextStatus" value="${nextStep}" type="hidden"/>
                    <input id="fillPersonId" name="fillPersonId" value="${fillPersonId}" type="hidden"/>
                    <input id="noTree" name="noTree" value="${noTree}" type="hidden"/>


                    <sys:message content="${message}"/>
                    <div style="color: red">(说明：红色字体为推送的考评项,蓝色字体为自动考评,绿色为审核人修改，紫色为绩效办修改)</div>
                    <table id="contentTable" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>选择</th>
                                <%--<c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                    <th>${examStandardTemplateItemDefine.columnName}</th>
                                </c:forEach>--%>
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
                           <%-- <c:choose>
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
                            <c:if test="${processType == 'appraiseExam' || fns:getUser().id == examPersonId}">
                                <th>操作</th>
                                <th>
                                    <c:choose>
                                        <c:when test="${nextStep == '4'}">
                                            <%--选择每一条考评项的下一环节评审人--%>
                                            部门负责人签字环节审核人
                                        </c:when>
                                        <c:when test="${nextStep == '5'}">
                                            <%--选择每一条考评项的下一环节评审人--%>
                                            分管局领导签字环节审核人
                                        </c:when>
                                        <c:when test="${nextStep == '6'}">
                                            <%--选择每一条考评项的下一环节评审人--%>
                                            绩效办调整环节审核人
                                        </c:when>
                                        <c:when test="${nextStep == '7'}">
                                            <%--选择每一条考评项的下一环节评审人--%>
                                            局领导签字环节审核人
                                        </c:when>

                                    </c:choose>
                                    <span class="help-inline"><font color="red">*</font> </span>
                                </th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${workflowDatasList}" varStatus="status"  var="workflowData">
                            <tr  <c:choose>
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
                            </c:choose>>
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
                                <td>${workflowData.value}</td>
                                <td>${workflowData.items}</td>
                                <td>
                                    <input id="datas${status.index}-nameFile" name="datas[${status.index}].path" type="hidden"
                                           value="${workflowData.path}" class="fileinput"/>
                                    <ol id="datas${status.index}-nameFilePreview"></ol>
                                  <%--  <div id="datas${status.index}-nameFileUploaderDiv" class="nameFileUploaderDiv">
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
                                    </div>--%>
                                </td>
                                <td>${workflowData.detail}</td>
                                <td <c:if test="${'2' == workflowData.operationStatus}">style="color:red;" </c:if> >${ fns:getDictLabel (workflowData.operationStatus, "workflow_operation_status", "")}</td>
                                <c:if test="${processType == 'appraiseExam' || fns:getUser().id == examPersonId}">
                                    <td>
<%--                                        <a href="javascript:;">删除</a>--%>
                                        <a href="javascript:;" onclick="push('${workflowData.id}','${workflowData.s3}')">推送</a>

                                    <span class="help-inline"><font color="red">*</font> </span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${nextStep == '4'}">
                                                <%--选择每一条考评项的下一环节评审人--%>
                                                <sys:treeselect id="datas${status.index}person" name="datas[${status.index}].departSignId" value="${workflowData.departSignId}" labelName="datas[${status.index}].departSign" labelValue="${workflowData.departSign}"
                                                                title="部门负责人签字环节审核人" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true" dataMsgRequired="必填信息" />
                                            </c:when>
                                            <c:when test="${nextStep == '5'}">
                                                <%--选择每一条考评项的下一环节评审人--%>
                                                <sys:treeselect id="datas${status.index}person" name="datas[${status.index}].partBureausSignId" value="${workflowData.partBureausSignId}" labelName="datas[${status.index}].partBureausSign" labelValue="${workflowData.partBureausSign}"
                                                                title="分管局领导签字环节审核人" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true" dataMsgRequired="必填信息"/>
                                            </c:when>
                                            <c:when test="${nextStep == '6'}">
                                                <%--选择每一条考评项的下一环节评审人--%>
                                                <sys:treeselect id="datas${status.index}person" name="datas[${status.index}].adjustPersonId" value="${workflowData.adjustPersonId}" labelName="datas[${status.index}].adjustPerson" labelValue="${workflowData.adjustPerson}"
                                                                title="绩效办调整环节审核人" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true" dataMsgRequired="必填信息"/>
                                            </c:when>
                                            <c:when test="${nextStep == '7'}">
                                                <%--选择每一条考评项的下一环节评审人--%>
                                                <sys:treeselect id="datas${status.index}person" name="datas[${status.index}].mainBureausSignId" value="${workflowData.mainBureausSignId}" labelName="datas[${status.index}].mainBureausSign" labelValue="${workflowData.mainBureausSign}"
                                                                title="局领导签字环节审核人" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" isAll="true" dataMsgRequired="必填信息"/>
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
                </form:form>
            </div>
            <script type="text/javascript">
                function nameFileDel(obj,num){
                    var url = $(obj).prev().attr("url");
                    $("#datas"+num+"-nameFile").val($("#datas"+num+"-nameFile").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
                    nameFilePreview(num);
                }
                function nameFileDelAll(num){
                    $("#datas"+num+"-nameFile").val("");
                    nameFilePreview();
                }
                function nameFilePreview(num){
                    var li, urls = $("#"+"datas"+num+"-nameFile").val().split("|");
                    $("#datas"+num+"-nameFilePreview").children().remove();
                    for (var i=0; i<urls.length; i++){
                        if (urls[i]!=""){//
                            li = "<li><a href=\"/politics" +urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//
                            li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"nameFileDel(this,"+num+");\">×</a></li>";
                            $("#datas"+num+"-nameFilePreview").append(li);
                        }
                    }
                    if ($("#datas"+num+"-nameFilePreview").text() == ""){
                        $("#datas"+num+"-nameFilePreview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
                    }
                }
                function setNumber(val) {
                    //debugger;er
                    $('#num').val(val);
                }

                function initFilePreview() {
                    $(".fileinput").each(function (index,el) {
                        var li, urls = $("#"+"datas"+index+"-nameFile").val().split("|");
                        $("#datas"+index+"-nameFilePreview").children().remove();
                        for (var i=0; i<urls.length; i++){
                            if (urls[i]!=""){//
                                li = "<li><a href=\"/politics" +urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//
                                li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"nameFileDel(this,"+index+");\">×</a></li>";
                                $("#datas"+index+"-nameFilePreview").append(li);
                            }
                        }
                        if ($("#datas"+index+"-nameFilePreview").text() == ""){
                            $("#datas"+index+"-nameFilePreview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
                        }
                    });
                }
                initFilePreview();
            </script>
            <script type="text/javascript">
                function init(){
                    $(function() {
                        var num = $('#num').val();
                        var $wrap = $('#datas'+num+'-nameFileUploaderDiv'),
                            $wrap = $('.nameFileUploaderDiv'),
                            // 图片容器
                            $queue = $( '<ul class="filelist"></ul>' )
                                .appendTo( $wrap.find( '.queueList' ) ),

                            // 状态栏，包括进度和控制按钮
                            $statusBar = $wrap.find( '.statusBar' ),

                            // 文件总体选择信息。
                            $info = $statusBar.find( '.info' ),

                            // 上传按钮
                            $upload = $wrap.find( '.uploadBtn' ),

                            // 没选择文件之前的内容。
                            $placeHolder = $wrap.find( '.placeholder' ),
                            $progress = $statusBar.find( '.progress' ).hide(),

                            // 添加的文件数量
                            fileCount = 0,
                            // 添加的文件总大小
                            fileSize = 0,

                            // 优化retina, 在retina下这个值是2
                            ratio = window.devicePixelRatio || 1,

                            // 缩略图大小
                            thumbnailWidth = 110 * ratio,
                            thumbnailHeight = 110 * ratio,

                            // 可能有pedding, ready, uploading, confirm, done.
                            state = 'pedding',

                            // WebUploader实例
                            uploader;
                        // 实例化
                        uploader = WebUploader.create({
                            auto:true,
                            pick: {
                                id: '.nameFileFilePicker',
                                multiple:true,
                                label: '选择'
                            },
                            formData: {
                                path: "affair/exam"
                            },
                            //dnd: '#nameFileDndArea',
                            //paste: '#nameFileUploaderDiv',
                            swf: '${ctxStatic}/webuploader/0.1.5/Uploader.swf',
                            chunked: false,
                            server: '${ctx}/file/upload',
                            // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
                            disableGlobalDnd: true,
                            fileNumLimit: 20,
                            fileSizeLimit: 200 * 1024 * 1024,    // 200 M
                            fileSingleSizeLimit: 50 * 1024 * 1024    // 50 M
                        });

                        // 拖拽时不接受 js, txt 文件。
                        uploader.on( 'dndAccept', function( items ) {
                            var denied = false,
                                len = items.length,
                                i = 0,
                                // 修改js类型
                                unAllowed = 'text/plain;application/javascript ';

                            for ( ; i < len; i++ ) {
                                // 如果在列表里面
                                if ( ~unAllowed.indexOf( items[ i ].type ) ) {
                                    denied = true;
                                    break;
                                }
                            }

                            return !denied;
                        });

                        uploader.on('ready', function() {
                            window.uploader = uploader;
                        });

                        // 当有文件添加进来时执行，负责view的创建
                        function addFile( file ) {
                            var $li = $( '<li id="' + file.id + '">' +
                                '<p class="title">' + file.name + '</p>' +
                                // '<p class="imgWrap"></p>'+
                                '<p class="progress"><span></span></p>' +
                                '</li>' ),

                                $btns = $('<div class="file-panel">' +
                                    '<span class="cancel">删除</span>' +
                                    '<span class="rotateRight">向右旋转</span>' +
                                    '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
                                $prgress = $li.find('p.progress span'),
                                $wrap = $li.find( 'p.imgWrap' ),
                                $info = $('<p class="error"></p>'),

                                showError = function( code ) {
                                    switch( code ) {
                                        case 'exceed_size':
                                            text = '文件大小超出';
                                            break;
                                        case 'interrupt':
                                            text = '上传暂停';
                                            break;
                                        default:
                                            text = '上传失败，请重试';
                                            break;
                                    }
                                    $info.text( text ).appendTo( $li );
                                };

                            file.on('statuschange', function( cur, prev ) {
                                if ( prev === 'progress' ) {
                                    $prgress.hide().width(0);
                                } else if ( prev === 'queued' ) {
                                    $li.off( 'mouseenter mouseleave' );
                                    $btns.remove();
                                }
                                // 成功
                                if ( cur === 'error' || cur === 'invalid' ) {
                                    showError( file.statusText );
                                    // percentages[ file.id ][ 1 ] = 1;
                                } else if ( cur === 'interrupt' ) {
                                    showError( 'interrupt' );
                                } else if ( cur === 'queued' ) {
                                    // percentages[ file.id ][ 1 ] = 0;
                                } else if ( cur === 'progress' ) {
                                    $info.remove();
                                    $prgress.css('display', 'block');
                                } else if ( cur === 'complete' ) {
                                    $li.append( '<span class="success"></span>' );
                                }
                                $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
                            });
                            $li.on( 'mouseenter', function() {
                                $btns.stop().animate({height: 30});
                            });
                            $li.on( 'mouseleave', function() {
                                $btns.stop().animate({height: 0});
                            });
                            $btns.on( 'click', 'span', function() {
                                var index = $(this).index(),
                                    deg;
                                switch ( index ) {
                                    case 0:
                                        uploader.removeFile( file );
                                        return;
                                    case 1:
                                        file.rotation += 90;
                                        break;
                                    case 2:
                                        file.rotation -= 90;
                                        break;
                                }
                                if ( supportTransition ) {
                                    deg = 'rotate(' + file.rotation + 'deg)';
                                    $wrap.css({
                                        '-webkit-transform': deg,
                                        '-mos-transform': deg,
                                        '-o-transform': deg,
                                        'transform': deg
                                    });
                                } else {
                                    $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                                }
                            });
                            $li.appendTo( $queue );
                        }

                        // 负责view的销毁
                        function removeFile( file ) {
                            var $li = $('#'+file.id);
                            updateTotalProgress();
                            $li.off().find('.file-panel').off().end().remove();
                        }

                        function updateTotalProgress() {
                            updateStatus();
                        }

                        function updateStatus() {
                            var text = '', stats;
                            var typeName = '文件', unit='份';
                            if ( state === 'ready' ) {
                                text = '选中' + fileCount + unit + typeName+'，共' +
                                    WebUploader.formatSize(fileSize) + '。';
                            } else if ( state === 'confirm' ) {
                                stats = uploader.getStats();
                                if ( stats.uploadFailNum ) {
                                    text = '已成功上传' + stats.successNum + unit + typeName+'，'
                                    stats.uploadFailNum + unit + typeName+'上传失败，<a class="retry" href="#">重新上传</a>失败'+typeName+''
                                }

                            } else {
                                stats = uploader.getStats();
                                text = '共' + fileCount + unit + '（' +
                                    WebUploader.formatSize(fileSize) +
                                    '），已上传' + stats.successNum + unit;

                                if (stats.uploadFailNum) {
                                    text += '，失败' + stats.uploadFailNum + unit;
                                }
                            }
                            if($(".fileinput").length>0){
                                nameFilePreview($("#num").val());
                            }
                            //$info.html( text );
                        }

                        function setState( val ) {
                            var file, stats;
                            if ( val === state ) {
                                return;
                            }
                            $upload.removeClass( 'state-' + state );
                            $upload.addClass( 'state-' + val );
                            state = val;
                            var num = $('#num').val();
                            switch ( state ) {
                                case 'pedding':
                                    $placeHolder.removeClass( 'element-invisible' );
                                    $queue.hide();
                                    $statusBar.addClass( 'element-invisible' );
                                    uploader.refresh();
                                    break;
                                case 'ready':
                                    $placeHolder.addClass( 'element-invisible' );
                                    $('#datas'+num+'nameFileFilePicker2').removeClass( 'element-invisible');
                                    $queue.show();
                                    $statusBar.removeClass('element-invisible');
                                    uploader.refresh();
                                    break;
                                case 'uploading':
                                    $('#datas'+num+'nameFileFilePicker2').addClass( 'element-invisible' );
                                    $progress.show();
                                    //$upload.text( '暂停上传' );
                                    break;
                                case 'paused':
                                    $progress.show();
                                    // $upload.text( '继续上传' );
                                    break;
                                case 'confirm':
                                    $progress.hide();
                                    $('#datas'+num+'nameFileFilePicker2').removeClass( 'element-invisible' );
                                    //$upload.text( '开始上传' );
                                    stats = uploader.getStats();
                                    if ( stats.successNum && !stats.uploadFailNum ) {
                                        setState( 'finish' );
                                        return;
                                    }
                                    break;
                                case 'finish':
                                    stats = uploader.getStats();
                                    if ( stats.successNum ) {

                                    } else {
                                        // 没有成功的图片，重设
                                        state = 'done';
                                        location.reload();
                                    }
                                    break;
                            }
                            updateStatus();
                        }

                        uploader.onUploadProgress = function( file, percentage ) {
                            var $li = $('#'+file.id),
                                $percent = $li.find('.progress span');
                            $percent.css( 'width', percentage * 100 + '%' );
                            //percentages[ file.id ][ 1 ] = percentage;
                            updateTotalProgress();
                        };

                        uploader.onFileQueued = function( file ) {
                            fileCount++;
                            fileSize += file.size;
                            if ( fileCount === 1 ) {
                                $placeHolder.addClass( 'element-invisible' );
                                $statusBar.show();
                            }
                            addFile( file );
                            setState( 'ready' );
                            updateTotalProgress();
                        };

                        uploader.onFileDequeued = function( file ) {
                            fileCount--;
                            fileSize -= file.size;
                            if ( !fileCount ) {
                                setState( 'pedding' );
                            }
                            removeFile( file );
                            updateTotalProgress();
                        };

                        uploader.on( 'uploadSuccess', function( file,data) {
                            if(data.success){
                                var num = $('#num').val();
                                var url = data.result;
                                var newUrl = "";
                                var oldUrl = $("#datas"+num+"-nameFile").val();
                                if(oldUrl==""){
                                    newUrl = url;
                                } else if (oldUrl.indexOf(url) == -1 ){
                                    newUrl = oldUrl + "|" + url;
                                } else {
                                    var uArr = url.split("/");
                                    var name = uArr[uArr.length - 1];
                                    alert(name + " 已存在");
                                    newUrl = oldUrl;
                                }
                                $("#datas"+num+"-nameFile").val(newUrl);
                            }
                            $('.filelist').hide();
                            $('.statusBar').hide();
                        });

                        uploader.on( 'all', function( type ) {
                            var stats;
                            switch( type ) {
                                case 'uploadFinished':
                                    setState( 'confirm' );
                                    break;
                                case 'startUpload':
                                    setState( 'uploading' );
                                    break;
                                case 'stopUpload':
                                    setState( 'paused' );
                                    break;
                            }
                        });

                        uploader.onError = function( code ) {
                            var text = '';
                            if(code=="Q_TYPE_DENIED"){
                                text = "文件类型不支持，仅支持gif,jpg,jpeg,bmp,png格式图片";
                            }else if(code=="F_DUPLICATE"){
                                text = "文件已选中，请勿重复上传";
                            }else if(code="F_EXCEED_SIZE"){
                                text = "文件大小超出限制，不得超过5MB";
                            } else{
                                text = 'Error: ' + code;
                            }
                            $info.html( text );
                        };

                        $upload.on('click', function() {
                            if ( $(this).hasClass( 'disabled' ) ) {
                                return false;
                            }
                            if ( state === 'ready' ) {
                                uploader.upload();
                            } else if ( state === 'paused' ) {
                                uploader.upload();
                            } else if ( state === 'uploading' ) {
                                uploader.stop();
                            }
                        });
                        $info.on( 'click', '.retry', function() {
                            uploader.retry();
                        } );
                        $info.on( 'click', '.ignore', function() {
                        } );
                        $upload.addClass( 'state-' + state );
                        updateTotalProgress();
                    });
                }
                init();
            </script>
        </div>
    </div>
</div>