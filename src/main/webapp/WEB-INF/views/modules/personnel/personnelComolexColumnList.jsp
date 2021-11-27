<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>复合查询字段列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkboxs = document.getElementsByName("myCheckBox");
			var checkColumnTextReady =  parent.parent.document.getElementById("checkColumnvalue").innerText;//选择字段
			var columnList = checkColumnTextReady.split("+");
			var nowIndexTableName = $("#tableNameInput").val();//当前页面表名
			if(columnList.length>0){
				for (var i = 0; i < checkboxs.length; i++) {
					//checkboxs[i].checked = true;
					//console.log(checkboxs[i])
					var checkValue = $("#tableNameInput").val()+"."+checkboxs[i].value;
					if(checkColumnTextReady.indexOf(checkValue)!=-1){
						checkboxs[i].checked = true;
					}
				}
			}

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		/*勾选所有的复选框*/
		specialChooseAll = function (obj,checkBoxsName) {
			if (obj.checked) {
				var checkboxs = document.getElementsByName(checkBoxsName);
				for (var i = 0; i < checkboxs.length; i++) {
					if(checkboxs[i].checked == false){
						checkboxs[i].click()
					}
					//checkboxs[i].checked = true;
				}
			} else {
				var checkboxs = document.getElementsByName(checkBoxsName);
				for (var i = 0; i < checkboxs.length; i++) {
					if(checkboxs[i].checked == true){
						checkboxs[i].click()
					}
					//checkboxs[i].checked = false;
				}
			}
		};

		/*选中状态*/
		function checkboxOnclick(checkbox,columnName){
			var checkTable =  parent.parent.document.getElementById("from_tableName_input");//表名
			var checkColumnName =  parent.parent.document.getElementById("checkColumntext");//选择字段名
			var checkColumn =  parent.parent.document.getElementById("checkColumnvalue");//选择字段
			//console.log(parent.parent.document.getElementById("checkColumnInput").innerText);
			var oldcheckTableText = checkTable.innerText;//已有表名
			var checkColumnNameText = checkColumnName.innerText;//已选字段名
			var checkColumnText = checkColumn.innerText;//已选字段
			var nowIndexTableName = $("#tableNameInput").val();//当前页面表名
			var nowIndexTableId = $("#tableIdInput").val();//当前页面表名
			//console.log(checkbox.className) //获取类名
			if ( checkbox.checked == true){
				//选中
				if(oldcheckTableText.indexOf($("#tableNameInput").val())== -1){
					checkTable.innerText = oldcheckTableText + nowIndexTableName+"+";
				}
				/*if(checkColumnNameText.indexOf(nowIndexTableId+"."+columnName)==-1){*/
					checkColumnName.innerText = checkColumnNameText+nowIndexTableId+"."+columnName+"+";
				/*}*/
				/*if(checkColumnText.indexOf(nowIndexTableName+"."+checkbox.value) == -1 ){*/
					checkColumn.innerText = checkColumnText +nowIndexTableName+"."+checkbox.value+"+";
				/*}*/
				//console.log($("#checkColumnInput").val());
			}else{
				//取消选中
				checkColumnName.innerText = checkColumnNameText.replace(nowIndexTableId+"."+columnName+"+","");
				checkColumn.innerText = checkColumnText.replace(nowIndexTableName+"."+checkbox.value+"+","");
				var list = oldcheckTableText.split("+");
				for(var i =0;i<list.length-1;i++){
					if(checkColumn.innerText.indexOf(list[i])==-1){
						checkTable.innerText = oldcheckTableText.replace(list[i]+"+","");
					}
				}
			}
		}

    </script>
</head>
<body>
	<input id="tableNameInput" value="${tableName}" hidden>
	<input id="tableIdInput" value="${tableId}" hidden>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='specialChooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>信息项名称</th>
				<th>信息项描述</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="tableColumn" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${tableColumn.name}"  onclick="checkboxOnclick(this,'${tableColumn.comment}')"/>
				</td>
				<td>
					${status.count}
				</td>
				<td>
						${tableColumn.name}
				</td>
				<td>
						${tableColumn.comment}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

</body>
</html>