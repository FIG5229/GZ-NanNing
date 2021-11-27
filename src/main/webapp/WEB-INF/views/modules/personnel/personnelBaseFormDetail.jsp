<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function(){
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false
            });
        });
    });
</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content" id="contentTable">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelBase.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelBase.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.nation, 'nation', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelBase.birthday}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.status, 'personnel_status', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">血型：</span><span class="modal-custom-info2-value">${personnelBase.bloodType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警员库标志：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.policeDepotSign, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${personnelBase.policeIdNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员类别：</span><span class="modal-custom-info2-value">${personnelBase.personnelType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">籍贯：</span><span class="modal-custom-info2-value">${personnelBase.nativePlace}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span class="modal-custom-info2-value">${personnelBase.education}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">电话：</span><span class="modal-custom-info2-value">${personnelBase.phoneNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生地：</span><span class="modal-custom-info2-value">${personnelBase.birthPlace}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成长地：</span><span class="modal-custom-info2-value">${personnelBase.growPlace}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">户口性质：</span><span class="modal-custom-info2-value">${personnelBase.populationCharacter}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">户籍所在地：</span><span class="modal-custom-info2-value">${personnelBase.hjszd}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">个人身份：</span><span class="modal-custom-info2-value">${personnelBase.identity}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">婚姻状况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.marriageStatus,'affair_marital_status', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">涉密标志：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.secretStatus,'yes_no','')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">健康状态：</span><span class="modal-custom-info2-value">${personnelBase.healthStatus}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">户籍所在地详址：</span><span class="modal-custom-info2-value">${personnelBase.hjszdxz}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">暂缓列入套改实施范围原因类别：</span><span class="modal-custom-info2-value">${personnelBase.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励综述：</span><span class="modal-custom-info2-value">${personnelBase.award}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年度考核综述：</span><span class="modal-custom-info2-value">${personnelBase.assessment}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${personnelBase.remarks}</span></div>
                        </div>

                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key"></span><img class="modal-custom-info2-value" src="<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() %>${personnelBase.photo}"></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加工作日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelBase.workDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加公安工作日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelBase.publicSecurityDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">基层工作经历时间：</span><span class="modal-custom-info2-value">${personnelBase.jcgzjlsj}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工龄计算校正值：</span><span class="modal-custom-info2-value">${personnelBase.gljsjzz}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔应加学制年限：</span><span class="modal-custom-info2-value">${personnelBase.jxyjxznx}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">政治面貌：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.politicsFace, 'political_status', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加组织日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelBase.organizationDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位代码：</span><span class="modal-custom-info2-value">${personnelBase.workunitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位名称：</span><span class="modal-custom-info2-value">${personnelBase.workunitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位代码：</span><span class="modal-custom-info2-value">${personnelBase.unitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">关系所在单位：</span><span class="modal-custom-info2-value">${personnelBase.relationshipUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员所属部门和警种：</span><span class="modal-custom-info2-value">${personnelBase.bmhjz}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务简称：</span><span class="modal-custom-info2-value">${personnelBase.jobAbbreviation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务全称：</span><span class="modal-custom-info2-value">${personnelBase.jobFullname}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员工作岗位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.job,'personnel_gw','')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">协管干部标识：</span><span class="modal-custom-info2-value">${personnelBase.xggbbs}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否是协管干部：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelBase.isXggb,'yes_no','')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">管理类别：</span><span class="modal-custom-info2-value">${personnelBase.category}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">专长：</span><span class="modal-custom-info2-value">${personnelBase.expertise}</span></div>

                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>