package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairTuixiu;
import com.thinkgem.jeesite.modules.affair.service.AffairLaborOfficeService;
import com.thinkgem.jeesite.modules.affair.service.AffairTuixiuService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 退休Controller
 * @author cecil.li
 * @version 2020-07-02
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairTuixiu")
public class AffairTuixiuController extends BaseController {

	@Autowired
	private AffairTuixiuService affairTuixiuService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private WarningService warningService;

	@Autowired
	private AffairLaborOfficeService affairLaborOfficeService;
	
	@ModelAttribute
	public AffairTuixiu get(@RequestParam(required=false) String id) {
		AffairTuixiu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = affairTuixiuService.get(id);
		}
		if (entity == null){
			entity = new AffairTuixiu();
		}
		return entity;
	}
	
	@RequiresPermissions("affair:affairTuixiu:view")
	@RequestMapping(value = {"list", ""})
	public String list(AffairTuixiu affairTuixiu, HttpServletRequest request, HttpServletResponse response, Model model, PersonnelBase personnelBase) {
		/*Page<AffairTuixiu> page = new Page<>();
		page.setList(list);*/
		if (!StringUtils.isNotBlank(affairTuixiu.getTabFlag()) ){
//			affairTuixiu.setTabFlag(UserUtils.getUser().getCompany().getId());
			if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
				affairTuixiu.setTabFlag(UserUtils.getUser().getCompany().getId());
			}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
				affairTuixiu.setTabFlag("777");
			}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
				affairTuixiu.setTabFlag("888");
			}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
				affairTuixiu.setTabFlag("999");
			}
			else {
				affairTuixiu.setTabFlag(UserUtils.getUser().getOffice().getId());
			}
		}
		Page<AffairTuixiu> page = affairTuixiuService.findPage(new Page<AffairTuixiu>(request, response), affairTuixiu);
		model.addAttribute("page", page);
		return "modules/affair/affairTuixiuList";
	}

	@RequiresPermissions("affair:affairTuixiu:edit")
	@RequestMapping(value = "shengcheng")
	public String shengCheng(){
		List<Map<String, String>> tuiList = affairTuixiuService.tuiList();
		List<String> allId = affairTuixiuService.findAllId();
		for (int i = 0; i < tuiList.size(); i++) {
			if (-1 == allId.indexOf(tuiList.get(i).get("id_number"))){
				AffairTuixiu affairTuixiu1 = new AffairTuixiu();
				affairTuixiu1.setUnitName(tuiList.get(i).get("workunit_name"));
				affairTuixiu1.setUnitNameId(tuiList.get(i).get("workunit_id"));
				affairTuixiu1.setSex(tuiList.get(i).get("sex"));
				affairTuixiu1.setName(tuiList.get(i).get("name"));
				affairTuixiu1.setIdNumber(tuiList.get(i).get("id_number"));
				String birthday = String.valueOf(tuiList.get(i).get("birthday"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date birth = null;
				try {
					birth = sdf.parse(birthday);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairTuixiu1.setBirth(birth);
				affairTuixiu1.setNation(DictUtils.getDictLabel(tuiList.get(i).get("nation"),"nation",""));
				affairTuixiu1.setHometown(tuiList.get(i).get("native_place"));
				String word_date = String.valueOf(tuiList.get(i).get("work_date"));
				Date workDate = null;
				try {
					workDate = sdf.parse(word_date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				affairTuixiu1.setWorkDate(workDate);
				Integer yearCopy = null;
				Integer tuiYearCopy = null;
				Integer monthCopy = null;
				Integer dayCopy = null;
				if("1".equals(affairTuixiu1.getSex())){
					String[] arr = birthday.split("-");
					int year = Integer.valueOf(arr[0]);
					int tuiYeat = year + 60;
					int month = Integer.parseInt(arr[1]);
					int day = Integer.parseInt(arr[2]);
//			Date date = new Date(tuiYeat, month, day);
					Calendar c =Calendar.getInstance();
					c.set(tuiYeat, month, day);
					Date de = c.getTime();
					affairTuixiu1.setRetirementTime(de);
					yearCopy = year;
					tuiYearCopy = tuiYeat;
					monthCopy = month;
					dayCopy = day;
				}else {
					String[] arr = birthday.split("-");
					int year = Integer.valueOf(arr[0]);
					int tuiYeat = year + 55;
					int month = Integer.parseInt(arr[1]);
					int day = Integer.parseInt(arr[2]);
//			Date date = new Date(tuiYeat, month, day);
					Calendar c =Calendar.getInstance();
					c.set(tuiYeat, month, day);
					Date de = c.getTime();
					affairTuixiu1.setRetirementTime(de);
					yearCopy = year;
					tuiYearCopy = tuiYeat;
					monthCopy = month;
					dayCopy = day;
				}

				affairTuixiu1.setRetirementJob(tuiList.get(i).get("job_abbreviation"));
				//把即将退休的人添加到预警中
				Date nowDate = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String nowTime = df.format(nowDate);
				String[] arr1 = nowTime.split("-");
				int nowYear = Integer.valueOf(arr1[0]);
				int nowMonth = Integer.parseInt(arr1[1])-1;
				if ("1".equals(affairTuixiu1.getSex())){
					if (nowYear - yearCopy == 60){
						Warning warning = new Warning();
						warning.setName(affairTuixiu1.getUnitName()+"单位下"+ affairTuixiu1.getName()+"即将要退休");
						warning.setReceivePerName("劳资信息管理,"+affairTuixiu1.getUnitName());
						warning.setReceivePerId("a58a91c7d4db4cd4b639c863c0e48832,"+officeService.findUserByName(affairTuixiu1.getUnitName()));
				/*warning.setReceivePerName(affairTuixiu1.getUnitName());
				warning.setReceivePerId(officeService.findUserByName(affairTuixiu1.getUnitName()));*/
						warning.setRepeatCycle("4");
						Calendar c2 =Calendar.getInstance();
						c2.set(tuiYearCopy, monthCopy-2, dayCopy);
						Date de2 = c2.getTime();
						warning.setDate(de2);
						warning.setRemind("5");
						warning.setIsAlert("1");
						warning.setContinueDay("0");
						warning.setAlertDegree("1");
						warning.setAlertContent(affairTuixiu1.getUnitName()+"单位下"+ affairTuixiu1.getName()+"即将要退休");
						warningService.save(warning);
					}
				}else {
					if (nowYear - yearCopy == 55) {
						Warning warning = new Warning();
						warning.setName(affairTuixiu1.getUnitName() + "单位下" + affairTuixiu1.getName() + "即将要退休");
						warning.setReceivePerName("劳资信息管理," + affairTuixiu1.getUnitName());
						warning.setReceivePerId("a58a91c7d4db4cd4b639c863c0e48832," + officeService.findUserByName(affairTuixiu1.getUnitName()));
				/*warning.setReceivePerName(affairTuixiu1.getUnitName());
				warning.setReceivePerId(officeService.findUserByName(affairTuixiu1.getUnitName()));*/
						warning.setRepeatCycle("4");
						Calendar c2 =Calendar.getInstance();
						c2.set(tuiYearCopy, monthCopy-2, dayCopy);
						Date de2 = c2.getTime();
						warning.setDate(de2);
						warning.setRemind("5");
						warning.setIsAlert("1");
						warning.setContinueDay("0");
						warning.setAlertDegree("1");
						warning.setAlertContent(affairTuixiu1.getUnitName()+"单位下"+ affairTuixiu1.getName()+"即将要退休");
						warningService.save(warning);
					}
				}
				affairTuixiuService.save(affairTuixiu1);
			}

		}
		return "redirect:"+Global.getAdminPath()+"/affair/affairTuixiu/";
	}


	@RequiresPermissions("affair:affairTuixiu:view")
	@RequestMapping(value = "form")
	public String form(AffairTuixiu affairTuixiu, Model model) {
		model.addAttribute("affairTuixiu", affairTuixiu);
		return "modules/affair/affairTuixiuForm";
	}

	@RequiresPermissions("affair:affairTuixiu:edit")
	@RequestMapping(value = "save")
	public String save(AffairTuixiu affairTuixiu, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, affairTuixiu)){
			return form(affairTuixiu, model);
		}
		affairTuixiuService.save(affairTuixiu);
		addMessage(redirectAttributes, "保存退休成功");
		request.setAttribute("saveResult","sucess");
		return "modules/affair/affairTuixiuForm";
	}
	@RequiresPermissions("affair:affairTuixiu:edit")
	@RequestMapping(value = "queDing")
	public String queDing(String id){
		affairTuixiuService.queDing(id);
		return "redirect:"+Global.getAdminPath()+"/affair/affairTuixiu/";
	}
	
	@RequiresPermissions("affair:affairTuixiu:edit")
	@RequestMapping(value = "delete")
	public String delete(AffairTuixiu affairTuixiu, RedirectAttributes redirectAttributes) {
		affairTuixiuService.delete(affairTuixiu);
		addMessage(redirectAttributes, "删除退休成功");
		return "redirect:"+Global.getAdminPath()+"/affair/affairTuixiu/?repage";
	}

	@ResponseBody
	@RequiresPermissions("affair:affairTuixiu:edit")
	@RequestMapping(value = {"deleteByIds"})
	public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
		Result result = new Result();
		if(ids != null && ids.size() > 0){
			affairTuixiuService.deleteByIds(ids);
			result.setSuccess(true);
			result.setMessage("删除成功");
		}else{
			result.setSuccess(false);
			result.setMessage("请先选择要删除的内容");
		}
		return result;
	}

	/**
	 * 导出excel格式数据
	 * @param
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportExcelByTemplate(AffairTuixiu affairTuixiu, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, boolean flag) {
		XSSFWorkbook wb = null;
		try
		{
			affairTuixiu.setCreateBy(UserUtils.getUser());
			String fileName = "";
			if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
				fileName= request.getParameter("fileName");
			}
			affairTuixiu.setTabFlag(UserUtils.getUser().getCompany().getId());
			Page<AffairTuixiu> page = null;
			if(flag == true){
				page = affairTuixiuService.findPage(new Page<AffairTuixiu>(request, response), affairTuixiu);
			}else{
				page = affairTuixiuService.findPage(new Page<AffairTuixiu>(request, response,-1), affairTuixiu);
			}

			String fileSeperator = File.separator;
			String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
			InputStream inputStream = new FileInputStream(filePath+fileName);
			if (null != inputStream)
			{
				try
				{
					wb = new  XSSFWorkbook(inputStream);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairTuixiu.class);
			exportExcelNew.setWb(wb);
			List<AffairTuixiu> list =page.getList();
			exportExcelNew.setDataList(list);
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			ServletOutputStream fout = response.getOutputStream();
			wb.write(fout);
			fout.close();
			return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			addMessage(redirectAttributes, "导出用户失败！失败信息："+ex);
		}
		return "redirect:" + adminPath + "/affair/affairNews/?repage";
	}


	@RequestMapping(value = "import", method=RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<AffairTuixiu> list = ei.getDataList(AffairTuixiu.class);
			for (AffairTuixiu affairTuixiu : list){
				try{
					affairTuixiu.setUnitId(affairLaborOfficeService.findByName(affairTuixiu.getUnit()));
					affairTuixiu.setUnitNameId(affairLaborOfficeService.findByName(affairTuixiu.getUnitName()));
					BeanValidators.validateWithException(validator, affairTuixiu);
					affairTuixiuService.save(affairTuixiu);
					successNum++;
				}catch(ConstraintViolationException ex){
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append(" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		redirectAttributes.addFlashAttribute("result","success");
		return "redirect:" + adminPath + "/file/template/download/view";
	}

}