package com.geekcattle.controller.kjk;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.service.console.LogService;
import com.geekcattle.service.kjk.KjkPlayTypeService;
import com.geekcattle.service.kjk.KjkService;
import com.geekcattle.service.kjk.NcmeSubjectService;
import com.geekcattle.util.DateUtil;
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
	
	
	private final static List<String> COURSEWARE_SOURCE = new ArrayList<String>() {
		{
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_CME.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_BASE.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_RCT.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_EXAM.toString());
			add(ConstantEnum.KJK_COURSEWARE_SOURCE_COUNTRY_DOCTORS.toString());
		}
	};
	private final static Map<String, Object> PROJECT_LEVEL = new HashMap<String, Object>() {
		{
			put(ConstantEnum.KJK_COURSEWARE_PROJECT_LEVEL_COMMON.toString(),"普通项目	");
			put(ConstantEnum.KJK_COURSEWARE_PROJECT_LEVEL_NATION.toString(),"国家级项目");
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
		ExportParams params = new ExportParams(ConstantEnum.DOWNLOAD_COURSEWARE_TITLENAME.toString(),
				ConstantEnum.DOWNLOAD_COURSEWARE_SHEETNAME.toString(), ExcelType.XSSF);
		model.put(NormalExcelConstants.DATA_LIST, list); // 数据集合
		model.put(NormalExcelConstants.CLASS, CoursewareVo.class);// 导出实体
		model.put(NormalExcelConstants.PARAMS, params);// 参数
		model.put(NormalExcelConstants.FILE_NAME, ConstantEnum.DOWNLOAD_COURSEWARE_FILENAME);// 文件名称
		ExcelOperate.renderMergedOutputModel(model, request, response);
	}

	@RequiresPermissions("courseware:download")
	@RequestMapping("/courseware/downloadTemplate")
	public void downloadCoursewareTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(System.getProperty("user.dir") );
		File file = new File(request.getSession().getServletContext().getRealPath(coursewareTemplate));
		response.setHeader("content-type", "application/octet-stream");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(file));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			logger.error("======下载课件导入模板异常", e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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
		//课件播放类型
		List<KjkPlayType> list = kjkPlayTypeService.findAll();
		model.addAttribute("kjkPlayTypeList", list);
		model.addAttribute("courSourceList", COURSEWARE_SOURCE);
		model.addAttribute("moduleMap", PROJECT_LEVEL);
		model.addAttribute("subjectList",ncmeSubjectService.getNcmeSubject2());
		model.addAttribute("info", kjkCourseware);
		return "console/kjk/fromCoursewareEdit";
	}

	/**
	 * 根据三级学科获取二级学科
	 * @param subjectName2
	 * @return
	 */
    @ResponseBody  
    @RequestMapping("/ajaxSubjectName")
    public List<NcmeSubject> ajaxSubjectName(String subjectName2){
    	List<NcmeSubject> list = ncmeSubjectService.getNcmeSubjectByName(subjectName2);
        return list;
    }
    
    /**
     * 添加或者修改
     * @param kjkCourseware
     * @param result
     * @param request
     * @return
     */
    @RequiresPermissions("courseware:edit")
    @RequestMapping(value="/courseware/save",method={RequestMethod.POST})
    @ResponseBody
    public ModelMap save(KjkCourseware kjkCourseware,BindingResult result,HttpServletRequest request){
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
			if (StringUtils.isEmpty(kjkCourseware.getName()))
                 return ReturnUtil.Error("课程名称不能为空", null, null);
			if (StringUtils.isEmpty(kjkCourseware.getpName()))
				return ReturnUtil.Error("项目名称不能为空", null, null);
			if(StringUtils.isEmpty(kjkCourseware.getExpert()))
				return ReturnUtil.Error("专家不能为空", null, null);
			if(StringUtils.isEmpty(kjkCourseware.getExpertUnit()))
				return ReturnUtil.Error("专家单位不能为空", null, null);
			if(StringUtils.isEmpty(kjkCourseware.getClassTimeStr()))
				return ReturnUtil.Error("时长不能为空", null, null);
			if(StringUtils.isEmpty(kjkCourseware.getSubject2()))
				return ReturnUtil.Error("二级学科不能为空", null, null);
			if(StringUtils.isEmpty(kjkCourseware.getSubject()))
				return ReturnUtil.Error("三级学科不能为空", null, null);
			if(StringUtils.isEmpty(kjkCourseware.getPar1()))
				return ReturnUtil.Error("播放参数1不能为空", null, null);
			if(StringUtils.isEmpty(kjkCourseware.getPar2()))
				return ReturnUtil.Error("播放参数2不能为空", null, null);
			
			if(StringUtils.isEmpty(kjkCourseware.getPlayType()) && StringUtils.isEmpty(kjkCourseware.getMobileType())){
				return ReturnUtil.Error("播放类型不能为空", null, null);
			}else{
				//手机播放类型或pc播放类型 为cc格式
				if(kjkCourseware.getPlayType().equals(ConstantEnum.KJK_PLAY_TYPE_CC.toString())  || StringUtils.isEmpty(kjkCourseware.getMobileType()) && kjkCourseware.getMobileType().equals(ConstantEnum.KJK_PLAY_TYPE_CC.toString())){
					if(StringUtils.isEmpty(kjkCourseware.getCode()))
						return ReturnUtil.Error("课件编号不能为空", null, null);
					if(kjkCourseware.getCode().trim().length()!=32)
						return ReturnUtil.Error("课件编号长度必须为32位", null, null);
					String par1=kjkCourseware.getPar1();
					String par2=kjkCourseware.getPar2();
					if(!par1.contains("vid=") || !par2.contains("vid="))
						return ReturnUtil.Error("播放参数格式不对", null, null);
					String str1=par1.substring(par1.indexOf("vid=")+4, par1.indexOf("&siteid"));
					String str2=par2.substring(par2.indexOf("vid=")+4, par2.indexOf("&siteid"));
					//课件类型是CC视频课件 播放参数1和播放参数2中vid必须相同
					if(!str1.endsWith(str2))
						return ReturnUtil.Error("播放参数1和播放参数2中vid必须相同", null, null);
				}
			}
			kjkCourseware.setCreateDate(DateUtil.getSysTime());
			kjkCourseware.setAddDate(DateUtil.getSysTime());
			kjkCourseware.setStatus(KjkEnum.KJK_COURSEWARE_STATUS_ENABLE.getValue().intValue());
			kjkCourseware.setPlayFlag(ConstantEnum.KJK_COURSEWARE_PLY_FLAG_NOT.toString());
			kjkCourseware.setClickCount(0);	//点击量
			kjkCourseware.setShotYear(DateUtil.getCurrentYear());//拍摄年份
			kjkCourseware.setCreater(admin.getUid());
			//kjkCourseware.setClassTime(new BigDecimal(DateUtil.dateToSS(kjkCourseware.getClassTimeStr())));
			kjkCourseware.setClassHour(new BigDecimal(0));
			kjkCourseware.setUpdateDate(DateUtil.getSysTime());
			if(kjkCourseware.getId()!=null){
				kjkCourseware.setModifier(admin.getUid());
				kjkService.save(kjkCourseware);
				return ReturnUtil.Success("操作成功", 1, null);
			}else
				kjkService.insert(kjkCourseware);
			
			return ReturnUtil.Success("操作成功", 2, null);
		} catch (Exception e) {
			logger.error("======",e);
			e.printStackTrace();			
			return ReturnUtil.Error("操作失败", null, null);
		}
	
    } 
    
    /**
     * 删除课件
     * @param cwareids
     * @param request
     * @return
     */
    @RequiresPermissions("courseware:edit")
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