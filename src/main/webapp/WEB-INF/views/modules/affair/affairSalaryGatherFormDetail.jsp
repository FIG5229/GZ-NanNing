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
</script>
<body>
<div class="modal-custom-info2-row" style="overflow-x: scroll" >
    <span class="modal-custom-info2-key"></span>
    <table id="contentTable" class="table table-striped table-bordered table-condensed" >
        <thead>
        <tr>
            <th rowspan="4">序号</th>
            <th rowspan="4">时间</th>
            <th colspan="3">个人信息</th>
            <th rowspan="4">合计</th>
            <th colspan="42">当月应发工资收入</th>
            <th colspan="8">当月个人扣缴（元）</th>
            <th colspan="5">机构、劳统信息</th>
            <th rowspan="4">备注</th>
        </tr>
        <tr>
            <th rowspan="3">姓名</th>
            <th rowspan="3">单位</th>
            <th rowspan="3">身份证号</th>
            <th rowspan="3">工资对应行政级别</th>
            <th colspan="2">基本工资</th>
            <th></th>
            <th colspan="37">津贴补贴</th>
            <th rowspan="3">年终一次性奖金</th>
            <th rowspan="3">合计</th>
            <th colspan="5">社会保险</th>
            <th rowspan="3">住房公积金</th>
            <th rowspan="3">个人所得税</th>
            <th rowspan="3">所在铁路公安局</th>
            <th rowspan="3">所在公安处（局机关、直属支队）</th>
            <th rowspan="3">部门</th>
            <th rowspan="3">劳统人员分类</th>
            <th rowspan="3">当年接受毕业生、复转军人标识</th>
        </tr>
        <tr>
            <th rowspan="2">1、职务工资、试用期工资</th>
            <th rowspan="2">2、级别工资</th>
            <th rowspan="2">1、国家统一的津贴补贴</th>
            <th>（1）</th>
            <th>（2）</th>
            <th>（3）</th>
            <th>（4）</th>
            <th>（5）</th>
            <th>（6）</th>
            <th>（7）</th>
            <th>（8）</th>
            <th>（9）</th>
            <th>（10）</th>
            <th>（11）</th>
            <th>（12）</th>
            <th>（13）</th>
            <th>（14）</th>
            <th>（15）</th>
            <th>（16）</th>
            <th>（17）</th>
            <th>（18）</th>
            <th rowspan="2">2、规范津贴补贴</th>
            <th>（1）</th>
            <th>（2）</th>
            <th rowspan="2">3、改革性 补贴</th>
            <th>（1）</th>
            <th>（2）</th>
            <th>（3）</th>
            <th>（4）</th>
            <th>（5）</th>
            <th rowspan="2">4、奖励性补贴和其他</th>
            <th>（1）</th>
            <th>（2）</th>
            <th>（3）</th>
            <th>（4）</th>
            <th>（5）</th>
            <th>（6）</th>
            <th>（7）</th>
            <th>（8）</th>
            <th>（9）</th>
            <th rowspan="2">基本养老保险</th>
            <th rowspan="2">职业年金</th>
            <th rowspan="2">基本医疗保险</th>
            <th rowspan="2">补充医疗保险</th>
            <th rowspan="2">生育保险等</th>
        </tr>
        <tr>
            <th>加班补贴</th>
            <th>警衔津贴</th>
            <th>执勤岗位津贴</th>
            <th>艰苦边远地区津贴</th>
            <th>住宅公务电话费</th>
            <th>移动通讯费补贴</th>
            <th>禁食猪肉补贴</th>
            <th>女同志卫生费</th>
            <th>独生子女父母奖励</th>
            <th>防暑降温费</th>
            <th>信访工作人员岗位津贴</th>
            <th>1993年工改保留补贴</th>
            <th>西藏特殊津贴</th>
            <th>高海拔地区折算工龄补贴</th>
            <th>新疆保留地区补贴</th>
            <th>特区津贴</th>
            <th>乡镇工作补贴</th>
            <th>增不抵缴临时性补贴</th>
            <th>（1）工作性津贴</th>
            <th>（2）生活性补贴</th>
            <th>住宅取暖补贴</th>
            <th>住房提租补贴</th>
            <th>物业服务补贴</th>
            <th>上下班交通补贴</th>
            <th>改革性补贴</th>
            <th>乘务补贴（含高原）</th>
            <th>线路津贴</th>
            <th>安全治安挂钩考核</th>
            <th>精神文明奖</th>
            <th>人民警察奖励</th>
            <th>公务员奖励</th>
            <th>讲课费</th>
            <th>往年减员补发平均额</th>
            <th>补发往年工资及津补</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${affairLabor}" var="affairLabor" varStatus="status">
        <tr>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairLabor.year}-${affairLabor.month}
            </td>
            <td>
               ${affairLabor.name}
            </td>
            <td>
                    ${affairLabor.unit}
            </td>
            <td>
                    ${affairLabor.idNumber}
            </td>
            <td>
                    ${affairLabor.sum}
            </td>
            <td>
                    ${affairLabor.level}
            </td>

            <td>
                    ${affairLabor.jbSalary}
            </td>
            <td>
                    ${affairLabor.jbGradeSalary}
            </td>
            <td>
                    ${affairLabor.gjSum}
            </td>
            <td>
                    ${affairLabor.jiabanAllowance}
            </td>
            <td>
                    ${affairLabor.jingxianAllowance}
            </td>
            <td>
                    ${affairLabor.zhiqinAllowance}
            </td>
            <td>
                    ${affairLabor.jkbyAllowance}
            </td>
            <td>
                    ${affairLabor.telephoneFee}
            </td>
            <td>
                    ${affairLabor.mobileFee}
            </td>
            <td>
                    ${affairLabor.jszrAllowance}
            </td>
            <td>
                    ${affairLabor.nvHygieneFee}
            </td>
            <td>
                    ${affairLabor.onlyChildAllowance}
            </td>
            <td>
                    ${affairLabor.fangshuAllowance}
            </td>
            <td>
                    ${affairLabor.xinfangAllowance}
            </td>
            <td>
                    ${affairLabor.gonggai1993Allowance}
            </td>
            <td>
                    ${affairLabor.xizangAllowance}
            </td>
            <td>
                    ${affairLabor.highAltitudeAllowance}
            </td>
            <td>
                    ${affairLabor.xinjiangAllowance}
            </td>
            <td>
                    ${affairLabor.sarAllowance}
            </td>
            <td>
                    ${affairLabor.townshipAllowance}
            </td>
            <td>
                    ${affairLabor.linshiAllowance}
            </td>
            <td>
                    ${affairLabor.guifanSum}
            </td>
            <td>
                    ${affairLabor.workingAllowance}
            </td>
            <td>
                    ${affairLabor.livingAllowance}
            </td>
            <td>
                    ${affairLabor.gaigeSum}
            </td>
            <td>
                    ${affairLabor.zhuzhaiAllowance}
            </td>
            <td>
                    ${affairLabor.zhufangAllowance}
            </td>
            <td>
                    ${affairLabor.wuyeAllowance}
            </td>
            <td>
                    ${affairLabor.jiaotongAllowance}
            </td>
            <td>
                    ${affairLabor.gaigeAllowance}
            </td>
            <td>
                    ${affairLabor.rewardSum}
            </td>
            <td>
                    ${affairLabor.chengwuAllowance}
            </td>
            <td>
                    ${affairLabor.xianluAllowance}
            </td>
            <td>
                    ${affairLabor.anquanAllowance}
            </td>
            <td>
                    ${affairLabor.jingshenAllowance}
            </td>
            <td>
                    ${affairLabor.jingchaAllowance}
            </td>
            <td>
                    ${affairLabor.gongwuyuanAllowance}
            </td>
            <td>
                    ${affairLabor.jiangkeAllowance}
            </td>
            <td>
                    ${affairLabor.jianyuanAllowance}
            </td>
            <td>
                    ${affairLabor.gongziAllowance}
            </td>
            <td>
                    ${affairLabor.yearEndAwards}
            </td>
            <td>
                    ${affairLabor.baoxianSum}
            </td>
            <td>
                    ${affairLabor.baseYanglaoAllowance}
            </td>
            <td>
                    ${affairLabor.zhiyeAllowance}
            </td>
            <td>
                    ${affairLabor.baseYiliaoAllowance}
            </td>
            <td>
                    ${affairLabor.buchongYiliaoAllowance}
            </td>
            <td>
                    ${affairLabor.shengyuAllowance}
            </td>
            <td>
                    ${affairLabor.gongjijin}
            </td>
            <td>
                    ${affairLabor.personalIncomeTax}
            </td>
            <td>
                    ${affairLabor.whereGonganju}
            </td>
            <td>
                    ${affairLabor.whereGonganchu}
            </td>
            <td>
                    ${affairLabor.department}
            </td>
            <td>
                    ${affairLabor.personnelType}
            </td>
            <td>
                    ${fns:getDictLabel(affairLabor.isLogo, 'yes_no', '')}
            </td>
            <td>
                    ${affairLabor.remark}
            </td>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>