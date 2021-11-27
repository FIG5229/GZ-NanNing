<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构添加-基本信息</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/info">预警列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">预警添加</a></li>
	</ul><br/>
	<form id="inputForm" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">预警名称:</label>
			<div class="controls">
				<input />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接受部门/人:</label>
			<div class="controls">
				<input />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重复:</label>
			<div class="controls">
				<select>
				  <option>每月</option>
				  <option>2</option>
				  <option>3</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间:</label>
			<div class="controls">
				<select>
				  <option>22</option>
				  <option>2</option>
				</select>
				天 
				<select>
				  <option>33</option>
				  <option>2</option>
				</select>
				时 
				<select>
				  <option>33</option>
				  <option>2</option>
				</select>
				分 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重复提醒:</label>
			<div class="controls">
				<select>
				  <option>10分钟</option>
				  <option>2</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
			<label style="width: auto;" class="control-label"><input type="checkbox"> 声音</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">铃声:</label>
			<div class="controls">
				<select>
				  <option>1</option>
				  <option>2</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
			<label style="width: auto;" class="control-label"><input type="checkbox"> 气泡</label>
			</div>
		</div>
		<div class="control-group displayinlineb">
			<label class="control-label">提示内容:</label>
			<div class="controls">
				<input />
			</div>
		</div>
		<div class="control-group displayinlineb">
			<label class="control-label">紧急程度:</label>
			<div class="controls">
				<select>
				  <option>1</option>
				  <option>2</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
			<label style="width: auto;" class="control-label"><input type="checkbox"> 弹窗</label>
			</div>
		</div>
		<div class="control-group displayinlineb">
			<label class="control-label">提示内容:</label>
			<div class="controls">
				<input />
			</div>
		</div>
		<div class="control-group displayinlineb">
			<label class="control-label">紧急程度:</label>
			<div class="controls">
				<select>
				  <option>1</option>
				  <option>2</option>
				</select>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
	</form>
</body>
</html>