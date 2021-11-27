/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExcelTemplate;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.entity.AffairEvaluationCriteria;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicePersonalTrainingFile;
import com.thinkgem.jeesite.modules.personnel.entity.*;
import com.thinkgem.jeesite.modules.personnel.service.*;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
//import javafx.scene.control.Cell;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairSevenKnowledge;
import com.thinkgem.jeesite.modules.affair.service.AffairSevenKnowledgeService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 七知档案Controller
 * @author daniel.liu
 * @version 2020-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairSevenKnowledge")
public class AffairSevenKnowledgeController extends BaseController {

	@Autowired
	private AffairSevenKnowledgeService affairSevenKnowledgeService;

	@Autowired
	private PersonnelFamilyService personnelFamilyService;
	@Autowired
	private PersonnelRewardService personnelRewardService;
	@Autowired
	private PersonnelPunishService personnelPunishService;
	@Autowired
	private PersonnelResumeService personnelResumeService;

	@Autowired
	private PersonnelPoliceRankService personnelPoliceRankService;

	@Autowired
	private PersonnelXueliService personnelXueliService;

	@ModelAttribute
	public AffairSevenKnowledge get(@RequestParam(required = false) String id) {
		AffairSevenKnowledge entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = affairSevenKnowledgeService.get(id);
		}
		if (entity == null) {
			entity = new AffairSevenKnowledge();
		}
		return entity;
	}

	@RequiresPermissions("affair:affairSevenKnowledge:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairSevenKnowledge affairSevenKnowledge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSevenKnowledge> page = affairSevenKnowledgeService.findPage(new Page<AffairSevenKnowledge>(request, response), affairSevenKnowledge);
		List<String> idNumbers = new ArrayList<>();
		page.getList().stream().forEach(item -> {
			idNumbers.add(item.getIdNumber());
		});
		model.addAttribute("page", page);
		return "modules/affair/affairSevenKnowledgeList";
	}

	@RequiresPermissions("affair:affairSevenKnowledge:view")
	@RequestMapping(value = "form")
	public String form(AffairSevenKnowledge affairSevenKnowledge, Model model) {
		model.addAttribute("affairSevenKnowledge", affairSevenKnowledge);
		return "modules/affair/affairSevenKnowledgeForm";
	}

	@RequiresPermissions("affair:affairSevenKnowledge:view")
	@RequestMapping(value = "formDetail")
	public String formDetail(AffairSevenKnowledge affairSevenKnowledge, Model model) {
		PersonnelFamily personnelFamily = new PersonnelFamily();
		personnelFamily.setIdNumber(affairSevenKnowledge.getIdNumber());
		List<PersonnelFamily> personnelFamilyList = personnelFamilyService.findFamilyByIdNumer(personnelFamily);
		Map<String, String> relationInfo = getRelationInfo(affairSevenKnowledge);
		affairSevenKnowledge.setCurriculumVitae(relationInfo.get("resume"));
		affairSevenKnowledge.setRewardsPunishments(relationInfo.get("rewardPunish"));
		model.addAttribute("affairSevenKnowledge", affairSevenKnowledge);
		model.addAttribute("personnelFamilyList", personnelFamilyList);
//		return "modules/affair/affairSevenKnowledgeFormDetail";
		return "modules/affair/affairSevenKnowledgeFormDetail2";
	}

	@RequiresPermissions("affair:affairSevenKnowledge:edit")
	@RequestMapping(value = "save")
	public String save(AffairSevenKnowledge affairSevenKnowledge, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, affairSevenKnowledge)) {
			return form(affairSevenKnowledge, model);
		}
		affairSevenKnowledgeService.save(affairSevenKnowledge);
		addMessage(redirectAttributes, "保存七知档案成功");
		request.setAttribute("saveResult", "success");
		return "modules/affair/affairSevenKnowledgeForm";
	}

	@RequiresPermissions("affair:affairSevenKnowledge:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairSevenKnowledge affairSevenKnowledge, RedirectAttributes redirectAttributes) {
		affairSevenKnowledgeService.delete(affairSevenKnowledge);
		addMessage(redirectAttributes, "删除七知档案成功");
		return "redirect:" + Global.getAdminPath() + "/affair/affairSevenKnowledge/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairSevenKnowledge:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if (ids != null && ids.size() > 0) {
			affairSevenKnowledgeService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} else {
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	@RequestMapping(value = "export")
	public String exportExcelByTemplate(AffairSevenKnowledge affairSevenKnowledge, String fileName, HttpServletResponse response) {
		//获取模板
		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		//初始化工具
		ExcelTemplate excel = new ExcelTemplate(filePath + fileName);

		Map<String, Object> map = getSevenInfo(affairSevenKnowledge);

//		response.setContentType("octets/stream");
//		response.addHeader("Content-Disposition", "attachment;filename=test.xlsx");

		// 第一个参数，需要操作的sheet的索引
		// 第二个参数，需要复制的区域的第一行索引（占位符所在的第一行）
		// 第三个参数，需要复制的区域的最后一行索引（占位符所在的最后一行）
		// 第四个参数，需要插入的位置的索引(占位符的下一行)
		// 第五个参数，填充行区域中${}的值
		// 第六个参数，是否需要删除原来的区域
		// 需要注意的是，行的索引一般要减一
		try {
			excel.fillVariable(0, (Map<String, String>) map.get("param"));
			int i = excel.addRowByExist(0, 14, 14, 15, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"), true);
			//家庭成员和主要社会关系下方多处来的列合并到一个单元格
			//没有关系就不需要合并了
			if (i > 0) {
//				excel.mergedRegion(0,16,16+i+1,1,1);
			}
//			excel.addRowByExist(0,14,14,15,rows,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 第一个参数，需要操作的sheet的索引
		// 第二个参数，替换sheet当中${<变量的值>}的值

		//下载
		try {
			// 清除buffer缓存
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			//指定下载名字
			response.setHeader("Content-Disposition", "attachment; filename=" +
					Encodes.urlEncode(affairSevenKnowledge.getName() + fileName));
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
			OutputStream fout = response.getOutputStream();
			excel.getWorkbook().write(fout);
			fout.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		//修改
		return "redirect:" + adminPath + "/affair/affairSevenKnowledge/?repage";
	}

	@RequestMapping(value = "exportBatch")
	public String exportBatch(AffairSevenKnowledge affairSevenKnowledge, HttpServletRequest request, HttpServletResponse response, boolean flag) {
		String fileName = request.getParameter("fileName");
		Page<AffairSevenKnowledge> page;
		if (flag == true) {
			page = affairSevenKnowledgeService.findPage(new Page<AffairSevenKnowledge>(request, response), affairSevenKnowledge);
		} else {
			page = affairSevenKnowledgeService.findPage(new Page<AffairSevenKnowledge>(request, response, -1), affairSevenKnowledge);
		}
		for (AffairSevenKnowledge item:page.getList()) {
				PersonnelPoliceRank personnelPoliceRank = new PersonnelPoliceRank();
				personnelPoliceRank.setIdNumber(item.getIdNumber());
				PersonnelPoliceRank rank = personnelPoliceRankService.findLastOneByIdNumber(personnelPoliceRank);

				item.setPoliceRank(rank.getPoliceRankLevel());

				PersonnelXueli personnelXueli =new PersonnelXueli();
				personnelXueli.setIdNumber(item.getIdNumber());
				personnelXueli = personnelXueliService.getLastByIdNumber(personnelXueli);
				item.setDegreeEducation(personnelXueli.getName());
			}

		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		//指定下载名字
		response.setHeader("Content-Disposition", "attachment; filename=" +
				Encodes.urlEncode("3.5七知档案.zip"));
		response.addHeader("Pargam", "no-cache");
		response.addHeader("Cache-Control", "no-cache");

		String fileSeperator = File.separator;
		String filePath = Global.getUserfilesBaseDir() + fileSeperator + "userfiles" + fileSeperator + "template" + fileSeperator;

		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ZipOutputStream finalOut = out;

		if (page.getList() != null) {
			page.getList().stream().forEach(item -> {
				Map<String, Object> map = getSevenInfo(item);
				try {
					ExcelTemplate excelTemplate = new ExcelTemplate(filePath + fileName);
					Map<String, String> param = (Map<String, String>) map.get("param");
					int col = excelTemplate.fillVariable(0, param);
					int row = excelTemplate.addRowByExist(0, 14, 14, 15, (LinkedHashMap<Integer, LinkedList<String>>) map.get("rows"), true);
					ZipEntry xlsx = new ZipEntry(item.getName() + "3.5七知档案.xlsx");
					finalOut.putNextEntry(xlsx);
					excelTemplate.getWorkbook().write(finalOut);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {

				}
			});
			/*全部遍历完 再关闭流*/
			try {
				finalOut.flush();
				finalOut.close();
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:" + Global.getAdminPath() + "/affair/affairSevenKnowledge/?repage";
	}

	/**
	 * 查询关联的人员信息
	 *
	 * @param affairSevenKnowledge
	 * @return 简历 奖惩信息
	 */
	public Map<String, String> getRelationInfo(AffairSevenKnowledge affairSevenKnowledge) {

		Map<String, String> result = new HashMap<>();

		/*奖惩信息*/
		PersonnelReward personnelReward = new PersonnelReward();
		personnelReward.setIdNumber(affairSevenKnowledge.getIdNumber());
		List<PersonnelReward> rewardList = personnelRewardService.findList(personnelReward);
		PersonnelPunish personnelPunish = new PersonnelPunish();
		personnelPunish.setIdNumber(affairSevenKnowledge.getIdNumber());
		List<PersonnelPunish> punishList = personnelPunishService.findList(personnelPunish);

		/*简历信息*/
		PersonnelResume personnelResume = new PersonnelResume();
		personnelResume.setIdNumber(affairSevenKnowledge.getIdNumber());
		List<PersonnelResume> resumeList = personnelResumeService.findList(personnelResume);


		StringBuffer resume = new StringBuffer();
		if (resumeList != null && resumeList.size() > 0) {
			resumeList.stream().forEach(item -> {
				resume.append(DateUtils.formatDate(item.getStartDate()));
				resume.append("-");
				resume.append(DateUtils.formatDate(item.getEndDate()));
				resume.append(" ");
				resume.append(item.getUnit());
				resume.append(item.getIdentityJobExplain());
				resume.append("\n");
			});
		}
		StringBuffer rewardPunish = new StringBuffer();
		if (rewardList != null && rewardList.size() > 0) {
			rewardList.stream().forEach(item -> {
				rewardPunish.append(DateUtils.formatDate(item.getApprocalDate()));
				rewardPunish.append(" ");
				rewardPunish.append(item.getName());
				rewardPunish.append(" ");
				rewardPunish.append(item.getFileNo());
				rewardPunish.append("\n");
			});
		}

		if (punishList != null && punishList.size() > 0) {
			punishList.stream().forEach(item -> {
				rewardPunish.append(DateUtils.formatDate(item.getApprovalDate()));
				rewardPunish.append(" ");
				rewardPunish.append(item.getName());
				rewardPunish.append(" ");
				rewardPunish.append(item.getFileNo());
				rewardPunish.append("\n");
			});
		}
		result.put("rewardPunish", rewardPunish.toString());
		result.put("resume", resume.toString());

		return result;
	}

	public Map<String, Object> getSevenInfo(AffairSevenKnowledge affairSevenKnowledge) {

		Map<String, Object> resultMap = new HashMap<>();
		//数据转为map
		Map<String, String> map = null;
		//处理字典以及日期格式
		//java8新加线程安全
//		DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");//代替simpleDateFormat
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat = new SimpleDateFormat();
//		dateFormat.format(simpleDateFormat.format(affairSevenKnowledge.getBirthday()));
		//// FIXME: 2020/7/9 挺啰嗦的
		affairSevenKnowledge.setDrink(DictUtils.getDictLabel(affairSevenKnowledge.getDrink(), "yes_no", ""));
		affairSevenKnowledge.setSex(DictUtils.getDictLabel(affairSevenKnowledge.getSex(), "sex", ""));
		affairSevenKnowledge.setNation(DictUtils.getDictLabel(affairSevenKnowledge.getNation(), "nation", ""));
		affairSevenKnowledge.setPoliticsFace(DictUtils.getDictLabel(affairSevenKnowledge.getPoliticsFace(), "political_status", ""));
		affairSevenKnowledge.setMaritalStatus(DictUtils.getDictLabel(affairSevenKnowledge.getMaritalStatus(), "affair_marital_status", ""));
		affairSevenKnowledge.setStockSpeculation(DictUtils.getDictLabel(affairSevenKnowledge.getStockSpeculation(), "yes_no", ""));
		affairSevenKnowledge.setBuyLottery(DictUtils.getDictLabel(affairSevenKnowledge.getBuyLottery(), "yes_no", ""));
		affairSevenKnowledge.setPracticeSkills(DictUtils.getDictLabel(affairSevenKnowledge.getPracticeSkills(), "yes_no", ""));
		affairSevenKnowledge.setGreedyCup(DictUtils.getDictLabel(affairSevenKnowledge.getGreedyCup(), "yes_no", ""));
		affairSevenKnowledge.setMahjong(DictUtils.getDictLabel(affairSevenKnowledge.getMahjong(), "yes_no", ""));
		affairSevenKnowledge.setHasGun(DictUtils.getDictLabel(affairSevenKnowledge.getHasGun(), "yes_no", ""));
		affairSevenKnowledge.setPoliceRank(DictUtils.getDictLabel(affairSevenKnowledge.getPoliceRank(), "police_rank_level", ""));
		try {
			map = BeanUtils.describe(affairSevenKnowledge);
			//// FIXME: 2020/7/9 挺啰嗦的
			map.put("birthday", affairSevenKnowledge.getBirthday() == null ? "" : simpleDateFormat.format(affairSevenKnowledge.getBirthday()));
			map.put("workTime", affairSevenKnowledge.getWorkTime() == null ? "" : simpleDateFormat.format(affairSevenKnowledge.getWorkTime()));
			map.put("fromPoliceTime", affairSevenKnowledge.getFromPoliceTime() == null ? "" : simpleDateFormat.format(affairSevenKnowledge.getFromPoliceTime()));
			map.put("gunHoldTime", affairSevenKnowledge.getGunHoldTime() == null ? "" : simpleDateFormat.format(affairSevenKnowledge.getGunHoldTime()));
			map.put("driverTime", affairSevenKnowledge.getDriverTime() == null ? "" : simpleDateFormat.format(affairSevenKnowledge.getDriverTime()));
			map.put("policeCarTime", affairSevenKnowledge.getPoliceCarTime() == null ? "" : simpleDateFormat.format(affairSevenKnowledge.getPoliceCarTime()));
			map.put("longPoliceCarTime", affairSevenKnowledge.getLongPoliceCarTime() == null ? "" : simpleDateFormat.format(affairSevenKnowledge.getLongPoliceCarTime()));
			map.put("helpEducateTime", affairSevenKnowledge.getHelpEducateTime() == null ? "" : simpleDateFormat.format(affairSevenKnowledge.getHelpEducateTime()));
			//往模板中写入数据
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		LinkedHashMap<Integer, LinkedList<String>> rows = new LinkedHashMap<>();
		//家庭关系
		// 创建第一个行区域里面填充的值，ExcelTemplate会按从左至右，
		// 从上往下的顺序，挨个填充区域里面的${}，所以创建的时候注意顺序就好
		// 把第一个行区域row1添加进入rows
		PersonnelFamily personnelFamily = new PersonnelFamily();
		personnelFamily.setIdNumber(affairSevenKnowledge.getIdNumber());
		List<PersonnelFamily> personnelFamilies = personnelFamilyService.findList(personnelFamily);
		personnelFamilies.forEach(family -> {
			LinkedList<String> row = new LinkedList<>();
			row.add(family.getName());
			row.add(family.getRelationship());

			row.add(family.getBirthday() == null ? "" : simpleDateFormat.format(family.getBirthday()));
			row.add(family.getUnitNameJob());
			row.add(family.getIdentityJob());
			row.add(DictUtils.getDictLabel(family.getNation(), "nation", ""));
			row.add(family.getEducation());
			row.add(DictUtils.getDictLabel(family.getPoliticsFace(), "political_status", ""));
			row.add(family.getJobLevel());
			row.add(family.getStatus());
			rows.put(personnelFamilies.indexOf(family), row);

		});
		Map<String, String> relationInfo = getRelationInfo(affairSevenKnowledge);
		map.put("curriculumVitae", relationInfo.get("resume"));
		map.put("rewardsPunishments", relationInfo.get("rewardPunish"));
		resultMap.put("param", map);
		resultMap.put("rows", rows);


		return resultMap;
	}

	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
		String idNumber = request.getParameter("idNumber");
		AffairSevenKnowledge affairSevenKnowledge = new AffairSevenKnowledge();
		affairSevenKnowledge.setIdNumber(idNumber);

		try {
			StringBuilder failureMsg = new StringBuilder();
			/*读取文件*/
			ImportExcel ei = new ImportExcel(file, 1, 0);
			int lastRow = ei.getLastDataRowNum();//最后行号
			int lastCellNum = ei.getLastCellNum();//最后列号
			/*遍历导入模板的每一行数据*/
			for (int i = 0; i < lastRow; i++) {
				Row row = ei.getRow(i);
				if (i == 3) {//对应表中第四行
					Object nameValue = ei.getCellValue(row, 2);
					Object IdNumberValue = ei.getCellValue(row, 4);
					/*Object birthdayValue = ei.getCellValue(row, 6);*/
					/*try {
						//日期型
						affairSevenKnowledge.setBirthday(DateUtil.getJavaDate((Double) birthdayValue));//出生年月
					}catch (Exception e){
						//文本型
						affairSevenKnowledge.setBirthday(DateUtils.parseDate(birthdayValue.toString()));//出生年月
					}*/
					/*Object degreeValue = ei.getCellValue(row, 9);
					Object politicsFaceValue = ei.getCellValue(row, 11);*/
					if (nameValue.toString().isEmpty()) {
						throw new RuntimeException("导入文件中姓名不能为空");
					}
					affairSevenKnowledge.setName(nameValue.toString());//姓名
					affairSevenKnowledge.setIdNumber(IdNumberValue.toString());//身份证号
					/*affairSevenKnowledge.setDegreeEducation(degreeValue.toString());//文化程度
					affairSevenKnowledge.setPoliticsFace(politicsFaceValue.toString());//政治面貌*/
				}
				if (i == 4) {//对应表中第五行
					/*Object homeAddressValue = ei.getCellValue(row, 2);*/
					Object houseAreaValue = ei.getCellValue(row, 9);
					Object contactTelValue = ei.getCellValue(row, 11);
					/*affairSevenKnowledge.setHomeAddress(homeAddressValue.toString());//家庭住址*/
					affairSevenKnowledge.setHouseArea(houseAreaValue.toString());//住房面积
					affairSevenKnowledge.setContactTel(contactTelValue.toString());//联系电话
				}
				/*if (i == 5) {//对应表中第6行
					Object workTimeValue = ei.getCellValue(row, 2);//工作时间
					Object fromPoliceTimeValue = ei.getCellValue(row, 6);//从警时间
					Object policeRankValue = ei.getCellValue(row, 9);//警衔
					Object maritalStatusValue = ei.getCellValue(row, 11);//婚姻状态
					//通过try  catch确定excel表格中该值类型，
					try {
						//日期型
						affairSevenKnowledge.setWorkTime(DateUtil.getJavaDate((Double) workTimeValue));
					}catch (Exception e){
						//文本型
						affairSevenKnowledge.setWorkTime(DateUtils.parseDate(workTimeValue.toString()));
					}
					try {
						//日期型
						affairSevenKnowledge.setFromPoliceTime(DateUtil.getJavaDate((Double) fromPoliceTimeValue));
					}catch (Exception e){
						//文本型
						affairSevenKnowledge.setFromPoliceTime(DateUtils.parseDate(fromPoliceTimeValue.toString()));
					}
					affairSevenKnowledge.setPoliceRank(policeRankValue.toString());
					affairSevenKnowledge.setMaritalStatus(maritalStatusValue.toString());

				}*/
				if (i == 6) {//对应表中第7行
					Object healthValue = ei.getCellValue(row, 2);
					Object medicalHistoryValue = ei.getCellValue(row, 6);
					Object mentalIllnessValue = ei.getCellValue(row, 11);

					affairSevenKnowledge.setHealth(healthValue.toString());//健康状况
					affairSevenKnowledge.setMedicalHistory(medicalHistoryValue.toString());//病史
					affairSevenKnowledge.setMentalIllness(mentalIllnessValue.toString());//心里疾病
				}
				/*if (i == 7) {//对应表中第8行

					Object curriculumVitaeValue = ei.getCellValue(row, 2);
					affairSevenKnowledge.setCurriculumVitae(curriculumVitaeValue.toString());//个人简历

				}*/
				/*if (i == 14) {//对应表中第9行

					String name = ei.getCellValue(row, 2).toString();//姓名
					String PersonalRelationship = ei.getCellValue(row, 4).toString();//与本人关系
					String dateBirth = ei.getCellValue(row, 5).toString();//出生年月
					String workUnit = ei.getCellValue(row, 6).toString();//工作单位
					//String job = ei.getCellValue(row, 8).toString();//职务
					String nation = ei.getCellValue(row, 9).toString();//民族
					String education = ei.getCellValue(row, 10).toString();//学历
					String politicsStatus = ei.getCellValue(row, 11).toString();//政治面貌
					String rank = ei.getCellValue(row, 12).toString();//职级
					String status = ei.getCellValue(row, 13).toString();//状态

					//affairSevenKnowledge.setOnlineCourseCredit(onlineCourseCreditValue);
				}*/
				if (i == 15) {//对应表中第16行
					String incomeValue = ei.getCellValue(row, 5).toString();//收入状况

					affairSevenKnowledge.setIncome(incomeValue);
				}
				if (i == 16) {//对应表中第17行
					String keyExpenditure = ei.getCellValue(row, 5).toString();//子女出国、房贷等重点项目支出

					affairSevenKnowledge.setKeyExpenditure(keyExpenditure);

				}
				if (i == 17) {//对应表中第18行
					String businessSituation = ei.getCellValue(row, 5).toString();//直系亲属经商情况

					affairSevenKnowledge.setBusinessSituation(businessSituation);
				}
				if (i == 18) {//对应表中第19行
					String otherHouse = ei.getCellValue(row, 5).toString();//有无其他房产（面积）

					affairSevenKnowledge.setOtherHouse(otherHouse);
				}
				if (i == 19) {//对应表中第20行
					String debtDispute = ei.getCellValue(row, 5).toString();//有无债务纠纷

					affairSevenKnowledge.setDebtDispute(debtDispute);
				}
				if (i == 20) {//对应表中第21行
					String character = ei.getCellValue(row, 2).toString();//性格
					String specialty = ei.getCellValue(row, 7).toString();//特长
					String hobbies = ei.getCellValue(row, 12).toString();//业余爱好
					affairSevenKnowledge.setCharacter(character);
					affairSevenKnowledge.setSpecialty(specialty);
					affairSevenKnowledge.setHobbies(hobbies);
				}
				if (i == 21) {//对应表中第22行
					String workJob = ei.getCellValue(row, 2).toString();//工作岗位
					String goodBusiness = ei.getCellValue(row, 7).toString();//擅长业务
					String insufficientBusiness = ei.getCellValue(row, 12).toString();//业务不足
					affairSevenKnowledge.setWorkJob(workJob);
					affairSevenKnowledge.setGoodBusiness(goodBusiness);
					affairSevenKnowledge.setInsufficientBusiness(insufficientBusiness);
				}
				if (i == 22) {//对应表中第23行
					String isstock = "";
					String isbuyLottery = "";
					String ispracticeSkills = "";
					String stockSpeculation = ei.getCellValue(row, 3).toString();//是否炒股（买基金等）
					if("是".equals(stockSpeculation)){
						isstock = "1";
					}else if("否".equals(stockSpeculation)){
						isstock = "0";
					}
					String buyLottery = ei.getCellValue(row, 7).toString();//是否常买彩票
					if("是".equals(buyLottery)){
						isbuyLottery = "1";
					}else if("否".equals(buyLottery)){
						isbuyLottery = "0";
					}
					String practiceSkills = ei.getCellValue(row, 12).toString();//是否习练功法
					if("是".equals(practiceSkills)){
						ispracticeSkills = "1";
					}else if("否".equals(practiceSkills)){
						ispracticeSkills = "0";
					}
					affairSevenKnowledge.setStockSpeculation(isstock);
					affairSevenKnowledge.setBuyLottery(isbuyLottery);
					affairSevenKnowledge.setPracticeSkills(ispracticeSkills);
				}
				if (i == 23) {//对应表中第24行
					String familyHarmony = ei.getCellValue(row, 3).toString();//家庭和睦情况
					String neighborhoodRelations = ei.getCellValue(row, 7).toString();//邻里关系
					String colleagueRelations = ei.getCellValue(row, 12).toString();//同事关系
					affairSevenKnowledge.setFamilyHarmony(familyHarmony);
					affairSevenKnowledge.setNeighborhoodRelations(neighborhoodRelations);
					affairSevenKnowledge.setColleagueRelations(colleagueRelations);
				}
				/*if (i == 24) {//对应表中第25行
					String rewardsPunishments = ei.getCellValue(row, 2).toString();//近期奖惩情况

					affairSevenKnowledge.setRewardsPunishments(rewardsPunishments);
				}*/
				if (i == 27) {//对应表中第28行
					Object gunHoldTime = ei.getCellValue(row, 2).toString();//获持枪资格时间

					String isHasGun = "";
					String hasGun = ei.getCellValue(row, 9).toString();//岗位是否配枪

					if("是".equals(hasGun)){
						isHasGun = "1";
					}else if("否".equals(hasGun)){
						isHasGun = "0";
					}
					//通过try  catch确定excel表格中该值类型，
					try {
						//日期型
						affairSevenKnowledge.setGunHoldTime(DateUtil.getJavaDate((Double) gunHoldTime));
					}catch (Exception e){
						//文本型
						affairSevenKnowledge.setGunHoldTime(DateUtils.parseDate(gunHoldTime.toString()));
					}
					affairSevenKnowledge.setHasGun(isHasGun);
				}
				if (i == 28) {//对应表中第29行
					String gunSystem = ei.getCellValue(row, 5).toString();//配枪制度落实情况有无酒后失控等情况

					affairSevenKnowledge.setGunSystem(gunSystem);
				}
				if (i == 29) {//对应表中第30行
					String isDrink = "";
					String isGreedyCup = "";
					String drink = ei.getCellValue(row, 5).toString();//是否喝酒
					String greedyCup = ei.getCellValue(row, 9).toString();//是否贪杯
					if("是".equals(drink)){
						isDrink = "1";
					}else if("否".equals(drink)){
						isDrink = "0";
					}
					if("是".equals(greedyCup)){
						isGreedyCup = "1";
					}else if("否".equals(greedyCup)){
						isGreedyCup = "0";
					}
					affairSevenKnowledge.setDrink(isDrink);
					affairSevenKnowledge.setGreedyCup(isGreedyCup);
				}

				if (i == 30) {//对应表中第31行
					String outControlDrink = ei.getCellValue(row, 5).toString();//有无酒后失控等情况

					affairSevenKnowledge.setOutControlDrink(outControlDrink);
				}
				if (i == 31) {//对应表中第32行
					String driverLicenseType = ei.getCellValue(row, 3).toString();//驾驶证类型
					String driverAgeCar = ei.getCellValue(row, 7).toString();//驾龄-汽车
					Object policeCarTime = ei.getCellValue(row, 10).toString();//获准警车驾驶时间
					//String colleagueRelations = ei.getCellValue(row,13).toString();//私家车类型

					//通过try  catch确定excel表格中该值类型，
					try {
						//日期型
						affairSevenKnowledge.setPoliceCarTime(DateUtil.getJavaDate((Double) policeCarTime));
					}catch (Exception e){
						//文本型
						affairSevenKnowledge.setPoliceCarTime(DateUtils.parseDate(policeCarTime.toString()));
					}

					affairSevenKnowledge.setFamilyHarmony(driverLicenseType);
					affairSevenKnowledge.setFamilyHarmony(driverAgeCar);
					//affairSevenKnowledge.setColleagueRelations(colleagueRelations);
				}
				if (i == 32) {//对应表中第33行
					Object driverTime = ei.getCellValue(row, 3).toString();//获证时间
					String driverAgeMotorcycle = ei.getCellValue(row, 7).toString();//驾龄-摩托车
					Object longPoliceCarTime = ei.getCellValue(row, 10).toString();//获准长途驾驶警车时间
					String safeDriver = ei.getCellValue(row,13).toString();//安全行车记录
					try {
						//日期型
						affairSevenKnowledge.setDriverTime(DateUtil.getJavaDate((Double) driverTime));
					}catch (Exception e){
						//文本型
						affairSevenKnowledge.setDriverTime(DateUtils.parseDate(driverTime.toString()));
					}
					try {
						//日期型
						affairSevenKnowledge.setLongPoliceCarTime(DateUtil.getJavaDate((Double) longPoliceCarTime));
					}catch (Exception e){
						//文本型
						affairSevenKnowledge.setLongPoliceCarTime(DateUtils.parseDate(longPoliceCarTime.toString()));
					}

					affairSevenKnowledge.setDriverAgeMotorcycle(driverAgeMotorcycle);
					affairSevenKnowledge.setSafeDriver(safeDriver);
				}
				if (i == 33) {//对应表中第34行
					String ismahjong = "";
					String mahjong = ei.getCellValue(row, 4).toString();//是否爱打麻将
					if("是".equals(mahjong)){
						ismahjong = "1";
					}else if("否".equals(mahjong)){
						ismahjong = "0";
					}
					String gambling = ei.getCellValue(row, 9).toString();//有无赌博现象

					affairSevenKnowledge.setMahjong(ismahjong);
					affairSevenKnowledge.setGambling(gambling);
				}
				if (i == 34) {//对应表中第35行
					String betOther = ei.getCellValue(row, 3).toString();//其他反应

					affairSevenKnowledge.setBetOther(betOther);
				}
				if (i == 35) {//对应表中第36行
					String familyMisfortune = ei.getCellValue(row, 3).toString();//近期个人、家庭重大变故

					affairSevenKnowledge.setFamilyHarmony(familyMisfortune);
							}
				if (i == 36) {//对应表中第37行
					String illegalOrganization = ei.getCellValue(row, 3).toString();//家属有无参与非法组织

					affairSevenKnowledge.setIllegalOrganization(illegalOrganization);
				}
				if (i == 37) {//对应表中第38行
					String badAssociation = ei.getCellValue(row, 3).toString();//有无社会不良交往

					affairSevenKnowledge.setBadAssociation(badAssociation);
				}
				if (i == 38) {//对应表中第39行
					String addictedInternet = ei.getCellValue(row, 3).toString();//是否沉迷网络

					affairSevenKnowledge.setAddictedInternet(addictedInternet);
				}
				if (i == 39) {//对应表中第40行
					String luxuryPlaces = ei.getCellValue(row, 3).toString();//是否经常出入高消费场所

					affairSevenKnowledge.setLuxuryPlaces(luxuryPlaces);
				}

				if (i == 40) {//对应表中第41行
					String participationBusiness = ei.getCellValue(row, 3).toString();//有无参股经商反映

					affairSevenKnowledge.setParticipationBusiness(participationBusiness);
				}

				if (i == 41) {//对应表中第42行
					String crruption = ei.getCellValue(row, 3).toString();//以权谋私不良反映

					affairSevenKnowledge.setCrruption(crruption);
				}

				if (i == 42) {//对应表中第43行
					String otherAdverseReactions = ei.getCellValue(row, 3).toString();//有无其他不良反映

					affairSevenKnowledge.setOtherAdverseReactions(otherAdverseReactions);
				}

				if (i == 43) {//对应表中第44行
					String offDuty = ei.getCellValue(row, 3).toString();//有无脱岗离岗记录

					affairSevenKnowledge.setOffDuty(offDuty);
				}

				if (i == 44) {//对应表中第45行
					String massComplaints = ei.getCellValue(row, 3).toString();//有无群众投诉

					affairSevenKnowledge.setMassComplaints(massComplaints);
				}
				if (i == 45) {//对应表中第46行
					String disciplining = ei.getCellValue(row, 3).toString();//有无上级督察训导记录

					affairSevenKnowledge.setDisciplining(disciplining);
				}
				if (i == 46) {//对应表中第47行
					Object helpEducateTime = ei.getCellValue(row, 3).toString();//列为重点帮教对象时间
					String suoLeader = ei.getCellValue(row, 8).toString();//包保所领导
					String chuLeader = ei.getCellValue(row,12).toString();//包保处领导

					try {
						//日期型
						affairSevenKnowledge.setHelpEducateTime(DateUtil.getJavaDate((Double) helpEducateTime));
					}catch (Exception e){
						//文本型
						affairSevenKnowledge.setHelpEducateTime(DateUtils.parseDate(helpEducateTime.toString()));
					}

					affairSevenKnowledge.setSuoLeader(suoLeader);
					affairSevenKnowledge.setChuLeader(chuLeader);
				}
			}
			int count = affairSevenKnowledgeService.selByIdNumber(idNumber);//查询数据库该人员的七知档案是否存在
			if (count > 0) {
				affairSevenKnowledgeService.deleteByIdNumber(idNumber);
			}
			User user = UserUtils.getUser();
			affairSevenKnowledge.setCreateOrgId(user.getOffice().getId());
			affairSevenKnowledge.setUpdateOrgId(user.getOffice().getId());
			affairSevenKnowledgeService.save(affairSevenKnowledge);
			addMessage(redirectAttributes, affairSevenKnowledge.getName() + "的七知档案已成功导入");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}

		return "redirect:" + adminPath + "/file/template/download/view?repage";

	}

	@RequestMapping(value = "openSevenKnowledgeDetail")
	public String openSevenKnowledgeDetail(AffairSevenKnowledge affairSevenKnowledge,  HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AffairSevenKnowledge> page = affairSevenKnowledgeService.findSevenKnowledgeChartList(new Page<AffairSevenKnowledge>(request, response), affairSevenKnowledge);
		model.addAttribute("page", page);
		return "modules/charts/chartSevenKnowLedgeList";
	}

}