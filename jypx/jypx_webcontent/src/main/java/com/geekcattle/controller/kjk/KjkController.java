package com.geekcattle.controller.kjk;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.conf.KjkEnum;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.model.kjk.KjkDic;
import com.geekcattle.model.kjk.KjkPlayType;
import com.geekcattle.model.kjk.NcmeSubject;
import com.geekcattle.service.console.LogService;
import com.geekcattle.service.importdata.CoursewareVerify;
import com.geekcattle.service.kjk.KjkDicService;
import com.geekcattle.service.kjk.KjkPlayTypeService;
import com.geekcattle.service.kjk.KjkService;
import com.geekcattle.service.kjk.NcmeSubjectService;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.FileUtil;
import com.geekcattle.util.IdUtil;
import com.geekcattle.util.IpUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.console.ExcelOperate;
import com.geekcattle.vo.kjk.CoursewareVo;
import com.github.pagehelper.PageInfo;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/console/kjk")
public class KjkController {
	private final static Logger logger = LoggerFactory.getLogger(KjkController.class);	
	@Value("${upload.courseware.filepath}")
	private String uploadCoursewarePath;
	@Value("${upload.courseware.errfilepath}")
	private String uploadCoursewareErrPath;
	@Autowired
	private KjkService kjkService;
	@Autowired
	private KjkPlayTypeService kjkPlayTypeService;
	@Autowired
	private LogService logService;
	@Autowired
	private NcmeSubjectService ncmeSubjectService;
	@Autowired
	private KjkDicService kjkDicService;
	
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
			put(ConstantEnum.KJK_COURSEWARE_PROJECT_LEVEL_COMMON.toString(), "普通项目 ");
			put(ConstantEnum.KJK_COURSEWARE_PROJECT_LEVEL_NATION.toString(), "国家级项目");
		}
	};
	private final static Map<String, Object> COURSEWARE_STATUS = new HashMap<String, Object>() {
		{
			put(KjkEnum.KJK_COURSEWARE_STATUS_ENABLE.toString(), "有效");
			put(KjkEnum.KJK_COURSEWARE_STATUS_DOWN.toString(), "下架");
		}
	};
	
	/*** kjk begin…… ***/
	@RequiresPermissions("courseware:index")
	@RequestMapping(value = "/courseware/index", method = { RequestMethod.GET })
	public String indexCourseware(Model model, String playFlag) {
		// 课件库播放类型集合
		List<KjkPlayType> list = kjkPlayTypeService.findAll();
		model.addAttribute("kjkPlayTypeList", list);
		model.addAttribute("playFlag", playFlag==null?"":playFlag);
		model.addAttribute("subjectList", ncmeSubjectService.getNcmeSubject2());
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
		model.put(NormalExcelConstants.FILE_NAME, ConstantEnum.DOWNLOAD_COURSEWARE_FILENAME.toString());// 文件名称
		ExcelOperate.renderMergedOutputModel(model, request, response);
	}
	
	@RequiresPermissions("courseware:download")
	@RequestMapping("/courseware/downloadCheck")
	public void downloadCheckfile(String ids, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<CoursewareVo> list = kjkService.getListByIds(ids.split(","));
		ExportParams params = new ExportParams(ConstantEnum.DOWNLOAD_COURSEWARE_TITLENAME.toString(),
				ConstantEnum.DOWNLOAD_COURSEWARE_SHEETNAME.toString(), ExcelType.XSSF);
		model.put(NormalExcelConstants.DATA_LIST, list); // 数据集合
		model.put(NormalExcelConstants.CLASS, CoursewareVo.class);// 导出实体
		model.put(NormalExcelConstants.PARAMS, params);// 参数
		model.put(NormalExcelConstants.FILE_NAME, ConstantEnum.DOWNLOAD_COURSEWARE_FILENAME.toString());// 文件名称
		ExcelOperate.renderMergedOutputModel(model, request, response);
	}

	@RequiresPermissions("courseware:download")
	@RequestMapping("/courseware/downloadTemplate")
	public void downloadCoursewareTemplate(HttpServletRequest request, HttpServletResponse response,String template) throws Exception {
		URL url = Thread.currentThread().getContextClassLoader().getResource(template);
		File file = new File(url.toURI());
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

	@RequiresPermissions("courseware:download")
	@RequestMapping("/courseware/importCourseware")
	@ResponseBody
	public ModelMap fromImportCourseware(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "imgFile", required = false) MultipartFile impFile) throws Exception {
		String filename = impFile.getOriginalFilename();
		if (impFile == null || impFile.getSize() == 0 || filename == null) {
			return ReturnUtil.Error("请先选择有内容的文件", null, null);
		}
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
		// 学科信息
		List<NcmeSubject> subject2List = ncmeSubjectService.getNcmeSubject2();
		Map<String, List<NcmeSubject>> map = new HashMap<String, List<NcmeSubject>>();
		for (NcmeSubject subject2 : subject2List) {
			map.put(subject2.getSubject2Name(), ncmeSubjectService.getNcmeSubjectByName(subject2.getSubject2Name()));
		}
		// 课件类型
		List<KjkPlayType> playTypeList = kjkPlayTypeService.findAll();
		ImportParams imParam = new ImportParams();
		imParam.setNeedVerfiy(true);
		imParam.setVerifyHanlder(new CoursewareVerify(playTypeList, map, admin.getUid()));
		long beginTime = System.currentTimeMillis();
		ExcelImportResult<CoursewareVo> eir = ExcelImportUtil.importExcelVerify(impFile.getInputStream(),
				CoursewareVo.class, imParam);
		// 上传文件保存路径
		String filePath = request.getSession().getServletContext().getRealPath(uploadCoursewarePath) + File.separator
				+ IdUtil.timeId() + FileUtil.getExt(filename);
		File uploadServerFile = FileUtil.createAndWriteFile(filePath, impFile.getBytes());
		File uploadServerErrFile = null;
		if (eir.isVerfiyFail()) { // 有错误
			OutputStream os = null;
			String errFileName = "";
			try {
				String errFolder = request.getSession().getServletContext().getRealPath(uploadCoursewareErrPath);
				File tempFolder = new File(errFolder);
				if (!tempFolder.exists()) {
					tempFolder.mkdir();
				}
				errFileName = IdUtil.timeId() + FileUtil.getExt(filename);
				os = new FileOutputStream(errFolder + File.separator + errFileName);
				Workbook wb = eir.getWorkbook();
				wb.write(os);
			} catch (Exception e) {
				throw e;
			} finally {
				if (os != null) {
					os.close();
				}
			}
			return ReturnUtil.Success("导入文件有错误，未导入课件请下载文件查看具体原因", null, "fromImport?type=err&errFile=" + errFileName);
		} else {
			List<CoursewareVo> list = eir.getList();
			if (list != null && list.size() > 0) {
				if (list.size() > 1000) { // 由于使用in判断，不能超过1000
					return ReturnUtil.Error("文件中内容请控制在1000条内", null, null);
				}
				List<String> dupList = getDuplicateElements(list);
				if (dupList != null && dupList.size() > 0) {
					return ReturnUtil.Error("文件中课件名称有重复请检查", null, null);
				}
				List<CoursewareVo> sameList = kjkService.getListByNames(list);
				if (sameList != null && sameList.size() > 0) {
					return ReturnUtil.Error("文件中课件名称同系统中课件名称有重复", null, null);
				}
				try {
					kjkService.insertBatch(list);
				} catch (Exception e) {
					return ReturnUtil.Error(e.getMessage(), null, null);
				}				
				String ip = IpUtil.getIpAddr(request);
				logService.insertLoginLog(admin.getUsername(), ip, "导入课件" + list.size() + "条");
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - beginTime);
		return ReturnUtil.Success("导入成功", 2, null);
	}

	/**
	 * 编辑课件
	 * 
	 * @param kjkCourseware
	 * @param model
	 * @return
	 */
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/courseware/edit", method = { RequestMethod.GET })
	public String from(KjkCourseware kjkCourseware, Model model) {
		if (kjkCourseware.getId() != null) {
			kjkCourseware = kjkService.getById(kjkCourseware.getId());
		}
		//标签集合
		List<KjkDic> labelDicList = kjkDicService.findDicByType(ConstantEnum.KJK_DIC_TYPE_QUESTION_TAGS.toString());
		// 课件播放类型
		List<KjkPlayType> list = kjkPlayTypeService.findAll();
		model.addAttribute("kjkPlayTypeList", list);
		model.addAttribute("courSourceList", COURSEWARE_SOURCE);
		model.addAttribute("moduleMap", PROJECT_LEVEL);
		model.addAttribute("subjectList", ncmeSubjectService.getNcmeSubject2());
		model.addAttribute("info", kjkCourseware);
		model.addAttribute("labelDicList", labelDicList);
		return "console/kjk/fromCoursewareEdit";
	}

	/**
	 * 编辑课件
	 * 
	 * @param kjkCourseware
	 * @param model
	 * @return
	 */
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/courseware/toEditStatus", method = { RequestMethod.GET })
	public String toEditStatus(Long id,Model model) {
		model.addAttribute("moduleMap", COURSEWARE_STATUS);
		model.addAttribute("id", id);
		return "console/kjk/fromCwareStatusEdit";
	}
	
	/**
	 * 根据三级学科获取二级学科
	 * 
	 * @param subjectName2
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ajaxSubjectName")
	public List<NcmeSubject> ajaxSubjectName(String subjectName2) {
		List<NcmeSubject> list = ncmeSubjectService.getNcmeSubjectByName(subjectName2);
		return list;
	}

	/**
	 * 添加或者修改
	 * 
	 * @param kjkCourseware
	 * @param result
	 * @param request
	 * @return
	 */
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/courseware/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap save(@Valid KjkCourseware kjkCourseware, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {

			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
			if (kjkCourseware.getId() == null && kjkService.getListByName(kjkCourseware.getName()).size() > 0)
				return ReturnUtil.Error("课程名称已存在", null, null);
			if (StringUtils.isEmpty(kjkCourseware.getPlayType())
					&& StringUtils.isEmpty(kjkCourseware.getMobileType())) {
				return ReturnUtil.Error("播放类型不能为空", null, null);
			} else {
				// 手机播放类型或pc播放类型 为cc格式
				if (kjkCourseware.getPlayType().equals(ConstantEnum.KJK_PLAY_TYPE_CC.toString())
						|| StringUtils.isEmpty(kjkCourseware.getMobileType())
								&& kjkCourseware.getMobileType().equals(ConstantEnum.KJK_PLAY_TYPE_CC.toString())) {
					if (StringUtils.isEmpty(kjkCourseware.getCode()))
						return ReturnUtil.Error("课件编号不能为空", null, null);
					if (kjkCourseware.getCode().trim().length() != 32)
						return ReturnUtil.Error("课件编号长度必须为32位", null, null);
					String par1 = kjkCourseware.getPar1();
					String par2 = kjkCourseware.getPar2();
					if (!par1.contains("vid=") || !par2.contains("vid="))
						return ReturnUtil.Error("播放参数格式不对", null, null);
					String str1 = par1.substring(par1.indexOf("vid=") + 4, par1.indexOf("&siteid"));
					String str2 = par2.substring(par2.indexOf("vid=") + 4, par2.indexOf("&siteid"));
					// 课件类型是CC视频课件 播放参数1和播放参数2中vid必须相同
					if (!str1.endsWith(str2))
						return ReturnUtil.Error("播放参数1和播放参数2中vid必须相同", null, null);
				}
			}
			kjkCourseware.setStatus(KjkEnum.KJK_COURSEWARE_STATUS_ENABLE.getValue().intValue());
			kjkCourseware.setPlayFlag(ConstantEnum.KJK_COURSEWARE_PLY_FLAG_NOT.toString());
			kjkCourseware.setClickCount(0); // 点击量
			kjkCourseware.setShotYear(DateUtil.getCurrentYear());// 拍摄年份
			kjkCourseware.setClassTime(new BigDecimal(DateUtil.dateToSS(kjkCourseware.getClassTimeStr())));
			kjkCourseware.setClassHour(new BigDecimal(0));
			kjkCourseware.setUpdateDate(DateUtil.getSysTime());
			if (kjkCourseware.getId() != null) {
				KjkCourseware tempKjk = kjkService.getById(kjkCourseware.getId());
				kjkCourseware.setCreateDate(tempKjk.getCreateDate());
				kjkCourseware.setAddDate(tempKjk.getAddDate());
				kjkCourseware.setCreater(tempKjk.getCreater());
				kjkCourseware.setModifier(admin.getUid());
				kjkService.save(kjkCourseware);
				return ReturnUtil.Success("操作成功", 1, null);
			} else {
				kjkCourseware.setCreater(admin.getUid());
				kjkCourseware.setCreateDate(DateUtil.getSysTime());
				kjkCourseware.setAddDate(DateUtil.getSysTime());
				kjkService.insert(kjkCourseware);
			}

			return ReturnUtil.Success("操作成功", 2, null);
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}

	}

	/**
	 * 删除课件
	 * 
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

	/**
	 * yuguoliang 课件预览
	 * 
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/courseware/view", method = { RequestMethod.GET })
	public String view(ModelMap model, Long id, HttpServletRequest request) {
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
		if (admin == null) {
			return "redirect:/console/login";
		}
		KjkCourseware kjkCourseware = kjkService.getById(id);
		String url = "";
		if (kjkCourseware != null) {
			if (kjkCourseware.getPlayType().equals(ConstantEnum.KJK_PLAY_TYPE_CC.toString())) {// cc视频
				url = "https://p.bokecc.com/player?vid=" + kjkCourseware.getCode()
						+ "&siteid=4066F9F39D08AB88&autoStart=false&width=600px&height=490px&playerid=5B8B3C4F1E5EBECF&playertype=1";
				model.addAttribute("url", url);
				return "console/kjk/ccView";
			} else if (kjkCourseware.getPlayType().equals(ConstantEnum.KJK_PLAY_TYPE_CME.toString())) {// cme视频
				url = "http://media.cmechina.net/cme_main.html?courseid=" + kjkCourseware.getPar1() + "&coursewareNo="
						+ kjkCourseware.getPar2()
						+ "&userid=null&examurl=http://www.cmechina.net/study/course_quiz.jsp&examicon=null";
				model.addAttribute("url", url);
				return "console/kjk/cmeView";
			}
		}
		return null;
	}

	/**
	 * 设置状态
	 * 
	 * @param kjkCourseware
	 * @param result
	 * @param request
	 * @return
	 */
	@RequiresPermissions("courseware:edit")
	@RequestMapping(value = "/courseware/editStatus", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap editStatus(KjkCourseware kjkCourseware, HttpServletRequest request) {
		try {
			kjkService.updateCourseCware(kjkCourseware);
			return ReturnUtil.Success("操作成功", 1, null);
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
		
	}
	
	private static List<String> getDuplicateElements(List<CoursewareVo> list) {
		return list.stream() // list 对应的 Stream
				.collect(Collectors.toMap(CoursewareVo::getName, e -> 1, (a, b) -> a + b)) // 获得元素出现频率的
																							// Map，键为元素，值为元素出现的次数
				.entrySet().stream() // 所有 entry 对应的 Stream
				.filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
				.map(entry -> entry.getKey()) // 获得 entry 的键（重复元素）对应的 Stream
				.collect(Collectors.toList()); // 转化为 List
	}
	/*** kjk end ***/
}