<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#print").click(function(){
                $("#printContentDiv").printThis({
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
    <style>
        #contentTable tr td {
            width: 25%;
        }
        .tdKeepRight{
            align:right;
        }
        .picbox{
            position: relative;
            width: 90%;
        }
        .picbox:before {
            content: "";
            display: block;
            padding-top: 150%; //宽高比例修改这个数值
        }

        .picbox img {
            position:  absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<br>
<div id="printContentDiv" style="width: 90%;margin: auto;text-align: center">
    <%--<table id="contentTable" class="table table-bordered" >
        <tr>
            <td class="tdKeepRight">姓&#12288;&#12288;名</td>
            <td>${personnelBase.name}</td>
            <td class="tdKeepRight">身份证号</td>
            <td>${personnelBase.idNumber}</td>
        </tr>
        <tr>
            <td class="tdKeepRight">民&#12288;&#12288;族</td>
            <td>${fns:getDictLabel(personnelBase.nation, 'nation', '')}</td>
            <td class="tdKeepRight">性&#12288;&#12288;别</td>
            <td>${fns:getDictLabel(personnelBase.sex, 'sex', '')}</td>
        </tr>
        <tr>
            <td class="tdKeepRight">警&#12288;&#12288;号</td>
            <td>${personnelBase.policeIdNumber}</td>
            <td class="tdKeepRight">人员类别</td>
            <td>${personnelBase.personnelType}</td>
        </tr>
        <tr>
            <td class="tdKeepRight">籍&#12288;&#12288;贯</td>
            <td>${personnelBase.nativePlace}</td>
            <td class="tdKeepRight">出生日期</td>
            <td><fmt:formatDate value="${personnelBase.birthday}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <td class="tdKeepRight">学&#12288;&#12288;历</td>
            <td>${personnelBase.education}</td>
            <td class="tdKeepRight">电&#12288;&#12288;话</td>
            <td>${personnelBase.phoneNumber}</td>
        </tr>
        <tr>
            <td class="tdKeepRight">参加工作日期</td>
            <td><fmt:formatDate value="${personnelBase.workDate}" pattern="yyyy-MM-dd"/></td>
            <td class="tdKeepRight">参加公安工作日期</td>
            <td><fmt:formatDate value="${personnelBase.publicSecurityDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <td class="tdKeepRight">政治面貌</td>
            <td>${fns:getDictLabel(personnelBase.politicsFace, 'political_status', '')}</td>
            <td class="tdKeepRight">参加组织日期</td>
            <td><fmt:formatDate value="${personnelBase.organizationDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <td class="tdKeepRight">工作单位名称</td>
            <td>${personnelBase.workunitName}</td>
            <td class="tdKeepRight">工作单位(实际)</td>
            <td>${personnelBase.actualUnit}</td>
        </tr>
        <tr>
            <td class="tdKeepRight">职务简称</td>
            <td>${personnelBase.jobAbbreviation}</td>
            <td class="tdKeepRight">所属部门和警种</td>
            <td>${personnelBase.bmhjz}</td>
            &lt;%&ndash;<td class="tdKeepRight"></td>
            <td></td>&ndash;%&gt;
        </tr>
    </table>--%>
    <table id="contentTable" class="table table-bordered" style="width: 90%;margin: auto;text-align: center">
            <tr>
                <td class="tdKeepRight">姓&#12288;&#12288;名</td>
                <td colspan="2">${personnelBase.name}</td>
                <td rowspan="5" >
                    <div class="picbox" style="margin: auto">
                        <img src="<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() %>${personnelBase.photo}">
                    </div>
                </td>
            </tr>
            <tr>
                <td class="tdKeepRight">身份证号</td>
                <td colspan="2">${personnelBase.idNumber}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">性&#12288;&#12288;别</td>
                <td colspan="2">${fns:getDictLabel(personnelBase.sex, 'sex', '')}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">民&#12288;&#12288;族</td>
                <td colspan="2">${fns:getDictLabel(personnelBase.nation, 'nation', '')}</td>

            </tr>
            <tr>
                <td class="tdKeepRight">出生日期</td>
                <td colspan="2"><fmt:formatDate value="${personnelBase.birthday}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="tdKeepRight">政治面貌</td>
                <td>${fns:getDictLabel(personnelBase.politicsFace, 'political_status', '')}</td>
                <td class="tdKeepRight">参加组织日期</td>
                <td><fmt:formatDate value="${personnelBase.organizationDate}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="tdKeepRight">学&#12288;&#12288;历</td>
                <td>${personnelBase.education}</td>
                <td class="tdKeepRight">电&#12288;&#12288;话</td>
                <td>${personnelBase.phoneNumber}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">警&#12288;&#12288;号</td>
                <td>${personnelBase.policeIdNumber}</td>
                <td class="tdKeepRight">人员类别</td>
                <td>${personnelBase.personnelType}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">参加工作日期</td>
                <td><fmt:formatDate value="${personnelBase.workDate}" pattern="yyyy-MM-dd"/></td>
                <td class="tdKeepRight">参加公安工作日期</td>
                <td><fmt:formatDate value="${personnelBase.publicSecurityDate}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td class="tdKeepRight">人员状态</td>
                <td>${fns:getDictLabel(personnelBase.status, 'personnel_status', '')}</td>
                <td class="tdKeepRight">基层工作经历时间</td>
                <td>${personnelBase.jcgzjlsj}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">警员库标志</td>
                <td>${fns:getDictLabel(personnelBase.policeDepotSign, 'yes_no', '')}</td>
                <td class="tdKeepRight">血&#12288;&#12288;型</td>
                <td>${personnelBase.bloodType}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">工龄计算校正值</td>
                <td>${personnelBase.gljsjzz}</td>
                <td class="tdKeepRight">警衔应加学制年限</td>
                <td>${personnelBase.jxyjxznx}</td>
            </tr>
            <%--警衔信息--%>
            <tr>
                <td class="tdKeepRight">警衔名称</td>
                <td>${personnelPoliceRank.name}</td>
                <td class="tdKeepRight">警衔类型</td>
                <td>${fns:getDictLabel(personnelPoliceRank.type, 'personnel_jxtype', '')}</td>
            </tr>
            <%--<tr>
                <td>衔称变动原因</td>
                <td>${fns:getDictLabel(personnelPoliceRank.changeReason,'dict_change_reason','')}</td>
                <td>起算日期</td>
                <td><fmt:formatDate value="${personnelPoliceRank.startDate}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td>授衔日期</td>
                <td><fmt:formatDate value="${personnelPoliceRank.awardTitleDate}" pattern="yyyy-MM-dd"/></td>
                <td>授衔来源</td>
                <td>${fns:getDictLabel(personnelPoliceRank.source, 'dict_rank_source', '')}</td>
            </tr>
            <tr>
                <td>授衔批准单位名称</td>
                <td>${personnelPoliceRank.approvalUnitName}</td>
                <td>授衔令号</td>
                <td>${personnelPoliceRank.approvalNumber}</td>
            </tr>--%>

            <tr>
                <td class="tdKeepRight">人事命令单位</td>
                <td>${personnelBase.workunitName}</td>
                <td class="tdKeepRight">实际工作单位</td>
                <td>${personnelBase.actualUnit}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">工作单位代码</td>
                <td>${personnelBase.workunitCode}</td>
                <td class="tdKeepRight">关系所在单位</td>
                <td>${personnelBase.relationshipUnit}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">单位代码</td>
                <td>${personnelBase.unitCode}</td>
                <td class="tdKeepRight">籍&#12288;&#12288;贯</td>
                <td>${personnelBase.nativePlace}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">户口性质</td>
                <td>${personnelBase.populationCharacter}</td>
                <td class="tdKeepRight">成&#8194;长&#8194;地</td>
                <td>${personnelBase.growPlace}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">出&#8194;生&#8194;地</td>
                <td>${personnelBase.birthPlace}</td>
                <td class="tdKeepRight">户籍所在地</td>
                <td>${personnelBase.hjszd}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">户籍所在地详址</td>
                <td>${personnelBase.hjszdxz}</td>
                <td class="tdKeepRight">人员所属部门和警种</td>
                <td>${personnelBase.bmhjz}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">管理类别</td>
                <td>${personnelBase.category}</td>
                <td class="tdKeepRight">职务简称</td>
                <td>${personnelBase.jobAbbreviation}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">职务全称</td>
                <td>${personnelBase.jobFullname}</td>
                <td class="tdKeepRight">个人身份</td>
                <td>${personnelBase.identity}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">人员工作岗位</td>
                <td>${fns:getDictLabel(personnelBase.job,'personnel_gw','')}</td>
                <td class="tdKeepRight">婚姻状况</td>
                <td>${fns:getDictLabel(personnelBase.marriageStatus,'affair_marital_status', '')}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">涉密标志</td>
                <td>${fns:getDictLabel(personnelBase.secretStatus,'yes_no','')}</td>
                <td class="tdKeepRight">是否是协管干部</td>
                <td>${fns:getDictLabel(personnelBase.isXggb,'yes_no','')}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">协管干部标识</td>
                <td>${personnelBase.xggbbs}</td>
                <td class="tdKeepRight">健康状态</td>
                <td>${personnelBase.healthStatus}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">专&#12288;&#12288;长</td>
                <td>${personnelBase.expertise}</td>
                <td class="tdKeepRight">暂缓列入套改实施范围原因类别</td>
                <td colspan="3">${personnelBase.reason}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">奖励综述</td>
                <td colspan="3">${personnelBase.award}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">年度考核综述</td>
                <td colspan="3">${personnelBase.assessment}</td>
            </tr>
            <tr>
                <td class="tdKeepRight">备&#12288;&#12288;注</td>
                <td colspan="3">${personnelBase.remarks}</td>
            </tr>

        </table>
    <br>
    <div class="modal-custom-info2-row">
        <span class="modal-custom-info2-key">奖励情况：</span>
        <table id="contentTableM" class="table table-striped table-bordered table-condensed">
            <thead>
            <tr>
                <th>序号</th>
                <th>奖励名称</th>
                <th>奖励文号</th>
                <th>奖励类别</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${AffairPersonalReward}" var="AffairPersonalReward" varStatus="status">
                <tr>
                    <td>${status.index+1}</td>
                    <td>${AffairPersonalReward.rewardName}</td>
                        <%--<td>${fns:getDictLabel(AffairPersonalReward.jlType, 'affair_grsb_type', '')}</td>--%>
                    <td>${AffairPersonalReward.fileNo}</td>
                    <td>${fns:getDictLabel(AffairPersonalReward.type, 'affair_org_reward_punish', '')}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="modal-custom-info2-row" style="margin-top: 20px;">
        <span class="modal-custom-info2-key" >惩戒情况：</span>
        <table id="contentTableC" class="table table-striped table-bordered table-condensed">
            <thead>
            <tr>
                <th>序号</th>
                <th>问题类型</th>
                <th>惩戒文号</th>
                <th>处分类型</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${disciplinaryActions}" var="disciplinaryActions" varStatus="status">
                <tr>
                    <td>${status.index+1}</td>
                    <td>${fns:getDictLabel(disciplinaryActions.nature, 'affair_wenti', '')}</td>
                    <td>${disciplinaryActions.fileNum}</td>
                    <td>${fns:getDictLabel(disciplinaryActions.disciplinaryType, 'affair_xzchufen', '')}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<br>
<div class="modal-custom-info1-bottom">
    <div class="modal-custom-info1-btn red" id="print">打印</div>
</div>
</body>
</html>

