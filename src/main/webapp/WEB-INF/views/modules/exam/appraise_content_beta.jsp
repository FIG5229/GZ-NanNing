<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

    <script>
        //if ("selfAppraise" == "${processType}") {
            //parent.window.location.href = "${ctx}/exam/examWorkflow/flowList?examType=${examType}";
        //}

        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
    <script type="text/javascript">
        let height =  $(window).height()-200;
        /*子iframe获取不到let类型 可以获取到var类型*/
        var test =  "咋获取不到呢";
        let width =  $(window).width()-200;


        function selectTerm() {
            var url = "iframe:${ctx}/exam/examWorkflow/selectTerm?standardId=${standardInfoList.get(0).id}&workflowId=${workflowId}&objId=${objId}&fillPersonId=${fillPersonId}";
            this.$.jBox.open(url, "选择考评项",width,height,{
                id:"termIFrame",
                target:top,
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){
                    self.location.href="${ctx}/exam/examWorkflowDatas/examBeta?examWorkflowId=${workflowId}&examType=${examType}";
                }
            });
        }
        /*
        *   selectedBoxes: 在弹窗中选则的考评项
        * */
        let columnList = [];


        function addBeta(selectedBoxes,rowList,standardId,workflowId) {
            console.log("standardId:"+standardId+"  workflowId:"+workflowId);
            <c:forEach items="${columnList}" var="columnList">
                columnList.push("${columnList}");
            </c:forEach>
            <%--var columnList =  $.parseJSON('${columnListJson}');--%>


                            <%--let rowList = $.parseJSON('${rowlistJson}');--%>
                            for (let index = 0; index < selectedBoxes.length; index++) {
                                let rowNum = selectedBoxes[index];
                                /*减行号*/
                                //debugger;er;
                                /*rowNum+rowList[0].row_num时会变成字符串相加*/
                                let rowId = rowNum;
                                rowNum = rowNum - parseInt(rowList[0].row_num);
                                let row = rowList[rowNum];
                                console.log(row);
                                let html = "<tr id=r_"+rowNum+">" +
                                    "    <td><input class=\"i-checks\" type=\"checkbox\" name=\"selectedBox\" value=\""+rowNum+"\"></td>\n" ;
                                /*列类型 其他 分类 评分标准 加分 减分 备注*/
                                columnList = [9,4,1,2,3,6];
                                for (let i = 0; i < columnList.length; i++) {
                                    let cell = row[columnList[i]];
                                    /*加分 减分 只能同时存在一个*/
                                    if (columnList[i] == 2 && row[columnList[i]] == undefined){
                                        continue;
                                    }
                                    if (columnList[i] == 3 && row[columnList[i]] == undefined){
                                        continue;
                                    }
                                    if (cell == undefined) {
                                        cell = "";
                                    }
                                    html += "    <td>" + cell + "</td>\n";
                                }
                                html +="    <input type=\"hidden\" name=\"standardDatas["+rowNum+"].value\" value=\""+rowNum+"\">\n" ;
                                html +="    <input type=\"hidden\" name=\"standardDatas["+rowNum+"].valueType\" value=\"-1\">\n" ;
                                html += "<td><input type='text' name='datas[" + rowNum + "].value' style='width:30px;' class='input-xlarge  number required'/></td>\n";
                                /*具体事项*/
                                html += "<td><textarea name='datas[" + rowNum + "].items' rows='2' cols='8'></textarea></td>\n";
                                /*附件*/
                                html += "<td>\n";
                                html +=     "<input id='datas" + rowNum + "-nameFile' name='datas[" + rowNum + "].path' type='hidden' value='' class='fileinput'/>\n";
                                html +=     "<ol id='datas" + rowNum + "-nameFilePreview'></ol>\n";
                                html +=     "<div id='datas" + rowNum + "-nameFileUploaderDiv'>\n";
                                html +=         "<div class='queueList'>\n";
                                html +=             "<div id='nameFileDndArea' class='placeholder'>\n";
                                html +=                 "<div class='nameFileFilePicker' onclick='setNumber(" + rowNum + ")'></div>\n";
                                html +=             "</div>\n";
                                html +=         "</div>\n";
                                html +=         "<div class='statusBar' style='display:none;'>\n";
                                html +=             "<div class='progress'>\n";
                                html +=                 "<span class='text'>0%</span>\n";
                                html +=                 "<span class='percentage'></span>\n";
                                html +=             "</div>\n";
                                html +=             "<div class='info'></div>\n";
                                html +=             "<div class='btns'>\n";
                                html +=                 "<div id='datas" + rowNum + "-nameFileFilePicker2'></div>\n<div class='uploadBtn'>开始上传</div>\n";
                                html +=             "</div>\n";
                                html +=         "</div>\n";
                                html +=     "</div>\n";
                                html += "</td>\n";
                                html += "<td></td>\n";
                                html += "<td>未提交</td>\n";
                                html += "<input type='hidden' name='datas[" + rowNum + "].standardId' value='" + standardId + "'/>\n";
                                html += "<input type='hidden' name='datas[" + rowNum + "].workflowId' value='" + workflowId + "'/>\n";
                                /*id需要加上行号*/
                                html += "<input type='hidden' name='datas[" + rowNum + "].rowId' value='" +( rowNum+ parseInt(rowList[0].row_num)) + "'/>\n";
                                html += " <input type='hidden' name='datas[" + rowNum + "].id' value='" + row.id + "'/>\n";
                                html += "</tr>\n";

                                $("#r_body").append(html);
                            }
                            init();
            window.parent.jBox.close();
        }
        function createHtmlBeta(list,columnSum,index) {
            let cell = list[index];
            if (cell == null || cell == undefined)
                return ;
            let rowNum = cell["rowNum"];
            let html = "<tr id=r_"+rowNum+">" +
                "    <td><input class=\"i-checks\" type=\"checkbox\" name=\"selectedBox\" value=\""+rowNum+"\"></td>\n" ;
            for (let i = 0; i < columnSum; i++) {
                if (index < list.length) {
                    // let column = list[index].column_order;
                    let cell = list[index].itemValue;
                    // console.log("顺序列的值-->"+""+"-->"+cell);
                    html += "    <td>" + cell + "</td>\n";
                    index ++;
                    // console.log("第二个循环里-->"+index);
                }
            }
            // html+=
            //     "    <input type=\"hidden\" name=\"standardDatas["+rowNum+"].value\" value=\"3.0\">\n" +
            //     "    <input type=\"hidden\" name=\"standardDatas["+rowNum+"].valueType\" value=\"-1\">\n" ;

            /*扣分*/
            html += "<td><input type='text' name='datas[" + rowNum + "].value' style='width:30px;' class='input-xlarge  number required'/></td>";
            /*具体事项*/
            html += "<td><textarea name='datas[" + rowNum + "].items' rows='2' cols='8'></textarea></td>";
            /*附件*/
            html += "<td>";
            html +=     "<input id='datas" + rowNum + "-nameFile' name='datas[" + rowNum + "].path' type='hidden' value='' class='fileinput'/>";
            html +=     "<ol id='datas" + rowNum + "-nameFilePreview'></ol>";
            html +=     "<div id='datas" + rowNum + "-nameFileUploaderDiv'>";
            html +=         "<div class='queueList'>";
            html +=             "<div id='nameFileDndArea' class='placeholder'>";
            html +=                 "<div class='nameFileFilePicker' onclick='setNumber(" + rowNum + ")'></div>";
            html +=             "</div>";
            html +=         "</div>";
            html +=         "<div class='statusBar' style='display:none;'>";
            html +=             "<div class='progress'>";
            html +=                 "<span class='text'>0%</span>";
            html +=                 "<span class='percentage'></span>";
            html +=             "</div>";
            html +=             "<div class='info'></div>";
            html +=             "<div class='btns'>";
            html +=                 "<div id='datas" + rowNum + "-nameFileFilePicker2'></div><div class='uploadBtn'>开始上传</div>";
            html +=             "</div>";
            html +=         "</div>";
            html +=     "</div>";
            html += "</td>";
            html += "<td></td>";
            html += "<td>未提交</td>";
            html += "<input type='hidden' name='datas[" + rowNum + "].standardId' value='" + $("#standardId").val() + "'/>";
            html += "<input type='hidden' name='datas[" + rowNum + "].workflowId' value='" + $("#id").val() + "'/>";
            html += "<input type='hidden' name='datas[" + rowNum + "].rowId' value=' " + rowNum + " '/>";
            html += "</tr>";
            return html;
        }

        function refreshData() {
            /*先清除存在的行*/
            $.getJSON("${ctx}/exam/examWorkflow/selfTermList",
                {"standardId":'${standardId}',"workflowId":'${workflowId}'},
                function (data) {
                if (data.success ){
                    if (data.result != null){
                        let list = data.result;
                        /*找出有多少列*/
                        let columnSum =0;
                        for (let i = 0;i<list.length;i++){
                            if (columnSum <= list[i].columnOrder)
                                columnSum++;
                        }
                        /*第一行*/
                        $("#r_body").append(createHtmlBeta(list,columnSum,0));
                        for (let index = 0;index<list.length;) {
                            for (let j = 0;j<columnSum;j++){
                                index++;
                            }
                            $("#r_body").append(createHtmlBeta(list,columnSum,index));
                        }
                        init();
                    }
                } else {

                }
            });
        }
        function add(selectedBoxes) {
          /*  var selectedBoxes = [];
            $('input[name="selectedBox"]').each(function () {
                selectedBoxes.push($(this).val());
            });*/
            var selectedRowNum = 0;
            if (null != $("#selectedNumber").val() && '' != $("#selectedNumber").val()) {
                selectedRowNum = parseInt($("#selectedNumber").val());
            }

            var i = 0;
            var rowNum = 0;
            for (let index = 0;index<selectedBoxes.length;index++) {
                if ($.inArray($(this).val(), selectedBoxes) < 0) {
                    rowNum = selectedRowNum + i;
                    var html = "<tr id='r_" + rowNum + "'><td><input class='i-checks' type='checkbox' name='selectedBox' value='" + $(this).val() + "'/></td>"
                    html += $("#s_" + $(this).val()).html();
                    html = html.substr(0, html.lastIndexOf("<td>"));
                    //html += "<td></td>";
                    html += "<td><input type='text' name='datas[" + rowNum + "].value' style='width:30px;' class='input-xlarge  number required'/></td>";
                    html += "<td><textarea name='datas[" + rowNum + "].items' rows='2' cols='8'></textarea></td>";
                    html += "<td>";
                    html += "<input id='datas" + rowNum + "-nameFile' name='datas[" + rowNum + "].path' type='hidden' value='' class='fileinput'/>";
                    html += "<ol id='datas" + rowNum + "-nameFilePreview'></ol>";
                    html += "<div id='datas" + rowNum + "-nameFileUploaderDiv'>";
                    html += "<div class='queueList'>";
                    html += "<div id='nameFileDndArea' class='placeholder'>";
                    html += "<div class='nameFileFilePicker' onclick='setNumber(" + rowNum + ")'></div>";
                    html += "</div>";
                    html += "</div>";
                    html += "<div class='statusBar' style='display:none;'>";
                    html += "<div class='progress'>";
                    html += "<span class='text'>0%</span>";
                    html += "<span class='percentage'></span>";
                    html += "</div><div class='info'></div>";
                    html += "<div class='btns'>";
                    html += "<div id='datas" + rowNum + "-nameFileFilePicker2'></div><div class='uploadBtn'>开始上传</div>";
                    html += "</div>";
                    html += "</div>";
                    html += "</div>";
                    html += "</td>";
                    html += "<td></td>";
                    html += "<td>未提交</td>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].standardId' value='" + $("#standardId").val() + "'/>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].workflowId' value='" + $("#id").val() + "'/>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].rowId' value='" + rowNum + "'/>";
                    html += "</tr>";
                    $("#r_body").append(html);
                    i++;
                }
            }
            init();
        }

        function reduce() {
            $('input[name="selectedBox"]:checked').each(function () {
                // console.log("删除的值"+$(this).val());   rowNum
                $.getJSON("${ctx}/exam/examWorkflow/selfTermDelete",
                    {"standardId":'${standardId}',"workflowId":'${workflowId}',"rowNum":$(this).val()},
                    function (data) {
                        if (data.success ) {
                            if (data.result != null) {

                            } else {

                            }
                        }
                    });
                $("#r_" + $(this).val()).replaceWith("");
            });
        }
        function formSubmit(processType) {
            $("#processType").val(processType);
            $("#inputForm").submit();
        }
        function sendSubmit(processType, personType) {
            if(null == $("#personId").val()||'' == $("#personId").val()){
                jBox.alert("请选择推送人");
                return false;
            }
            $("#processType").val(processType);
            $("#personType").val(personType);
            $("#inputForm").submit();
        }
        function subFunction(standardId) {
            $("#standardId").val(standardId);
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate();

        });
        <%--console.log('${columnList}','${rowlist}');--%>
    </script>
<div>
<form:form id="inputForm" action="${ctx}/exam/examWorkflow/appaise/data/saveBeta" method="post" class="form-horizontal">
    <sys:message content="${message}"/>
    <input id="id" name="id" value="${workflowId}" type="hidden"/>
    <input id="status" name="status" value="${status}" type="hidden"/>
    <input id="standardId" name="standardId" value="${standardId}" type="hidden"/>
    <input id="selectedNumber" name="selectedNumber" value="${selectedNumber}" type="hidden"/>
    <input id="processType" name="processType" value="${processType}" type="hidden"/>
    <input id="personType" name="personType" value="${personType}" type="hidden"/>
    <input id="examType" name="examType" value="${examType}" type="hidden"/>
    <input id="num" name="num" value="0" type="hidden"/>
    <div>
        <span>业务得分：(<span id="businessScore">100</span>)</span>
        <span>重点得分：(<span id="importantScore">66</span>)</span>
        <span>公共部分得分：(<span id="publicScore">23</span>)</span>
    </div>
    <div id="modalSysSelf">
        <div class="modal-custom-content">
            <div class="">
                <table class="table table-striped table-bordered table-condensed" style="width: 100%;">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <%--<c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                            <th>${examStandardTemplateItemDefine.columnName}</th>
                        </c:forEach>--%>
                        <th>项目</th>
                        <th>类别</th>
                        <th>评分标准</th>
                        <th>加减分</th>
                        <th>备注</th>
                        <!--
                        <th>上级检查评分情况</th>
                        -->
                        <c:choose>
                            <c:when test="${'2'.equals(modleType)}">
                                <th>加分(实际)</th>
                            </c:when>
                            <c:otherwise>
                                <th>扣分(实际)</th>
                            </c:otherwise>
                        </c:choose>
                        <th>具体事项</th>
                        <th>附件</th>
                        <th>理由</th>
                        <th>状态</th>
                        <c:if test="${processType == 'appraiseExam'}">
                            <th>操作</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody id="r_body">
<%--                    <c:set var="num" value="0"></c:set>--%>
                    <c:set var="operStatus" value="-1"></c:set>
                    <c:set var="step" value="1"></c:set>
                    <c:set var="lineNum" value="2"></c:set>
                    <c:forEach items="${workflowDatasList}" varStatus="status"  var="workflowData">
                        <tr>
                            <td>${status.index+1}</td>
                            <td>${workflowData.s1}</td>
                            <td>${workflowData.s2}</td>
                            <td>${workflowData.s3}</td>
<%--                            <td>${workflowData.s4}</td>--%>
                            <td>${workflowData.s5}</td>
                            <td>${workflowData.s6}</td>
                        <td><input type='text' name='value' style='width:30px;' class='input-xlarge  number required' value='${row.value}'
                                   <c:if test="${'3'.equals(row.operationStatus)}">readonly="readonly"</c:if>
                        /></td>
                        <td><textarea rows='2' name='items' cols='10'>${workflowData.items}</textarea></td>
                        <td>
                           <input id="datas${num}-nameFile" name="path" type="hidden" value="${workflowData.path}" class="fileinput"/>
                            <ol id="datas${num}-nameFilePreview"></ol>
                            <div id="datas${num}-nameFileUploaderDiv" class="nameFileUploaderDiv">
                                <div class="queueList">
                                    <div id="nameFileDndArea" class='placeholder'>
                                        <div class="nameFileFilePicker" onclick="setNumber('${num}')"></div>
                                    </div>
                                </div>
                                <div class="statusBar" style="display:none;">
                                    <div class="progress">
                                        <span class="text">0%</span>
                                        <span class="percentage"></span>
                                    </div><div class="info"></div>
                                    <div class="btns">
                                        <div id="datas${num}-nameFileFilePicker2"></div><div class="uploadBtn">开始上传</div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>${row.detail}</td>
                        <td>${ fns:getDictLabel (row.operationStatus, "workflow_operation_status", "")}</td>
                        <c:if test="${processType == 'appraiseExam'}">
                            <td>
                                <a href="javascript:;">删除</a>
                                <a href="javascript:;">推送</a>
                            </td>
                        </c:if>
                        <input type='hidden' name='standardId'
                               value='${workflowData.standardId}'/>
                        <input type='hidden' name='workflowId'
                               value='${workflowData.workflowId}'/>
                        <input type='hidden' name='rowId'
                               value='${workflowData.rowId}'/>
                        <input type='hidden' name='datas[${num}].id'
                               value='${row.id}'/>
                        </tr>

                    </c:forEach>
                  <%--  <c:forEach items="${standardInfoList}" varStatus="standard">

                        <c:forEach items="${rowlist}" var="row"   varStatus="status">
                        <c:set var="operStatus" value="${row.operationStatus}"></c:set>
                        <c:if test="${'1'.equals(row.isSelected)}">
                        <c:set var="num" value='${row.row_num-lineNum}'></c:set>
                            <tr id="r_${row.row_num-lineNum}">
                                <td>
                                    <input class='i-checks' type='checkbox' name='selectedBox'
                                           value='${row.row_num-lineNum}'/>
                                </td>
                                <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                    <td>
                                            ${row.get(String.valueOf(examStandardTemplateItemDefine))}
                                    </td>
                                </c:forEach>
                                <!--
                                <td></td>-->
                                <td><input type='text' name='datas[${num}].value' style='width:30px;' class='input-xlarge  number required' value='${row.value}'
                                           <c:if test="${'3'.equals(row.operationStatus)}">readonly="readonly"</c:if>
                                /></td>
                                <td><textarea rows='2' name='datas[${num}].items' cols='10'>${row.items}</textarea></td>
                                <td>
                                    <input id="datas${num}-nameFile" name="datas[${num}].path" type="hidden" value="${row.path}" class="fileinput"/>
                                    <ol id="datas${num}-nameFilePreview"></ol>
                                    <div id="datas${num}-nameFileUploaderDiv" class="nameFileUploaderDiv">
                                        <div class="queueList">
                                            <div id="nameFileDndArea" class='placeholder'>
                                                <div class="nameFileFilePicker" onclick="setNumber('${num}')"></div>
                                            </div>
                                        </div>
                                        <div class="statusBar" style="display:none;">
                                            <div class="progress">
                                                <span class="text">0%</span>
                                                <span class="percentage"></span>
                                            </div><div class="info"></div>
                                            <div class="btns">
                                                <div id="datas${num}-nameFileFilePicker2"></div><div class="uploadBtn">开始上传</div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>${row.detail}</td>
                                <td>${ fns:getDictLabel (row.operationStatus, "workflow_operation_status", "")}</td>
                                <c:if test="${processType == 'appraiseExam'}">
                                    <td>
                                        <a href="javascript:;">删除</a>
                                        <a href="javascript:;">推送</a>
                                    </td>
                                </c:if>
                                <input type='hidden' name='datas[${num}].standardId'
                                       value='${standardId}'/>
                                <input type='hidden' name='datas[${num}].workflowId'
                                       value='${workflowId}'/>
                                &lt;%&ndash;rowId value 不需要减行号，最开始就没减&ndash;%&gt;
                                <input type='hidden' name='datas[${num}].rowId'
                                       value='${row.row_num}'/>
                                <input type='hidden' name='datas[${num}].id'
                                       value='${row.id}'/>
                            </tr>
&lt;%&ndash;                            <c:set var="num" value="${num+step}"></c:set>&ndash;%&gt;
                        </c:if>
                        </c:forEach>
                    </c:forEach>--%>

                    </tbody>
                </table>
                <c:if test="${history != 'true'}">
                    <div class="tb-btn tb-jia" style="text-align: right;margin-bottom: 20px;"><i class="icon-plus-sign" onclick="selectTerm()"></i></div>
                    <div class="tb-btn tb-jian" style="text-align: right;margin-bottom: 20px;"><i class="icon-minus-sign" onclick="reduce()"></i></div>
                </c:if>
                <div style="text-align: right;">
                    <c:choose>
                        <c:when test="${'2'.equals(nextStep)&& !'true'.equals(history)}" >
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('selfAppraise','appraiseExam')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'3'.equals(nextStep) && '4'.equals(nnextStep)&& !'true'.equals(history)}" >
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam','departSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'3'.equals(nextStep) &&'5'.equals(nnextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam','partBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'3'.equals(nextStep) &&'6'.equals(nnextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="确认并推送到" onclick="sendSubmit('appraiseExam','groupAdjust')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'3'.equals(nextStep) &&'7'.equals(nnextStep)&& !'true'.equals(history)}">
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam','mainBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'4'.equals(nextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('departSign','departSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'4'.equals(nextStep)&&'6'.equals(nnextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('departSign','groupAdjust')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'4'.equals(nextStep)&&'7'.equals(nnextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('departSign','mainBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'5'.equals(nextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('partBureausSign','partBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'6'.equals(nextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="确认并推送到" onclick="sendSubmit('groupAdjust','groupAdjust')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"  disabled="disabled"/>
                        </c:when>
                        <c:when test="${'7'.equals(nextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('mainBureausSign','mainBureausSign')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'8'.equals(nextStep)&& !'true'.equals(history)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if>  />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束本项工作考评" onclick="formSubmit('appraiseExam','')" <c:if test="${status==nextStep-1}"> disabled="true" </c:if> />
                        </c:when>
                        <c:otherwise>
                            <c:if test="${!'true'.equals(history)}">
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam')"/>
                            <sys:treeselect id="person" name="personId" value="" labelName="" labelValue=""
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                            </c:if>
                            <%--方便测试使用--%>
<%--                            <c:if test="${processType == 'appraiseExam'}">--%>
                                <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"  <c:if test="${'false'==isEdit}"> disabled="true" </c:if>  />&nbsp;&nbsp;&nbsp;&nbsp;
<%--                            </c:if>--%>
                        </c:otherwise>
                    </c:choose>
                </div>


            </div>
        </div>
    </div>
</form:form>
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
