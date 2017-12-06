package com.geekcattle.controller.kjk;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.service.kjk.KjkService;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.vo.kjk.CoursewareVo;
import com.github.pagehelper.PageInfo;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/console/kjk")
public class KjkController {
	private final static Logger logger = LoggerFactory.getLogger(KjkController.class);	
	@Autowired
	private KjkService kjkService;
	
	/***kjk begin……***/
	@RequiresPermissions("courseware:index")
	@RequestMapping(value = "/courseware/index", method = { RequestMethod.GET })
	public String indexCourseware(Model model,String moduleCode) {		
		return "console/kjk/indexCourseware";
	}

	@RequiresPermissions("courseware:index")
	@RequestMapping(value = "/courseware/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listCourseware(KjkCourseware kjkCourseware) {
		ModelMap map = new ModelMap();
		List<KjkCourseware> lists = kjkService.getPageList(kjkCourseware);
		map.put("pageInfo", new PageInfo<KjkCourseware>(lists));
		map.put("queryParam", kjkCourseware);
		return ReturnUtil.Success("加载成功", map, null);
	}
	@RequiresPermissions("courseware:download")
	@RequestMapping("/courseware/download")
	public void downloadfile(KjkCourseware kjkCourseware,HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("appliction/octet-stream;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=courseware.xls");
		ExportParams params = new ExportParams("课件表", "课件表");
		params.setColor(HSSFColor.PALE_BLUE.index);		
		List<CoursewareVo> list = kjkService.getExcelList(kjkCourseware);		
		Workbook  workbook = ExcelExportUtil.exportExcel(params, CoursewareVo.class, list);
		workbook.write(response.getOutputStream());
	}
	
	/*@RequiresPermissions("cms:business:scienceEducation:edit")
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
    }	*/
	/***kjk end***/		
}
