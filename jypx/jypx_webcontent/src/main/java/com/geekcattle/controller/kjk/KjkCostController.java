package com.geekcattle.controller.kjk;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.kjk.KjkCost;
import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.model.kjk.KjkDic;
import com.geekcattle.model.kjk.NcmeExpert;
import com.geekcattle.service.console.LogService;
import com.geekcattle.service.kjk.KjkCostService;
import com.geekcattle.service.kjk.KjkDicService;
import com.geekcattle.service.kjk.KjkService;
import com.geekcattle.service.kjk.NcmeExpertService;
import com.geekcattle.util.CardCodeVerify;
import com.geekcattle.util.IpUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UuidUtil;
import com.geekcattle.util.console.ExcelOperate;
import com.geekcattle.vo.kjk.KjkCostVo;
import com.github.pagehelper.PageInfo;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

@Controller
@RequestMapping("/console/kjk/cost")
public class KjkCostController {
	private final static Logger logger = LoggerFactory.getLogger(KjkCostController.class);
	
	@Autowired
	private NcmeExpertService ncmeExpertService;
	@Autowired
	private KjkDicService kjkDicService;
	@Autowired
	private KjkCostService kjkCostService;
	@Autowired
	private KjkService kjkService;
	@Autowired
	private LogService logService;
	
	
	/*** kjkCost begin…… ***/
	@RequiresPermissions("kjkCost:index")
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String indexCourseware(Model model, String playFlag) {
		//归属项目类型集合
		List<KjkDic> kjkDicList = kjkDicService.findDicByType(ConstantEnum.KJK_DIC_TYPE_SYSTEM.toString());
		model.addAttribute("kjkDicList", kjkDicList);
		return "console/kjk/indexKjkCost";
	}

	@RequiresPermissions("kjkCost:index")
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listCourseware(KjkCost kjkCost) {
		ModelMap map = new ModelMap();
		List<KjkCost> lists = kjkCostService.getPageList(kjkCost);
		map.put("pageInfo", new PageInfo<KjkCost>(lists));
		map.put("queryParam", kjkCost);
		return ReturnUtil.Success("加载成功", map, null);
	}
	
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/edit", method = { RequestMethod.GET })
	public String from(KjkCost kjkCost,String id, Model model) {
		List<KjkDic> kjkDicList = kjkDicService.findDicByType(ConstantEnum.KJK_DIC_TYPE_SYSTEM.toString());
		model.addAttribute("cwId",Long.valueOf(id));
		model.addAttribute("kjkDicList", kjkDicList);
		return "console/kjk/fromKjkCostEdit";
	}
	
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/aduit", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap aduit(String id, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(id)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				
				KjkCost kjkCost = new KjkCost();
				kjkCost.setCostId(id);
				kjkCost.setAuditStatus(ConstantEnum.KJK_COST_AUDIT_STATUS_IS.toString());
				kjkCost.setPayTime(new Date());
				kjkCostService.updateKjkCost(kjkCost);
				
				// 记录审核劳务费日志
				String ip = IpUtil.getIpAddr(request);
				Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
				logService.insertLoginLog(admin.getUsername(), ip,"审核劳务费id" + id+"通过");
				return ReturnUtil.Success("1", null, null);
			}

		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("0", null, null);
		}
	}
	
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/batchAduit", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap batchAudit(String costIds, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(costIds)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				
				String[] ids = costIds.split(",");
				List<String> arrayIds = Arrays.asList(ids);
				kjkCostService.batchAudit(arrayIds);
				
				// 记录审核劳务费日志
				String ip = IpUtil.getIpAddr(request);
				Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
				logService.insertLoginLog(admin.getUsername(), ip,"审核劳务费id" + arrayIds.toString()+"通过");
				return ReturnUtil.Success("1", null, null);
			}

		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("0", null, null);
		}
	}
	
	/**
	 * 添加劳务费
	 * @param kjkCost
	 * @param mobile
	 * @param idCard
	 * @param result
	 * @param request
	 * @return
	 */
	@Transactional
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap save(@Valid KjkCost kjkCost,BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
			//验证非空begin========================
			if (kjkCost.getIdCard().length() == 15 || kjkCost.getIdCard().length() == 18) {
			    if (!CardCodeVerify.cardCodeVerifySimple(kjkCost.getIdCard())) {
			        return ReturnUtil.Error("15位或18位身份证号码不正确", null, null);
			    } else {
			        if (kjkCost.getIdCard().length() == 18 && !CardCodeVerify.cardCodeVerify(kjkCost.getIdCard()))
			            return ReturnUtil.Error("18位身份证号码不符合国家规范", null, null);
			    }
			} else {
				return ReturnUtil.Error("身份证号码长度必须等于15或18位", null, null);
			}
			//验证非空end========================
			
			//操作专家表begin=================
			List<NcmeExpert> ncmeExpertList = ncmeExpertService.getListByName(kjkCost.getIdCard());
			if(null!=ncmeExpertList && ncmeExpertList.size()>0){
				//修改专家银行卡号
				NcmeExpert ncmeExpert = ncmeExpertList.get(0);
				ncmeExpert.setBankCard(kjkCost.getCardNo());
				ncmeExpert.setBankName(kjkCost.getOpeningBank().trim());
				ncmeExpert.setUpdateDate(new Date());
				ncmeExpert.setIdCard(kjkCost.getIdCard());
				ncmeExpert.setExpName(kjkCost.getExpertName().trim());
				//手机号如果存并且不相等在则追加，不存在则添加
				ncmeExpert.setMobile(StringUtils.isEmpty(ncmeExpert.getMobile())?kjkCost.getMobile():ncmeExpert.getMobile().equals(kjkCost.getMobile())?ncmeExpert.getMobile():ncmeExpert.getMobile()+","+kjkCost.getMobile());
				ncmeExpertService.update(ncmeExpert);
				
				kjkCost.setExpertId(ncmeExpert.getExpId());
			}else{
				//新增专家信息
				NcmeExpert ncmeExpert = new NcmeExpert(kjkCost.getExpertName(),kjkCost.getMobile(), kjkCost.getIdCard(), kjkCost.getOpeningBank(), kjkCost.getCardNo(), new Date());
				ncmeExpertService.insert(ncmeExpert);
				kjkCost.setExpertId(ncmeExpert.getExpId());
			}
			//操作专家表end=================
			
			//修改课件库表的付费状态begin================
			
			KjkCourseware kjkCourseware = new KjkCourseware();
			kjkCourseware.setId(kjkCost.getCwId().longValue());
			kjkCourseware.setPlayFlag(ConstantEnum.KJK_COURSEWARE_PLY_FLAG_IS.toString());
			kjkService.updateCourseCware(kjkCourseware);
			//修改课件库表的付费状态end================
			
			//劳务费属性设置
			KjkCourseware courseware = kjkService.getById(kjkCost.getCwId().longValue());
			kjkCost.setCwareName(courseware.getName());
			kjkCost.setCostId(UuidUtil.getUUID());//主键id
			kjkCost.setAuditStatus(ConstantEnum.KJK_COST_AUDIT_STATUS_NOT.toString());
			kjkCost.setOperator(admin.getUsername());
			kjkCost.setStatus(ConstantEnum.KJK_COST_STATUS_ENABLE.toString());
			kjkCostService.insertKjkCost(kjkCost);
			
			
			//记录添加劳务费日志
			String ip = IpUtil.getIpAddr(request);
			logService.insertLoginLog(admin.getUsername(), ip,"课件id:" + kjkCost.getCwId()+"添加劳务费"+kjkCost.getCost());
			return ReturnUtil.Success("操作成功", 1, null);
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
	
	@RequiresPermissions("courseware:download")
	@RequestMapping("/download")
	public void downloadfile(KjkCost kjkCost, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<KjkCostVo> list = kjkCostService.getExcelList(kjkCost);
		ExportParams params = new ExportParams(ConstantEnum.DOWNLOAD_COST_TITLENAME.toString(),
				ConstantEnum.DOWNLOAD_COST_SHEETNAME.toString(), ExcelType.XSSF);
		model.put(NormalExcelConstants.DATA_LIST, list); // 数据集合
		model.put(NormalExcelConstants.CLASS, KjkCostVo.class);// 导出实体
		model.put(NormalExcelConstants.PARAMS, params);// 参数
		model.put(NormalExcelConstants.FILE_NAME, ConstantEnum.DOWNLOAD_COST_FILENAME.toString());// 文件名称
		ExcelOperate.renderMergedOutputModel(model, request, response);
	}

}
