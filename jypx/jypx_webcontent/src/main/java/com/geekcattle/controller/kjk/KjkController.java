package com.geekcattle.controller.kjk;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.conf.KjkEnum;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.model.kjk.KjkPlayType;
import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.service.console.LogService;
import com.geekcattle.service.kjk.KjkPlayTypeService;
import com.geekcattle.service.kjk.KjkService;
import com.geekcattle.service.kjk.NcmeSubjectService;
import com.geekcattle.util.IpUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.console.ExcelOperate;
import com.geekcattle.vo.kjk.CoursewareVo;
import com.github.pagehelper.PageInfo;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
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
	@Value("${upload.courseware.template}")
	private String coursewareTemplate;
	@Autowired
	private KjkService kjkService;
	@Autowired
	private KjkPlayTypeService kjkPlayTypeService;
	@Autowired
    private LogService logService;
	@Autowired
	private NcmeSubjectService ncmeSubjectService;
	
	
	/***kjk begin……***/
	private final static List<String> COURSEWARE_SOURCE = new ArrayList<String>() {
		{
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_CME.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_BASE.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_RCT.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_EXAM.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_COUNTRY_DOCTORS.toString());
		}
	};

	/*** kjk begin…… ***/
	@RequiresPermissions("courseware:index")
	@RequestMapping(value = "/courseware/index", method = { RequestMethod.GET })
	public String indexCourseware(Model model, String moduleCode) {
		// 课件库播放类型集合
		List<KjkPlayType> list = kjkPlayTypeService.findAll();
		model.addAttribute("kjkPlayTypeList", list);
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
	public void downloadfile(KjkCourseware kjkCourseware, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<CoursewareVo> list = kjkService.getExcelList(kjkCourseware);
		ExportParams params = new ExportParams("课件表", "课件表", ExcelType.XSSF);
       /* params.setHeaderColor(HSSFColor.SKY_BLUE.index);        
        model.put(NormalExcelConstants.DATA_LIST, list); // 数据集合
        model.put(NormalExcelConstants.CLASS, CoursewareVo.class);//导出实体
        model.put(NormalExcelConstants.PARAMS, params);//参数
        model.put(NormalExcelConstants.FILE_NAME, "课件表");//文件名称
        ExcelOperate.renderMergedOutputModel(model, request, response);*/
	}
	
	/**
	 * 编辑课件
	 * @param kjkCourseware
	 * @param model
	 * @return
	 */
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/courseware/eidt", method = { RequestMethod.GET })
	public String from(KjkCourseware kjkCourseware, Model model) {
		if (kjkCourseware.getId()!=null) {
			kjkCourseware = kjkService.getById(kjkCourseware.getId());
		}
		
		model.addAttribute("subjectList",ncmeSubjectService.getNcmeSubject2());
		model.addAttribute("info", kjkCourseware);
		return "console/kjk/fromCoursewareEdit";
	}

    @ResponseBody  
    @RequestMapping("/ajaxSubjectName")
    public List<NcmeSubject> ajaxSubjectName(String subjectName2){
    	List<NcmeSubject> list = ncmeSubjectService.getNcmeSubjectByName(subjectName2);
        return list;
    }
    
	/***kjk end***/		

	@RequestMapping(value = "/courseware/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap delete(String cwareids, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(cwareids)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				StringBuffer sbf = new StringBuffer();
				KjkCourseware kjkCourseware = new KjkCourseware();
				String[] ids = cwareids.split(",");
				for (String id : ids) {
					kjkCourseware.setId(Long.valueOf(id));
					kjkCourseware.setStatus(KjkEnum.KJK_COURSEWARE_STATUS_DISABLE.getValue().intValue());
					kjkService.updateCourseCware(kjkCourseware);
					sbf.append(id).append(",");
				}
				// 记录删除课件日志
				String ip = IpUtil.getIpAddr(request);
				Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
				logService.insertLoginLog(admin.getUsername(), ip,
						"删除课件" + sbf.toString().substring(0, sbf.toString().length() - 1));
				return ReturnUtil.Success("1", null, null);
			}

		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("0", null, null);
		}
	}
	/*** kjk end ***/
}