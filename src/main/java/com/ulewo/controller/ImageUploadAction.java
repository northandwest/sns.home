package com.ulewo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
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
import com.ulewo.po.vo.UeditorUploadResult;
import com.ulewo.utils.Constants;
import com.ulewo.utils.DateUtil;
import com.ulewo.utils.ScaleFilter;
import com.ulewo.utils.ServerUtils;

@Controller
public class ImageUploadAction {

	private final static int MAX_FILE = 1024 * 1024 * 2;

	private final static int MAX_FILE_MAX = 1024 * 1024 * 3;

	private final static int TEMP_IMG_MAX_LENGTH = 1000;

	private Logger log = LoggerFactory.getLogger(ImageUploadAction.class);

	@ResponseBody
	@RequestMapping(value = "/imageUpload.action")
	public Map<String, Object> imageUpload(HttpSession session, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String realPath = ServerUtils.getRealPath();
			Iterator<String> itr = request.getFileNames();
			if (itr.hasNext()) {
				MultipartFile multipartFile = request.getFile(itr.next());
				long size = multipartFile.getSize();
				if (size > MAX_FILE_MAX) {
					map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
					map.put("msg", "文件最大不能超过3M");
					return map;
				}
				String fileName = multipartFile.getOriginalFilename();
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (!"JPG".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)
						&& !"gif".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix)) {
					map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
					map.put("msg", "文件类型只能是图片");
					return map;
				}
				String current = String.valueOf(System.currentTimeMillis());
				fileName = current + "." + suffix;
				String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
				String savePath = saveDir + "/" + fileName;
				String fileDir = realPath + Constants.PATH_UPLOAD + saveDir;
				File dir = new File(fileDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				String filePath = fileDir + "/" + fileName;
				File file = new File(filePath);
				multipartFile.transferTo(file);
				if (size > MAX_FILE) {
					BufferedImage src = ImageIO.read(file);
					BufferedImage dst = new ScaleFilter().filter4Height(src, src.getWidth());
					ImageIO.write(dst, "JPEG", file);
				}
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
	@RequestMapping(value = "/imageUpload2Temp.action")
	public Map<String, Object> imageUpload2Temp(HttpSession session, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Iterator<String> itr = request.getFileNames();
			if (itr.hasNext()) {
				MultipartFile multipartFile = request.getFile(itr.next());
				
				long size = multipartFile.getSize();
				
				if (size > MAX_FILE_MAX) {
					result.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
					result.put("msg", "文件最大不能超过3M");
					return result;
				}
				
				String fileName = multipartFile.getOriginalFilename();
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (!"JPG".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)
						&& !"gif".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix)) {
					result.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
					result.put("msg", "文件类型只能是图片");
					return result;
				}
				
				String current = String.valueOf(System.currentTimeMillis());
				fileName = current + "." + suffix;
				String savePath = Constants.PATH_TEMP_UPLOAD + fileName;
				String fileDir = ServerUtils.getRealPath() + Constants.PATH_TEMP_UPLOAD;
				File dir = new File(fileDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				String filePath = fileDir + fileName;
				File file = new File(filePath);
				multipartFile.transferTo(file);
				
				BufferedImage src = ImageIO.read(file);
				if (src.getWidth() > TEMP_IMG_MAX_LENGTH) {
					result.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
					result.put("msg", "图片超过制定宽度");
					file.delete();
					return result;
				}
				
				if (size > MAX_FILE) {
					BufferedImage dst = new ScaleFilter().filter4Height(src, src.getWidth());
					ImageIO.write(dst, "JPEG", file);
				}
				result.put("responseCode", ResponseCode.SUCCESS.getCode());
				result.put("savePath", savePath);
				return result;
				
			} else {
				result.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
				result.put("msg", "上传文件未找到");
				return result;
			}
			
		} catch (Exception e) {
			result.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
			result.put("msg", "服务器异常，上传失败");
			log.error(e.getMessage(), e);
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/imageUpload4Ajax.action", method = RequestMethod.POST)
	public Map<String, Object> imageUpload4Ajax(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String realPath = session.getServletContext().getRealPath("/");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			long size = multipartFile.getSize();
			if (size > MAX_FILE_MAX) {
				map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
				map.put("msg", "文件最大不能超过3M");
				return map;
			}
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!"JPG".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix) && !"gif".equalsIgnoreCase(suffix)
					&& !"BMP".equalsIgnoreCase(suffix)) {
				map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
				map.put("msg", "文件类型只能是图片");
				return map;
			}
			String current = String.valueOf(System.currentTimeMillis());
			fileName = current + "." + suffix;
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
			String saveDir = formater.format(new Date());
			String savePath = saveDir + "/" + fileName;
			String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
			String hostPath = "http://" + request.getServerName() + port + request.getContextPath();
			savePath = hostPath + Constants.PATH_UPLOAD + savePath;
			String fileDir = realPath + Constants.PATH_UPLOAD + saveDir;
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			multipartFile.transferTo(file);
			if (size > MAX_FILE) {
				BufferedImage src = ImageIO.read(file);
				BufferedImage dst = new ScaleFilter().filter4Height(src, src.getWidth());
				ImageIO.write(dst, "JPEG", file);
			}
			map.put("responseCode", ResponseCode.SUCCESS.getCode());
			map.put("savePath", savePath);
			return map;
		} catch (Exception e) {
			map.put("responseCode", ResponseCode.BUSINESSERROR.getCode());
			map.put("msg", "系统异常");
			return map;
		}
	}

	/**
	 * 
	 * ueditorImageUpload:(百度编辑器图片上传). <br/>
	 *
	 * @author 不错啊
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @since JDK 1.7
	 */
	@ResponseBody
	@RequestMapping(value = "/ueditorImageUpload.action")
	public UeditorUploadResult ueditorImageUpload(HttpSession session, MultipartHttpServletRequest request,
			HttpServletResponse response) {

		UeditorUploadResult result = new UeditorUploadResult();
		try {
			String realPath = ServerUtils.getRealPath();
			Iterator<String> itr = request.getFileNames();
			if (itr.hasNext()) {
				MultipartFile multipartFile = request.getFile(itr.next());
				long size = multipartFile.getSize();
				if (size > MAX_FILE_MAX) {
					result.setState("图片超过限制");
					return result;
				}
				String fileName = multipartFile.getOriginalFilename();
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (!"JPG".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)
						&& !"gif".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix)) {
					result.setState("只允许上传图片文件");
					return result;
				}
				String current = String.valueOf(System.currentTimeMillis());
				fileName = current + "." + suffix;
				String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
				String savePath = saveDir + "/" + fileName;
				String fileDir = realPath + Constants.PATH_UPLOAD + saveDir;
				File dir = new File(fileDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				String filePath = fileDir + "/" + fileName;
				File file = new File(filePath);
				multipartFile.transferTo(file);
				if (size > MAX_FILE) {
					BufferedImage src = ImageIO.read(file);
					BufferedImage dst = new ScaleFilter().filter4Height(src, src.getWidth());
					ImageIO.write(dst, "JPEG", file);
				}
				result.setState("SUCCESS");
				result.setUrl(ServerUtils.getDomain() + Constants.PATH_UPLOAD + savePath);
				return result;
			} else {
				result.setState("上传参数不对");
				return result;
			}
		} catch (Exception e) {
			result.setState("服务器异常");
			return result;
		}
	}
}
