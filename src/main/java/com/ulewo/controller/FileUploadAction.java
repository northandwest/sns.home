package com.ulewo.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ulewo.po.enums.DateTimePatternEnum;
import com.ulewo.po.enums.ResponseCode;
import com.ulewo.utils.Constants;
import com.ulewo.utils.DateUtil;
import com.ulewo.utils.ServerUtils;

@Controller
public class FileUploadAction {

	private final static int MAX_FILE = 1024 * 1024 * 2;

	private Logger log = LoggerFactory.getLogger(FileUploadAction.class);

	@ResponseBody
	@RequestMapping(value = "/fileUpload.action")
	public Map<String, Object> fileupload(HttpSession session, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String realPath = ServerUtils.getRealPath();
			Iterator<String> itr = request.getFileNames();
			if (itr.hasNext()) {
				MultipartFile multipartFile = request.getFile(itr.next());
				long size = multipartFile.getSize();
				if (size > MAX_FILE) {
					map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
					map.put("msg", "文件最大不能超过2M");
					return map;
				}
				String fileName = multipartFile.getOriginalFilename();
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (!"zip".equalsIgnoreCase(suffix) && !"rar".equalsIgnoreCase(suffix)) {
					map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
					map.put("msg", "文件类型只能是压缩包");
					return map;
				}
				String current = String.valueOf(System.currentTimeMillis());
				fileName = current + "." + suffix;
				String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
				String savePath = saveDir + "/" + fileName;
				String fileDir = realPath + Constants.PATH_TEMP_UPLOAD + saveDir;
				File dir = new File(fileDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				String filePath = fileDir + "/" + fileName;
				File file = new File(filePath);
				multipartFile.transferTo(file);
				map.put("responseCode", ResponseCode.SUCCESS.getCode());
				map.put("savePath", savePath);
				return map;
			} else {
				map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
				map.put("msg", "上传文件未找到");
				return map;
			}
		} catch (Exception e) {
			map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
			map.put("msg", "服务器异常，上传失败");
			log.error(e.getMessage(), e);
			return map;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteFile.action", method = RequestMethod.POST)
	public Map<String, Object> deleteFile(HttpSession session, HttpServletRequest request, String fileName) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String realPath = ServerUtils.getRealPath() + Constants.PATH_TEMP_UPLOAD;
			File file = new File(realPath + fileName);
			if (file.exists()) {
				file.delete();
			}
			modelMap.put("result", ResponseCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error("删除附件异常" + e.getMessage(), e);
			modelMap.put("result", ResponseCode.SERVERERROR.getCode());
			return modelMap;
		}
	}
}
