<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>七知档案管理</title>
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
		function addRow(list, idx, tpl, row) {
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list + idx).find("select").each(function () {
				$(this).val($(this).attr("data-value"));
			});
			$(list + idx).find("input[type='checkbox'], input[type='radio']").each(function () {
				var ss = $(this).attr("data-value").split(',');
				for (var i = 0; i < ss.length; i++) {
					if ($(this).val() == ss[i]) {
						$(this).attr("checked", "checked");
					}
				}
			});
		}

		function delRow(obj, prefix) {
			var id = $(prefix + "_id");
			var delFlag = $(prefix + "_delFlag");
			if (id.val() == "") {
				$(obj).parent().parent().remove();
			} else if (delFlag.val() == "0") {
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			} else if (delFlag.val() == "1") {
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}

		function setFamilyInfo(){
			// delRow(this, '#familyList{{idx}}')
			$.ajax({
				type:"post",
				url:"${ctx}/personnel/personnelFamily/getFamilyByIdNumber",
				data:{idNumber:$("#idNumber").val()},
				dataType:"json",
				success:function(data){
					if (data.success=true && data.result.length > 0) {
						data=data.result;
						for (var i = 0; i < data.length; i++) {
							// 政治面貌  民族
							addRow('#familyList', affairSevenKnowledgeChildRowIdx, affairSevenKnowledgeChildTpl, data[i]);
							affairSevenKnowledgeChildRowIdx = affairSevenKnowledgeChildRowIdx + 1;
						}

					}else {
						$.jBox.tip('没有查询到该人名相关信息');
					}
				}
			});
			return true;

		}
        //根据姓名自动查询相关信息
        function setUserInfo() {
            //清空
            $("#idNumber").val('');
            $.ajax({
                type:"post",
                url:"${ctx}/personnel/personnelBase/getPersonByName",
                data:{name:$("#name").val()},
                dataType:"json",
                success:function(data){
                    	////debugger;er;
                    	if (data.success==true ){


                    if( data.result.length==1){
                        //获取到身份证号后 查询家庭关系
                        // setFamilyInfo();
                        $("#idNumber").val(data.result[0].idNumber);
                        $("#policeNo").val(data.result[0].policeIdNumber);
                        $("#nation").val(data.result[0].nation);
                        // $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
                        $("#birthday").val(data.result[0].birthday);
                        $("#sex").val(data.result[0].sex);
                        $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
                    }else if( data.result.length>1){
                        var html = '<table id="contentTable" class="table table-striped table-bordered table-condensed">';
                        html += '<thead><tr><th></th><th>姓名</th><th>单位</th><th>身份证号</th><th>警号</th></tr></thead>';
                        html += '<tbody>';
                        for(var i=0; i< data.result.length; i++) {
                            html += '<tr><td><input type="radio" name="selected" value="'+i+'"></td>';
                            html += '<td>'+data.result[i].name+'</td>';
                            html += '<td>'+data.result[i].workunitName+'</td>';
                            html += '<td>'+data.result[i].idNumber+'</td>';
                            html += '<td>'+data.result[i].policeIdNumber+'</td>';
                            html += '</tr>';
                        }

                        html +=	'</tbody>';
                        html +=	'</table>';
                        var submit = function (v, h, f) {
                            //获取到身份证号后 查询家庭关系
                            // setFamilyInfo();
                            $("#idNumber").val(data.result[0].idNumber);
                            $("#policeNo").val(data.result[0].policeIdNumber);
                            $("#nation").val(data.result[0].nation);
                            // $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#nation").find("option:selected").text());
                            $("#birthday").val(data.result[0].birthday);
                            $("#sex").val(data.result[0].sex);
                            $('#sex').siblings('.select2-container').find('.select2-chosen').text($("#sex").find("option:selected").text());
                            return true;
                        };

                        top.$.jBox(html, { title: "输入",width: 600, height: 300, submit: submit });
                    }else {
                        $.jBox.tip('没有查询到该人名相关信息');
                    }
						}
				}
            });
            // var idNumber = $("#idNumber").val();
            // if (idNumber != null && idNumber != ''){
                // setFamilyInfo();
            // }
        }
	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairSevenKnowledge/">七知档案列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairSevenKnowledge/form?id=${affairSevenKnowledge.id}">七知档案<shiro:hasPermission name="affair:affairSevenKnowledge:edit">${not empty affairSevenKnowledge.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="affair:affairSevenKnowledge:edit">查看</shiro:lacksPermission></a></li>
	</ul>--%>
	<br/>
	<form:form id="inputForm" modelAttribute="affairSevenKnowledge" action="${ctx}/affair/affairSevenKnowledge/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge required"
                            onkeydown="if(event.keyCode==13){setUserInfo();return false;}"/>
				<span class="help-inline"><font color="red">*输入完后请务必按回车自动查询相关信息</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<%--家庭信息查看时显示--%>
				<form:input path="idNumber" htmlEscape="true" class="input-xlarge required"/>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- 9.2问题反馈 部分信息自动关联人员信息  不在保存--%>
		<%--<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
				<form:select path="nation" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSevenKnowledge.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文化程度：</label>
			<div class="controls">
				<form:input path="degreeEducation" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:select path="politicsFace" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('zzmm')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家庭地址：</label>
			<div class="controls">
				<form:input path="homeAddress" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">住房面积：</label>
			<div class="controls">
				<form:input path="houseArea" htmlEscape="false" class="input-xlarge required" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系方式：</label>
			<div class="controls">
				<form:input path="contactTel" htmlEscape="false" class="input-xlarge required" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">参加工作时间：</label>
			<div class="controls">
				<input name="workTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSevenKnowledge.workTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">从警时间：</label>
			<div class="controls">
				<input name="fromPoliceTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSevenKnowledge.fromPoliceTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警衔：</label>
			<div class="controls">
				<form:input path="policeRank" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">婚姻状态：</label>
			<div class="controls">
				<form:select path="maritalStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_marital_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">健康状况：</label>
			<div class="controls">
				<form:input path="health" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">病史：</label>
			<div class="controls">
				<form:input path="medicalHistory" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">心里疾病：</label>
			<div class="controls">
				<form:input path="mentalIllness" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">个人简历：</label>
			<div class="controls">
				<form:textarea path="curriculumVitae" htmlEscape="false" class="input-xlarge " rows="6"/>
			</div>
		</div>--%>
		<%--家庭关系--%>
		<%--此部分关联民警家庭信息采集，无需录入，也不用修改--%>
		<%--<div class="control-group">
			<label class="control-label">家庭关系：</label>
			<div class="controls">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
						<th class="hide"></th>
						<th>姓名</th>
						<th>与本人关系</th>
						<th>出生日期</th>
						<th>工作单位</th>
						<th>职务</th>
						<th>民族</th>
						<th>学历</th>
						<th>政治面貌</th>
						<th>职级</th>
						<th>状态</th>
						<shiro:hasPermission name="affair:affairSevenKnowledge:edit">
							<th width="10">&nbsp;</th>
						</shiro:hasPermission>
					</tr>
					</thead>
					<tbody id="familyList">
					</tbody>
					&lt;%&ndash;<shiro:hasPermission name="affair:affairSevenKnowledge:edit">
						<tfoot>
						<tr>
							<td colspan="46">
								<a href="javascript:;" onclick="addRow('#familyList', affairSevenKnowledgeChildRowIdx,affairSevenKnowledgeChildTpl);
								affairSevenKnowledgeChildRowIdx = affairSevenKnowledgeChildRowIdx + 1;"class="btn">新增</a>
							</td>
						</tr>
						</tfoot>
					</shiro:hasPermission>&ndash;%&gt;
				</table>
				<script type="text/template" id="affairSevenKnowledgeChildTpl">//
				<tr id="familyList{{idx}}">
					<td class="hide">
						<input id="familyList{{idx}}_id" name="familyList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="familyList{{idx}}_delFlag" name="familyList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					<td>
						<input id="familyList{{idx}}title" name="familyList[{{idx}}].name" type="text" value="{{row.name}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_relationship" name="familyList[{{idx}}].relationship" type="text" value="{{row.relationship}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_birthday" name="familyList[{{idx}}].birthday" type="text" value="{{row.birthday}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_unitJob" name="familyList[{{idx}}].unitNameJob" type="text" value="{{row.unitNameJob}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_identityJob" name="familyList[{{idx}}].identityJob" type="text" value="{{row.identityJob}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_nation" name="familyList[{{idx}}].nation" type="text" value="{{row.nation}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_education" name="familyList[{{idx}}].education" type="text" value="{{row.education}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_politicsFace" name="familyList[{{idx}}].politicsFace" type="text" value="{{row.politicsFace}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_jobLevel" name="familyList[{{idx}}].jobLevel" type="text" value="{{row.jobLevel}}" class="input-small "/>
					</td>
					<td>
						<input id="familyList{{idx}}_status" name="familyList[{{idx}}].status" type="text" value="{{row.status}}" class="input-small "/>
					</td>
					<shiro:hasPermission name="affair:affairSevenKnowledge:edit"><td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#familyList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td></shiro:hasPermission>
				</tr>
				</script>
				<script type="text/javascript">
					var affairSevenKnowledgeChildRowIdx = 0,
							affairSevenKnowledgeChildTpl = $("#affairSevenKnowledgeChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
					$(document).ready(function () {
						var data = ${fns:toJson(affairSevenKnowledge.familyList)};
						for (var i = 0; i < data.length; i++) {
							addRow('#familyList', affairSevenKnowledgeChildRowIdx, affairSevenKnowledgeChildTpl, data[i]);
							affairSevenKnowledgeChildRowIdx = affairSevenKnowledgeChildRowIdx + 1;
						}
					});
				</script>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">收入情况：</label>
			<div class="controls">
				<form:input path="income" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重点支出：</label>
			<div class="controls">
				<form:input path="keyExpenditure" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">直系亲属经商情况：</label>
			<div class="controls">
				<form:input path="businessSituation" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有无其他房产（面积）：</label>
			<div class="controls">
				<form:input path="otherHouse" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有无债务纠纷：</label>
			<div class="controls">
				<form:input path="debtDispute" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性格：</label>
			<div class="controls">
				<form:input path="character" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特长：</label>
			<div class="controls">
				<form:input path="specialty" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业余爱好：</label>
			<div class="controls">
				<form:input path="hobbies" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
		<c:choose>
			<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
				<div class="control-group">
					<label class="control-label">工作单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairSevenKnowledge.unitId}" labelName="unit" labelValue="${affairSevenKnowledge.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"  isAll="true"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<label class="control-label">工作单位：</label>
					<div class="controls">
						<sys:treeselect id="unit" name="unitId" value="${affairSevenKnowledge.unitId}" labelName="unit" labelValue="${affairSevenKnowledge.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<%--<div class="control-group">
			<label class="control-label">工作单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairSevenKnowledge.unitId}" labelName="unit" labelValue="${affairSevenKnowledge.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">工作岗位：</label>
			<div class="controls">
				<form:input path="workJob" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">擅长业务：</label>
			<div class="controls">
				<form:input path="goodBusiness" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务不足：</label>
			<div class="controls">
				<form:input path="insufficientBusiness" htmlEscape="false" class="input-xlarge  required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否炒股（基金）：</label>
			<div class="controls">
				<form:select path="stockSpeculation" class="input-xlarge required ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否常买彩票：</label>
			<div class="controls">
				<form:select path="buyLottery" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否习练功法：</label>
			<div class="controls">
				<form:select path="practiceSkills" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家庭和睦情况：</label>
			<div class="controls">
				<form:input path="familyHarmony" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邻里关系：</label>
			<div class="controls">
				<form:input path="neighborhoodRelations" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同事关系：</label>
			<div class="controls">
				<form:input path="colleagueRelations" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	<%--	<div class="control-group">
			<label class="control-label">近期奖惩状况：</label>
			<div class="controls">
				<form:textarea path="rewardsPunishments" htmlEscape="false" class="input-xlarge " rows="4"/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">获持枪资格时间：</label>
			<div class="controls">
				<input name="gunHoldTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSevenKnowledge.gunHoldTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">岗位是否配枪：</label>
			<div class="controls">
				<form:select path="hasGun" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配枪制度落实情况：</label>
			<div class="controls">
				<form:input path="gunSystem" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否喝酒：</label>
			<div class="controls">
				<form:select path="drink" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否贪杯：</label>
			<div class="controls">
				<form:select path="greedyCup" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有无酒后失控情况：</label>
			<div class="controls">
				<form:input path="outControlDrink" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驾驶证类型：</label>
			<div class="controls">
				<form:input path="driverLicenseType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驾照获取时间：</label>
			<div class="controls">
				<input name="driverTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSevenKnowledge.driverTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">汽车驾龄：</label>
			<div class="controls">
				<form:input path="driverAgeCar" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">摩托车驾龄：</label>
			<div class="controls">
				<form:input path="driverAgeMotorcycle" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">获准驾驶警车时间：</label>
			<div class="controls">
				<input name="policeCarTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSevenKnowledge.policeCarTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">获准长途驾驶警车时间：</label>
			<div class="controls">
				<input name="longPoliceCarTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSevenKnowledge.longPoliceCarTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">安全行车记录：</label>
			<div class="controls">
				<form:input path="safeDriver" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否爱打麻将：</label>
			<div class="controls">
				<form:select path="mahjong" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有赌博迹象：</label>
			<div class="controls">
				<form:input path="gambling" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赌博其他反应：</label>
			<div class="controls">
				<form:input path="betOther" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家庭重大变故：</label>
			<div class="controls">
				<form:input path="familyMisfortune" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家属有无参与非法组织：</label>
			<div class="controls">
				<form:input path="illegalOrganization" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有无社区不良交往：</label>
			<div class="controls">
				<form:input path="badAssociation" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否沉迷网络：</label>
			<div class="controls">
				<form:input path="addictedInternet" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否经常出入高消费场所：</label>
			<div class="controls">
				<form:input path="luxuryPlaces" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有无参股经商反应：</label>
			<div class="controls">
				<form:input path="participationBusiness" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">以权谋私不良反映：</label>
			<div class="controls">
				<form:input path="crruption" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他不良反应：</label>
			<div class="controls">
				<form:input path="otherAdverseReactions" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有无脱岗离岗记录：</label>
			<div class="controls">
				<form:input path="offDuty" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有无群众投诉：</label>
			<div class="controls">
				<form:input path="massComplaints" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级督察训导记录：</label>
			<div class="controls">
				<form:input path="disciplining" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">列为重点帮教对象时间：</label>
			<div class="controls">
				<input name="helpEducateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${affairSevenKnowledge.helpEducateTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">包保所领导：</label>
			<div class="controls">
				<form:input path="suoLeader" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">包保处领导：</label>
			<div class="controls">
				<form:input path="chuLeader" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">综合评定：</label>
			<div class="controls">
				<form:select path="evaluate" htmlEscape="false" class="input-xlarge required">
					<form:option value="" label=""/>
					<%--谈心 家访 七只档案共同使用此字典--%>
					<form:options items="${fns:getDictList('seven_evaluate_rating')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间：</label>
			<div class="controls">
				<input name="time" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="<fmt:formatDate value="${affairSevenKnowledge.time}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairSevenKnowledge:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close()"/>
		</div>
	</form:form>
	<script>
		if ("success" == "${saveResult}"){
			parent.$.jBox.tip("保存成功");
			parent.$.jBox.close();
		}
	</script>
</body>
</html>