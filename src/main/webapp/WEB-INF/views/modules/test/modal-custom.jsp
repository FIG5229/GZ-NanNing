<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<!-- 人员信息集 -->
<div id="modalPeo">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-title">教育培训（进修）信息集管理</div>
			<div class="modal-custom-bar">
				<div class="modal-custom-btn red">编辑</div>
				<div class="modal-custom-btn">删除</div>
				<div class="modal-custom-btn">导入</div>
			</div>
			<div class="modal-custom-content">
				<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><input type="checkbox" onclick="selectAll(this.checked, 'peocheck')" />全选</th>
							<th>序号</th>
							<th>培训班名称</th>
							<th>培训类型</th>
							<th>培新完成情况</th>
							<th>培训时所在单位及职务</th>
							<th>培训机构名称</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							<td>1</td>
							<td>治安工作</td>
							<td>治安工作</td>
							<td>治安工作</td>
							<td>治安工作</td>
							<td>治安工作</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-custom-page">分页组件</div>
		</div>
	</div>
</div>


<!-- 人员信息导航 -->
<div id="modalNav"class="modal-custom-wrap">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-title">人员详细信息详情-XXX</div>
			<div class="modal-custom-content">
				<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
					<tbody>
						<tr>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
						</tr>
						<tr>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
							<td><a href="#">人员基本情况信息</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<!-- 详细信息1 -->
<div id="modalInfo1" class="modal-custom-wrap">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-title">详细信息详细信息详细信息详细信息1</div>
			<div class="modal-custom-content">
				<div class="modal-custom-info1">
					<p style="text-indent: 0;">发布部门：XXXXXXXXX</p>
					<p>
						上午11时40分许，晋衔仪式在庄严的国歌声中开始。中央军委副主席许其亮宣读了中央军委主席习近平签署的晋升上将军衔警衔命令。上午11时40分许，晋衔仪式在庄严的国歌声中开始。中央军委副主席许其亮宣读了中央军委主席习近平签署的晋升上将军衔警衔命令。中央军委副主席张又侠主持晋衔仪式</p>
					<p>上午11时40分许，晋衔仪式在庄严的国歌声中开始。中央军委副主席许其亮宣读了中央军委主席习近平签署的晋升上将军衔警衔</p>
					<p> 晋升上将军衔警衔的10位军官警官军容严整、精神抖擞来到主席台前。习近平向他们颁发命令状，并同他们亲切握手，表示祝贺。佩戴了上将军衔警衔肩章的10位军官警官向习近平敬礼，向参加仪式的全体同志敬礼，全场响起热烈的掌声。</p>
				</div>
				<div class="modal-custom-info1-file">
					<div class="modal-custom-info1-file-l">附件:</div>
					<div class="modal-custom-info1-file-r">
						<div class="modal-custom-info1-file-item">
							<span>XXXXXXXXXXXXXXXXXXXXXXXXX.pdf</span>
							<a href="#">在线预览</a>
							<a href="#">下载</a>
						</div>
						<div class="modal-custom-info1-file-item">
							<span>XXXXXXXXX.pdf</span>
							<a href="#">在线预览</a>
							<a href="#">下载</a>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-custom-info1-bottom">
				<div class="modal-custom-info1-btn red">打印</div>
			</div>
		</div>
	</div>
</div>


<!-- 详细信息2 -->
<div id="modalInfo2" class="modal-custom-wrap">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-title">详细信息详细信息详细信息详细信息2</div>
			<div class="modal-custom-content">
				<div class="modal-custom-info2">
					<div class="modal-custom-info2-col modal-custom-info2-col1">
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">xxxx</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span class="modal-custom-info2-value">汉</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生日期：</span><span class="modal-custom-info2-value">1991-01-01</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">转为正式党员日期：</span><span class="modal-custom-info2-value">1991-01-01</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加工作日期：</span><span class="modal-custom-info2-value">1991-01-01</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在党支部：</span><span class="modal-custom-info2-value">中共柳州铁路公安处兴安站派出所 支部委员会</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">家庭住址：</span><span class="modal-custom-info2-value">南宁市佛子岭路23号宁铁馨苑21栋 2单元11号</span></div>
					</div>
					<div class="modal-custom-info2-col modal-custom-info2-col2">
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">450111199100000000</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">籍贯：</span><span class="modal-custom-info2-value">XXXXXXXXXXXXXX</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span class="modal-custom-info2-value">大学</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位：</span><span class="modal-custom-info2-value">xxxxxxxxxxxx</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系电话：</span><span class="modal-custom-info2-value">xxxxxxxxxxx</span></div>
					</div>
					<div class="modal-custom-info2-col modal-custom-info2-col3">
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">男</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">婚姻状况：</span><span class="modal-custom-info2-value">未婚</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员类别：</span><span class="modal-custom-info2-value">预备党员</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否台湾省籍：</span><span class="modal-custom-info2-value">否</span></div>
						<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">固定电话：</span><span class="modal-custom-info2-value">xxxxxxxxxxxx</span></div>
					</div>
				</div>
			</div>	
			<div class="modal-custom-info1-bottom">
				<div class="modal-custom-info1-btn red">打印</div>
			</div>
		</div>
	</div>
</div>

<!-- 绩效考评-系统自评 -->
<div id="modalSysSelf" class="modal-custom-wrap">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-step">
				<div class="step-start active">开始</div>
				<div class="modal-step-col active">系统自评</div>
				<div class="modal-step-col">系统初步考核</div>
				<div class="modal-step-col">系统公示</div>
				<div class="modal-step-col">部门负责人签字</div>
				<div class="modal-step-col">分管局领导签字</div>
				<div class="modal-step-col">绩效考评领导小组复核及调整</div>
				<div class="modal-step-col">局主管领导最终审签</div>
				<div class="modal-step-col">最终结果公示</div>
				<div class="step-start step-end">结束</div>
			</div>
			<div class="modal-custom-bar">
				<div class="modal-custom-btn red">部门业务扣分</div>
				<div class="modal-custom-btn">部门公共扣分</div>
				<div class="modal-custom-btn">部门公共加分</div>
			</div>
			<div class="modal-custom-content">
				<div class="modal-custom-tb-l">
					<table class="table table-striped table-bordered table-condensed" style="width: 100%;">
						<thead>
							<tr>
								<th>分类</th>
								<th>序号</th>
								<th>考评点</th>
								<th>考点类型</th>
								<th>分值/扣奖</th>
								<th>选择</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>治安工作</td>
								<td>1</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>治安工作</td>
								<td>2</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>3</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>4</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>5</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>6</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>7</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
							<tr>
								<td>安检工作</td>
								<td>8</td>
								<td>xxxxxxxxxxxxxxxx</td>
								<td>扣分</td>
								<td>10</td>
								<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-custom-tb-c">
					<div class="tb-btn tb-jia"><i class="icon-long-arrow-right"></i></div>
					<div class="tb-btn tb-jian"><i class="icon-long-arrow-left"></i></div>
				</div>
				<div class="modal-custom-tb-r">
					<table class="table table-striped table-bordered table-condensed" style="width: 100%;">
						<thead>
							<tr>
								<th>分类</th>
								<th>序号</th>
								<th>上级检查评分情况</th>
								<th>考评点</th>
								<th>考点类型</th>
								<th>分值/扣奖</th>
								<th>理由</th>
								<th>附件</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>治安工作</td>
								<td>1</td>
								<td>xxxxxxxxxx</td>
								<td>xxxxxxxxxxx</td>
								<td>扣分</td>
								<td><input style="width: 40px;height: 30px;" type="text"></td>
								<td><textarea rows="" cols=""></textarea></td>
								<td>
									<p>xxx.pdf</p>
									<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
								</td>
							</tr>
							<tr>
								<td>治安工作</td>
								<td>2</td>
								<td>xxxxxxxxxx</td>
								<td>xxxxxxxxxxx</td>
								<td>扣分</td>
								<td><input style="width: 40px;height: 30px;" type="text"></td>
								<td><textarea rows="" cols=""></textarea></td>
								<td>
									<p>xxx.pdf</p>
									<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
								</td>
							</tr>
							<tr>
								<td>治安工作</td>
								<td>3</td>
								<td>xxxxxxxxxx</td>
								<td>xxxxxxxxxxx</td>
								<td>扣分</td>
								<td><input style="width: 40px;height: 30px;" type="text"></td>
								<td><textarea rows="" cols=""></textarea></td>
								<td>
									<p>xxx.pdf</p>
									<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
								</td>
							</tr>
							<tr>
								<td>治安工作</td>
								<td>4</td>
								<td>xxxxxxxxxx</td>
								<td>xxxxxxxxxxx</td>
								<td>扣分</td>
								<td><input style="width: 40px;height: 30px;" type="text"></td>
								<td><textarea rows="" cols=""></textarea></td>
								<td>
									<p>xxx.pdf</p>
									<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
								</td>
							</tr>
						</tbody>
					</table>
					<div style="text-align: right;">填报人：<input type="text"></div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 绩效考评-系统初步考核 -->
<div id="modalSysFirst" class="modal-custom-wrap">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-step">
				<div class="step-start active">开始</div>
				<div class="modal-step-col active">系统自评</div>
				<div class="modal-step-col active">系统初步考核</div>
				<div class="modal-step-col">系统公示</div>
				<div class="modal-step-col">部门负责人签字</div>
				<div class="modal-step-col">分管局领导签字</div>
				<div class="modal-step-col">绩效考评领导小组复核及调整</div>
				<div class="modal-step-col">局主管领导最终审签</div>
				<div class="modal-step-col">最终结果全局公示</div>
				<div class="step-start step-end">结束</div>
			</div>
			<div class="modal-custom-content">
				<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>单位名称</th>
							<th>自评状态</th>
							<th>系统初核</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="color: #2429FF;">南宁公安处</td>
							<td style="font-size: 20px;">
								<i style="color: #6FAD47;" class="icon-check"></i>
								<i style="color: #D0282E;" class="icon-remove"></i>
								<i style="color: #D0282E;" class="icon-lock"></i>
							</td>
							<td style="font-size: 20px;"><i style="color: #D0282E;" class="icon-check"></i></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>


<!-- 绩效考评-系统公示 -->
<div id="modalSysPublic" class="modal-custom-wrap">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-step">
				<div class="step-start active">开始</div>
				<div class="modal-step-col active">系统自评</div>
				<div class="modal-step-col active">系统初步考核</div>
				<div class="modal-step-col active">系统公示</div>
				<div class="modal-step-col">部门负责人签字</div>
				<div class="modal-step-col">分管局领导签字</div>
				<div class="modal-step-col">绩效考评领导小组复核及调整</div>
				<div class="modal-step-col">局主管领导最终审签</div>
				<div class="modal-step-col">最终结果全局公示</div>
				<div class="step-start step-end">结束</div>
			</div>
			<div class="modal-custom-content">
				<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>单位名称</th>
							<th>总得分</th>
							<th>基础分</th>
							<th>扣分情况</th>
							<th>实际得分</th>
							<th>扣算后得分</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>南宁公安处</td>
							<td style="color: #2429FF;">90</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>



<!-- 绩效考评-部门负责人签字 -->
<div id="modalSysDepartment" class="modal-custom-wrap">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-step">
				<div class="step-start active">开始</div>
				<div class="modal-step-col active">系统自评</div>
				<div class="modal-step-col active">系统初步考核</div>
				<div class="modal-step-col active">系统公示</div>
				<div class="modal-step-col active">部门负责人签字</div>
				<div class="modal-step-col">分管局领导签字</div>
				<div class="modal-step-col">绩效考评领导小组复核及调整</div>
				<div class="modal-step-col">局主管领导最终审签</div>
				<div class="modal-step-col">最终结果全局公示</div>
				<div class="step-start step-end">结束</div>
			</div>
			<div class="modal-custom-tab">
				<div class="modal-custom-tab-item">南宁公安处（99）</div>
				<div class="modal-custom-tab-item">南宁公安处（99）</div>
				<div class="modal-custom-tab-item">南宁公安处（99）</div>
			</div>
			<div class="modal-custom-bar">
				<div class="modal-custom-btn red">部门业务扣分</div>
				<div class="modal-custom-btn">部门公共扣分</div>
				<div class="modal-custom-btn">部门公共扣分</div>
			</div>
			<div class="modal-custom-content">
				<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><input type="checkbox" onclick="selectAll(this.checked, 'peocheck')" />全选</th>
							<th>序号</th>
							<th>工作</th>
							<th>考评内容</th>
							<th>分值/奖金</th>
							<th>自评时间</th>
							<th>自评人</th>
							<th>附件</th>
							<th>考评人</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input class="i-checks" type="checkbox" name="peocheck"></td>
							<td>1</td>
							<td>安检工作</td>
							<td>
								<p>1.xxxx,扣x分</p>
								<p>2.xxxx,扣x分</p>
								<p>3.xxxx,扣x分</p>
							</td>
							<td>-2</td>
							<td>2019-01-01 15:01:01</td>
							<td>xxx</td>
							<td>
								<p>xxx.pdf</p>
								<p style="cursor: pointer;color: #2429FF;"><i class="icon-paperclip"></i>附件</p>
							</td>
							<td>xxx</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-custom-info1-bottom">
				<div class="modal-custom-info1-btn red">确认签字</div>
			</div>
		</div>
	</div>
</div>


<!-- 绩效考评-全局公示 -->
<div id="modalAllPublic" class="modal-custom-wrap">
	<div class="modal-custom">
		<div class="modal-custom-head">
			<h3 class="close" onclick="$(this).parent().parent().parent().css('display','none')">x</h3>
		</div>
		<div class="modal-custom-main">
			<div class="modal-custom-main-step">
				<div class="step-start active">开始</div>
				<div class="modal-step-col active">系统自评</div>
				<div class="modal-step-col active">系统初步考核</div>
				<div class="modal-step-col active">系统公示</div>
				<div class="modal-step-col active">部门负责人签字</div>
				<div class="modal-step-col active">分管局领导签字</div>
				<div class="modal-step-col active">绩效考评领导小组复核及调整</div>
				<div class="modal-step-col active">局主管领导最终审签</div>
				<div class="modal-step-col active">最终结果全局公示</div>
				<div class="step-start step-end active">结束</div>
			</div>
			<div class="modal-custom-bar" style="padding-left: 20px;">
				2019年9月份南宁铁路公安局各处考评情况  
				<select>
					<option value ="">总成绩</option>
				</select>
			</div>
			<div class="modal-custom-content">
				<div class="modal-custom-tb-l">
					<div id="modalAllPublicEchart"></div>
				</div>
				<div class="modal-custom-tb-r">
					<table class="table table-striped table-bordered table-condensed" style="width: 100%;">
						<thead>
							<tr>
								<th>单位名称</th>
								<th>总得分</th>
								<th>基础分</th>
								<th>扣分情况</th>
								<th>实际得分</th>
								<th>换算后得分</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>南宁公安处</td>
								<td style="color: #2429FF;">99</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	modalAllPublicEchart()
	function modalAllPublicEchart(){
		var myChart1 = echarts.init(document.getElementById('modalAllPublicEchart'));
		option = {
			title : {
			    text: '2019年9月份南宁铁路公安局各处考评情况',
			    x:'center'
			},
			legend: {
					orient: 'horizontal',
					bottom: 0,
					data: ['新闻宣传情况区','新闻宣传情况区','新闻宣传情况区']
			},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data: ['新闻宣传情况区','新闻宣传情况区','新闻宣传情况区'],
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'考评',
		            type:'bar',
		            barWidth: '60%',
		            data:[15, 8, 4, 7]
		        }
		    ]
		};
		myChart1.setOption(option);
	}
</script>
