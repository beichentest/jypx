/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle.controller.console;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.geekcattle.conf.ModuleEnum;
import com.geekcattle.model.cms.Info;
import com.geekcattle.model.console.Admin;
import com.geekcattle.service.cms.BannerService;
import com.geekcattle.service.cms.ScienceEducationService;
import com.geekcattle.service.common.RedisService;
import com.geekcattle.util.DateUtil;
import com.geekcattle.util.ReturnUtil;
import com.geekcattle.util.UploadUtil;
import com.geekcattle.util.UuidUtil;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/console/operation")
public class OperationController {
	private final static Logger logger = LoggerFactory.getLogger(OperationController.class);
	@Value("${spring.upload.path}")
	private String uploadPath;

	@Autowired
	private BannerService bannerService;

	/*** Banner图begin…… ***/
	@RequiresPermissions("cms:operation:banner:index")
	@RequestMapping(value = "/banner/index", method = { RequestMethod.GET })
	public String index(Model model) {
		return "console/cms/indexBanner";
	}

	@RequiresPermissions("cms:operation:banner:index")
	@RequestMapping(value = "/banner/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap list(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = bannerService.getPageList(info,ModuleEnum.BANNER.toString());
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}

	@RequiresPermissions("cms:operation:banner:edit")
	@RequestMapping(value = "/banner/from", method = { RequestMethod.GET })
	public String from(Info info, Model model) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = bannerService.getById(info.getInfoId());
		}
		model.addAttribute("info", info);
		return "console/cms/fromBanner";
	}

	@RequiresPermissions("cms:operation:banner:edit")
	@RequestMapping(value = "/banner/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap save(Info info, BindingResult result,
			@RequestParam(value = "imgFile", required = false) MultipartFile file) {
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
				return ReturnUtil.Error("Banner标题不能为空", null, null);
			}
			if (StringUtils.isEmpty(info.getIsOpen())) {
				return ReturnUtil.Error("是否启用不能为空", null, null);
			}
			if (info.getIsOpen().equals("1")) {
				if (StringUtils.isEmpty(info.getLinkUrl()))
					return ReturnUtil.Error("跳转链接不能为空", null, null);
				if (StringUtils.isEmpty(imgUrl))
					return ReturnUtil.Error("海报图片不能为空", null, null);
			}
			info.setImgUrl(imgUrl);
			info.setUpdteDate(DateUtil.getSysTime());
			info.setCreateUser(admin.getUid());
			info.setModuleId(ModuleEnum.BANNER.toString());
			if (StringUtils.isEmpty(info.getInfoId())) {
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				bannerService.insert(info);
			} else {
				bannerService.save(info);
			}
			return ReturnUtil.Success("操作成功", null, "/console/operation/banner/index");
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}

	@RequiresPermissions("cms:operation:banner:edit")
	@RequestMapping(value = "/showPhotoByPath", method = RequestMethod.GET)
	@ResponseBody
	public void showPhotoByPath(String path, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(path)) {
			try {
				InputStream is = new FileInputStream(uploadPath + path);
				Image image = ImageIO.read(is);// 读图片
				String imageType = path.substring(path.lastIndexOf(".") + 1);
				RenderedImage img = (RenderedImage) image;
				OutputStream out = response.getOutputStream();
				ImageIO.write(img, imageType, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.error("======", e);
			}
		}
	}

	@RequiresPermissions("cms:operation:banner:delete")
	@RequestMapping(value = "/banner/delete", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap delete(String[] ids) {
		try {
			if ("null".equals(ids) || "".equals(ids)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				for (String id : ids) {
					bannerService.deleteById(id);
				}
				return ReturnUtil.Success("操作成功", null, null);
			}
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}

	/*** Banner图  end***/
	/*** advert begin…… ***/
	@RequiresPermissions("cms:operation:advert:index")
	@RequestMapping(value = "/advert/index", method = { RequestMethod.GET })
	public String indexAdvert(Model model) {
		return "console/cms/indexAdvert";
	}

	@RequiresPermissions("cms:operation:advert:index")
	@RequestMapping(value = "/advert/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listAdvert(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = bannerService.getPageList(info,ModuleEnum.ADVERT.toString());
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}

	@RequiresPermissions("cms:operation:advert:edit")
	@RequestMapping(value = "/advert/from", method = { RequestMethod.GET })
	public String fromAdvert(Info info, Model model) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = bannerService.getById(info.getInfoId());
		}
		model.addAttribute("info", info);
		return "console/cms/fromAdvert";
	}

	@RequiresPermissions("cms:operation:advert:edit")
	@RequestMapping(value = "/advert/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveAdvert(Info info, BindingResult result,
			@RequestParam(value = "imgFile", required = false) MultipartFile file) {
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
				return ReturnUtil.Error("Banner标题不能为空", null, null);
			}
			if (StringUtils.isEmpty(info.getIsOpen())) {
				return ReturnUtil.Error("是否启用不能为空", null, null);
			}			
			info.setImgUrl(imgUrl);
			info.setUpdteDate(DateUtil.getSysTime());
			info.setCreateUser(admin.getUid());
			info.setModuleId(ModuleEnum.ADVERT.toString());
			if (StringUtils.isEmpty(info.getInfoId())) {
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				bannerService.insert(info);
			} else {
				bannerService.save(info);
			}
			return ReturnUtil.Success("操作成功", null, "/console/operation/advert/index");
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
	@RequiresPermissions("cms:operation:advert:delete")
	@RequestMapping(value = "/advert/delete", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap deleteAdvert(String[] ids) {
		try {
			if ("null".equals(ids) || "".equals(ids)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				for (String id : ids) {
					bannerService.deleteById(id);
				}
				return ReturnUtil.Success("操作成功", null, null);
			}
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
	/*** advert end ***/	
	
	/*** topLink begin…… ***/
	@RequiresPermissions("cms:operation:topLink:index")
	@RequestMapping(value = "/topLink/index", method = { RequestMethod.GET })
	public String indexTopLink(Model model) {
		return "console/cms/indexTopLink";
	}

	@RequiresPermissions("cms:operation:topLink:index")
	@RequestMapping(value = "/topLink/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listTopLink(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = bannerService.getPageList(info,ModuleEnum.TOP_LINK.toString());
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}

	@RequiresPermissions("cms:operation:topLink:edit")
	@RequestMapping(value = "/topLink/from", method = { RequestMethod.GET })
	public String fromTopLink(Info info, Model model) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = bannerService.getById(info.getInfoId());
		}
		model.addAttribute("info", info);
		return "console/cms/fromTopLink";
	}

	@RequiresPermissions("cms:operation:topLink:edit")
	@RequestMapping(value = "/topLink/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveTopLink(Info info, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();			
			if (StringUtils.isEmpty(info.getInfoName())) {
				return ReturnUtil.Error("标题不能为空", null, null);
			}
			if (StringUtils.isEmpty(info.getLinkUrl())) {
				return ReturnUtil.Error("跳转链接不能为空", null, null);
			}						
			info.setUpdteDate(DateUtil.getSysTime());
			info.setCreateUser(admin.getUid());
			info.setModuleId(ModuleEnum.TOP_LINK.toString());
			if (StringUtils.isEmpty(info.getInfoId())) {
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				bannerService.insert(info);
			} else {
				bannerService.save(info);
			}
			return ReturnUtil.Success("操作成功", null, "/console/operation/topLink/index");
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
	@RequiresPermissions("cms:operation:topLink:delete")
	@RequestMapping(value = "/topLink/delete", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap deleteTopLink(String[] ids) {
		try {
			if ("null".equals(ids) || "".equals(ids)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				for (String id : ids) {
					bannerService.deleteById(id);
				}
				return ReturnUtil.Success("操作成功", null, null);
			}
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
	/*** topLink end ***/
	
	/*** friendLink begin…… ***/
	@RequiresPermissions("cms:operation:friendLink:index")
	@RequestMapping(value = "/friendLink/index", method = { RequestMethod.GET })
	public String indexFriendLink(Model model) {
		return "console/cms/indexFriendLink";
	}

	@RequiresPermissions("cms:operation:friendLink:index")
	@RequestMapping(value = "/friendLink/list", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap listFriendLink(Info info) {
		ModelMap map = new ModelMap();
		List<Info> lists = bannerService.getPageList(info,ModuleEnum.FRIEND_LINK.toString());
		map.put("pageInfo", new PageInfo<Info>(lists));
		map.put("queryParam", info);
		return ReturnUtil.Success("加载成功", map, null);
	}

	@RequiresPermissions("cms:operation:friendLink:edit")
	@RequestMapping(value = "/friendLink/from", method = { RequestMethod.GET })
	public String fromFriendLink(Info info, Model model) {
		if (!StringUtils.isEmpty(info.getInfoId())) {
			info = bannerService.getById(info.getInfoId());
		}
		model.addAttribute("info", info);
		return "console/cms/fromFriendLink";
	}

	@RequiresPermissions("cms:operation:friendLink:edit")
	@RequestMapping(value = "/friendLink/save", method = { RequestMethod.POST })
	@ResponseBody
	public ModelMap saveFriendLink(Info info, BindingResult result) {
		if (result.hasErrors()) {
			for (ObjectError er : result.getAllErrors())
				return ReturnUtil.Error(er.getDefaultMessage(), null, null);
		}
		try {
			Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();			
			if (StringUtils.isEmpty(info.getInfoName())) {
				return ReturnUtil.Error("标题不能为空", null, null);
			}
			if (StringUtils.isEmpty(info.getLinkUrl())) {
				return ReturnUtil.Error("跳转链接不能为空", null, null);
			}						
			info.setUpdteDate(DateUtil.getSysTime());
			info.setCreateUser(admin.getUid());
			info.setModuleId(ModuleEnum.FRIEND_LINK.toString());
			if (StringUtils.isEmpty(info.getInfoId())) {
				info.setInfoId(UuidUtil.getUUID());
				info.setCreateDate(DateUtil.getSysTime());
				bannerService.insert(info);
			} else {
				bannerService.save(info);
			}
			return ReturnUtil.Success("操作成功", null, "/console/operation/friendLink/index");
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
	@RequiresPermissions("cms:operation:friendLink:delete")
	@RequestMapping(value = "/friendLink/delete", method = { RequestMethod.GET })
	@ResponseBody
	public ModelMap deleteFriendLink(String[] ids) {
		try {
			if ("null".equals(ids) || "".equals(ids)) {
				return ReturnUtil.Error("Error", null, null);
			} else {
				for (String id : ids) {
					bannerService.deleteById(id);
				}
				return ReturnUtil.Success("操作成功", null, null);
			}
		} catch (Exception e) {
			logger.error("======", e);
			e.printStackTrace();
			return ReturnUtil.Error("操作失败", null, null);
		}
	}
	/*** friendLink end ***/
}
