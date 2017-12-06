package com.geekcattle.controller.console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.geekcattle.conf.ModuleEnum;
import com.geekcattle.model.cms.Info;
import com.geekcattle.model.console.Admin;
import com.geekcattle.service.cms.ScienceEducationService;
import com.geekcattle.service.cms.UserDistrictService;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UploadUtil;
import com.geekcattle.util.UuidUtil;
import com.geekcattle.util.console.DistrictTreeUtil;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/console/business")
public class BusinessController {
	private final static Logger logger = LoggerFactory.getLogger(BusinessController.class);	
	@Value("${spring.upload.path}")
	private String uploadPath;
	@Autowired
	private ScienceEducationService scienceEducationService;	
	@Autowired
	private UserDistrictService userDistrictService;
	
	private final String NOTICE = "NOTICE";
	private final String POLICY = "POLICY";
	private final static Map<String,String> COURSE_TYPE = new HashMap<String, String>() {
		 {
		     put(ModuleEnum.COURSE_ABROAD.toString(), ModuleEnum.COURSE_ABROAD_VIEW.toString());
		     put(ModuleEnum.COURSE_CME.toString(), ModuleEnum.COURSE_CME_VIEW.toString());
		 }
	};
	private final static Map<String, String> EXAM_NOTICE_TYPE = new HashMap<String, String>() {
	    {
	        put(ModuleEnum.NURSE_EXAM_NOTICE.toString(), ModuleEnum.NURSE_EXAM_VIEW.toString());
	        put(ModuleEnum.DOCTOR__EXAM_NOTICE.toString(), ModuleEnum.DOCTOR__EXAM_VIEW.toString());
	    }
	};
	private final static Map<String, String> EXAM_POLICY_TYPE = new HashMap<String, String>() {
	    {
	        put(ModuleEnum.NURSE_EXAM_POLICY.toString(),ModuleEnum.NURSE_EXAM_VIEW.toString());
	        put(ModuleEnum.DOCTOR__EXAM_POLICY.toString(), ModuleEnum.DOCTOR__EXAM_VIEW.toString());
	    }
	};
	
	/***ScienceEducation begin……***/
	@RequiresPermissions("cms:business:scienceEducation:index")
	@RequestMapping(value = "/scienceEducation/index", method = { RequestMethod.GET })
	public String indexScienceEducation(Model model,String moduleCode) {		
		Info info = new Info();
		if(POLICY.equals(moduleCode)){			
			info.setModuleId(ModuleEnum.SCIENCE_EDUCATION_POLICY.toString());
		}else{
			moduleCode = NOTICE;
			info.setModuleId(ModuleEnum.SCIENCE_EDUCATION_NOTICE.toString());
		}
		model.addAttribute("info", info);
		model.addAttribute("moduleCode", moduleCode);
		return "console/cms/indexScienceEducation";
	}

	@RequiresPermissions("cms:business:scienceEducation:index")
	@RequestMapping(value = "/scienceEducation/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listScienceEducation(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = scienceEducationService.getPageList(info,info.getModuleId());
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}
	
	@RequiresPermissions("cms:business:scienceEducation:edit")
	@RequestMapping(value = "/scienceEducation/from", method = { RequestMethod.GET })
	public String from(Info info, Model model,String moduleIdv,String moduleCode) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = scienceEducationService.getById(info.getInfoId());
		}
		model.addAttribute("info", info);
		model.addAttribute("moduleId", moduleIdv);
		model.addAttribute("moduleCode", moduleCode);
		return "console/cms/fromScienceEducation";
	}

	@RequiresPermissions("cms:business:scienceEducation:edit")
	@RequestMapping(value = "/scienceEducation/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap save(Info info, BindingResult result,String moduleCode) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();			
			 if (StringUtils.isEmpty(info.getInfoName())) {
                 return ReturnUtil.Error("标题不能为空", null, null);
             } 
			 if (StringUtils.isEmpty(info.getContent())) {
                 return ReturnUtil.Error("内容不能为空", null, null);
             }  
			 info.setUpdteDate(DateUtil.getSysTime());				
			 info.setCreateUser(admin.getUid());
			 //info.setModuleId(ModuleEnum.BANNER.toString());
			if(StringUtils.isEmpty(info.getInfoId())){
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				scienceEducationService.insert(info);
			}else{
				scienceEducationService.save(info);
			}						
			return ReturnUtil.Success("操作成功", null, "/console/business/scienceEducation/index?moduleCode="+moduleCode);
		} catch (Exception e) {
			logger.error("======",e);
			e.printStackTrace();			
			return ReturnUtil.Error("操作失败", null, null);
		}
	}	
	@RequiresPermissions("cms:business:scienceEducation:delete")
    @RequestMapping(value = "/scienceEducation/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap delete(String[] ids) {
        try {
            if ("null".equals(ids) || "".equals(ids)) {
                return ReturnUtil.Error("Error", null, null);
            } else {
            	for (String id : ids) {
            		scienceEducationService.deleteById(id);
				}            	
                return ReturnUtil.Success("操作成功", null, null);
            }
        } catch (Exception e) {
        	logger.error("======", e);
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }
	@RequiresPermissions("cms:business:grant")
    @RequestMapping(value = "/grant", method = {RequestMethod.GET})
    public String grantForm(String infoId, Model model) {
        model.addAttribute("infoId", infoId);
        return "console/cms/grant";
    }	
	@RequiresPermissions("cms:business:grant")
	@RequestMapping(value = "/menutree", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelMap comboTree(String id) {
		DistrictTreeUtil dtu = new DistrictTreeUtil(userDistrictService.getDistrictAll(),userDistrictService.getRootDistrictList());
		List<Map<String, Object>> mapList = dtu.buildTree();        
        return ReturnUtil.Success(null, mapList, null);
    }
	@Transactional
    @RequiresPermissions("cms:business:grant")
    @RequestMapping(value = "/grant", method = {RequestMethod.POST})
    @ResponseBody
    public ModelMap grant(String infoId, String[] menuIds) {
        try {
            return ReturnUtil.Success("操作成功", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }
	/***ScienceEducation end***/	
	
	/***ScienceEducationSys begin……***/
	@RequiresPermissions("cms:business:scienceEducationSys:index")
	@RequestMapping(value = "/scienceEducationSys/index", method = { RequestMethod.GET })
	public String indexScienceEducationSys(Model model,String moduleCode) {				
		return "console/cms/indexScienceEducationSys";
	}

	@RequiresPermissions("cms:business:scienceEducationSys:index")
	@RequestMapping(value = "/scienceEducationSys/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listScienceEducationSys(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = scienceEducationService.getPageList(info,ModuleEnum.SCIENCE_EDUCATION_SYS.toString());
		for (Info infov : lists) {
			if(!StringUtil.isEmpty(infov.getOrgId())){
				infov.setOrdView(userDistrictService.getDistrictByIds(infov.getOrgId().split(",")));
			}
		}
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}
	
	@RequiresPermissions("cms:business:scienceEducationSys:edit")
	@RequestMapping(value = "/scienceEducationSys/from", method = { RequestMethod.GET })
	public String fromScienceEducationSys(Info info, Model model) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = scienceEducationService.getById(info.getInfoId());
		}
		if(!StringUtil.isEmpty(info.getOrgId())){
			info.setOrdView(userDistrictService.getDistrictByIds(info.getOrgId().split(",")));
		}
		model.addAttribute("info", info);
		return "console/cms/fromScienceEducationSys";
	}

	@RequiresPermissions("cms:business:scienceEducationSys:edit")
	@RequestMapping(value = "/scienceEducationSys/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveScienceEducationSys(Info info, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();			
			 if (StringUtils.isEmpty(info.getInfoName())) {
                 return ReturnUtil.Error("系统名称不能为空", null, null);
             } 
			 if (StringUtils.isEmpty(info.getLinkUrl())) {
                 return ReturnUtil.Error("跳转链接不能为空", null, null);
             } 
			 info.setModuleId(ModuleEnum.SCIENCE_EDUCATION_SYS.toString());
			 info.setUpdteDate(DateUtil.getSysTime());				
			 info.setCreateUser(admin.getUid());
			 //info.setModuleId(ModuleEnum.BANNER.toString());
			if(StringUtils.isEmpty(info.getInfoId())){
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				scienceEducationService.insert(info);
			}else{
				scienceEducationService.save(info);
			}						
			return ReturnUtil.Success("操作成功", null, "/console/business/scienceEducationSys/index");
		} catch (Exception e) {
			logger.error("======",e);
			e.printStackTrace();			
			return ReturnUtil.Error("操作失败", null, null);
		}
	}	
	@RequiresPermissions("cms:business:scienceEducationSys:delete")
    @RequestMapping(value = "/scienceEducationSys/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap deleteScienceEducationSys(String[] ids) {
        try {
            if ("null".equals(ids) || "".equals(ids)) {
                return ReturnUtil.Error("Error", null, null);
            } else {
            	for (String id : ids) {
            		scienceEducationService.deleteById(id);
				}            	
                return ReturnUtil.Success("操作成功", null, null);
            }
        } catch (Exception e) {
        	logger.error("======", e);
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }
	/***ScienceEducation end***/	
	
	/***educationPolicy begin***/
	@RequiresPermissions("cms:business:educationPolicy:index")
	@RequestMapping(value = "/educationPolicy/index", method = { RequestMethod.GET })
	public String indexEducationPolicy(Model model,String moduleCode) {				
		return "console/cms/indexEducationPolicy";
	}

	@RequiresPermissions("cms:business:educationPolicy:index")
	@RequestMapping(value = "/educationPolicy/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listEducationPolicy(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = scienceEducationService.getPageList(info,ModuleEnum.EDUCATION_POLICY.toString());		
		for (Info infov : lists) {
			if(!StringUtil.isEmpty(infov.getOrgId())){
				infov.setOrdView(userDistrictService.getDistrictByIds(infov.getOrgId().split(",")));
			}
		}
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}
	
	@RequiresPermissions("cms:business:educationPolicy:edit")
	@RequestMapping(value = "/educationPolicy/from", method = { RequestMethod.GET })
	public String fromEducationPolicy(Info info, Model model) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = scienceEducationService.getById(info.getInfoId());
		}
		if(!StringUtil.isEmpty(info.getOrgId())){
			info.setOrdView(userDistrictService.getDistrictByIds(info.getOrgId().split(",")));
		}
		model.addAttribute("info", info);
		return "console/cms/fromEducationPolicy";
	}

	@RequiresPermissions("cms:business:educationPolicy:edit")
	@RequestMapping(value = "/educationPolicy/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveEducationPolicy(Info info, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();			
			 if (StringUtils.isEmpty(info.getInfoName())) {
                 return ReturnUtil.Error("标题不能为空", null, null);
             } 			 
			 info.setModuleId(ModuleEnum.EDUCATION_POLICY.toString());
			 info.setUpdteDate(DateUtil.getSysTime());				
			 info.setCreateUser(admin.getUid());
			if(StringUtils.isEmpty(info.getInfoId())){
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				scienceEducationService.insert(info);
			}else{
				scienceEducationService.save(info);
			}						
			return ReturnUtil.Success("操作成功", null, "/console/business/educationPolicy/index");
		} catch (Exception e) {
			logger.error("======",e);
			e.printStackTrace();			
			return ReturnUtil.Error("操作失败", null, null);
		}
	}	
	@RequiresPermissions("cms:business:educationPolicy:delete")
    @RequestMapping(value = "/educationPolicy/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap deleteEducationPolicy(String[] ids) {
        try {
            if ("null".equals(ids) || "".equals(ids)) {
                return ReturnUtil.Error("Error", null, null);
            } else {
            	for (String id : ids) {
            		scienceEducationService.deleteById(id);
				}            	
                return ReturnUtil.Success("操作成功", null, null);
            }
        } catch (Exception e) {
        	logger.error("======", e);
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }
	/***educationPolicy end***/
	
	/***courseCME begin***/
	@RequiresPermissions("cms:business:courseCME:index")
	@RequestMapping(value = "/courseCME/index", method = { RequestMethod.GET })
	public String indexCourseCME(Model model,String moduleCode) {				
		return "console/cms/indexCourseCME";
	}

	@RequiresPermissions("cms:business:courseCME:index")
	@RequestMapping(value = "/courseCME/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listCourseCME(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = scienceEducationService.getPageList(info,COURSE_TYPE);		
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}
	
	@RequiresPermissions("cms:business:courseCME:edit")
	@RequestMapping(value = "/courseCME/from", method = { RequestMethod.GET })
	public String fromCourseCME(Info info, Model model) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = scienceEducationService.getById(info.getInfoId());
		}		
		model.addAttribute("info", info);
		model.addAttribute("moduleMap", COURSE_TYPE);
		return "console/cms/fromCourseCME";
	}

	@RequiresPermissions("cms:business:courseCME:edit")
	@RequestMapping(value = "/courseCME/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveCourseCME(Info info, BindingResult result,@RequestParam(value = "imgFile", required = false) MultipartFile file) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
			String imgUrl = "";
			if (file != null) {
				imgUrl = UploadUtil.uploadFile(file, uploadPath);
			} else {
				imgUrl = info.getImgPath();
			}
			 if (StringUtils.isEmpty(info.getInfoName())) {
                 return ReturnUtil.Error("课程名称不能为空", null, null);
             } 			 			
			 info.setImgUrl(imgUrl);
			 info.setUpdteDate(DateUtil.getSysTime());				
			 info.setCreateUser(admin.getUid());
			if(StringUtils.isEmpty(info.getInfoId())){
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				scienceEducationService.insert(info);
			}else{
				scienceEducationService.save(info);
			}						
			return ReturnUtil.Success("操作成功", null, "/console/business/courseCME/index");
		} catch (Exception e) {
			logger.error("======",e);
			e.printStackTrace();			
			return ReturnUtil.Error("操作失败", null, null);
		}
	}	
	@RequiresPermissions("cms:business:courseCME:delete")
    @RequestMapping(value = "/courseCME/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap deleteCourseCME(String[] ids) {
        try {
            if ("null".equals(ids) || "".equals(ids)) {
                return ReturnUtil.Error("Error", null, null);
            } else {
            	for (String id : ids) {
            		scienceEducationService.deleteById(id);
				}            	
                return ReturnUtil.Success("操作成功", null, null);
            }
        } catch (Exception e) {
        	logger.error("======", e);
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }
	/***courseCME end***/
	
	/***projectCME begin***/
	@RequiresPermissions("cms:business:projectCME:index")
	@RequestMapping(value = "/projectCME/index", method = { RequestMethod.GET })
	public String indexProjectCME(Model model,String moduleCode) {				
		return "console/cms/indexProjectCME";
	}

	@RequiresPermissions("cms:business:projectCME:index")
	@RequestMapping(value = "/projectCME/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listProjectCME(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = scienceEducationService.getPageList(info,ModuleEnum.CME_PROJECT.toString());
		for (Info infov : lists) {
			if(!StringUtil.isEmpty(infov.getOrgId())){
				infov.setOrdView(userDistrictService.getDistrictByIds(infov.getOrgId().split(",")));
			}
		}
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}
	
	@RequiresPermissions("cms:business:projectCME:edit")
	@RequestMapping(value = "/projectCME/from", method = { RequestMethod.GET })
	public String fromProjectCME(Info info, Model model) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = scienceEducationService.getById(info.getInfoId());
		}
		if(!StringUtil.isEmpty(info.getOrgId())){
			info.setOrdView(userDistrictService.getDistrictByIds(info.getOrgId().split(",")));
		}
		model.addAttribute("info", info);
		return "console/cms/fromProjectCME";
	}

	@RequiresPermissions("cms:business:projectCME:edit")
	@RequestMapping(value = "/projectCME/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveProjectCME(Info info, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();			
			 if (StringUtils.isEmpty(info.getInfoName())) {
                 return ReturnUtil.Error("项目名称不能为空", null, null);
             } 			 
			 info.setModuleId(ModuleEnum.CME_PROJECT.toString());
			 info.setUpdteDate(DateUtil.getSysTime());				
			 info.setCreateUser(admin.getUid());
			if(StringUtils.isEmpty(info.getInfoId())){
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				scienceEducationService.insert(info);
			}else{
				scienceEducationService.save(info);
			}						
			return ReturnUtil.Success("操作成功", null, "/console/business/projectCME/index");
		} catch (Exception e) {
			logger.error("======",e);
			e.printStackTrace();			
			return ReturnUtil.Error("操作失败", null, null);
		}
	}	
	@RequiresPermissions("cms:business:projectCME:delete")
    @RequestMapping(value = "/projectCME/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap deleteProjectCME(String[] ids) {
        try {
            if ("null".equals(ids) || "".equals(ids)) {
                return ReturnUtil.Error("Error", null, null);
            } else {
            	for (String id : ids) {
            		scienceEducationService.deleteById(id);
				}            	
                return ReturnUtil.Success("操作成功", null, null);
            }
        } catch (Exception e) {
        	logger.error("======", e);
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }
	/***projectCME end***/
	
	/***GrassRoot begin……***/
	@RequiresPermissions("cms:business:grassRoot:index")
	@RequestMapping(value = "/grassRoot/index", method = { RequestMethod.GET })
	public String indexGrassRoot(Model model,String moduleCode) {		
		Info info = new Info();
		if(POLICY.equals(moduleCode)){			
			info.setModuleId(ModuleEnum.GRASS_ROOT_POLICY.toString());
		}else{
			moduleCode = NOTICE;
			info.setModuleId(ModuleEnum.GRASS_ROOT_NOTICE.toString());
		}
		model.addAttribute("info", info);
		model.addAttribute("moduleCode", moduleCode);
		return "console/cms/indexGrassRoot";
	}

	@RequiresPermissions("cms:business:grassRoot:index")
	@RequestMapping(value = "/grassRoot/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listGrassRoot(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = scienceEducationService.getPageList(info,info.getModuleId());
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}
	
	@RequiresPermissions("cms:business:grassRoot:edit")
	@RequestMapping(value = "/grassRoot/from", method = { RequestMethod.GET })
	public String fromGrassRoot(Info info, Model model,String moduleIdv,String moduleCode) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = scienceEducationService.getById(info.getInfoId());
		}
		model.addAttribute("info", info);
		model.addAttribute("moduleId", moduleIdv);
		model.addAttribute("moduleCode", moduleCode);
		return "console/cms/fromGrassRoot";
	}

	@RequiresPermissions("cms:business:grassRoot:edit")
	@RequestMapping(value = "/grassRoot/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveGrassRoot(Info info, BindingResult result,String moduleCode) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();			
			 if (StringUtils.isEmpty(info.getInfoName())) {
                 return ReturnUtil.Error("标题不能为空", null, null);
             } 
			 if (StringUtils.isEmpty(info.getContent())) {
                 return ReturnUtil.Error("内容不能为空", null, null);
             }  
			 info.setUpdteDate(DateUtil.getSysTime());				
			 info.setCreateUser(admin.getUid());		
			if(StringUtils.isEmpty(info.getInfoId())){
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				scienceEducationService.insert(info);
			}else{
				scienceEducationService.save(info);
			}						
			return ReturnUtil.Success("操作成功", null, "/console/business/grassRoot/index?moduleCode="+moduleCode);
		} catch (Exception e) {
			logger.error("======",e);
			e.printStackTrace();			
			return ReturnUtil.Error("操作失败", null, null);
		}
	}	
	@RequiresPermissions("cms:business:grassRoot:delete")
    @RequestMapping(value = "/grassRoot/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap deleteGrassRoot(String[] ids) {
        try {
            if ("null".equals(ids) || "".equals(ids)) {
                return ReturnUtil.Error("Error", null, null);
            } else {
            	for (String id : ids) {
            		scienceEducationService.deleteById(id);
				}            	
                return ReturnUtil.Success("操作成功", null, null);
            }
        } catch (Exception e) {
        	logger.error("======", e);
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }	
	/***GrassRoot end***/
	/***Exam begin……***/
	@RequiresPermissions("cms:business:exam:index")
	@RequestMapping(value = "/exam/index", method = { RequestMethod.GET })
	public String indexExam(Model model,String moduleCode) {		
		Info info = new Info();
		if(POLICY.equals(moduleCode)){						
		}else{
			moduleCode = NOTICE;			
		}
		model.addAttribute("info", info);
		model.addAttribute("moduleCode", moduleCode);
		return "console/cms/indexExam";
	}

	@RequiresPermissions("cms:business:exam:index")
	@RequestMapping(value = "/exam/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listExam(Info info) {
		ModelMap map = new ModelMap();
		Map<String,String> moduleMap = null;
		if(POLICY.equals(info.getModuleId())){
			moduleMap = EXAM_POLICY_TYPE;			
		}else{
			moduleMap = EXAM_NOTICE_TYPE;			
		}
		List<Info> lists = scienceEducationService.getPageList(info,moduleMap);		
		map.put("moduleCode", info.getModuleId());
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}
	
	@RequiresPermissions("cms:business:exam:edit")
	@RequestMapping(value = "/exam/from", method = { RequestMethod.GET })
	public String fromExam(Info info, Model model,String moduleIdv) {
		Map<String,String> moduleMap = null;
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = scienceEducationService.getById(info.getInfoId());
		}
		if(POLICY.equals(moduleIdv)){
			moduleMap = EXAM_POLICY_TYPE;
		}else{
			moduleMap = EXAM_NOTICE_TYPE;
		}
		model.addAttribute("info", info);
		model.addAttribute("moduleMap", moduleMap);
		model.addAttribute("moduleCode", moduleIdv);
		return "console/cms/fromExam";
	}

	@RequiresPermissions("cms:business:exam:edit")
	@RequestMapping(value = "/exam/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveExam(Info info, BindingResult result,String moduleCode) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();			
			 if (StringUtils.isEmpty(info.getInfoName())) {
                 return ReturnUtil.Error("标题不能为空", null, null);
             } 
			 if (StringUtils.isEmpty(info.getContent())) {
                 return ReturnUtil.Error("内容不能为空", null, null);
             }  
			 info.setUpdteDate(DateUtil.getSysTime());				
			 info.setCreateUser(admin.getUid());
			if(StringUtils.isEmpty(info.getInfoId())){
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				scienceEducationService.insert(info);
			}else{
				scienceEducationService.save(info);
			}						
			return ReturnUtil.Success("操作成功", null, "/console/business/exam/index?moduleCode="+moduleCode);
		} catch (Exception e) {
			logger.error("======",e);
			e.printStackTrace();			
			return ReturnUtil.Error("操作失败", null, null);
		}
	}	
	@RequiresPermissions("cms:business:exam:delete")
    @RequestMapping(value = "/exam/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap deletExam(String[] ids) {
        try {
            if ("null".equals(ids) || "".equals(ids)) {
                return ReturnUtil.Error("Error", null, null);
            } else {
            	for (String id : ids) {
            		scienceEducationService.deleteById(id);
				}            	
                return ReturnUtil.Success("操作成功", null, null);
            }
        } catch (Exception e) {
        	logger.error("======", e);
            e.printStackTrace();
            return ReturnUtil.Error("操作失败", null, null);
        }
    }		
	/***Exam end***/	
}
