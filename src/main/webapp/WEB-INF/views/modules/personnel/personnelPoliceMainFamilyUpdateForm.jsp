<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>民警家庭管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
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

        function addRow(list, idx, tpl, row){
            $(list).append(Mustache.render(tpl, {
                idx: idx, delBtn: true, row: row
            }));
            $(list+idx).find("select").each(function(){
                $(this).val($(this).attr("data-value"));
            });
            $(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
                var ss = $(this).attr("data-value").split(',');
                for (var i=0; i<ss.length; i++){
                    if($(this).val() == ss[i]){
                        $(this).attr("checked","checked");
                    }
                }
            });
        }
        function delRow(obj, prefix){
            var id = $(prefix+"_id");
            var delFlag = $(prefix+"_delFlag");
            if (id.val() == ""){
                $(obj).parent().parent().remove();
            }else if(delFlag.val() == "0"){
                delFlag.val("1");
                $(obj).html("&divide;").attr("title", "撤销删除");
                $(obj).parent().parent().addClass("error");
            }else if(delFlag.val() == "1"){
                delFlag.val("0");
                $(obj).html("&times;").attr("title", "删除");
                $(obj).parent().parent().removeClass("error");
            }
        }
    </script>
</head>
<body>
<br/>
<form:form id="inputForm" modelAttribute="personnelPoliceMainFamily" action="${ctx}/personnel/personnelPoliceMainFamily/saveInfo?hasMarried=${hasMarried}&hasBrother=${hasBrother}&hasChild=${hasChild}&hasChildInLow${hasChildInLow}" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <input type="hidden" id="hasMarried" name="hasMarried" value="${hasMarried}"/>
    <input type="hidden" id="hasBrother" name="hasBrother" value="${hasBrother}"/>
    <input type="hidden" id="hasChild" name="hasChild" value="${hasChild}"/>
    <input type="hidden" id="hasChildInLow" name="hasChildInLow" value="${hasChildInLow}"/>
    <div class="control-group">
        <label class="control-label">姓名：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">单位：</label>
        <div class="controls">
            <sys:treeselect id="unit" name="unitId" value="${personnelPoliceMainFamily.unitId}" labelName="unit" labelValue="${personnelPoliceMainFamily.unit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">身份证号：</label>
        <div class="controls">
            <form:input path="idNumber" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">警号：</label>
        <div class="controls">
            <form:input path="policeNum" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group" id="hasMarried">
        <label class="control-label">是否已婚:</label>
        <div class="controls">
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

        </div>
    </div>
    <div class="control-group" id="hasBrother">
        <label class="control-label">是否有兄弟姐妹:</label>
        <div class="controls">
            <c:choose>
                <c:when test="${personnelPoliceMainFamily.hasBrother == '1'}">
                    <input id="hasBrother"  name="hasBrother" value="1" type="radio" class="input-xlarge " checked="checked" disabled/>是
                    <input id="hasBrother"  name="hasBrother" value="0" type="radio" class="input-xlarge " disabled/>否
                </c:when>
                <c:otherwise>
                    <input id="hasBrother"  name="hasBrother" value="1" type="radio" class="input-xlarge "  disabled/>是
                    <input id="hasBrother"  name="hasBrother" value="0" type="radio" class="input-xlarge " checked="checked" disabled/>否
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="control-group" id="hasChild">
        <label class="control-label">是否有子女:</label>
        <div class="controls">
            <c:choose>
                <c:when test="${personnelPoliceMainFamily.hasChild == '1'}">
                    <input id="hasChild"  name="hasChild" value="1" type="radio" class="input-xlarge " checked="checked" disabled/>是
                    <input id="hasChild"  name="hasChild" value="0" type="radio" class="input-xlarge " disabled/>否
                </c:when>
                <c:otherwise>
                    <input id="hasChild"  name="hasChild" value="1" type="radio" class="input-xlarge " disabled/>是
                    <input id="hasChild"  name="hasChild" value="0" type="radio" class="input-xlarge " checked="checked" disabled/>否
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">子女是否有配偶:</label>
        <div class="controls">
            <c:choose>
                <c:when test="${personnelPoliceMainFamily.hasChildInLow == '1'}">
                    <input id="hasChildInLow"  name="hasChildInLow" value="1" type="radio" class="input-xlarge " checked="checked" disabled/>是
                    <input id="hasChildInLow"  name="hasChildInLow" value="0" type="radio" class="input-xlarge " disabled/>否
                </c:when>
                <c:otherwise>
                    <input id="hasChildInLow"  name="hasChildInLow" value="1" type="radio" class="input-xlarge " disabled />是
                    <input id="hasChildInLow"  name="hasChildInLow" value="0" type="radio" class="input-xlarge " checked="checked" disabled/>否
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="controls">
        <table id="contentTable" class="table table-striped table-bordered table-condensed">
            <thead>
            <tr>
                <th class="hide"></th>
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
            </thead>
            <tbody id="personnelPoliceFamilyInfoList">
            </tbody>
            <shiro:hasPermission name="personnel:personnelPoliceMainFamily:edit"><tfoot>
            <tr>
                <td colspan="10"><a href="javascript:"
                                    onclick="addRow('#personnelPoliceFamilyInfoList', personnelPoliceFamilyInfoRowIdx, personnelPoliceFamilyInfoTpl);personnelPoliceFamilyInfoRowIdx = personnelPoliceFamilyInfoRowIdx + 1;"
                                    class="btn">新增</a></td>
            </tr>
            </tfoot></shiro:hasPermission>
        </table>
        <script type="text/template" id="personnelPoliceFamilyInfoTpl">//<!--
						<tr id="personnelPoliceFamilyInfoList{{idx}}">
							<td class="hide">
								<input id="personnelPoliceFamilyInfoList{{idx}}_id" name="personnelPoliceFamilyInfoList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="personnelPoliceFamilyInfoList{{idx}}_delFlag" name="personnelPoliceFamilyInfoList[{{idx}}].delFlag" type="hidden" value="0"/>
								<input id="personnelPoliceFamilyInfoList{{idx}}_pfId" name="personnelPoliceFamilyInfoList[{{idx}}].pfId" type="hidden" value="{{row.pfId}}"/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_name" name="personnelPoliceFamilyInfoList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_idNumber" name="personnelPoliceFamilyInfoList[{{idx}}].idNumber" type="text" value="{{row.idNumber}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_relationship" name="personnelPoliceFamilyInfoList[{{idx}}].relationship" type="text" value="{{row.relationship}}" class="input-small "/>
							</td>
							<td>
								<select id="personnelPoliceFamilyInfoList{{idx}}_sex" name="personnelPoliceFamilyInfoList[{{idx}}].sex" data-value="{{row.sex}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('sex')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_birthday" name="personnelPoliceFamilyInfoList[{{idx}}].birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.birthday}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</td>
							<td>
								<select id="personnelPoliceFamilyInfoList{{idx}}_politicalStatus" name="personnelPoliceFamilyInfoList[{{idx}}].politicalStatus" data-value="{{row.politicalStatus}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('political_status')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_statusQuo" name="personnelPoliceFamilyInfoList[{{idx}}].statusQuo" type="text" value="{{row.statusQuo}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_job" name="personnelPoliceFamilyInfoList[{{idx}}].job" type="text" value="{{row.job}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_steer" name="personnelPoliceFamilyInfoList[{{idx}}].steer" type="text" value="{{row.steer}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_nationality" name="personnelPoliceFamilyInfoList[{{idx}}].nationality" type="text" value="{{row.nationality}}" class="input-small "/>
							</td>
							<td>
								<select id="personnelPoliceFamilyInfoList{{idx}}_nation" name="personnelPoliceFamilyInfoList[{{idx}}].nation" data-value="{{row.nation}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('nation')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_education" name="personnelPoliceFamilyInfoList[{{idx}}].education" type="text" value="{{row.education}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_identity" name="personnelPoliceFamilyInfoList[{{idx}}].identity" type="text" value="{{row.identity}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_position" name="personnelPoliceFamilyInfoList[{{idx}}].position" type="text" value="{{row.position}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_jobLevel" name="personnelPoliceFamilyInfoList[{{idx}}].jobLevel" type="text" value="{{row.jobLevel}}" class="input-small "/>
							</td>
								<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_contact" name="personnelPoliceFamilyInfoList[{{idx}}].contact" type="text" value="{{row.contact}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_address" name="personnelPoliceFamilyInfoList[{{idx}}].address" type="text" value="{{row.address}}" class="input-small "/>
							</td>
							<td>
								<input id="personnelPoliceFamilyInfoList{{idx}}_remark" name="personnelPoliceFamilyInfoList[{{idx}}].remark" type="text" value="{{row.remark}}" class="input-small "/>
							</td>
							<%--<td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#personnelPoliceFamilyInfoList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td>--%>
						</tr>//-->
        </script>
        <script type="text/javascript">
            var personnelPoliceFamilyInfoRowIdx = 0, personnelPoliceFamilyInfoTpl = $("#personnelPoliceFamilyInfoTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
            function initData(data){
                for (var i=0; i<data.length; i++){
                    addRow('#personnelPoliceFamilyInfoList', personnelPoliceFamilyInfoRowIdx, personnelPoliceFamilyInfoTpl, data[i]);
                    personnelPoliceFamilyInfoRowIdx = personnelPoliceFamilyInfoRowIdx + 1;
                }
            }
            $(document).ready(function() {
                var data = ${fns:toJson(personnelPoliceMainFamily.personnelPoliceFamilyInfoList)};
                console.log(data);
                initData(data);
            });
        </script>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="personnel:personnelPoliceMainFamily:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick=" parent.$.jBox.close();"/>
    </div>
</form:form>
<script>
    if ("success" == "${saveResult}"){
        parent.$.jBox.close();
    }
</script>
</body>
</html>