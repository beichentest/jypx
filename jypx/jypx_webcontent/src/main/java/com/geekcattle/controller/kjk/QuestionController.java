package com.geekcattle.controller.kjk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
import org.thymeleaf.util.ArrayUtils;

import com.geekcattle.conf.ConstantEnum;
import com.geekcattle.conf.KjkEnum;
import com.geekcattle.model.console.Admin;
import com.geekcattle.model.kjk.KjkCourseware;
import com.geekcattle.model.kjk.KjkDic;
import com.geekcattle.model.kjk.KjkQuestion;
import com.geekcattle.service.console.LogService;
import com.geekcattle.service.importdata.QuestionVerify;
import com.geekcattle.service.kjk.KjkDicService;
import com.geekcattle.service.kjk.KjkQuestionService;
import com.geekcattle.service.kjk.KjkService;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.FileUtil;
import com.geekcattle.util.IdUtil;
import com.geekcattle.util.IpUtil;
import com.geekcattle.util.JsonUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UuidUtil;
import com.geekcattle.vo.kjk.CoursewareVo;
import com.geekcattle.vo.kjk.Option;
import com.geekcattle.vo.kjk.QuestionVo;
import com.github.pagehelper.PageInfo;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

@Controller
@RequestMapping("/console/kjk")
public class QuestionController {
	private final static Logger logger = LoggerFactory.getLogger(QuestionController.class);
	@Value("${upload.courseware.filepath}")
	private String uploadCoursewarePath;
	@Value("${upload.courseware.errfilepath}")
	private String uploadCoursewareErrPath;
	@Autowired
	private KjkDicService dicService;
	@Autowired
	private KjkQuestionService questionService;
	@Autowired
	private LogService logService;
	@Autowired
	private KjkService kjkService;
	@RequiresPermissions("question:index")
	@RequestMapping(value = "/dic/flushDic", method = { RequestMethod.GET })
	public void flushDic(Model model) {
		dicService.flushDic();
	}

	@RequiresPermissions("question:index")
	@RequestMapping(value = "/question/index", method = { RequestMethod.GET })
	public String indexQuestion(Model model) {
		List<KjkDic> questionTypeList = dicService.findDicByType(ConstantEnum.KJK_DIC_TYPE_QUESTION_TYPE.toString());
		List<KjkDic> questionClassList = dicService.findDicByType(ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS.toString());
		model.addAttribute("questionTypeList",questionTypeList);
		model.addAttribute("questionClassList",questionClassList);
		return "console/kjk/indexQuestion";
	}

	@RequiresPermissions("question:index")
	@RequestMapping(value = "/question/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listQuestion(Model model, KjkQuestion question) {
		ModelMap map = new ModelMap();
		List<KjkQuestion> lists = questionService.getPageList(question);
		map.put("pageInfo", new PageInfo<KjkQuestion>(lists));
		map.put("queryParam", question);
		return ReturnUtil.Success("加载成功", map, null);
	}

	@RequiresPermissions("question:edit")
	@RequestMapping(value = "/question/eidt", method = { RequestMethod.GET })
	public String from(KjkQuestion question, Model model) {
		List<KjkDic> questionTypeList = dicService.findDicByType(ConstantEnum.KJK_DIC_TYPE_QUESTION_TYPE.toString());
		List<KjkDic> questionClassList = dicService.findDicByType(ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS.toString());
		if (StringUtils.isNotBlank(question.getqId())) {
			question = questionService.getQuestionById(question.getqId());
			if (StringUtils.isNotBlank(question.getqData())) {
				question.setOptions(JsonUtil.parse(question.getqData(),Option.class));
			}
		} else {
			List<Option> options = new ArrayList<Option>();
			Option option = null;
			char alisa = 'A';
			for (int i = 0; i < KjkEnum.KJK_QUESTION_OPTION_NUMBER.getValue(); i++) {
				option = new Option();
				option.setAlisa(String.valueOf(alisa));
				option.setType("radio");
				if (i == 0)
					option.setChecked(true);
				options.add(option);
				alisa = (char) (alisa + '\001');
			}
			question.setOptions(options);
		}
		model.addAttribute("questionTypeList", questionTypeList);
		model.addAttribute("questionClassList", questionClassList);
		model.addAttribute("question", question);
		return "console/kjk/fromQuestionEdit";
	}

	@RequiresPermissions("question:edit")
	@RequestMapping(value = "/question/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap save(@Valid KjkQuestion question,BindingResult result,String[] opts, HttpServletRequest request) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			String ip = IpUtil.getIpAddr(request);
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();			
			if (ConstantEnum.KJK_DIC_TYPE_QUESTION_TYPE_PROCESS.toString().equals(question.getqType())
					&& StringUtils.isBlank(question.getExecuteTime())) {
				return ReturnUtil.Error("过程题中出现时间不能为空", null, null);
			}			
			KjkCourseware courseware = kjkService.getById(question.getCwId());
			if(courseware==null) {
				return ReturnUtil.Error("课件ID："+question.getCwId()+",不存在！", null, null);
			}				
			question.setqData(JsonUtil.toJson(question.getOptions()));
			question.setqKey(StringUtils.join(question.getKeys(), null));
			question.setOperator(admin.getUid());
			question.setModifyDate(DateUtil.getSysTime());
			addData(question,opts);
			if (StringUtils.isEmpty(question.getqId())) {
				question.setqId(UuidUtil.getUUID());
				question.setCreateDate(DateUtil.getSysTime());
				questionService.insetQuestion(question);
				logService.insertLoginLog(admin.getUsername(), ip,
						"添加试题" + question.getContent()+" : "+question.getqId());
			} else {
				questionService.updateQuestion(question);
				logService.insertLoginLog(admin.getUsername(), ip,
						"修改试题" + question.getContent()+" : "+question.getqId());
			}
			return ReturnUtil.Success("操作成功", 2,
					null);
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
	@RequiresPermissions("question:edit")
	@RequestMapping(value = "/question/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap delete(String qIds, HttpServletRequest request) {
		String ip = IpUtil.getIpAddr(request);
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
		questionService.deleteQuestion(qIds);
		logService.insertLoginLog(admin.getUsername(), ip,
				"删除试题" +qIds);
		return ReturnUtil.Success("1", null, null);
	}
	
	@RequiresPermissions("question:edit")
	@RequestMapping("/question/importQuestion")
	@ResponseBody
	public ModelMap fromImportQuestion(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "imgFile", required = false) MultipartFile impFile) throws Exception {
		String filename = impFile.getOriginalFilename();
		if (impFile == null || impFile.getSize() == 0 || filename == null) {
			return ReturnUtil.Error("请先选择有内容的文件", null, null);
		}
		Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();		
		ImportParams imParam = new ImportParams();
		imParam.setNeedVerfiy(true);
		imParam.setVerifyHanlder(new QuestionVerify(admin.getUid()));
		long beginTime = System.currentTimeMillis();
		ExcelImportResult<QuestionVo> eir = ExcelImportUtil.importExcelVerify(impFile.getInputStream(),
				QuestionVo.class, imParam);
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
			List<QuestionVo> list = eir.getList();
			if (list != null && list.size() > 0) {				
				questionService.insertQuestionBatch(list);
				String ip = IpUtil.getIpAddr(request);
				logService.insertLoginLog(admin.getUsername(), ip, "导入试题" + list.size() + "条");
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - beginTime);
		return ReturnUtil.Success("导入成功", 2, null);
	}
	
	private KjkQuestion addData(KjkQuestion question,String[] opts) {
		char alisa = 'A';
		List<Option> options = new ArrayList<Option>();
		Option option = null;
		for (String text : opts) {
			option = new Option();
			option.setAlisa(String.valueOf(alisa));
			option.setText(text);
			if(ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS_SINGLE.toString().equals(question.getqClass())) {
				option.setType("radio");
			}else if(ConstantEnum.KJK_DIC_TYPE_QUESTION_CLASS_MULTIPLE.toString().equals(question.getqClass())) {
				option.setType("checkbox");
			}
			if(ArrayUtils.contains(question.getKeys(), String.valueOf(alisa))) {
				option.setChecked(true);
			}						
			alisa = (char) (alisa + '\001');
			options.add(option);
		}
		question.setqData(JsonUtil.toJson(options));
		return question;
	}
}
