package com.geekcattle.controller.kjk;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.conf.KjkEnum;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.model.kjk.KjkPlayType;
import com.geekcattle.model.kjk.NcmeExpert;
import com.geekcattle.service.console.LogService;
import com.geekcattle.service.kjk.NcmeExpertService;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.IpUtil;
import com.geekcattle.util.ReturnUtil;
import com.github.pagehelper.PageInfo;

/**
 * @author yuguoliang
 *
 */
@Controller
@RequestMapping("/console/kjk")
public class NcmeExpertController {
	private final static Logger logger = LoggerFactory.getLogger(NcmeExpertController.class);
	@Autowired
	private NcmeExpertService ncmeExpertService;
	@Autowired
	private LogService logService;

	/**
	 * 跳转页面
	 * 
	 * @param model
	 * @param moduleCode
	 * @return
	 */
	@RequiresPermissions("ncmeExpert:index")
	@RequestMapping(value = "/ncmeExpert/index", method = { RequestMethod.GET })
	public String indexNcmeExpert(Model model, String moduleCode) {
		return "console/kjk/indexNcmeExpert";
	}

	/**
	 * 专家列表
	 * 
	 * @param ncmeExpert
	 * @return
	 */
	@RequiresPermissions("ncmeExpert:index")
	@RequestMapping(value = "ncmeExpert/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listNcmeExpert(NcmeExpert ncmeExpert) {
		ModelMap map = new ModelMap();
		List<NcmeExpert> lists = ncmeExpertService.getNcmeExpertPageList(ncmeExpert);
		map.put("pageInfo", new PageInfo<NcmeExpert>(lists));
		map.put("queryParam", ncmeExpert);
		return ReturnUtil.Success("加载成功", map, null);
	}

	@RequiresPermissions("ncmeExpert:edit")
	@RequestMapping(value = "/ncmeExpert/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap delete(String expIds, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(expIds)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				StringBuffer sbf = new StringBuffer();
				NcmeExpert ncmeExpert = new NcmeExpert();
				String[] ids = expIds.split(",");
				for (String id : ids) {
					ncmeExpert.setExpId(String.valueOf(id));
					ncmeExpertService.deleteNcmeExpert(ncmeExpert);
					sbf.append(id).append(",");
				}
				// 记录删除专家日志
				String ip = IpUtil.getIpAddr(request);
				Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
				logService.insertLoginLog(admin.getUsername(), ip,
						"删除专家" + sbf.toString().substring(0, sbf.toString().length() - 1));
				return ReturnUtil.Success("1", null, null);
			}

		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("0", null, null);
		}
	}

	@RequiresPermissions("ncmeExpert:edit")
	@RequestMapping(value = "/ncmeExpert/edit", method = { RequestMethod.GET })
	public String from(NcmeExpert ncmeExpert, Model model) {
		if (ncmeExpert.getExpId() != null) {
			ncmeExpert = ncmeExpertService.getById(ncmeExpert.getExpId());
		}
		model.addAttribute("info", ncmeExpert);
		return "console/kjk/fromNcmeExpertEdit";
	}
	
	@RequiresPermissions("ncmeExpert:edit")
	@RequestMapping(value = "/ncmeExpert/edit", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap add(NcmeExpert ncmeExpert, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
			String str="";
			if (StringUtils.isEmpty(ncmeExpert.getExpName()))
				return ReturnUtil.Error("专家姓名不能为空", null, null);
			if (ncmeExpert.getExpId() == null && ncmeExpertService.getListByName(ncmeExpert.getIdCard()).size() > 0)
				return ReturnUtil.Error("省份证号已存在", null, null);
			if (ncmeExpert.getExpId() != null) {
				str="修改专家-"+ncmeExpert.getExpName()+" idCard-"+ncmeExpert.getIdCard();
				ncmeExpert.setUpdateDate(DateUtil.getSysTime());
				ncmeExpertService.update(ncmeExpert);
				return ReturnUtil.Success("操作成功", 1, null);
			} else {
				str="添加专家-"+ncmeExpert.getExpName()+" idCard-"+ncmeExpert.getIdCard();
				ncmeExpert.setAddDate(DateUtil.getSysTime());
				ncmeExpertService.insert(ncmeExpert);
			}
			String ip = IpUtil.getIpAddr(request);
			logService.insertLoginLog(admin.getUsername(), ip,str);
			return ReturnUtil.Success("操作成功", 2, null);
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
}
