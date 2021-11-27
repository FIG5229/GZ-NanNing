<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预警信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function handle(idStr) {
			//查看详情
            if(idStr != null && idStr !='' && idStr != undefined){
                var url = "iframe:${ctx}/warn/warning/handel?idStr="+idStr;
                top.$.jBox.open(url, "预警消息处理",1000,450,{
                    buttons:{"关闭":true},
                    loaded:function(h){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
						parent.$.jBox.close();
					}
                });
            }

		}
		function noRemind(idStr) {
			//不再提醒
			if(idStr != null && idStr !='' && idStr != undefined){
				$.ajax({
					url:"${ctx}/warn/warning/noRemindByIds",
					type:"post",
					data:{idStr:idStr},
					dataType:"json",
					success:function(data){
						if(data.success == true){
							$.jBox.tip(data.message);
						}else{
							$.jBox.tip(data.message);
						}
					},
					error:function(d){
						$.jBox.tip('发生错误，提交失败');
					}
				});
				//parent.$.jBox.close();
			}
		}

	</script>
	<%--<style>
		.handle {
			background: rgba(208,40,46,1);
			border: 1px solid rgba(208,40,46,1);
			color: #fff;
			width: 68px;
			margin-top: 36px;
			margin-left: 310px;
		}
	</style>--%>
</head>
<body>
<div style="margin-top: 20px;">
    <%--消息为0条时不提醒--%>
	<span style="font-size: larger;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温馨提示：您当前有<span style="color: red">${num}</span>条预警通知，请尽快处理！</span>
		<div class="form-actions" style="text-align: right;">
			<%--<input  class="btn" type="button" value="不再提醒" onclick="noRemind('${idStr}')"/>&nbsp;--%>
			<input  class="btn btn-primary" type="button" value="查看详情" onclick="handle('${idStr}')"/>
		</div>
</div>
</body>
</html>