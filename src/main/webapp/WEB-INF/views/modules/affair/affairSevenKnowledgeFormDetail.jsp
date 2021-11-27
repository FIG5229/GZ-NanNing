<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">姓名：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.name}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">性别：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.sex, 'sex', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">身份证号：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.idNumber}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">出生日期：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairSevenKnowledge.birthday}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">民族：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.nation, 'nation', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">文化程度：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.degreeEducation}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">政治面貌：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.politicsFace, 'political_status', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">家庭地址：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.homeAddress}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">住房面积：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.houseArea}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">联系方式：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.contactTel}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">参加工作时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairSevenKnowledge.workTime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">从警时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairSevenKnowledge.fromPoliceTime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">警衔：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.policeRank, 'police_rank_level', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">婚姻状态：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.maritalStatus, 'affair_marital_status', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">健康状况：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.health, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">病史：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.medicalHistory}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">心里疾病：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.mentalIllness}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">个人简历：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.curriculumVitae}</span>
                            </div>
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
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">收入情况：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.income}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">重点支出：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.keyExpenditure}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">直系亲属经商情况：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.businessSituation}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">有无其他房产（面积）：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.otherHouse}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">有无债务纠纷：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.debtDispute}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">性格：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.character}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">特长：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.specialty}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">业余爱好：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.hobbies}</span>
                            </div>
                    <%--   复制多余     <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">姓名：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.name}</span>
                            </div>--%>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">工作单位：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.unit}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">工作岗位：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.workJob}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">职务：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.job}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">擅长业务：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.goodBusiness}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">业务不足：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.insufficientBusiness}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否炒股（基金）：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.stockSpeculation, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否常买彩票：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.buyLottery, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否练功法：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.practiceSkills, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">家庭和睦情况：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.familyHarmony}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">邻里关系：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.neighborhoodRelations}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">同事关系：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.colleagueRelations}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">近期奖惩状况：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.rewardsPunishments}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">获持枪资格时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairSevenKnowledge.gunHoldTime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">岗位是否配枪：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.hasGun, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">配枪制度落实情况：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.gunSystem}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否喝酒：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.drink, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否贪杯：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.greedyCup, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">有无酒后失控情况：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.outControlDrink}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">驾驶证类型：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.driverLicenseType}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">驾照获取时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairSevenKnowledge.driverTime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">汽车驾龄：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.driverAgeCar}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">摩托车驾龄：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.driverAgeMotorcycle}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">获准驾驶警车时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairSevenKnowledge.policeCarTime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">获准长途驾驶警车时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairSevenKnowledge.longPoliceCarTime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">安全行车记录：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.safeDriver}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否爱打麻将：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.mahjong, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否有赌博迹象：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.gambling}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">赌博其他反应：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.betOther}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">家庭重大变故：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.familyMisfortune}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">家属有无参与非法组织：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.illegalOrganization}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">有无社区不良交往：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairSevenKnowledge.badAssociation, 'yes_no', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否沉迷网络：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.addictedInternet}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">是否经常出入高消费场所：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.luxuryPlaces}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">有无参股经商反应：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.participationBusiness}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">以权谋私不良反映：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.crruption}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">其他不良反应：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.otherAdverseReactions}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">有无脱岗离岗记录：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.offDuty}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">有无群众投诉：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.massComplaints}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">上级督察训导记录：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.disciplining}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">列为重点帮教对象时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairSevenKnowledge.helpEducateTime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">包保所领导：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.suoLeader}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">包保处领导：</span>
                                <span class="modal-custom-info2-value">${affairSevenKnowledge.chuLeader}</span>
                            </div>


                        </div>
                    </div>

                    <div class="modal-custom-info2-col modal-custom-info2-col1">

                    </div>
                </div>
            </div>
            <div class="modal-custom-info1-bottom">
                <div class="modal-custom-info1-btn red">打印</div>
            </div>
        </div>
    </div>
</div>
