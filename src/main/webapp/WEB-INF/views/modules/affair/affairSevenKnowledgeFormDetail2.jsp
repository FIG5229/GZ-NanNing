<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#print").click(function () {
                $("#contentDiv").printThis({
                    debug: false,
                    importCSS: true,
                    importStyle: true,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css", "${ctxStatic}/common/modal-custom.css"],
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

        .tdKeepRight {
            align: right;
        }

        .picbox {
            position: relative;
            width: 90%;
        }

        .picbox:before {
            content: "";
            display: block;
            padding-top: 150%;
        / / 宽高比例修改这个数值
        }

        .picbox img {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<br>
<div id="contentDiv" style="width: 90%;margin: auto;">
    <table id="contentTable" class="table table-bordered">
    <tr>
        <td class="tdKeepRight">姓&#12288;&#12288;名</td>
        <td>${affairSevenKnowledge.name}</td>
        <td class="tdKeepRight">性&#12288;&#12288;别</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.sex, 'sex', '')}</td>
    </tr>
    <tr>
        <td class="tdKeepRight">身份证号</td>
        <td>${affairSevenKnowledge.idNumber}</td>
        <td class="tdKeepRight">出生日期</td>
        <td><fmt:formatDate value="${affairSevenKnowledge.birthday}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <td>民&#12288;&#12288;族</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.nation, 'nation', '')}</td>
        <td>政治面貌</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.politicsFace, 'political_status', '')}</td>
    </tr>
    <tr>
        <td>文化程度</td>
        <td>${affairSevenKnowledge.degreeEducation}</td>
        <td>联系方式</td>
        <td>${affairSevenKnowledge.contactTel}</td>
    </tr>
    <tr>
        <td>家庭地址</td>
        <td>${affairSevenKnowledge.homeAddress}</td>
        <td>住房面积</td>
        <td>${affairSevenKnowledge.houseArea}</td>
    </tr>
    <tr>
        <td>参加工作时间</td>
        <td><fmt:formatDate value="${affairSevenKnowledge.workTime}" pattern="yyyy-MM-dd"/></td>
        <td>从警时间</td>
        <td><fmt:formatDate value="${affairSevenKnowledge.fromPoliceTime}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <td>警&#12288;&#12288;衔</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.policeRank, 'police_rank_level', '')}</td>
        <td>婚姻状态</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.maritalStatus, 'affair_marital_status', '')}</td>
    </tr>
    <tr>
        <td>健康状况</td>
        <td colspan="3">${fns:getDictLabel(affairSevenKnowledge.health, 'yes_no', '')}</td>
    </tr>
    <tr>
        <td>病&#12288;&#12288;史</td>
        <td colspan="3">${affairSevenKnowledge.medicalHistory}</td>
    </tr>
    <tr>
        <td>心里疾病</td>
        <td colspan="3">${affairSevenKnowledge.mentalIllness}</td>
    </tr>
    <tr>
        <td>个人简历</td>
        <td colspan="3">${affairSevenKnowledge.curriculumVitae}</td>
    </tr>
    <tr>
        <td>收入情况</td>
        <td>${affairSevenKnowledge.income}</td>
        <td>重点支出</td>
        <td>${affairSevenKnowledge.keyExpenditure}</td>
    </tr>
    <tr>
        <td>直系亲属经商情况</td>
        <td colspan="3">${affairSevenKnowledge.businessSituation}</td>
    </tr>
    <tr>
        <td>有无其他房产（面积）</td>
        <td>${affairSevenKnowledge.otherHouse}</td>
        <td>有无债务纠纷</td>
        <td>${affairSevenKnowledge.debtDispute}</td>
    </tr>
    <tr>
        <td>性&#12288;&#12288;格</td>
        <td>${affairSevenKnowledge.character}</td>
        <td>特&#12288;&#12288;长</td>
        <td>${affairSevenKnowledge.specialty}</td>
    </tr>
    <tr>
        <td>业余爱好</td>
        <td>${affairSevenKnowledge.hobbies}</td>
        <td>工作单位</td>
        <td>${affairSevenKnowledge.unit}</td>
    </tr>
    <tr>
        <td>工作岗位</td>
        <td>${affairSevenKnowledge.workJob}</td>
        <td>职&#12288;&#12288;务</td>
        <td>${affairSevenKnowledge.job}</td>
    </tr>
    <tr>
        <td>擅长业务</td>
        <td>${affairSevenKnowledge.goodBusiness}</td>
        <td>业务不足</td>
        <td>${affairSevenKnowledge.insufficientBusiness}</td>
    </tr>
    <tr>
        <td>是否炒股（基金）</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.stockSpeculation, 'yes_no', '')}</td>
        <td>是否常买彩票</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.buyLottery, 'yes_no', '')}</td>
    </tr>
    <tr>
        <td>是否练功法</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.practiceSkills, 'yes_no', '')}</td>
        <td>家庭和睦情况</td>
        <td>${affairSevenKnowledge.familyHarmony}</td>
    </tr>
    <tr>
        <td>邻里关系</td>
        <td>${affairSevenKnowledge.neighborhoodRelations}</td>
        <td>同事关系</td>
        <td>${affairSevenKnowledge.colleagueRelations}</td>
    </tr>
    <tr>
        <td>近期奖惩状况</td>
        <td colspan="3">${affairSevenKnowledge.rewardsPunishments}</td>
    </tr>
    <tr>
        <td>岗位是否配枪</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.hasGun, 'yes_no', '')}</td>
        <td>获持枪资格时间</td>
        <td><fmt:formatDate value="${affairSevenKnowledge.gunHoldTime}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <td>配枪制度落实情况</td>
        <td colspan="3">${affairSevenKnowledge.gunSystem}</td>
    </tr>
    <tr>
        <td>是否喝酒</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.drink, 'yes_no', '')}</td>
        <td>是否贪杯</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.greedyCup, 'yes_no', '')}</td>
    </tr>
    <tr>
        <td>有无酒后失控情况</td>
        <td colspan="3">${affairSevenKnowledge.outControlDrink}</td>
    </tr>
    <tr>
        <td>驾驶证类型</td>
        <td>${affairSevenKnowledge.driverLicenseType}</td>
        <td>驾照获取时间</td>
        <td><fmt:formatDate value="${affairSevenKnowledge.driverTime}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <td>汽车驾龄</td>
        <td>${affairSevenKnowledge.driverAgeCar}</td>
        <td>摩托车驾龄</td>
        <td>${affairSevenKnowledge.driverAgeMotorcycle}</td>
    </tr>
    <tr>
        <td>获准驾驶警车时间</td>
        <td><fmt:formatDate value="${affairSevenKnowledge.policeCarTime}" pattern="yyyy-MM-dd"/></td>
        <td>获准长途驾驶警车时间</td>
        <td><fmt:formatDate value="${affairSevenKnowledge.longPoliceCarTime}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <td>安全行车记录</td>
        <td colspan="3">${affairSevenKnowledge.safeDriver}</td>
    </tr>
    <tr>
        <td>是否爱打麻将</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.mahjong, 'yes_no', '')}</td>
        <td>是否有赌博迹象</td>
        <td>${affairSevenKnowledge.gambling}</td>
    </tr>
    <tr>
        <td>赌博其他反应</td>
        <td colspan="3">${affairSevenKnowledge.betOther}</td>
    </tr>
    <tr>
        <td>家庭重大变故</td>
        <td colspan="3">${affairSevenKnowledge.familyMisfortune}</td>
    </tr>
    <tr>
        <td>家属有无参与非法组织</td>
        <td>${affairSevenKnowledge.illegalOrganization}</td>
        <td>有无社区不良交往</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.badAssociation, 'yes_no', '')}</td>
    </tr>
    <tr>
        <td>是否沉迷网络</td>
        <td>${affairSevenKnowledge.addictedInternet}</td>
        <td>是否经常出入高消费场所</td>
        <td>${affairSevenKnowledge.luxuryPlaces}</td>
    </tr>
    <tr>
        <td>有无参股经商反应</td>
        <td colspan="3">${affairSevenKnowledge.participationBusiness}</td>
    </tr>
    <tr>
        <td>以权谋私不良反映</td>
        <td colspan="3">${affairSevenKnowledge.crruption}</td>
    </tr>
    <tr>
        <td>其他不良反应</td>
        <td colspan="3">${affairSevenKnowledge.otherAdverseReactions}</td>
    </tr>
    <tr>
        <td>有无脱岗离岗记录</td>
        <td>${affairSevenKnowledge.offDuty}</td>
        <td>有无群众投诉</td>
        <td>${affairSevenKnowledge.massComplaints}</td>
    </tr>
    <tr>
        <td>上级督察训导记录</td>
        <td colspan="3">${affairSevenKnowledge.disciplining}</td>
    </tr>
    <tr>
        <td>包保所领导</td>
        <td>${affairSevenKnowledge.suoLeader}</td>
        <td>包保处领导</td>
        <td>${affairSevenKnowledge.chuLeader}</td>
    </tr>
    <tr>
        <td>列为重点帮教对象时间</td>
        <td colspan="3"><fmt:formatDate value="${affairSevenKnowledge.helpEducateTime}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <td>时间：</td>
        <td><fmt:formatDate value="${affairSevenKnowledge.time}" pattern="yyyy-MM-dd"/></td>
        <td>综合评定：</td>
        <td>${fns:getDictLabel(affairSevenKnowledge.evaluate, "seven_evaluate_rating", "")}</td>
    </tr>

    </table>
    <div class="modal-custom-info2-row">
        <span class="modal-custom-info2-key">家庭关系：</span>
        <br>
        <span class="modal-custom-info2-value">
        <c:if test="${personnelFamilyList.size()>0}">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
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
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${personnelFamilyList}" var="familyList" varStatus="status">
                    <tr>
                        <td>${familyList.name}</td>
                        <td>${familyList.relationship}</td>
                        <td><fmt:formatDate value="${familyList.birthday}" pattern="yyyy-MM-dd"/></td>
                        <td>${familyList.unitNameJob}</td>
                        <td>${familyList.identityJob}</td>
                        <td>${fns:getDictLabel(familyList.nation, 'nation', '')}</td>
                        <td>${familyList.education}</td>
                        <td>${fns:getDictLabel(familyList.politicsFace, 'political_status', '')}</td>
                        <td>${familyList.jobLevel}</td>
                        <td>${familyList.status}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </span>
    </div>
</div>

<br>
<div class="modal-custom-info1-bottom">
    <div class="modal-custom-info1-btn red" id="print">打印</div>
</div>
</body>
</html>

