<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<title>机构添加-基本信息</title>
		<meta name="decorator" content="default" />
	</head>
	<body>
		<ul class="nav nav-tabs">
			<li><a href="${ctx}/sys/user/info">机构列表</a></li>
			<li class="active"><a href="${ctx}/sys/user/modifyPwd">机构添加</a></li>
		</ul><br />
		<div class="btn-group btn-group-custom">
			<button class="btn active"><a href="${ctx}/sys/user/modifyPwd">基本信息</a></button>
			<button class="btn"><a href="${ctx}/sys/user/info">编制信息</a></button>
			<button class="btn"><a href="${ctx}/sys/user/info">职教信息</a></button>
		</div>
		<form id="inputForm" class="form-horizontal" style="width:1300px;">
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">上级单位:</label>
					<div class="controls">
						<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
						 title="公司" url="/sys/office/treeData?type=1" cssClass="required" />
					</div>
				</div>
				<div class="control-group form-fixw" style="width: 500px;">
					<label class="control-label">单位全称:</label>
					<div class="controls">
						<input style="width: 300px;" />
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">单位简称:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位隶属关系:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位性质类别:</label>
					<div class="controls">
						<select>
							<option>内设机构</option>
							<option>2</option>
						</select>
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">单位所在政区:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位级别:</label>
					<div class="controls">
						<select>
							<option>铁路公安处</option>
							<option>2</option>
						</select>
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位所在层级:</label>
					<div class="controls">
						<select>
							<option>铁路公安处</option>
							<option>2</option>
						</select>
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">单位所属部门与警钟:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位编码:</label>
					<div class="controls">
						<input/>
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位类别:</label>
					<div class="controls">
						<select>
							<option>11 城市街道</option>
							<option>2</option>
						</select>
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">单位负责人:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位成立批注时间:</label>
					<div class="controls">
						<input/>
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位成立批准文号:</label>
					<div class="controls">
						<input />
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">单位撤销批准时间:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位撤销批准文号:</label>
					<div class="controls">
						<input/>
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位撤销批准机关名称:</label>
					<div class="controls">
						<input />
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">注册资金:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位代码:</label>
					<div class="controls">
						<input/>
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">撤销标识:</label>
					<div class="controls">
						<input />
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">派出所类型:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">派出所管理户口数:</label>
					<div class="controls">
						<input/>
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">单位层级:</label>
					<div class="controls">
						<input />
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">虚单位:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">派出所人数:</label>
					<div class="controls">
						<input/>
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">公务员系统编码:</label>
					<div class="controls">
						<input />
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">法人单位标识:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">登记管理机关:</label>
					<div class="controls">
						<input/>
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">业务范围:</label>
					<div class="controls">
						<input />
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">经费来源:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label">机构更新时间:</label>
					<div class="controls">
						<input/>
					</div>
				</div>
			</div>
			
			<div class="form-row">
				<div class="control-group form-fixw">
					<label class="control-label">单位成立批准机关名称:</label>
					<div class="controls">
						<input />
					</div>
				</div>
				<div class="control-group form-fixw">
					<label class="control-label" style="color: #D0282E;">单位建立党组织情况:</label>
					<div class="controls">
						<select name="">
							<option>11 建立党委</option>
						</select>
					</div>
				</div>
			</div>
			
			<div class="form-actions">
				<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
			
		</form>
	</body>
</html>
